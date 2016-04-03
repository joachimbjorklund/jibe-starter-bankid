package nu.jibe.bankid.api;

/**
 *
 */
public class AuthResponse {
    private final OrderReference orderReference;
    private final AutostartToken autoStartToken;

    public AuthResponse(OrderReference orderReference, AutostartToken autoStartToken) {
        this.orderReference = orderReference;
        this.autoStartToken = autoStartToken;
    }

    public AutostartToken getAutoStartToken() {
        return autoStartToken;
    }

    public OrderReference getOrderReference() {
        return orderReference;
    }
}
