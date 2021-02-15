package pages;

import com.codeborne.selenide.Condition;
import common.RegressionTest;
import org.openqa.selenium.By;
import java.util.logging.Logger;
import static com.codeborne.selenide.Selenide.$;

public class IncidentCardPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(IncidentCardPage.class.getSimpleName());

    public IncidentCardPage(){
        String lastFieldLocator = locators.getProperty("search_criteria_input_template").replace("''","'Полное решение:'");
        $(By.xpath(lastFieldLocator)).shouldBe(Condition.visible);
    }

    public IncidentCardPage switchToTab(String tabName){
        String tabLocator = locators.getProperty("link_template1").replace("''","'" + tabName + "'");
        $(By.xpath(tabLocator)).shouldBe(Condition.enabled).click();
        return this;
    }
}
