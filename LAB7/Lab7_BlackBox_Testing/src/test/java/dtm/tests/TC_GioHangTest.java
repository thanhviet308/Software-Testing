package dtm.tests;

import org.testng.annotations.Test;
import dtm.base.BaseTest;
import dtm.data.GioHangData;

public class TC_GioHangTest extends BaseTest {
    @Test(dataProvider = "gioHangData", dataProviderClass = GioHangData.class, groups = {"smoke"})
    public void testGioHang(String item, int quantity) {
        // Thực hiện test giỏ hàng
    }
}
