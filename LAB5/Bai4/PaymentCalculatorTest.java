package Bai4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import Bai4.PaymentCalculator.Type;

public class PaymentCalculatorTest {

    // ===== Child =====
    @Test
    public void child_0_returns50() {
        assertEquals(50, PaymentCalculator.calculatePayment(0, Type.CHILD));
    }

    @Test
    public void child_17_returns50() {
        assertEquals(50, PaymentCalculator.calculatePayment(17, Type.CHILD));
    }

    @Test(expected = IllegalArgumentException.class)
    public void child_18_throws() {
        PaymentCalculator.calculatePayment(18, Type.CHILD);
    }

    // ===== Male =====
    @Test
    public void male_18_returns100() {
        assertEquals(100, PaymentCalculator.calculatePayment(18, Type.MALE));
    }

    @Test
    public void male_35_returns100() {
        assertEquals(100, PaymentCalculator.calculatePayment(35, Type.MALE));
    }

    @Test
    public void male_36_returns120() {
        assertEquals(120, PaymentCalculator.calculatePayment(36, Type.MALE));
    }

    @Test
    public void male_50_returns120() {
        assertEquals(120, PaymentCalculator.calculatePayment(50, Type.MALE));
    }

    @Test
    public void male_51_returns140() {
        assertEquals(140, PaymentCalculator.calculatePayment(51, Type.MALE));
    }

    @Test
    public void male_145_returns140() {
        assertEquals(140, PaymentCalculator.calculatePayment(145, Type.MALE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void male_17_throws() {
        PaymentCalculator.calculatePayment(17, Type.MALE);
    }

    // ===== Female =====
    @Test
    public void female_18_returns80() {
        assertEquals(80, PaymentCalculator.calculatePayment(18, Type.FEMALE));
    }

    @Test
    public void female_35_returns80() {
        assertEquals(80, PaymentCalculator.calculatePayment(35, Type.FEMALE));
    }

    @Test
    public void female_36_returns110() {
        assertEquals(110, PaymentCalculator.calculatePayment(36, Type.FEMALE));
    }

    @Test
    public void female_50_returns110() {
        assertEquals(110, PaymentCalculator.calculatePayment(50, Type.FEMALE));
    }

    @Test
    public void female_51_returns140() {
        assertEquals(140, PaymentCalculator.calculatePayment(51, Type.FEMALE));
    }

    // ===== Invalid common =====
    @Test(expected = IllegalArgumentException.class)
    public void age_negative_throws() {
        PaymentCalculator.calculatePayment(-1, Type.CHILD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void age_146_throws() {
        PaymentCalculator.calculatePayment(146, Type.MALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void type_null_throws() {
        PaymentCalculator.calculatePayment(20, null);
    }
}
