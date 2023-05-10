package cart.product.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.product.application.ProductService;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductViewController.class)
class ProductViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("/ 경로로 접근하면 상품 목록을 조회하여 보여준다.")
    @Test
    void showHomePage() throws Exception {
        given(productService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
            .andExpect(view().name("index"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("products"))
            .andDo(print());
    }

    @DisplayName("/admin 경로로 접근하면 상품 목록을 조회하여 보여준다.")
    @Test
    void showAdminView() throws Exception {
        given(productService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/admin"))
            .andExpect(view().name("admin"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("products"))
            .andDo(print());
    }
}
