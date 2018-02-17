package org.project;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.NoSuchElementException;

import static com.generics.CalendarsDatesTimes.*;
import static com.generics.ComparingScreenshots.subtractImages;
import static com.generics.ComparingWebElements.compareWebElementsInsideParentElement;
import static com.generics.ComparingWebElements.compareWebElementsOnPage;
import static com.generics.CreatingEditingFiles.insertingScreenshotLinksIntoLoggerHTMLfile;
import static com.generics.TestUnderIEbrowser.driver;
import static com.generics.VerifyInternetConnection.checkIntConnection;
import static com.generics.Waitings.*;
import static com.generics.WebElementScreenshot.captureWebElementScreenshot;


/**
 * Created by Alex Grischenko on 7/25/2017.
 */


public class CreateNewTenderTest {


    final static Logger logger = Logger.getLogger (CreateNewTenderTest.class);


    @BeforeTest
    public void setUp() throws Exception {


        //Deleting old image files from 'current_images' folder
        File file = new File (".\\src\\main\\resources\\current_images");
        String[] myFiles;
        if (file.isDirectory ( )) {

            try {
                myFiles = file.list ( );
                for (int i = 0; i < myFiles.length; i++) {
                    File myFile = new File (file, myFiles[i]);
                    myFile.delete ( );
                }
            } catch (Exception e) {
                logger.error ("Unable to delete old image files from 'current_images' folder: " + e.getMessage ( ));
            }
        }

        //Deleting old image files from 'difference_images' folder
        File file2 = new File (".\\src\\main\\resources\\difference_images");
        String[] myFiles2;
        if (file2.isDirectory ( )) {
            try {
                myFiles2 = file2.list ( );
                for (int k = 0; k < myFiles2.length; k++) {
                    File myFile2 = new File (file2, myFiles2[k]);
                    myFile2.delete ( );
                }
            } catch (Exception e) {
                logger.error ("Unable to delete old image files from 'difference_images' folder: " + e.getMessage ( ));
            }
        }


        //Deleting old log4j HTML and log files from 'log_files' folder
        File file3 = new File (".\\src\\main\\resources\\log_files");
        String[] myFiles3;
        if (file3.isDirectory ( )) {
            try {
                LogManager.resetConfiguration ( );
                myFiles3 = file3.list ( );
                for (int l = 0; l < myFiles3.length; l++) {
                    File myFile4 = new File (file3, myFiles3[l]);
                    myFile4.delete ( );
                }
            } catch (Exception e) {
                logger.error ("Unable to delete old LOG files from 'log_files' folder: " + e.getMessage ( ));
            }
        }


    }

    //OPEN "LOGIN" PAGE
    @Test(priority = 0)
    public void openCargoclixLoginPage() throws Exception {

        String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Login_Baseline_FullSize.png";
        String CurrentImg = ".\\src\\main\\resources\\current_images\\Login_Current_FullSize.png";
        String DiffImg = ".\\src\\main\\resources\\difference_images\\Login_Difference_FullSize.png";

        //Path to the Log4j logger config file
        PropertyConfigurator.configure (".\\src\\main\\resources\\log4j.properties");
        //PropertyConfigurator.configure(CreateNewTenderTest.class.getClassLoader().getResource(".\\src\\main\\resources\\log4j.properties"));


        //Check if Internet connection is available
        if (checkIntConnection ("ccxdemo.softprise.net")) {

            waitForAjaxComplete (20, "return document.readyState", "complete", "LOGIN");


            //If "Login" window opens - continue the test
            if (driver.findElement (By.xpath ("//*[@id='LoginWindow_header_hd-textEl']")).getText ( ).equals ("Please, enter your username and password")) {

                try {
                    compareWebElementsOnPage (".\\src\\main\\resources\\LoginWebElements.txt", "LOGIN");
                } catch (IOException e) {
                    logger.error ("Unable to run the 'compareWebElementsOnPage' method on LOGIN page: " + e.getMessage ( ));
                }


                try {
                    captureWebElementScreenshot ("//div[@id='ext-gen1058']", "Login_Current_FullSize.", "LOGIN");

                } catch (Exception e) {
                    logger.error ("Cannot take a screenshot of LOGIN page: " + e.getMessage ( ));
                    e.printStackTrace ( );
                }

                try {
                    //check that the files exist in folders...
                    subtractImages (BaseLineImg, CurrentImg, DiffImg);
                } catch (IOException e) {
                    logger.error ("Unable to run the 'compareWebElementsOnLoginPage' method: " + e.getMessage ( ));
                }


                try {
                    driver.findElement (By.xpath ("//input[@id='txtUsername-inputEl']")).clear ( );
                    driver.findElement (By.xpath ("//input[@id='txtUsername-inputEl']")).sendKeys ("s03");
                } catch (NoSuchElementException e) {
                    logger.error ("No such element - USERNAME field was not found: " + e.getMessage ( ));
                }

                try {
                    driver.findElement (By.xpath ("//input[@id='txtPassword-inputEl']")).clear ( );
                    driver.findElement (By.xpath ("//input[@id='txtPassword-inputEl']")).sendKeys ("admin");
                } catch (NoSuchElementException e) {
                    logger.error ("No such element - PASSWORD field was not found: " + e.getMessage ( ));
                }

            } else {
                // If "Compatibility Test Results" window opens - stop the test and log the error
                if (driver.findElement (By.xpath ("//*[@id='CompatibilityWindow_header_hd-textEl']")).getText ( ).equals ("Compatibility test results")) {
                    logger.error ("Not all required components are installed to run the application. Please check the 'Compatibility Test Results' form for more details. Or in IE browser go to 'Tools' -> 'Internet options' -> 'Privacy' tab and set the slider to MEDIUM.");
                    driver.quit ( );
                }
            }
        } else {
            logger.warn ("LOGIN page was not loaded. Make sure to establish INTERNET connection and try again.");

        }
    }


    //OPEN "ALL TENDERS" TAB and CHECK THAT ENGLISH VERSION IS TURNED ON
    @Test(priority = 1)
    public void openCargoclixAllTendersTabAndCheckLanguage() throws Exception {

        String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\AllTenders_Baseline_FullSize.png";
        String CurrentImg = ".\\src\\main\\resources\\current_images\\AllTenders_Current_FullSize.png";
        String DiffImg = ".\\src\\main\\resources\\difference_images\\AllTenders_Difference_FullSize.png";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //  WebElement fr = driver.findElement (By.xpath ("//body[@id='ext-gen1018']//div[@id='tpMain']//div[@id='tpMain-body']//div[@id='mmCtl13']//div[@id='mmCtl13-body']//iframe[@id='container-1022']"));

        try {
            //Checking if "Login" button is displayed and clicking on it
            assert (driver.findElement (By.xpath ("//button[@id='LoginButton-btnEl']")).getText ( ).contains ("Login"));
            driver.findElement (By.xpath ("//button[@id='LoginButton-btnEl']")).click ( );


            //IMPORTANT!!! - FOR IE to run this code make the following pre-sets i.e
            // https://answers.microsoft.com/en-us/ie/forum/ie11-windows_7/featurebfcache-in-ie11/b461e166-c1e0-4171-be93-aec65e9de727?auth=1

            //Waiting till the Shipper's main window is downloaded
            explicitWaitsUntilElementLocated (20, "//iframe[@name='mmCtl13_IFrame']", "ALL TENDERS");


            //When using an iframe, you will first have to switch to the iframe, before selecting the elements of that iframe.
            driver.switchTo ( ).frame ("mmCtl13_IFrame");

            //Waiting till the 'All tenders' grid is downloaded in the Shipper's main window
            explicitWaitsUntilElementLocated (20, "//*[@class='row-imagecommand   icon-report ']", "SHIPPER's GRID");

            //Exit iframe to have access to the main window's web-elements
            driver.switchTo ( ).defaultContent ( );


            //"//body[@id='ext-gen1018']", "AllTenders_Current_FullSize.",


            //Checking whether English version is selected. If not - then select English version
            if (driver.findElement (By.xpath ("//*[@id='btnLogout-btnInnerEl']")).getText ( ).contains ("Logout")) {

                try {
                    // writingPageElementsIntoTXTfile("ALL TENDERS");
                    compareWebElementsOnPage (".\\src\\main\\resources\\AllTendersWebElements.txt", "ALL TENDERS");

                } catch (IOException e) {
                    logger.error ("Unable to run the 'compareWebElementsOnPage' method on ALL TENDERS page: " + e.getMessage ( ));
                }


                try {
                    captureWebElementScreenshot ("//body[@id='ext-gen1018']", "AllTenders_Current_FullSize.", "ALL TENDERS");

                } catch (Exception e) {
                    logger.error ("Cannot take a screenshot of ALL TENDERS page: " + e.getMessage ( ));
                    e.printStackTrace ( );
                }

                try {
                    //check that the files exist in folders and compare pixel-by-pixel
                    subtractImages (BaseLineImg, CurrentImg, DiffImg);
                } catch (IOException e4) {
                    logger.error ("Unable to run the 'subtractImages' method on ALL TENDERS tab: " + e4.getMessage ( ));
                }


            } else {
                logger.info ("Default GUI language is not ENGLISH. Changing it to ENGLISH.");

                //Clicking on "Account" button in the toolbar
                driver.findElement (By.xpath ("//body[@id='ext-gen1018']//div[@id='TopToolbar']//div[@id='TopToolbar-innerCt']//div[@id='TopToolbar-targetEl']//div[@id='mmAccount']//em[@id='mmAccount-btnWrap']//button[@id='mmAccount-btnEl']")).click ( );

                Thread.sleep (2000); //add a wait

                //Waiting for the "Account" dropdown to open with all its items being visible
                //explicitWaitsUntilElementPresent (10, "//body[@id='ext-gen1018']//div[@id='mmCtl22']//div[@id='mmCtl22-body']//div[@id='mmCtl22-innerCt']//div[@id='mmCtl22-targetEl']//div[@id='mmCtl24']//a[@id='mmCtl24-itemEl']", "TENDER droplist");


                try {
                    Actions action = new Actions (driver);

                    //Open language selection submenu
                    action.moveToElement (driver.findElement (By.xpath ("//body[@id='ext-gen1018']//div[@id='mmCtl22']//div[@id='mmCtl22-body']//div[@id='mmCtl22-innerCt']//div[@id='mmCtl22-targetEl']//div[@id='mmCtl24']//a[@id='mmCtl24-itemEl']"))).build ( ).perform ( );

                    Thread.sleep (2000); //add a wait

                    //Waiting for the "Languages" submenu to open with all its items being visible
                    //explicitWaitsUntilElementPresent (10, "//*[@id='2-itemEl']", "TENDER droplist -> Languages submenu");


                    //Clicking on "English" item to set the application's language
                    js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//body[@id='ext-gen1018']//div[@id='mmCtl23']//div[@id='mmCtl23-body']//div[@id='mmCtl23-innerCt']//div[@id='mmCtl23-targetEl']//div[@id='2']//a[@id='2-itemEl']")));


                } catch (Exception e1) {
                    logger.error ("UNABLE to change GUI language to ENGLISH: " + e1.getMessage ( ));
                    driver.quit ( );
                }


                driver.switchTo ( ).defaultContent ( );

                //Waiting till the Shipper's main window is downloaded after changing the language
                explicitWaitsUntilElementLocated (20, "//iframe[@name='mmCtl13_IFrame']", "ALL TENDERS");
                //"//body[@id='ext-gen1018']//div[@id='tpMain']//div[@id='tpMain-body']//div[@id='mmCtl13']//div[@id='mmCtl13-body']//iframe[@id='container-1022']

                driver.switchTo ( ).defaultContent ( );

                //When using an iframe, you will first have to switch to the iframe, before selecting the elements of that iframe.
                //driver.switchTo ( ).frame ("mmCtl13_IFrame");
                driver.switchTo ( ).frame (0);

                //driver.findElement(By.xpath("//*[@id='container-1022']")).click();


                //assert (driver.findElement (By.xpath ("//*[@id='mmTenders-btnInnerEl']")).getText ( ).contains ("Tenders"));

                //Waiting till the 'All tenders' grid is downloaded in the Shipper's main window after changing the language
                explicitWaitsUntilElementLocated (20, "//*[@class='row-imagecommand   icon-report ']", "SHIPPER's GRID");


                //Exit iframe to have access to the main window's web-elements
                driver.switchTo ( ).defaultContent ( );


                try {

                    compareWebElementsOnPage (".\\src\\main\\resources\\AllTendersWebElements.txt", "ALL TENDERS");

                } catch (IOException e) {
                    logger.error ("Unable to run the 'compareWebElementsOnPage' method on ALL Tenders page: " + e.getMessage ( ));
                }


                try {
                    captureWebElementScreenshot ("//body[@id='ext-gen1018']", "AllTenders_Current_FullSize.", "ALL TENDERS");

                } catch (Exception e) {
                    logger.error ("Cannot take a screenshot of ALL TENDERS page: " + e.getMessage ( ));
                    e.printStackTrace ( );
                }

                try {
                    //check that the files exist in folders and compare pixel-by-pixel
                    subtractImages (BaseLineImg, CurrentImg, DiffImg);
                } catch (IOException e4) {
                    logger.error ("Unable to run the 'subtractImages' method on ALL TENDERS tab: " + e4.getMessage ( ));
                }

            }


        } catch (AssertionError e) {
            logger.error ("No such element - LOGIN button was not found: " + e.getMessage ( ));

        }
    }


