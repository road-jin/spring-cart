package cart.cartitem.domain;

import cart.product.domain.Product;
import java.util.Objects;

public class CartItemWithProduct {

    private final Long id;
    private final Product product;
    private final Long ownerId;

    public CartItemWithProduct(Long id, Product product, Long ownerId) {
        this.id = id;
        this.product = product;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }

    public String getProductImage() {
        return product.getImage();
    }

    public Long getProductPrice() {
        return product.getPrice();
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
