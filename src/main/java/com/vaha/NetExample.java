package com.vaha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NetExample {
    private static final int DEFAULT_PORT = 10000;

    public static void main(String[] args) {
        System.out.println("start ...");
        try {
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            Socket socket = serverSocket.accept();
            System.out.println("Connection: " + socket);

            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.println(line);
                writer.println("OK");
                writer.flush();
                if (line.equals("bye")){
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}