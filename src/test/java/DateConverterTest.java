package app.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DateConverterTest {

    @Test
    void g2h_yyyy_mm_dd() {
        String input1 = "16-10-1957";
        String input2 = "1957-10-16";
        String expectedOutput = "1377-03-22";
        assertEquals(expectedOutput, DateConverter.g2h_yyyy_mm_dd(input1));
        assertEquals(expectedOutput, DateConverter.g2h_yyyy_mm_dd(input2));
    }

    @Test
    void g2h_dd_mm_yyyy() {
        String input1 = "16-10-1957";
        String input2 = "1957-10-16";
        String expectedOutput = "22-03-1377";
        assertEquals(expectedOutput, DateConverter.g2h_dd_mm_yyyy(input1));
        assertEquals(expectedOutput, DateConverter.g2h_dd_mm_yyyy(input2));
    }

    @Test
    void h2g_yyyy_mm_dd() {
        String input1 = "22-03-1377";
        String input2 = "1377-03-22";
        String expectedOutput = "1957-10-16";
        assertEquals(expectedOutput, DateConverter.h2g_yyyy_mm_dd(input1));
        assertEquals(expectedOutput, DateConverter.h2g_yyyy_mm_dd(input2));
    }

    @Test
    void h2g_dd_mm_yyyy() {
        String input1 = "22-03-1377";
        String input2 = "1377-03-22";
        String expectedOutput = "16-10-1957";
        assertEquals(expectedOutput, DateConverter.h2g_dd_mm_yyyy(input1));
        assertEquals(expectedOutput, DateConverter.h2g_dd_mm_yyyy(input2));
    }

}