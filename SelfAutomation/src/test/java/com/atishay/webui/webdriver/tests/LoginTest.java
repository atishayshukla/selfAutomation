package com.atishay.webui.webdriver.tests;

import com.atishay.webui.pages.Login.LoginPage;
import com.atishay.webui.pages.home.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ati on 28-12-2015.
 */
public class LoginTest extends BaseTest {
    private static final String LOGIN_USER = "atishayshukla@gmail.com";
    private static final String PASSWORD = "shikhashikha";
    @Test
    public void successfulLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSignInLink();
        loginPage.setInputUserName(LOGIN_USER);
        loginPage.setInputPassword(PASSWORD);
        HomePage homePage = loginPage.clickSignInButton();
        Assert.assertTrue(homePage.isUserLoggedIn(LOGIN_USER));
        homePage.SignOut();
    }
}
