package com.atishay.webui.pages.home;

import com.atishay.webui.pages.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Ati on 28-12-2015.
 */
public class HomePage extends AbstractPageObject {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.id("hd_user_widdget");
    }

    @FindBy(id = "hd_user_widdget")
    WebElement userWiddget;

    @FindBy(css = "#hd_user_widdget b")
    WebElement placeHolderWithUserName;

    @FindBy(css = "[href*='logout']")
    WebElement linkLogut;

    public boolean isUserLoggedIn(String userName){
        Actions actions = new Actions(driver);
        actions.moveToElement(userWiddget).build().perform();
        String loggedInUser = placeHolderWithUserName.getText();
        if (loggedInUser.equals(userName)) return true;
        else return false;
    }

    public void SignOut(){
        Actions actions = new Actions(driver);
        actions.moveToElement(userWiddget).build().perform();
        linkLogut.click();
    }
}
