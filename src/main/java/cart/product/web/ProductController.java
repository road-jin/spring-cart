package cart.product.web;

import cart.product.application.ProductService;
import cart.product.web.dto.ProductCreateRequest;
import cart.product.web.dto.ProductResponse;
import cart.product.web.dto.ProductUpdateRequest;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(ProductResponse.from(productService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return ResponseEntity.ok(ProductResponse.from(productService.create(productCreateRequest.toProductCreateData())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        productUpdateRequest.injectId(id);
        return ResponseEntity.ok(ProductResponse.from(productService.update(productUpdateRequest.toProductUpdateData())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
