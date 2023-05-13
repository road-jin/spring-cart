package cart.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    private static final String ERROR_MESSAGE = "사용자 정보가 일치하지 않거나 권한이 없습니다.";

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
