package cart.cartitem.exception;

import cart.global.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CartItemWithProductNotFoundException extends ApiException {

    private static final String ERROR_MESSAGE = "해당 장바구니에 있는 상품을 찾을 수 없습니다.";

    public CartItemWithProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, ERROR_MESSAGE);
    }
}
