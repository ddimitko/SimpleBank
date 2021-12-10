package com.mitkobra4eda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignInScreen extends JFrame implements ActionListener{

    private Container c;
    private JLabel title;
    private JLabel email;
    private JTextField tEmail;
    private JLabel pass;
    private JPasswordField tPass;
    private JButton signIn;
    private JButton signUp;

    public SignInScreen(){
        setTitle("SimpleBank");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1050, 600);
        setLocationRelativeTo(null);


        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Welcome! Please, Sign in to continue!");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setSize(500, 35);
        title.setLocation(300, 100);
        c.add(title);

        email = new JLabel("E-Mail:");
        email.setFont(new Font("Arial", Font.PLAIN, 15));
        email.setSize(100, 20);
        email.setLocation(430, 225);
        c.add(email);

        tEmail = new JTextField();
        tEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        tEmail.setSize(115, 20);
        tEmail.setLocation(525, 225);
        c.add(tEmail);

        pass = new JLabel("Password:");
        pass.setFont(new Font("Arial", Font.PLAIN, 15));
        pass.setSize(100, 20);
        pass.setLocation(430, 265);
        c.add(pass);

        tPass = new JPasswordField();
        tPass.setFont(new Font("Arial", Font.PLAIN, 12));
        tPass.setSize(115, 20);
        tPass.setLocation(525, 265);
        c.add(tPass);

        signIn = new JButton("Sign In");
        signIn.setFont(new Font("Arial", Font.PLAIN, 14));
        signIn.setSize(225, 21);
        signIn.setLocation(420, 325);
        signIn.addActionListener(this);
        c.add(signIn);

        signUp = new JButton("Create a New Account");
        signUp.setFont(new Font("Arial", Font.BOLD, 14));
        signUp.setSize(225, 21);
        signUp.setLocation(420, 355);
        signUp.addActionListener(this);
        c.add(signUp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signUp){
            dispose();
            new SignupScreen();
        }
        if(e.getSource() == signIn){
            try{
                Connection conn = DriverManager.getConnection("jdbc:postgresql://109.104.206.19:5432/accounts",
                        "postgres",
                        "admin");
                PreparedStatement check = conn.prepareStatement("SELECT userid FROM accounts WHERE email = ? AND password = ?");
                check.setString(1, tEmail.getText());
                check.setString(2, tPass.getText());
                ResultSet rs = check.executeQuery();
                if(rs.next()){
                    String userID = rs.getString(1);
                    dispose();
                    new MainScreen(userID);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wrong Email/Password. Please, Check your Credentials!");
                }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "CONNECTION ERROR!\nServer may be offline.");
                ex.printStackTrace();
            }
        }
    }
}
