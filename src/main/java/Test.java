import BE.Model.Account;
import BE.Model.Note;
import BE.Service.AccountService;
import BE.Shared.ConnectDB;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

  public static void downloadFile(URL url, String fileName) throws IOException {
    try (InputStream in = url.openStream();
         BufferedInputStream bis = new BufferedInputStream(in);
         FileOutputStream fos = new FileOutputStream(fileName)) {

      byte[] data = new byte[1024];
      int count;
      while ((count = bis.read(data, 0, 1024)) != -1) {
        fos.write(data, 0, count);
      }
    }
  }

  public static void main(String[] args) {
    URL url = null;
    try {
      url = new URL("http://res.cloudinary.com/dhd5h2a7n/image/upload/v1659822931/beemolang_wallpaper_desktop1.jpg.jpg");
      downloadFile(url,"hi.jpg");
    } catch (Exception e) {
      e.printStackTrace();
    }




  }
}
