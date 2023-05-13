package cart.global.authentication;

import cart.global.exception.UnauthorizedException;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final int TOKEN_INDEX = 1;
    private static final String BASIC_TOKEN_BOUNDARY = " ";
    private static final String EMAIL_PASSWORD_BOUNDARY = ":";

    private final AuthenticationService authenticationService;

    public AuthenticationArgumentResolver(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AccountPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
            List<String> credentials = parserToken(authorization);
            return authenticationService.verifyAccount(credentials.get(EMAIL_INDEX), credentials.get(PASSWORD_INDEX));
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            throw new UnauthorizedException();
        }
    }

    private List<String> parserToken(String authorization) {
        String token = authorization.split(BASIC_TOKEN_BOUNDARY)[TOKEN_INDEX];
        String decodeToken = new String(Base64.decodeBase64(token));
        return List.of(decodeToken.split(EMAIL_PASSWORD_BOUNDARY));
    }
}
