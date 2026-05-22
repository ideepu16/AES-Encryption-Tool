package com.aesapp;

import com.aesapp.gui.MainWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().show();
        });
    }
}