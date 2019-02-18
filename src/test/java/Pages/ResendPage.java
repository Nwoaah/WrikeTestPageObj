package Pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.TwitterIconMatching;
import java.util.List;
import java.util.Random;
import static util.TestConstants.*;


public class ResendPage extends BasePage {

    private static final By answerVeryInterested = By.xpath("//button[contains(text(),'Very interested')]");
    private static final By answerJustLooking = By.xpath("//button[contains(text(),'Just looking')]");
    private static final By resendEmailButton = By.xpath("//div[@class='section section-resend-main section-resend-main-va section-resend-main--survey']//button[contains(text(),'Resend email')]");
    private static final By waitResendButton = By.xpath(" //span[contains(text(),'again.')]");
    private static final By surveySuccess = By.className("survey-success");
    private static final By checkResendEmailButton = By.className("wg-btn-progress");
    private static final By switchInput = By.className("switch__input");
    private static final By form = By.className("survey");
    private static final By FOOTER_SOCIAL_ITEM = By.className("wg-footer__social-item");
    private static final By FOOTER_SOCIAL_LINK = By.className("wg-footer__social-link");
    private static final By divTeamMembers = By.cssSelector("div[data-code='team_members']");
    private static final By ButtonsOnForm = By.cssSelector("button");
    private static final By divPrimaryBusiness = By.cssSelector("div[data-code='primary_business']");
    private static final By SubmitButton = By.cssSelector(".section-resend-main .submit");
    private static final By USE = By.cssSelector("use");

    public ResendPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void waitForPageToLoad() {
        //wait for page
        super.waitForPageToLoad();
        wait.until(ExpectedConditions.urlToBe(WRIKE_URL_RESEND));
    }

    @Step("Fill in the Q&A section at the left part of page (like random generated answers) + check with assertion that your answers are submitted;")
    // randomly generates answers to the QA section.
    public void FillingTheQASection() {
        Random random = new Random();

        if (random.nextInt(2) == 1) {
            this.click(answerVeryInterested);
        } else {
            this.click(answerJustLooking);
        }

        WebElement elements = this.findElement(divTeamMembers);
        List<WebElement> elementList = elements.findElements(ButtonsOnForm);
        int n = random.nextInt(elementList.size());
        int counter = 0;
        for (WebElement element : elementList) {
            if (n == counter) {
                element.click();
            }
            counter++;
        }

        elements = this.findElement(divPrimaryBusiness);
        elementList = elements.findElements(ButtonsOnForm);
        n = random.nextInt(elementList.size());
        counter = 0;

        for (WebElement element : elementList) {
            if (n == counter) {
                element.click();
                if (n == 2) {
                    element.findElement(switchInput).sendKeys(RandomStringUtils.randomAlphabetic(20));
                }
            }
            counter++;
        }

        this.submitTheQASection();
        Assert.assertEquals(driver.findElement(surveySuccess).getAttribute(STYLE_ATTRIBUTE), DISPLAY_BLOCK);
    }

    private void submitTheQASection() {
        this.click(SubmitButton);
        this.wait.until(ExpectedConditions.attributeToBeNotEmpty(this.driver.findElement(form), STYLE_ATTRIBUTE));
    }

    @Step("Click on \"Resend email\" + check it with assertion;")
    public void ResendEMail() {
        this.click(resendEmailButton);
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(waitResendButton));
        Assert.assertEquals(driver.findElement(checkResendEmailButton).getAttribute(STYLE_ATTRIBUTE), DISPLAY_BLOCK_RESEND_EMAIL);
    }

    @Step("Check that section \"Follow us\" at the site footer contains the \"Twitter\" button that leads to the correct url and has the correct icon.")
    public void checkSiteFooterForCorrectTwitterButton() {
        boolean flag = false;

        List<WebElement> li_All = this.driver.findElements(FOOTER_SOCIAL_ITEM);

        for (WebElement element : li_All) {
            String urlToSite = element.findElement(FOOTER_SOCIAL_LINK).getAttribute(HREF);
            String imgLink = element.findElement(USE).getAttribute(XLINK_HREF);
            if (urlToSite.equals(TWITTER_COM_WRIKE)) {
                if (imgLink.equals(PATH_TO_TWITTER_ICON)) {
                    flag = TwitterIconMatching.isItTwitterIcon();
                }
            }
        }
        Assert.assertTrue(flag);
    }

}


