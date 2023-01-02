package com.nnk.springboot.constants;

/**
 * Constants to help validate a password.
 */
public class PasswordRegExp {
    /**
     * Password validation constraints:
     *  - (?=.*[A-Z]) must contain one uppercase letter.
     *  - (?=.*[a-z]) must contain one lowercase letter.
     *  - (?=.*[0-9]) must contain a single digit from 0 to 9.
     *  - (?=.*?[#?!@$%^&*-+\"',./:=^`|~]) must contain one special character,
     *  - .{8,} must be at least eight characters in length
     */
    public final static String REGEX ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-+\\\"',./:=^`|~]).{8,}$";
}
