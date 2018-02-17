package com.generics;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Alex Grischenko on 9/4/2017.
 */
public class ComparingWebElements {

    final static Logger logger = Logger.getLogger (ComparingWebElements.class);

    //Compares page's web-elements in current build with the expected web-elements (to check for GUI modifications)
    public static void compareWebElementsOnPage(String WebElemSourceFile, String PageName) throws IOException {

        List<WebElement> LoginWebElements_Current = TestUnderIEbrowser.driver.findElements (new By.ByTagName ("*"));
        //System.out.println ("LIST SIZE (number of web-elements on LOGIN page): " + Integer.toString (LoginWebElements_Current.size ( )));
        logger.info ("CURRENT number of web-elements on " + PageName + " page (from dynamically created list): " + Integer.toString (LoginWebElements_Current.size ( )));

        List<String> LoginWebElements_CurrentConverted = new ArrayList<String> ( );
        for (WebElement elmnt : LoginWebElements_Current) {

            LoginWebElements_CurrentConverted.add (elmnt.getAttribute ("id"));
            //System.out.println (LoginWebElements_CurrentConverted);


        }
        Collections.sort(LoginWebElements_CurrentConverted);

        //Loading all web elements on Login page from the previous build from TXT file
        List<String> LoginWebElements_Original = new ArrayList<String> ( );


        try {

            FileReader fr = new FileReader (WebElemSourceFile);
            BufferedReader br = new BufferedReader (fr);
            String x = "";
            while ((x = br.readLine ( )) != null) {
                LoginWebElements_Original.add (x);


            }
            br.close ( );
            Collections.sort(LoginWebElements_Original);

            logger.info ("ORIGINAL number of web-elements on " + PageName + " page (from file): " + Integer.toString (LoginWebElements_Original.size ( )));
            assertEquals (LoginWebElements_Original.size ( ), LoginWebElements_CurrentConverted.size ( ));
            assertTrue (LoginWebElements_CurrentConverted.containsAll (LoginWebElements_Original));

            //This is to compare 2 list arrays (i.e. from txt file and currently created) element by elements - for debugging purpose only
        /*  for (int i=0; i<= LoginWebElements_Original.size();  i++ ) {

                  if (LoginWebElements_Original.get(i).contentEquals ( LoginWebElements_CurrentConverted.get(i))){
                             continue;
                  } else {

                      System.out.println ("ArrayLists are not equal in line: " + i);
                      System.out.println ("ArrayLists Original value: " + LoginWebElements_Original.get(i).toString ());
                      System.out.println ("ArrayLists Current value: " + LoginWebElements_CurrentConverted.get(i).toString ());

                  }


          }*/


        } catch (IOException e) {
            logger.warn ("Cannot read the text file - " + WebElemSourceFile + e.getMessage ( ));
        } catch (AssertionError e2) {
            logger.warn ("Number of web elements on " + PageName + " page is not the same as in previous versions: " + e2.getMessage ( ));
        }

       /* for ( WebElement el : LoginWebElements_Current ) {
            System.out.println (LoginWebElements_CurrentConverted.add (el.getTagName () + ": " + " <" + el.getAttribute ("id") + ">" + el.getText ()));
        }*/
    }




