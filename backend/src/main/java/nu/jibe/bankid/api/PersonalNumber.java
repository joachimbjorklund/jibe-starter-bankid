package nu.jibe.bankid.api;

/**
 *
 */
public class PersonalNumber extends StringValidator {
    public PersonalNumber(String value) {
        super(value, DEFAULT_PATTERN);
    }
}
