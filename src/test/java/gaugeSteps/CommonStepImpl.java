package gaugeSteps;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.scoreAssignment.constants.Locators.*;

public class CommonStepImpl extends BaseClass {

    @Step("Navigate to <navBar> Page")
    public void NavigateTo(String destination) {
        WebElement NavButton = driver.findElement(By.id(NAV_BAR_ROOT_ID + destination.toLowerCase()));
        boolean isSelected = NavButton.isSelected();
        //If desired tab is already selected, skip clicking
        if (!isSelected) {
            NavButton.click();
        }
        // Check page title to verify we are on the desired page
        String pageTitle = driver.findElement(By.id(PAGE_TITLE_ID)).getText();
        assertTrue(destination.equalsIgnoreCase(pageTitle));
    }

    @Step("Navigate to previous page")
    public void navigateUp() {
        String currentPageTitle = driver.findElement(By.id(PAGE_TITLE_ID)).getText();
        driver.findElement(By.xpath(NAVIGATE_UP_XPATH)).click();
        String newPageTItle = driver.findElement(By.id(PAGE_TITLE_ID)).getText();
        // Verify page title has changed.
        assertNotEquals(currentPageTitle, newPageTItle);
    }
}
