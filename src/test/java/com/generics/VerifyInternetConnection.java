package com.generics;

import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex Grischenko on 9/4/2017.
 */
public class VerifyInternetConnection {

    final static Logger logger = Logger.getLogger (VerifyInternetConnection.class);

    //Checking whether the Inet connection is available via SocketAddress before launching the test
    public static boolean checkIntConnection(String host) {
        boolean status = false;
        Socket sock = new Socket ( );
        InetSocketAddress address = new InetSocketAddress (host, 80);
        long startTime = System.nanoTime ( );

        try {
            sock.connect (address, 10000);
            if (sock.isConnected ( )) {
                status = true;
                long endTime = System.nanoTime ( );
                double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert ((endTime - startTime), TimeUnit.NANOSECONDS) / 1000.0;
                logger.info ("INTERNET connection is AVAILABLE. Socket responded in " + elapsedTimeInSeconds + " seconds.");
                TestUnderIEbrowser.getBrowserAndVersion ( );
                return status;
            }
        } catch (Exception e) {
            logger.warn ("INTERNET connection is NOT AVAILABLE: " + e.getMessage ( ));
            status = false;
            return status;
        } finally {
            try {
                sock.close ( );
            } catch (Exception e) {
                logger.info ("FAILED to close the connection: " + e.getMessage ( ));
            }
        }

        return status;
    }



      /*
      //Checking whether the Inet connection is available by PING before launching the test
      public static boolean isReachableByPing(String host) {
          try {
              String cmd = "";
              if (System.getProperty ("os.name").startsWith ("Windows")) {
                  // For Windows
                  cmd = "ping -n 1" + host;
              } else {
                  // For Linux and OSX
                  cmd = "ping -c 1 " + host;
              }


              Process myProcess = java.lang.Runtime.getRuntime().exec(cmd);
              myProcess.waitFor();
              System.out.println ("myProcess: " + myProcess);

              if (myProcess.exitValue ( ) == 0) {
                  System.out.println ("INTERNET connection is AVAILABLE.");
                  TestUnderIEbrowser.getBrowserAndVersion ( );
                  return true;

              } else {
                  System.out.println ("WARNING: INTERNET connection is NOT AVAILABLE.");
                  return false;
              }

          } catch (Exception e) {

              e.printStackTrace ( );
              return false;
          }
      }
  */

}
