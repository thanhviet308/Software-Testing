import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai1 extends JFrame {

    private JTextField txtHoTen;
    private JTextField txtTenDangNhap;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JPasswordField txtMatKhau;
    private JPasswordField txtXacNhanMatKhau;
    private JTextField txtNgaySinh;
    private JTextField txtMaGioiThieu;

    private JRadioButton rdNam;
    private JRadioButton rdNu;
    private JRadioButton rdKhongTietLo;

    private JCheckBox chkDongY;

    private JButton btnDangKy;
    private JButton btnDangNhap;

    public Bai1() {
        setTitle("Form Đăng Ký Tài Khoản - ShopVN");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Họ tên
        panel.add(new JLabel("Họ và tên (*)"));
        txtHoTen = new JTextField();
        panel.add(txtHoTen);

        // Tên đăng nhập
        panel.add(new JLabel("Tên đăng nhập (*)"));
        txtTenDangNhap = new JTextField();
        panel.add(txtTenDangNhap);

        // Email
        panel.add(new JLabel("Email (*)"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        // Số điện thoại
        panel.add(new JLabel("Số điện thoại (*)"));
        txtSoDienThoai = new JTextField();
        panel.add(txtSoDienThoai);

        // Mật khẩu
        panel.add(new JLabel("Mật khẩu (*)"));
        txtMatKhau = new JPasswordField();
        panel.add(txtMatKhau);

        // Xác nhận mật khẩu
        panel.add(new JLabel("Xác nhận mật khẩu (*)"));
        txtXacNhanMatKhau = new JPasswordField();
        panel.add(txtXacNhanMatKhau);

        // Ngày sinh
        panel.add(new JLabel("Ngày sinh (dd/mm/yyyy)"));
        txtNgaySinh = new JTextField();
        panel.add(txtNgaySinh);

        // Giới tính
        panel.add(new JLabel("Giới tính"));
        rdNam = new JRadioButton("Nam");
        rdNu = new JRadioButton("Nữ");
        rdKhongTietLo = new JRadioButton("Không muốn tiết lộ");

        ButtonGroup group = new ButtonGroup();
        group.add(rdNam);
        group.add(rdNu);
        group.add(rdKhongTietLo);

        panel.add(rdNam);
        panel.add(rdNu);
        panel.add(rdKhongTietLo);

        // Mã giới thiệu
        panel.add(new JLabel("Mã giới thiệu"));
        txtMaGioiThieu = new JTextField();
        panel.add(txtMaGioiThieu);

        // Checkbox điều khoản
        chkDongY = new JCheckBox("Tôi đồng ý Điều khoản (*)");
        panel.add(chkDongY);

        // Buttons
        btnDangKy = new JButton("Đăng ký");
        btnDangNhap = new JButton("Đăng nhập");

        panel.add(btnDangKy);
        panel.add(btnDangNhap);

        add(panel);

        // Action
        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyDangKy();
            }
        });
    }

    private void xuLyDangKy() {
        String hoTen = txtHoTen.getText();
        String tenDangNhap = txtTenDangNhap.getText();
        String email = txtEmail.getText();
        String sdt = txtSoDienThoai.getText();
        String matKhau = new String(txtMatKhau.getPassword());
        String xacNhan = new String(txtXacNhanMatKhau.getPassword());

        if (hoTen.isEmpty() || tenDangNhap.isEmpty() || email.isEmpty()
                || sdt.isEmpty() || matKhau.isEmpty() || xacNhan.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ các trường bắt buộc!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matKhau.equals(xacNhan)) {
            JOptionPane.showMessageDialog(this,
                    "Mật khẩu xác nhận không khớp!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!chkDongY.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Bạn phải đồng ý Điều khoản!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Đăng ký thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai1().setVisible(true);
        });
    }
}