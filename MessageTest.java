/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quick_chat;
/**
 *
 * @author lerat
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {

        // Reset arrays before every test
        Message.sentCounter = 0;
        Message.disregardedCounter = 0;
        Message.storedCounter = 0;

        for (int i = 0; i < Message.max; i++) {
            Message.sentMessageArray[i] = null;
            Message.disregardedMessageArray[i] = null;
            Message.storedMessageArray[i] = null;
            Message.messageHashArray[i] = null;
            Message.messageIdArray[i] = null;
            Message.recipientCellArray[i] = null;
        }
    }

    // ======================================================
    // PART 2 TESTS
    // ======================================================

    @Test
    public void testMessageLengthValid() {

        String result =
                Message.checkMessageLength(
                        "Hi Mike, can you join us for dinner tonight?");

        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthTooLong() {

        String longMessage = "A".repeat(260);

        String result = Message.checkMessageLength(longMessage);

        assertTrue(result.contains("Message exceeds 250 characters"));
    }

    @Test
    public void testRecipientCellValid() {

        assertEquals(
                "Cell phone number successfully captured.",
                Message.checkRecipientCell("+27834567890")
        );
    }

    @Test
    public void testRecipientCellInvalid() {

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkRecipientCell("0834567890")
        );
    }

    @Test
    public void testMessageHash() {

        String hash =
                Message.createMessageHash(
                        "0012345678",
                        0,
                        "Hi there"
                );

        assertEquals("00:0:HITHERE", hash);
    }

    @Test
    public void testMessageIDValid() {

        assertTrue(Message.checkMessageID("1234567890"));
    }

    @Test
    public void testMessageIDInvalid() {

        assertFalse(Message.checkMessageID("12345678901"));
    }

    @Test
    public void testSendMessageOption() {

        assertEquals(
                "Message successfully sent.",
                Message.SentMessage("Send")
        );
    }

    @Test
    public void testStoreMessageOption() {

        assertEquals(
                "Message successfully stored.",
                Message.SentMessage("Store")
        );
    }

    @Test
    public void testDisregardMessageOption() {

        assertEquals(
                "Press 0 to delete the message.",
                Message.SentMessage("0")
        );
    }

    // ======================================================
    // PART 3 TESTS
    // ======================================================

    @Test
    public void testSentMessagesArrayPopulated() {

        Message.addMessage(
                "1234567890",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        Message.addMessage(
                "2234567890",
                "+27838884567",
                "It is dinner time !",
                "Sent"
        );

        assertEquals(
                "Did you get the cake?",
                Message.sentMessageArray[0]
        );

        assertEquals(
                "It is dinner time !",
                Message.sentMessageArray[1]
        );
    }

    @Test
    public void testReturnTotalMessages() {

        Message.addMessage(
                "1234567890",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        Message.addMessage(
                "2234567890",
                "+27838884567",
                "It is dinner time !",
                "Sent"
        );

        assertEquals(2, Message.returnTotalMessagess());
    }

    @Test
    public void testPrintMessages() {

        Message.addMessage(
                "1234567890",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        String result = Message.printMessages();

        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testGetLastMessageHash() {

        Message.addMessage(
                "1234567890",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        assertNotNull(Message.getLastMessageHash());
    }

    @Test
    public void testDisregardedMessagesArray() {

        Message.addMessage(
                "1234567890",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                "Disregard"
        );

        assertEquals(
                "Yohoooo, I am at your gate.",
                Message.disregardedMessageArray[0]
        );
    }

    @Test
    public void testDeleteMessageByHash() {

        Message.addMessage(
                "1234567890",
                "+27838884567",
                "Where are you? You are late!",
                "Sent"
        );

        String hash = Message.messageHashArray[0];

        Message.deleteByHash(hash);

        assertEquals(0, Message.sentCounter);
    }

    @Test
    public void testSearchMessageIDDataExists() {

        Message.addMessage(
                "0838884567",
                "+27838884567",
                "It is dinner time !",
                "Sent"
        );

        assertEquals(
                "It is dinner time !",
                Message.sentMessageArray[0]
        );
    }

    @Test
    public void testStoredMessagesLoaded() {

        Message.addMessage(
                "1234567890",
                "+27838884567",
                "Stored test message",
                "Stored"
        );

        Message.readJSONToArray();

        assertTrue(Message.storedCounter >= 1);
    }
}

