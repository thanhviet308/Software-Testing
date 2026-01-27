package Bai4;

public class PaymentCalculator {

    public enum Type {
        MALE, FEMALE, CHILD
    }

    public static int calculatePayment(int age, Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Type is required");
        }

        if (age < 0 || age > 145) {
            throw new IllegalArgumentException("Age must be in range 0 - 145");
        }

        // Child: 0 - 17
        if (type == Type.CHILD) {
            if (age <= 17) return 50;
            throw new IllegalArgumentException("Child age must be 0 - 17");
        }

        // Adult: 18+
        if (age < 18) {
            throw new IllegalArgumentException("Adult age must be >= 18");
        }

        if (type == Type.MALE) {
            if (age <= 35) return 100;
            if (age <= 50) return 120;
            return 140; // 51 - 145
        }

        // FEMALE
        if (age <= 35) return 80;
        if (age <= 50) return 110;
        return 140; // 51 - 145
    }
}
