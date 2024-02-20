package com.example.testypie.domain.bugreport.controller;

import com.example.testypie.domain.bugreport.dto.request.CreateBugReportRequestDTO;
import com.example.testypie.domain.bugreport.dto.response.CreateBugReportResponseDTO;
import com.example.testypie.domain.bugreport.dto.response.ReadBugReportResponseDTO;
import com.example.testypie.domain.bugreport.dto.response.ReadPageBugReportResponseDTO;
import com.example.testypie.domain.bugreport.service.BugReportService;
import com.example.testypie.global.security.UserDetailsImpl;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/category/{parentCategoryName}/{childCategoryId}/products/{productId}")
@RequiredArgsConstructor
public class BugReportController {

    private final BugReportService bugReportService;

    @PostMapping(
            value = "/reports",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CreateBugReportResponseDTO> createBugReport(
            @PathVariable Long productId,
            @RequestPart(value = "req", required = false) CreateBugReportRequestDTO req,
            @RequestPart(value = "file", required = false) @Nullable MultipartFile multipartFile,
            @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        CreateBugReportResponseDTO res =
                bugReportService.createBugReport(productId, req, userDetails.getUser(), multipartFile);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/reports/{bugReport_id}")
    public ModelAndView getProductBugReport(
            @PathVariable Long bugReport_id,
            @PathVariable Long productId,
            @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        ReadBugReportResponseDTO res =
                bugReportService.getBugReport(bugReport_id, productId, userDetails.getUser());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bugReport");
        modelAndView.addObject("bugReport", res);
        return modelAndView;
    }

    @GetMapping("/reports")
    public ResponseEntity<Page<ReadPageBugReportResponseDTO>> getBugReportPage(
            @PathVariable Long productId,
            @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
            Pageable pageable,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        Page<ReadPageBugReportResponseDTO> res =
                bugReportService.getBugReportPage(pageable, productId, userDetails.getUser());

        return ResponseEntity.ok().body(res);
    }
}
