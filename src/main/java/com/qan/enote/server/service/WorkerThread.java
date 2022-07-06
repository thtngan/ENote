package com.qan.enote.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerThread extends Thread{
    private Socket socket;

	public WorkerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		System.out.println("Processing: " + socket);
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        
			while (true) {
				int ch = in.read(); // Receive data from client
				if (ch == -1) {
					break;
				}
				out.write(ch); // Send the results to client
			}
		} catch (IOException e) {
			System.err.println("Request Processing Error: " + e);
		}
		System.out.println("Complete processing: " + socket);
	}
}