    //FILLING OUT STEP1 - "BASIC INFORMATION"
    @Test(priority = 2)
    public void openCreateTenderWizardStep1_BasicInformation() throws Exception {

        String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step1_BasicInformation_Baseline_FullSize.png";
        String CurrentImg = ".\\src\\main\\resources\\current_images\\Step1_BasicInformation_Current_FullSize.png";
        String DiffImg = ".\\src\\main\\resources\\difference_images\\Step1_BasicInformation_Difference_FullSize.png";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        DecimalFormat formatter = new DecimalFormat ("00");


        try {
            //Open "Tenders" selection submenu
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='mmTenders-btnEl']")));
            //body[@id='ext-gen1018']//div[@id='TopToolbar']//div[@id='TopToolbar-innerCt']//div[@id='TopToolbar-targetEl']//div[@id='mmTenders']//em[@id='mmTenders-btnWrap']//button[@id='mmTenders-btnEl']//span[@id='mmTenders-btnInnerEl']

            Thread.sleep (1000); //add a wait


            //Clicking on "Start Tender" item to set the application's language
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='mmStartTender-itemEl']")));
            //body[@id='ext-gen1018']//div[@id='mmCtl10']//div[@id='mmCtl10-body']//div[@id='mmCtl10-innerCt']//div[@id='mmCtl10-targetEl']//div[@id='mmStartTender']//a[@id='mmStartTender-itemEl']


