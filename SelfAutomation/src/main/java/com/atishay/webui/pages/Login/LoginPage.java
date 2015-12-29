package com.atishay.webui.pages.Login;

import com.atishay.webui.helpers.Util;
import com.atishay.webui.pages.AbstractPageObject;
import com.atishay.webui.pages.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Ati on 28-12-2015.
 */
public class LoginPage extends AbstractPageObject {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.id("searchWidgetNew");
    }

    @FindBy(css = "iframe[id*='webklipper']")
    WebElement notificationCenterContainer;

    @FindBy(id = "authiframe")
    WebElement authFrame;

    @FindBy(css = "#hdr_user_signin a:first-child")
    WebElement linkSignIn;

    @FindBy(name = "username")
    WebElement inputUserName;

    @FindBy(id = "id_password")
    WebElement inputPassword;

    @FindBy(xpath = "(.//*[@id='signinBtn'])[1]")
    WebElement signInButton;

    public void clickSignInLink(){
        //wait.until(ExpectedConditions.visibilityOf(notificationCenterContainer));
        wait.until(ExpectedConditions.visibilityOf(linkSignIn));
        linkSignIn.click();
        driver.switchTo().frame(authFrame);
    }

    public void setInputUserName(String userName){
        inputUserName.click();
        inputUserName.clear();
        inputUserName.sendKeys(userName);
        //Util.pause(2);
    }

    public void setInputPassword(String password){
        inputPassword.click();
        inputPassword.clear();
        inputPassword.sendKeys(password);
        //Util.pause(2);
    }

    public HomePage clickSignInButton(){
        //wait.until(ExpectedConditions.visibilityOf(signInButton));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", signInButton);
        //signInButton.click();
        return new HomePage(driver);
    }

}
