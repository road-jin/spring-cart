package cart.product.web.dto;

import cart.product.application.dto.ProductInformation;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final Long price;

    public ProductResponse(Long id, String name, String image, Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(ProductInformation productInformation) {
        return new ProductResponse(
            productInformation.getId(),
            productInformation.getName(),
            productInformation.getImage(),
            productInformation.getPrice());
    }

    public static List<ProductResponse> from(List<ProductInformation> productInformations) {
        return productInformations.stream()
            .map(ProductResponse::from)
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
