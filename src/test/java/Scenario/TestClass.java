package Scenario;

import Pages.MainPage;
import Pages.ResendPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.Issue;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import static util.TestConstants.WRIKE_EMAIL_DOMAIN;


public class TestClass {

    MainPage mainPage = null;
    ResendPage resendPage = null;

    @Before
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }


    @Test
    @Issue("Wrike.com site test")
    public void searchTest() throws Exception {
        // Move to main page
        mainPage = mainPage.toMainPage();
        // Click
        mainPage.clickGetStartedForFree();
        // filling random email
        String email = RandomStringUtils.randomAlphabetic(5, 12) + WRIKE_EMAIL_DOMAIN;
        mainPage.fillInTheEmail(email);
        // Check successful registration/Wait for loading the page
        resendPage = mainPage.clickCreateMyWrikeAccount();
        // Answering on QA section & checking with assertion
        resendPage.FillingTheQASection();
        // Clicking on resend email button & checking for the succesful result
        resendPage.ResendEMail();
        // Check for Twitter icon and url
        resendPage.checkSiteFooterForCorrectTwitterButton();
    }

    @After
    public void close() {
        mainPage.driver.close();
    }

}
