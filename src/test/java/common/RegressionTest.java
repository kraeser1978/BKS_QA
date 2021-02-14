package common;

import com.codeborne.selenide.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static java.util.logging.Level.INFO;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RegressionTest {
    private static Logger logger = Logger.getLogger(RegressionTest.class.getSimpleName());
    protected static WebDriver driver;
    static FileHandler fh = null;
    public static Props props;
    public static String currentWorkingDir;
    public static HashMap<String, String> dataInput = new HashMap<>();
    public static Properties locators = new Properties();

    public void clickLink(String linkName){
        String linkLocator = locators.getProperty("link_template1").replace("''","'" + linkName + "'");
        $(By.xpath(linkLocator)).waitUntil(Condition.enabled,10000).click();
    }

    public void clickButton(String buttonName){
        String button1Locator = locators.getProperty("button_template1").replace("''","'" + buttonName + "'");
        SelenideElement button1 = $(By.xpath(button1Locator));
        if (button1.exists())
            button1.waitUntil(Condition.enabled,8000).click();
        else {
            String button2Locator = locators.getProperty("button_template2").replace("''","'" + buttonName + "'");
            SelenideElement button2 = $(By.xpath(button2Locator));
            if (button2.exists())
                button2.waitUntil(Condition.enabled,8000).click();
        }
    }

    public static void readInputDataFile(String inputFileName) throws IOException {
        //считываем датасет
        String fieldName, fieldValue;
        dataInput.clear();
        String inputDataFilePath = currentWorkingDir + inputFileName;
        List<String> inputDataText = FileUtils.readLines(new File(inputDataFilePath), "UTF-8");
        for (int i = 0; i < inputDataText.size(); i++) {
            int delim = inputDataText.get(i).indexOf("=");
            fieldName = inputDataText.get(i).substring(0, delim).trim();
            fieldValue = inputDataText.get(i).substring(delim + 1).trim();
            dataInput.put(fieldName, fieldValue);
        }
    }

    public static void getLocators(String locatorsShortFileName) {
        //считываем локаторы
        String locatorsFilePath = currentWorkingDir + locatorsShortFileName;
        String locatorsText = null;
        try {
            locatorsText = FileUtils.readFileToString(new File(locatorsFilePath), "UTF-8");
            locators.load(new StringReader(locatorsText));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setWorkingFolder() throws URISyntaxException {
        File file = new File(RegressionTest.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        currentWorkingDir = file.getParentFile().getParentFile().getPath();
    }

    private void chromeDvrInit() throws InterruptedException, IOException {
        logger.log(INFO, "прибиваем процесс chromedriver.exe, если он остался от предыдущей сессии");
        Process process = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        process.waitFor();
        process.destroy();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extenstions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
        options.addArguments("--disable-default-apps");
        options.addArguments("--enable-precise-memory-info");
        System.setProperty("webdriver.chrome.driver", props.getPropertyValue("driver_path"));
        logger.log(INFO, "запускаем Chrome...");
        driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }


    public void loadProps() throws Exception {
        logger.log(INFO, "считываем параметры проекта из properties файлов...");
        String propsFilePath = currentWorkingDir + "\\BKS.properties";
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        props = new Props(paramsFile);
    }

    public void SetUp() throws Exception {
        //определяем индивидуальные параметры
        loadProps();
        //задаем режим логирования сообщений в лог файл
        logInit(currentWorkingDir + "\\bks_ver_1.0.log");
        //задаем путь к файлам скриншотов с ошибками в ходе выполнения тестов
        Configuration.reportsFolder = currentWorkingDir;
        chromeDvrInit();
    }

    public static void tearDown() {
        if (!driver.getWindowHandle().equals("")) {
            logger.log(INFO, "выходим из приложения...");
            fh.flush();
            fh.close();
            driver.close();
            closeWebDriver();
        }
    }

    public static void logInit(String lofFileName) {
        try {
            fh = new FileHandler(lofFileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger log = Logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        log.addHandler(fh);
        log.setLevel(Level.CONFIG);
    }
}