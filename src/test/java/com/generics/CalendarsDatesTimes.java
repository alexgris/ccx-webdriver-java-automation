package com.generics;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TimeZone;

import static com.generics.TestUnderIEbrowser.driver;

/**
 * Created by Alex Grischenko on 10/3/2017.
 */
public class CalendarsDatesTimes {

    final static Logger logger = Logger.getLogger (ComparingScreenshots.class);


    //Get The Current Day
    public static String getCurrentDay(String simpDateFormat) {

        Date currentDatePlusOne = new Date ( );
        DateFormat dateFormat = new SimpleDateFormat (simpDateFormat);

        try {


            //Create a Calendar Object
            Calendar calendar = Calendar.getInstance (TimeZone.getDefault ( ));
            // Calendar calendar = Calendar.getInstance (TimeZone.getTimeZone("Europe/Minsk"));

            //Get Current Day as a number
            // int todayInt = calendar.get (Calendar.DATE);


            calendar.get (Calendar.DATE);
            calendar.get (Calendar.HOUR_OF_DAY);
            calendar.get (Calendar.MINUTE);
            calendar.get (Calendar.SECOND);
            calendar.get (Calendar.MILLISECOND);

            //System.out.println("Today Int: " + todayInt +"\n");

            // convert calendar to date
            currentDatePlusOne = calendar.getTime ( );

            //Integer to String Conversion
            //String todayStr = Integer.toString (todayInt);
            // System.out.println("Today Str: " + todayStr + "\n");


        } catch (Exception e) {
            logger.error ("Unable to retrieve current date or calendar object in 'getCurrentDay' method: " + e.getMessage ( ));
        }

        return dateFormat.format (currentDatePlusOne);
    }


    //Example to add 1 year, 1 month, 1 day, 1 hour, 1 minute and 1 second to the current date.
    public static String incrementYYmmDDhhMMssBy1(int year, int month, int date, int hour, int minute, int second, String simpDateFormat) {

        //DateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat (simpDateFormat);
        Date currentDate = new Date ( );
        Date currentDatePlusOne = new Date ( );
        String dateToString = "";

        try {

            // System.out.println (dateFormat.format (currentDate));

            // convert date to calendar
            Calendar c = Calendar.getInstance ( );
            c.setTime (currentDate);

            // manipulate date
            c.add (Calendar.YEAR, year);
            c.add (Calendar.MONTH, month);
            c.add (Calendar.DATE, date); //same with c.add(Calendar.DAY_OF_MONTH, 1);
            c.add (Calendar.HOUR, hour);
            c.add (Calendar.MINUTE, minute);
            c.add (Calendar.SECOND, second);

            // convert calendar to date
            currentDatePlusOne = c.getTime ( );

            //String incrementedYYmmDDhhMMss = dateFormat.format (currentDatePlusOne);
            //System.out.println ("\nOutput : " + dateFormat.format (currentDatePlusOne));


            dateToString = dateFormat.format (currentDatePlusOne);


            //Get to string 2 first digits (HH) of current time
          /*  String dateToString1 = dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6);
            //System.out.println ("RETRIEVED TTT: " + dateToString1);

            if (simpDateFormat == "hh:mm a") {

                switch (dateToString1) {

                    case "00":
                        dateToString1 = "12";
                        break;
                    case "13":
                        dateToString1 = "1";
                        break;
                    case "14":
                        dateToString1 = "2";
                        break;
                    case "15":
                        dateToString1 = "3";
                        break;
                    case "16":
                        dateToString1 = "4";
                        break;
                    case "17":
                        dateToString1 = "5";
                        break;
                    case "18":
                        dateToString1 = "6";
                        break;
                    case "19":
                        dateToString1 = "7";
                        break;
                    case "20":
                        dateToString1 = "8";
                        break;
                    case "21":
                        dateToString1 = "9";
                        break;
                    case "22":
                        dateToString1 = "10";
                        break;
                    case "23":
                        dateToString1 = "11";
                        break;
                    default:
                        ;
                }*/

/*
                if (dateToString.substring (dateToString.length ( ) - 2).equals ("AM")) {
                    if (Integer.valueOf (dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6)) == 12) {

                        //Change AM to PM after 12PM (afternoon)
                        dateToString = dateToString.replace ("AM", "PM");

                        //Replace 24 hours to 12 hours after 12PM
                        dateToString = dateToString.replace (dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6), dateToString1);

                       // return dateToString;
                    }
                } else {
                   // System.out.println ("dateToString.substring (dateToString.length ( ) - 2).equals (\"pm\"): " + dateToString.substring (dateToString.length ( ) - 2));
                    if (dateToString.substring (dateToString.length ( ) - 2).equals ("PM")) {
                        if (Integer.valueOf (dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6)) == 12) {

                            //Change PM to AM after 12AM (midnight)
                            dateToString = dateToString.replace ("PM", "AM");

                            //Replace 24 hours to 12 hours after 12PM
                            dateToString = dateToString.replace (dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6), dateToString1);

                          //  return dateToString;
                        }

                    }
                }*/
            // dateToString = dateToString.replace (dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6), dateToString1);
            //return dateToString;

/*
            } else {

                if (dateToString1 == "24") {

                    dateToString1 = "00";
                    dateToString = dateToString.replace (dateToString.substring (dateToString.length ( ) - 5, dateToString.length ( ) - 3), dateToString1);
                    //return dateToString;

                }

            }*/


            // return dateFormat.format (currentDateConverted);


        } catch (Exception e) {
            logger.error ("Unable to increment year, month, date, hour, minute or second in 'incrementYYmmDDhhMMssBy_1' method: " + e.getMessage ( ));
        }
        return dateToString;
        //return dateFormat.format (currentDatePlusOne);

    }


