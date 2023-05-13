package cart.cartitem.application;

import cart.cartitem.application.dto.CartItemAddData;
import cart.cartitem.application.dto.CartItemInfomation;
import cart.cartitem.application.dto.CartItemRemoveData;
import cart.cartitem.domain.CartItem;
import cart.cartitem.domain.CartItemRepository;
import cart.cartitem.domain.CartItemWithProduct;
import cart.cartitem.exception.CartItemNotFoundException;
import cart.cartitem.exception.CartItemWithProductNotFoundException;
import cart.global.exception.UnauthorizedException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional(readOnly = true)
    public List<CartItemInfomation> findAllByMemberId(Long memberId) {
        return CartItemInfomation.from(cartItemRepository.findAllWithProductByMemberId(memberId));
    }

    public CartItemInfomation add(CartItemAddData cartItemAddData) {
        Long savedId = cartItemRepository.save(cartItemAddData.toCartItem());
        CartItemWithProduct cartItemWithProduct = cartItemRepository.findWithProductById(savedId)
            .orElseThrow(CartItemWithProductNotFoundException::new);
        return CartItemInfomation.from(cartItemWithProduct);
    }

    public int remove(CartItemRemoveData cartItemRemoveData) {
        CartItem cartItem = cartItemRepository.findById(cartItemRemoveData.getId())
            .orElseThrow(CartItemNotFoundException::new);

        if (!cartItem.verifyOwner(cartItemRemoveData.getMemberId())) {
            throw new UnauthorizedException();
        }

        return cartItemRepository.delete(cartItemRemoveData.getId());
    }
}
