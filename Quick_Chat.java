/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quick_chat;

import java.util.Scanner;

/**
 *
 * @author Student
 */




/**
 * Quick_Chat.java
 * Main class for the QuickChat application.
 * Part 1: Handles user registration and login
 * Part 2: Handles sending and managing messages after login
 * @author Student
 */
public class Quick_Chat {

    public static void main(String[] args) {
        
        // Create Scanner object to read input from the keyboard
        Scanner scanner = new Scanner(System.in);
        
        // Create Login object to use registration and login methods from Login.java
        Login loginSystem = new Login();
        

        // ==================== PART 1: REGISTRATION ====================
        System.out.println("====REGISTER====");

        // Ask for first name and store it
        System.out.println("Enter First Name");
        String firstName = scanner.nextLine();

        // Ask for last name and store it
        System.out.println("Enter Last Name");
        String lastName = scanner.nextLine();

        // Ask for username and store it
        System.out.println("Enter Username ");
        String username = scanner.nextLine();

        // Ask for password and store it
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        // Ask for cell phone number. Must be in +27XXXXXXXXX format per rubric
        System.out.println("Enter Cell Phone Number (+27XXXXXXXXX)");
        String cellPhoneNumber = scanner.nextLine();

        // Call registerUser method from Login class. It validates and saves the user
        String registrationMessage = loginSystem.registerUser(username, password, firstName, lastName, cellPhoneNumber);
        System.out.println(registrationMessage); // Display success or error message

        // ==================== CHECK IF REGISTRATION WAS SUCCESSFUL ====================
        // Only continue to login and messaging if registration worked
        if (registrationMessage.contains("successfully")) {
            
            // ==================== LOGIN SECTION ====================
            System.out.println("\n=== Login ===");
            
            // Ask user to enter username again for login
            System.out.println("Enter Username: ");
            String loginUsername = scanner.nextLine();

            // Ask user to enter password again for login
            System.out.println("Enter Password: ");
            String loginPassword = scanner.nextLine();

            // Call loginUser method to check credentials. Returns true or false
            boolean loginSuccess = loginSystem.loginUser(loginUsername, loginPassword);
            
            // Call returnLoginStatus to get the welcome or error message
            System.out.println(loginSystem.returnLoginStatus(loginSuccess));

            // ==================== PART 2: MESSAGING SYSTEM ====================
            // Only run this if login was successful. If not, program ends here
            if (loginSuccess) {
                
                System.out.println("\nWelcome to QuickChat.");
                
                // Use an infinite loop to keep showing the menu until user chooses to quit
                while (true) {
                    
                    // Display the main menu options from the rubric
                    System.out.println("\n1. Send Messages");
                    System.out.println("2. Show recently sent messages - Coming Soon");
                    System.out.println("3. Quit");
                    System.out.print("Choose: ");
                    
                    // Read user's menu choice
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Clear the newline from buffer so nextLine() works later

                    // ==================== OPTION 1: SEND MESSAGES ====================
                    if (option == 1) {
                        
                        // Ask how many messages the user wants to send in this session
                        System.out.print("How many messages do you want to send? ");
                        int numMessages = scanner.nextInt();
                        scanner.nextLine(); // Clear buffer

                        // Loop runs once for each message the user wants to send
                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Message " + (i + 1) + " ---");

                            // Ask for recipient number
                            System.out.print("Enter Recipient Number (+27XXXXXXXXX): ");
                            String recipient = scanner.nextLine();
                            
                            // Create a temporary Message object just to check the cell number format
                            // We use CheckrecipientCell() method from Message.java
                            Message tempMsg = new Message(recipient, "");
                            String cellCheck = tempMsg.CheckrecipientCell();
                            System.out.println(cellCheck); // Print validation result
                            
                            // If the number is invalid, skip this message and go to the next one
                            if (!cellCheck.equals("Cell phone number successfully captured.")) {
                                continue;
                            }

                            // Ask user to type the actual message text
                            System.out.print("Enter Message: ");
                            String messageText = scanner.nextLine();
                            
                            // Create the real Message object with recipient and message text
                            Message msg = new Message(recipient, messageText);

                            // Check if message is 250 characters or less using checkMessage() method
                            // This method returns true if valid, false if too long
                            if (!msg.checkMessage()) {
                                System.out.println("Message exceeds 250 characters; please reduce the size.");
                                continue; // Skip to next message if too long
                            } else {
                                System.out.println("Message ready to send.");
                            }

                            // Ask user what action to take with this message
                            System.out.println("\n1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message");
                            System.out.print("Choose option: ");
                            int action = scanner.nextInt();
                            scanner.nextLine(); // Clear buffer

                            // Convert the number choice to a status string that setStatus() understands
                            String statusStr = action == 1 ?"Sent" : action == 3 ? "Disregarded" : "Stored" ;
                               
                            //calling message class method: setStatus
                            msg.setStatus(statusStr);
                            
                            //calling message class method : sentMessage
                            System.out.println(msg.SentMessage());
                            
                            // Process the message using SentMessage() method
                            // This method returns the message ID, hash, and status
                            System.out.println(msg.SentMessage());

                            // If message was Sent or Stored, display its details
                            // Disregarded messages are not displayed
                            if (action == 1 || action == 3) {
                                //calling message class method : printMessages
                                System.out.println("\n" + msg.printMessages());
                            }
                        } // End of for loop for sending multiple messages
                        
                        // After all messages are processed, display total messages sent
                        // We create a dummy Message object to call the non-static method
                        Message counter = new Message("", "");
                        System.out.println("\nTotal messages sent: " + counter.returnTotalMessages());

                    // ==================== OPTION 2: COMING SOON ====================
                    } else if (option == 2) {
                        System.out.println("Coming Soon.");
                    
                    // ==================== OPTION 3: QUIT ====================
                    } else if (option == 3) {
                        System.out.println("Goodbye!");
                        break; // Exit the while loop and end the program
                    
                    // ==================== INVALID OPTION ====================
                    } else {
                        System.out.println("Invalid option. Try again.");
                    }
                } // End of while loop
            } // End of if loginSuccess
        } else {
            // This runs if registration failed
            System.out.println("Registration failed. Please restart the program.");
        }

        // Close the scanner to prevent resource leak
        scanner.close();
    } // End of main method
} // End of Quick_Chat class