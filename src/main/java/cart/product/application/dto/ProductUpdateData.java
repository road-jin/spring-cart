package cart.product.application.dto;

import cart.product.domain.Product;

public class ProductUpdateData {

    private final Long id;
    private final String name;
    private final String image;
    private final Long price;

    public ProductUpdateData(Long id, String name, String image, Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
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

    public Product toProduct() {
        return new Product(id, name, image, price);
    }
}
