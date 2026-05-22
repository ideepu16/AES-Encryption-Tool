package com.aesapp.gui;

import com.aesapp.service.EncryptionService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class DecryptPanel extends JPanel {
    private JTextArea cipherTextArea;
    private JPasswordField keyField;
    private JButton decryptButton;
    private JTextArea outputArea;
    private JLabel statusLabel;
    private EncryptionService service;

    public DecryptPanel(EncryptionService service) {
        this.service = service;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setOpaque(false);

        // --- Top Input Area ---
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);
        
        cipherTextArea = new JTextArea(4, 40);
        cipherTextArea.setLineWrap(true);
        JScrollPane cipherScroll = new JScrollPane(cipherTextArea);
        cipherScroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60,60,60)), "Paste Cipher Text Here"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        topPanel.add(cipherScroll, BorderLayout.CENTER);
        
        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Passphrase:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        keyField = new JPasswordField();
        formPanel.add(keyField, gbc);
        
        // Action Buttons (Clear & Decrypt) side-by-side
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setOpaque(false);

        JButton clearButton = new JButton("Clear");
        styleInteractiveButton(clearButton, new Color(80, 80, 80), new Color(100, 100, 100));
        clearButton.addActionListener(e -> clearFields());

        decryptButton = new JButton("Decrypt Data");
        styleInteractiveButton(decryptButton, new Color(46, 125, 50), new Color(67, 160, 71)); 
        decryptButton.addActionListener(e -> onDecryptClicked());

        actionPanel.add(clearButton);
        actionPanel.add(decryptButton);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(actionPanel, gbc);
        
        topPanel.add(formPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // --- Center Result Area ---
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60,60,60)), "Decrypted Plain Text"),
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

    private void onDecryptClicked() {
        String cipher = cipherTextArea.getText().trim();
        String pass = new String(keyField.getPassword());
        statusLabel.setVisible(false);
        try {
            outputArea.setText(service.decrypt(cipher, pass));
            showSuccess("Decryption successful!");
        } catch (Exception e) {
            showError("Error: Wrong passphrase or corrupted data.");
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
        cipherTextArea.setText("");
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