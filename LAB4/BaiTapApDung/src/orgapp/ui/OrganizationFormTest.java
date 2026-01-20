package BaiTapApDung.src.orgapp.ui;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrganizationFormTest {
    @Test
    public void testFormInit() {
        OrganizationForm form = new OrganizationForm();
        // Kiểm tra tiêu đề
        assertEquals("Quản lý Tổ chức", form.getTitle());
        // Kiểm tra các nút
        assertEquals("Lưu", form.btnSave.getText());
        assertEquals("Quay lại", form.btnBack.getText());
        assertEquals("Giám đốc", form.btnDirector.getText());
        // Kiểm tra trạng thái nút Giám đốc
        assertFalse(form.btnDirector.isEnabled());
    }
}
