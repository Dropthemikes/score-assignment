package gaugeSteps;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;

import static org.scoreAssignment.constants.Locators.GET_STARTED_BUTTON_ID;

public class WelcomePageStepImp extends BaseClass {

    @Step("Press 'Get Started' button")
    public void pressGetStartedButton() {
        driver.findElement(By.id(GET_STARTED_BUTTON_ID)).click();
    }

}
