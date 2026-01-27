package Bai2;

import org.junit.Test;

public class SuiteTest1 {

    public String message = "Fpoly";
    JunitMessage junitMessage = new JunitMessage(message);

    @Test
    public void testJunitMessage() {
        System.out.println("Junit Message is printing");
        junitMessage.printMessage();
    }
}

