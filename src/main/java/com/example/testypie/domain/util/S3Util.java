package com.example.testypie.domain.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Getter
    @RequiredArgsConstructor // final필드를 초기화하기 위해 사용
    public enum FilePath { // 파일 경로를 나타내는 상수를 정의
        PROFILE("profile/"),
        BOARD("board/"),
        COMMENT("comment/"),
        BUGREPORT("bugReportImg/");
        private final String path; // 경로를 저장하는 final 필드
    }

    private static ObjectMetadata setObjectMetadata(MultipartFile multipartFile) {
        // 업로드할 파일의 메타데이터를 설정하는 메소드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }

    public String uploadFile(MultipartFile multipartFile, FilePath filePath) {

        // 업로드할 파일이 이미지인 경우에만 실행
        if (Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            // 업로드할 파일의 고유한 파일명 생성
            String fileName = createFileName(multipartFile.getOriginalFilename());
            String fileFormatName =
                    multipartFile
                            .getContentType()
                            .substring(multipartFile.getContentType().lastIndexOf("/") + 1);

            MultipartFile resizedImage = resizer(fileName, fileFormatName, multipartFile, 400);

            // 파일명을 UTF-8로 디코딩
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            log.info("업로드 파일 디코딩 완료 : " + filePath + fileName);
            // 업로드할 파일의 메타데이터 생성
            ObjectMetadata metadata = setObjectMetadata(resizedImage);
            try {
                // S3에 파일 업로드
                amazonS3Client.putObject(
                        bucketName, filePath.getPath() + fileName, resizedImage.getInputStream(), metadata);
            } catch (IOException e) {
                throw new GlobalExceptionHandler.CustomException(ErrorCode.CREATE_IMAGE_FAIL);
            } catch (Exception e) {
                // 업로드 중에 예외 발생 시 전역 예외(GlobalException) 발생
                throw new GlobalExceptionHandler.CustomException(ErrorCode.SERVER_ERROR);
            }
            // 업로드한 파일의 URL 반환
            return getFileUrl(fileName, filePath);
        }
        return null;
    }

    public void deleteFile(String fileUrl, FilePath filePath) {
        // 주어진 파일 URL로부터 파일명을 추출
        String fileName = getFileNameFromFileUrl(fileUrl, filePath);
        log.info("fileName: {}", fileName);
        // 파일명을 UTF-8로 디코딩
        fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        log.info("bucketName: {" + bucketName + "}");
        log.info("fileKey: {" + filePath.getPath() + fileName + "}");

        // 파일명이 비어있거나 해당 파일이 존재하지 않으면 예외 발생
        if (fileName.isBlank()
                || !amazonS3Client.doesObjectExist(bucketName, filePath.getPath() + fileName)) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_IMAGE_NOT_FOUND);
        }
        // S3에서 파일 삭제
        amazonS3Client.deleteObject(bucketName, filePath.getPath() + fileName);
    }

    public String getFileUrl(String fileName, FilePath filePath) {
        // AWS S3 클라이언트를 사용하여 주어진 버킷, 파일 경로 및 파일명에 해당하는 파일의 URL을 얻어옴
        return amazonS3Client.getUrl(bucketName, filePath.getPath() + fileName).toString();
    }

    private String getFileNameFromFileUrl(String fileUrl, FilePath filePath) {
        // 파일 URL에서 파일 경로 다음의 문자열부터 파일명의 끝까지 추출하여 반환
        return fileUrl.substring(fileUrl.lastIndexOf(filePath.getPath()) + filePath.getPath().length());
    }

    private String createFileName(String fileName) {
        // UUID를 사용하여 고유한 문자열을 생성하고, 주어진 파일명과 연결하여 반환
        return UUID.randomUUID().toString().concat(fileName);
    }

    @Transactional
    public MultipartFile resizer(
            String fileName, String fileFormat, MultipartFile originalImage, int width) {

        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(originalImage.getInputStream());

            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // origin 이미지가 400보다 작으면 패스
            if (originWidth < width) {
                return originalImage;
            }

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", width);
            scale.setAttribute("newHeight", width * originHeight / originWidth); // 비율유지를 위해 높이 유지
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormat, baos);
            baos.flush();

            return new CustomMultipartFile(
                    fileName, fileFormat, originalImage.getContentType(), baos.toByteArray());

        } catch (IOException e) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_IMAGE_NOT_RESIZABLE);
        }
    }
}
