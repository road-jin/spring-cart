package cart.product.web.dto;

import cart.product.application.dto.ProductInformation;
import java.util.List;
import java.util.stream.Collectors;

public class ProductAdminViewResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final Long price;

    public ProductAdminViewResponse(Long id, String name, String image, Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductAdminViewResponse from(ProductInformation productInformation) {
        return new ProductAdminViewResponse(
            productInformation.getId(),
            productInformation.getName(),
            productInformation.getImage(),
            productInformation.getPrice());
    }

    public static List<ProductAdminViewResponse> from(List<ProductInformation> productInformations) {
        return productInformations.stream()
            .map(ProductAdminViewResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }
}
