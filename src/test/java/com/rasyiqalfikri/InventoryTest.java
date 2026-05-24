package com.rasyiqalfikri;

import com.rasyiqalfikri.pages.LoginPage;
import com.rasyiqalfikri.pages.InventoryPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class InventoryTest extends BaseTest {

    private void savePageSource(String filename) {
        try {
            File dir = new File("target");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter writer = new FileWriter("target/" + filename);
            writer.write(driver.getPageSource());
            writer.close();
            System.out.println("Saved page source to target/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPositiveAddToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        try {
            Assertions.assertTrue(inventoryPage.isLoaded(), "Inventory page did not load successfully!");

            Assertions.assertFalse(inventoryPage.isCartBadgeDisplayed(),
                    "Cart badge should not be displayed initially!");

            inventoryPage.addBackpackToCart();
            Assertions.assertTrue(inventoryPage.isCartBadgeDisplayed(),
                    "Cart badge should be displayed after adding item!");
            Assertions.assertEquals(1, inventoryPage.getCartBadgeCount(), "Cart badge count should be 1!");
            Assertions.assertTrue(inventoryPage.isBackpackRemoveButtonDisplayed(),
                    "Remove button should be displayed!");

            inventoryPage.removeBackpackFromCart();

            Assertions.assertFalse(inventoryPage.isCartBadgeDisplayed(),
                    "Cart badge should not be displayed after removing item!");
            Assertions.assertTrue(inventoryPage.isBackpackAddToCartButtonDisplayed(),
                    "Add to cart button should be displayed!");
        } catch (Throwable t) {
            savePageSource("testPositiveAddToCart-failure.html");
            throw t;
        }
    }

    @Test
    public void testNegativeAddToCartWithoutLogin() {
        InventoryPage inventoryPage = new InventoryPage(driver);

        try {
            inventoryPage.openDirectly();
            LoginPage loginPage = new LoginPage(driver);
            String error = loginPage.getErrorMessage();

            Assertions.assertTrue(error.contains("You can only access '/inventory.html' when you are logged in"),
                    "Expected access error message, but got: " + error);
        } catch (Throwable t) {
            savePageSource("testNegativeAddToCartWithoutLogin-failure.html");
            throw t;
        }
    }
}
