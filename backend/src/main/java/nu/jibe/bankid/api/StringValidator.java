package nu.jibe.bankid.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 *
 */
public class StringValidator {
    protected static final Pattern DEFAULT_PATTERN = Pattern.compile(".*");

    private final String value;

    public StringValidator(String value, Pattern pattern) {
        if (!pattern.matcher(requireNonNull(value)).matches()) {
            throw new IllegalArgumentException("value: " + value + " does not match: " + pattern.pattern());
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).toString();
    }
}
