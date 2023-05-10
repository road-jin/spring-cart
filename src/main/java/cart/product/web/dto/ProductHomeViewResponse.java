package cart.product.web.dto;

import cart.product.application.dto.ProductInformation;
import java.util.List;
import java.util.stream.Collectors;

public class ProductHomeViewResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final Long price;

    public ProductHomeViewResponse(Long id, String name, String image, Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductHomeViewResponse from(ProductInformation productInformation) {
        return new ProductHomeViewResponse(
            productInformation.getId(),
            productInformation.getName(),
            productInformation.getImage(),
            productInformation.getPrice());
    }

    public static List<ProductHomeViewResponse> from(List<ProductInformation> productInformations) {
        return productInformations.stream()
            .map(ProductHomeViewResponse::from)
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
