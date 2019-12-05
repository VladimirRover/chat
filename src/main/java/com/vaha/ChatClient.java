package com.vaha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private PrintWriter writer;

    public static void main(String[] args) {
        ChatClient chat = new ChatClient();
        Scanner keyboardScanner = new Scanner(System.in);

        new Thread(() -> {
            while (keyboardScanner.hasNextLine()) {
                String s = keyboardScanner.nextLine();
                chat.sendTextToServer(s);
            }
        }).start();

        chat.init();
    }

    public void init() {
        try {
            Socket socket = new Socket("localhost", 10000);

            Scanner serverScanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());

            new Thread(() -> {
                while (serverScanner.hasNextLine()) {
                    String line = serverScanner.nextLine();
                    System.out.println(line);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTextToServer(String s) {
        System.out.println("sending to server: " + s);
        writer.println(s);
        writer.flush();
    }
}
