package com.vaha;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class ChatSession {

    private String name;
    private PrintWriter writer;
    private Socket socket;
    private Scanner scanner;

    public String getName() {
        return name;
    }

    public ChatSession(Socket socket, String name) {
        this.name = name;
        this.socket = socket;
        try {
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void processConnection(Consumer<String> broadcaster, Consumer<ChatSession> sessionRemover) {
        try {
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
