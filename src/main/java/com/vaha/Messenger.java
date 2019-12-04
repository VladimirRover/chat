package com.vaha;

import javax.swing.*;
import java.awt.*;

public class Messenger {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LayoutManager manager = new BorderLayout();
        JPanel panel = new JPanel(manager);
        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        panel.add(textArea, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel();
        JTextField textField = new JTextField(20);
        inputPanel.add(textField);
        textField.addActionListener(e -> {
            placeText(textArea, textField);
        });
        JButton sendButton = new JButton("send");
        inputPanel.add(sendButton);
        sendButton.addActionListener(e -> {
            placeText(textArea, textField);
        });
        panel.add(inputPanel, BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(500, 500));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void placeText(JTextArea textArea, JTextField textField) {
        String text = textField.getText();
        textField.setText("");
        textArea.append(text + "\n");
    }
}
