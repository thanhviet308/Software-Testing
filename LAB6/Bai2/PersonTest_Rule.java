package Bai2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersonTest_Rule {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAgeInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Age must be >= 0");

        new Person("An", -5);
    }
}
