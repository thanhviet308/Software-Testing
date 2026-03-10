import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderProcessorTest {

    private final OrderProcessor orderProcessor = new OrderProcessor();

    private List<Item> items(double... prices) {
        return Arrays.stream(prices)
                .mapToObj(Item::new)
                .toList();
    }

    @Test(description = "Path 1: Gio hang null -> nem exception")
    public void testPath1_EmptyCart_Null() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> orderProcessor.calculateTotal(null, null, "NONE", "COD"));
    }

    @Test(description = "Path 1b: Gio hang rong -> nem exception")
    public void testPath1_EmptyCart_ListRong() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> orderProcessor.calculateTotal(Collections.emptyList(), null, "NONE", "COD"));
    }

    @Test(description = "Path 2: Khong coupon, khong member, total < 500k, online")
    public void testPath2_NoCoupon_NoMember_OnlineShip() {
        double actual = orderProcessor.calculateTotal(items(100000, 200000), "", "NONE", "BANKING");
        Assert.assertEquals(actual, 330000, 0.01, "Sai tong tien path 2.");
    }

    @Test(description = "Path 3: Khong coupon, khong member, total < 500k, COD")
    public void testPath3_NoCoupon_NoMember_CODShip() {
        double actual = orderProcessor.calculateTotal(items(100000, 200000), "", "NONE", "COD");
        Assert.assertEquals(actual, 320000, 0.01, "Sai tong tien path 3.");
    }

    @Test(description = "Path 4: Coupon SALE10, khong member, online")
    public void testPath4_Sale10_NoMember_Online() {
        double actual = orderProcessor.calculateTotal(items(200000, 200000), "SALE10", "NONE", "BANKING");
        Assert.assertEquals(actual, 390000, 0.01, "Sai tong tien path 4.");
    }

    @Test(description = "Path 5: Coupon SALE20, khong member, COD")
    public void testPath5_Sale20_NoMember_COD() {
        double actual = orderProcessor.calculateTotal(items(200000, 200000), "SALE20", "NONE", "COD");
        Assert.assertEquals(actual, 340000, 0.01, "Sai tong tien path 5.");
    }

    @Test(description = "Path 6: Coupon khong hop le -> nem exception")
    public void testPath6_InvalidCoupon() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> orderProcessor.calculateTotal(items(200000), "SALE50", "NONE", "COD"));
    }

    @Test(description = "Path 7: Member GOLD, khong coupon, online")
    public void testPath7_GoldMember_Online() {
        double actual = orderProcessor.calculateTotal(items(200000, 200000), "", "GOLD", "BANKING");
        Assert.assertEquals(actual, 410000, 0.01, "Sai tong tien path 7.");
    }

    @Test(description = "Path 8: Member PLATINUM, khong coupon, COD")
    public void testPath8_PlatinumMember_COD() {
        double actual = orderProcessor.calculateTotal(items(200000, 200000), "", "PLATINUM", "COD");
        Assert.assertEquals(actual, 380000, 0.01, "Sai tong tien path 8.");
    }

    @Test(description = "Path 9: Tong >= 500k nen khong tinh phi ship")
    public void testPath9_NoShipWhenTotalGe500k() {
        double actual = orderProcessor.calculateTotal(items(300000, 300000), "", "NONE", "BANKING");
        Assert.assertEquals(actual, 600000, 0.01, "Sai tong tien path 9.");
    }

    @Test(description = "MC/DC A: couponCode = null -> khong vao nhanh coupon")
    public void testMCDC_Coupon_Null() {
        double actual = orderProcessor.calculateTotal(items(100000, 100000), null, "NONE", "COD");
        Assert.assertEquals(actual, 220000, 0.01, "Sai khi coupon = null.");
    }

    @Test(description = "MC/DC B: couponCode rong -> khong vao nhanh coupon")
    public void testMCDC_Coupon_Empty() {
        double actual = orderProcessor.calculateTotal(items(100000, 100000), "", "NONE", "COD");
        Assert.assertEquals(actual, 220000, 0.01, "Sai khi coupon rong.");
    }

    @Test(description = "MC/DC D3: coupon SALE10")
    public void testMCDC_Coupon_Sale10() {
        double actual = orderProcessor.calculateTotal(items(100000, 100000), "SALE10", "NONE", "COD");
        Assert.assertEquals(actual, 200000, 0.01, "Sai khi coupon SALE10.");
    }

    @Test(description = "MC/DC D4: coupon SALE20")
    public void testMCDC_Coupon_Sale20() {
        double actual = orderProcessor.calculateTotal(items(100000, 100000), "SALE20", "NONE", "COD");
        Assert.assertEquals(actual, 180000, 0.01, "Sai khi coupon SALE20.");
    }
}