    //Example to add 1 year, 1 month, 1 day, 1 hour, 1 minute and 1 second to the current date/time displayed on the Shipper's dashboard.
    public static String incrementYYmmDDhhMMssBy2(int year, int month, int date, int hour, int minute, int second, String simpDateFormat) {

        //DateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat (simpDateFormat);
        Date currentDate = new Date ( );
        Date currentDatePlusOne = new Date ( );
        Date d = new Date ( );
        String dateToString = "";

        try {

            // System.out.println (dateFormat.format (currentDate));


            // convert date to calendar
            Calendar c = Calendar.getInstance ( );

            c.setTime (dateFormat.parse (getCurrentTimeFromShippersDashboardAndConvert24Hto12H ( )));

            // manipulate date
            c.add (Calendar.YEAR, year);
            c.add (Calendar.MONTH, month);
            c.add (Calendar.DATE, date); //same with c.add(Calendar.DAY_OF_MONTH, 1);
            c.add (Calendar.HOUR, hour);
            c.add (Calendar.MINUTE, minute);
            c.add (Calendar.SECOND, second);

            // convert calendar to date
            currentDatePlusOne = c.getTime ( );

            //String incrementedYYmmDDhhMMss = dateFormat.format (currentDatePlusOne);
            //System.out.println ("\nOutput : " + dateFormat.format (currentDatePlusOne));


            dateToString = dateFormat.format (currentDatePlusOne);


        } catch (Exception e) {
            logger.error ("Unable to increment year, month, date, hour, minute or second in 'incrementYYmmDDhhMMssBy_1' method: " + e.getMessage ( ));
        }


        return dateToString;
        //return dateToString;
        //return dateFormat.format (currentDatePlusOne);

    }


