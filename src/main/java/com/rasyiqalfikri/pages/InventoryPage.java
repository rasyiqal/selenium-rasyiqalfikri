package com.rasyiqalfikri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class InventoryPage {
    private final WebDriver driver;

    private final By titleHeader = By.className("title");
    private final By backpackAddToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By backpackRemoveButton = By.id("remove-sauce-labs-backpack");
    private final By cartBadge = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openDirectly() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }

    public boolean isLoaded() {
        try {
            return driver.findElement(titleHeader).getText().equalsIgnoreCase("Products");
        } catch (Exception e) {
            return false;
        }
    }

    private void clickElementJS(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void addBackpackToCart() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        clickElementJS(backpackAddToCartButton);
    }

    public void removeBackpackFromCart() {
        clickElementJS(backpackRemoveButton);
    }

    public boolean isBackpackRemoveButtonDisplayed() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            return driver.findElement(backpackRemoveButton).isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public boolean isBackpackAddToCartButtonDisplayed() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            return driver.findElement(backpackAddToCartButton).isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public int getCartBadgeCount() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            String text = driver.findElement(cartBadge).getText();
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public boolean isCartBadgeDisplayed() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            return driver.findElement(cartBadge).isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }
}
