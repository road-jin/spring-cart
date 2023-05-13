package cart.global.authentication;

import cart.global.exception.UnauthorizedException;
import cart.member.domain.Member;
import cart.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;

    public AuthenticationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Account verifyAccount(String email, String password) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(UnauthorizedException::new);

        if (!member.verifyPassword(password)) {
            throw new UnauthorizedException();
        }

        return new Account(member.getId());
    }
}
