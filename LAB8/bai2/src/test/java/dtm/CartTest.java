package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest {

    @Test(groups = {"smoke", "regression"}, description = "Them san pham vao gio hang")
    public void testAddToCart() {
        int cartCount = 1;
        Assert.assertEquals(cartCount, 1,
                "Them san pham vao gio hang that bai!");
    }

    @Test(groups = {"regression"}, description = "Xoa san pham khoi gio hang")
    public void testRemoveFromCart() {
        int cartCount = 0;
        Assert.assertEquals(cartCount, 0,
                "Xoa san pham khoi gio hang that bai!");
    }
}