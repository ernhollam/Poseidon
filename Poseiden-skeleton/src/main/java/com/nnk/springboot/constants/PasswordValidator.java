package com.nnk.springboot.constants;

/**
 * Constants to help validate a password.
 */
public class PasswordValidator {
    /**
     * Password must contain at least:
     *  - one upper case letter
     *  - one lower case letter
     *  - one digit
     *  - one special character,
     *  with minimum eight characters in length
     */
    public final static String REGEX ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
}