    //Example to add 1 year, 1 month, 1 day, 1 hour, 1 minute and 1 second to the current date/time displayed on the Shipper's dashboard.
    public static String incrementYYmmDDhhMMssBy3(int year, int month, int date, int hour, int minute, int second, String simpDateFormat) {

        //DateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat (simpDateFormat);
        Date currentDate = new Date ( );
        Date currentDatePlusOne = new Date ( );
        Date d = new Date ( );
        String dateToString = "";

        try {

            // System.out.println (dateFormat.format (currentDate));


            // convert date to calendar
            Calendar c = Calendar.getInstance ( );

            c.setTime (dateFormat.parse (getCurrentTimeFromShippersDashboardIn24Hformat ( )));

            // manipulate date
            c.add (Calendar.YEAR, year);
            c.add (Calendar.MONTH, month);
            c.add (Calendar.DATE, date); //same with c.add(Calendar.DAY_OF_MONTH, 1);
            c.add (Calendar.HOUR, hour);
            c.add (Calendar.MINUTE, minute);
            c.add (Calendar.SECOND, second);

            // convert calendar to date
            currentDatePlusOne = c.getTime ( );

            //String incrementedYYmmDDhhMMss = dateFormat.format (currentDatePlusOne);
            //System.out.println ("\nOutput : " + dateFormat.format (currentDatePlusOne));


            dateToString = dateFormat.format (currentDatePlusOne);


            //Get to string 2 first digits (HH) of current time
            String dateToString1 = dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6);


            if (dateToString1 == "24") {

                dateToString1 = "00";
                dateToString = dateToString.replace (dateToString.substring (dateToString.length ( ) - 5, dateToString.length ( ) - 3), dateToString1);
                //return dateToString;

            }


        } catch (Exception e) {
            logger.error ("Unable to increment year, month, date, hour, minute or second in 'incrementYYmmDDhhMMssBy_1' method: " + e.getMessage ( ));
        }
        return dateToString;
        //return dateFormat.format (currentDatePlusOne);

    }


    //Use the plus and minus methods to manipulate LocalDate, LocalDateTime and ZoneDateTime
    public static String changeLocalDateTime(int years, int months, int days, int hours, int minutes, int seconds, String simpDateFormat) {

        String DATE_FORMAT = simpDateFormat;
        DateFormat dateFormat = new SimpleDateFormat (DATE_FORMAT);
        //DateTimeFormatter dateFormat8 = DateTimeFormatter.ofPattern (DATE_FORMAT);

        // Get current date
        Date currentDate = new Date ( );

        Date currentDateConverted = new Date ( );

        String dateToString = "";

        try {

            //System.out.println ("date : " + dateFormat.format (currentDate));

            // convert date to localdatetime
            LocalDateTime localDateTime = currentDate.toInstant ( ).atZone (ZoneId.systemDefault ( )).toLocalDateTime ( );
            //System.out.println ("localDateTime : " + dateFormat8.format (localDateTime));

            // plus one
            localDateTime = localDateTime.plusYears (years).plusMonths (months).plusDays (days);
            localDateTime = localDateTime.plusHours (hours).plusMinutes (minutes).plusSeconds (seconds);

            // convert LocalDateTime to date
            currentDateConverted = Date.from (localDateTime.atZone (ZoneId.systemDefault ( )).toInstant ( ));

/*
            System.setProperty("user.timezone", "Europe/Minsk"), but before Calendar.getInstance() call.
                    System.setProperty("user.timezone", "UTC");
            TimeZone.setDefault(null);
            TimeZone.setDefault(System.setProperty("user.timezone", "Europe/Minsk"))*/

            //System.out.println ("\nOutput : " + dateFormat.format (currentDatePlusOneDay));

            dateToString = dateFormat.format (currentDateConverted);

            //Get to string 2 first digits (HH) of current time
            String dateToString1 = dateToString.substring (dateToString.length ( ) - 8, dateToString.length ( ) - 6);
            //System.out.println ("RETRIEVED TTT: " + dateToString1);


            if (dateToString1 == "24") {

                dateToString1 = "00";
                dateToString = dateToString.replace (dateToString.substring (dateToString.length ( ) - 5, dateToString.length ( ) - 3), dateToString1);

            }

        } catch (Exception e) {
            logger.error ("Unable to change LocalDate, LocalDateTime or ZoneDateTime in 'changeLocalDateTime' method: " + e.getMessage ( ));
        }

        return dateToString;
    }


    public static String roundingMinutes(String currentTime) {
        DecimalFormat formatter = new DecimalFormat ("00");

        String rounded_time1HourFromCurrentTime_minutes = currentTime.substring (currentTime.length ( ) - 5, currentTime.length ( ) - 3);
        System.out.println ("roundingMinutes method minutes: " + rounded_time1HourFromCurrentTime_minutes);

        //Round current minutes to an integer in 30 increments (half an hour)
        Integer roundedCurrentMM = ((Integer.valueOf (rounded_time1HourFromCurrentTime_minutes) + 29) / 30) * 30;

        //Convert minutes to 2-digit number (e.g. "7" to "07")
        String roundedCurrentMM1 = formatter.format (roundedCurrentMM);

        //Insert rounded minutes into a time variable
        currentTime = currentTime.replace (rounded_time1HourFromCurrentTime_minutes, roundedCurrentMM1);

        /*
        if (Integer.valueOf (rounded_time1HourFromCurrentTime_minutes) > 30) {
            currentTime = currentTime.replace (rounded_time1HourFromCurrentTime_minutes, "30");
        } else {
            if (Integer.valueOf (rounded_time1HourFromCurrentTime_minutes) > 00 && Integer.valueOf (rounded_time1HourFromCurrentTime_minutes) < 30) {
                currentTime = currentTime.replace (rounded_time1HourFromCurrentTime_minutes, "00");
            }
        }*/

        return currentTime;
    }


    public static String getCurrentTimeFromShippersDashboardAndConvert24Hto12H() {

        String dashboardCurrentTime = "";

        //Getting date and time from the shipper's dashboard
        try {
            dashboardCurrentTime = driver.findElement (By.xpath ("//*[@id='appCurDateTime']/.//span[contains(@class,'x-label-value')]")).getText ( );
            System.out.println ("Current DASHBOARD date/time: " + dashboardCurrentTime);
        } catch (NoSuchElementException e) {
            logger.error ("No such element - SHIPPER's TIMER on DASHBOARD was not found: " + e.getMessage ( ));
        }


        //Getting to string the time value only (out of date, month, year and time)
        dashboardCurrentTime = dashboardCurrentTime.substring (dashboardCurrentTime.length ( ) - 5);
        System.out.println ("Current DASHBOARD TIME: " + dashboardCurrentTime);
        // Integer dashboardCurrentTimeHH1 = Integer.valueOf (dashboardCurrentTimeHH);

        //Replace space with "0" at the beginning if hours are not a 2 digit number
        if (dashboardCurrentTime.substring (0, 1).equals (" ")) {

            dashboardCurrentTime = dashboardCurrentTime.replace (dashboardCurrentTime.substring (0, 1), "0");
            System.out.println ("Trimmed time from shipper's Dashboard: " + dashboardCurrentTime);

        }

        //Convert 24H format to 12H format
        String convertedTimeFormat = LocalTime.parse (dashboardCurrentTime, DateTimeFormatter.ofPattern ("H:mm")).format (DateTimeFormatter.ofPattern ("hh:mm a"));
        System.out.println ("Converted time from 24H to 12H: " + convertedTimeFormat);

       /* String convertedTimeFormat2 = convertedTimeFormat.substring (0,2);

        switch (convertedTimeFormat2) {

            case "13":
                convertedTimeFormat2 = "1";
                break;
            case "14":
                convertedTimeFormat2 = "2";
                break;
            case "15":
                convertedTimeFormat2 = "3";
                break;
            case "16":
                convertedTimeFormat2 = "4";
                break;
            case "17":
                convertedTimeFormat2 = "5";
                break;
            case "18":
                convertedTimeFormat2 = "6";
                break;
            case "19":
                convertedTimeFormat2 = "7";
                break;
            case "20":
                convertedTimeFormat2 = "8";
                break;
            case "21":
                convertedTimeFormat2 = "9";
                break;
            case "22":
                convertedTimeFormat2 = "10";
                break;
            case "23":
                convertedTimeFormat2 = "11";
                break;
            default:
                ;
        }

        convertedTimeFormat = convertedTimeFormat.replace (convertedTimeFormat.substring (0,2), convertedTimeFormat2);*/

        return convertedTimeFormat;
    }


    public static String getCurrentTimeFromShippersDashboardIn24Hformat() {

        String dashboardCurrentTime = "";

        //Getting date and time from the shipper's dashboard
        try {
            dashboardCurrentTime = driver.findElement (By.xpath ("//*[@id='appCurDateTime']/.//span[contains(@class,'x-label-value')]")).getText ( );
            System.out.println ("Current DASHBOARD date/time: " + dashboardCurrentTime);
        } catch (NoSuchElementException e) {
            logger.error ("No such element - SHIPPER's TIMER on DASHBOARD was not found: " + e.getMessage ( ));
        }


        //Getting to string the time value only (out of date, month, year and time)
        dashboardCurrentTime = dashboardCurrentTime.substring (dashboardCurrentTime.length ( ) - 5);
        System.out.println ("Current DASHBOARD TIME: " + dashboardCurrentTime);
        // Integer dashboardCurrentTimeHH1 = Integer.valueOf (dashboardCurrentTimeHH);

        if (dashboardCurrentTime.substring (0, 1).equals (" ")) {

            dashboardCurrentTime = dashboardCurrentTime.replace (dashboardCurrentTime.substring (0, 1), "0");
            System.out.println ("Trimmed time from shipper's Dashboard: " + dashboardCurrentTime);

        }


        return dashboardCurrentTime;
    }


}
