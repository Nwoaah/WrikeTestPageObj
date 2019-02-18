package Pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.TestConstants;

import static util.TestConstants.WRIKE_URL_RESEND;

public class MainPage extends BasePage {

    public static final By getStartedForFreeBtn = By.xpath("//div[@class='r']//button[@type='submit'][contains(text(),'Get started')]");
    public static final By newEmailModalText = By.cssSelector(".modal-form-trial__input");
    public static final By newEmailSubmitModalBtn = By.cssSelector(".modal-form-trial__submit");


    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.urlToBe(TestConstants.WRIKE_URL_MAIN));
        super.waitForPageToLoad();
    }

    @Step("Open main page wrike.com")
    public MainPage toMainPage() {
        this.driver.get(TestConstants.WRIKE_URL_MAIN);
        return new MainPage(this.driver);
    }

    @Step("Click \"Get started for free\" button near \"Login\" button;")
    public void clickGetStartedForFree() {
        this.click(MainPage.getStartedForFreeBtn);
    }

    @Step("Fill in the email field with random generated value of email with mask <random_text>+wpt@wriketask.qaa")
    public void fillInTheEmail(String email) {
        // Fill email form
        this.writeText(MainPage.newEmailModalText, email);
    }

    @Step("Click on \"Create my Wrike account\" button + check with assertion that you are moved to the next page;")
    public ResendPage clickCreateMyWrikeAccount() {
        this.click(MainPage.newEmailSubmitModalBtn);
        ResendPage resendPage = new ResendPage(driver);
        resendPage.waitForPageToLoad();
        Assert.assertEquals(driver.getCurrentUrl(), WRIKE_URL_RESEND);
        return resendPage;
    }

}


