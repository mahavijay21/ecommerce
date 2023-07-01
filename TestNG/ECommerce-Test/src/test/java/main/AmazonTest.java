package main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonTest {
	
	private WebDriver driver;

    @BeforeClass
    public void setup() {
    	// Code to set up the browser and open the Amazon website
        System.setProperty("webdriver.chrome.driver", "/Users/admin/Downloads/chromedriver_mac64/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.amazon.in");
        driver.manage().window().maximize();
        System.out.println("Launch browser and open Amazon India website");
    }

    @AfterClass
    public void teardown() {
        // Code to close the browser after each test method
    	// driver.quit();
        System.out.println("Teardown: Close browser");
    }

    @Test
    public void loginToAmazon() {
        // Code to perform login
    	WebElement signInLink = driver.findElement(By.id("nav-link-accountList"));
    	signInLink.click();
    	
    	WebElement emailField = driver.findElement(By.id("ap_email"));
    	emailField.sendKeys("email");
    	WebElement continueButton = driver.findElement(By.id("continue"));
    	continueButton.click();
    	
    	WebElement passwordField = driver.findElement(By.id("ap_password"));
    	passwordField.sendKeys("password");
    	WebElement signInButton = driver.findElement(By.id("signInSubmit"));
    	signInButton.click();

        System.out.println("Login to Amazon");
    }

    @Test(dependsOnMethods = "loginToAmazon")
    public void searchItem() {
        // Code to search for an item
    	WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
    	searchBox.sendKeys("Laptop");
    	WebElement signInButton = driver.findElement(By.id("nav-search-submit-button"));
    	signInButton.click();
    	
        System.out.println("Search for an item");
    }

    @Test(dependsOnMethods = "searchItem")
    public void printProducts() {
        // Code to print all products on the first page
        WebElement productsContainer = driver.findElement(By.xpath("//div[@class='s-main-slot s-result-list s-search-results sg-row']"));
        List<WebElement> productElements = productsContainer.findElements(By.xpath(".//div[@class='sg-col-20-of-24 s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 AdHolder sg-col s-widget-spacing-small sg-col-12-of-16']"));

        System.out.println("Products on the first page:");
        for (WebElement productElement : productElements) {
            String productName = productElement.findElement(By.xpath(".//a")).getText();
            System.out.println("Product: " + productName);
        }
    	
        System.out.println("Print all products on the first page");
    }

    @Test(dependsOnMethods = "printProducts")
    public void addProduct() {
        // Code to add a product
        // Click on the first search result
        WebElement firstResult = driver.findElement(By.xpath("//div[@data-cel-widget='search_result_2']//h2/a"));
        firstResult.click();

        // Click on the "Add to Cart" button
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
        addToCartButton.click();
        System.out.println("Add a product");
    }

    @Test(dependsOnMethods = "addProduct")
    public void updateProduct() {
        // Code to update a product
    	// Navigate to the cart page
        driver.findElement(By.id("cart")).click();

        // Locate the input field for the quantity and update the value
        WebElement quantityInput = driver.findElement(By.xpath("//input[@class='cart-quantity-input']"));
        quantityInput.clear();
        quantityInput.sendKeys("2");

        // Click the "Update" button to update the quantity
        driver.findElement(By.xpath("//button[text()='Update']")).click();
        System.out.println("Update a product");
    }

    @Test(dependsOnMethods = "updateProduct")
    public void deleteProduct() {
        // Code to delete a product
        // Navigate to the cart page
        driver.findElement(By.id("cart")).click();

        // Find the delete button for the product and click it
        WebElement deleteButton = driver.findElement(By.xpath("//button[text()='Delete']"));
        deleteButton.click();
        System.out.println("Delete a product");
    }

    @Test(dependsOnMethods = "deleteProduct")
    public void logout() {
        // Code to perform logout
        // Find the element to hover over
        WebElement profileOptionsHover = driver.findElement(By.id("nav-link-accountList"));

        // Create an instance of Actions class
        Actions actions = new Actions(driver);

        // Perform the hover action
        actions.moveToElement(profileOptionsHover).perform();

        // Find and click the link in the popped up display
        WebElement signOutElement = driver.findElement(By.id("nav-item-signout"));
        signOutElement.click();
        System.out.println("Logout from Amazon");
    }
}

