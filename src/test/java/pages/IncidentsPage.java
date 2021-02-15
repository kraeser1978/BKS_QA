package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import common.RegressionTest;
import org.openqa.selenium.By;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.*;

public class IncidentsPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(IncidentsPage.class.getSimpleName());

    public IncidentsPage(){
        waitForTheTableToLoad();
    }

    public IncidentCardPage openIncidentDetailsPage(){
        String tableRowsLocator = locators.getProperty("incidents_table_row1_all_cells");
        ElementsCollection rows = $$(By.xpath(tableRowsLocator));
        rows.get(0).shouldBe(Condition.enabled).click();
        switchTo().window(1);
        return page(IncidentCardPage.class);
    }

    public IncidentsPage clickFilter(){
        String tableRowsLocator = locators.getProperty("incidents_table_rows");
        ElementsCollection rows = $$(By.xpath(tableRowsLocator));
        int currentSize = rows.size();
        clickButton("Применить простой фильтр");
        rows.shouldBe(CollectionCondition.sizeNotEqual(currentSize));
        return this;
    }

    public IncidentsPage filterByList(String fieldName, String valueToFilterBy){
        int seqNo = getColSeqNoByName(fieldName);
        String fieldControlLocator = locators.getProperty("incidents_header_search_field_values_template").replace("th","th[" + seqNo + "]");
        $(By.xpath(fieldControlLocator)).shouldBe(Condition.enabled).click();
        String itemLocator = locators.getProperty("listitem_option").replace("''","'" + valueToFilterBy + "'");
        SelenideElement option = $(By.xpath(itemLocator));
//        if (option.is(not(Condition.visible))){
//            String searchFieldLocator = locators.getProperty("search_field_criteria_input");
//            $(By.xpath(searchFieldLocator)).shouldBe(Condition.enabled).setValue(valueToFilterBy);
//            clickButton("Применить простой фильтр");
//        }
        option.shouldBe(Condition.enabled).click();
        return this;
    }

    public IncidentsSettingsDialog openSettingsDialog(){
        clickButton("Настроить представление");
        return page(IncidentsSettingsDialog.class);
    }
}
