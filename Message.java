/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quick_chat;



import org.json.JSONArray; // Import library to create JSON arrays - stores multiple messages
import org.json.JSONObject; // Import library to create JSON objects - stores one message
import java.io.FileReader; // Import to READ files from computer - used for Part 3 JSON read
import java.io.FileWriter; // Import to WRITE files to computer - used for Part 3 JSON save

/**
 *
 * @author lerat
 */


/**
 * ============================================================================
 * FILE: Message.java
 * MODULE: PROG5121 POE Part 1 + Part 2 + Part 3 Combined
 * PURPOSE: Handles all message operations: validation, storage, search, delete, report
 *
 * PART 1 METHODS: checkMessageID, checkRecipientCell - validation
 * PART 2 METHODS: createMessageHash, SentMessage, Arrays, JSON, Search, Delete
 * PART 3 METHODS: 5 Arrays + readJSONToArray + submenu a-f + Full Report
 *
 * RUBRIC TOTAL: 100 marks Part 3 + previous parts
 * KEY REQUIREMENTS FROM YOUR SCREENSHOTS:
 * 1. 5 parallel arrays populated with no hard-coding
 * 2. Menu option 4: Stored Messages with submenu a-f
 * 3. Read messages.json into Stored Messages array
 * 4. Test data Message 1-5 must work
 * ============================================================================
 */
public class Message {

    // ==================== PART 3 REQUIREMENT 1: 5 PARALLEL ARRAYS ====================
    // 'static' = shared data - no need to create Message object, call Message.method()
    // 'int max = 100' = maximum capacity for all arrays. Can store 100 messages max

    public static int  max  = 100 ; // Maximum size for all arrays

    // ARRAY 1: sentMessageArray - stores all message content/text
    // Type: String array because messages are text
    // Size: max = 100 elements, index 0 to 99
     static String[] sentMessageArray = new String[max];

    // ARRAY 2: disregardedMessageArray - stores messages user chose to disregard
     static String[] disregardedMessageArray = new String[max];

    // ARRAY 3: storedMessageArray - stores messages read from JSON file
     static String[] storedMessageArray = new String[max];

    // ARRAY 4: messageHashArray - stores all generated message hashes
     static String[] messageHashArray = new String[max];

    // ARRAY 5: messageIdArray - stores all message IDs entered by user
     static String[] messageIdArray = new String[max];

    // PARALLEL ARRAY: recipientCellArray - stores recipient for each message
    // Needed because we must display "Sender and Recipient" in submenu option a
     static String[] recipientCellArray = new String[max];

    // ==================== COUNTER VARIABLES ====================
    // Counters track how many items are currently stored in each array
    // Also used as the index for the next empty position in array

    public static int sentCounter = 0; // Tracks number of sent messages in sentMessageArray
    public static int disregardedCounter = 0; // Tracks number in disregardedMessageArray
    public static int storedCounter = 0; // Tracks number read from JSON into storedMessageArray


    // ==================== PART 2: VALIDATION METHOD 1 ====================
    /**
     * METHOD: checkMessageID
     * RETURN: boolean true/false
     * PURPOSE: Validate message ID is not more than 10 characters
     * RUBRIC: Test AssertEquals for message ID
     * SCREENSHOT: "The message ID is not more than ten characters"
     */
    public static boolean checkMessageID(String messageId) {
        // Use.length() method on String to count characters
        // Compare result to 10 using <= less-than-or-equal operator
        // Returns true if 10 or less, false if 11 or more
        return messageId.length() <= 10;
    }

