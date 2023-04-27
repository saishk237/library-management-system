package com.pbl;

import com.pbl.admin.AdminLogin;
import com.pbl.librarian.LibrarianDashboard;
import com.pbl.models.Librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static com.pbl.Database.DBConnection;

public class Login implements ActionListener{
    JFrame loginFrame;
    JButton exitButton;
    JButton submitButton;
    JButton adminLogin;
    JTextField usernameField;
    JPasswordField passwordField;

    public Login(){
        loginFrame = new JFrame();
        loginFrame.setUndecorated(true);
        loginFrame.setBackground(new Color(0, 0, 0, 0));
        JLabel loginImage = new JLabel(new ImageIcon("./images/lib_logo2.png"));
        loginFrame.setIconImage(new ImageIcon("./images/favicon.png").getImage());
        exitButton = new JButton("Exit");
        submitButton = new JButton("Submit");
        adminLogin = new JButton("Admin Login");
        usernameField = new JTextField("Enter your username");
        passwordField = new JPasswordField("Enter your password");
        passwordField.setEchoChar ((char) 0);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(700, 500);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setPreferredSize(new Dimension(350, 500));
        panel2.setPreferredSize(new Dimension(350, 500));
        panel1.setBackground(Color.darkGray);
        panel2.setBackground(new Color(0f, 0f, 0f, 0.7f));

        loginImage.setSize(200,500);
        loginImage.setText("Librarian Login");
        loginImage.setFont(new Font("Times New Roman", Font.BOLD, 20));
        loginImage.setForeground(Color.white);
        panel1.setLayout(new BorderLayout());
        loginImage.setVerticalTextPosition(JLabel.BOTTOM);
        loginImage.setHorizontalTextPosition(JLabel.CENTER);
        loginImage.setVerticalAlignment(JLabel.CENTER);
        adminLogin.setFocusable(false);
        adminLogin.addActionListener(this);
        panel1.setBorder(new EmptyBorder(new Insets(80, 60, 40, 60)));
        panel1.add(loginImage, BorderLayout.CENTER);
        panel1.add(adminLogin, BorderLayout.SOUTH);

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        panel2.setBorder(new EmptyBorder(new Insets(80, 20, 80, 20)));
        //usernameField.addFocusListener(usernameFieldFocusGained(this.usernameField.hasFocus()));
        exitButton.addActionListener(this);
        submitButton.addActionListener(this);
        usernameField.setPreferredSize(new Dimension(150, 30));
        passwordField.setPreferredSize(new Dimension(150, 30));

        submitButton.setFocusable(false);
        exitButton.setFocusable(false);

        panel2.add(usernameField);
        panel2.add(Box.createRigidArea(new Dimension(150, 80)));
        panel2.add(passwordField);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.add(Box.createRigidArea(new Dimension(150, 100)));
        panel2.add(submitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.add(Box.createRigidArea(new Dimension(150, 20)));
        panel2.add(exitButton);



        loginFrame.add(panel1, BorderLayout.WEST);
        loginFrame.add(panel2, BorderLayout.EAST);

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter your username")) {
                    usernameField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Enter your username");
                }
            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String password = new String(passwordField.getPassword());
                if (password.equals("Enter your password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(passwordField.getPassword());
                if(password.isEmpty()){
                    passwordField.setText("Enter your password");
                    passwordField.setEchoChar ((char) 0);
                }
            }
        });

        loginFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton){
            loginFrame.setVisible(false);
            loginFrame.dispose();
            System.exit(0);
        }

        if(e.getSource() == adminLogin){
            loginFrame.setVisible(false);
            loginFrame.dispose();
            new AdminLogin();
        }
        if(e.getSource() == submitButton){
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Connection conn = DBConnection;
            Statement ps= null;
            try {
                ps = DBConnection.createStatement();
                String sql = "SELECT * FROM librarian WHERE username='" + username + "'";
                ResultSet rs=ps.executeQuery(sql);
                rs.next();
                while(true){
                    if(Objects.equals(rs.getString(3), password)){
                        Librarian librarian = new Librarian(rs.getString(4), rs.getString(1));
                        loginFrame.dispose();
                        new LibrarianDashboard(librarian);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Wrong Password or Username. Enter correct credentials", "Error in Logging In", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Wrong Password or Username. Enter correct credentials", "Error in Logging In", JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void usernameFieldFocusGained(java.awt.event.FocusEvent evt) {
        if(usernameField.getText().trim().equalsIgnoreCase("enter your username")){
            usernameField.setText("");
        }
    }
}
