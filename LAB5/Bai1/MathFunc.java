public class MathFunc {

    private int calls = 0;

    public int getCalls() {
        return calls;
    }

    public int factorial(int n) {
        calls++;

        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }

        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public int plus(int a, int b) {
        calls++;
        return a + b;
    }
}
