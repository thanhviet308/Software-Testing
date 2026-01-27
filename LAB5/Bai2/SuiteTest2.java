package Bai2;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteTest2 {

    public String message = "Fpoly";
    JunitMessage junitMessage = new JunitMessage(message);

    @Test
    public void testJunitHiMessage() {
        message = "Hi!" + message;
        System.out.println("Junit Hi Message is printing");

        assertEquals(message, junitMessage.printHiMessage());
        System.out.println("Suite Test 2 is successful " + message);
    }
}

