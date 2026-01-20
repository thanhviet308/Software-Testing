package BaiTapApDung.src.orgapp.ui;

import BaiTapApDung.src.orgapp.model.Organization;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DirectorForm extends JFrame {

    private Organization org;

    public DirectorForm(Organization org) {
        this.org = org;

        setTitle("Director Management - " + org.getOrgName());
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JLabel lblInfo = new JLabel(
            "<html>Director Management for:<br/>" +
            "OrgName: " + org.getOrgName() + "<br/>" +
            "Address: " + (org.getAddress() == null ? "" : org.getAddress()) + "<br/>" +
            "</html>",
            SwingConstants.CENTER
        );

        add(lblInfo, BorderLayout.CENTER);
    }
}
