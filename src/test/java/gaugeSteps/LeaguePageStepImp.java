package gaugeSteps;

import com.thoughtworks.gauge.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.scoreAssignment.constants.Locators.*;

public class LeaguePageStepImp extends BaseClass {

    private final List<String> CFL_TEAM_LIST = Arrays.asList("TOR Argonauts", "HAM Tiger-Cats", "OTT Redblacks", "MTL Alouettes", "WPG Blue Bombers", "BC Lions", "EDM Elks", "CGY Stampeders", "SSK Roughriders", "EAST", "WEST");
    private final List<String> MLB_TEAM_LIST = Arrays.asList("NY Yankees", "BAL Orioles", "TB Rays", "BOS Red Sox", "TOR Blue Jays", "CLE Guardians", "KC Royals", "MIN Twins", "DET Tigers", "CHI White Sox", "SEA Mariners", "TEX Rangers", "HOU Astros", "OAK Athletics", "LA Angels", "AL EAST", "AL CENTRAL", "AL WEST");
    private final List<String> NCAA_FOOTBALL_TEAM_LIST = Arrays.asList("AAC", "SMU", "Tulane", "UTSA", "Memphis", "South Florida", "Rice", "Navy", "North Texas", "Florida Atlantic", "UAB", "Tulsa", "Charlotte", "Temple", "East Carolina");
    private final List<String> WNBA_TEAM_LIST = Arrays.asList("NY Liberty", "CON Sun", "ATL Dream", "CHI Sky", "WSH Mystics", "IND Fever", "LV Aces", "MIN Lynx", "PHX Mercury", "DAL Wings", "SEA Storm", "LA Sparks", "EASTERN", "WESTERN");
    private final List<String> NHL_TEAM_LIST = Arrays.asList("SJ Sharks", "ANA Ducks", "SEA Kraken", "CGY Flames", "CHI Blackhawks", "ARI Coyotes", "MIN Wild", "STL Blues", "CBJ Blue Jackets", "NJ Devils", "PHI Flyers", "PIT Penguins", "WSH Capitals", "MTL Canadiens", "OTT Senators", "BUF Sabres", "DET Red Wings", "NY Rangers", "FLA Panthers", "CAR Hurricanes", "DAL Stars", "WPG Jets", "VAN Canucks", "BOS Bruins", "COL Avalanche", "EDM Oilers", "TOR Maple Leafs", "NSH Predators", "LA Kings", "TB Lightning", "VGS Golden Knights", "NY Islanders", "EASTERN ATLANTIC", "EASTERN METROPOLITAN", "WESTERN CENTRAL", "WESTERN PACIFIC");
    private final List<String> NBA_TEAM_LIST = Arrays.asList("BOS Celtics", "NY Knicks", "PHI 76ers", "BKN Nets", "TOR Raptors", "MIL Bucks", "CLE Cavaliers", "IND Pacers", "CHI Bulls", "DET Pistons", "ORL Magic", "MIA Heat", "ATL Hawks", "CHA Hornets", "WSH Wizards", "EASTERN", "ATLANTIC", "CENTRAL", "SOUTHEAST", "WESTERN", "NORTHWEST", "PACIFIC", "SOUTHWEST");


    @Step("Select <leagueName> from leagues tab")
    public void selectLeague(String leagueName) {
        boolean isNameFound = false;
        while (!isNameFound) {
            List<WebElement> leaguesList = driver.findElements(By.id(LEAGUE_NAME_TEXT_ID));
            // Checks each league until it locates the correct one
            for (int i = 0; i < leaguesList.size(); i++) {
                WebElement league = leaguesList.get(i);
                String leagueText = league.getText();
                if (Objects.equals(leagueText, leagueName)) {
                    // Opens league page once found
                    isNameFound = true;
                    List<WebElement> leagueIconsList = driver.findElements(By.id(LEAGUE_ICON_ID));
                    WebElement leagueIcon = leagueIconsList.get(i);
                    leagueIcon.click();
                    String pageTitle = driver.findElement(By.id(PAGE_TITLE_ID)).getText();
                    // Checks if the opened Page matches the league name
                    assertTrue(leagueName.equalsIgnoreCase(pageTitle), "Expected " + leagueName + " but found " + pageTitle);
                    break;
                }
            }

            if (!isNameFound) {
                //Scroll Function using first and last elements of the list as coordinates
                final var startLocation = leaguesList.get(leaguesList.size() - 1).getLocation();
                final var endLocation = leaguesList.get(0).getLocation();
                fingerGestureUtil.swipeScreen(startLocation.x, startLocation.y, endLocation.x, endLocation.y);
            }
        }


    }

    @Step("Open <tabName> tab")
    public void openTab(String tabName) {
        driver.findElement(By.xpath("//android.widget.LinearLayout[@content-desc=\"" + tabName + "\"]")).click();
    }

    @Step("Verify standings")
    public void verifyStandings() {
        //todo: Handle empty standings e.g NFL
        String pageTitle = driver.findElement(By.id(PAGE_TITLE_ID)).getText();
        List<String> sportTeams = null;
        switch (pageTitle) {
            case "CFL":
                sportTeams = CFL_TEAM_LIST;
                break;
            case "MLB":
                sportTeams = MLB_TEAM_LIST;
                break;
            case "NCAA Football":
                sportTeams = NCAA_FOOTBALL_TEAM_LIST;
                break;
            case "WNBA":
                sportTeams = WNBA_TEAM_LIST;
                break;
            case "NHL":
                sportTeams = NHL_TEAM_LIST;
                driver.findElement(By.xpath(DIVISION_TAB_XPATH)).click();
                break;
            case "NBA":
                sportTeams = NBA_TEAM_LIST;
                driver.findElement(By.xpath(DIVISION_TAB_XPATH)).click();
                break;
            default:
                Assertions.fail(pageTitle + " Must be added to list of teams for testing.");
                break;
        }
        List<WebElement> standingsList = driver.findElements(By.id(TEAM_NAME));
        // Verify that teams present belong to the correct league.
        for (int i = 0; i < standingsList.size(); i++) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(TEAM_NAME)));
            String teamName = element.getText().replaceFirst("^[a-zA-Z]-", "");
            assertTrue(sportTeams.contains(teamName), teamName + " does not belong to team.");
        }

    }
}
