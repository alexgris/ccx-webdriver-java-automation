package com.generics;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static com.generics.TestUnderIEbrowser.driver;


/**
 * Created by Alex Grischenko on 8/29/2017.
 */
public class WebElementScreenshot {

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger  (WebElementScreenshot.class);

    //Use this method to create a current image of a certain form or page
    public static void captureWebElementScreenshot(String ElemXpath, String FileName, String WindowName)  {

        try {
            //Locate Image element to capture screenshot.
            WebElement Image1 = driver.findElement (By.xpath (ElemXpath));


            //JavascriptExecutor jss = (JavascriptExecutor) driver;
            //WebElement Image1 = null;

            //Thread.sleep (5000);


            //Find element by ID using JS
            //Image1 = (WebElement) jss.executeScript("return document.getElementById('ext-gen1058');", Image1);


            //Call captureElementScreenshot function to capture screenshot of element.
            //captureElementScreenshot (Image1);


            //Capture entire page screenshot as buffer.
            //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
            File screen = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);

            //Used selenium getSize() method to get height and width of element.
            //Retrieve width of element.
            int ImageWidth = Image1.getSize ( ).getWidth ( );
            //Retrieve height of element.
            int ImageHeight = Image1.getSize ( ).getHeight ( );

            //Used selenium Point class to get x y coordinates of Image element.
            //get location(x y coordinates) of the element.
            Point point = Image1.getLocation ( );
            int xcord = point.getX ( );
            int ycord = point.getY ( );

            String format = "png";

            String fullPath = ".\\src\\main\\resources\\current_images\\" + FileName + format;

            //Reading full image screenshot.
            BufferedImage img = ImageIO.read (screen);

            //cut Image using height, width and x y coordinates parameters.
            BufferedImage dest = img.getSubimage (xcord, ycord, ImageWidth, ImageHeight);
            ImageIO.write (dest, format, new File (fullPath));

            logger.info (WindowName + " window screenshot was made. ");


            //Used FileUtils class of apache.commons.io.
            //save Image screenshot In D: drive.
            //FileUtils.copyFile(screen, new File("D:\\screenshot.png"));

        } catch (Exception e9) {
            logger.error ("Unable to run 'captureWebElementScreenshot' method: " + e9.getMessage ( ));
        }
    }


    //Use this method to insert in code to create a baseline image of a certain form or page
    public static void captureWebElementScreenshotBASELINEimages(String ElemXpath, String FileName, String WindowName)  {

        try {
            //Locate Image element to capture screenshot.
            WebElement Image1 = driver.findElement (By.xpath (ElemXpath));


            //JavascriptExecutor jss = (JavascriptExecutor) driver;
            //WebElement Image1 = null;

            //Thread.sleep (5000);


            //Find element by ID using JS
            //Image1 = (WebElement) jss.executeScript("return document.getElementById('ext-gen1058');", Image1);


            //Call captureElementScreenshot function to capture screenshot of element.
            //captureElementScreenshot (Image1);


            //Capture entire page screenshot as buffer.
            //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
            File screen = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);

            //Used selenium getSize() method to get height and width of element.
            //Retrieve width of element.
            int ImageWidth = Image1.getSize ( ).getWidth ( );
            //Retrieve height of element.
            int ImageHeight = Image1.getSize ( ).getHeight ( );

            //Used selenium Point class to get x y coordinates of Image element.
            //get location(x y coordinates) of the element.
            Point point = Image1.getLocation ( );
            int xcord = point.getX ( );
            int ycord = point.getY ( );

            String format = "png";

            String fullPath = ".\\src\\main\\resources\\baseline_images\\" + FileName + format;

            //Reading full image screenshot.
            BufferedImage img = ImageIO.read (screen);

            //cut Image using height, width and x y coordinates parameters.
            BufferedImage dest = img.getSubimage (xcord, ycord, ImageWidth, ImageHeight);
            ImageIO.write (dest, format, new File (fullPath));

            logger.info (WindowName + " window screenshot was made. ");


            //Used FileUtils class of apache.commons.io.
            //save Image screenshot In D: drive.
            //FileUtils.copyFile(screen, new File("D:\\screenshot.png"));

        } catch (Exception e9) {
            logger.error ("Unable to run 'captureWebElementScreenshot' method: " + e9.getMessage ( ));
        }
    }




    public static void captureElementScreenshot(WebElement element) throws IOException {
        //Capture entire page screenshot as buffer.
        //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
        File screen = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);

        //Used selenium getSize() method to get height and width of element.
        //Retrieve width of element.
        int ImageWidth = element.getSize ( ).getWidth ( );
        //Retrieve height of element.
        int ImageHeight = element.getSize ( ).getHeight ( );

        //Used selenium Point class to get x y coordinates of Image element.
        //get location(x y coordinates) of the element.
        Point point = element.getLocation ( );
        int xcord = point.getX ( );
        int ycord = point.getY ( );

        String format = "png";
        String fileName = ".\\src\\main\\resources\\current_images\\"+"Login_Current_FullSize." + format;

        //Reading full image screenshot.
        BufferedImage img = ImageIO.read (screen);

        //cut Image using height, width and x y coordinates parameters.
        BufferedImage dest = img.getSubimage (xcord, ycord, ImageWidth, ImageHeight);
        ImageIO.write (dest, format, new File (fileName));


        //Used FileUtils class of apache.commons.io.
        //save Image screenshot In D: drive.
        //FileUtils.copyFile(screen, new File("D:\\screenshot.png"));
    }


    public static void TakeFullSizeScreenshot(String fileName) throws InterruptedException, IOException {

        try {
            Robot robot = new Robot ( );
            String format = "png";
            String fullFileName = fileName + format;

            Rectangle screenRect = new Rectangle (Toolkit.getDefaultToolkit ( ).getScreenSize ( ));
            BufferedImage screenFullImage = robot.createScreenCapture (screenRect);
            ImageIO.write (screenFullImage, format, new File (fullFileName));

            //logger.info ("A full screenshot saved!");
            //System.out.println ("A full screenshot saved!");

        } catch (AWTException e10) {
            logger.error ("Unable to run 'TakeFullSizeScreenshot' method: " + e10.getMessage ());
            //System.err.println (e);
        }


    }


}