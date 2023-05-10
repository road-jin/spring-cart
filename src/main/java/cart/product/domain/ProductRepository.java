package cart.product.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Product update(Product product);

    void delete(Long id);

    Boolean existById(Long id);
}