    // ==================== PART 2: VALIDATION METHOD 2 ====================
    /**
     * METHOD: checkRecipientCell
     * RETURN: String - exact messages from your screenshot
     * PURPOSE: Validate recipient cell number format
     * SCREENSHOT: Must return 2 exact strings for success/failure
     */
    public static String checkRecipientCell(String cell) {
        // Condition 1: cell.startsWith("+") checks first character is plus sign
        // Condition 2: cell.length() >=10 checks minimum length
        // Condition 3: cell.length() <=13 checks maximum length
        // All conditions joined with AND '&&' operator
        if (cell.startsWith("+") && cell.length() >= 10 && cell.length() <= 13) {
            // Return SUCCESS message EXACTLY as shown in your screenshot
            return "Cell phone number successfully captured.";
        } else {
            // Return FAILURE message EXACTLY as shown in your screenshot
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // ==================== PART 2: MESSAGE HASH CREATION ====================
    /**
     * METHOD: createMessageHash
     * RETURN: String - formatted hash code
     * PURPOSE: Generate unique hash for each message using string manipulation
     * FORMAT: XX:0:FIRSTWORDLASTWORD - e.g. "00:0:HITONIGHT"
     * RUBRIC: Message Hash 4-5 marks - "created using string manipulation and loop counters"
     * SCREENSHOT TEST CASE 1: Must return "00:0:HITONIGHT"
     */
    public static String createMessageHash(String messageId, int counter, String messageContent) {
        // STEP 1: Extract first 2 characters from messageId
        // substring(0, 2) means: start at index 0, end before index 2
        // Math.min prevents error if ID is only 1 character long
        // toUpperCase() converts to capital letters as per screenshot format
        String idPart = messageId.substring(0, Math.min(2, messageId.length())).toUpperCase();

        // STEP 2: Split message into individual words using space as delimiter
        // "Hi Mike" becomes array ["Hi", "Mike"]
        // split(" ") means split wherever there is a space character
        String[] words = messageContent.split(" ");

        // STEP 3: Get first word from words array at index 0
        // Check if array has elements first to avoid error: words.length > 0
        // If empty, use "NA" as backup value
        String firstWord = words.length > 0? words[0] : "NA";

        // STEP 4: Get last word using last index = array.length - 1
        // Example: array of 5 elements has last index 4
        String lastWord = words.length > 0? words[words.length-1] : "NA";

        // STEP 5: Build final hash by joining parts with colon ':' separator
        // Format: idPart + ":" + counter + ":" + firstWord + lastWord
        // All converted to uppercase for consistency with screenshot
        return idPart + ":" + counter + ":" + firstWord.toUpperCase() + lastWord.toUpperCase();
    }

    // ==================== PART 2: SENT MESSAGE STATUS MESSAGES ====================
    /**
     * METHOD: SentMessage
     * RETURN: String - exact output strings from screenshot
     * PURPOSE: Return specific message based on user action choice
     * SCREENSHOT REQUIREMENTS:
     * 1) "Message successfully sent."
     * 2) "Message successfully stored."
     * 3) "Press 0 to delete the message."
     */
    public static String SentMessage(String action) {
        // equalsIgnoreCase allows "send", "Send", "SEND" to all work
        if (action.equalsIgnoreCase("Send")) {
            // Return exact string from screenshot for sending action
            return "Message successfully sent.";
        } else if (action.equalsIgnoreCase("Store")) {
            // Return exact string from screenshot for storing action
            return "Message successfully stored.";
        } else if (action.equals("0")) {
            // Return exact string from screenshot for delete prompt
            return "Press 0 to delete the message.";
        } else {
            // Backup message if user enters invalid action
            return "Invalid choice.";
        }
    }

    // ==================== PART 2 + 3: ADD MESSAGE TO ARRAYS ====================
    /**
     * METHOD: addMessage
     * RETURN: void - does not return anything, just stores data
     * PURPOSE: Validates input then stores in appropriate array based on flag
     * FLAG OPTIONS: "Sent", "Stored", "Disregard" - from your test data screenshots
     * RUBRIC: Arrays correctly populated 8-10 marks - "no hard-coding"
     */
    public static void addMessage(String messageId, String recipientCell, String messageContent, String flag) {
        // VALIDATION STEP 1: Check message ID length using checkMessageID method
        // '!' means NOT - if NOT valid, print error and exit method early with 'return'
        if (!checkMessageID(messageId)) {
            System.out.println("Message ID exceeds 10 characters. Please reduce the size.");
            return; // Exit method immediately - do not store invalid message
        }

        // VALIDATION STEP 2: Check recipient cell format using checkRecipientCell method
        String cellCheck = checkRecipientCell(recipientCell); // Get validation result
        System.out.println(cellCheck); // Display validation message to user
        // If result does NOT contain "successfully captured", validation failed
        if (!cellCheck.contains("successfully captured")) {
            return; // Exit method - do not store invalid cell number
        }

        // STORAGE STEP: Check flag to decide which array to use
        // If flag is "Sent" or "Stored", store in sent arrays
        if (flag.equalsIgnoreCase("Sent") || flag.equalsIgnoreCase("Stored")) {
            // Check if sent array has space left: sentCounter must be less than max
            if (sentCounter < max) {
                // Store data in ALL parallel arrays at same index = sentCounter
                // This keeps data related: index 0 of all arrays = Message 1
                messageIdArray[sentCounter] = messageId; // Store ID in Array 5
                recipientCellArray[sentCounter] = recipientCell; // Store recipient
                sentMessageArray[sentCounter] = messageContent; // Store message in Array 1
                messageHashArray[sentCounter] = createMessageHash(messageId, sentCounter, messageContent); // Store hash in Array 4

                // Display success messages using SentMessage method
                System.out.println(SentMessage("Send")); // "Message successfully sent."
                if (flag.equalsIgnoreCase("Stored")) {
                    System.out.println(SentMessage("Store")); // "Message successfully stored."
                }
                System.out.println(SentMessage("0")); // "Press 0 to delete..."

                sentCounter++; // Increment counter for next message position
                storeToJSON(); // Save all sent messages to physical JSON file
            } else {
                // Array is full, cannot add more messages
                System.out.println("Message storage is full. Maximum " + max + " messages allowed.");
            }
        }
        // If flag is "Disregard", store in disregarded array instead
        else if (flag.equalsIgnoreCase("Disregard")) {
            if (disregardedCounter < max) {
                disregardedMessageArray[disregardedCounter] = messageContent; // Store in Array 2
                System.out.println(SentMessage("0")); // Show delete option
                disregardedCounter++; // Move counter forward
            }
        }
    }

    // ==================== PART 3: SAVE ALL MESSAGES TO JSON FILE ====================
    /**
     * METHOD: storeToJSON
     * RETURN: void
     * PURPOSE: Write all sent messages to messages.json file on computer
     * RUBRIC: "Research how to create a method to store the messages in JSON" 8-10 marks
     * OUTPUT FILE: messages.json created in project folder
     */
    public static void storeToJSON() {
        try {
            // STEP 1: Create JSONArray to hold multiple JSONObject message objects
            JSONArray jsonArray = new JSONArray();

            // STEP 2: Loop through all sent messages from index 0 to sentCounter-1
            for (int i = 0; i < sentCounter; i++) {
                // Create JSONObject for one single message
                JSONObject obj = new JSONObject();
                obj.put("MessageID", messageIdArray[i]); // Key="MessageID", Value=ID
                obj.put("Recipient", recipientCellArray[i]); // Key="Recipient", Value=cell
                obj.put("Message", sentMessageArray[i]); // Key="Message", Value=text
                obj.put("MessageHash", messageHashArray[i]); // Key="MessageHash", Value=hash
                jsonArray.put(obj); // Add this message object to the array
            }

            // STEP 3: Create FileWriter to write text to file
            FileWriter file = new FileWriter("messages.json"); // File name
            file.write(jsonArray.toString(4)); // Convert JSON to string with 4-space indentation
            file.close(); // IMPORTANT: Always close file after writing to save changes
            System.out.println("Message successfully stored."); // Confirm to user
        } catch (Exception e) {
            // If error occurs like file permission denied, catch and display error
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    // ==================== PART 3 REQUIREMENT: READ JSON INTO ARRAY ====================
    /**
     * METHOD: readJSONToArray
     * RETURN: void
     * PURPOSE: Read messages.json file and populate Stored Messages array
     * RUBRIC: "Read the JSON file you stored into an array" 8-10 marks
     * SCREENSHOT: Array 3 "Stored Messages" must be populated from file
     */
    public static void readJSONToArray() {
        try {
            // STEP 1: Create FileReader to read characters from messages.json file
            FileReader reader = new FileReader("messages.json");

            // STEP 2: Use StringBuilder to build complete JSON string character by character
            StringBuilder jsonContent = new StringBuilder();
            int ch; // Variable to hold each character read
            // Loop until read() returns -1 which means end of file
            while ((ch = reader.read())!= -1) {
                jsonContent.append((char) ch); // Convert int to char and add to string
            }
            reader.close(); // Close file after reading

            // STEP 3: Convert string to JSONArray object for easy processing
            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            storedCounter = 0; // Reset counter before populating array

            // STEP 4: Loop through each JSONObject in the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i); // Get message object at index i
                storedMessageArray[storedCounter] = obj.getString("Message"); // Extract "Message" value into Array 3
                storedCounter++; // Move to next position
            }
            System.out.println("JSON file read successfully. " + storedCounter + " messages loaded into Stored Messages array.");
        } catch (Exception e) {
            // If file doesn't exist yet, display helpful message
            System.out.println("JSON file not found. Send messages first.");
        }
    }

    // ==================== PART 3 REQUIREMENT 2: SUBMENU A-F ====================
    /**
     * METHOD: displayStoredMessageMenu
     * PURPOSE: Display submenu with options a-f as per your screenshot
     * RUBRIC: "Create a fourth main menu option for Stored Messages"
     * OPTIONS: a.display sender/recipient, b.longest, c.search ID, d.search recipient, e.delete hash, f.full report
     */
    
    // ==================== SUBMENU OPTION A ====================
    /**
     * METHOD: displaySenderAndRecipient
     * PURPOSE: Display all senders and recipients - submenu option a
     * RUBRIC: "Display the sender and recipient of all stored messages"
     */
    public static void displaySenderAndRecipient() {
        // Check if any messages exist before trying to display
        if (sentCounter == 0) {
            System.out.println("No messages stored yet.");
            return; // Exit method early
        }
        System.out.println("\n--- Sender and Recipient List ---");
        // FOR LOOP: i starts at 0, runs while i < sentCounter, i increases by 1 each loop
        for (int i = 0; i < sentCounter; i++) {
            // Sender is always "User" since we don't collect sender name
            // Recipient comes from recipientCellArray at same index i
            System.out.println("Sender: User | Recipient: " + recipientCellArray[i]);
        }
    }

    // ==================== SUBMENU OPTION B ====================
    /**
     * METHOD: displayLongestMessage
     * PURPOSE: Find and display message with most characters - submenu option b
     * RUBRIC: "Display the longest stored message" 4-5 marks
     * ALGORITHM: Loop array, compare length, track index of longest
     */
    public static void displayLongestMessage() {
        if (sentCounter == 0) {
            System.out.println("No messages stored.");
            return;
        }
        int longestIndex = 0; // Assume first message at index 0 is longest initially

        // Loop starting from index 1 because we already assume index 0 is longest
        for (int i = 1; i < sentCounter; i++) {
            // Compare current message length with current longest message length
            //.length() method counts characters including spaces
            if (sentMessageArray[i].length() > sentMessageArray[longestIndex].length()) {
                longestIndex = i; // Update longest index if current message is longer
            }
        }
        // After loop, longestIndex holds position of longest message
        System.out.println("\n--- Longest Message ---");
        System.out.println("Message: \"" + sentMessageArray[longestIndex] + "\""); // Display with quotes
        System.out.println("Character Count: " + sentMessageArray[longestIndex].length()); // Show length
    }

    // ==================== SUBMENU OPTION C ====================
    /**
     * METHOD: searchByMessageId
     * PURPOSE: Search for specific message ID and show recipient + message - option c
     * RUBRIC: "Search for a message ID and display corresponding recipient and message" 8-10 marks
     */
    public static void searchByMessageId(String searchId) {
    for (int i = 0; i < sentCounter; i++) {
        if (messageIdArray[i].equalsIgnoreCase(searchId)) {
            System.out.println("Recipient: " + recipientCellArray[i]);
            System.out.println("Message: " + sentMessageArray[i]);
            return;
        }
    }
    System.out.println("Message ID not found.");
}
    // ==================== SUBMENU OPTION D ====================
    /**
     * METHOD: searchByRecipient
     * PURPOSE: Find all messages sent to specific recipient - option d
     * RUBRIC: "Search for all messages stored for a particular recipient" 8-10 marks
     */
    public static void searchByRecipient(String searchRecipient) {
    boolean found = false;

    for (int i = 0; i < sentCounter; i++) {
        if (recipientCellArray[i].equalsIgnoreCase(searchRecipient)) {
            System.out.println("Message: " + sentMessageArray[i]);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No messages found for this recipient.");
    }
}

    // ==================== SUBMENU OPTION E ====================
    /**
     * METHOD: deleteByHash
     * PURPOSE: Delete message by searching for its hash - option e
     * RUBRIC: "Delete a message using the message hash" 8-10 marks
     * SCREENSHOT: "Message: 'Where are you? You are late!' successfully deleted."
     * ALGORITHM: Find index, shift all elements after it one position left
     */
    public static void deleteByHash(String hashToDelete) {

    for (int i = 0; i < sentCounter; i++) {
        if (messageHashArray[i].equalsIgnoreCase(hashToDelete)) {

            System.out.println("Message: \"" + sentMessageArray[i] + "\" successfully deleted.");

            for (int j = i; j < sentCounter - 1; j++) {
                messageIdArray[j] = messageIdArray[j + 1];
                recipientCellArray[j] = recipientCellArray[j + 1];
                sentMessageArray[j] = sentMessageArray[j + 1];
                messageHashArray[j] = messageHashArray[j + 1];
            }

            sentCounter--;
            return;
        }
    }

    System.out.println("Message hash not found.");
}

    // ==================== SUBMENU OPTION F ====================
    /**
     * METHOD: displayFullReport
     * PURPOSE: Display complete report with Hash, Recipient, Message - option f
     * RUBRIC: "Display a report that lists the full details of all stored messages" 8-10 marks
     * SCREENSHOT: Report must show Message Hash, Recipient, Message
     */
    public static void displayFullReport() {
        if (sentCounter == 0) {
            System.out.println("No messages stored.");
            return;
        }
        System.out.println("\n========== FULL MESSAGE REPORT ==========");
        // Loop through all messages and print all 3 required fields
        for (int i = 0; i < sentCounter; i++) {
            System.out.println("\nMessage Number: " + (i + 1)); // User-friendly numbering starting at 1
            System.out.println("Message Hash: " + messageHashArray[i]); // Field 1: Hash
            System.out.println("Recipient: " + recipientCellArray[i]); // Field 2: Recipient
            System.out.println("Message: " + sentMessageArray[i]); // Field 3: Message content
        }
        System.out.println("=========================================");
    }

    // ==================== HELPER METHODS ====================
    public static int returnTotalMessagess() {
        return sentCounter; // Return current count - note 3 's' as per screenshot spelling
    }

    public static String printMessages() {
        if (sentCounter == 0) return "No messages sent.";
        StringBuilder sb = new StringBuilder(); // Efficient string building
        for (int i = 0; i < sentCounter; i++) {
            sb.append("Recipient: ").append(recipientCellArray[i])
            .append(" | Message: ").append(sentMessageArray[i]).append("\n");
        }
        return sb.toString(); // Convert StringBuilder to String
    }

    public static String getLastMessageHash() {
        // Return hash of last message added - used for immediate delete option
        return sentCounter > 0? messageHashArray[sentCounter-1] : "";
    }

    public static String checkMessageLength(String longMessage) {
    if (longMessage.length() > 250) {
        return "Message exceeds 250 characters and cannot be sent.";
    } else {
        return "Message ready to send.";
    }
}
}
    



