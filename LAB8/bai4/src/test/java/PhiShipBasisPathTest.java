import org.testng.Assert;
import org.testng.annotations.Test;

public class PhiShipBasisPathTest {

    @Test(description = "Path 1: Trong luong khong hop le")
    public void testPath1_InvalidWeight() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> PhiShip.tinhPhiShip(-1, "noi_thanh", false));
    }

    @Test(description = "Path 2: Noi thanh <=5kg khong member")
    public void testPath2() {
        Assert.assertEquals(PhiShip.tinhPhiShip(3, "noi_thanh", false), 15000, 0.01);
    }

    @Test(description = "Path 3: Noi thanh >5kg")
    public void testPath3() {
        Assert.assertEquals(PhiShip.tinhPhiShip(7, "noi_thanh", false), 19000, 0.01);
    }

    @Test(description = "Path 4: Ngoai thanh <=3kg")
    public void testPath4() {
        Assert.assertEquals(PhiShip.tinhPhiShip(2, "ngoai_thanh", false), 25000, 0.01);
    }

    @Test(description = "Path 5: Ngoai thanh >3kg")
    public void testPath5() {
        Assert.assertEquals(PhiShip.tinhPhiShip(5, "ngoai_thanh", false), 31000, 0.01);
    }

    @Test(description = "Path 6: Tinh khac <=2kg")
    public void testPath6() {
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "tinh_khac", false), 50000, 0.01);
    }

    @Test(description = "Path 7: Tinh khac >2kg")
    public void testPath7() {
        Assert.assertEquals(PhiShip.tinhPhiShip(4, "tinh_khac", false), 60000, 0.01);
    }

    @Test(description = "Path 8: Member giam 10%")
    public void testPath8() {
        Assert.assertEquals(PhiShip.tinhPhiShip(3, "noi_thanh", true), 13500, 0.01);
    }
}