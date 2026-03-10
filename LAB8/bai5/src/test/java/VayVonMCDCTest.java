import org.testng.Assert;
import org.testng.annotations.Test;

public class VayVonMCDCTest {

    @Test(description = "MC/DC cho C: co tai san bao lanh = true, du dieu kien vay")
    public void testMCDC_CoTaiSanDocLap_True() {
        boolean actual = VayVon.duDieuKienVay(25, 12000000, true, 650);
        Assert.assertTrue(actual,
                "Sai: khi tuoi va thu nhap dat, co tai san bao lanh = true thi phai du dieu kien vay.");
    }

    @Test(description = "MC/DC co so: khong co tai san, diem tin dung tot van du dieu kien vay")
    public void testMCDC_CoSo_DiemTinDungTot() {
        boolean actual = VayVon.duDieuKienVay(25, 12000000, false, 750);
        Assert.assertTrue(actual,
                "Sai: khi tuoi va thu nhap dat, diem tin dung >= 700 thi phai du dieu kien vay.");
    }

    @Test(description = "MC/DC cho D: diem tin dung < 700 lam ket qua chuyen sang false")
    public void testMCDC_DiemTinDungDocLap_ThapHon700() {
        boolean actual = VayVon.duDieuKienVay(25, 12000000, false, 650);
        Assert.assertFalse(actual,
                "Sai: khi khong co tai san bao lanh va diem tin dung < 700 thi khong du dieu kien vay.");
    }

    @Test(description = "MC/DC cho B: thu nhap < 10 trieu lam ket qua chuyen sang false")
    public void testMCDC_ThuNhapDocLap_ThapHon10Trieu() {
        boolean actual = VayVon.duDieuKienVay(25, 9000000, false, 750);
        Assert.assertFalse(actual,
                "Sai: khi thu nhap < 10 trieu thi khong du dieu kien vay.");
    }

    @Test(description = "MC/DC cho A: tuoi < 22 lam ket qua chuyen sang false")
    public void testMCDC_TuoiDocLap_ThapHon22() {
        boolean actual = VayVon.duDieuKienVay(20, 12000000, false, 750);
        Assert.assertFalse(actual,
                "Sai: khi tuoi < 22 thi khong du dieu kien vay.");
    }
}