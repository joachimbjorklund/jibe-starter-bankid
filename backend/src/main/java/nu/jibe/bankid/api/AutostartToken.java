package nu.jibe.bankid.api;

/**
 *
 */
public class AutostartToken extends StringValidator {
    public AutostartToken(String value) {
        super(value, DEFAULT_PATTERN);
    }
}
