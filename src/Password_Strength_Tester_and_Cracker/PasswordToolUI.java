package Password_Strength_Tester_and_Cracker;

import javax.swing.*;
import java.awt.*;

public class PasswordToolUI extends JFrame {

    private final JTextField inputField = new JTextField(24);
    private final JTextArea outputArea = new JTextArea(10, 40);
    private final JButton checkBtn = new JButton("Check Strength");
    private final JButton crackBtn = new JButton("Crack Password");
    private final JButton genBtn = new JButton("Generate Password");
    private final JButton exitBtn = new JButton("Exit");

    public PasswordToolUI() {
        super("Password Strength Tester & Cracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setLocationRelativeTo(null);

        // Top: input
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        top.add(new JLabel("Password / Target:"));
        top.add(inputField);
        add(top, BorderLayout.NORTH);

        // Center: output
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Bottom: buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        bottom.add(checkBtn);
        bottom.add(crackBtn);
        bottom.add(genBtn);
        bottom.add(exitBtn);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        checkBtn.addActionListener(e -> doCheck());
        crackBtn.addActionListener(e -> doCrack());
        genBtn.addActionListener(e -> doGenerate());
        exitBtn.addActionListener(e -> dispose());

        pack();
        setResizable(false);
    }

    private void doCheck() {
        String pwd = inputField.getText();
        if (pwd == null || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a password to check.");
            return;
        }
        // static call to your checker
        String strength = PasswordStrengthChecker.checkStrength(pwd);
        outputArea.setText("Password: " + pwd + "\nStrength: " + strength + "\n");
    }

    private void doCrack() {
        String target = inputField.getText();
        if (target == null || target.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a password/target to crack.");
            return;
        }

        // disable buttons while cracking
        setButtonsEnabled(false);
        outputArea.setText("Starting brute-force crack (max length 5). This may take a while...\n");

        // run heavy work on background thread
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                try {
                    // call the static method you have in PasswordCracker
                    return PasswordCracker.crackPassword(target);
                } catch (Throwable t) {
                    return "[error] " + t.getMessage();
                }
            }

            @Override
            protected void done() {
                try {
                    String res = get();
                    if (res == null) {
                        outputArea.append("Result: Could not crack the password (not found within limit).\n");
                    } else {
                        outputArea.append("Result: " + res + "\n");
                    }
                } catch (Exception ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                } finally {
                    setButtonsEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void doGenerate() {
        String lenStr = JOptionPane.showInputDialog(this, "Enter desired password length (min 6):", "12");
        if (lenStr == null) return;
        try {
            int length = Integer.parseInt(lenStr.trim());
            String generated = PasswordGenerator.generatePassword(length);
            inputField.setText(generated); // put generated password in the input box for easy copy
            outputArea.setText("Generated password: " + generated + "\n");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage());
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        checkBtn.setEnabled(enabled);
        crackBtn.setEnabled(enabled);
        genBtn.setEnabled(enabled);
        exitBtn.setEnabled(enabled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordToolUI ui = new PasswordToolUI();
            ui.setVisible(true);
        });
    }
}
