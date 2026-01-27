package Bai2;

public class JunitMessage {

    private String message;

    public JunitMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
    }

    public String printHiMessage() {
        System.out.println("Hi!" + message);
        return "Hi!" + message;
    }
}
