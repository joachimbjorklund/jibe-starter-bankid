package nu.jibe.bankid.api;

/**
 *
 */
public class AuthResponse {
    private final OrderReference orderReference;
    private final AutoStartToken autoStartToken;

    public AuthResponse(OrderReference orderReference, AutoStartToken autoStartToken) {
        this.orderReference = orderReference;
        this.autoStartToken = autoStartToken;
    }

    public AutoStartToken getAutoStartToken() {
        return autoStartToken;
    }

    public OrderReference getOrderReference() {
        return orderReference;
    }
}
