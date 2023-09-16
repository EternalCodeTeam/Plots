package com.eternalcode.plots.util;

import java.util.regex.Pattern;

public final class TextUtil {

    private static final Pattern ALPHA_NUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]]+$");

    private TextUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isLetterOrDigit(String text) {
        return ALPHA_NUMERIC_PATTERN.matcher(text).matches();
    }

}
