package com.generics;


import java.io.IOException;
import java.util.logging.*;


/**
 * Created by Alex Grischenko on 8/23/2017.
 */
public class MyLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
/*
        // set the LogLevel to Severe, only severe Messages will be written
        logger.setLevel(Level.SEVERE);
        logger.severe("Info Log");
        logger.warning("Info Log");
        logger.info("Info Log");
        logger.finest("Really not important");

        // set the LogLevel to Info, severe, warning and info will be written
        // finest is still not written
        logger.setLevel(Level.INFO);
        logger.severe("Info Log");
        logger.warning("Info Log");
        logger.info("Info Log");
        logger.finest("Really not important");*/

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging.txt");
        fileHTML = new FileHandler("Logging.html");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // create an HTML formatter
        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }
}
