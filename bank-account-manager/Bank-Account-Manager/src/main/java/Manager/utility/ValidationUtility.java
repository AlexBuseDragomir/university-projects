package manager.utility;

import manager.database.DatabaseHandler;
import manager.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtility {

    private static final String ACCEPTED_SPECIAL_CHARACTERS = "!@#$%^&*~?.";

    private static final int MIN_NUMBER_OF_PASSWORD_CHARS = 8;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_MONEY_AMOUNT_REGEX =
            Pattern.compile("^[+]?[0-9]{1,3}(?:,?[0-9]{3})*.[0-9]{0,2}$");

    private static final int MAX_FIRST_NAME_LENGTH = 30;
    private static final int MIN_FIRST_NAME_LENGTH = 2;

    private static final int MAX_LAST_NAME_LENGTH = 30;
    private static final int MIN_LAST_NAME_LENGTH = 2;

    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_USERNAME_LENGTH = 5;

    private static final int MAX_ADDRESS_LENGTH = 150;
    private static final int MIN_ADDRESS_LENGTH = 10;

    public static boolean doesDatabaseContainAccount(String accountName) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet account = databaseHandler.getAccountsByName(accountName);
        return account.next();
    }

    public static boolean doesAccountBelongToUser(User user, String accountName) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet account = databaseHandler.getAccountsByUser(user);
        while (account.next()) {
            if (accountName.equals(account.getString("accountName"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean meetsPasswordFormat(String registerPassword) {
        boolean hasSpecialChar = false;
        boolean hasDigit = false;
        boolean hasLetter = false;
        boolean hasEnoughCharacters = false;

        char[] specialCharArray = ACCEPTED_SPECIAL_CHARACTERS.toCharArray();
        List<Character> specialCharList = new ArrayList<>();

        for (char ch: specialCharArray) {
            specialCharList.add(ch);
        }

        if (registerPassword.length() > MIN_NUMBER_OF_PASSWORD_CHARS) {
            hasEnoughCharacters = true;
        }

        for (int i = 0; i < registerPassword.length(); i++) {
            char ch = registerPassword.charAt(i);
            if (Character.isDigit(ch)) {
                hasDigit = true;
            }
            if (Character.isLetter(ch)) {
                hasLetter = true;
            }
            if (specialCharList.contains(ch)) {
                hasSpecialChar = true;
            }
        }

        return hasSpecialChar && hasDigit &&
                hasLetter && hasEnoughCharacters;
    }

    public static boolean meetsEmailFormat(String registerEmail) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(registerEmail);
        return matcher.find();
    }

    public static boolean meetsUsernameFormat(String registerUsername) {
        return registerUsername.length() >= MIN_USERNAME_LENGTH &&
                registerUsername.length() <= MAX_USERNAME_LENGTH &&
                checkIfStringHasOnlyLetters(registerUsername);
    }

    public static boolean meetsFirstNameFormat(String registerFirstName) {
        return registerFirstName.length() >= MIN_FIRST_NAME_LENGTH &&
                registerFirstName.length() <= MAX_FIRST_NAME_LENGTH &&
                checkIfStringHasOnlyLetters(registerFirstName);
    }

    public static boolean meetsLastNameFormat(String registerLastName) {
        return registerLastName.length() >= MIN_LAST_NAME_LENGTH &&
                registerLastName.length() <= MAX_LAST_NAME_LENGTH &&
                checkIfStringHasOnlyLetters(registerLastName);
    }

    public static boolean meetsAddressFormat(String registerAddress) {
        return registerAddress.length() >= MIN_ADDRESS_LENGTH &&
                registerAddress.length() <= MAX_ADDRESS_LENGTH;
    }

    public static boolean checkIfStringIsMoneyFormat(String stringToTest) {
        Matcher matcher = VALID_MONEY_AMOUNT_REGEX.matcher(stringToTest);
        int sizeBeforeDot = 0;
        for (char ch : stringToTest.toCharArray()) {
            if (ch != '.') {
                sizeBeforeDot++;
            } else {
                break;
            }
        }
        return matcher.find() && sizeBeforeDot < 5;
    }

    private static boolean checkIfStringHasOnlyDigits(String stringToTest) {
        boolean containsOnlyDigits = true;
        for (char ch: stringToTest.toCharArray()) {
            if (! Character.isDigit(ch)) {
                containsOnlyDigits = false;
            }
        }
        return containsOnlyDigits;
    }

    private static boolean checkIfStringHasOnlyLetters(String stringToTest) {
        boolean containsOnlyLetters = true;
        for (char ch: stringToTest.toCharArray()) {
            if (! Character.isLetter(ch)) {
                containsOnlyLetters = false;
            }
        }
        return containsOnlyLetters;
    }

}
