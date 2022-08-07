package FE.Frame;

import BE.RMI.IEnote;
import BE.Shared.CommonBus;
import FE.Panel.ClientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class LogIn extends JFrame implements Runnable {
  public final static int WIDTH_DIALOG = 380;
  public final static int HEIGHT_DIALOG = 300;

  private ClientPanel client_panel;
  private CommonBus common_bus;
  private IEnote enote_obj;

  public LogIn(ClientPanel client_panel, CommonBus common_bus) {
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setTitle("E-NOTE APPLICATION");
    this.setResizable(false);
    this.getContentPane().setPreferredSize(new Dimension(LogIn.WIDTH_DIALOG, LogIn.HEIGHT_DIALOG));
    this.setLayout(null);
    this.pack();
    this.setVisible(true);

    this.common_bus = common_bus;
    this.enote_obj = this.common_bus.getRMIClient().getRemoteObject();
    this.client_panel = client_panel;

    //add components
    this.initComponents();
  }

  private void initComponents() {
    JLabel label = new JLabel();
    label.setText("LOGIN");
    label.setFont(new Font("segoe ui", Font.BOLD, 40));
    label.setBounds(135, 20, 510, 50);
    this.add(label);

    // TODO: username
    JLabel lblUsername = new JLabel("Username: ");
    lblUsername.setBounds(40,80,130,30);
    lblUsername.setFont(new Font("SEGOE UI", Font.BOLD, 14));
    this.add(lblUsername);

    JTextField txtUsername = new JTextField("");
    txtUsername.setBounds(130, 80, 200, 30);
    this.add(txtUsername);

    // TODO: password
    JLabel lblPassword = new JLabel("Password: ");
    lblPassword.setBounds(40,130,130,30);
    lblPassword.setFont(new Font("SEGOE UI", Font.BOLD, 14));
    this.add(lblPassword);

    JPasswordField txtPassword = new JPasswordField("");
    txtPassword.setBounds(130, 130, 200, 30);
    this.add(txtPassword);

    // TODO: button login
    Button btnLogin = new Button("LOGIN");
    btnLogin.setBounds(115, 180, 162, 30);
    btnLogin.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        loginMousePressed(username, password);
      }
    });
    this.add(btnLogin);

    // TODO: sign up
    Button btnSignUp = new Button("SIGNUP");
    btnSignUp.setBounds(115, 220, 162, 30);
    btnSignUp.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        signUpMousePressed();
      }
    });
    this.add(btnSignUp);
  }

  @Override
  public void run() {

  }

  // TODO: login
  private void loginMousePressed(String username, String password) {
    try {
      int loginProcess = this.enote_obj.logIn(username, password);
      System.out.println(loginProcess);

      switch (loginProcess) {
        case 0:
          System.out.println("Log in successful");
          ListNote listNote = new ListNote(this.client_panel, this.common_bus, username);
          listNote.setVisible(true);
          break;
        case 1:
          JOptionPane.showMessageDialog(this,"Username is not exist","Error",JOptionPane.WARNING_MESSAGE);
          break;
        case 2:
          JOptionPane.showMessageDialog(this,"Wrong password","Error",JOptionPane.WARNING_MESSAGE);
          break;
      }



    } catch (RemoteException e) {
      e.printStackTrace();
    }

  }

  private void signUpMousePressed() {
    SignUp signUp = new SignUp(this.client_panel, this.common_bus);
    signUp.setVisible(true);
  }
}