            //Waiting till the 'Basic Information - Step1' form is loading
            waitForExtAjaxIsLoading (10, "STEP #1 - BASIC INFORMATION");
            //explicitWaitsUntilElementVisible(10, "//input[@id='TimezoneCombo-triggerWrap']", "STEP #1 - BASIC INFORMATION");


        } catch (Exception e1) {
            logger.error ("UNABLE to click on START TENDER item in TENDERS dropdown menu. " + e1.getMessage ( ));
            driver.quit ( );
        }


        try {

            //writingPageElementsIntoTXTfile("STEP1_BASIC_INFORMATION");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step1_BasicInformation_WebElements.txt", "STEP1_BASIC_INFORMATION", "//*[@id='tenderWizardDlg-body']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP1_BASIC_INFORMATION form: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//*[@id='tenderWizardDlg']", "Step1_BasicInformation_Current_FullSize.", "Step1_BasicInformation");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP1_BASIC_INFORMATION form: " + e.getMessage ( ));
            e.printStackTrace ( );
        }

        try {
            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);
        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP1_BASIC_INFORMATION form: " + e.getMessage ( ));
        }


        //long tenderTime = System.nanoTime ( );
        //double tenderCreationTime = TimeUnit.MILLISECONDS.convert ((tenderTime), TimeUnit.NANOSECONDS) / 1000.0;

        //Date date = new Date ( );
        //SimpleDateFormat currentdate = new SimpleDateFormat ( );
        //currentdate.applyPattern ("MMM/dd/yyy HH:mm:ss");
        //String stringDate = currentdate.format (date);
        //Calendar.getInstance(TimeZone.getTimeZone("GMT+3"));

        String stringDate = incrementYYmmDDhhMMssBy1 (0, 0, 0, 0, 0, 0, "MMM/dd/yyy HH:mm:ss");
        //String stringDate =  getCurrentDay ("MMM/dd/yyy HH:mm:ss");


        //Providing value in the "Tender Title" text field
        try {
            driver.findElement (By.xpath ("//input[@id='Title-inputEl']")).clear ( );
            driver.findElement (By.xpath ("//input[@id='Title-inputEl']")).sendKeys ("TEST_AUTOMATION_Tender_Created_on_" + stringDate);
            //System.out.println ("TEST_AUTOMATION_Tender_Created_on_" + stringDate);
        } catch (NoSuchElementException e) {
            logger.error ("No such element - TENDER TITLE field was not found: " + e.getMessage ( ));
        }


        //Providing value in the "Tender Number" text field
        try {
            driver.findElement (By.xpath ("//input[@id='TenderNumber-inputEl']")).clear ( );
            driver.findElement (By.xpath ("//input[@id='TenderNumber-inputEl']")).sendKeys ("TAT-546984");

        } catch (NoSuchElementException e) {
            logger.error ("No such element - TENDER NUMBER field was not found: " + e.getMessage ( ));
        }


        //Providing value in the "Internal Memo" text field
        try {
            driver.findElement (By.xpath ("//input[@id='InternalTitle-inputEl']")).clear ( );
            driver.findElement (By.xpath ("//input[@id='InternalTitle-inputEl']")).sendKeys ("Test Automation");
        } catch (NoSuchElementException e) {
            logger.error ("No such element - INTERNAL MEMO field was not found: " + e.getMessage ( ));
        }


        //Providing value in the "Tender Description" text field
        try {
            // Clear text in the "Tender Description" text field
            js.executeScript ("$(\"#Description-iframeEl\").attr(\"src\", \"\")", driver.findElement (By.xpath ("//*[@id='Description-wrapEl']")));

            //Type text in the "Tender Description" text field
            js.executeScript ("$(\"#Description-iframeEl\").contents().find(\"body\").append('<p>' + '<b>' + 'Tendering of long-term contracts covering transport and logistics with direct access to the complete Cargoclix network.' + '</b>' + '</p>'  + '<p>' + 'The purpose of a tender in the area of Cargoclix Tender is to locate the optimal logistics service provider for the long-term execution of corresponding contracts covering transport and logistics. Logistics service providers are invited to the tendering process by any appropriate contractors which are of interest to them.'  + '</p>' + '<p>' + 'As a contractor you will obtain via a tender in the area Cargoclix Tender access to the complete Cargoclix network with several thousand associated service providers in the areas of transport and logistics. You will thus find the ideal partners for your contract. All appropriate service providers based on their service profile will be invited to the tendering of your contract. You will have at all times an overview of the contact data and company profiles of the logistics service providers participating in the tendering of the contract. As an alternative you can also execute closed tenders or exclude certain companies from your tenders by means of a blacklist. You are not required to accept the lowest bid, but you can award the job to the best logistics service provider in terms of price and performance.' +'</p>');", driver.findElement (By.xpath ("//*[@id='Description-wrapEl']")));
        } catch (NoSuchElementException e) {
            logger.error ("No such element - TENDER DESCRIPTION text area was not found: " + e.getMessage ( ));
        }


        //Providing value in the "Start of tendering" calendar depending on the time format in the time picker

        Actions action = new Actions (driver);

        //Read default time from the "Start of Tendering" time picker
        WebElement element2 = driver.findElement (By.xpath ("//*[@id='StartTenderTime-inputEl']"));
        String timeX = element2.getAttribute ("value");


        //Check if the time format AM/PM
        if (timeX.substring (timeX.length ( ) - 2).equals ("am") || timeX.substring (timeX.length ( ) - 2).equals ("pm")) {

            logger.info ("12 HOURS AM/PM TIME FORMAT DETECTED in the time picker: " + timeX);

            //Simply set the "Start of Tendering" date to yesterday
            try {
                driver.findElement (By.xpath ("//input[@id='StartTenderDate-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='StartTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, -1, 0, 0, 0, "MM/dd/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - START OF TENDERING date picker was not found: " + e.getMessage ( ));
            }

            //Providing current date in the "End of tendering" calendar
            try {
                driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 0, 0, 0, 0, "MM/dd/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - END OF TENDERING date picker was not found: " + e.getMessage ( ));
            }


            //Provide "Start of collaboration" date which is 7 days later from now
            try {
                driver.findElement (By.xpath ("//input[@id='StartCollaboration-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='StartCollaboration-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 7, 0, 0, 0, "MM/dd/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - START OF COLLABORATION date picker was not found: " + e.getMessage ( ));
            }


            //Provide "End of collaboration" date which is 37 days later from now
            try {
                driver.findElement (By.xpath ("//input[@id='FinishCollaboration-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='FinishCollaboration-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 27, 0, 0, 0, "MM/dd/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - END OF COLLABORATION date picker was not found: " + e.getMessage ( ));
            }

            //Retrieve, process and insert correct time into the END OF TENDERING time picker

            //Get current time in AM/PM format to string
            //String timeCurrentTime = incrementYYmmDDhhMMssBy1 (0, 0, 0, 0, 0, 0, "HH:mm a");
            //System.out.println ("Current time: " + timeCurrentTime);

            //Read current time from the shipper's dashboard and convert it to AM/PM format
            String timeCurrentTime = getCurrentTimeFromShippersDashboardAndConvert24Hto12H ( );

            //Initialize empty string variable
            String time1HourFromCurrentTime = "";


            //Getting to string the minutes of the current time
            String timeCurrentMM = timeCurrentTime.substring (timeCurrentTime.length ( ) - 5, timeCurrentTime.length ( ) - 3);
            System.out.println ("Current minutes: " + timeCurrentMM);
            Integer timeCurrentMM1 = Integer.valueOf (timeCurrentMM);


            //Round current minutes to an integer in 30 increments (half an hour)
            //Integer roundedCurrentMM = ((Integer.valueOf (timeCurrentMM) + 29) / 30) * 30;


            //After 45 minutes
            if (timeCurrentMM1 >= 45) {

                //Set time to be 30 + XX minutes from now if less than 15 minutes remaining till the next hour
                time1HourFromCurrentTime = incrementYYmmDDhhMMssBy2 (0, 0, 0, 0, (30 + (60 - timeCurrentMM1)), 0, "hh:mm a");
                System.out.println ("Time set to be 30 + XX minutes from now as less than 15 minutes remaining till the next hour: " + time1HourFromCurrentTime);

                //Rounding minutes to 30 or 00
                time1HourFromCurrentTime = roundingMinutes (time1HourFromCurrentTime);

                //Change "End of Tendering" date to the next day on the border of two days
                if (timeCurrentTime.substring (timeCurrentTime.length ( ) - 2).equals ("PM")) {

                    if (timeCurrentTime.substring (timeCurrentTime.length ( ) - timeCurrentTime.length ( ), timeCurrentTime.length ( ) - (timeCurrentTime.length ( ) - 2)).equals ("11")) {

                        //Providing current date in the "End of tendering" calendar
                        try {
                            driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).clear ( );
                            driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 1, 0, 0, 0, "MM/dd/yyyy"));
                        } catch (NoSuchElementException e) {
                            logger.error ("No such element - END OF TENDERING date picker was not found: " + e.getMessage ( ));
                        }

                    }
                }

            }

            //Set time to be at HH:30 if less than 15 minutes have passed after new hour
            if (timeCurrentMM1 <= 15) {

                time1HourFromCurrentTime = incrementYYmmDDhhMMssBy2 (0, 0, 0, 0, (30 - timeCurrentMM1), 0, "hh:mm a");
                System.out.println ("Time set to be at HH:30 as less than 15 minutes have passed after new hour: " + time1HourFromCurrentTime);

                //Rounding minutes to 30 or 00
                time1HourFromCurrentTime = roundingMinutes (time1HourFromCurrentTime);

            }

            //Set time to the next hour if more than 15 minutes have already passed after the new hour but still more than 15 minutes remaining till the next hour
            if (timeCurrentMM1 > 15 && timeCurrentMM1 < 44) {

                time1HourFromCurrentTime = incrementYYmmDDhhMMssBy2 (0, 0, 0, 0, (60 - timeCurrentMM1), 0, "hh:mm a");
                System.out.println ("Time set to the next hour as more than 15 minutes have already passed after the new hour but still more than 15 minutes remaining till the next hour: " + time1HourFromCurrentTime);

                //Rounding minutes to 30 or 00
                time1HourFromCurrentTime = roundingMinutes (time1HourFromCurrentTime);

                //Change "End of Tendering" date to the next day on the border of two days
                if (timeCurrentTime.substring (timeCurrentTime.length ( ) - 2).equals ("PM")) {

                    if (timeCurrentTime.substring (timeCurrentTime.length ( ) - timeCurrentTime.length ( ), timeCurrentTime.length ( ) - (timeCurrentTime.length ( ) - 2)).equals ("11")) {

                        //Providing current date in the "End of tendering" calendar
                        try {
                            driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).clear ( );
                            driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 1, 0, 0, 0, "MM/dd/yyyy"));
                        } catch (NoSuchElementException e) {
                            logger.error ("No such element - END OF TENDERING date picker was not found: " + e.getMessage ( ));
                        }

                    }
                }

            }

            //Replacing MM in current time with minutes rounded to half an hour i.e. 30 or 00
            //String finalEndOfTenderingTime = time1HourFromCurrentTime.replace (timeCurrentMM, formatter.format (roundedCurrentMM));
            //System.out.println ("Time with rounded minutes: " + finalEndOfTenderingTime);


            //Change the time's AM/PM letters to lower case as they appear to be in the system
            String finalEndOfTenderingTimeLowerCase = time1HourFromCurrentTime.toLowerCase ( );
            System.out.println ("END OF TENDERING time set to lower case: " + finalEndOfTenderingTimeLowerCase);

            // System.out.println ("finalEndOfTenderingTimeLowerCase.substring (0,1): " + finalEndOfTenderingTimeLowerCase.substring (0, 1));


            if (finalEndOfTenderingTimeLowerCase.substring (0, 1).equals ("0")) {

                finalEndOfTenderingTimeLowerCase = finalEndOfTenderingTimeLowerCase.substring (1);
                System.out.println ("Trimmed finalEndOfTenderingTimeLowerCase: " + finalEndOfTenderingTimeLowerCase);

            }


            //Click on the time picker field for "End of Tendering" date
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='EndTenderTime-inputEl']")));

            Thread.sleep (1000);


            //Find the required time from the time picker dropdown and select it (for "End of Tendering" date)
            try {
                List<WebElement> available_times = driver.findElements (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::*"));
                //Select dropdown = new Select(driver.findElement(By.xpath ("//*[@class='x-boundlist-list-ct']")));
                // List<WebElement> available_times = dropdown.getOptions();
                // String timeX = available_times.get (0).getText ();


                for (WebElement item : available_times) {

                    if (item.getText ( ).equals (time1HourFromCurrentTime) || item.getText ( ).equals (finalEndOfTenderingTimeLowerCase)) {

                        //Select and click on calculated "End of Tendering" time
                        //action.moveToElement (item).click ( ).build ( ).perform ( );
                        //OR  //item.click ();
                        //OR  //item.sendKeys(Keys.RETURN);
                        //OR  //driver.findElement(By.linkText(finalEndOfTenderingTimeLowerCase)).sendKeys(Keys.ENTER);
                        //OR //driver.findElement (By.xpath ("//*[@name='EndTenderTime']")).sendKeys (finalEndOfTenderingTimeLowerCase);

                        WebElement el = driver.findElement (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + item.getText ( ) + "']"));
                        // el.click();
                        //action.moveToElement (el).click ( ).perform ( );
                        js.executeScript ("arguments[0].click();", el);
                        //driver.findElement(By.linkText(finalEndOfTenderingTimeLowerCase)).sendKeys(Keys.ENTER);

                        Thread.sleep (1000);

                        break;
                        //return;
                    }
                }
            } catch (Exception e) {
                logger.error ("Unable to select time for END OF TENDERING time picker: " + e.getMessage ( ));
            }


        } else { //if current time is not AM/PM format

            logger.info ("24 HOURS TIME FORMAT DETECTED in the time picker: " + timeX);

            //Simply set the "Start of Tendering" date to yesterday
            try {
                driver.findElement (By.xpath ("//input[@id='StartTenderDate-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='StartTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, -1, 0, 0, 0, "dd/MM/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - START OF TENDERING date picker was not found: " + e.getMessage ( ));
            }

            //Providing current date in the "End of tendering" calendar
            try {
                driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 0, 0, 0, 0, "dd/MM/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - END OF TENDERING date picker was not found: " + e.getMessage ( ));
            }


            //Provide "Start of collaboration" date which is 7 days later from now
            try {
                driver.findElement (By.xpath ("//input[@id='StartCollaboration-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='StartCollaboration-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 7, 0, 0, 0, "dd/MM/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - START OF COLLABORATION date picker was not found: " + e.getMessage ( ));
            }


            //Provide "End of collaboration" date which is 37 days later from now
            try {
                driver.findElement (By.xpath ("//input[@id='FinishCollaboration-inputEl']")).clear ( );
                driver.findElement (By.xpath ("//input[@id='FinishCollaboration-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 27, 0, 0, 0, "dd/MM/yyyy"));
            } catch (NoSuchElementException e) {
                logger.error ("No such element - END OF COLLABORATION date picker was not found: " + e.getMessage ( ));
            }

            //Retrieve, process and insert correct time into the END OF TENDERING time picker


            //Get current time in 24H format to string
            String timeCurrentTime = incrementYYmmDDhhMMssBy3 (0, 0, 0, 0, 0, 0, "H:mm");
            System.out.println ("Current time: " + timeCurrentTime);

            //Initialize empty string variable
            String time1HourFromCurrentTime = "";


            //Getting to string the minutes of the current time
            String timeCurrentMM = timeCurrentTime.substring (timeCurrentTime.length ( ) - 5, timeCurrentTime.length ( ) - 3);
            System.out.println ("Current minutes: " + timeCurrentMM);
            Integer timeCurrentMM1 = Integer.valueOf (timeCurrentMM);


            //Round current minutes to an integer in 30 increments (half an hour)
            //Integer roundedCurrentMM = ((Integer.valueOf (timeCurrentMM) + 29) / 30) * 30;


            //After 45 minutes
            if (timeCurrentMM1 >= 45) {

                //Set time to be 30 + XX minutes from now if less than 15 minutes remaining till the next hour
                time1HourFromCurrentTime = incrementYYmmDDhhMMssBy3 (0, 0, 0, 0, (30 + (60 - timeCurrentMM1)), 0, "H:mm");
                System.out.println ("Time set to be 30 + XX minutes from now as less than 15 minutes remaining till the next hour: " + time1HourFromCurrentTime);

                //Rounding minutes to 30 or 00
                time1HourFromCurrentTime = roundingMinutes (time1HourFromCurrentTime);

                //Change "End of Tendering" date to the next day on the border of two days
                if (Integer.valueOf (timeCurrentTime.substring (timeCurrentTime.length ( ) - 5, timeCurrentTime.length ( ) - 3)) == 23) {

                    //Providing current date in the "End of tendering" calendar
                    try {
                        driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).clear ( );
                        driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 1, 0, 0, 0, "MM/dd/yyyy"));
                    } catch (NoSuchElementException e) {
                        logger.error ("No such element - END OF TENDERING date picker was not found: " + e.getMessage ( ));
                    }

                }
            }

            //Set time to be at HH:30 if less than 15 minutes have passed after new hour
            if (timeCurrentMM1 <= 15) {

                time1HourFromCurrentTime = incrementYYmmDDhhMMssBy3 (0, 0, 0, 0, -timeCurrentMM1 + 30, 0, "H:mm");
                System.out.println ("Time set to be at HH:30 as less than 15 minutes have passed after new hour: " + time1HourFromCurrentTime);

                //Rounding minutes to 30 or 00
                time1HourFromCurrentTime = roundingMinutes (time1HourFromCurrentTime);
            }

            //Set time to the next hour if more than 15 minutes have already passed after the new hour but still more than 15 minutes remaining till the next hour
            if (timeCurrentMM1 > 15 && timeCurrentMM1 < 44) {

                time1HourFromCurrentTime = incrementYYmmDDhhMMssBy3 (0, 0, 0, 0, (60 - timeCurrentMM1), 0, "H:mm");
                System.out.println ("Time set to the next hour as more than 15 minutes have already passed after the new hour but still more than 15 minutes remaining till the next hour: " + time1HourFromCurrentTime);

                //Rounding minutes to 30 or 00
                time1HourFromCurrentTime = roundingMinutes (time1HourFromCurrentTime);

                //Change "End of Tendering" date to the next day on the border of two days
                if (timeCurrentTime.substring (timeCurrentTime.length ( ) - timeCurrentTime.length ( ), timeCurrentTime.length ( ) - (timeCurrentTime.length ( ) - 2)).equals ("23")) {

                    //Providing current date in the "End of tendering" calendar
                    try {
                        driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).clear ( );
                        driver.findElement (By.xpath ("//input[@id='EndTenderDate-inputEl']")).sendKeys (incrementYYmmDDhhMMssBy1 (0, 0, 1, 0, 0, 0, "MM/dd/yyyy"));
                    } catch (NoSuchElementException e) {
                        logger.error ("No such element - END OF TENDERING date picker was not found: " + e.getMessage ( ));
                    }
                }

            }

            //Replacing MM in current time with minutes rounded to half an hour i.e. 30 or 00
            //String finalEndOfTenderingTime = time1HourFromCurrentTime.replace (timeCurrentMM, formatter.format (roundedCurrentMM));
            //System.out.println ("Time with rounded minutes: " + finalEndOfTenderingTime);


            //Change the time's AM/PM letters to lower case as they appear to be in the system
           // String finalEndOfTenderingTimeLowerCase = time1HourFromCurrentTime.toLowerCase ( );
           // System.out.println ("END OF TENDERING time set to lower case: " + finalEndOfTenderingTimeLowerCase);

            //System.out.println ("finalEndOfTenderingTimeLowerCase.substring (0,1): " + finalEndOfTenderingTimeLowerCase.substring (0, 1));


            if (time1HourFromCurrentTime.substring (0, 1).equals ("0")) {

                time1HourFromCurrentTime = time1HourFromCurrentTime.substring (1);
                System.out.println ("Trimmed finalEndOfTenderingTimeLowerCase: " + time1HourFromCurrentTime);

            }


            //Click on the time picker field for "End of Tendering" date
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='EndTenderTime-inputEl']")));

            Thread.sleep (1000);

            //Find the required time from the time picker dropdown and select it (for "End of Tendering" date)
            try {
                List<WebElement> available_times = driver.findElements (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::*"));
                //Select dropdown = new Select(driver.findElement(By.xpath ("//*[@id='timepicker-1073-listEl']")));
                // List<WebElement> available_times = dropdown.getOptions();
                // String timeX = available_times.get (0).getText ();

                for (WebElement item : available_times) {

                    if (item.getText ( ).equals (time1HourFromCurrentTime) || item.getText ( ).equals (time1HourFromCurrentTime)) {

                        //Select and click on calculated "End of Tendering" time
                        //action.moveToElement (item).click ( ).build ( ).perform ( );
                        //OR  //item.click ();
                        //OR  //item.sendKeys(Keys.RETURN);
                        //OR  //driver.findElement(By.linkText(finalEndOfTenderingTimeLowerCase)).sendKeys(Keys.ENTER);
                        //OR //driver.findElement (By.xpath ("//*[@name='EndTenderTime']")).sendKeys (finalEndOfTenderingTimeLowerCase);

                        WebElement el = driver.findElement (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + item.getText ( ) + "']"));
                        // el.click();
                        //action.moveToElement (el).click ( ).perform ( );
                        js.executeScript ("arguments[0].click();", el);
                        //driver.findElement(By.linkText(finalEndOfTenderingTimeLowerCase)).sendKeys(Keys.ENTER);

                        Thread.sleep (1000);

                        break;
                        //return;
                    }
                }

            } catch (Exception e) {
                logger.error ("Unable to select time for END OF TENDERING time picker: " + e.getMessage ( ));
            }

        }


        //Click on "Target of Tendering" dropdown and select a value
        try {

            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='ReasonCombo-inputEl']")));

            Thread.sleep (1000);

            WebElement el = driver.findElement (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + "Calculations for new business" + "']"));
            // el.click();
            //action.moveToElement (el).click ( ).perform ( );
            js.executeScript ("arguments[0].click();", el);

            Thread.sleep (1000);

        } catch (Exception e) {
            logger.error ("Unable to click on the TARGET OF TENDERING dropdown on STEP# 1: " + e.getMessage ( ));
        }


        //Click on "Awarding" dropdown and select a value
        try {

            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='AwardCombo-inputEl']")));

            Thread.sleep (1000);

            WebElement el = driver.findElement (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + "Allocation to multiple service providers" + "']"));
            // el.click();
            //action.moveToElement (el).click ( ).perform ( );
            js.executeScript ("arguments[0].click();", el);

            Thread.sleep (1000);

        } catch (Exception e) {
            logger.error ("Unable to click on the AWARDING droplist on STEP# 1: " + e.getMessage ( ));
        }


    }


    //FILLING OUT STEP2 - "TENDER SETTINGS"
    @Test(priority = 3)
    public void openCreateTenderWizardStep2_TenderSettings() throws Exception {

        String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step2_TenderSettings_Baseline_FullSize.png";
        String CurrentImg = ".\\src\\main\\resources\\current_images\\Step2_TenderSettings_Current_FullSize.png";
        String DiffImg = ".\\src\\main\\resources\\difference_images\\Step2_TenderSettings_Difference_FullSize.png";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions (driver);


        try {

            //Click "NEXT" button to go from step#1 to step#2
            //js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='button-1028-btnEl']")));
            //action.moveToElement (driver.findElement (By.xpath ("//*[@id='button-1028-btnEl']"))).click ( ).build ( ).perform ( );
            action.moveToElement (driver.findElement (By.xpath ("//div[@class='x-btn x-box-item x-toolbar-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button[@class='x-btn-center']/span[contains(text(), 'Next')]"))).click ( ).build ( ).perform ( );


            //Waiting till the 'Tender Settings - Step2' form is loading
            //waitForExtAjaxIsLoading (10, "STEP #2 - TENDER SETTINGS");
            explicitWaitsUntilElementVisible (10, "//input[@id='ShowCompanyName-inputEl']", "STEP #2 - TENDER SETTINGS");


        } catch (Exception e) {
            logger.error ("Unable to click on the NEXT button on STEP# 1 - BASIC INFORMATION: " + e.getMessage ( ));
            driver.quit ( );
        }


        try {

            // writingPageElementsIntoTXTfile("STEP2_TENDER_SETTINGS");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS", "//*[@id='tenderWizardDlg-body']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP2_TENDER_SETTINGS form: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//*[@id='tenderWizardDlg']", "Step2_TenderSettings_Current_FullSize.", "Step2_TenderSettings");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP2_TENDER_SETTINGS form: " + e.getMessage ( ));
            e.printStackTrace ( );
        }

        try {
            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);
        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP2_TENDER_SETTINGS form: " + e.getMessage ( ));
        }


        //Select all possible options in the "Contact Details" for the tender owner
        try {

            //Double-click on the "Contact Details" column

            WebElement baseTable = driver.findElement (By.xpath ("//div[@id='TenderAccessGridPanel-body']/div/table/tbody"));
            WebElement tableRow = baseTable.findElement (By.xpath ("//tr[1]"));
            WebElement cellIneed = tableRow.findElement (By.xpath ("//td[1]"));

            action.doubleClick (cellIneed).perform ( );

            Thread.sleep (500);

        } catch (NoSuchElementException e) {
            logger.error ("Unable to open 'CONTACT DETAILS' dropdown on STEP2_TENDER_SETTINGS form for the TENDER OWNER: " + e.getMessage ( ));
        }


        //Set all possible values in the "Contact Details" for the tender owner
        try {

            //Click to open the "Contact Details" dropdown
            WebElement contactDetails = driver.findElement (By.xpath ("//*[@id='ContactDetailsCombo-inputEl']"));

            js.executeScript ("arguments[0].click();", contactDetails);

            Thread.sleep (500);

            //Check if the "Contact Details" dropdown is enabled
            if (driver.findElement (By.xpath ("//*[@id='ContactDetailsCombo-inputEl']")).isEnabled ( )) {

                //Get the options of the "Contact Details"  dropdown
                List<WebElement> available_ContactDetails = driver.findElements (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li/div"));

                //If at least 1 option is selected - click on the clean button to remove the ticks from all contact details
                for (WebElement item : available_ContactDetails) {

                    if (item.getAttribute ("class").equals ("x-combo-list-item x-mcombo-item-checked")) {

                        //Select and click on calculated "End of Tendering" time
                        //action.moveToElement (item).click ( ).build ( ).perform ( );
                        //OR  //item.click ();
                        //OR  //item.sendKeys(Keys.RETURN);
                        //OR  //driver.findElement(By.linkText(finalEndOfTenderingTimeLowerCase)).sendKeys(Keys.ENTER);
                        //OR //driver.findElement (By.xpath ("//*[@name='EndTenderTime']")).sendKeys (finalEndOfTenderingTimeLowerCase);

                        //WebElement el = driver.findElement (By.xpath ("//*[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + item.getText ( ) + "']"));
                        // el.click();
                        //action.moveToElement (item).click ( ).perform ( );
                        //driver.findElement(By.linkText(finalEndOfTenderingTimeLowerCase)).sendKeys(Keys.ENTER);
                        js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='ContactDetailsCombo-triggerWrap']/tbody/tr/td[2]/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));

                        Thread.sleep (500);

                        break;
                        //return;
                    }
                }

                Thread.sleep (500);

                //Get the options of the "Contact Details"  dropdown again
                List<WebElement> available_ContactDetails2 = driver.findElements (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li/div"));

                for (WebElement item2 : available_ContactDetails2) {

                    String optionText = item2.getText ( );
                    //System.out.println ("ITEM_TEXT: " + optionText);

                    switch (optionText) {
                        case "Phone":
                            //Select "Phone" option from the "Contact Details" dropdown
                            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/li/div/*[text()= 'Phone']")));
                            break;
                        case "Email":
                            //Select "Email" option from the "Contact Details" dropdown
                            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/li/div/*[text()= 'Email']")));
                            break;
                        case "Mobile Phone":
                            //Select "Phone" option from the "Contact Details" dropdown
                            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/li/div/*[text()= 'Mobile Phone']")));
                            break;
                        case "Fax":
                            //Select "Phone" option from the "Contact Details" dropdown
                            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/li/div/*[text()= 'Fax']")));
                            break;

                        default:
                            break;
                    }

                    Thread.sleep (500);

                }

                //Close the "Contact Details" dropdown
                js.executeScript ("arguments[0].click();", contactDetails);

                Thread.sleep (500);

            }

            //Click on "Update" button
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-btn x-box-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button/span[contains(text(), 'Update')]")));

            Thread.sleep (500);

        } catch (NoSuchElementException e) {
            logger.error ("Unable to open and select 'EMAIL' and 'PHONE' in the CONTACT DETAILS dropdown on STEP2_TENDER_SETTINGS form: " + e.getMessage ( ));
        }


        //Check if the "Add User" button is enabled and add 2 more users
        if (driver.findElement (By.xpath ("//*[@id='btnAddUser-btnEl']")).isEnabled ( )) {
            Integer i = 0;
            try {
                while (i < 2) {
                    action.moveToElement (driver.findElement (By.xpath ("//*[@id='btnAddUser-btnEl']"))).click ( ).build ( ).perform ( );
                    Thread.sleep (500);

                    //Click on "Update" button
                    js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-btn x-box-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button/span[contains(text(), 'Update')]")));
                    i++;
                }
            } catch (NoSuchElementException e) {
                logger.error ("No such element - ADD USER button was not found: " + e.getMessage ( ));
            }
        } else {

            logger.error ("ADD USER button is DISABLED on STEP2_TENDER_SETTINGS form.");

        }

        Thread.sleep (500);


        //Set "View only" access for the first colleague-shipper
        try {

            //Double-click on the "Access Right" column

            WebElement baseTable = driver.findElement (By.xpath ("//div[@id='TenderAccessGridPanel-body']/div/table/tbody"));
            WebElement tableRow = baseTable.findElement (By.xpath ("//tr[1]"));
            WebElement cellIneed = tableRow.findElement (By.xpath ("//td[1]"));

            action.doubleClick (cellIneed).perform ( );

            Thread.sleep (500);


            if (driver.findElement (By.xpath ("//*[@id='AccessCombo-inputEl']")).isEnabled ( )) {

                //Click on the button to open "Access Right" dropdown
                WebElement accessRightsDropdown = driver.findElement (By.xpath ("//*[@id='AccessCombo-triggerWrap']/tbody/tr/descendant::*[@class='x-trigger-index-0 x-form-trigger x-form-arrow-trigger x-form-trigger-last x-unselectable']"));

                action.moveToElement (accessRightsDropdown).click ( ).build ( ).perform ( );

                Thread.sleep (500);

                //Get the options of the "Access Right" dropdown
                List<WebElement> available_accessRights = driver.findElements (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/*[@class='x-boundlist-list-ct']/ul/descendant::*"));

                for (WebElement item : available_accessRights) {

                    if (item.getText ( ).equals ("View only")) {

                        js.executeScript ("arguments[0].click();", item);

                        Thread.sleep (500);

                        break;
                        //return;
                    }
                }
            }

            Thread.sleep (500);

        } catch (NoSuchElementException e) {
            logger.error ("Unable to open and select 'VIEW ONLY' access rights into the ACCESS RIGHTS dropdown on STEP2_TENDER_SETTINGS form for the COLLEAGUE-SHIPPER: " + e.getMessage ( ));
        }


        //Set "Email" and "Phone" into the "Contact Details" for the first colleague-shipper
        try {

            //Click to open the "Contact Details" dropdown
            WebElement contactDetails = driver.findElement (By.xpath ("//*[@id='ContactDetailsCombo-inputEl']"));

            js.executeScript ("arguments[0].click();", contactDetails);

            Thread.sleep (500);

            //Check if the "Contact Details" dropdown is enabled
            if (driver.findElement (By.xpath ("//*[@id='ContactDetailsCombo-inputEl']")).isEnabled ( )) {

                //Get the options of the "Contact Details"  dropdown
                List<WebElement> available_ContactDetails = driver.findElements (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li/div"));

                //If at least 1 option is selected - click on the clean button to remove the ticks from all contact details
                for (WebElement item : available_ContactDetails) {

                    if (item.getAttribute ("class").equals ("x-combo-list-item x-mcombo-item-checked")) {

                        js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='ContactDetailsCombo-triggerWrap']/tbody/tr/td[2]/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));

                        Thread.sleep (500);

                        break;
                        //return;
                    }
                }

                Thread.sleep (500);

                //Get the options of the "Contact Details"  dropdown again
                List<WebElement> available_ContactDetails2 = driver.findElements (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li/div"));

                for (WebElement item2 : available_ContactDetails2) {

                    String optionText = item2.getText ( );
                    //System.out.println ("ITEM_TEXT: " + optionText);

                    switch (optionText) {
                        case "Phone":
                            //Select "Phone" option from the "Contact Details" dropdown
                            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/li/div/*[text()= 'Phone']")));
                            break;
                        case "Email":
                            //Select "Email" option from the "Contact Details" dropdown
                            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/li/div/*[text()= 'Email']")));
                            break;
                        case "Mobile Phone":
                            break;
                        case "Fax":
                            break;

                        default:
                            break;
                    }

                    Thread.sleep (500);

                }

                //Close the "Contact Details" dropdown
                js.executeScript ("arguments[0].click();", contactDetails);

                Thread.sleep (500);

            }

            //Click on "Update" button
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-btn x-box-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button/span[contains(text(), 'Update')]")));

            Thread.sleep (500);

        } catch (NoSuchElementException e) {
            logger.error ("Unable to open and select 'EMAIL' and 'PHONE' in the CONTACT DETAILS dropdown on STEP2_TENDER_SETTINGS form: " + e.getMessage ( ));
        }


        //Check if the "Add Currency" button is enabled and add 6 more currencies (i.e USD, GBP, SEK, CHF, NOK, RUB)
        if (driver.findElement (By.xpath ("//*[@id='btnAddCurrency-btnEl']")).isEnabled ( )) {
            Integer i = 0;
            try {
                while (i < 6) {

                    //Click on the "Add Currency" button
                    js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='btnAddCurrency-btnEl']")));
                    Thread.sleep (500);

                    //Click on "Update" button
                    driver.findElement (By.xpath ("//*[@id='CurrenciesCombo-inputEl']")).sendKeys (Keys.ENTER);

                    //Next currency
                    i++;
                }
            } catch (NoSuchElementException e) {
                logger.error ("No such element - ADD CURRENCY button was not found: " + e.getMessage ( ));
            }
        } else {

            logger.error ("ADD CURRENCY button is DISABLED on STEP2_TENDER_SETTINGS form.");

        }


        //Set currency symbols
        try {

            List<WebElement> available_CurrencyRows = driver.findElements (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/table[@class='x-grid-table x-grid-table-resizer']/tbody/descendant::tr"));

            int RowIndex = 1;

            //Loop the table's rows
            for (WebElement item : available_CurrencyRows) {

                //Retrieve all columns from the row
                List<WebElement> TotalColumnCount = item.findElements (By.xpath ("td"));

                int ColumnIndex = 1;

                //Loop the columns of a row
                for (WebElement colElement : TotalColumnCount) {

                    //Read each cell's text
                    String optionText = colElement.getText ( );
                    //System.out.println ("TR_TEXT: " + optionText);

                    switch (optionText) {
                        case "NOK":

                            //Double-click on a row to open it for editing
                            action.doubleClick (driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/table/tbody/tr/td/div[contains(text(), 'NOK')]"))).perform ( );

                            //Insert "&" symbol for NOK currency
                            driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/div/div/div/div/table[@id='TextField2']/tbody/tr/td[@id='TextField2-bodyEl']/input[@id='TextField2-inputEl']")).sendKeys ("&");

                            //Click on "Update" button
                            driver.findElement (By.xpath ("//*[@id='CurrenciesCombo-inputEl']")).sendKeys (Keys.ENTER);

                            break;
                        case "SEK":

                            //Double-click on a row to open it for editing
                            action.doubleClick (driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/table/tbody/tr/td/div[contains(text(), 'SEK')]"))).perform ( );

                            //Insert "*" symbol for SEK currency
                            driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/div/div/div/div/table[@id='TextField2']/tbody/tr/td[@id='TextField2-bodyEl']/input[@id='TextField2-inputEl']")).sendKeys ("*");

                            //Click on "Update" button
                            driver.findElement (By.xpath ("//*[@id='CurrenciesCombo-inputEl']")).sendKeys (Keys.ENTER);
                            break;
                        case "CHF":
                            //Double-click on a row to open it for editing
                            action.doubleClick (driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/table/tbody/tr/td/div[contains(text(), 'CHF')]"))).perform ( );

                            //Insert "#" symbol for CHF currency
                            driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/div/div/div/div/table[@id='TextField2']/tbody/tr/td[@id='TextField2-bodyEl']/input[@id='TextField2-inputEl']")).sendKeys ("#");

                            //Click on "Update" button
                            driver.findElement (By.xpath ("//*[@id='CurrenciesCombo-inputEl']")).sendKeys (Keys.ENTER);
                            break;
                        case "RUB":
                            //Double-click on a row to open it for editing
                            action.doubleClick (driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/table/tbody/tr/td/div[contains(text(), 'RUB')]"))).perform ( );

                            //Insert "@" symbol for RUB currency
                            driver.findElement (By.xpath ("//div[@id='CurrenciesGridPanel-body']/div/div/div/div/div/table[@id='TextField2']/tbody/tr/td[@id='TextField2-bodyEl']/input[@id='TextField2-inputEl']")).sendKeys ("@");

                            //Click on "Update" button
                            driver.findElement (By.xpath ("//*[@id='CurrenciesCombo-inputEl']")).sendKeys (Keys.ENTER);
                            break;
                        default:
                            break;
                    }


                    //Next column
                    ColumnIndex = ColumnIndex + 1;

                    Thread.sleep (500);

                }

                //Next row
                RowIndex = RowIndex + 1;
            }


            Thread.sleep (500);

        } catch (NoSuchElementException e) {
            logger.error ("No such element - Error while ADDING CURRENCY SYMBOL: " + e.getMessage ( ));
        }


        try {

            //Click to select "Allow carriers to provide additional comments together with bids" check-box
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//input[@id='UseUploadComments-inputEl']")));

            Thread.sleep (500);

        } catch (NoSuchElementException e) {
            logger.error ("No such element - Error while selecting ALLOW CARRIERS TO PROVIDE ADDITIONAL COMMENTS TOGETHER WITH BIDS check-box: " + e.getMessage ( ));
        }

    }


    //FILLING OUT STEP3 - "FREIGHT"
    @Test(priority = 4)
    public void openCreateTenderWizardStep3_Freight() throws Exception {

        String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step3_Freight_Baseline_FullSize.png";
        String CurrentImg = ".\\src\\main\\resources\\current_images\\Step3_Freight_Current_FullSize.png";
        String DiffImg = ".\\src\\main\\resources\\difference_images\\Step3_Freight_Difference_FullSize.png";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions (driver);


        try {

            //Click "NEXT" button to go from step#2 to step#3
            action.moveToElement (driver.findElement (By.xpath ("//div[@class='x-btn x-box-item x-toolbar-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button[@class='x-btn-center']/span[contains(text(), 'Next')]"))).click ( ).build ( ).perform ( );
            //js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='button-1028-btnEl']")));
            //action.moveToElement (driver.findElement (By.xpath ("//*[@id='button-1028-btnEl']"))).click ( ).build ( ).perform ( );


            //Waiting till the 'Freight - Step3' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='GoodsDescription-toolbar']", "STEP #3 - FREIGHT");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");


        } catch (Exception e) {
            logger.error ("Unable to click on the NEXT button on STEP# 2 - TENDER SETTINGS: " + e.getMessage ( ));
            driver.quit ( );
        }


        try {

            //writingPageElementsIntoTXTfile("STEP3_FREIGHT");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step3_Freight_WebElements.txt", "STEP3_FREIGHT", "//*[@id='tenderWizardDlg-body']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP3_FREIGHT form: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//*[@id='tenderWizardDlg']", "Step3_Freight_Current_FullSize.", "Step3_Freight");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP3_FREIGHT form: " + e.getMessage ( ));
            e.printStackTrace ( );
        }

        try {
            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);
        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP2_TENDER_SETTINGS form: " + e.getMessage ( ));
        }


        //Providing value in the "Freight Headline" text field
        try {
            driver.findElement (By.xpath ("//input[@id='Destination-inputEl']")).clear ( );
            driver.findElement (By.xpath ("//input[@id='Destination-inputEl']")).sendKeys ("Delivery of potassium fertilizers and liquid solvents.");
            //System.out.println ("TEST_AUTOMATION_Tender_Created_on_" + stringDate);
        } catch (NoSuchElementException e) {
            logger.error ("No such element - FREIGHT HEADLINE field was not found: " + e.getMessage ( ));
        }


        //Providing value in the "Freight Description" text field
        try {
            // Clear text in the "Tender Description" text field
            js.executeScript ("$(\"#GoodsDescription-iframeEl\").attr(\"src\", \"\")", driver.findElement (By.xpath ("//*[@id='GoodsDescription-wrapEl']")));

            //Type text in the "Tender Description" text field
            js.executeScript ("$(\"#GoodsDescription-iframeEl\").contents().find(\"body\").append('<p>' + '<b>' + 'RAIL TRANSPORT' + '</b>' + '</p>'  + '<p>' + 'Apply the national rail transport regulations noting that these may differ from country to country. The  transport  must  be  carried  out  in  accordance  with  the  RID  regulations  when  transporting  any classified material. Distribute bags evenly in the wagon and in such a way that any movement of the load does not prevent the opening of the doors. Care is needed with bulk loads to ensure the closure is effective to prevent moisture ingress and product leakage.' + '</p>' + '<p>' + '<br>' + '<b>' + 'SEA TRANSPORT' + '</b>' + '</br>' +'</p>' + '<p>' + 'Sea transport deserves a high degree of attention and careful management as this involves relatively large quantities  of  materials, long  travel  distances  and  often  international  shipments.  A  number  of requirements have been specified for the safe transportation for classified materials in regulations, e.g.' + '<ul>' + '<li>' + 'IMDG Code for the transport of packaged goods by sea.'+ '</li>' + '<li>' + 'IMO BC Code for the transport of bulk material by sea.' + '</li>' + '</ul>' + '</p>' + '<p>' + 'These  regulations  specify  certain  safety  related  requirements  for  the  products,  e.g. resistance  to detonation, which must be complied with.' + '</p>' + '<p>' + 'EFMA has published a guidance document for the sea transport of AN-based fertilizers (Ref 51) and, therefore, only an overview is given below.' + '</p>');", driver.findElement (By.xpath ("//*[@id='GoodsDescription-wrapEl']")));
        } catch (NoSuchElementException e) {
            logger.error ("No such element - FREIGHT DESCRIPTION text area was not found: " + e.getMessage ( ));
        }

        Thread.sleep (500);
    }


    //FILLING OUT STEP4 - "TRANSPORT"
    @Test(priority = 5)
    public void openCreateTenderWizardStep4_Transport() throws Exception {


        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions (driver);


        try {

            //Click "NEXT" button to go from step#3 to step#4
            action.moveToElement (driver.findElement (By.xpath ("//div[@class='x-btn x-box-item x-toolbar-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button[@class='x-btn-center']/span[contains(text(), 'Next')]"))).click ( ).build ( ).perform ( );
            //js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='button-1028-btnEl']")));
            //action.moveToElement (driver.findElement (By.xpath ("//*[@id='button-1028-btnEl']"))).click ( ).build ( ).perform ( );


            //Waiting till the 'Transport - Step4' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='TruckEquipmentCombo']", "STEP #4 - TRANSPORT");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Unable to click on the NEXT button on STEP# 3 - FREIGHT: " + e.getMessage ( ));
            driver.quit ( );
        }

        //////////////////////////////////////////////////////
        //TRANSPORT - ROAD
        //////////////////////////////////////////////////////

        //Activate "Road" tab by clicking on the "Road transport is requested during this tender" check-box
        try {

            //Check if the "Road transport is requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='RoadPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Road transport is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='RoadPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("ROAD TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - ROAD tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the ROAD TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - ROAD tab: " + e.getMessage ( ));

        }


        try {

            // writingPageElementsIntoTXTfile("STEP4_TRANSPORT - ROAD");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_ROAD_WebElements.txt", "STEP4_TRANSPORT - ROAD", "//div[@id='RoadPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_ROAD tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_ROAD_Current_FullSize.", "Step4_Transport_ROAD");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_ROAD tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_ROAD_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_ROAD_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_ROAD_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_ROAD tab: " + e.getMessage ( ));
        }


        //Selecting all possible options in the "Transport description and specifications" section on the Step4-Transports-Road tab
        try {

            //Check if the "Subcontractors are allowed for this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='RoadPanel-body']/fieldset/div/table/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Subcontractors are allowed for this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='RoadPanel-body']/fieldset/div/table/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);


                //Click on the "Service sector" to select all dropdown items
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//table[@id='ServiceSectorCombo-triggerWrap']/tbody/tr/td[@class='x-trigger-cell']/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));
                Thread.sleep (500);


                //Click on "Average Truck age" dropdown and select a value
                try {

                    js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='TruckAgeCombo-inputEl']")));

                    Thread.sleep (500);

                    WebElement el = driver.findElement (By.xpath ("//div[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + "< 4 years" + "']"));
                    // el.click();
                    //action.moveToElement (el).click ( ).perform ( );
                    js.executeScript ("arguments[0].click();", el);

                    Thread.sleep (500);

                } catch (Exception e) {
                    logger.error ("Unable to click on the AVERAGE TRUCK AGE droplist on STEP# 4 - TRANSPORT - ROAD tab: " + e.getMessage ( ));
                }


                //Click on the "Truck Type" to select all droipdown items
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//table[@id='TruckTypeCombo-triggerWrap']/tbody/tr/td[@class='x-trigger-cell']/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));
                Thread.sleep (500);


                //Click on the "Truck Type Mounting" to select all droipdown items
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//table[@id='TruckSpecificationCombo-triggerWrap']/tbody/tr/td[@class='x-trigger-cell']/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));
                Thread.sleep (500);


                //Click on the "Exhaust Standard" to select all droipdown items
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//table[@id='TruckExhaustStandardCombo-triggerWrap']/tbody/tr/td[@class='x-trigger-cell']/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));
                Thread.sleep (500);


                //Click on "Own Fleet %" dropdown and select a value
                try {

                    js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//input[@name='OwnFleet']")));

                    Thread.sleep (500);

                    WebElement el = driver.findElement (By.xpath ("//div[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + "40%" + "']"));
                    // el.click();
                    //action.moveToElement (el).click ( ).perform ( );
                    js.executeScript ("arguments[0].click();", el);

                    Thread.sleep (500);

                } catch (Exception e) {
                    logger.error ("Unable to click on the OWN FLEET % droplist on STEP# 4 - TRANSPORT - ROAD tab: " + e.getMessage ( ));
                }


                //Click on the "Truck Type Mounting - Additional Information" to select all droipdown items
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//table[@id='TruckAdditionalCombo-triggerWrap']/tbody/tr/td[@class='x-trigger-cell']/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));
                Thread.sleep (500);


                //Click on the "Truck Equipment" to select all droipdown items
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//table[@id='TruckEquipmentCombo-triggerWrap']/tbody/tr/td[@class='x-trigger-cell']/div[@class='x-trigger-index-0 x-form-trigger x-form-simpletick-trigger x-unselectable']")));
                Thread.sleep (500);


            } else {
                logger.error ("SUBCONTRACTORS ARE ALLOWED FOR THIS TENDER check-box on STEP# 4 - TRANSPORT - ROAD tab is DISABLED.");
                driver.quit ( );
            }


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_ROAD tab: " + e.getMessage ( ));
        }

        //////////////////////////////////////////////////////
        //TRANSPORT - RAILROAD
        //////////////////////////////////////////////////////
        try {

            //Click on "Railroad" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Railroad']")));

            //Waiting till the 'Transport - Step4 - Railroad' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='TankCar-inputEl']", "STEP #4 - TRANSPORT - RAILROAD");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_RAILROAD tab: " + e.getMessage ( ));
        }


        //Activate "Railroad" tab by clicking on the "Railway transport is requested during this tender" check-box
        try {

            //Check if the "Railway transport is requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='RailroadPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Railway transport is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='RailroadPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("RAILWAY TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - RAILROAD tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the RAILROAD TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - RAILROAD tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - RAILROAD");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_RAILROAD_WebElements.txt", "STEP4_TRANSPORT - RAILROAD", "//div[@id='RailroadPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_RAILROAD tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_RAILROAD_Current_FullSize.", "Step4_Transport_RAILROAD");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_RAILROAD tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_RAILROAD_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_RAILROAD_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_RAILROAD_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_RAILROAD tab: " + e.getMessage ( ));
        }


        //Select all check-boxes in all sub-section on the Ste4 -Transport - Railroad tab
        try {
            //Retrieve all check-boxes
            List<WebElement> inputs = driver.findElements (By.xpath ("//div[@id='RailroadPanel-body']/div[@class='x-container x-container-default x-column-layout-ct']/descendant::input"));

            //Loop the check-boxe and and click on each one of them
            for (WebElement checkbox : inputs) {

                // el.click();
                //action.moveToElement (el).click ( ).perform ( );
                //Exclude the "Transport Capacity" dropdown from the list of check-boxes
                if (!checkbox.getAttribute ("id").equals ("RailwayTransportCapacity-inputEl")) {
                    js.executeScript ("arguments[0].click();", checkbox);
                }
            }
            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Unable to retrieve all check-boxes on STEP# 4 - TRANSPORT - RAILROAD tab: " + e.getMessage ( ));
        }


        //Click on "Transport Capacity" dropdown and select a value
        try {

            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//input[@id='RailwayTransportCapacity-inputEl']")));

            Thread.sleep (500);

            WebElement el = driver.findElement (By.xpath ("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + "< 2500" + "']"));
            // el.click();
            //action.moveToElement (el).click ( ).perform ( );
            js.executeScript ("arguments[0].click();", el);

            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Unable to click on the TRANSPORT CAPACITY droplist on STEP# 4 - TRANSPORT - RAILROAD tab: " + e.getMessage ( ));
        }


        //////////////////////////////////////////////////////
        //TRANSPORT - INLAND WATER
        //////////////////////////////////////////////////////

        try {

            //Click on "Inland Water" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Inland water']")));

            //Waiting till the 'Transport - Step4 - Inland Water' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='bargeFR40-inputEl']", "STEP #4 - TRANSPORT - INLAND WATER");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_INLAND WATER tab: " + e.getMessage ( ));
        }


        //Activate "Inland Water" tab by clicking on the "Inland waterways are requested during this tender" check-box
        try {

            //Check if the "Inland waterways are requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='InlandWaterPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Railway transport is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='InlandWaterPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("RAILWAY TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - RAILROAD tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the INLAND WATERWAYS ARE REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - INLAND WATER tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - INLAND WATER");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_INLAND_WATER_WebElements.txt", "STEP4_TRANSPORT - INLAND WATER", "//div[@id='InlandWaterPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_INLAND_WATER tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_INLAND_WATER_Current_FullSize.", "Step4_Transport_INLAND_WATER");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_INLAND_WATER tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_INLAND_WATER_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_INLAND_WATER_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_INLAND_WATER_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_INLAND_WATER tab: " + e.getMessage ( ));
        }


        //Select all check-boxes in all sub-section on the Ste4 -Transport - Inland Water tab
        try {
            //Retrieve all check-boxes
            List<WebElement> inputs = driver.findElements (By.xpath ("//div[@id='InlandWaterPanel-body']/div[@class='x-container x-container-default x-column-layout-ct']/descendant::input"));

            //Loop the check-boxes and and click on each one of them
            for (WebElement checkbox : inputs) {

                // el.click();
                //action.moveToElement (el).click ( ).perform ( );

                js.executeScript ("arguments[0].click();", checkbox);

            }
            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Unable to retrieve all check-boxes on STEP# 4 - TRANSPORT - INLAND WATER tab: " + e.getMessage ( ));
        }


        //////////////////////////////////////////////////////
        //TRANSPORT - SHORT SEA
        //////////////////////////////////////////////////////

        try {

            //Click on "Short Sea" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Short Sea']")));

            //Waiting till the 'Transport - Step4 - Inland Water' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='shortseaFR40-inputEl']", "STEP #4 - TRANSPORT - SHORT SEA");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_SHORT SEA tab: " + e.getMessage ( ));
        }


        //Activate "Short Sea" tab by clicking on the "Ferry freight is requested during this tender" check-box
        try {

            //Check if the "Ferry freight is requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='ShortSeaPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Ferry freight is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='ShortSeaPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("FERRY FREIGHT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - SHORT SEA tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the FERRY FREIGHT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - SHORT SEA tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - SHORT SEA");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_SHORT_SEA_WebElements.txt", "STEP4_TRANSPORT - SHORT SEA", "//div[@id='ShortSeaPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_SHORT_SEA tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_SHORT_SEA_Current_FullSize.", "Step4_Transport_SHORT_SEA");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_SHORT_SEA tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_SHORT_SEA_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_SHORT_SEA_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_SHORT_SEA_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_SHORT_SEA tab: " + e.getMessage ( ));
        }


        //Select all check-boxes in all sub-section on the Ste4 -Transport - Short Sea tab
        try {
            //Retrieve all check-boxes
            List<WebElement> inputs = driver.findElements (By.xpath ("//div[@id='ShortSeaPanel-body']/div[@class='x-container x-container-default x-column-layout-ct']/descendant::input"));

            //Loop the check-boxes and and click on each one of them
            for (WebElement checkbox : inputs) {

                // el.click();
                //action.moveToElement (el).click ( ).perform ( );

                js.executeScript ("arguments[0].click();", checkbox);

            }
            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Unable to retrieve all check-boxes on STEP# 4 - TRANSPORT - SHORT SEA tab: " + e.getMessage ( ));
        }


        //////////////////////////////////////////////////////
        //TRANSPORT - SEA
        //////////////////////////////////////////////////////

        try {

            //Click on "Sea" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Sea']")));

            //Waiting till the 'Transport - Step4 - Sea' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='seaFR40-inputEl']", "STEP #4 - TRANSPORT - SEA");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_SEA tab: " + e.getMessage ( ));
        }


        //Activate "Sea" tab by clicking on the "Sea freight is requested during this tender" check-box
        try {

            //Check if the "Sea freight is requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='SeaPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Sea freight is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='SeaPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("SEA FREIGHT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - SEA tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the SEA FREIGHT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - SEA tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - SEA");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_SEA_WebElements.txt", "STEP4_TRANSPORT - SEA", "//div[@id='SeaPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_SEA tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_SEA_Current_FullSize.", "Step4_Transport_SEA");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_SEA tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_SEA_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_SEA_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_SEA_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_SEA tab: " + e.getMessage ( ));
        }


        //Select all check-boxes in all sub-section on the Ste4 -Transport - Sea tab
        try {
            //Retrieve all check-boxes
            List<WebElement> inputs = driver.findElements (By.xpath ("//div[@id='SeaPanel-body']/div[@class='x-container x-container-default x-column-layout-ct']/descendant::input"));

            //Loop the check-boxes and and click on each one of them
            for (WebElement checkbox : inputs) {

                // el.click();
                //action.moveToElement (el).click ( ).perform ( );

                js.executeScript ("arguments[0].click();", checkbox);

            }
            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Unable to retrieve all check-boxes on STEP# 4 - TRANSPORT - SEA tab: " + e.getMessage ( ));
        }


        //////////////////////////////////////////////////////
        //TRANSPORT - AIR
        //////////////////////////////////////////////////////

        try {

            //Click on "Air" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Air']")));

            //Waiting till the 'Transport - Step4 - Air' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='airForwardingAgency-inputEl']", "STEP #4 - TRANSPORT - AIR");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_AIR tab: " + e.getMessage ( ));
        }


        //Activate "Air" tab by clicking on the "Air transport is requested during this tender" check-box
        try {

            //Check if the "Air transport is requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='AirPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Air transport is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='AirPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("AIR TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - AIR tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the AIR TRANSPORT IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - AIR tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - AIR");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_AIR_WebElements.txt", "STEP4_TRANSPORT - AIR", "//div[@id='AirPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_AIR tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_AIR_Current_FullSize.", "Step4_Transport_AIR");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_AIR tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_AIR_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_AIR_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_AIR_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_AIR tab: " + e.getMessage ( ));
        }


        //Select all check-boxes in all sub-section on the Ste4 -Transport - Air tab
        try {
            //Retrieve all check-boxes
            List<WebElement> inputs = driver.findElements (By.xpath ("//div[@id='FieldAirCompanyType-body']/descendant::input"));

            //Loop the check-boxes and and click on each one of them
            for (WebElement checkbox : inputs) {

                // el.click();
                //action.moveToElement (el).click ( ).perform ( );

                js.executeScript ("arguments[0].click();", checkbox);

            }
            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Unable to retrieve all check-boxes on STEP# 4 - TRANSPORT - AIR tab: " + e.getMessage ( ));
        }


        //////////////////////////////////////////////////////
        //TRANSPORT - WAREHOUSING
        //////////////////////////////////////////////////////

        try {

            //Click on "Warehousing" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Warehousing']")));

            //Waiting till the 'Transport - Step4 - Warehousing' form is loading
            explicitWaitsUntilElementVisible (10, "//*[@id='TenderWarehouseGrid-body']", "STEP #4 - TRANSPORT - WAREHOUSING");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_WAREHOUSING tab: " + e.getMessage ( ));
        }


        //Activate "Warehousing" tab by clicking on the "Warehousing is requested during this tender" check-box
        try {

            //Check if the "Warehousing is requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='WarehousingPanel-targetEl']/table[@class='x-field x-form-item x-box-item x-field-default x-vbox-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Air transport is requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='WarehousingPanel-targetEl']/table[@class='x-field x-form-item x-box-item x-field-default x-vbox-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("WAREHOUSING IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - AIR tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the WAREHOUSING IS REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - WAREHOUSING tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - WAREHOUSING");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_WAREHOUSING_WebElements.txt", "STEP4_TRANSPORT - WAREHOUSING", "//div[@id='WarehousingPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_AIR tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_WAREHOUSING_Current_FullSize.", "Step4_Transport_WAREHOUSING");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_WAREHOUSING tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_WAREHOUSING_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_WAREHOUSING_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_WAREHOUSING_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_WAREHOUSING tab: " + e.getMessage ( ));
        }

        //Add 2 warehousing records
        Integer i = 0;
        try {

            String index1 = String.valueOf (84102);
            String index2 = String.valueOf (10559);
            String capacity1 = "< 500 m²";
            String capacity2 = "< 250 m²";


            String[][] warehousingInfo1 = {{"Slovakia", index1, "Bratislava", capacity1},
                    {"Germany", index2, "Berlin", capacity2}};

            while (i < 2) {
                //action.moveToElement (driver.findElement (By.xpath ("//*[@id='NodeAddBtnToolbar']/div/div/div/em/button/span[text()='Add']"))).click ( ).build ( ).perform ( );

                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@id='NodeAddBtnToolbar']/div/div/div/em/button/span[text()='Add']")));
                Thread.sleep (500);


                //Enter value into "COUNTRY", "ZIP", "CITY", "WAREHOUSE TYPE" and "CAPACITY TYPE" fields
                try {
                    List<WebElement> inputField = driver.findElements (By.xpath ("//div[@id='TenderWarehouseGrid-body']/descendant::input"));

                    for (WebElement textField : inputField) {

                        String inputName = textField.getAttribute ("name");
                        //System.out.println ("Input Fields: " + inputName);

                        switch (inputName) {

                            case "countryId":
                                //Click on the "Country" dropdown
                                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='TenderWarehouseGrid-body']/div/div/div/div/div/table[@class='x-field x-form-item x-box-item x-field-default x-hbox-form-item x-form-invalid']/tbody/tr/td[@class='x-form-item-body x-form-trigger-wrap-focus']/table/tbody/tr/td[@class='x-trigger-cell']/div")));

                                Thread.sleep (500);

                                //Select value in the "Country" dropdown
                                WebElement el = driver.findElement (By.xpath ("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li[text() ='" + warehousingInfo1[i][0] + "']"));
                                js.executeScript ("arguments[0].click();", el);
                                break;

                            case "zip":
                                //Enter value into "ZIP" field
                                textField.sendKeys (warehousingInfo1[i][1]);
                                break;

                            case "city":
                                //Enter value into "CITY" field
                                textField.sendKeys (warehousingInfo1[i][2]);
                                break;

                            case "typeId":
                                //Click on the "Warehouse type" dropdown
                                js.executeScript ("arguments[0].click();", textField);

                                Thread.sleep (500);

                                //Select value in the "Warehouse type" dropdown
                                List<WebElement> elm = driver.findElements (By.xpath ("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li"));

                                //Loop the check-boxes and click on 3 of them
                                for (WebElement el1 : elm) {
                                    String warehouseType = el1.getText ( );
                                    // System.out.println ("Warehouse type: " + warehouseType);


                                    switch (warehouseType) {

                                        case "Common warehouse":
                                            if (i == 0) {
                                                js.executeScript ("arguments[0].click();", el1);
                                            }
                                            break;
                                        case "Tank farm":
                                            if (i == 0) {
                                                js.executeScript ("arguments[0].click();", el1);
                                            }
                                            break;
                                        case "Roofed storage":
                                            if (i == 0) {
                                                js.executeScript ("arguments[0].click();", el1);
                                            }
                                            break;

                                        case "Cold storage":
                                            if (i == 1) {
                                                js.executeScript ("arguments[0].click();", el1);
                                            }
                                            break;

                                        default:
                                            break;
                                    }
                                }

                                js.executeScript ("arguments[0].click();", textField);
                                Thread.sleep (500);
                                break;

                            case "capacityId":
                                //Click on the "Capacity class" dropdown
                                js.executeScript ("arguments[0].click();", textField);

                                //Select capacity class from "Capacity class" dropdown
                                WebElement el2 = driver.findElement (By.xpath ("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default']/div[@class='x-boundlist-list-ct']/ul/descendant::li[text() = '" + warehousingInfo1[i][3] + "']"));
                                js.executeScript ("arguments[0].click();", el2);
                                break;

                            default:
                                break;
                        }

                    }
                    Thread.sleep (1000);

                } catch (Exception e) {
                    logger.error ("Unable to enter value(s) in one of the fields i.e. COUNTRY, ZIP, CITY, WAREHOUSE TYPE or CAPACITY TYPE on STEP# 4 - TRANSPORT - WAREHOUSING tab: " + e.getMessage ( ));
                }


                //Click on "Update" button
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//*[@class='x-btn x-box-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button/span[contains(text(), 'Update')]")));

                i++;
            }
        } catch (NoSuchElementException e) {
            logger.error ("No such element - ADD button was not found for Warehousing: " + e.getMessage ( ));
        }


        //////////////////////////////////////////////////////
        //TRANSPORT - PARCEL
        //////////////////////////////////////////////////////

        try {

            //Click on "Parcel" tab
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//button[@class='x-tab-center']/span[text()='Parcel']")));

            //Waiting till the 'Transport - Step4 - Parcel' form is loading
            explicitWaitsUntilElementVisible (10, "//div[@id='ParcelPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']", "STEP #4 - TRANSPORT - PARCEL");
            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Error while selecting options on STEP4_TRANSPORT_PARCEL tab: " + e.getMessage ( ));
        }


        //Click on the "Parcel services are requested during this tender" check-box
        try {

            //Check if the "Parcel services are requested during this tender" check-box is enabled
            if (driver.findElement (By.xpath ("//div[@id='ParcelPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")).isEnabled ( )) {

                //Click on the "Parcel services are requested during this tender" check-box
                js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='ParcelPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']")));

                Thread.sleep (500);

            } else {
                logger.error ("PARCEL SERVICES ARE REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - PARCEL tab is DISABLED.");

            }

        } catch (Exception e) {
            logger.error ("Unable to click on the PARCEL SERVICES ARE REQUESTED DURING THIS TENDER check-box on STEP# 4 - TRANSPORT - PARCEL tab: " + e.getMessage ( ));

        }


        try {

            //writingPageElementsIntoTXTfile("STEP4_TRANSPORT - PARCEL");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step4_Transport_PARCEL_WebElements.txt", "STEP4_TRANSPORT - PARCEL", "//div[@id='ParcelPanel']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP4_TRANSPORT_PARCEL tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step4_Transport_PARCEL_Current_FullSize.", "Step4_Transport_PARCEL");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP4_TRANSPORT_PARCEL tab: " + e.getMessage ( ));

        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step4_Transport_PARCEL_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step4_Transport_PARCEL_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step4_Transport_PARCEL_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP4_TRANSPORT_PARCEL tab: " + e.getMessage ( ));
        }

        Thread.sleep (1000);
    }


    //FILLING OUT STEP5 - "BIDDING MATRIX"
    @Test(priority = 6)
    public void openCreateTenderWizardStep5_BiddingMatrix() throws Exception {


        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions (driver);


        try {

            //Click "NEXT" button to go from step#4 to step#5
            action.moveToElement (driver.findElement (By.xpath ("//div[@class='x-btn x-box-item x-toolbar-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button[@class='x-btn-center']/span[contains(text(), 'Next')]"))).click ( ).build ( ).perform ( );

            //Waiting till the content of the Bidding Matrix is downloaded
            waitTillDescendentElementsAvailable (20, "//div[@id='silverlightControlHost']/object/descendant::param", "name", "InitParams", "STEP #5 - BIDDING MATRIX");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Unable to click on the NEXT button on STEP# 4 - TRANSPORTS: " + e.getMessage ( ));
            driver.quit ( );
        }


        try {

            //writingPageElementsIntoTXTfile("STEP5_BIDDING_MATRIX");

            //compareWebElementsOnPage (".\\src\\main\\resources\\Step2_TenderSettings_WebElements.txt", "STEP2_TENDER_SETTINGS");
            compareWebElementsInsideParentElement (".\\src\\main\\resources\\Step5_BIDDING_MATRIX_WebElements.txt", "STEP5_BIDDING_MATRIX", "//div[@id='silverlightControlHost']/descendant::*");

        } catch (IOException e) {
            logger.error ("Unable to run the 'compareWebElementsOnPage' method on STEP5_BIDDING_MATRIX tab: " + e.getMessage ( ));
        }

        try {

            //Click on the "Maximize" button
            js.executeScript ("arguments[0].click();", driver.findElement (By.xpath ("//div[@id='tenderWizardDlg_header-targetEl']/div[@class='x-tool x-box-item x-tool-default']/img[@class='x-tool-maximize']")));


            Thread.sleep (1000);

        } catch (NoSuchElementException e) {
            logger.error ("Unable to maximize the STEP5_BIDDING_MATRIX tab: " + e.getMessage ( ));
        }


        try {

            captureWebElementScreenshot ("//div[@id='tenderWizardDlg']", "Step5_BIDDING_MATRIX_Current_FullSize.", "Step5_BIDDING_MATRIX");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP5_BIDDING_MATRIX: " + e.getMessage ( ));
        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step5_BIDDING_MATRIX_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step5_BIDDING_MATRIX_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step5_BIDDING_MATRIX_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP5_BIDDING_MATRIX: " + e.getMessage ( ));
        }

        //Import Bidding Matrix from external "Default.ccx" file
        try {
            js.executeScript ("objShippingMatrix.Content.ShippingMatrixClient.ImportTestMatrix('D:/ccx3/Storage/Default.ccx');");

            //Waiting till the "Default.ccx" file is imported

            // waitForExtAjaxIsLoading (10, "STEP #3 - FREIGHT");
            //waitForExtAjaxIsLoading (30, "STEP #5 - BIDDING MATRIX - Default.ccx file");
            waitTillDescendentElementsAvailable (30, "//div[@id='silverlightControlHost']/object/descendant::param", "name", "InitParams", "STEP #5 - BIDDING MATRIX Grid");

            Thread.sleep (500);

        } catch (Exception e) {
            logger.error ("Cannot import 'Default.ccx' file on STEP5_BIDDING_MATRIX: " + e.getMessage ( ));
        }


        //Take a screenshot after downloading the "Default.ccx" file
        try {

            captureWebElementScreenshot ("//div[@id='silverlightControlHost']", "Step5_BIDDING_MATRIX_Grid_Current_FullSize.", "Step5_BIDDING_MATRIX_Grid");

        } catch (Exception e) {
            logger.error ("Cannot take a screenshot of STEP5_BIDDING_MATRIX_Grid: " + e.getMessage ( ));
        }


        try {
            String BaseLineImg = ".\\src\\main\\resources\\baseline_images\\Step5_BIDDING_MATRIX_Grid_Baseline_FullSize.png";
            String CurrentImg = ".\\src\\main\\resources\\current_images\\Step5_BIDDING_MATRIX_Grid_Current_FullSize.png";
            String DiffImg = ".\\src\\main\\resources\\difference_images\\Step5_BIDDING_MATRIX_Grid_Difference_FullSize.png";

            //check that the files exist in folders and compare pixel-by-pixel
            subtractImages (BaseLineImg, CurrentImg, DiffImg);

        } catch (IOException e) {
            logger.error ("Unable to run the 'subtractImages' method on STEP5_BIDDING_MATRIX_Grid: " + e.getMessage ( ));
        }


    }


    //FILLING OUT STEP6 - "SERVICE PROVIDERS"
    @Test(priority = 6)
    public void openCreateTenderWizardStep6_ServiceProviders() throws Exception {


        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions (driver);


        try {

            //Click "NEXT" button to go from step#5 to step#6
            action.moveToElement (driver.findElement (By.xpath ("//div[@class='x-btn x-box-item x-toolbar-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon']/em/button[@class='x-btn-center']/span[contains(text(), 'Next')]"))).click ( ).build ( ).perform ( );

            //Waiting till the content of the Bidding Matrix is downloaded
            //waitTillDescendentElementsAvailable (20, "//div[@id='silverlightControlHost']/object/descendant::param", "name", "InitParams", "STEP #5 - BIDDING MATRIX");
            //Waiting till the 'Transport - Step4 - Parcel' form is loading
           // explicitWaitsUntilElementVisible (10, "//div[@id='ParcelPanel-body']/table[@class='x-field x-form-item x-field-default x-autocontainer-form-item']/tbody/tr/td[@class='x-form-item-body x-form-cb-wrap']/input[@class='x-form-field x-form-checkbox']", "STEP #4 - TRANSPORT - PARCEL");
             waitForExtAjaxIsLoading (10, "STEP #6 - SERVICE PROVIDERS");
            //waitForExtAjaxIsReadyState( 10, "STEP #3 - FREIGHT");

            Thread.sleep (500);


        } catch (Exception e) {
            logger.error ("Unable to click on the NEXT button on STEP# 5 - BIDDING MATRIX: " + e.getMessage ( ));
            driver.quit ( );
        }
    }


    @AfterTest
    public void tearDown() throws Exception {


        try {
            String tdLineBeforeChange = "LOGIN window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Login_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Login_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Login_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Login_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Login_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Login_Difference</a>";
            String startText = "LOGIN screenshots. ";

            //inserting "LOGIN" page screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);

        } catch (IOException e8) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for LOGIN page: " + e8.getMessage ( ));
        }

        try {
            String tdLineBeforeChange = "ALL TENDERS window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\AllTenders_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">AllTenders_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\AllTenders_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">AllTenders_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\AllTenders_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">AllTenders_Difference</a>";
            String startText = "ALL TENDERS screenshot. ";

            //inserting "ALL TENDERS" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for ALL TENDERS page: " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step1_BasicInformation window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step1_BasicInformation_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step1_BasicInformation_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step1_BasicInformation_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step1_BasicInformation_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step1_BasicInformation_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step1_BasicInformation_Difference</a>";
            String startText = "STEP1_BASIC_INFORMATION screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP1_BASIC_INFORMATION form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step2_TenderSettings window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step2_TenderSettings_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step2_TenderSettings_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step2_TenderSettings_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step2_TenderSettings_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step2_TenderSettings_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step2_TenderSettings_Difference</a>";
            String startText = "STEP2_TENDER_SETTINGS screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP2_TENDER_SETTINGS form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step3_Freight window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step3_Freight_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step3_Freight_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step3_Freight_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step3_Freight_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step3_Freight_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step3_Freight_Difference</a>";
            String startText = "STEP3_FREIGHT screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP3_FREIGHT form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_ROAD window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_ROAD_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_ROAD_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_ROAD_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_ROAD_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_ROAD_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_ROAD_Difference</a>";
            String startText = "STEP4_TRANSPORT_ROAD tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - ROAD form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_RAILROAD window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_RAILROAD_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_RAILROAD_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_RAILROAD_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_RAILROAD_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_RAILROAD_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_RAILROAD_Difference</a>";
            String startText = "STEP4_TRANSPORT_RAILROAD tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - RAILROAD form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_INLAND_WATER window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_INLAND_WATER_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_INLAND_WATER_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_INLAND_WATER_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_INLAND_WATER_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_INLAND_WATER_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_INLAND_WATER_Difference</a>";
            String startText = "STEP4_TRANSPORT_INLAND_WATER tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - INLAND WATER form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_SHORT_SEA window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_SHORT_SEA_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_SHORT_SEA_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_SHORT_SEA_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_SHORT_SEA_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_SHORT_SEA_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_SHORT_SEA_Difference</a>";
            String startText = "STEP4_TRANSPORT_SHORT_SEA tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - SHORT SEA form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_SEA window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_SEA_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_SEA_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_SEA_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_SEA_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_SEA_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_SEA_Difference</a>";
            String startText = "STEP4_TRANSPORT_SEA tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - SEA form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_AIR window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_AIR_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_AIR_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_AIR_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_AIR_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_AIR_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_AIR_Difference</a>";
            String startText = "STEP4_TRANSPORT_AIR tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - AIR form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_WAREHOUSING window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_WAREHOUSING_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_WAREHOUSING_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_WAREHOUSING_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_WAREHOUSING_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_WAREHOUSING_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_WAREHOUSING_Difference</a>";
            String startText = "STEP4_TRANSPORT_WAREHOUSING tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - WAREHOUSING form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step4_Transport_PARCEL window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step4_Transport_PARCEL_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_PARCEL_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step4_Transport_PARCEL_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_PARCEL_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step4_Transport_PARCEL_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step4_Transport_PARCEL_Difference</a>";
            String startText = "STEP4_TRANSPORT_PARCEL tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP4_TRANSPORT - PARCEL form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step5_BIDDING_MATRIX window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step5_BIDDING_MATRIX_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step5_BIDDING_MATRIX_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step5_BIDDING_MATRIX_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step5_BIDDING_MATRIX_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step5_BIDDING_MATRIX_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step5_BIDDING_MATRIX_Difference</a>";
            String startText = "STEP5_BIDDING_MATRIX tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP5_BIDDING_MATRIX form : " + e9.getMessage ( ));
        }


        try {
            String tdLineBeforeChange = "Step5_BIDDING_MATRIX_Grid window screenshot was made. ";
            String baselineScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\baseline_images\\Step5_BIDDING_MATRIX_Grid_Baseline_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step5_BIDDING_MATRIX_Grid_Baseline</a>";
            String currentScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\current_images\\Step5_BIDDING_MATRIX_Grid_Current_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step5_BIDDING_MATRIX_Grid_Current</a>";
            String differenceScreen = "<a href=" + Paths.get (".\\src\\main\\resources\\difference_images\\Step5_BIDDING_MATRIX_Grid_Difference_FullSize.png").toRealPath ( ).toUri ( ).toURL ( ) + ">Step5_BIDDING_MATRIX_Grid_Difference</a>";
            String startText = "STEP5_BIDDING_MATRIX_Grid tab screenshot. ";

            //inserting "STEP1_BASIC_INFORMATION" tab screenshot links into "log4j-application.html" file
            insertingScreenshotLinksIntoLoggerHTMLfile (tdLineBeforeChange, baselineScreen, currentScreen, differenceScreen, startText);
        } catch (IOException e9) {
            logger.error ("Unable to run the 'insertingScreenshotLinksIntoLoggerHTMLfile' method for STEP5_BIDDING_MATRIX_Grid form : " + e9.getMessage ( ));
        }


        Thread.sleep (12000);
        driver.quit ( );
        LogManager.shutdown ( );

    }

}