    //Compares page's web-elements in current build with the expected web-elements (to check for GUI modifications)
    public static void compareWebElementsInsideParentElement(String WebElemSourceFile, String PageName, String xpathExpression) throws IOException {

        List<WebElement> LoginWebElements_Current = TestUnderIEbrowser.driver.findElements (new By.ByXPath (xpathExpression));
        //System.out.println ("LIST SIZE (number of web-elements on LOGIN page): " + Integer.toString (LoginWebElements_Current.size ( )));
        logger.info ("CURRENT number of web-elements on " + PageName + " page (from dynamically created list): " + Integer.toString (LoginWebElements_Current.size ( )));

        List<String> LoginWebElements_CurrentConverted = new ArrayList<String> ( );
        for (WebElement elmnt : LoginWebElements_Current) {

            LoginWebElements_CurrentConverted.add (elmnt.getAttribute ("id"));
            //System.out.println (LoginWebElements_CurrentConverted);

        }

        Collections.sort(LoginWebElements_CurrentConverted);


        //Loading all web elements on Login page from the previous build from TXT file
        List<String> LoginWebElements_Original = new ArrayList<String> ( );


        try {

            FileReader fr = new FileReader (WebElemSourceFile);
            BufferedReader br = new BufferedReader (fr);
            String x = "";
            while ((x = br.readLine ( )) != null) {
                LoginWebElements_Original.add (x);


            }
            br.close ( );
            Collections.sort(LoginWebElements_Original);

            logger.info ("ORIGINAL number of web-elements on " + PageName + " page (from file): " + Integer.toString (LoginWebElements_Original.size ( )));
            assertEquals (LoginWebElements_Original.size ( ), LoginWebElements_CurrentConverted.size ( ));
           // assertTrue (LoginWebElements_CurrentConverted.containsAll (LoginWebElements_Original));

            //This is to compare 2 list arrays (i.e. from txt file and currently created) element by elements - for debugging purpose only
     /*    for (int i=0; i<= LoginWebElements_Original.size();  i++ ) {
              //for (int y=0; y<= LoginWebElements_CurrentConverted.size();  y++ ){
                  if (LoginWebElements_Original.get (i).equals (LoginWebElements_CurrentConverted.get (i))){
                             continue;
                  } else {

                      System.out.println ("ArrayLists are not equal in line: " + i);
                      System.out.println ("ArrayLists Original value: " + LoginWebElements_Original.get (i));
                      System.out.println ("ArrayLists Current value: " + LoginWebElements_CurrentConverted.get (i));

                  }
             // }

          }*/


        } catch (IOException e) {
            logger.warn ("Cannot read the text file - " + WebElemSourceFile + e.getMessage ( ));
        } catch (AssertionError e) {
            logger.warn ("Number of web elements on " + PageName + " page is not the same as in previous versions: " + e.getMessage ( ));
        }

       /* for ( WebElement el : LoginWebElements_Current ) {
            System.out.println (LoginWebElements_CurrentConverted.add (el.getTagName () + ": " + " <" + el.getAttribute ("id") + ">" + el.getText ()));
        }*/
    }





    //Use this to insert into various parts of code to create a TXT file with the list of all web-elements on selected web-page
    public static void writingPageElementsIntoTXTfile(String PageName) throws IOException {
        FileWriter fWriter = new FileWriter (".\\src\\main\\resources\\Step5_BIDDING_MATRIX_WebElements.txt");
        BufferedWriter writer = new BufferedWriter (fWriter);
        String finalList = new String ( );


        //TestUnderIEbrowser.driver.findElements (new By.ByXPath ("//*[@id='tenderWizardDlg-body']/descendant::*"));

        List<WebElement> LoginWebElements_Current = TestUnderIEbrowser.driver.findElements (new By.ByXPath ("//div[@id='silverlightControlHost']/descendant::*")); //finds all elems inside an elem
        System.out.println ("CURRENT number of web-elements on " + PageName + " page (from dynamically created list): " + Integer.toString (LoginWebElements_Current.size ( )));


        List<String> LoginWebElements_CurrentConverted = new ArrayList<String> ( );

        for (WebElement elmnt : LoginWebElements_Current) {

            LoginWebElements_CurrentConverted.add (elmnt.getAttribute ("id"));

        }

        for (String elemnt : LoginWebElements_CurrentConverted) {

            writer.write (elemnt + System.getProperty ("line.separator"));
            //writer.newLine ( ); //this is not actually needed for html files - can make your code more readable though

        }

        writer.close ( ); //make sure you close the writer object

    }

}
