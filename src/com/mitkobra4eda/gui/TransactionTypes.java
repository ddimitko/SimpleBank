package com.mitkobra4eda.gui;

import java.sql.*;
import java.util.Random;

public enum TransactionTypes{
    DEPOSIT,
    WITHDRAW,
    TRANSFER;

    private static String gen;

    public static void Types(TransactionTypes type, double sum, String userid, String userid2){
        generateID();
        switch(type){
            case DEPOSIT:
                try{
                    Connection con = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/accounts",
                            "postgres",
                            "admin");
                    PreparedStatement pStatement = con.prepareStatement(
                            "UPDATE accounts SET wallet = wallet + ? WHERE userid = ?");
                    pStatement.setString(2, userid);
                    pStatement.setDouble(1, sum);
                    pStatement.executeUpdate();
                    pStatement.close();

                    PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions VALUES(?,?,?,?,?)");
                    stmt.setString(1, gen);
                    stmt.setString(2, userid);
                    stmt.setString(3, userid2);
                    stmt.setDouble(4, sum);
                    stmt.setString(5, "DEPOSIT");
                    stmt.executeUpdate();
                    stmt.close();

                    con.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case WITHDRAW:
                try{
                    Connection con = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/accounts",
                            "postgres",
                            "admin");
                    PreparedStatement pStatement = con.prepareStatement(
                            "UPDATE accounts SET wallet = wallet - ? WHERE userid = ?");
                    pStatement.setString(2, userid);
                    pStatement.setDouble(1, sum);
                    pStatement.executeUpdate();
                    pStatement.close();

                    PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions VALUES(?,?,?,?,?)");
                    stmt.setString(1, gen);
                    stmt.setString(2, userid);
                    stmt.setString(3, userid2);
                    stmt.setDouble(4, sum);
                    stmt.setString(5, "WITHDRAWAL");
                    stmt.executeUpdate();
                    stmt.close();

                    con.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case TRANSFER:
                try{
                    Connection con = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/accounts",
                            "postgres",
                            "admin");
                    PreparedStatement pStatement = con.prepareStatement(
                            "UPDATE accounts SET wallet = wallet - ? WHERE userid = ?;" +
                                    "UPDATE accounts SET wallet = wallet + ? WHERE userid = ?;");
                    pStatement.setString(2, userid);
                    pStatement.setDouble(1, sum);
                    pStatement.setDouble(3, sum);
                    pStatement.setString(4, userid2);
                    pStatement.executeUpdate();
                    pStatement.close();

                    PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions VALUES(?,?,?,?,?)");
                    stmt.setString(1, gen);
                    stmt.setString(2, userid);
                    stmt.setString(3, userid2);
                    stmt.setDouble(4, sum);
                    stmt.setString(5, "TRANSFER");
                    stmt.executeUpdate();
                    stmt.close();

                    con.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }
    }

    public static void generateID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 16;
        Random random = new Random();

        gen = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
