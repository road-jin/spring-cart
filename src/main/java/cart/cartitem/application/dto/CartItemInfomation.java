package cart.cartitem.application.dto;

import cart.cartitem.domain.CartItemWithProduct;
import java.util.List;
import java.util.stream.Collectors;

public class CartItemInfomation {

    private final Long id;
    private final String productName;
    private final String productImage;
    private final Long productPrice;

    public CartItemInfomation(Long id, String productName, String productImage, Long productPrice) {
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public static CartItemInfomation from(CartItemWithProduct cartItemWithProduct) {
        return new CartItemInfomation(cartItemWithProduct.getId(),
            cartItemWithProduct.getProductName(),
            cartItemWithProduct.getProductImage(),
            cartItemWithProduct.getProductPrice());
    }

    public static List<CartItemInfomation> from(List<CartItemWithProduct> cartItemWithProducts) {
        return cartItemWithProducts.stream()
            .map(CartItemInfomation::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
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
}
