import java.util.List;

public class OrderProcessor {

    public double calculateTotal(List<Item> items, String couponCode,
                                 String memberLevel, String paymentMethod) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Gio hang trong");
        }

        double subtotal = items.stream().mapToDouble(Item::getPrice).sum();

        double discount = 0;
        if (couponCode != null && !couponCode.isEmpty()) {
            if (couponCode.equals("SALE10")) {
                discount = subtotal * 0.10;
            } else if (couponCode.equals("SALE20")) {
                discount = subtotal * 0.20;
            } else {
                throw new IllegalArgumentException("Ma giam gia khong hop le");
            }
        }

        double memberDiscount = 0;
        if (memberLevel.equals("GOLD")) {
            memberDiscount = (subtotal - discount) * 0.05;
        } else if (memberLevel.equals("PLATINUM")) {
            memberDiscount = (subtotal - discount) * 0.10;
        }

        double total = subtotal - discount - memberDiscount;

        if (total < 500_000) {
            if (!paymentMethod.equals("COD")) {
                total += 30_000;
            } else {
                total += 20_000;
            }
        }

        return total;
    }
}