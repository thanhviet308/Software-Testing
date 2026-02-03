package Bai3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class ErrorCollectorExample {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

   @Test
public void testCollectErrors() {
    int b = 2;
    if (b == 0) collector.addError(new Throwable("Lỗi 1: chia cho 0"));

    String name = "Nam";
    if (name.trim().isEmpty()) collector.addError(new Throwable("Lỗi 2: name rỗng"));

    int age = 20;
    if (age < 0) collector.addError(new Throwable("Lỗi 3: age âm"));
}

}
