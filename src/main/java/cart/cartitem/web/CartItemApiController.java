package cart.cartitem.web;

import cart.cartitem.application.CartItemService;
import cart.cartitem.application.dto.CartItemInfomation;
import cart.cartitem.application.dto.CartItemRemoveData;
import cart.cartitem.web.dto.CartItemAddRequest;
import cart.cartitem.web.dto.CartItemResponse;
import cart.global.authentication.Account;
import cart.global.authentication.AccountPrincipal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cart/items")
@RestController
public class CartItemApiController {

    private final CartItemService cartItemService;

    public CartItemApiController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AccountPrincipal Account account) {
        return ResponseEntity.ok(CartItemResponse.from(cartItemService.findAllByMemberId(account.getId())));
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> add(@RequestBody @Valid CartItemAddRequest cartItemAddRequest,
        @AccountPrincipal Account account) {
        CartItemInfomation cartItemInfomation = cartItemService.add(
            cartItemAddRequest.toCartItemAddData(account.getId()));
        return ResponseEntity.ok(CartItemResponse.from(cartItemInfomation));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> remove(@PathVariable Long cartItemId, @AccountPrincipal Account account) {
        cartItemService.remove(new CartItemRemoveData(cartItemId, account.getId()));
        return ResponseEntity.noContent().build();
    }
}
