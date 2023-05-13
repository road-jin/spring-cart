package cart.cartitem.web.dto;

import cart.cartitem.application.dto.CartItemInfomation;
import java.util.List;
import java.util.stream.Collectors;

public class CartItemResponse {

    private final Long id;
    private final String productName;
    private final String productImage;
    private final Long productPrice;

    public CartItemResponse(Long id, String productName, String productImage, Long productPrice) {
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public static CartItemResponse from(CartItemInfomation cartItemInfomation) {
        return new CartItemResponse(cartItemInfomation.getId(), cartItemInfomation.getProductName(),
            cartItemInfomation.getProductImage(), cartItemInfomation.getProductPrice());
    }

    public static List<CartItemResponse> from(List<CartItemInfomation> cartItemServices) {
        return cartItemServices.stream()
            .map(CartItemResponse::from)
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
