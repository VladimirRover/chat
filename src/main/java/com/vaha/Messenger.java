package com.vaha;

import javax.swing.*;
import java.awt.*;

public class Messenger {

    static Communicator chat;
    private static JTextArea textArea;
    private static JScrollPane sp;
    private static final int USERS_IN_LIST = 10;
    private static List userList;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LayoutManager manager = new BorderLayout();
        JPanel panel = new JPanel(manager);
        panel.setPreferredSize(new Dimension(400, 400));
        textArea = new JTextArea();
        textArea.setEnabled(false);
        sp = new JScrollPane(textArea);
        panel.add(sp, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        JTextField textField = new JTextField(20);
        inputPanel.add(textField);
        textField.addActionListener(e -> sendText(textField));

        JButton sendButton = new JButton("send");
        inputPanel.add(sendButton);
        sendButton.addActionListener(e -> sendText(textField));

        panel.add(inputPanel, BorderLayout.SOUTH);

        userList = new List(USERS_IN_LIST, false);
        userList.addActionListener(actionEvent -> {
            textField.setText(actionEvent.getActionCommand()+ " ");
        });

        panel.add(userList, BorderLayout.WEST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        chat = new Communicator();
        chat.init(Messenger::processServerMessage);
    }

    private static void sendText(JTextField textField) {
        String text = textField.getText();
        textField.setText("");
        chat.sendTextToServer(text);
    }

    private static void processServerMessage(String text) {
        if (text.startsWith("/name")) {
            String[] words = text.split(" ");
            String userName = words[1];
            textArea.append("Welcome to chat, " + userName + "\n");
            return;
        }
        if (text.startsWith("/list")){
            String[] names = text.split(" ");
            for (int i = 1; i < names.length; i++) {
                userList.add(names[i]);
            }
            return;
        }
        if (text.startsWith("/add")){
            String[] words = text.split(" ");
            String userName = words[1];
            userList.add(userName);
            return;
        }
        if (text.startsWith("/remove")){
            String[] words = text.split(" ");
            String userName = words[1];
            userList.remove(userName);
            return;
        }
        textArea.append(text + "\n");
    }
}