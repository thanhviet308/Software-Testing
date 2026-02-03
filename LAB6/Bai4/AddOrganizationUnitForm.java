package Bai4;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddOrganizationUnitForm extends JFrame {

    private final JTextField txtUnitId = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextArea txtDescription = new JTextArea(5, 20);

    private final OrganizationUnitDAO dao = new OrganizationUnitDAO();

    public AddOrganizationUnitForm() {
        super("Add Organization Unit");
        DBUtil.initDatabase();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Add Organization Unit");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        root.add(title, BorderLayout.NORTH);

        // Form panel
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Unit Id
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Unit Id"), gbc);

        gbc.gridy = 1;
        form.add(txtUnitId, gbc);

        // Name*
        gbc.gridy = 2;
        form.add(new JLabel("Name *"), gbc);

        gbc.gridy = 3;
        form.add(txtName, gbc);

        // Description
        gbc.gridy = 4;
        form.add(new JLabel("Description"), gbc);

        gbc.gridy = 5;
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(txtDescription);
        form.add(sp, gbc);

        root.add(form, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnCancel = new JButton("Cancel");
        JButton btnSave = new JButton("Save");

        btnCancel.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> onSave());

        buttons.add(btnCancel);
        buttons.add(btnSave);

        root.add(buttons, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void clearForm() {
        txtUnitId.setText("");
        txtName.setText("");
        txtDescription.setText("");
    }

    private void onSave() {
        String unitId = txtUnitId.getText().trim();
        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();

        // Validate
        if (unitId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Unit Id không được để trống.", "Validation", JOptionPane.WARNING_MESSAGE);
            txtUnitId.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name (*) không được để trống.", "Validation", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return;
        }

        // DB check + insert
        try {
            if (dao.existsUnitId(unitId)) {
                JOptionPane.showMessageDialog(this, "Unit Id đã tồn tại (trùng dữ liệu).", "Duplicate", JOptionPane.ERROR_MESSAGE);
                txtUnitId.requestFocus();
                return;
            }

            dao.insert(unitId, name, description);
            JOptionPane.showMessageDialog(this, "Lưu thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi CSDL: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddOrganizationUnitForm().setVisible(true));
    }
}
