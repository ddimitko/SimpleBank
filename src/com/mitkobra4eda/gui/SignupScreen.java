package com.mitkobra4eda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class SignupScreen extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel name;
    private JLabel email;
    private JLabel pass;
    private JLabel confPass;
    private JTextField tName;
    private JTextField tEmail;
    private JPasswordField tPass;
    private JPasswordField tConfPass;
    private JButton newAccountButton;
    private JButton back;
    private String gen;

    public SignupScreen() {
        setTitle("SimpleBank");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1050, 600);
        setLocationRelativeTo(null);


        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("New Account creation");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(380, 30);
        c.add(title);

        name = new JLabel("First Name:");
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setSize(100, 20);
        name.setLocation(400, 150);
        c.add(name);

        tName = new JTextField();
        tName.setFont(new Font("Arial", Font.PLAIN, 12));
        tName.setSize(115, 20);
        tName.setLocation(525, 150);
        c.add(tName);

        email = new JLabel("Email:");
        email.setFont(new Font("Arial", Font.PLAIN, 15));
        email.setSize(100, 20);
        email.setLocation(400, 185);
        c.add(email);

        tEmail = new JTextField();
        tEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        tEmail.setSize(115, 20);
        tEmail.setLocation(525, 185);
        c.add(tEmail);

        pass = new JLabel("Password:");
        pass.setFont(new Font("Arial", Font.PLAIN, 15));
        pass.setSize(115, 20);
        pass.setLocation(400, 255);
        c.add(pass);

        tPass = new JPasswordField();
        tPass.setFont(new Font("Arial", Font.PLAIN, 12));
        tPass.setSize(115, 20);
        tPass.setLocation(525, 255);
        c.add(tPass);

        confPass = new JLabel("Confirm:");
        confPass.setFont(new Font("Arial", Font.PLAIN, 15));
        confPass.setSize(115, 20);
        confPass.setLocation(400, 290);
        c.add(confPass);

        tConfPass = new JPasswordField();
        tConfPass.setFont(new Font("Arial", Font.PLAIN, 12));
        tConfPass.setSize(115, 20);
        tConfPass.setLocation(525, 290);
        c.add(tConfPass);

        newAccountButton = new JButton("Create a New Account");
        newAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        newAccountButton.setSize(240, 21);
        newAccountButton.setLocation(400, 350);
        newAccountButton.addActionListener(this);
        c.add(newAccountButton);

        back = new JButton("Back to Sign In Screen");
        back.setFont(new Font("Arial", Font.PLAIN, 14));
        back.setSize(240, 21);
        back.setLocation(400, 380);
        back.addActionListener(this);
        c.add(back);
        setVisible(true);
    }

    public void generateID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        gen = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newAccountButton){
            try{
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/accounts",
                        "postgres",
                        "admin");
                PreparedStatement Pstatement = connection.prepareStatement("INSERT INTO accounts VALUES(?,?,?,?)");
                Pstatement.setString(2, tEmail.getText());
                Pstatement.setString(3, tName.getText());
                Pstatement.setString(4, tPass.getText());
                PreparedStatement check = connection.prepareStatement("SELECT * FROM accounts WHERE email = ?");
                check.setString(1, tEmail.getText());
                ResultSet rs = check.executeQuery();
                if(!rs.next()) {
                    if (tPass.getText().equals(tConfPass.getText())) {
                        generateID();
                        Pstatement.setString(1, gen);
                        Pstatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Successful account creation!");
                        dispose();
                        new SignInScreen();
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords do not match!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "An account with that email already exists!");
                }
            }catch(SQLException e1){
                e1.printStackTrace();
            }

        }
        if(e.getSource() == back){
            dispose();
            new SignInScreen();
        }
    }
}
