package nu.jibe.bankid;

import nu.jibe.bankid.api.User;

import java.util.Random;

/**
 *
 */
public class TestUser {
    private final User.PersonalNumber personalNumber;

    public TestUser(User.PersonalNumber personalNumber) {
        this.personalNumber = personalNumber;
    }

    public static TestUser randomTestUser() {
        return new TestUser(randomPersonalNumber());
    }

    private static User.PersonalNumber randomPersonalNumber() {
        String year = String.valueOf(nextInt(40, 90));
        String month = String.valueOf(nextInt(1, 12));
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = String.valueOf(nextInt(1, 28));
        if (day.length() == 1) {
            day = "0" + day;
        }

        String last = String.valueOf(nextInt(0, 999));
        if (last.length() == 1) {
            last = "00" + last;
        }

        if (last.length() == 2) {
            last = "0" + last;
        }
        String pNbr = String.format("%s%s%s%s", year, month, day, last);
        pNbr += luhn(pNbr);
        return new User.PersonalNumber("19" + pNbr);
    }

    private static String luhn(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        return String.valueOf((mod == 0) ? 0 : 10 - mod);
    }

    private static int nextInt(int min, int max) {
        Random random = new Random();
        int i;
        while ((i = random.nextInt(max + 1)) < min) {
            ;
        }
        return i;
    }

    public User.PersonalNumber getPersonalNumber() {
        return personalNumber;
    }
}
