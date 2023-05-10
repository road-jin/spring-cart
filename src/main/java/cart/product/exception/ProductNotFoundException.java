package cart.product.exception;

import cart.global.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ApiException {

    private static final String ERROR_MESSAGE = "해당 상품을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, ERROR_MESSAGE);
    }
}
