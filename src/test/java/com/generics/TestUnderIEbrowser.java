package com.generics;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;


/**
 * Created by Alex Grischenko on 7/29/2017.
 */
public class TestUnderIEbrowser {

    final static Logger logger = Logger.getLogger (TestUnderIEbrowser.class);

    public static WebDriver driver;

    public static void getBrowserAndVersion() throws Exception {

        PropertyConfigurator.configure (".\\src\\main\\resources\\log4j.properties");

        //Initialize IE and IEDriver and set path to IEDriverServer.exe on local box
        File ieFile = new File ("C://SeleniumWebDrivers//Ie11//IEDriverServer.exe");
        System.setProperty ("webdriver.ie.driver", ieFile.getAbsolutePath ( ));

        //Instantiating Desired Capabilities on IE11
        DesiredCapabilities ieCaps = DesiredCapabilities.internetExplorer ( );

        ieCaps.setCapability (InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        ieCaps.internetExplorer ( ).setCapability ("ignoreProtectedModeSettings", true);
        ieCaps.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
        ieCaps.setJavascriptEnabled (true);
        //IEDesiredCapabilities.setCapability("requireWindowFocus", true);
        ieCaps.setCapability ("enablePersistentHover", false);

        /* LOGGING VIA setCapability
        //Enable logging in the driver you're using, select which log types you're interested in and the log level
        // Enabling all types of logs and collecting all log messages in IEWebDriver
        LoggingPreferences logs = new LoggingPreferences ( );
        logs.enable (LogType.BROWSER, Level.ALL);
        logs.enable (LogType.CLIENT, Level.ALL);
        logs.enable (LogType.DRIVER, Level.ALL);
        logs.enable (LogType.PERFORMANCE, Level.ALL);
        logs.enable (LogType.PROFILER, Level.ALL);
        logs.enable (LogType.SERVER, Level.ALL);

        //Then, after running the test you can collect the logs (below only the DRIVER logs are collected,
        // but you can do the same for any type of log
        Logs logs2 = TestUnderIEbrowser.driver.manage().logs();
        LogEntries logEntries = logs2.get(LogType.BROWSER);

        for (LogEntry logEntry : logEntries) {
            System.out.println(logEntry.getMessage ());
        }

        ieCaps.setCapability (CapabilityType.LOGGING_PREFS, logs);
        */

        //Navigating to desired web-site using setCapability
        ieCaps.setCapability (InternetExplorerDriver.INITIAL_BROWSER_URL, "http://test.net");

        //Clear the cache, cookies, history, and saved form data. Clears for all running instances of IE, including those started manually.
        ieCaps.setCapability ("ie.ensureCleanSession", true);

        //Instantiating IE driver with the list of Desired Capabilities
        driver = new InternetExplorerDriver (ieCaps);
        driver.manage ( ).window ( ).maximize ( );


        String browser_version = null;
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities ( );
        String browsername = cap.getBrowserName ( );

        // This block is to find out IE Version number
        if ("internet explorer".equalsIgnoreCase (browsername)) {
            String uAgent = (String) ((JavascriptExecutor) driver).executeScript ("return navigator.userAgent;");
            System.out.println ("This is the user agent i.e. uAgent: " + uAgent);

            //uAgent return as "MSIE 8.0 Windows" for IE8
            if (uAgent.contains ("MSIE") && uAgent.contains ("Windows")) {
                browser_version = uAgent.substring (uAgent.indexOf ("MSIE") + 5, uAgent.indexOf ("Windows") - 2);
                //System.out.println ("Internet Explorer 11 should be installed. Current version of IE browser is " + browser_version);
                logger.error ("Internet Explorer 11 should be installed. Current version of IE browser is " + browser_version);
                driver.quit ( );
            } else if (uAgent.contains ("Trident/7.0")) {
                browser_version = "11.0";
                //System.out.println ("IE11 browser was detected on this box.");
                logger.info ("IE11 browser was detected on this box.");


            } else {
                browser_version = "0.0";
                //System.out.println ("Internet Explorer was not detected on this box.");
                logger.error ("Internet Explorer was not detected on this box.");
                driver.quit ( );
            }
        } else {
            //Browser version for Firefox and Chrome
            browser_version = cap.getVersion ( );// .split(".")[0];
        }
        String browserversion = browser_version.substring (0, browser_version.indexOf ("."));
        logger.info ("Other detected browser & version: " + browsername + " " + browserversion);
        return;


    }


}
