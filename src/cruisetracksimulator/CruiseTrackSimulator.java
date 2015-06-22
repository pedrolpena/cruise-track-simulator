/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruisetracksimulator;

import java.io.*;
import java.util.*;
import gnu.io.*;

/**
 *
 * @author pena
 */
public class CruiseTrackSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double latStart = 25.77;
        double latEnd = 35.583;
        double lonStart = -80.19;
        double lonEnd = -5.75;
        double speed = 20;
        double knots = 20;
        double period = 1;
        double distanceInPeriod = 0;
        double totalDistance = 0;
        double bearing;
        double currentBearing = 0;
        long currentTime;
        long previousTime;

        String wantedPort = "/dev/ttyUSB0";

        if (args != null && args.length >= 6) {
            wantedPort = args[0];
            latStart = new Double(args[1]);
            lonStart = new Double(args[2]);
            latEnd = new Double(args[3]);
            lonEnd = new Double(args[4]);
            knots = new Double(args[5]);
            speed = 1.852 * knots;
        }//end if
        else {
            System.out.println("usage: cruiseTrackSimulator"
                    + " serialPort "
                    + " startingLatitude"
                    + " startingLongitude "
                    + " endingLatitude "
                    + " endingLongitude "
                    + " speedInKnots");
            //System.exit(0);
        }
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier portId = null;
        SerialPort port = null;
        PrintStream os = null;

        while (portIdentifiers.hasMoreElements()) {
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();

            if (pid.getPortType() == CommPortIdentifier.PORT_SERIAL && pid.getName().equals(wantedPort)) {

                portId = pid;

            }// end if

        }// end while

        try {
            port = (SerialPort) portId.open("NMEA Simulator", 10000);
            port.setSerialPortParams(4800, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            os = new PrintStream(port.getOutputStream(), true);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);

        }

        Position currentPosition = new Position(latStart, lonStart);
        Position startPosition = new Position(latStart, lonStart);
        Position endPosition = new Position(latEnd, lonEnd);
        EarthGeodesics cs = new EarthGeodesics();
        NMEASentenceGenerator nsg = new NMEASentenceGenerator();

        bearing = cs.getBearing(startPosition, endPosition);
        currentBearing = bearing;
        totalDistance = cs.getGreatCircleDistance(startPosition, endPosition);

        System.out.println("       Starting: " + startPosition);
        System.out.println("         Ending: " + endPosition);
        System.out.println("Initial Bearing: " + currentBearing + " Degrees");
        System.out.println("          Speed: " + knots + " Knots");
        System.out.println(" Total Distance: " + totalDistance + "Km");
        System.out.println("    Serial Port: " + wantedPort);

        previousTime = System.currentTimeMillis();

        try {
            InputStreamReader fis = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(fis);
            os.print(nsg.generateGPRMC(currentPosition, currentBearing, knots) + "\n\r");
            os.flush();
            while (true) {

                Thread.sleep(100);
                currentTime = System.currentTimeMillis();
                if (currentTime - previousTime >= (long) period * 1000 - 100) {
                    distanceInPeriod = (currentTime - previousTime) * speed / 3600000;
                    currentPosition.setPosition(cs.getNextPosition(currentPosition, distanceInPeriod, currentBearing));
                    currentBearing = cs.getBearing(currentPosition, endPosition);

                    os.print(nsg.generateGPRMC(currentPosition, currentBearing, knots) + "\n\r");
                    os.flush();

                    previousTime = currentTime;
                }//end if

                if (br.ready()) {

                    if (br.read() == '+') {
                        knots++;
                        speed = 1.852 * knots;
                        System.out.println(nsg.generateGPRMC(currentPosition, currentBearing, knots));

                    }//end if
                    if (br.read() == '-') {
                        if (knots > 0) {
                            knots--;
                        }
                        speed = 1.852 * knots;
                        System.out.println(nsg.generateGPRMC(currentPosition, currentBearing, knots));

                    }//end if 
                }//end if

            }//end while
        }//end try
        catch (Exception e) {
            e.printStackTrace();
        }//end catch
    }// end main

}// end class

