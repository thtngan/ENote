package BE.RMI;

import BE.Model.Account;
import BE.Model.Note;
import BE.Service.AccountService;
import BE.Service.NoteService;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EnoteImpl extends UnicastRemoteObject implements IEnote {
  protected EnoteImpl() throws RemoteException {
  }

  protected EnoteImpl(int port) throws RemoteException {
    super(port);
  }

  protected EnoteImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
    super(port, csf, ssf);
  }


  @Override
  public int logIn(String username, String pwd) throws RemoteException {
    Account account = AccountService.getByUsername(username);
//    System.out.println(account.toString());
    if (account == null) {
      return 1;
    } else {
      if (!BCrypt.checkpw(pwd, account.getPassword())) {
        return 2;
      }
    }
    return 0;
  }

  @Override
  public String signUp(String username, String pwd) throws RemoteException {
    String message = checkValid(username, pwd);

    if (message.equals("Success")) {
      Account account = new Account(username, pwd);
      if (AccountService.addOne(account)) {
        return "Success";
      } else {
        return "Create new user is failed.";
      }
    }

    return message;
  }

  private String checkValid(String username, String pwd) {
    // TODO: check 3 letter
    if (pwd.length() < 3){
      return "Password need at least 3 character";
    }
    // TODO: check 5 letter, a-z, 0-9
    String PATTERN ="^[a-z0-9]{4,50}$";
    Pattern p = Pattern.compile(PATTERN);
    if (!p.matcher(username).matches()) {
      return "Username need at least 5 character: a-z, 0-9";
    }
    return "Success";
  }

  @Override
  public boolean addNote(Note note) throws RemoteException {
    return NoteService.addNote(note);
  }

  @Override
  public ArrayList<Note> getAllNote(String username) throws RemoteException {
    return NoteService.getAllNote(username);
  }

  @Override
  public Note getNote(String username, String id) throws RemoteException {
    return NoteService.detailNote(username, id);
  }
}
