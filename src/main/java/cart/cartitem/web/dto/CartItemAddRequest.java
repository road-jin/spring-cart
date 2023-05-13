package cart.cartitem.web.dto;

import cart.cartitem.application.dto.CartItemAddData;
import javax.validation.constraints.NotNull;

public class CartItemAddRequest {

    @NotNull(message = "상품은 필수 입니다.")
    private Long productId;

    public CartItemAddRequest() {
    }

    public CartItemAddRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public CartItemAddData toCartItemAddData(Long ownerId) {
        return new CartItemAddData(productId, ownerId);
    }
}
