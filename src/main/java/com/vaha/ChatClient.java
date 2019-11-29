package com.vaha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 10000);

            Scanner serverScanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Scanner keyboardScanner = new Scanner(System.in);

            new Thread(() -> {
                while (keyboardScanner.hasNextLine()) {
                    String s = keyboardScanner.nextLine();
                    writer.println(s);
                    writer.flush();
                }
            });

            while (serverScanner.hasNextLine()) {
                String line = serverScanner.nextLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
