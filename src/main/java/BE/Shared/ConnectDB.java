package BE.Shared;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

  public Connection getConnection() {
    @SuppressWarnings("UnusedAssignment")
    Connection conn = null;
    String dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;encrypt=true;trustServerCertificate=true;databaseName=eNote";
    String user = "sa";
    String pass = "sa";

    try {
      conn = DriverManager.getConnection(dbURL, user, pass);
      if (conn != null) {
        DatabaseMetaData dm = conn.getMetaData();
//                System.out.println("Driver name: " + dm.getDriverName());
//                System.out.println("Driver version: " + dm.getDriverVersion());
//                System.out.println("Product name: " + dm.getDatabaseProductName());
//                System.out.println("Product version: " + dm.getDatabaseProductVersion());
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return conn;
  }

}
