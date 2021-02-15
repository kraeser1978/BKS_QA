package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import common.RegressionTest;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.openqa.selenium.By;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static common.RegressionTest.locators;

public class AllTransactionsPage extends IncidentsPage {
    private static Logger logger = Logger.getLogger(BKSMainPage.class.getSimpleName());

    public AllTransactionsPage(){
        waitForTheTableToLoad();
    }

    public IncidentsPage filterByInput(String fieldName, String valueToFilterBy){
        int seqNo = getColSeqNoByName(fieldName);
        String fieldControlLocator = locators.getProperty("incidents_header_search_field_values_template").replace("th","th[" + seqNo + "]");
        String fieldInputLocator = fieldControlLocator + locators.getProperty("table_input_field_template");
        $(By.xpath(fieldInputLocator)).shouldBe(Condition.enabled).setValue(valueToFilterBy);
        return this;
    }
}
