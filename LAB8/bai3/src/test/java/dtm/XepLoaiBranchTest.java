package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class XepLoaiBranchTest {

    @Test(description = "N1-True: Diem khong hop le")
    public void testInvalidScore() {
        Assert.assertEquals(
                XepLoai.xepLoai(-1, false),
                "Diem khong hop le",
                "Diem -1 phai duoc xep loai 'Diem khong hop le'"
        );
    }

    @Test(description = "N2-True: Xep loai Gioi")
    public void testGioi() {
        Assert.assertEquals(
                XepLoai.xepLoai(9.0, false),
                "Gioi",
                "Diem 9.0 phai duoc xep loai 'Gioi'"
        );
    }

    @Test(description = "N3-True: Xep loai Kha")
    public void testKha() {
        Assert.assertEquals(
                XepLoai.xepLoai(7.5, false),
                "Kha",
                "Diem 7.5 phai duoc xep loai 'Kha'"
        );
    }

    @Test(description = "N4-True: Xep loai Trung Binh")
    public void testTrungBinh() {
        Assert.assertEquals(
                XepLoai.xepLoai(6.0, false),
                "Trung Binh",
                "Diem 6.0 phai duoc xep loai 'Trung Binh'"
        );
    }

    @Test(description = "N5-True: Thi lai")
    public void testThiLai() {
        Assert.assertEquals(
                XepLoai.xepLoai(4.0, true),
                "Thi lai",
                "Diem 4.0 va co thi lai phai tra ve 'Thi lai'"
        );
    }

    @Test(description = "N5-False: Yeu - Hoc lai")
    public void testYeuHocLai() {
        Assert.assertEquals(
                XepLoai.xepLoai(4.0, false),
                "Yeu - Hoc lai",
                "Diem 4.0 va khong thi lai phai tra ve 'Yeu - Hoc lai'"
        );
    }
}