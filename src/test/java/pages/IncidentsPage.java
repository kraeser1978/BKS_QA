package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import common.RegressionTest;
import org.openqa.selenium.By;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class IncidentsPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(IncidentsPage.class.getSimpleName());

    public IncidentsPage(){
        String tableRowsLocator = locators.getProperty("incidents_table_rows");
        ElementsCollection rows = $$(By.xpath(tableRowsLocator)).shouldBe(CollectionCondition.sizeGreaterThanOrEqual(20),15000);
        logger.log(Level.INFO,"таблица загружена на странице");
    }

    public IncidentsSettingsDialog openSettingsDialog(){
        clickButton("Настроить представление");
        return page(IncidentsSettingsDialog.class);
    }
}
