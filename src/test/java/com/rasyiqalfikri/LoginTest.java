package com.rasyiqalfikri;

import com.rasyiqalfikri.pages.LoginPage;
import com.rasyiqalfikri.pages.InventoryPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        Assertions.assertTrue(inventoryPage.isLoaded(), "Inventory page did not load successfully!");
    }

    @Test
    public void testNegativeLoginInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login("standard_user", "wrong_password");

        String error = loginPage.getErrorMessage();
        Assertions.assertTrue(error.contains("Username and password do not match any user in this service"),
                "Expected invalid password error message, but got: " + error);
    }

    @Test
    public void testNegativeLoginLockedOut() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login("locked_out_user", "secret_sauce");

        String error = loginPage.getErrorMessage();
        Assertions.assertTrue(error.contains("Sorry, this user has been locked out"),
                "Expected locked out error message, but got: " + error);
    }

    @Test
    public void testNegativeLoginEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        Assertions.assertTrue(error.contains("Username is required"),
                "Expected empty username error message, but got: " + error);
    }
}
