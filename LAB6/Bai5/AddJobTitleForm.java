package Bai5;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;

public class AddJobTitleForm extends JFrame {

    private final JTextField txtJobTitle = new JTextField();
    private final JTextArea txtDescription = new JTextArea(4, 20);
    private final JTextArea txtNote = new JTextArea(4, 20);

    private final JTextField txtFilePath = new JTextField();
    private File selectedFile = null;

    private final JobTitleDAO dao = new JobTitleDAO();

    // Giới hạn theo đề
    private static final int JOB_TITLE_MAX = 100;
    private static final int DESC_MAX = 400;
    private static final int NOTE_MAX = 400;
    private static final int FILE_MAX_KB = 1024;

    public AddJobTitleForm() {
        super("Add Job Title");
        DBUtil.initDatabase();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(620, 520);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Add Job Title");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;

        // Job Title
        gbc.gridy = 0;
        form.add(new JLabel("Job Title * (1-100 chars)"), gbc);
        gbc.gridy = 1;
        form.add(txtJobTitle, gbc);

        // Description
        gbc.gridy = 2;
        form.add(new JLabel("Description (0-400 chars)"), gbc);
        gbc.gridy = 3;
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        form.add(new JScrollPane(txtDescription), gbc);

        // Job Specification (file)
        gbc.gridy = 4;
        form.add(new JLabel("Job Specification (<=1024KB)"), gbc);

        gbc.gridy = 5;
        JPanel filePanel = new JPanel(new BorderLayout(8, 0));
        txtFilePath.setEditable(false);
        JButton btnChooseFile = new JButton("Choose File...");
        btnChooseFile.addActionListener(e -> chooseFile());
        filePanel.add(txtFilePath, BorderLayout.CENTER);
        filePanel.add(btnChooseFile, BorderLayout.EAST);
        form.add(filePanel, gbc);

        // Note
        gbc.gridy = 6;
        form.add(new JLabel("Note (0-400 chars)"), gbc);
        gbc.gridy = 7;
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        form.add(new JScrollPane(txtNote), gbc);

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

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getAbsolutePath());
        }
    }

    private void clearForm() {
        txtJobTitle.setText("");
        txtDescription.setText("");
        txtNote.setText("");
        txtFilePath.setText("");
        selectedFile = null;
    }

    private void onSave() {
        String jobTitle = txtJobTitle.getText().trim();
        String description = txtDescription.getText().trim();
        String note = txtNote.getText().trim();

        // ===== Validate text =====
        if (jobTitle.isEmpty()) {
            showWarn("Job Title (*) không được để trống.");
            txtJobTitle.requestFocus();
            return;
        }
        if (jobTitle.length() > JOB_TITLE_MAX) {
            showWarn("Job Title tối đa " + JOB_TITLE_MAX + " ký tự.");
            txtJobTitle.requestFocus();
            return;
        }
        if (description.length() > DESC_MAX) {
            showWarn("Description tối đa " + DESC_MAX + " ký tự.");
            return;
        }
        if (note.length() > NOTE_MAX) {
            showWarn("Note tối đa " + NOTE_MAX + " ký tự.");
            return;
        }

        // ===== Validate file (optional) =====
        String fileName = null;
        String filePath = null;
        Integer fileSizeKb = null;

        if (selectedFile != null) {
            long bytes = selectedFile.length();
            if (bytes <= 0) {
                JOptionPane.showMessageDialog(this, "File rỗng (0KB) không hợp lệ.", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            long kb = (bytes + 1023) / 1024; // làm tròn lên KB
            if (kb > FILE_MAX_KB) {
                JOptionPane.showMessageDialog(this, "File quá lớn. Tối đa " + FILE_MAX_KB + "KB.", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            fileName = selectedFile.getName();
            filePath = selectedFile.getAbsolutePath();
            fileSizeKb = (int) kb;
        }

        // ===== DB: check duplicate (nếu cần) =====
        try {
            // Nếu không cần unique, bạn có thể bỏ đoạn này
            if (dao.existsJobTitle(jobTitle)) {
                JOptionPane.showMessageDialog(this, "Job Title đã tồn tại (trùng dữ liệu).", "Duplicate", JOptionPane.ERROR_MESSAGE);
                txtJobTitle.requestFocus();
                return;
            }

            dao.insert(jobTitle, description, note, fileName, filePath, fileSizeKb);
            JOptionPane.showMessageDialog(this, "Lưu Job Title thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi CSDL: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddJobTitleForm().setVisible(true));
    }
}
