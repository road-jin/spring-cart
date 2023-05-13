package cart.member.application;

import cart.member.application.dto.MemberInformation;
import cart.member.domain.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberInformation> findAll() {
        return MemberInformation.from(memberRepository.findAll());
    }
}
