package com.atishay.webui.testng;

import com.atishay.webui.webdriver.tests.BaseTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Ati on 27-12-2015.
 */
public class Listener implements IReporter{
    private ExtentReports extentReports;
    public static int EXTENT_FLAG = 0;
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extentReports = new ExtentReports(outputDirectory + File.separator + "SelfAutomation.html",true);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }

        for (String s : Reporter.getOutput()) {
            extentReports.setTestRunnerOutput(s);
        }

        extentReports.flush();
        extentReports.close();
    }
    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extentReports.startTest(result.getMethod().getMethodName());

                test.getTest().setStartedTime(getTime(result.getStartMillis()));
                test.getTest().setEndedTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                    String image = test.addScreenCapture(BaseTest.takeScreenshot(result.getName()));
                    test.log(LogStatus.FAIL,"Test Fail",image);
                    EXTENT_FLAG = 1;
                }

                else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                extentReports.endTest(test);
            }
        }
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
