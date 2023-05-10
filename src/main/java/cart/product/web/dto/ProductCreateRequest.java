package cart.product.web.dto;

import cart.product.application.dto.ProductCreateData;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ProductCreateRequest {

    @NotBlank(message = "이름은 필수 값입니다.")
    @Length(min = 1, max = 20, message = "이름은 최소 {min}자 최대 {max} 입니다.")
    private final String name;

    @NotBlank(message = "이미지는 필수 값입니다.")
    @Length(min = 1, max = 1000, message = "이미지 주소는 최소 {min}자 최대 {max} 입니다.")
    private final String image;

    @NotNull(message = "가격은 필수 값입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private final Long price;

    public ProductCreateRequest(String name, String image, Long price) {
        this.name = name;
        this.image = image;
        this.price = price;
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

    public ProductCreateData toProductCreateData() {
        return new ProductCreateData(name, image, price);
    }
}
