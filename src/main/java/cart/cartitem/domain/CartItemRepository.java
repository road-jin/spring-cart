package cart.cartitem.domain;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {

    Long save(CartItem cartItem);

    Optional<CartItemWithProduct> findWithProductById(Long id);

    Optional<CartItem> findById(Long id);

    List<CartItemWithProduct> findAllWithProductByMemberId(Long memberId);

    int delete(Long id);
}
