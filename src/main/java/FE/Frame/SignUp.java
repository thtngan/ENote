package FE.Frame;

import BE.RMI.IEnote;
import BE.Shared.CommonBus;
import FE.Panel.ClientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

public class SignUp extends JFrame implements Runnable {
  public final static int WIDTH_DIALOG = 380;
  public final static int HEIGHT_DIALOG = 300;

  private CommonBus common_bus;
  private IEnote enote_obj;

  public SignUp(ClientPanel client_panel, CommonBus common_bus) {
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setTitle("E-NOTE APPLICATION");
    this.setResizable(false);
    this.getContentPane().setPreferredSize(new Dimension(LogIn.WIDTH_DIALOG, LogIn.HEIGHT_DIALOG));
    this.setLayout(null);
    this.pack();
    this.setVisible(true);

    this.common_bus = common_bus;
    this.enote_obj = this.common_bus.getRMIClient().getRemoteObject();

    //add components
    this.initComponents();
  }

  private void initComponents() {
    JLabel label = new JLabel();
    label.setText("SIGN UP");
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

    // TODO: Re-password
    JLabel lblPassword1 = new JLabel("Re-password: ");
    lblPassword1.setBounds(40,180,130,30);
    lblPassword1.setFont(new Font("SEGOE UI", Font.BOLD, 14));
    this.add(lblPassword1);

    JPasswordField txtPassword1 = new JPasswordField("");
    txtPassword1.setBounds(130, 180, 200, 30);
    this.add(txtPassword1);

    // TODO: button sign up
    Button btnSignUp = new Button("SIGN UP");
    btnSignUp.setBounds(115, 220, 162, 30);
    btnSignUp.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if(checkValid(txtUsername.getText(), txtPassword.getText(), txtPassword1.getText())) {
          String username = txtUsername.getText();
          String password = txtPassword.getText();
          signUpMousePressed(username, password);
        }
      }
    });
    this.add(btnSignUp);
 }

  @Override
  public void run() {

  }

  private boolean checkValid(String username, String pwd, String rePwd) {
    // TODO: check not null
    if (username.isEmpty() || pwd.isEmpty() || rePwd.isEmpty()) {
      JOptionPane.showMessageDialog(this,"Username and password can not be null","Error",JOptionPane.OK_OPTION);
      return false;
    }

    if (!pwd.equals(rePwd)) {
      JOptionPane.showMessageDialog(this,"Password are not match","Error",JOptionPane.OK_OPTION);
      return false;
    }
    return true;
  }

  // TODO: sign up
  private void signUpMousePressed(String username, String password) {
    try {
      String signUpProcess = this.enote_obj.signUp(username, password);
      if (signUpProcess.equals("Success")) {
        JOptionPane.showMessageDialog(this, signUpProcess,"Success",JOptionPane.OK_OPTION);
        this.dispose();
      } else {
        JOptionPane.showMessageDialog(this, signUpProcess,"Error",JOptionPane.OK_CANCEL_OPTION);
      }

    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}
