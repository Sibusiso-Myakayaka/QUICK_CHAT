/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quick_chat;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for Login functionality
 * Tests username, password, cell number validation,
 * registration, login, and login status messages
 */
public class LoginTest {

    /**
     * Test valid username format
     * Must contain underscore and be <= 5 characters
     */
    @Test
    public void testCheckUserNameValid() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    /**
     * Test invalid username format
     * No underscore or too long
     */
    @Test
    public void testCheckUserNameInvalid() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle"));
    }

    /**
     * Test valid password complexity
     * Must contain uppercase, lowercase, number, special char, and be 8+ chars
     */
    @Test
    public void testCheckPasswordValid() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Passw0rd!"));
    }

    /**
     * Test invalid password complexity
     * Does not meet required rules
     */
    @Test
    public void testCheckPasswordInvalid() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    /**
     * Test valid South African cell number
     * Must start with +27 and have 9 digits after
     */
    @Test
    public void testCheckCellPhoneValid() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27831234567"));
    }

    /**
     * Test invalid cell number format
     */
    @Test
    public void testCheckCellPhoneInvalid() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("0831234567"));
    }

    /**
     * Test successful user registration
     * All inputs are valid so registration should succeed
     */
    @Test
    public void testRegisterUserSuccess() {
        Login login = new Login();

        String result = login.registerUser(
                "kyl_1",
                "Passw0rd!",
                "Kyle",
                "Smith",
                "+27831234567"
        );

        assertTrue(result.contains("Username successfully captured"));
        assertTrue(result.contains("Password successfully captured"));
        assertTrue(result.contains("Cell phone number successfully captured"));
    }

    /**
     * Test successful login after registration
     */
    @Test
    public void testLoginSuccess() {
        Login login = new Login();

        login.registerUser(
                "kyl_1",
                "Passw0rd!",
                "Kyle",
                "Smith",
                "+27831234567"
        );

        assertTrue(login.loginUser("kyl_1", "Passw0rd!"));
    }

    /**
     * Test failed login with incorrect credentials
     */
    @Test
    public void testLoginFail() {
        Login login = new Login();

        login.registerUser(
                "kyl_1",
                "Passw0rd!",
                "Kyle",
                "Smith",
                "+27831234567"
        );

        assertFalse(login.loginUser("wrong", "wrong"));
    }

    /**
     * Test login success message
     */
    @Test
    public void testLoginStatusSuccess() {
        Login login = new Login();

        login.registerUser(
                "kyl_1",
                "Passw0rd!",
                "Kyle",
                "Smith",
                "+27831234567"
        );

        String msg = login.returnLoginStatus(true);

        assertTrue(msg.contains("Welcome Kyle, Smith"));
    }

    /**
     * Test login failure message
     */
    @Test
    public void testLoginStatusFail() {
        Login login = new Login();

        String msg = login.returnLoginStatus(false);

        assertEquals(
                "Username or password incorrect, please try again.",
                msg
        );
    }
}