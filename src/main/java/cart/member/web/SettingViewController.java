package cart.member.web;

import cart.member.application.MemberService;
import cart.member.web.dto.SettingViewResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingViewController {

    private final MemberService memberService;

    public SettingViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String showSettingView(Model model) {
        model.addAttribute("members", SettingViewResponse.from(memberService.findAll()));
        return "settings";
    }
}
