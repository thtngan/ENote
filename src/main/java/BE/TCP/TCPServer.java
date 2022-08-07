package BE.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

  private ServerSocket server;
  private Socket client;
  private String password;
  private boolean is_listening;

  public TCPServer() {
    this.server = null;
    this.client = null;
    this.password = null;
    this.is_listening = false;
  }

  public void startListeningOnTcpServer(String host, int port, String password) throws IOException {
    if(this.is_listening == false) {
      try {
        InetSocketAddress endpoint = new InetSocketAddress(host, port);
        this.password = password;
        this.server = new ServerSocket();
        this.server.bind(endpoint);
        this.is_listening = true;
      } catch (IOException exception) {
        System.out.println("TCP Server: " + exception);
      }

    }
  }

  public void stopListeningOnTcpServer() throws IOException {
    if(this.is_listening == true) {
      this.server.close();
      if(this.client != null) this.client.close();
      this.is_listening = false;
    }
  }

  public void waitingConnectionFromClient() throws IOException {
    this.client = this.server.accept();
    DataOutputStream dos = new DataOutputStream(this.client.getOutputStream());
    DataInputStream dis = new DataInputStream(this.client.getInputStream());
    String password = dis.readUTF();
    String result = null;

    if(this.password.equals(password)) {
      result = "true";
      
    }
    else result = "false";
    dos.writeUTF(result);
  }

  public boolean isListening() {
    return this.is_listening;
  }

  public ServerSocket getServer() {
    return this.server;
  }

}
