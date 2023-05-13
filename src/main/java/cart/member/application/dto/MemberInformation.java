package cart.member.application.dto;

import cart.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;

public class MemberInformation {

    private final Long id;
    private final String email;
    private final String password;

    public MemberInformation(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberInformation from(Member member) {
        return new MemberInformation(member.getId(), member.getEmail(), member.getPassword());
    }

    public static List<MemberInformation> from(List<Member> members) {
        return members.stream()
            .map(MemberInformation::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
