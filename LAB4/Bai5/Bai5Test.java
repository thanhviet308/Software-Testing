package Bai5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Bai5Test {

    // Học viên đủ điều kiện: TB >= 8, không môn nào dưới 5
    @Test
    public void testDuocHocBong_DuDieuKien() {
        Bai5.HocVien hv = new Bai5.HocVien("HV01", "A", "HN",
                8.0, 8.5, 9.0); // TB = 8.5
        assertTrue(hv.duocHocBong());
    }

    // TB < 8 → không được học bổng
    @Test
    public void testDuocHocBong_TBThapHon8() {
        Bai5.HocVien hv = new Bai5.HocVien("HV02", "B", "HCM",
                7.0, 8.0, 7.5); // TB ~ 7.5
        assertFalse(hv.duocHocBong());
    }

    // Có môn < 5 → không được học bổng dù TB cao
    @Test
    public void testDuocHocBong_CoMonDuoi5() {
        Bai5.HocVien hv = new Bai5.HocVien("HV03", "C", "ĐN",
                9.0, 4.5, 9.0); // môn 2 < 5
        assertFalse(hv.duocHocBong());
    }

    // Trường hợp biên: TB đúng bằng 8, các môn >=5 → được học bổng
    @Test
    public void testDuocHocBong_BienBang8() {
        Bai5.HocVien hv = new Bai5.HocVien("HV04", "D", "QN",
                8.0, 8.0, 8.0);
        assertEquals(8.0, hv.getDiemTrungBinh(), 1e-9);
        assertTrue(hv.duocHocBong());
    }

    // Kiểm thử hàm lọc danh sách học viên nhận học bổng
    @Test
    public void testTimHocVienNhanHocBong() {
        Bai5.HocVien hv1 = new Bai5.HocVien("HV01", "A", "HN",
                8.0, 8.5, 9.0);   // đủ điều kiện
        Bai5.HocVien hv2 = new Bai5.HocVien("HV02", "B", "HCM",
                7.0, 8.0, 7.5);   // TB < 8
        Bai5.HocVien hv3 = new Bai5.HocVien("HV03", "C", "ĐN",
                9.0, 4.5, 9.0);   // có môn < 5
        Bai5.HocVien hv4 = new Bai5.HocVien("HV04", "D", "QN",
                8.0, 8.0, 8.0);   // đủ điều kiện

        List<Bai5.HocVien> ds = Arrays.asList(hv1, hv2, hv3, hv4);

        List<Bai5.HocVien> ketQua = Bai5.timHocVienNhanHocBong(ds);

        // Chỉ hv1 và hv4 được học bổng
        assertEquals(2, ketQua.size());
        assertEquals("HV01", ketQua.get(0).getMaHV());
        assertEquals("HV04", ketQua.get(1).getMaHV());
    }
}

