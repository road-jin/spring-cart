package cart.cartitem.domain;

import java.util.Objects;

public class CartItemWithProduct {

    private final Long id;
    private final Long productId;
    private final String productName;
    private final String productImage;
    private final Long productPrice;
    private final Long ownerId;

    public CartItemWithProduct(Long id, Long productId, String productName, String productImage,
        Long productPrice, Long ownerId) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItemWithProduct)) {
            return false;
        }
        CartItemWithProduct that = (CartItemWithProduct) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
