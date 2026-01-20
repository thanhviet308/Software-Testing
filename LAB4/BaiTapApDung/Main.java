package BaiTapApDung;

import javax.swing.SwingUtilities;
import BaiTapApDung.src.orgapp.ui.OrganizationForm;

public class Main {
    public static void main(String[] args) {
        // chạy Swing trên EDT
        SwingUtilities.invokeLater(() -> {
            new OrganizationForm().setVisible(true);
        });
    }
}
