package BaiTapApDung.src.orgapp.ui;

import BaiTapApDung.src.orgapp.dao.OrganizationDAO;
import BaiTapApDung.src.orgapp.model.Organization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class OrganizationForm extends JFrame {

    private JTextField txtOrgName;
    private JTextField txtAddress;
    private JTextField txtPhone;
    private JTextField txtEmail;

    public JButton btnSave;
    public JButton btnBack;
    public JButton btnDirector;

    private OrganizationDAO orgDAO;
    private Organization currentOrg;

    public OrganizationForm() {
        setTitle("Quản lý Tổ chức");
        setSize(450, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        orgDAO = new OrganizationDAO();

        initComponents();
    }

    private void initComponents() {
        JLabel lblOrgName = new JLabel("Tên tổ chức");
        JLabel lblAddress = new JLabel("Địa chỉ");
        JLabel lblPhone = new JLabel("Điện thoại");
        JLabel lblEmail = new JLabel("Email");

        txtOrgName = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        btnSave = new JButton("Lưu");
        btnBack = new JButton("Quay lại");
        btnDirector = new JButton("Giám đốc");
        btnDirector.setEnabled(false);  // theo đề bài

        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlInput.add(lblOrgName);
        pnlInput.add(txtOrgName);
        pnlInput.add(lblAddress);
        pnlInput.add(txtAddress);
        pnlInput.add(lblPhone);
        pnlInput.add(txtPhone);
        pnlInput.add(lblEmail);
        pnlInput.add(txtEmail);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButton.add(btnDirector);
        pnlButton.add(btnSave);
        pnlButton.add(btnBack);

        add(pnlInput, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

        btnSave.addActionListener(this::onSaveClicked);
        btnBack.addActionListener(e -> onBackClicked());
        btnDirector.addActionListener(e -> onDirectorClicked());
    }

    private boolean validateInputs() {
        String orgName = txtOrgName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        // OrgName: required + 3-255 chars
        if (orgName.isEmpty()) {
            showError("Organization Name is required");
            return false;
        }
        if (orgName.length() < 3 || orgName.length() > 255) {
            showError("Organization Name must be 3-255 characters");
            return false;
        }

        // Phone: optional, digits only, 9-12
        if (!phone.isEmpty()) {
            if (!phone.matches("\\d+")) {
                showError("Phone must contain digits only");
                return false;
            }
            if (phone.length() < 9 || phone.length() > 12) {
                showError("Phone must be 9-12 digits");
                return false;
            }
        }

        // Email: optional, check format
        if (!email.isEmpty()) {
            String regex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
            if (!Pattern.compile(regex).matcher(email).matches()) {
                showError("Invalid email format");
                return false;
            }
        }

        return true;
    }

    private void onSaveClicked(ActionEvent e) {
        if (!validateInputs()) return;

        String orgName = txtOrgName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        try {
            if (orgDAO.existsOrgName(orgName)) {
                showError("Organization Name already exists");
                return;
            }

            Organization org = new Organization(orgName, address, phone, email);
            int newId = orgDAO.insertOrganization(org);
            currentOrg = org;

            JOptionPane.showMessageDialog(this,
                    "Save successfully! (OrgID = " + newId + ")",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            btnDirector.setEnabled(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
            showError("Database error: " + ex.getMessage());
        }
    }

    private void onDirectorClicked() {
        if (currentOrg == null) {
            showError("Please save an Organization first");
            return;
        }

        DirectorForm df = new DirectorForm(currentOrg);
        df.setVisible(true);
    }

    private void onBackClicked() {
        this.dispose();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
