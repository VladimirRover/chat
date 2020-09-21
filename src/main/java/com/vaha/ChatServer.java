package com.vaha;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int DEFAULT_PORT = 10000;
    private static List<ChatSession> sessions;
    private static ExecutorService broadcastService;
    static int userCount = 0;

    public static void main(String[] args) {
        System.out.println("server started ...");
        sessions = new ArrayList<>();
        broadcastService = Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);
                new Thread(() -> {
                    String name ="user" + userCount++;
                    ChatSession chatSession = new ChatSession(name);
                    sessions.add(chatSession);
                    System.out.println("sessions size: " + sessions.size());
                    chatSession.processConnection(socket,
                            ChatServer::broadcast,
                            ChatServer::removeSession);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String line) {
        for (ChatSession session : sessions) {
            broadcastService.execute(() -> {
                session.send2client(line);
            });
        }
    }

    private static void removeSession(ChatSession session) {
        sessions.remove(session);
        System.out.println("session removed: " + session);
        System.out.println("sessions size: " + sessions.size());
    }
}