package com.qan.enote.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientService {

    public final static String SERVER_IP = "127.0.0.1";
    private final static int SERVER_PORT = 7;
    private static String str;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) { 
            System.out.println("Connected: " + socket);

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while(true){
				str = bufferedReader.readLine();//read a sentence from the standard input
				sendMessage(str);//Send the sentence to the server
				System.out.println("Meassage sent: " + str);
				System.out.println("Please enter command as a string ");
			}
            
//            int ch = 0;
//             while (true) {
//                        ch = in.read(); // Receive data from client
//                        if (ch == -1) {
//                            break;
//                        }
//                        out.write(ch); // Send the results to client
//                    }
         
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        }
    }
    
    private static void sendMessage(String msg)
	{
		try{
			//stream write the message
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			System.out.println(ioException);
		}
	}
}
