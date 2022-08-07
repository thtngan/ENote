package BE.Service;

import BE.Model.Account;
import BE.Shared.ConnectDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountService {

  public static Account getByUsername(String username) {
    Account res = null;
    ConnectDB cn = new ConnectDB();
    Connection connection = cn.getConnection();
    PreparedStatement ps = null;

    try {
      String query = "SELECT * FROM ACCOUNT WHERE Username = ?";
      ps = connection.prepareStatement(query);
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        res = new Account();
        res.setUsername(rs.getString("Username"));
        res.setPassword(rs.getString("Password"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  public static Boolean addOne(Account acc){
    ConnectDB cn = new ConnectDB();
    Connection connection = cn.getConnection();
    PreparedStatement ps = null;
    String query = "INSERT INTO ACCOUNT VALUES(?,?)";
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, acc.getUsername());
      String hash = BCrypt.hashpw(acc.getPassword(), BCrypt.gensalt(13));
      ps.setString(2, hash);
      ps.execute();
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

}
