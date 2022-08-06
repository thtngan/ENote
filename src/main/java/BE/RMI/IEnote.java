package BE.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEnote extends Remote {
    int logIn(String username, String pwd) throws RemoteException;
}
