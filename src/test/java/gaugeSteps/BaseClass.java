package gaugeSteps;

import com.thoughtworks.gauge.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.scoreAssignment.util.FingerGestureUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.scoreAssignment.constants.Locators.*;

public class BaseClass {

    protected static AndroidDriver driver;
    protected static WebDriverWait wait;
    protected static FingerGestureUtil<AppiumDriver> fingerGestureUtil;

    @BeforeSuite
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("appium:deviceName", "Pixel 6a");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
//           capabilities.setCapability("appium:udid","26171JEGR10583");
        capabilities.setCapability("appium:udid", "emulator-5554");
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "14");
        capabilities.setCapability("appium:autoGrantPermissions", true);
//        capabilities.setCapability("appPackage","com.fivemobile.thescore");
//        capabilities.setCapability("appActivity","com.fivemobile.thescore.ui.MainActivity");
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/build/com.fivemobile.thescore.apk");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        fingerGestureUtil = new FingerGestureUtil<>(driver);
        onWelcomePage();
        pressLogInButton();
        loginUser("mkoszy1985@gmail.com","ScoreAssignment");
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }


    public void pressLogInButton() {
        WebElement logInButton = driver.findElement(By.id(LOG_IN_BUTTON_TXT_ID));
        // Get coordinates of bottom corner of element, offsetting by 5 pixels  in height and width.
        final var location = logInButton.getLocation();
        final var size = logInButton.getSize();
        final var x = location.getX() + (size.getWidth() - 5);
        final var y = location.getY() + (size.getHeight() - 5);
        fingerGestureUtil.tapScreen(x, y);
    }


    public void loginUser(String userName, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_INPUT_FIELD_ID))).click();
        driver.findElement(By.id(EMAIL_INPUT_FIELD_ID)).sendKeys(userName);
        driver.findElement(By.id(PASSWORD_INPUT_FIELD_ID)).sendKeys(password);
        driver.findElement(By.id(LOG_IN_BUTTON_ID)).click();
        // Verifies if we reach the Main page after logging in.
        boolean isNavBarDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(NAV_BAR_ID))).isDisplayed();
        assertTrue(isNavBarDisplayed);
    }


    public void onWelcomePage() {
        String welcome = driver.findElement(By.id(LOG_IN_WELCOME_TXT_ID)).getText();
        String experience = driver.findElement(By.id(LOG_IN_EXPERIENCE_TXT_ID)).getText();
        String signIn = driver.findElement(By.id(LOG_IN_BUTTON_TXT_ID)).getText();
        String terms = driver.findElement(By.id(LOG_IN_TERMS_TXT_ID)).getText();
        String getStarted = driver.findElement(By.id(GET_STARTED_BUTTON_TXT_ID)).getText();
        assertEquals(welcome, "WELCOME");
        assertEquals(experience, "Time to experience the best \n" +
                "sports app. Ever.");
        assertEquals(signIn, "HAVE AN ACCOUNT? LOG IN");
        assertEquals(terms, "By tapping “Get Started”, you agree to our \n" +
                "Terms of use, Privacy Policy and EULA");
        assertEquals(getStarted, "Get Started");
    }

}

