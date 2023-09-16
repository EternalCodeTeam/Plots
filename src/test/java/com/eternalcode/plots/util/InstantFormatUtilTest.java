package com.eternalcode.plots.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstantFormatUtilTest {

    @Test
    @DisplayName("Given valid Instant, When format, Then return correct format")
    void testFormat() {
        ZonedDateTime zdt = ZonedDateTime.of(2023, 2, 1, 10, 15, 0, 0, ZoneId.systemDefault());
        Instant instant = zdt.toInstant();

        String result = InstantFormatUtil.format(instant);

        assertEquals("01.02.2023 10:15", result);
    }

    @Test
    @DisplayName("Given valid Instant with different date, When format, Then return correct format")
    void testFormatDifferentDate() {
        ZonedDateTime zdt = ZonedDateTime.of(2022, 12, 31, 23, 59, 0, 0, ZoneId.systemDefault());
        Instant instant = zdt.toInstant();

        String result = InstantFormatUtil.format(instant);

        assertEquals("31.12.2022 23:59", result);
    }

    @Test
    @DisplayName("Given valid Instant with different timezone, When format, Then return correct format following system's timezone")
    void testFormatDifferentTimezone() {
        ZonedDateTime zdt = ZonedDateTime.of(2023, 2, 1, 10, 15, 0, 0, ZoneId.of("Asia/Tokyo"));
        Instant instant = zdt.toInstant();

        String result = InstantFormatUtil.format(instant);

        String expected = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        assertEquals(expected, result);
    }
}