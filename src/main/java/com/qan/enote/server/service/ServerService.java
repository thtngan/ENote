package com.qan.enote.server.service;

import com.qan.enote.client.service.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {

    private final ServerSocket serverSocket;

    public ServerService(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            // Listen for connections (clients to connect) on port 1234.
            while (!serverSocket.isClosed()) {
                // Will be closed in the Client Handler.
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    // Close the server socket gracefully.
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        ServerService server = new ServerService(serverSocket);
        server.startServer();
    }
}
