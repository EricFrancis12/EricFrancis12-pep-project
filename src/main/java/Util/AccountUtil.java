package Util;

import Model.Account;

public class AccountUtil {
    public static final int MIN_PASSWORD_LENGTH = 4;

    public static boolean isValidUsername(String username) {
        return username != null && !username.isEmpty();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static boolean isValidAccount(Account acct) {
        return isValidUsername(acct.getUsername()) && isValidPassword(acct.getPassword());
    }
}
