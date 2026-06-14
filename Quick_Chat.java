/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quick_chat;


/**
 *
 * @author Student
 */
 
               
import java.util.Scanner;

/**
 * ========================================================================
 * MAIN CLASS - PROG5121 POE
 * ========================================================================
 * PURPOSE:
 * This class handles ALL user interaction only.
 * All business logic (validation, storage, search, delete) is handled
 * inside the Message class.
 *
 * RESPONSIBILITIES:
 * - User registration
 * - User login
 * - Main menu navigation
 * - Calling methods from Message class
 * ========================================================================
 */
public class Quick_Chat {

    public static void main(String[] args) {

        // Scanner object used to capture user input from keyboard
        Scanner inputScanner = new Scanner(System.in);

        // Login object handles registration and authentication
        Login loginObject = new Login();

        // ================= REGISTRATION =================
        System.out.println("========== REGISTRATION ==========");

        // Capture user personal details
        System.out.print("Enter First Name: ");
        String firstName = inputScanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = inputScanner.nextLine();

        System.out.print("Enter Username: ");
        String username = inputScanner.nextLine();

        System.out.print("Enter Password: ");
        String password = inputScanner.nextLine();

        System.out.print("Enter Cell Number: ");
        String cellNumber = inputScanner.nextLine();

        // Register user using Login class method
        
        String regResult = loginObject.registerUser(
        username,
        password,
        firstName,
        lastName,
        cellNumber);
        // Display registration result message
        System.out.println(regResult);

        // ================= LOGIN =================
        System.out.println("\n========== LOGIN ==========");

        // Capture login credentials
        System.out.print("Enter Username: ");
        String loginUsername = inputScanner.nextLine();

        System.out.print("Enter Password: ");
        String loginPassword = inputScanner.nextLine();

        // Validate login details
        boolean loginStatus = loginObject.loginUser(loginUsername, loginPassword);

        // Display login result message
        System.out.println(loginObject.returnLoginStatus(loginStatus));

        // Stop program if login fails
        if (!loginStatus) {
            System.out.println("Exiting application...");
            return;
        }

        // Load stored messages from JSON file into array
        Message.readJSONToArray();

        // ================= MAIN MENU =================
        int menuOption;

        do {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Stored Messages Menu");
            System.out.println("4. Display Total Messages Sent");
            System.out.println("5. Quit");
            System.out.print("Select option: ");

            menuOption = inputScanner.nextInt();
            inputScanner.nextLine();

            switch (menuOption) {

                // ================= SEND MESSAGE =================
                case 1:
                    System.out.print("How many messages? ");
                    int count = inputScanner.nextInt();
                    inputScanner.nextLine();

                    for (int i = 0; i < count; i++) {

                        System.out.println("\n--- Message " + (i + 1) + " ---");

                        System.out.print("Message ID: ");
                        String id = inputScanner.nextLine();

                        System.out.print("Recipient Cell: ");
                        String cell = inputScanner.nextLine();

                        System.out.print("Message Content: ");
                        String msg = inputScanner.nextLine();

                        System.out.print("Flag (Sent / Stored / Disregard): ");
                        String flag = inputScanner.nextLine();

                        // Send data to Message class for processing
                        Message.addMessage(id, cell, msg, flag);
                    }
                    break;

                // ================= DISREGARD =================
                case 2:
                    System.out.println("Use Flag = Disregard when sending message.");
                    break;

                // ================= STORED MESSAGES =================
                case 3:
                    displayStoredMessageMenu(inputScanner);
                    break;

                // ================= TOTAL MESSAGES =================
                case 4:
                    System.out.println(
                            "Total Messages Sent: " + Message.returnTotalMessagess()
                    );
                    break;

                // ================= EXIT =================
                case 5:
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (menuOption != 5);

        // Close scanner to prevent memory leaks
        inputScanner.close();
    }

    // ================= STORED MESSAGE SUBMENU =================
    public static void displayStoredMessageMenu(Scanner inputScanner) {

        int option;

        do {
            System.out.println("\n========== STORED MESSAGES MENU ==========");
            System.out.println("1. Display All Recipients and Messages");
            System.out.println("2. Display Longest Message");
            System.out.println("3. Search by Message ID");
            System.out.println("4. Search by Recipient Cell");
            System.out.println("5. Delete Message by Hash");
            System.out.println("6. Display Full Message Report");
            System.out.println("7. Return to Main Menu");
            System.out.print("Please select an option: ");

            option = inputScanner.nextInt();
            inputScanner.nextLine();

            switch (option) {

                case 1:
                    Message.displaySenderAndRecipient();
                    break;

                case 2:
                    Message.displayLongestMessage();
                    break;

                case 3:
                    System.out.print("Enter Message ID: ");
                    Message.searchByMessageId(inputScanner.nextLine());
                    break;

                case 4:
                    System.out.print("Enter Recipient Cell: ");
                    Message.searchByRecipient(inputScanner.nextLine());
                    break;

                case 5:
                    System.out.print("Enter Message Hash: ");
                    Message.deleteByHash(inputScanner.nextLine());
                    break;

                case 6:
                    Message.displayFullReport();
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Invalid option selected.");
            }

        } while (option != 7);
    }
}



