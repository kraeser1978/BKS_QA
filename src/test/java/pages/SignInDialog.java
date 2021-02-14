package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import common.RegressionTest;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class SignInDialog extends RegressionTest {
    private static Logger logger = Logger.getLogger(SignInDialog.class.getSimpleName());

    private void typeByLetter(String text) throws AWTException {
        Robot rb = new Robot();
        for (int i = 0; i < text.length();i++){
            Character letter = text.charAt(i);
            int keyCode = letter.hashCode();
            if (needsShift(letter)) {
                rb.keyPress(KeyEvent.VK_SHIFT);
                rb.delay(50);
            }
            rb.keyPress(KeyEvent.getExtendedKeyCodeForChar(keyCode));
            rb.keyRelease(KeyEvent.getExtendedKeyCodeForChar(keyCode));
            rb.delay(50);
            if (needsShift(letter)) {
                rb.keyRelease(KeyEvent.VK_SHIFT);
                rb.delay(50);
            }
        }
    }

        public SignInDialog enterSignInCredentials(String userName) throws InterruptedException, AWTException {
            String userNameValue = null,userPass = "";
            if (userName.equals("testuser_name")) userNameValue = props.getPropertyValue("signin_popup_username");
//            if (userName.equals("admin_name")) userNameValue = props.getPropertyValue("signin_popup_username");
            //вводим имя пользователя
            typeByLetter(userNameValue);
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_TAB);
            rb.keyRelease(KeyEvent.VK_TAB);
            if (userNameValue.equals(props.getPropertyValue("signin_popup_username"))) userPass=props.getPropertyValue("signin_popup_password");
//            if (userNameValue.equals(props.adminName())) userPass=props.adminPass();
            typeByLetter(userPass);
            rb.keyPress(KeyEvent.VK_TAB);
            sleep(100);
            rb.keyRelease(KeyEvent.VK_TAB);
            sleep(100);
            rb.keyPress(KeyEvent.VK_ENTER);
            sleep(100);
            rb.keyRelease(KeyEvent.VK_ENTER);
            sleep(500);
            return this;
        }

        public SignInDialog enterLoginCredentials(String userName){
            String userNameValue = null,userPass = "";
            String userNameXpath = locators.getProperty("login_user");
            String userPassXpath = locators.getProperty("login_password");
            if (userName.equals("testuser_name")) {
                userNameValue = props.getPropertyValue("login_username");
                userPass = props.getPropertyValue("login_password");
            }
            $(By.xpath(userNameXpath)).shouldBe(Condition.enabled).setValue(userNameValue);
            $(By.xpath(userPassXpath)).shouldBe(Condition.enabled).setValue(userPass);
            clickButton("Войти");
            return this;
        }

        public BKSMainPage main(String userName) throws Exception {
            logger.log(Level.INFO,"запускаем приложение...");
            SetUp();
            SignInDialog signInDialog = open(props.getPropertyValue("bks_major_url"),SignInDialog.class);
            Thread.sleep(700);
            logger.log(Level.INFO,"авторизуемся...");
            signInDialog.enterSignInCredentials(userName);
            signInDialog.enterLoginCredentials(userName);
            return Selenide.page(BKSMainPage.class);
        }

    private boolean needsShift(Character c) {
        return Character.isUpperCase(c);
    }
}