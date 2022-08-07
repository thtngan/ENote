package BE.Model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class Note implements Serializable {
  private String id, type, user_id, content;
  private Timestamp create_time;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getCreate_time() {
    return create_time;
  }

  public void setCreate_time(Timestamp create_time) {
    this.create_time = create_time;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  @Override
  public String toString() {
    return "Note{" +
        "id='" + id + '\'' +
        ", type='" + type + '\'' +
        ", user_id='" + user_id + '\'' +
        ", content=" + content +
        ", create_time=" + create_time +
        '}';
  }
}
