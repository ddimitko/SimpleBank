package com.mitkobra4eda;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Account {
    String userName;
    String userID;
    double currentBalance;

    public Account(){
        Scanner myObj = new Scanner (System.in);

        System.out.println("Welcome to Simple Bank! Let's create your first account!");
        System.out.println("What is your name?");
        String name = myObj.nextLine();
        userName = name;
        generateID();
        System.out.println();
        /*System.out.println("Hello, " + name + "!" + "\nWhat is your UserID?");
        String uid = myObj.nextLine();
        userID = uid;*/
        System.out.println();
        System.out.println("Great! Now, how much money do you want to deposit for your starting balance?");
        double balance = myObj.nextDouble();
        currentBalance = balance;
        System.out.println();
        System.out.println("Username: " + name/* + "\nUserID: " + uid*/);
        System.out.println("Current Balance: " + balance + "$");
        System.out.println();
    }
    public void generateID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String gen = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        userID = gen;
    }


    public void checkAccInfo(){
        System.out.println("Your Username is: " + userName + "." + "\nYour User ID is: " + userID + "." + "\nYour Balance is: " + currentBalance + "$.");
    }

    public void deposit(){
        Scanner menu = new Scanner (System.in);
        System.out.println("What amount would you like to deposit?");
        double depositAmount = menu.nextDouble();
        currentBalance += depositAmount;
        System.out.println();
        System.out.println("Successful deposit!");
        System.out.println("Your new balance is: " + currentBalance + "$");
    }

    public void withdraw(){
        Scanner menu = new Scanner (System.in);
        System.out.println("What amount would you like to deposit?");
        double withdrawAmount = menu.nextDouble();
        if(currentBalance > withdrawAmount) {
            currentBalance -= withdrawAmount;
            System.out.println("Successful withdrawal!");
            System.out.println("Your new balance is: " + currentBalance + "$");
        }
        else {
            System.out.println("You don't have sufficient funds to make this transaction!");
            System.out.println("Desired amount to withdraw: " + withdrawAmount + "$");
            System.out.println("Current balance: " + currentBalance + "$");
        }
    }


    public void mainMenu(){
        Scanner menu = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println();
            System.out.println("What are you going to do today?");
            System.out.println();
            System.out.println("Option 1: Check your current Balance and Account info.");
            System.out.println("Option 2: Make a Deposit.");
            System.out.println("Option 3: Withdraw money.");
            System.out.println("Option 4: Exit.");
            try {
                int choice = menu.nextInt();
                switch (choice) {
                    case 1:
                        checkAccInfo();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Have a nice day!");
                        break;
                    default:
                        System.out.println("Please, pick an option from the list of options!");
                }
            }catch(InputMismatchException e){
                System.out.println("You MUST insert a number!");
                menu.next();
            }
        }
    }
}

