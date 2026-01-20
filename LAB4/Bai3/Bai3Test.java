package Bai3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Bai3Test {

    // 10 ở hệ 10 -> "1010" ở hệ 2
    @Test
    public void testConvert_ToBinary() {
        Bai3 r = new Bai3(10);
        assertEquals("1010", r.ConvertDecimalToAnother(2));
    }

    // 255 ở hệ 10 -> "FF" ở hệ 16
    @Test
    public void testConvert_ToHex_FF() {
        Bai3 r = new Bai3(255);
        assertEquals("FF", r.ConvertDecimalToAnother(16));
    }

    // 31 ở hệ 10 -> "1F" ở hệ 16
    @Test
    public void testConvert_ToHex_1F() {
        Bai3 r = new Bai3(31);
        assertEquals("1F", r.ConvertDecimalToAnother(16));
    }

    // chuyển sang cùng cơ số 10 (radix = 10)
    @Test
    public void testConvert_Base10() {
        Bai3 r = new Bai3(123);
        assertEquals("123", r.ConvertDecimalToAnother(10));
    }

    // number âm -> ném ngoại lệ "Incorrect Value"
    @Test
    public void testConstructor_NegativeNumber() {
        try {
            new Bai3(-5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Incorrect Value", ex.getMessage());
        }
    }

    // radix < 2 -> ném ngoại lệ "Invalid Radix"
    @Test
    public void testConvert_InvalidRadix_TooSmall() {
        Bai3 r = new Bai3(10);
        try {
            r.ConvertDecimalToAnother(1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid Radix", ex.getMessage());
        }
    }

    // radix > 16 -> ném ngoại lệ "Invalid Radix"
    @Test
    public void testConvert_InvalidRadix_TooBig() {
        Bai3 r = new Bai3(10);
        try {
            r.ConvertDecimalToAnother(17);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid Radix", ex.getMessage());
        }
    }
}

