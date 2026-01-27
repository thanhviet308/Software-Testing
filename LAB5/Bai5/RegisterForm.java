package Bai5;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.toedter.calendar.JDateChooser;   // ✅ calendar popup

public class RegisterForm extends JFrame {

    private final JTextField txtMa = new JTextField();
    private final JTextField txtHoTen = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtSdt = new JTextField();
    private final JTextArea txtDiaChi = new JTextArea(3, 20);

    private final JPasswordField txtMk = new JPasswordField();
    private final JPasswordField txtXnMk = new JPasswordField();

    // ✅ Date chooser có lịch popup
    private final JDateChooser dateNgaySinh = new JDateChooser();

    private final JRadioButton rNam = new JRadioButton("Nam");
    private final JRadioButton rNu = new JRadioButton("Nữ");
    private final JRadioButton rKhac = new JRadioButton("Khác");

    private final JCheckBox chkDieuKhoan = new JCheckBox("Tôi đồng ý với các điều khoản dịch vụ");

    private final JButton btnDangKy = new JButton("Đăng ký");
    private final JButton btnNhapLai = new JButton("Nhập lại");

    private final CustomerDAO dao = new CustomerDAO();

    public RegisterForm() {
        setTitle("Đăng Ký Tài Khoản Khách Hàng");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 520);
        setLocationRelativeTo(null);

        applyLookAndFeel();
        setContentPane(buildUI());
        bindEvents();
    }

    private void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    private JPanel buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel title = new JLabel("ĐĂNG KÝ TÀI KHOẢN KHÁCH HÀNG", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(16, 16, 16, 16));
        form.setBackground(Color.white);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        int row = 0;
        addRow(form, g, row++, "Mã Khách Hàng", txtMa, "6-10 ký tự chỉ chữ và số");
        addRow(form, g, row++, "Họ và Tên", txtHoTen, "Nhập họ tên đầy đủ");
        addRow(form, g, row++, "Email", txtEmail, "VD: nguyenvana@email.com");
        addRow(form, g, row++, "Số điện thoại", txtSdt, "Bắt đầu bằng số 0, 10-12 số");

        // Địa chỉ (textarea)
        g.gridx = 0; g.gridy = row; g.weightx = 0; g.gridwidth = 1;
        form.add(new JLabel("Địa chỉ"), g);

        JScrollPane sp = new JScrollPane(txtDiaChi);
        txtDiaChi.setLineWrap(true);
        txtDiaChi.setWrapStyleWord(true);

        g.gridx = 1; g.gridy = row; g.weightx = 1; g.gridwidth = 2;
        form.add(sp, g);
        row++;

        addRow(form, g, row++, "Mật khẩu", txtMk, "Ít nhất 8 ký tự");
        addRow(form, g, row++, "Xác nhận Mật khẩu", txtXnMk, "Nhập lại mật khẩu");

        // ✅ cấu hình định dạng dd/MM/yyyy + nút lịch
        dateNgaySinh.setDateFormatString("dd/MM/yyyy");
        dateNgaySinh.setPreferredSize(new Dimension(200, 28));

        addRow(form, g, row++, "Ngày sinh", dateNgaySinh, "định dạng dd/MM/yyyy");

        // Giới tính
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        genderPanel.setOpaque(false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rNam); bg.add(rNu); bg.add(rKhac);
        genderPanel.add(rNam);
        genderPanel.add(rNu);
        genderPanel.add(rKhac);

        g.gridx = 0; g.gridy = row; g.weightx = 0; g.gridwidth = 1;
        form.add(new JLabel("Giới tính"), g);
        g.gridx = 1; g.gridy = row; g.weightx = 1; g.gridwidth = 2;
        form.add(genderPanel, g);
        row++;

        // Điều khoản
        g.gridx = 1; g.gridy = row; g.gridwidth = 2;
        form.add(chkDieuKhoan, g);
        row++;

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        btnPanel.setOpaque(false);
        btnDangKy.setPreferredSize(new Dimension(120, 36));
        btnNhapLai.setPreferredSize(new Dimension(120, 36));
        btnPanel.add(btnDangKy);
        btnPanel.add(btnNhapLai);

        g.gridx = 0; g.gridy = row; g.gridwidth = 3;
        form.add(btnPanel, g);

        root.add(new JPanel(new BorderLayout()) {{
            setBorder(BorderFactory.createLineBorder(new Color(0x2B6CB0), 2, true));
            add(form, BorderLayout.CENTER);
        }}, BorderLayout.CENTER);

        return root;
    }

    private void addRow(JPanel form, GridBagConstraints g, int row, String label, JComponent field, String hint) {
        g.gridx = 0; g.gridy = row; g.weightx = 0; g.gridwidth = 1;
        form.add(new JLabel(label), g);

        g.gridx = 1; g.gridy = row; g.weightx = 1; g.gridwidth = 1;
        form.add(field, g);

        JLabel lblHint = new JLabel(hint);
        lblHint.setForeground(new Color(0x666666));
        lblHint.setFont(lblHint.getFont().deriveFont(11f));

        g.gridx = 2; g.gridy = row; g.weightx = 0.8; g.gridwidth = 1;
        form.add(lblHint, g);
    }

    private void bindEvents() {
        btnDangKy.addActionListener(e -> onRegister());
        btnNhapLai.addActionListener(e -> onReset());
    }

    private void onRegister() {
        try {
            String ma = txtMa.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String email = txtEmail.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String mk = new String(txtMk.getPassword());
            String xn = new String(txtXnMk.getPassword());

            // ✅ ngày sinh: có thể null (không bắt buộc)
            LocalDate dob = null;
            Date picked = dateNgaySinh.getDate();
            if (picked != null) {
                dob = picked.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            String err = Validator.validateAll(ma, hoTen, email, sdt, diaChi, mk, xn, dob, chkDieuKhoan.isSelected());
            if (err != null) {
                JOptionPane.showMessageDialog(this, err, "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (dao.existsMaKh(ma)) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dao.existsEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer c = new Customer();
            c.maKh = ma;
            c.hoTen = hoTen;
            c.email = email;
            c.sdt = sdt;
            c.diaChi = diaChi;
            c.matKhauHash = PasswordUtil.sha256(mk);
            c.ngaySinh = dob;

            if (rNam.isSelected()) c.gioiTinh = "Nam";
            else if (rNu.isSelected()) c.gioiTinh = "Nữ";
            else if (rKhac.isSelected()) c.gioiTinh = "Khác";
            else c.gioiTinh = null;

            dao.insert(c);

            JOptionPane.showMessageDialog(this, "Đăng ký tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            onReset();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onReset() {
        txtMa.setText("");
        txtHoTen.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        txtMk.setText("");
        txtXnMk.setText("");

        // ✅ reset ngày sinh về trống (đúng “không bắt buộc”)
        dateNgaySinh.setDate(null);

        chkDieuKhoan.setSelected(false);
        rNam.setSelected(false);
        rNu.setSelected(false);
        rKhac.setSelected(false);

        txtMa.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterForm().setVisible(true));
    }
}
