package nu.jibe.bankid.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 */
public class User {

    private final FirstName firstName;
    private final LastName lastName;
    private final Name name;
    private final PersonalNumber personalNumber;
    private final LocalDate notBefore;
    private final LocalDate notAfter;
    private final IPAddress ipAddress;

    public User(FirstName firstName, LastName lastName, Name name, PersonalNumber personalNumber, LocalDate notBefore, LocalDate notAfter,
            IPAddress ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.personalNumber = personalNumber;
        this.notBefore = notBefore;
        this.notAfter = notAfter;
        this.ipAddress = ipAddress;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getNotAfter() {
        return notAfter;
    }

    public LocalDate getNotBefore() {
        return notBefore;
    }

    public PersonalNumber getPersonalNumber() {
        return personalNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("name", name)
                .append("personalNumber", personalNumber)
                .append("notBefore", notBefore)
                .append("notAfter", notAfter)
                .append("ipAddress", ipAddress)
                .toString();
    }

    /**
     *
     */
    public static class FirstName extends StringValidator {
        public FirstName(String value) {
            super(value, DEFAULT_PATTERN);
        }
    }

    /**
     *
     */
    public static class IPAddress extends StringValidator {
        public IPAddress(String value) {
            super(value, DEFAULT_PATTERN);
        }
    }

    /**
     *
     */
    public static class LastName extends StringValidator {
        public LastName(String value) {
            super(value, DEFAULT_PATTERN);
        }
    }

    /**
     *
     */
    public static class Name extends StringValidator {
        public Name(String value) {
            super(value, DEFAULT_PATTERN);
        }
    }

    /**
     *
     */
    public static class PersonalNumber extends StringValidator {
        public PersonalNumber(String value) {
            super(value, DEFAULT_PATTERN);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PersonalNumber that = (PersonalNumber) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
