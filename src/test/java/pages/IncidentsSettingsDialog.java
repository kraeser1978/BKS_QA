package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static common.RegressionTest.locators;

public class IncidentsSettingsDialog {
    private static Logger logger = Logger.getLogger(IncidentsSettingsDialog.class.getSimpleName());

    public IncidentsSettingsDialog(){
        String settingsButtonLocator = locators.getProperty("setup_dialog_title");
        $(By.xpath(settingsButtonLocator)).waitUntil(Condition.visible,8000);
        String saveButtonLocator = locators.getProperty("button_template1").replace("''","'Сохранить'");
        $(By.xpath(saveButtonLocator)).shouldBe(Condition.visible);
    }

    public String getLastGroupName(){
        String groupNameLocator = locators.getProperty("items_list").replace("''","'Группа №'");
        ElementsCollection groups = $$(By.xpath(groupNameLocator)).shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1));
        String lastName = groups.get(groups.size()-1).getText();
        return lastName;
    }

    public IncidentsSettingsDialog switchToTab(String tabName){
        String tabNameLocator = locators.getProperty("link_template1").replace("''","'" + tabName + "'");
        $(By.xpath(tabNameLocator)).shouldBe(Condition.enabled).click();
        getLastGroupName();
        return this;
    }

    public IncidentsSettingsDialog addNewGroup(){
        String allFilterItemsLocator = locators.getProperty("current_filter_items");
        ElementsCollection items = $$(By.xpath(allFilterItemsLocator));
        int sizeBeforeAdd = items.size();
        String newGoupButtonLocator = locators.getProperty("button_template2").replace("''","'Добавить группу'");
        $(By.xpath(newGoupButtonLocator)).shouldBe(Condition.enabled).click();
        items = $$(By.xpath(allFilterItemsLocator)).shouldBe(CollectionCondition.sizeGreaterThan(sizeBeforeAdd));
        return this;
    }

    public IncidentsSettingsDialog removeGroup(){
        String allFilterItemsLocator = locators.getProperty("current_filter_items");
        ElementsCollection items = $$(By.xpath(allFilterItemsLocator));
        int sizeBeforeAdd = items.size();
        String removeGoupButtonLocator = "(" + locators.getProperty("button_template2").replace("''","'Удалить группу полей из фильтра'") + ")[last()]";
        $(By.xpath(removeGoupButtonLocator)).shouldBe(Condition.enabled).click();
        items = $$(By.xpath(allFilterItemsLocator)).shouldBe(CollectionCondition.sizeLessThan(sizeBeforeAdd));
        return this;
    }
}
