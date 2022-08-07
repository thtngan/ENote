package FE.Frame;

import BE.Model.Note;
import BE.RMI.IEnote;
import BE.Service.NoteService;
import BE.Shared.CommonBus;
import FE.Dialog.CreateNote;
import FE.Dialog.DetailNote;
import FE.Panel.ClientPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class ListNote extends JFrame implements Runnable {
  public final static int WIDTH_DIALOG = 480;
  public final static int HEIGHT_DIALOG = 480;

  private ClientPanel client_panel;
  private CommonBus common_bus;
  private IEnote enote_obj;
  private String username;

  private CreateNote createNote;
  private DetailNote detailNote;

  private JScrollPane scroll;
  private JTable table;

  public ListNote(ClientPanel client_panel, CommonBus common_bus, String username) {
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setTitle("E-NOTE APPLICATION");
    this.setResizable(false);
    this.getContentPane().setPreferredSize(new Dimension(ListNote.WIDTH_DIALOG, ListNote.HEIGHT_DIALOG));
    this.setLayout(null);
    this.pack();
    this.setVisible(true);

    this.common_bus = common_bus;
    this.enote_obj = this.common_bus.getRMIClient().getRemoteObject();
    this.client_panel = client_panel;
    this.username = username;

    //add components
    this.initComponents();
  }

  private void initComponents() {
    JLabel label = new JLabel();
    label.setText("LIST NOTE");
    label.setFont(new Font("segoe ui", Font.BOLD, 30));
    label.setBounds(145, 20, 510, 50);
    this.add(label);

    // TODO: add refresh
    Button refreshBtn = new Button("Refresh");
    refreshBtn.setBounds(340, 80, 110, 25);
    refreshBtn.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        initListNote();
      }
    });
    this.add(refreshBtn);

    // TODO: add table
//    ArrayList<Note> noteList = NoteService.getAllNote(this.username);
    ArrayList<Note> noteList = null;
    try {
      noteList = this.enote_obj.getAllNote(this.username);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    noteList.forEach(note1 -> System.out.println(note1.toString()));
    String col[] = {"ID", "Type", "Created Time"};
    DefaultTableModel tableModel = new DefaultTableModel(col, 0);

    noteList.forEach(note -> {
      String ID = note.getId();
      String type = note.getType();
      Timestamp created_time = note.getCreate_time();

      Object[] data = {ID, type, created_time};
      tableModel.addRow(data);
    });

    this.table = new JTable(tableModel);
    this.table.setAutoCreateRowSorter(true);
    this.table.getRowSorter().toggleSortOrder(2);

    this.scroll = new JScrollPane(this.table);
    this.scroll.setBounds(20, 110, 430, 300);
    this.add(this.scroll);

    // TODO: add two button
    Button btnAdd = new Button("ADD");
    btnAdd.setBounds(240, 420, 210,30);
    btnAdd.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        createNote();
      }
    });
    this.add(btnAdd);

    Button btnDet = new Button("DETAIL");
    btnDet.setBounds(20, 420, 210,30);
    btnDet.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
//        System.out.println(table.getValueAt(table.getSelectedRow(), 0));
        detailNote(table.getValueAt(table.getSelectedRow(), 0).toString());
      }
    });
    this.add(btnDet);

  }

  @Override
  public void run() {

  }



//  public static void main(String[] args) {
//    new ListNote("username");
//  }

  private void initListNote() {
    ArrayList<Note> noteList = null;
    try {
      noteList = this.enote_obj.getAllNote(this.username);
    } catch (RemoteException e) {
      e.printStackTrace();
    }

//    ArrayList<Note> noteList = NoteService.getAllNote(this.username);


    String col[] = {"ID", "Type", "Created Time"};
    DefaultTableModel tableModel = new DefaultTableModel(col, 0);

    noteList.forEach(note -> {
      String ID = note.getId();
      String type = note.getType();
      Timestamp created_time = note.getCreate_time();

      Object[] data = {ID, type, created_time};
      tableModel.addRow(data);
      tableModel.fireTableDataChanged();
    });

    this.remove(this.scroll);

    this.table = new JTable(tableModel);
    this.table.setAutoCreateRowSorter(true);
    this.table.getRowSorter().toggleSortOrder(2);
    this.scroll = new JScrollPane(this.table);
    this.scroll.setBounds(20, 110, 430, 300);
    this.add(this.scroll);
    this.repaint();
    this.revalidate();
  }

  public void createNote() {
    this.createNote = new CreateNote(this, this.enote_obj, this.username);
    this.createNote.setVisible(true);
  }


  private void detailNote(String id) {
    // TODO: open detail note to view
    this.detailNote = new DetailNote(this, this.enote_obj, this.username, id);
    this.detailNote.setVisible(true);
  }
}
