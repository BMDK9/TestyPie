package com.example.testypie.domain.bugreport.service;

import com.example.testypie.domain.bugreport.dto.request.CreateBugReportRequestDTO;
import com.example.testypie.domain.bugreport.dto.response.CreateBugReportResponseDTO;
import com.example.testypie.domain.bugreport.dto.response.ReadBugReportResponseDTO;
import com.example.testypie.domain.bugreport.dto.response.ReadPageBugReportResponseDTO;
import com.example.testypie.domain.bugreport.entity.BugReport;
import com.example.testypie.domain.bugreport.repository.BugReportRepository;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.util.S3Util;
import com.example.testypie.domain.util.S3Util.FilePath;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class BugReportService {

    private final BugReportRepository bugReportRepository;
    private final ProductService productService;
    private final S3Util s3Util;

    public CreateBugReportResponseDTO createBugReport(
            Long productId, CreateBugReportRequestDTO req, User user, MultipartFile multipartFile) {

        Product product = productService.checkProduct(productId);
        String fileUrl = s3Util.uploadFile(multipartFile, FilePath.BUGREPORT);
        BugReport bugReport =
                BugReport.builder()
                        .content(req.content())
                        .product(product)
                        .user(user)
                        .fileUrl(fileUrl)
                        .build();

        BugReport saveBugReport = bugReportRepository.save(bugReport);

        return CreateBugReportResponseDTO.of(saveBugReport);
    }

    public ReadBugReportResponseDTO getBugReport(Long bugReportId, Long productId, User user) {

        Product product = productService.checkProduct(productId);

        if (!product.getUser().getId().equals(user.getId())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_BUGREPORT_NOT_FOUND);
        }

        return ReadBugReportResponseDTO.of(
                bugReportRepository
                        .findByProductIdAndId(productId, bugReportId)
                        .orElseThrow(
                                () ->
                                        new GlobalExceptionHandler.CustomException(
                                                ErrorCode.SELECT_BUGREPORT_NOT_FOUND)));
    }

    public Page<ReadPageBugReportResponseDTO> getBugReportPage(
            Pageable pageable, Long productId, User user) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        Product product = productService.checkProduct(productId);

        if (product.getUser().getId().equals(user.getId())) {
            Page<BugReport> bugReportPage =
                    bugReportRepository.findAllByProductId(
                            productId, PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "id")));

            List<ReadPageBugReportResponseDTO> resList = new ArrayList<>();

            for (BugReport bugReport : bugReportPage) {
                ReadPageBugReportResponseDTO res = ReadPageBugReportResponseDTO.of(bugReport);
                resList.add(res);
            }

            return new PageImpl<>(resList, pageable, bugReportPage.getTotalElements());
        }

        throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_BUGREPORT_INVALID);
    }
}
