package cart.member.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.global.authentication.AuthenticationArgumentResolver;
import cart.member.application.MemberService;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SettingViewController.class)
public class SettingViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AuthenticationArgumentResolver argumentResolver;

    @DisplayName("/settings 경로로 접근하면 사용자 목록을 조회하여 보여준다.")
    @Test
    void showAdminView() throws Exception {
        given(memberService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/settings"))
            .andExpect(view().name("settings"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("members"))
            .andDo(print());
    }
}
