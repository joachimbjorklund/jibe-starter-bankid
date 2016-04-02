package nu.jibe.bankid.api;

/**
 *
 */
public class OrderReference extends StringValidator {
    public OrderReference(String value) {
        super(value, DEFAULT_PATTERN);
    }
}
