package com.nnk.springboot.constants;

/**
 * Constants to validate Integers or Doubles.
 */
public class NumericValueRegExp {
    /**
     * Regular expression for a Double value.
     */
    public final static String DOUBLE = "[+-]?([0-9]+[,.])?[0-9]+";

    /**
     * Regular expression for an Integer value.
     */
    public final static String INTEGER = "\\d+";
}
