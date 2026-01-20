import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Bai1Test {

    @Test
    public void testPower_Nequal0() {
        assertEquals(1.0, Bai1.Power(5, 0), 1e-9);
    }

    @Test
    public void testPower_PositiveN() {
        assertEquals(8.0, Bai1.Power(2, 3), 1e-9);
        assertEquals(27.0, Bai1.Power(3, 3), 1e-9);
    }

    @Test
    public void testPower_NegativeN() {
        assertEquals(0.25, Bai1.Power(2, -2), 1e-9);
        assertEquals(0.5, Bai1.Power(2, -1), 1e-9);
    }

    @Test
    public void testPower_NegativeBase_Odd() {
        assertEquals(-8.0, Bai1.Power(-2, 3), 1e-9);
    }

    @Test
    public void testPower_NegativeBase_Even() {
        assertEquals(4.0, Bai1.Power(-2, 2), 1e-9);
    }

    @Test
    public void testPower_ZeroBase() {
        assertEquals(0.0, Bai1.Power(0, 3), 1e-9);
    }
}
