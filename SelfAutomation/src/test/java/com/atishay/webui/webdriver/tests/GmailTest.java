package com.atishay.webui.webdriver.tests;

import com.atishay.webui.pages.home.GmailPage;
import org.testng.annotations.Test;

/**
 * Created by Ati on 09-01-2016.
 */
public class GmailTest extends BaseTest {

    @Test
    public void gmailTest(){
        GmailPage gmailPage = new GmailPage(driver);
        gmailPage.clickSignInLink();
        gmailPage.selectMonthFromMonthList();
    }
}
