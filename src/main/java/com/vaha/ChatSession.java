package com.vaha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class ChatSession {

    String name;
    private PrintWriter writer;

    public ChatSession(String name) {
        this.name = name;
    }

    void processConnection(Socket socket,
                           Consumer<String> broadcaster,
                           Consumer<ChatSession> sessionRemover) {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            send2client("/name " + name);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                broadcaster.accept(name + " > " + line);
            }
            System.out.println("connection is closed");
            sessionRemover.accept(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send2client(String line) {
        writer.println(line);
        writer.flush();
    }
}
