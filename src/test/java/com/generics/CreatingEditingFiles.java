package com.generics;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Paths;

/**
 * Created by Alex Grischenko on 9/25/2017.
 */
public class CreatingEditingFiles {

    final static Logger logger = Logger.getLogger (CreatingEditingFiles.class);


    public static void insertingScreenshotLinksIntoLoggerHTMLfile(String tdLineBeforeChange, String baselineScreen, String currentScreen, String differenceScreen, String startText) throws IOException {

        File input = new File (".\\src\\main\\resources\\log_files\\log4j-application.html");
        org.jsoup.nodes.Document doc = Jsoup.parse (input, "UTF-8", "");
        FileWriter fWriter = null;
        BufferedWriter writer = null;


        String html2 = doc.outerHtml ( ).toString ( );



        String tdLineAfterChange = startText + " " + baselineScreen + " " + currentScreen + " " + differenceScreen;

        if (html2.contains (tdLineBeforeChange)) {
            String replaceLine = html2.replace (tdLineBeforeChange, tdLineAfterChange);


            try {
                fWriter = new FileWriter (".\\src\\main\\resources\\log_files\\log4j-application.html");
                writer = new BufferedWriter (fWriter);
                writer.write (replaceLine);
                writer.newLine ( ); //this is not actually needed for html files - can make your code more readable though
                writer.close ( ); //make sure you close the writer object



            } catch (Exception e) {
                logger.error ("Unable to write changes into \"log4j-application.html\" file: " + e.getMessage ( ));
            }

            //logger.info ("Changes to \"log4j-application.html\" file have been made successfully.");

        }
    }


    public static void replaceElementInHTMLfile(String filePath, String htmlTag, String htmlTagAttributeKey, String htmlTagAttributeKeyValue, String replaceWithText) throws IOException {

        File input = new File (filePath);
        org.jsoup.nodes.Document doc = Jsoup.parse (input, "UTF-8", "");
        String html = doc.outerHtml ( ).toString ( );

        Elements items = doc.select (htmlTag);

        for (Element item : items) {
            String href = item.attr (htmlTagAttributeKey);

            try {
                if (href.contains (htmlTagAttributeKeyValue)) {

                    html.replaceAll (href, replaceWithText);
                }


            } catch (Exception e) {
                logger.error ("Unable to replace content in HTML  file: " + e.getMessage ( ));
            }
        }

    }


    public static void editFileThroughStringBuilder(String newFilePathAndName, String pathToHTMLfile, String stringToEdit, String subStringToInsert, int startIndex) throws IOException {

        StrBuilder buf = new StrBuilder ( );
        File file = new File (newFilePathAndName);
        FileWriter fWriter = new FileWriter (file);

        try (BufferedReader in = new BufferedReader (new FileReader (pathToHTMLfile))) {
            String str;
            while ((str = in.readLine ( )) != null) {

                buf.append (str);
                if (str.equals (stringToEdit)) {
                    buf.replace (startIndex, str.length ( ), subStringToInsert);
                }
            }


            fWriter.write (String.valueOf (buf));
            fWriter.close ( );

        } catch (IOException e) {

            logger.error ("Unable to replace content in HTML  file using StrBuilder : " + e.getMessage ( ));

        }

    }

}
