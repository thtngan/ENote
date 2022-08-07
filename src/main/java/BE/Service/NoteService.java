package BE.Service;

import BE.Model.Account;
import BE.Model.Note;
import BE.Shared.ConnectDB;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteService {

  public static Boolean addNote(Note note){
    ConnectDB cn = new ConnectDB();
    Connection connection = cn.getConnection();
    PreparedStatement ps = null;
    String query = "INSERT INTO NOTE VALUES(?,?,?,?,?)";
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, note.getId());
      ps.setString(2, note.getType());
      ps.setString(3, note.getContent());
      ps.setString(4, note.getUser_id());
      ps.setTimestamp(5, note.getCreate_time());
      ps.execute();

      return true;
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return false;
  }

  public static ArrayList<Note> getAllNote(String username) {
    ArrayList<Note> res = new ArrayList<>();
    ConnectDB cn = new ConnectDB();
    Connection connection = cn.getConnection();
    PreparedStatement ps = null;

    try {
      String sql = "SELECT * FROM NOTE WHERE USERNAME_ID = ?";
      ps = connection.prepareStatement(sql);
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Note rec = new Note();

        rec.setId(rs.getString("ID"));
        rec.setType(rs.getString("Type"));
//        FileInputStream file = new FileInputStream(String.valueOf(rs.getBinaryStream("Content")));
//        rec.setContent(file);
        rec.setUser_id(rs.getString("Username_ID"));
        rec.setContent(null);
        rec.setCreate_time(rs.getTimestamp("Create_Time"));

        res.add(rec);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (ps != null) {
        try {
          ps.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    }
    return res;
  }

  public static Note detailNote(String username, String id) {
    Note res = null;
    ConnectDB cn = new ConnectDB();
    Connection connection = cn.getConnection();
    PreparedStatement ps = null;

    try {
      String query = "SELECT * FROM NOTE WHERE Username_ID = ? AND ID = ?";
      ps = connection.prepareStatement(query);
      ps.setString(1, username);
      ps.setString(2, id);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        res = new Note();

        res.setId(rs.getString("ID"));
        res.setType(rs.getString("Type"));
        res.setContent(rs.getString("Content"));
        res.setUser_id(rs.getString("Username_ID"));
        res.setCreate_time(rs.getTimestamp("Create_Time"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;

  }
}
