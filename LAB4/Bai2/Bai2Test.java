import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Bai2Test {

    // Đa thức bậc 0: P(x) = 5
    @Test
    public void testCal_ConstantPolynomial() {
        List<Integer> a = Arrays.asList(5);   // a0 = 5
        Bai2 p = new Bai2(0, a);
        assertEquals(5, p.Cal(10));          // P(10) = 5
    }

    // Đa thức P(x) = 2 + 3x + 4x^2, n = 2
    // Tại x = 2: 2 + 3*2 + 4*4 = 24
    @Test
    public void testCal_QuadraticPolynomial() {
        List<Integer> a = Arrays.asList(2, 3, 4); // a0=2, a1=3, a2=4
        Bai2 p = new Bai2(2, a);
        assertEquals(24, p.Cal(2));
    }

    // Kiểm tra ném ngoại lệ khi số hệ số != n + 1 (thiếu hệ số)
    @Test
    public void testConstructor_InvalidCoeffCount_TooFew() {
        List<Integer> a = Arrays.asList(1, 2); // n = 2 nhưng chỉ có 2 hệ số, thiếu a2
        try {
            new Bai2(2, a);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid Data", ex.getMessage());
        }
    }

    // Kiểm tra ném ngoại lệ khi số hệ số != n + 1 (dư hệ số)
    @Test
    public void testConstructor_InvalidCoeffCount_TooMany() {
        List<Integer> a = Arrays.asList(1, 2, 3); // n = 1 nhưng có 3 hệ số
        try {
            new Bai2(1, a);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid Data", ex.getMessage());
        }
    }

    // Kiểm tra ném ngoại lệ khi n âm
    @Test
    public void testConstructor_NegativeDegree() {
        List<Integer> a = Arrays.asList(1); // hệ số gì cũng được
        try {
            new Bai2(-1, a);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid Data", ex.getMessage());
        }
    }
}
