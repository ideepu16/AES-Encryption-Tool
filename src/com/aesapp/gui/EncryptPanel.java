package com.aesapp.gui;

import com.aesapp.service.EncryptionService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class EncryptPanel extends JPanel {
    private JTextField plaintextField;
    private JPasswordField keyField;
    private JButton encryptButton;
    private JTextArea outputArea;
    private JLabel statusLabel;
    private EncryptionService service;

    public EncryptPanel(EncryptionService service) {
        this.service = service;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setOpaque(false);

        // --- Top Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Plain Text:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        plaintextField = new JTextField();
        formPanel.add(plaintextField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Passphrase (min 8 chars):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        keyField = new JPasswordField();
        formPanel.add(keyField, gbc);

        // Action Buttons (Clear & Encrypt) side-by-side
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setOpaque(false);

        JButton clearButton = new JButton("Clear");
        styleInteractiveButton(clearButton, new Color(80, 80, 80), new Color(100, 100, 100));
        clearButton.addActionListener(e -> clearFields());

        encryptButton = new JButton("Encrypt Data");
        styleInteractiveButton(encryptButton, new Color(14, 99, 156), new Color(26, 115, 232));
        encryptButton.addActionListener(e -> onEncryptClicked());

        actionPanel.add(clearButton);
        actionPanel.add(encryptButton);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(actionPanel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // --- Center Result Area ---
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60,60,60)), "Encrypted Result (Base64)"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // --- Bottom Panel (Status & Copy Button) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        statusLabel = new JLabel("");
        statusLabel.setVisible(false);
        bottomPanel.add(statusLabel, BorderLayout.WEST);

        JButton copyButton = new JButton("Copy Result");
        styleInteractiveButton(copyButton, new Color(50, 50, 50), new Color(70, 70, 70));
        copyButton.addActionListener(e -> copyToClipboard());
        bottomPanel.add(copyButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleInteractiveButton(JButton btn, Color defaultColor, Color hoverColor) {
        btn.setBackground(defaultColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hoverColor); }
            public void mouseExited(MouseEvent e) { btn.setBackground(defaultColor); }
        });
    }

    private void onEncryptClicked() {
        String text = plaintextField.getText();
        String pass = new String(keyField.getPassword());
        statusLabel.setVisible(false);
        try {
            outputArea.setText(service.encrypt(text, pass));
            showSuccess("Encryption successful!");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
            outputArea.setText("");
        }
    }

    private void copyToClipboard() {
        String text = outputArea.getText();
        if (!text.isEmpty()) {
            StringSelection selection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            showSuccess("Copied to clipboard!");
        }
    }

    private void clearFields() {
        plaintextField.setText("");
        keyField.setText("");
        outputArea.setText("");
        statusLabel.setVisible(false);
    }

    private void showError(String message) {
        statusLabel.setForeground(new Color(244, 67, 54)); // Soft Red
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }

    private void showSuccess(String message) {
        statusLabel.setForeground(new Color(76, 175, 80)); // Soft Green
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }
}