package com.generics;

import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static com.generics.TestUnderIEbrowser.driver;


/**
 * Created by Alex Grischenko on 9/4/2017.
 */
public class Waitings {

    final static Logger logger = Logger.getLogger (Waitings.class);

    // Waiting for the LOGIN page to load and calculating the time in seconds it takes the LOGIN page to load
    public static void waitForAjaxComplete(int maxSeconds, String JScode, String JSresult, String PageName) throws Exception {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long startTime = System.nanoTime ( );

        try {
            for (int i = 1; i <= maxSeconds; i++) {

                if (((String) js.executeScript (JScode)).equals (JSresult)) {
                    long endTime = System.nanoTime ( );
                    double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;
                    logger.info ("TOTAL TIME that " + PageName + " page was loading: " + elapsedTimeInSeconds + " seconds.");
                    return;
                }
                Thread.sleep (1000);
            }

        } catch (NullPointerException e) {
            logger.warn (PageName + " PAGE has timed out after " + maxSeconds + " seconds");
            //throw new RuntimeException ("WARNING: Timed out after " + maxSeconds + " seconds");
        }
    }





    public static void waitForExtAjaxIsLoading(int maxSeconds, String PageName) throws Exception {
        boolean is_ajax_complete = false;
        long startTime = System.nanoTime ( );

        for (int i = 1; i <= maxSeconds; i++) {

            is_ajax_complete = (boolean) ((JavascriptExecutor) driver).executeScript ("return !Ext.Ajax.isLoading();");
            if (is_ajax_complete) {

                long endTime = System.nanoTime ( );
                double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;
                logger.info ("TOTAL TIME that the " + PageName + " page was loading: " + elapsedTimeInSeconds + " seconds.");

                return;
            }
            Thread.sleep(1000);
        }
        throw new RuntimeException("Timed out on " + PageName+ " after " + maxSeconds + " seconds");
    }




    public static void waitTillDescendentElementsAvailable(int maxSeconds, String XPath, String webElementAttribute, String attributeValueToSearch, String PageName) throws Exception {
        long startTime = System.nanoTime ( );

        for (int i = 1; i <= maxSeconds; i++) {

            //Select the parent web element
            List<WebElement> elm = driver.findElements (By.xpath (XPath));

            //Loop the selected web element descendants
            for (WebElement el1 : elm) {
                String paramName = el1.getAttribute (webElementAttribute);
               // System.out.println ("Object parameter name: " + paramName);

                if (paramName.equals (attributeValueToSearch)) {

                    long endTime = System.nanoTime ( );
                    double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;

                    logger.info ("TOTAL TIME that the " + PageName + " page was loading: " + elapsedTimeInSeconds + " seconds.");

                    return;


                }else {
                    Thread.sleep(1000);
                }

            }

        }
        throw new RuntimeException("Timed out on " + PageName+ " after " + maxSeconds + " seconds");
    }



    public static void waitForExtAjaxIsReadyState(int maxSeconds, String PageName) throws Exception {
        boolean is_ajax_complete = false;
        long startTime = System.nanoTime ( );

        for (int i = 1; i <= maxSeconds; i++) {

            is_ajax_complete = (boolean) ((JavascriptExecutor) driver).executeScript ("return document.readyState").equals ("complete");
            if (is_ajax_complete) {

                long endTime = System.nanoTime ( );
                double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;
                logger.info ("TOTAL TIME that the " + PageName + " page was loading: " + elapsedTimeInSeconds + " seconds.");

                return;
            }
            Thread.sleep(1000);
        }
        throw new RuntimeException("Timed out on " + PageName+ " after " + maxSeconds + " seconds");
    }



    public static void explicitWaitsUntilElementPresent(int maxSeconds, String elementXPath, String PageName) throws Exception {

        WebDriverWait wait = new WebDriverWait (driver, maxSeconds); // seconds
        Actions action = new Actions(driver);



        long startTime = System.nanoTime ( );

        try {
            // action.moveToElement(driver.findElement(By.xpath (elemXPath0))).build().perform();

            wait.until (ExpectedConditions.presenceOfElementLocated (By.xpath (elementXPath)));


            long endTime = System.nanoTime ( );
            double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;
            logger.info ("TOTAL TIME that " + PageName + " was loading: " + elapsedTimeInSeconds + " seconds.");
            return;
        } catch (NullPointerException e) {
            logger.warn (PageName + " page has timed out after " + maxSeconds + " seconds");

        }
    }



    public static void explicitWaitsUntilElementLocated(int maxSeconds, String elementXPath, String PageName) throws Exception {

        WebDriverWait wait = new WebDriverWait (driver, maxSeconds); // seconds
        Actions action = new Actions(driver);



        long startTime = System.nanoTime ( );

        try {
            // action.moveToElement(driver.findElement(By.xpath (elemXPath0))).build().perform();

            wait.until (ExpectedConditions.visibilityOfElementLocated (By.xpath (elementXPath)));


            long endTime = System.nanoTime ( );
            double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;

            logger.info ("TOTAL TIME that " + PageName + " page was loading: " + elapsedTimeInSeconds + " seconds.");
            return;



        } catch (NullPointerException e) {
            logger.warn (PageName + " page has timed out after " + maxSeconds + " seconds");

        }
    }



    public static void explicitWaitsUntilElementVisible(int maxSeconds, String elementXPath, String PageName) throws Exception {

        WebDriverWait wait = new WebDriverWait (driver, maxSeconds); // seconds
        Actions action = new Actions(driver);



        long startTime = System.nanoTime ( );

        try {
            // action.moveToElement(driver.findElement(By.xpath (elemXPath0))).build().perform();

            wait.until (ExpectedConditions.visibilityOfElementLocated (By.xpath (elementXPath)));


            long endTime = System.nanoTime ( );
            double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;
            logger.info ("TOTAL TIME that " + PageName + " was loading: " + elapsedTimeInSeconds + " seconds.");
            return;
        } catch (NullPointerException e) {
            logger.warn (PageName + " page has timed out after " + maxSeconds + " seconds");

        }
    }



    public static void webDriverWaitTillFrameIsAvailable (String frameLocator){

        //(new WebDriverWait (driver, 5)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        (new WebDriverWait (driver, 5)).until(ExpectedConditions.visibilityOfElementLocated (By.xpath (frameLocator)));
    }
}
