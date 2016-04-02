package nu.jibe.bankid.api;

/**
 *
 */
public class OcspResponse extends StringValidator {
    public OcspResponse(String value) {
        super(value, DEFAULT_PATTERN);
    }
}
