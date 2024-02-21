package com.example.testypie.domain.productLike.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.testypie.common.mvc.MockSpringSecurityFilter;
import com.example.testypie.domain.ControllerTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ProductLikeControllerTest extends ControllerTestSupport {

    @BeforeEach
    void setUp() {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(springSecurity(new MockSpringSecurityFilter()))
                        .alwaysDo(print())
                        .build();
    }

    //    @DisplayName("Product 좋아요 클릭")
    //    @Test
    //    void clickProductLike() throws Exception {
    //        //given
    //
    //        //when
    //
    //        //then
    //    }
}
