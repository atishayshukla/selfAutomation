package com.atishay.webui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ati on 27-12-2015.
 */
public abstract class AbstractPageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected FluentWait<WebDriver> fluentWait;

    public AbstractPageObject(WebDriver driver){
        this(driver,20);
    }

    public AbstractPageObject(WebDriver driver, int timeOutInSec){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeOutInSec);
        this.fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeOutInSec, TimeUnit.SECONDS)
                .pollingEvery(5,TimeUnit.SECONDS)
                .ignoring(StaleElementReferenceException.class);
        PageFactory.initElements(driver, this);
        waitForPageToLoad();
        assertPageIsLoaded();
    }

    protected abstract By getUniqueElement();

    protected void waitForPageToLoad(){
        // Wait until the Page is loaded by Asserting that the unique element on page is visible
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getUniqueElement()));
        }catch (TimeoutException e){
            TimeoutException timeoutException = new TimeoutException("Timed Out Loading " + this.getClass().getSimpleName() + "\n" + e.getMessage());
            timeoutException.setStackTrace(e.getStackTrace());
            throw timeoutException;
        }
    }

    protected void assertPageIsLoaded() {
        Assert.assertTrue(driver.findElements(getUniqueElement()).size() > 0,
                "Unique Element" + getUniqueElement().toString() + "not Found for" + this.getClass().getSimpleName());
    }
}
