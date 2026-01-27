package Bai4;

import javax.swing.*;
import java.awt.*;

public class PatientPaymentForm extends JFrame {

    private JTextField txtAge;
    private JTextField txtPayment;

    private JRadioButton rMale;
    private JRadioButton rFemale;
    private JRadioButton rChild;

    private JButton btnCalc;
    private JButton btnReset;

    public PatientPaymentForm() {
        setTitle("Calculate the Payment for the Patient");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 240);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        bindEvents();
    }

    private void initUI() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Calculate the Payment for the Patient");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        root.add(lblTitle, g);

        // Age
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1;
        root.add(new JLabel("Age:"), g);

        txtAge = new JTextField();
        g.gridx = 1; g.gridy = 1; g.gridwidth = 3;
        root.add(txtAge, g);

        // Type
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 2;
        root.add(new JLabel("Type:"), g);

        rMale = new JRadioButton("Male");
        rFemale = new JRadioButton("Female");
        rChild = new JRadioButton("Child");

        ButtonGroup group = new ButtonGroup();
        group.add(rMale);
        group.add(rFemale);
        group.add(rChild);

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(rMale);
        typePanel.add(rFemale);
        typePanel.add(rChild);

        g.gridx = 1; g.gridy = 2; g.gridwidth = 3;
        root.add(typePanel, g);

        // Payment
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 3;
        root.add(new JLabel("Payment (euro):"), g);

        txtPayment = new JTextField();
        txtPayment.setEditable(false);
        g.gridx = 1; g.gridy = 3; g.gridwidth = 3;
        root.add(txtPayment, g);

        // Buttons
        btnCalc = new JButton("Calculate");
        btnReset = new JButton("Reset");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnCalc);
        btnPanel.add(btnReset);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 4;
        root.add(btnPanel, g);

        setContentPane(root);
    }

    private void bindEvents() {
        btnCalc.addActionListener(e -> onCalculate());
        btnReset.addActionListener(e -> onReset());
    }

    private void onCalculate() {
        try {
            if (txtAge.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Age!", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(txtAge.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be an integer number!", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PaymentCalculator.Type type = null;
            if (rMale.isSelected()) type = PaymentCalculator.Type.MALE;
            else if (rFemale.isSelected()) type = PaymentCalculator.Type.FEMALE;
            else if (rChild.isSelected()) type = PaymentCalculator.Type.CHILD;

            if (type == null) {
                JOptionPane.showMessageDialog(this, "Please choose Type!", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int payment = PaymentCalculator.calculatePayment(age, type);
            txtPayment.setText(String.valueOf(payment));

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            txtPayment.setText("");
        }
    }

    private void onReset() {
        txtAge.setText("");
        txtPayment.setText("");
        rMale.setSelected(false);
        rFemale.setSelected(false);
        rChild.setSelected(false);
        txtAge.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientPaymentForm().setVisible(true));
    }
}
