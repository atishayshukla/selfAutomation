package com.atishay.webui.webdriver.tests;

import com.atishay.webui.pages.Login.LoginPage;
import com.atishay.webui.pages.home.HomePage;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ati on 28-12-2015.
 */
public class LoginTest extends BaseTest {
    private static final String LOGIN_USER = "atishayshukla@gmail.com";
    private static final String PASSWORD = "shikhashikha";
    private final JSONObject getConfig;

    public LoginTest() throws JSONException {
        getConfig = this.getConfigFile("LoginTest.json");
    }

    @Test
    public void successfulLogin() throws JSONException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSignInLink();
        loginPage.setInputUserName(getConfig.getString("username"));
        loginPage.setInputPassword(getConfig.getString("password"));
        HomePage homePage = loginPage.clickSignInButton();
        Assert.assertTrue(homePage.isUserLoggedIn(LOGIN_USER));
        homePage.SignOut();
    }

    @Test
    public void blankLoginAndPassword() throws JSONException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSignInLink();
        loginPage.setInputUserName("");
        loginPage.setInputPassword("");
        loginPage.clickSignInForErrors();
        Assert.assertEquals(loginPage.errorMessageOnBlankUsrAndPass(), "Please provide a valid email ");
        //loginPage.CloseSignInPopUp();
    }

    @Test
    public void correctUserNameAndInvalidPassword() throws JSONException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSignInLink();
        loginPage.setInputUserName(getConfig.getString("username"));
        loginPage.setInputPassword(getConfig.getString("invalidPassword"));
        loginPage.clickSignInForErrors();
        Assert.assertEquals(loginPage.errorMessagePresentOnInvalidPassword(), "Please enter a correct username and password. Note that both fields are"); //case-sensitive.");
        //loginPage.CloseSignInPopUp();
    }
}
