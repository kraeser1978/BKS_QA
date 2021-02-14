import com.codeborne.selenide.logevents.SelenideLogger;
import common.RegressionTest;
import org.junit.*;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.runners.MethodSorters;
import pages.BKSMainPage;
import pages.IncidentsPage;
import pages.IncidentsSettingsDialog;
import pages.SignInDialog;

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

    @BeforeClass
    public static void testPreparation(){
        try {
            setWorkingFolder();
            getLocators("\\BKS_locators.properties");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @After
    public void testCompletion(){
//        tearDown();
    }
}
