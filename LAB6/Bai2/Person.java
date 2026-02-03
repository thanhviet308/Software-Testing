package Bai2;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be >= 0");
        }
        this.name = name;
        this.age = age;
    }
}
