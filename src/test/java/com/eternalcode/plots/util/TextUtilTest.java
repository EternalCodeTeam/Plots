package com.eternalcode.plots.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextUtilTest {

    @Test
    @DisplayName("Return true when string is alphanumeric")
    void whenStringIsAlphanumericReturnTrue() {
        assertTrue(TextUtil.isLetterOrDigit("hello123"));
        assertTrue(TextUtil.isLetterOrDigit("123hello"));
        assertTrue(TextUtil.isLetterOrDigit("HELLO123"));
        assertTrue(TextUtil.isLetterOrDigit("123HELLO"));
        assertTrue(TextUtil.isLetterOrDigit("Hello123"));
    }

    @Test
    @DisplayName("Return true when string is only digits or only letters")
    void whenStringIsOnlyDigitsOrLettersReturnTrue() {
        assertTrue(TextUtil.isLetterOrDigit("1234567890"));
        assertTrue(TextUtil.isLetterOrDigit("Hello"));
        assertTrue(TextUtil.isLetterOrDigit("HELLO"));
        assertTrue(TextUtil.isLetterOrDigit("hello"));
    }

    @Test
    @DisplayName("Return false when string is empty or contains special characters")
    void whenStringIsEmptyOrSpecialReturnFalse() {
        assertFalse(TextUtil.isLetterOrDigit(""));
        assertFalse(TextUtil.isLetterOrDigit("!@#%^&*"));
        assertFalse(TextUtil.isLetterOrDigit("hello!"));
        assertFalse(TextUtil.isLetterOrDigit("123?"));
        assertFalse(TextUtil.isLetterOrDigit("@Hello"));
    }

}