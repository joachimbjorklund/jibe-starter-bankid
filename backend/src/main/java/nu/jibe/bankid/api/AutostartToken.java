package nu.jibe.bankid.api;

/**
 *
 */
public class AutoStartToken extends StringValidator {
    public AutoStartToken(String value) {
        super(value, DEFAULT_PATTERN);
    }
}
