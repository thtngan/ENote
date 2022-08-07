package BE.RMI;

import BE.Model.Note;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IEnote extends Remote {
  int logIn(String username, String pwd) throws RemoteException;
  String signUp(String username, String pwd) throws RemoteException;

  // TODO: function of note
  boolean addNote(Note note) throws RemoteException;
  ArrayList<Note> getAllNote(String username) throws RemoteException;
  Note getNote(String username, String id) throws RemoteException;
}
