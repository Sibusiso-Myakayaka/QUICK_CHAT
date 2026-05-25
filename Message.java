package com.mycompany.quick_chat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Random;
import java.io.FileWriter;
import org.json.JSONObject;

/**
 *
 * @author Student
 */
public class Message {
   

    // Static variables keep their value across all Message objects
    // messageCount is used to generate unique hashes
    // totalSent tracks only messages that were actually sent
    public static int messageCount = 0;
    public static int totalSent = 0;

    // Instance variables store data for each individual message
     String messageID; // Unique 10-digit ID for each message
     String recipient; // Recipient phone number
     String messageText; // The actual message content
     String messageHash; // Generated hash for the message
     String status; // Status: Sent, Stored, or Disregarded

    /**
     * Constructor: Called when creating a new message
     * Automatically generates ID and hash when object is created
     * @param recipient - phone number of recipient
     * @param messageText - content of the message
     */
     

   public Message(String recipient, String messageText) {
    this.recipient = recipient;
    this.messageText = messageText;

    this.messageID = generateMessageID();
    messageCount++;

    // SAFE now because messageText is already assigned
    this.messageHash = createMessageHash();
}

    /**
     * Internal method to generate a random 10-digit numeric ID
     * Uses Random class to ensure each message has a unique ID
     * @return String containing 10 digits
     */
    
    
     public String generateMessageID() {
    Random rand = new Random();
    long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
    return String.valueOf(id);
}
    /**
     * checkMessage()
     * Checks if the message is 250 characters or less
     * This is required by the rubric and must return a boolean
     * @return true if message is valid length, false if too long
     */
    public boolean checkMessage() {
        return messageText.length() <= 250;
    }

    /**
     * CheckrecipientCell()
     * Validates the recipient's cell phone number format
     * Must start with +27 or 27 followed by 9 digits
     * Returns exact strings as required by the marking rubric
     * @return Success or error message string
     */
    public String CheckrecipientCell() {
        // Regex: [\\+27]\\+27 or 27 followed by exactly 9 digits 0-9
        if (recipient.matches("(\\+27|27)[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }

    /**
     * createMessageHash()
     * Creates a unique hash for each message
     * Format: XX:count:FIRST2WORDSLASTWORD
     * XX = first 2 digits of MessageID
     * count = number of messages created so far
     * Words are converted to uppercase and spaces removed
     * Example: ID "1234567890", message "Hi Mike", count 0 -> "12:0:HIMIKE"
     * @return String containing the generated hash
     */
    
    
  
    public String createMessageHash() {

    if (messageText == null || messageText.trim().isEmpty()) {
        return "00:0:EMPTY";
    }

    String[] words = messageText.trim().split("\\s+");

    String firstTwo = "";
    String lastWord = "";

    if (words.length >= 2) {
        firstTwo = (words[0].charAt(0) + "" + words[1].charAt(0)).toUpperCase();
        lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");
    } else {
        firstTwo = words[0].substring(0, 1).toUpperCase();
        lastWord = words[0].toUpperCase().replaceAll("[^A-Z]", "");
    }

    String firstTwoDigits = messageID.substring(0, 2);

    return firstTwoDigits + ":" + messageCount + ":" + firstTwo + lastWord;
}
    /**
     * SentMessage()
     * Processes the user's choice for what to do with the message
     * Updates counters and status based on choice
     * Note: You must call setStatus() before calling this method
     * @return Response message for user feedback
     */
    public String SentMessage() {
        if ("Sent".equals(status)) {
            totalSent++; // Only increment sent count for sent messages
            messageCount++; // Increment total count for hash generation
            return "Message successfully sent.";
        } else if ("Disregarded".equals(status)) {
            return "Press 0 to delete the message.";
        } else if ("Stored".equals(status)) {
            storeMessage(); // Save message to JSON file
            
            return "Message successfully stored.";
        }
        return "Invalid choice.";
    }

    /**
     * printMessages()
     * Formats and returns all message details for display
     * Used when a message is sent or stored
     * @return Formatted string with ID, Hash, Recipient, and Message
     */
    public String printMessages() {
        return "MessageID: " + messageID + "\n" +
               "MessageHash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    /**
     * returnTotalMessages()
     * Returns the total number of messages that were actually sent
     * Method name must match rubric exactly
     * @return int count of sent messages
     */
    public static int returnTotalMessages() {
        return totalSent;
    }

    /**
     * json()
     * Converts message data into a JSON string format
     * Used for storing messages to the messages.json file
     * @return JSON string representation of the message
     */
    public String json() {
        JSONObject json = new JSONObject();
        json.put("MessageID", messageID);
        json.put("MessageHash", messageHash);
        json.put("Recipient", recipient);
        json.put("Message", messageText);
        json.put("Status", status);
        return json.toString();
    }

    /**
     * setStatus()
     * Helper method to set the status before calling SentMessage()
     * Not in the rubric but required for the logic to work
     * @param status - "Sent", "Stored", or "Disregarded"
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * storeMessage()
     * Private helper method to write message JSON to messages.json file
     * Uses try-with-resources to auto-close the file after writing
     * 'true' flag enables appending to file instead of overwriting
     */
    private void storeMessage() {
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json() + "\n"); // Add newline for readability
        } catch (Exception e) {
            e.printStackTrace(); // Print error if file write fails
        }
    }

      

         public String getMessageID() {
         return messageID;
        }

        public String getMessageHash() {
        return messageHash;
        }

       public String getRecipient() {
       return recipient;
       }

       public String getMessageText() {
       return messageText;
     }

       public String getStatus() {
    return status;
}}
    /*boolean checkMessage(){
        return false;
    }
    String CheckrecipientCell(){
        
    }
    String createMessageHash(){
        
    }
    String SentMessage(){
        
    }
    String printMessages(){
        
    }
    src/test/java/com/mycompany/quick_chat/LoginTest.java
src/test/java/com/mycompany/quick_chat/MessageTest.java
    int returnTotalMessages(){
        
    }
    String json(){
        
    }*/

