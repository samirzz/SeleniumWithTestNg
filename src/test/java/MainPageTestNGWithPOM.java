import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MainPageTestNGWithPOM {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ShoppingCartPage shoppingCartPage;
    private CheckoutPage checkoutPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "F://seleniumJarFilesandDrivers//chromedriver-win64//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testOrderProcess() throws InterruptedException {

        loginPage.login("standard_user", "secret_sauce");

        Thread.sleep(3000);
        productsPage.addToCart("sauce-labs-backpack");

        Thread.sleep(3000);
        productsPage.navigateToShoppingCart();

        Thread.sleep(3000);
        shoppingCartPage.continueShopping();

        Thread.sleep(3000);
        productsPage.addToCart("test.allthethings()-t-shirt-(red)");

        Thread.sleep(3000);
        productsPage.navigateToShoppingCart();

        Thread.sleep(3000);
        shoppingCartPage.checkout();

        Thread.sleep(3000);
        checkoutPage.fillCheckoutDetails("samir", "bhandari", "4450");

        Thread.sleep(3000);
        checkoutPage.finishCheckout();

        Thread.sleep(10000);


    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

class LoginPage {
    private WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}

class ProductsPage {
    private WebDriver driver;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartButton;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement shoppingCartLink;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addToCart(String productId) {
        driver.findElement(By.id("add-to-cart-" + productId)).click();
    }

    public void navigateToShoppingCart() {
        shoppingCartLink.click();
    }
}

class ShoppingCartPage {
    private WebDriver driver;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void continueShopping() {
        continueShoppingButton.click();
    }

    public void checkout() {
        driver.findElement(By.name("checkout")).click();
    }
}

class CheckoutPage {
    private WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "finish")
    private WebElement finishButton;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillCheckoutDetails(String firstName, String lastName, String postalCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postalCodeInput.sendKeys(postalCode);
        driver.findElement(By.id("continue")).click();
    }

    public void finishCheckout() {
        finishButton.click();
    }
}
