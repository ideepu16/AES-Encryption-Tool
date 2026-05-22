package com.aesapp.gui;

import com.aesapp.service.EncryptionService;
import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    public MainWindow() {
        setModernBlueTheme(); // Theme function
        EncryptionService service = new EncryptionService();
        initComponents(service);
    }

    private void setModernBlueTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            Color darkBg = new Color(15, 23, 42);      // Deep Midnight Blue (Frame Background)
            Color panelBg = new Color(30, 41, 59);     // Slate Blue (Tabs aur Panel Background)
            Color lightText = new Color(248, 250, 252); // Off-White Text 
            Color fieldBg = new Color(51, 65, 85);     // Lighter Blue-Grey (Text Boxes )
            
            UIManager.put("Panel.background", panelBg);
            UIManager.put("Label.foreground", lightText);
            
            UIManager.put("TextField.background", fieldBg);
            UIManager.put("TextField.foreground", lightText);
            UIManager.put("TextField.caretForeground", Color.WHITE);
            UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(71, 85, 105)), 
                BorderFactory.createEmptyBorder(8, 10, 8, 10))); 
                
            UIManager.put("PasswordField.background", fieldBg);
            UIManager.put("PasswordField.foreground", lightText);
            UIManager.put("PasswordField.caretForeground", Color.WHITE);
            UIManager.put("PasswordField.border", UIManager.get("TextField.border"));

            UIManager.put("TextArea.background", fieldBg);
            UIManager.put("TextArea.foreground", lightText);
            UIManager.put("TextArea.caretForeground", Color.WHITE);
            
            UIManager.put("TabbedPane.background", darkBg);
            UIManager.put("TabbedPane.foreground", lightText);
            UIManager.put("TabbedPane.contentAreaColor", panelBg);
            UIManager.put("TabbedPane.selected", panelBg);
            UIManager.put("TitledBorder.titleColor", new Color(148, 163, 184)); // Borders ka colour
            
            Font modernFont = new Font("Segoe UI", Font.PLAIN, 14);
            UIManager.put("Label.font", modernFont);
            UIManager.put("TextField.font", modernFont);
            UIManager.put("PasswordField.font", modernFont);
            UIManager.put("TextArea.font", modernFont);
            UIManager.put("TabbedPane.font", new Font("Segoe UI", Font.BOLD, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents(EncryptionService service) {
        frame = new JFrame("AES Encryption Tool - By Deepak and Priyanshi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 550);
        frame.setLocationRelativeTo(null); 
        // Window ke piche ka background
        frame.getContentPane().setBackground(new Color(15, 23, 42));

        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        tabbedPane.addTab("    Encrypt    ", new EncryptPanel(service));
        tabbedPane.addTab("    Decrypt    ", new DecryptPanel(service));

        frame.add(tabbedPane);
    }

    public void show() {
        frame.setVisible(true);
    }
}