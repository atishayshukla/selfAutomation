package com.atishay.webui.pages.home;

import com.atishay.webui.helpers.Util;
import com.atishay.webui.pages.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by Ati on 09-01-2016.
 */
public class GmailPage extends AbstractPageObject {
    public GmailPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.id("link-signup");
    }
    @FindBy (id = "link-signup")
    WebElement LinkSignIn;

    @FindBy (id = ":0")
    WebElement monthLocator;

    @FindBy (xpath = ".//*[@id='BirthMonth']/div[1]")
    WebElement monthSelector;


    public void clickSignInLink(){
        wait.until(ExpectedConditions.visibilityOf(LinkSignIn));
        LinkSignIn.click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("createaccount")));
    }
    public void selectMonthFromMonthList(){
        //wait.until(ExpectedConditions.visibilityOf(monthSelector));
        monthSelector.sendKeys("february??");
        //driver.findElement(By.xpath(".//*[@id='BirthMonth']/div[1]")).sendKeys("april");
        wait.until(ExpectedConditions.textToBePresentInElement(monthLocator,"February"));
    }
}
