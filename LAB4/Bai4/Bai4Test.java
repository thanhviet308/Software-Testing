package Bai4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Bai4Test {

    // Test diện tích: HCN 10 x 5 → S = 50
    @Test
    public void testDienTich_10x5() {
        Bai4.Diem tl = new Bai4.Diem(0, 5);   // (x=0, y=5)
        Bai4.Diem br = new Bai4.Diem(10, 0);  // (x=10, y=0)
        Bai4.HinhChuNhat hcn = new Bai4.HinhChuNhat(tl, br);

        assertEquals(50.0, hcn.dienTich(), 1e-9);
    }

    // Test diện tích: HCN 3 x 4 → S = 12
    @Test
    public void testDienTich_3x4() {
        Bai4.Diem tl = new Bai4.Diem(1, 6);   // width = 3, height = 4
        Bai4.Diem br = new Bai4.Diem(4, 2);
        Bai4.HinhChuNhat hcn = new Bai4.HinhChuNhat(tl, br);

        assertEquals(12.0, hcn.dienTich(), 1e-9);
    }

    // Hai hình chữ nhật giao nhau
    @Test
    public void testGiaoNhau_True() {
        // HCN 1: (0, 10) - (10, 0)
        Bai4.HinhChuNhat h1 = new Bai4.HinhChuNhat(
                new Bai4.Diem(0, 10),
                new Bai4.Diem(10, 0));

        // HCN 2: (5, 8) - (12, 2) → chồng lên 1 phần
        Bai4.HinhChuNhat h2 = new Bai4.HinhChuNhat(
                new Bai4.Diem(5, 8),
                new Bai4.Diem(12, 2));

        assertTrue(h1.giaoNhau(h2));
    }

    // Hai hình tách rời, không giao nhau
    @Test
    public void testGiaoNhau_False() {
        // HCN 1: (0, 10) - (10, 0)
        Bai4.HinhChuNhat h1 = new Bai4.HinhChuNhat(
                new Bai4.Diem(0, 10),
                new Bai4.Diem(10, 0));

        // HCN 2 nằm hẳn bên phải: (11, 9) - (20, 1)
        Bai4.HinhChuNhat h2 = new Bai4.HinhChuNhat(
                new Bai4.Diem(11, 9),
                new Bai4.Diem(20, 1));

        assertFalse(h1.giaoNhau(h2));
    }

    // Hình chữ nhật không hợp lệ: điểm trái/phải sai → ném exception
    @Test
    public void testConstructor_InvalidRectangle() {
        // topLeft.x >= bottomRight.x → sai
        Bai4.Diem tl = new Bai4.Diem(5, 10);
        Bai4.Diem br = new Bai4.Diem(3, 0);

        try {
            new Bai4.HinhChuNhat(tl, br);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}

