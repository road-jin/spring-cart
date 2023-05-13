package cart.cartitem.domain;

import java.util.Objects;

public class CartItem {

    private final Long id;
    private final Long productId;
    private final Long ownerId;

    public CartItem(Long id, Long productId, Long ownerId) {
        this.id = id;
        this.productId = productId;
        this.ownerId = ownerId;
    }

    public boolean verifyOwner(Long memberId) {
        return this.ownerId.equals(memberId);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItem)) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(getId(), cartItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
