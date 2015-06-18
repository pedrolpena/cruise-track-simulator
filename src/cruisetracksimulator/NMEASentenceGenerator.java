/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruisetracksimulator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author pena
 */
public class NMEASentenceGenerator {

    String generateGPRMC(Position p, double bearing, double sog) {
        SimpleDateFormat utcDate = new SimpleDateFormat("ddMMyy");
        SimpleDateFormat utcTime = new SimpleDateFormat("HHmmss");
        utcDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        utcTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = utcDate.format(new Date());
        String time = utcTime.format(new Date());
        String magVar = "003.1,W";
        double lat = p.getLatitude();
        double lon = p.getLongitude();
        String latDirection = p.getLatitudeDirection();
        String lonDirection = p.getLongitudeDirection();
        String latS = p.getLatitudeDegrees() + p.getLatitudeMinutes();
        String lonS = p.getLongitudeDegrees() + p.getLongitudeMiuntes();
        String trackAngle = "";
        String sogS = "";
        int checksum = 0;
        String hexChecksum = "";
        DecimalFormat df = new DecimalFormat("00.0");
        trackAngle = df.format(bearing);
        sogS = df.format(sog);

        String RMC = "GPRMC," + time + ",A," + latS + "," + latDirection + "," + lonS + "," + lonDirection + "," + sogS + "," + trackAngle + "," + date + "," + magVar;
        //get checksum
        for (int i = 0; i < RMC.length(); i++) {
            checksum ^= RMC.charAt(i);
        }//end for

        hexChecksum = Integer.toHexString(checksum).toUpperCase();
        hexChecksum = ("00" + hexChecksum).substring(hexChecksum.length());

        return "$" + RMC + "*" + hexChecksum;
    }//end generate GPRMC    

}
