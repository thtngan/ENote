package FE.Dialog;

import BE.Model.Note;
import BE.RMI.IEnote;
import BE.Service.NoteService;
import BE.Shared.CommonBus;
import FE.Panel.ClientPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DetailNote extends JDialog implements Runnable {
  public final static int WIDTH_DIALOG = 470;
  public final static int HEIGHT_DIALOG = 320;

  private IEnote enote_obj;
  private String username;
  private String id;
  private Note note;

  private JTextField txtName;
  private JTextArea txtContent;
  private JScrollPane jScrollPane;
  private Choice choice;

  public DetailNote(JFrame owner, IEnote enote_obj, String username, String id) {
    super(owner);
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setTitle("DETAIL NOTE");
    this.setResizable(true);
    this.getContentPane().setPreferredSize(new Dimension(DetailNote.WIDTH_DIALOG, DetailNote.HEIGHT_DIALOG));
    this.setLayout(null);
    this.pack();

    this.enote_obj = enote_obj;
    this.username = username;
    this.id = id;
    this.note = new Note();

    this.initComponents();

  }

  private void initComponents() {
//    this.note = NoteService.detailNote(this.username, this.id);
    try {
      this.note = this.enote_obj.getNote(this.username, this.id);
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    System.out.println(this.note.toString());
    // TODO: label
    JLabel label = new JLabel();
    label.setText("DETAIL A NOTE");
    label.setFont(new Font("segoe ui", Font.BOLD, 14));
    label.setBounds(150, 20, 500, 30);
    this.add(label);

    // TODO: Name
    JLabel name = new JLabel("Name:");
    name.setBounds(20, 50, 50, 30);
    this.add(name);

    this.txtName = new JTextField("");
    this.txtName.setBounds(80, 50, 370, 30);
    this.txtName.setText(note.getId());
    this.txtName.setEditable(false);
    this.add(this.txtName);

    // TODO: Type
    JLabel type = new JLabel("Type:");
    type.setBounds(20, 100, 50, 30);
    this.add(type);

    this.choice = new Choice();
    this.choice.setBounds(80, 100, 370, 30);
    this.choice.add("Text");
    this.choice.add("Image");
    this.choice.add("File");
    this.choice.select(note.getType());
    this.choice.setEnabled(false);
    this.add(this.choice);

    // TODO: Content
    JLabel content = new JLabel("Content:");
    content.setBounds(20, 150, 60, 30);
    this.add(content);



    // TODO: show image
    if (this.note.getType().equals("Image")) {
      try {
        URL url = new URL(this.note.getContent());
        Image image = ImageIO.read(url);
        JLabel imgLabel = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        this.jScrollPane = new JScrollPane(imgLabel);
        this.jScrollPane.setBounds(80, 150, 370, 100);
        this.add(jScrollPane);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
          this.txtContent = new JTextArea(this.note.getContent());
          this.txtContent.setLineWrap(true);
          this.txtContent.setEnabled(false);
          this.jScrollPane = new JScrollPane(txtContent);
          this.jScrollPane.setBounds(80, 150, 370, 100);
          this.add(jScrollPane);
    }


    // TODO: button SAVE
    Button btnSave = new Button("DOWNLOAD");
    btnSave.setBounds(20, 260, 430, 30);
    btnSave.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        save(e);
      }
    });
    this.add(btnSave);

  }

  @Override
  public void run() {

  }

  private void save(MouseEvent e) {
    if (this.note.getType().equals("Text")) {
      if(createFile(this.note.getContent(), this.note.getId())) {
        JOptionPane.showMessageDialog(this,"Created file successful","Success",JOptionPane.OK_OPTION);
      } else {
        JOptionPane.showMessageDialog(this,"Cannot create a new file","Error",JOptionPane.WARNING_MESSAGE);
      }
    } else {
      try {
        URL url = new URL(this.note.getContent());
        String fileName = this.note.getId();

        if(downloadFile(url, fileName)) {
          JOptionPane.showMessageDialog(this,"Created file successful","Success",JOptionPane.OK_OPTION);
        } else {
          JOptionPane.showMessageDialog(this,"Cannot create a new file","Error",JOptionPane.WARNING_MESSAGE);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static boolean downloadFile(URL url, String fileName) {
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      InputStream in = url.openStream();
      BufferedInputStream bis = new BufferedInputStream(in);
      byte[] data = new byte[1024];
      int count;
      while ((count = bis.read(data, 0, 1024)) != -1) {
        fos.write(data, 0, count);
      }
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  public static boolean createFile(String content, String fileName) {
    try {
      FileOutputStream fos = new FileOutputStream(fileName + ".txt", true);
      byte[] b= content.getBytes();
      fos.write(b);
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

//  public static void main(String[] args) {
//    DetailNote note = new DetailNote("username", "123");
//    note.setVisible(true);
//  }

}
