/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quick_chat;




import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MessageTest {

    @Test
    public void testCheckMessageValid() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessage());
    }

    @Test
    public void testCheckMessageTooLong() {
        String longMsg = "a".repeat(251);
        Message msg = new Message("+27718693002", longMsg);
        assertFalse(msg.checkMessage());
    }

    @Test
    public void testRecipientCellValid() {
        Message msg = new Message("+27718693002", "Test");
        assertEquals(
                "Cell phone number successfully captured.",
                msg.CheckrecipientCell()
        );
    }

    @Test
    public void testRecipientCellInvalid() {
        Message msg = new Message("08575975889", "Test");
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code.",
                msg.CheckrecipientCell()
        );
    }

    @Test
    public void testSentMessageSent() {
        Message msg = new Message("+27718693002", "Test");
        msg.setStatus("Sent");
        assertEquals("Message successfully sent.", msg.SentMessage());
    }

    @Test
    public void testSentMessageDisregarded() {
        Message msg = new Message("+27718693002", "Test");
        msg.setStatus("Disregarded");
        assertEquals("Press 0 to delete the message.", msg.SentMessage());
    }

    @Test
    public void testSentMessageStored() {
        Message msg = new Message("+27718693002", "Test");
        msg.setStatus("Stored");
        assertEquals("Message successfully stored.", msg.SentMessage());
    }

    @Test
    public void testMessageHashFormat() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        msg.setStatus("Sent");
        msg.SentMessage();

        String hash = msg.getMessageHash();

        assertFalse(hash.contains("HITONIGHT") || hash.contains("HIMITONIGHT"));
    }

    @Test
    public void testMessageIDLength() {
        Message msg = new Message("+27718693002", "Test");

        assertEquals(10, msg.getMessageID().length());
        assertTrue(msg.getMessageID().matches("\\d{10}"));
    }

    @Test
    public void testTotalMessagesSent() {
        int before = Message.returnTotalMessages();

        Message msg1 = new Message("+27718693002", "Msg 1");
        msg1.setStatus("Sent");
        msg1.SentMessage();

        Message msg2 = new Message("+27718693002", "Msg 2");
        msg2.setStatus("Sent");
        msg2.SentMessage();

        assertEquals(before + 2, Message.returnTotalMessages());
    }
}