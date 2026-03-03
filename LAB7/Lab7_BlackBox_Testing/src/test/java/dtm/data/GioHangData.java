package dtm.data;

import org.testng.annotations.DataProvider;

public class GioHangData {
    @DataProvider(name = "gioHangData")
    public static Object[][] getGioHangData() {
        return new Object[][] {
            {"item1", 2},
            {"item2", 1}
        };
    }
}
