package cart.product.application.dto;

import cart.product.domain.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductInformation {

    private final Long id;
    private final String name;
    private final String image;
    private final Long price;

    public ProductInformation(Long id, String name, String image, Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductInformation from(Product product) {
        return new ProductInformation(
            product.getId(),
            product.getName(),
            product.getImage(),
            product.getPrice());
    }

    public static List<ProductInformation> from(List<Product> products) {
        return products.stream()
            .map(ProductInformation::from)
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
