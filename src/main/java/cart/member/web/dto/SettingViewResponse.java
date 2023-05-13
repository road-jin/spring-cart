package cart.member.web.dto;

import cart.member.application.dto.MemberInformation;
import java.util.List;
import java.util.stream.Collectors;

public class SettingViewResponse {

    private final String email;
    private final String password;

    public SettingViewResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static SettingViewResponse from(MemberInformation memberInformation) {
        return new SettingViewResponse(memberInformation.getEmail(), memberInformation.getPassword());
    }

    public static List<SettingViewResponse> from(List<MemberInformation> memberInformations) {
        return memberInformations.stream()
            .map(SettingViewResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
