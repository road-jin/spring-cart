package cart.cartitem.application.dto;

import cart.cartitem.domain.CartItem;

public class CartItemAddData {

    private final Long productId;
    private final Long ownerId;

    public CartItemAddData(Long productId, Long ownerId) {
        this.productId = productId;
        this.ownerId = ownerId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public CartItem toCartItem() {
        return new CartItem(null, productId, ownerId);
    }
}
