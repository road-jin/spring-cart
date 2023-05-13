package cart.cartitem.application.dto;

public class CartItemRemoveData {

    private final Long id;
    private final Long memberId;

    public CartItemRemoveData(Long id, Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }
}
