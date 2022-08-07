package BE.RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {

  private IEnote enote_obj;
  private boolean is_remote_server;

  public RMIClient() {
    this.enote_obj = null;
    this.is_remote_server = false;
  }

  public void startConnectingToRmiServer(String host, int port) throws RemoteException, NotBoundException, MalformedURLException {
    if(this.is_remote_server == false) {
      String url = "rmi://" + host + ":" + port + "/remote";
      System.out.println(url);
      this.enote_obj = (IEnote) Naming.lookup(url);
      this.is_remote_server = true;
    }
  }

  public void stopConnectingToRmiServer() {
    if(this.is_remote_server == true) {
      this.enote_obj = null;
      this.is_remote_server = false;
    }
  }

  public IEnote getRemoteObject() {
    return this.enote_obj;
  }

  public boolean isRemoteServer() {
    return this.is_remote_server;
  }

  public void setRemoteServer(boolean b) {
    this.is_remote_server = b;
  }

}
