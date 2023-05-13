package cart.product.application;

import cart.product.application.dto.ProductCreateData;
import cart.product.application.dto.ProductInformation;
import cart.product.application.dto.ProductUpdateData;
import cart.product.domain.ProductRepository;
import cart.product.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductInformation> findAll() {
        return ProductInformation.from(productRepository.findAll());
    }

    public ProductInformation create(ProductCreateData productCreateData) {
        return ProductInformation.from(productRepository.save(productCreateData.toProduct()));
    }

    public ProductInformation update(ProductUpdateData productUpdateData) {
        if (!productRepository.existById(productUpdateData.getId())) {
            throw new ProductNotFoundException();
        }

        return ProductInformation.from(productRepository.update(productUpdateData.toProduct()));
    }

    public int delete(Long id) {
        return productRepository.delete(id);
    }
}
