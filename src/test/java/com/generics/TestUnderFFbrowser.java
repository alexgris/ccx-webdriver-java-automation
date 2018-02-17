package com.generics;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

import static com.generics.TestUnderIEbrowser.driver;

/**
 * Created by Alex Grischenko on 7/29/2017.
 */
public class TestUnderFFbrowser {
/*    public static WebDriver driver;


    public static void setUpFF() throws Exception {

        File ffFile = new File("C://SeleniumWebDrivers//FireFox//geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", ffFile.getAbsolutePath());



        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("extensions.lastPlatformVersion", "50.0");
        firefoxProfile.setPreference("intl.accept_languages", "en-us");
        firefoxProfile.setAcceptUntrustedCertificates(true);
        firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
        driver = new FirefoxDriver(firefoxProfile);
        driver.navigate().to("http://ccxdemo.softprise.net");
       // if (driver.findElement(By.xpath("//span[@id='CompatibilityWindow_header_hd-textEl']"))){
        Thread.sleep(5000);
            driver.findElement(By.xpath("//button[@id='button-1042-btnEl']")).click();
        //}



        Capabilities ffCaps = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = ffCaps.getBrowserName();
        String browserVersion = ffCaps.getVersion();
        System.out.println("browserName = " + browserName); // firefox
        System.out.println("browserVersion = " + browserVersion); // 28.0
        //driver.quit();
    }*/
}

