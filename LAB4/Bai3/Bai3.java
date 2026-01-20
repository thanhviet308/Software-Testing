package Bai3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bai3 {
    private int number;

    public Bai3(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Incorrect Value");
        }
        this.number = number;
    }

    public String ConvertDecimalToAnother(int radix) {
        int n = this.number;

        if (radix < 2 || radix > 16) {
            throw new IllegalArgumentException("Invalid Radix");
        }

        // trường hợp số 0 (nếu muốn hỗ trợ thêm)
        if (n == 0) {
            return "0";
        }

        List<String> result = new ArrayList<>();

        while (n > 0) {
            int value = n % radix;
            if (value < 10) {
                result.add(Integer.toString(value));
            } else {
                switch (value) {
                    case 10: result.add("A"); break;
                    case 11: result.add("B"); break;
                    case 12: result.add("C"); break;
                    case 13: result.add("D"); break;
                    case 14: result.add("E"); break;
                    case 15: result.add("F"); break;
                }
            }
            n /= radix;
        }

        Collections.reverse(result);
        return String.join("", result);
    }
}

