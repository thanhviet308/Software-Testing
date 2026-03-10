package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TienNuocBranchTest {

    @Test(description = "N1-True: soM3 <= 0")
    public void testSoM3KhongHopLe() {
        Assert.assertEquals(
                TienNuoc.tinhTienNuoc(0, "dan_cu"),
                0.0,
                "Khi soM3 <= 0 thi tien nuoc phai bang 0"
        );
    }

    @Test(description = "N2-True: Khach hang ho_ngheo")
    public void testHoNgheo() {
        Assert.assertEquals(
                TienNuoc.tinhTienNuoc(5, "ho_ngheo"),
                25000.0,
                "Ho ngheo 5m3 phai co tien nuoc = 25000"
        );
    }

    @Test(description = "N4-True: dan_cu, soM3 <= 10")
    public void testDanCuBac1() {
        Assert.assertEquals(
                TienNuoc.tinhTienNuoc(8, "dan_cu"),
                60000.0,
                "Dan cu 8m3 phai co tien nuoc = 60000"
        );
    }

    @Test(description = "N5-True: dan_cu, 10 < soM3 <= 20")
    public void testDanCuBac2() {
        Assert.assertEquals(
                TienNuoc.tinhTienNuoc(15, "dan_cu"),
                148500.0,
                "Dan cu 15m3 phai co tien nuoc = 148500"
        );
    }

    @Test(description = "N5-False: dan_cu, soM3 > 20")
    public void testDanCuBac3() {
        Assert.assertEquals(
                TienNuoc.tinhTienNuoc(25, "dan_cu"),
                285000.0,
                "Dan cu 25m3 phai co tien nuoc = 285000"
        );
    }

    @Test(description = "N3-False: Khach hang kinh_doanh")
    public void testKinhDoanh() {
        Assert.assertEquals(
                TienNuoc.tinhTienNuoc(10, "kinh_doanh"),
                220000.0,
                "Kinh doanh 10m3 phai co tien nuoc = 220000"
        );
    }
}