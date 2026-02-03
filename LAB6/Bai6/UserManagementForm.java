package Bai6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UserManagementForm extends JFrame {

    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JTextField txtFullName = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JComboBox<String> cbRole = new JComboBox<>(new String[]{"ADMIN", "USER"});
    private final JComboBox<String> cbStatus = new JComboBox<>(new String[]{"ACTIVE", "LOCKED"});

    private final JTextField txtSearch = new JTextField();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Username", "Full Name", "Email", "Role", "Status"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable table = new JTable(model);

    private final UserDAO dao = new UserDAO();

    private Integer selectedId = null;

    // Validate rules (để match “bao phủ các trường hợp”)
    private static final int USERNAME_MAX = 30;
    private static final int PASSWORD_MIN = 6;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public UserManagementForm() {
        super("User Management (Bai 6)");
        DBUtil.initDatabase();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(920, 560);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setContentPane(root);

        root.add(buildTopPanel(), BorderLayout.NORTH);
        root.add(buildCenterPanel(), BorderLayout.CENTER);
        root.add(buildButtonPanel(), BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedRowToForm();
        });

        refreshTable("");
    }

    private JPanel buildTopPanel() {
        JPanel top = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("User Management");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> refreshTable(txtSearch.getText()));
        JButton btnClearSearch = new JButton("Clear");
        btnClearSearch.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable("");
        });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.add(searchPanel);
        right.add(btnSearch);
        right.add(btnClearSearch);

        top.add(right, BorderLayout.EAST);
        return top;
    }

    private JPanel buildCenterPanel() {
        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        center.add(buildFormPanel());
        center.add(buildTablePanel());
        return center;
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("User Info"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Username *"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; form.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Password *"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; form.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Full Name"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; form.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Email"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; form.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Role *"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; form.add(cbRole, gbc);

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Status *"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; form.add(cbStatus, gbc);

        JLabel hint = new JLabel("<html><i>Tip: Click a row to edit. Username unique. Email unique (if provided).</i></html>");
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        form.add(hint, gbc);

        return form;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Users"));
        table.setRowHeight(24);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnLockUnlock = new JButton("Lock/Unlock");
        JButton btnClear = new JButton("Clear Form");
        JButton btnReload = new JButton("Reload");

        btnAdd.addActionListener(e -> onAdd());
        btnUpdate.addActionListener(e -> onUpdate());
        btnDelete.addActionListener(e -> onDelete());
        btnLockUnlock.addActionListener(e -> onToggleStatus());
        btnClear.addActionListener(e -> clearForm());
        btnReload.addActionListener(e -> refreshTable(txtSearch.getText()));

        btns.add(btnAdd);
        btns.add(btnUpdate);
        btns.add(btnDelete);
        btns.add(btnLockUnlock);
        btns.add(btnClear);
        btns.add(btnReload);

        return btns;
    }

    private void onAdd() {
        try {
            User u = readFormForCreate();
            if (dao.existsUsername(u.getUsername(), null)) {
                error("Username đã tồn tại (trùng).");
                txtUsername.requestFocus();
                return;
            }
            if (dao.existsEmail(u.getEmail(), null)) {
                error("Email đã tồn tại (trùng).");
                txtEmail.requestFocus();
                return;
            }

            dao.insert(u);
            info("Thêm user thành công!");
            clearForm();
            refreshTable(txtSearch.getText());

        } catch (IllegalArgumentException ex) {
            warn(ex.getMessage());
        } catch (SQLException ex) {
            error("Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void onUpdate() {
        if (selectedId == null) {
            warn("Bạn chưa chọn user để Update.");
            return;
        }
        try {
            User u = readFormForUpdate();
            if (dao.existsUsername(u.getUsername(), selectedId)) {
                error("Username đã tồn tại (trùng).");
                return;
            }
            if (dao.existsEmail(u.getEmail(), selectedId)) {
                error("Email đã tồn tại (trùng).");
                return;
            }

            dao.update(u);
            info("Cập nhật thành công!");
            refreshTable(txtSearch.getText());

        } catch (IllegalArgumentException ex) {
            warn(ex.getMessage());
        } catch (SQLException ex) {
            error("Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void onDelete() {
        if (selectedId == null) {
            warn("Bạn chưa chọn user để Delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa user này?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            dao.deleteById(selectedId);
            info("Đã xóa user.");
            clearForm();
            refreshTable(txtSearch.getText());
        } catch (SQLException ex) {
            error("Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void onToggleStatus() {
        if (selectedId == null) {
            warn("Bạn chưa chọn user để Lock/Unlock.");
            return;
        }
        String current = String.valueOf(cbStatus.getSelectedItem());
        String next = current.equals("ACTIVE") ? "LOCKED" : "ACTIVE";

        try {
            dao.updateStatus(selectedId, next);
            cbStatus.setSelectedItem(next);
            info("Đã đổi trạng thái: " + next);
            refreshTable(txtSearch.getText());
        } catch (SQLException ex) {
            error("Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void refreshTable(String keyword) {
        try {
            List<User> list = dao.search(keyword);
            model.setRowCount(0);
            for (User u : list) {
                model.addRow(new Object[]{
                        u.getId(),
                        u.getUsername(),
                        u.getFullName(),
                        u.getEmail(),
                        u.getRole(),
                        u.getStatus()
                });
            }
        } catch (SQLException ex) {
            error("Lỗi CSDL khi load/search: " + ex.getMessage());
        }
    }

    private void loadSelectedRowToForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        selectedId = (Integer) model.getValueAt(row, 0);
        txtUsername.setText(String.valueOf(model.getValueAt(row, 1)));
        txtFullName.setText(valueOrEmpty(model.getValueAt(row, 2)));
        txtEmail.setText(valueOrEmpty(model.getValueAt(row, 3)));
        cbRole.setSelectedItem(String.valueOf(model.getValueAt(row, 4)));
        cbStatus.setSelectedItem(String.valueOf(model.getValueAt(row, 5)));

        // Password không hiển thị trong table -> giữ lại trên form là rỗng để user nhập lại khi update
        txtPassword.setText("");
    }

    private String valueOrEmpty(Object v) {
        return v == null ? "" : String.valueOf(v);
    }

    private User readFormForCreate() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String role = String.valueOf(cbRole.getSelectedItem());
        String status = String.valueOf(cbStatus.getSelectedItem());

        validate(username, password, email);

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setFullName(fullName.isEmpty() ? null : fullName);
        u.setEmail(email.isEmpty() ? null : email);
        u.setRole(role);
        u.setStatus(status);
        return u;
    }

    private User readFormForUpdate() {
        User u = readFormForCreate();
        u.setId(selectedId);
        return u;
    }

    private void validate(String username, String password, String email) {
        if (username.isEmpty()) throw new IllegalArgumentException("Username (*) không được để trống.");
        if (username.length() > USERNAME_MAX) throw new IllegalArgumentException("Username tối đa " + USERNAME_MAX + " ký tự.");
        if (username.contains(" ")) throw new IllegalArgumentException("Username không được chứa khoảng trắng.");

        if (password.isEmpty()) throw new IllegalArgumentException("Password (*) không được để trống.");
        if (password.length() < PASSWORD_MIN) throw new IllegalArgumentException("Password tối thiểu " + PASSWORD_MIN + " ký tự.");

        if (!email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email không đúng định dạng.");
        }
    }

    private void clearForm() {
        selectedId = null;
        txtUsername.setText("");
        txtPassword.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        cbRole.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);
        table.clearSelection();
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
    }
    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserManagementForm().setVisible(true));
    }
}
