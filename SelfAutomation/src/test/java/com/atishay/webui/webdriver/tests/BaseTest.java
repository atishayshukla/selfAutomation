package com.atishay.webui.webdriver.tests;

import com.atishay.webui.testng.Listener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IResultMap;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ati on 27-12-2015.
 */
public class BaseTest {
    protected static final boolean REMOTE_DRIVER = Boolean.valueOf(System.getProperty("REMOTE_DRIVER", "false"));
    protected static final String BROWSER = System.getProperty("BROWSER","firefox");
    protected static final String SELENIUM_HOST = System.getProperty("SELENIUM_HOST", "10.3.1.191");
    protected static final int SELENIUM_PORT = 4444;
    protected static final String URL = System.getProperty("URL","www.goibibo.com");
    public static final Map<String,String> EXTENT_REPORT_SCREENSHOT_MAP = new HashMap<>();

    protected static RemoteWebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest test;

    @BeforeClass
    public void suiteSetup() throws Exception {
        if (REMOTE_DRIVER) {
            DesiredCapabilities capabilities;
            switch (BROWSER) {
                case "firefox":
                    capabilities = DesiredCapabilities.firefox();
                    break;
                case "internetExplorer":
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    break;
                case "chrome":
                    capabilities = DesiredCapabilities.chrome();
                    break;
                default:
                    throw new RuntimeException("Browser type unsupported");
            }
            driver = new RemoteWebDriver(
                    new URL("http://" + SELENIUM_HOST + ":" + SELENIUM_PORT + "/wd/hub"),
                    capabilities);
            driver.setFileDetector(new LocalFileDetector());
        } else {
            switch (BROWSER) {
                case "firefox":
                    //DesiredCapabilities capabilities = DesiredCapabilities.firefox();

                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    String path;
                    if (System.getProperty("os.name").contains("Windows")) {
                        path = "C://tools//chromedriver.exe";
                    } else {
                        path = "webui-tests/lib/chromedriver";
                    }
                    System.setProperty("webdriver.chrome.driver", path);
                    driver = new ChromeDriver();
                    break;
                case "internetExplorer":
                    String iePath;
                    if (System.getProperty("os.name").contains("Windows")) {
                        iePath = "C://tools//IEDriverServer.exe";
                    } else {
                        iePath = "webui-tests/lib/chromedriver";
                    }
                    System.setProperty("webdriver.ie.driver", iePath);
                    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                    //capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    driver = new InternetExplorerDriver(capabilities);
                    break;
                default:
                    throw new RuntimeException("Browser type unsupported");
            }
        }
        driver.manage().window().maximize();
    }
    @BeforeMethod
    public void testSetup(){
        driver.get("https://" + URL);
    }
    @AfterMethod
    public void testTearDown(ITestResult result){
        if (!result.isSuccess()){
            EXTENT_REPORT_SCREENSHOT_MAP.put(result.getName(),takeScreenshot(result.getName()));
        }
        driver.manage().deleteAllCookies();
    }
    @AfterClass
    public void suiteTearDown(){

        driver.quit();
    }


    protected JSONObject getConfigFile(String fileName) throws JSONException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("testdata/" + fileName);
        return new JSONObject(new JSONTokener(new InputStreamReader(inputStream)));
    }

    public static String takeScreenshot(String testName) {
        SimpleDateFormat sDate = new SimpleDateFormat("HH_mm_ss");
        Date now = new Date();
        String currentTime = sDate.format(now);
        File DestFile = new File("build/reports/" + BROWSER + "/" + testName + "_" + BROWSER + "-" + currentTime + ".png");
        //WebDriver augmentedDriver = new Augmenter().augment(driver);
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, DestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DestFile.getAbsolutePath();
    }

    public static void getTestResults(ITestResult result){
        Map<String,String> resultMap = new HashMap<>();
        while (!result.isSuccess()){
            String testName = result.getName();
            String imgPath = takeScreenshot(testName);
            resultMap.put(testName,imgPath);
        }
        for (int i=0; i<resultMap.entrySet().size(); i++){
            System.out.println(resultMap.get(i).toString());
        }
    }
}
