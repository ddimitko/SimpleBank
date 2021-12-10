package com.mitkobra4eda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static com.mitkobra4eda.gui.TransactionTypes.*;

public class MainScreen extends JFrame implements ActionListener, MouseListener {

    private Container c;
    private JPanel actionPanel;
    private JLabel name;
    private JLabel wallet;
    private JLabel profileIcon;
    private JLabel refresh;
    private JLabel settings;
    private JLabel userID;
    private JLabel deposit;
    private JLabel withdraw;
    private JLabel transfer;
    private JLabel history;
    private JLabel amount;
    private JButton button;
    private JFormattedTextField amountField;
    private JTextField targetUserID;
    private final JPopupMenu settingsMenu;
    private JMenuItem item1;
    private JMenuItem item2;
    private JMenuItem item3;
    private String sName;
    private Double sWallet;
    private String sUserID;

    public MainScreen(String uid) {
        setTitle("SimpleBank");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1050, 600);
        setLocationRelativeTo(null);

        c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.white);

        sUserID = uid;

        getProfileName();

        profileIcon = new JLabel();
        profileIcon.setIcon(new ImageIcon(new ImageIcon("Images/default.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        profileIcon.setSize(50, 50);
        profileIcon.setLocation(26, 28);
        c.add(profileIcon);

        name = new JLabel(sName);
        name.setFont(new Font("Arial", Font.BOLD, 16));
        name.setSize(300, 20);
        name.setLocation(85, 35);
        c.add(name);

        wallet = new JLabel();
        wallet.setFont(new Font("Arial", Font.PLAIN, 12));
        wallet.setSize(300, 20);
        wallet.setLocation(85, 52);
        c.add(wallet);

        getProfileWallet();

        refresh = new JLabel();
        refresh.setIcon(new ImageIcon(new ImageIcon("Images/refresh.png").getImage()
                .getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        refresh.setSize(16, 16);
        refresh.setLocation(13, 29);
        refresh.addMouseListener(this);
        c.add(refresh);

        settings = new JLabel();
        settings.setIcon(new ImageIcon(new ImageIcon("Images/settings.png").getImage()
                .getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        settings.setSize(25, 25);
        settings.setLocation(970, 45);
        settings.addMouseListener(this);
        c.add(settings);

        userID = new JLabel("Your User ID is: " + sUserID);
        userID.setFont(new Font("Arial", Font.BOLD, 16));
        userID.setSize(300, 20);
        userID.setLocation(100, 180);
        c.add(userID);

        settingsMenu = new JPopupMenu();
        item1 = new JMenuItem("Settings");
        item2 = new JMenuItem("Edit Profile");
        item3 = new JMenuItem("Sign out...");
        item3.addActionListener(this);
        settingsMenu.add(item1);
        settingsMenu.add(item2);
        settingsMenu.add(item3);

        deposit = new JLabel("Deposit");
        deposit.setFont(new Font("Arial", Font.BOLD, 20));
        deposit.setSize(200, 50);
        deposit.setLocation(120, 220);
        deposit.addMouseListener(this);
        c.add(deposit);

        withdraw = new JLabel("Withdraw");
        withdraw.setFont(new Font("Arial", Font.BOLD, 20));
        withdraw.setSize(200, 50);
        withdraw.setLocation(120, 260);
        withdraw.addMouseListener(this);
        c.add(withdraw);

        transfer = new JLabel("Transfer");
        transfer.setFont(new Font("Arial", Font.BOLD, 20));
        transfer.setSize(200, 50);
        transfer.setLocation(120, 300);
        transfer.addMouseListener(this);
        c.add(transfer);

        history = new JLabel("Transaction History");
        history.setFont(new Font("Arial", Font.BOLD, 20));
        history.setSize(200, 50);
        history.setLocation(120, 340);
        c.add(history);
        setVisible(true);

        actionPanel = new JPanel(null);
        actionPanel.setSize(450, 350);
        actionPanel.setLocation(525, 180);
        actionPanel.setBackground(Color.white);
        c.add(actionPanel);
        actionPanel.setVisible(false);

        amount = new JLabel();
        amount.setFont(new Font("Arial", Font.BOLD, 16));
        amount.setSize(180, 45);
        //amount.setLocation(550, 180);
        actionPanel.add(amount);
        amount.setVisible(true);


        amountField = new JFormattedTextField();
        amountField.setSize(100, 20);
        amountField.setLocation(0, 40);
        actionPanel.add(amountField);
        amountField.setVisible(true);

        targetUserID = new JTextField();
        targetUserID.setSize(100, 20);
        targetUserID.setLocation(0, 80);
        actionPanel.add(targetUserID);
        targetUserID.setVisible(false);

        button = new JButton();
        button.setSize(100, 20);
        button.setLocation(0, 65);
        actionPanel.add(button);
        button.setVisible(true);
        button.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            if(button.getText().equals("Withdraw")){
                if(Double.parseDouble(amountField.getText()) <= sWallet && Double.parseDouble(amountField.getText()) > 0) {
                    TransactionTypes.Types(WITHDRAW, Double.parseDouble(amountField.getText()), sUserID, null);
                    getProfileWallet();
                    wallet.revalidate();
                }
                else{
                    JOptionPane.showMessageDialog(null, "You don't have enough money in your wallet!");
                }
            }
            else if(button.getText().equals("Deposit")){
                if(Double.parseDouble(amountField.getText()) > 0) {
                    TransactionTypes.Types(DEPOSIT, Double.parseDouble(amountField.getText()), sUserID, null);
                    getProfileWallet();
                    wallet.revalidate();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please, enter a positive value!");
                }
            }
            else if(button.getText().equals("Transfer")){
                if(!targetUserID.getText().isEmpty() && !amountField.getText().isEmpty()) {
                    if(Double.parseDouble(amountField.getText()) <= sWallet && Double.parseDouble(amountField.getText()) > 0) {
                        TransactionTypes.Types(TRANSFER, Double.parseDouble(amountField.getText()), sUserID,
                                targetUserID.getText());
                        getProfileWallet();
                        wallet.revalidate();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "You don't have enough money in your wallet!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Fields must not be empty!");
                }
            }
            else if(button.getText().equals("Transaction History")){

            }
        }
        if(e.getSource() == item3){
            dispose();
            new SignInScreen();
        }
    }

    public void getProfileName(){
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/accounts",
                    "postgres",
                    "admin");
            PreparedStatement Pstatement = con.prepareStatement(
                    "SELECT name FROM accounts WHERE userid = ?");
            Pstatement.setString(1, sUserID);
            ResultSet rs = Pstatement.executeQuery();
            rs.next();
            sName = rs.getString(1);

            System.out.println(sName);

            rs.close();
            Pstatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getProfileWallet(){
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/accounts",
                    "postgres",
                    "admin");
            PreparedStatement Pstatement = con.prepareStatement(
                    "SELECT wallet FROM accounts WHERE userid = ?");
            Pstatement.setString(1, sUserID);
            ResultSet rs = Pstatement.executeQuery();
            rs.next();
            sWallet = rs.getDouble(1);

            wallet.setText("Available in Wallet: " + "$" + sWallet);

            System.out.println(sWallet);

            rs.close();
            Pstatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 1 && e.getSource() == settings){
            settingsMenu.show(e.getComponent(), -50, 30);
        }

        if(e.getButton() == 1 && e.getSource() == refresh){
            getProfileWallet();
            wallet.revalidate();
        }

        if(e.getButton() == 1 && e.getSource() == withdraw){
            amountField.setText("");
            amount.setText("Amount to Withdraw:");
            button.setLocation(0, 65);
            button.setText("Withdraw");
            actionPanel.setVisible(true);
            targetUserID.setVisible(false);
        }
        if(e.getButton() == 1 && e.getSource() == deposit){
            amountField.setText("");
            amount.setText("Amount to Deposit:");
            button.setLocation(0, 65);
            button.setText("Deposit");
            actionPanel.setVisible(true);
            targetUserID.setVisible(false);
        }
        if(e.getButton() == 1 && e.getSource() == transfer) {
            amountField.setText("");
            amount.setText("Amount to Transfer:");
            button.setLocation(0, 105);
            button.setText("Transfer");
            actionPanel.setVisible(true);
            targetUserID.setVisible(true);
        }
        if(e.getButton() == 1 && e.getSource() == history){
            //TODO
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
