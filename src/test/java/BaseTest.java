import com.codeborne.selenide.logevents.SelenideLogger;
import common.RegressionTest;
import org.hamcrest.core.*;
import org.junit.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.runners.MethodSorters;
import pages.*;

import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest extends RegressionTest {
    private static Logger logger = Logger.getLogger(BaseTest.class.getSimpleName());

    @Test
    public void test001userCanAuthorizeInApplication() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        assertEquals("ОШИБКА авторизации в системе БКС Инциденты",true,bksMainPage != null);
    }

    @Test
    public void test002userCanOpenOKKPIncidentsList() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        IncidentsPage incidentsPage = bksMainPage.moveToOKKPIncidents();
        assertEquals("ОШИБКА ошибка при открытии страницы Инциденты - ОККП",true,incidentsPage == null);
    }

    @Test
    public void test003userCanOpenIncidentSettingsDialog() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        IncidentsPage incidentsPage = bksMainPage.moveToOKKPIncidents();
        IncidentsSettingsDialog incidentsSettingsDialog = incidentsPage.openSettingsDialog();
        assertEquals("ОШИБКА ошибка при открытии страницы Инциденты - ОККП - Настройка представления (Базовое)",true,incidentsSettingsDialog != null);
    }

    @Test
    public void test004userCanAddNewGroupIntoIncidentSettingsDialog() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        IncidentsSettingsDialog incidentsSettingsDialog = bksMainPage
                .moveToOKKPIncidents()
                .openSettingsDialog()
                .switchToTab("Фильтр");
        String groupBeforeAdd = incidentsSettingsDialog.getLastGroupName();
        incidentsSettingsDialog.addNewGroup();
        String groupAfterAdd = incidentsSettingsDialog.getLastGroupName();
        assertNotEquals("ОШИБКА на странице Настройка представления (Базовое) не работает кнопка добавления группы в фильтре",groupBeforeAdd,groupAfterAdd);
    }

    @Test
    public void test005userCanRemoveGroupFromIncidentSettingsDialog() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        IncidentsSettingsDialog incidentsSettingsDialog = bksMainPage
                .moveToOKKPIncidents()
                .openSettingsDialog()
                .switchToTab("Фильтр");
        String groupBeforeAdd = incidentsSettingsDialog.getLastGroupName();
        incidentsSettingsDialog.removeGroup();
        String groupAfterAdd = incidentsSettingsDialog.getLastGroupName();
        assertNotEquals("ОШИБКА на странице Настройка представления (Базовое) не работает кнопка удаления группы в фильтре",groupBeforeAdd,groupAfterAdd);
    }

    @Test
    public void test006userCanFilterByState() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        IncidentsPage incidentsPage = bksMainPage.moveToOKKPIncidents();
        String searchByState1 = dataInput.get("Состояние1");
        incidentsPage
                .filterByList("Состояние",searchByState1)
                .clickFilter();
        ArrayList<String> rows4State1 = getAllRowsValuesByColName("Состояние");
        assertThat("ОШИБКА: отфильтрованные строки в поле \"Состояние\" содержат иное значение" ,rows4State1, IsCollectionContaining.hasItem(searchByState1));

        String searchByState2 = dataInput.get("Состояние2");
        incidentsPage
                .filterByList("Состояние",searchByState2)
                .clickFilter();
        ArrayList<String> rows4State2 = getAllRowsValuesByColName("Состояние");
        assertThat("ОШИБКА: отфильтрованные строки в поле \"Состояние\" содержат иное значение" ,rows4State2, IsCollectionContaining.hasItem(searchByState2));
    }

    @Test
    public void test007userCanSwitchIncidentCardTabs() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        IncidentsPage incidentsPage = bksMainPage.moveToOKKPIncidents();
        IncidentCardPage incidentCardPage = incidentsPage.openIncidentDetailsPage();
        try {
            incidentCardPage
                    .switchToTab("Аналитика")
                    .switchToTab("Дополнительная аналитика")
                    .switchToTab("Решение")
                    .switchToTab("Возмещение");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("ОШИБКА при переходе на вкладку в карточке инцидента",true,incidentCardPage != null);
        }
    }

    @Test
    public void test008userCanOpenTransactionsList() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        AllTransactionsPage allTransactionsPage = bksMainPage.moveToAllTransactionsList();
        assertEquals("ОШИБКА ошибка при открытии страницы Проводки АБС - Все",true,allTransactionsPage != null);
    }

    @Test
    public void test009userCanFilterByAmount() throws Exception {
        BKSMainPage bksMainPage = new SignInDialog().main("testuser_name");
        AllTransactionsPage allTransactionsPage = bksMainPage.moveToAllTransactionsList();
        String searchBySum = dataInput.get("Сумма1");
        allTransactionsPage
                .filterByInput("Сумма",searchBySum)
                .clickFilter();
        ArrayList<String> result1 = getAllRowsValuesByColName("Сумма");
        assertThat("ОШИБКА: отфильтрованные строки в поле \"Сумма\" содержат иное значение" ,result1, IsCollectionContaining.hasItem(searchBySum));
    }

    @BeforeClass
    public static void testPreparation(){
        try {
            setWorkingFolder();
            readInputDataFile("\\Input_data.txt");
            getLocators("\\BKS_locators.properties");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @After
    public void testCompletion(){
        tearDown();
    }
}
