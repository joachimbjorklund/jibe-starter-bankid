package nu.jibe.bankid.api;

/**
 *
 */
public class Signature extends StringValidator {
    public Signature(String value) {
        super(value, DEFAULT_PATTERN);
    }
}
