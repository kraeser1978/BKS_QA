package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import common.RegressionTest;
import org.openqa.selenium.By;
import java.util.logging.Logger;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class BKSMainPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(BKSMainPage.class.getSimpleName());

    public BKSMainPage(){
        String chartsLocator = locators.getProperty("open_incidents_charts");
        $(By.cssSelector(chartsLocator)).waitUntil(Condition.visible,10000);
    }

    public IncidentsPage moveToOKKPIncidents(){
        clickLink("Инциденты");
        clickLink("ОККП");
        return Selenide.page(IncidentsPage.class);
    }
}
