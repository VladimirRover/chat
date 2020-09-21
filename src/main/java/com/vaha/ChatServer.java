package com.vaha;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int DEFAULT_PORT = 10000;
    private static List<ChatSession> sessions;

    public static void main(String[] args) {
        System.out.println("start ...");

        sessions = new ArrayList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);
                new Thread(() -> {
                    ChatSession chatSession = new ChatSession();
                    sessions.add(chatSession);
                    chatSession.processConnection(socket, ChatServer::broadcast);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String line) {
        for (ChatSession session: sessions) {
            session.send2client(line);
        }
    }
}