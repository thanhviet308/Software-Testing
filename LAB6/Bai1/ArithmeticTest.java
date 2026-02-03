import org.junit.Test;

public class ArithmeticTest {

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        JunitMessage jm = new JunitMessage();
        jm.divide(10, 0);
    }
}
