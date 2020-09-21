package com.vaha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class Communicator {

    String host = "localhost";
    int port = 10000;
    private PrintWriter writer;

    public void init(Consumer<String> consumer) {
        try {
            Socket socket = new Socket(host, port);
            Scanner serverScanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            new Thread(() -> {
                while (serverScanner.hasNextLine()) {
                    String line = serverScanner.nextLine();
                    consumer.accept(line);
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
