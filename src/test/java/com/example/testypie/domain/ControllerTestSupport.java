package com.example.testypie.domain;

import com.example.testypie.domain.category.controller.CategoryController;
import com.example.testypie.domain.category.service.CategoryService;
import com.example.testypie.domain.comment.controller.CommentController;
import com.example.testypie.domain.comment.service.CommentService;
import com.example.testypie.domain.feedback.controller.FeedbackController;
import com.example.testypie.domain.feedback.service.FeedbackService;
import com.example.testypie.domain.product.controller.ProductController;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.productLike.controller.ProductLikeController;
import com.example.testypie.domain.productLike.service.ProductLikeService;
import com.example.testypie.domain.reward.controller.RewardController;
import com.example.testypie.domain.reward.service.RewardService;
import com.example.testypie.global.config.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
        controllers = {
            CategoryController.class, CommentController.class, FeedbackController.class,
            RewardController.class, ProductController.class, ProductLikeController.class
        },
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
        })
public abstract class ControllerTestSupport {

    @MockBean protected CategoryService categoryService;

    @MockBean protected CommentService commentService;

    @MockBean protected FeedbackService feedbackService;

    @MockBean protected RewardService rewardService;

    @MockBean protected ProductService productService;

    @MockBean protected ProductLikeService productLikeService;

    @Autowired protected ObjectMapper objectMapper;

    @Autowired protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected Principal mockPrincipal;
}
