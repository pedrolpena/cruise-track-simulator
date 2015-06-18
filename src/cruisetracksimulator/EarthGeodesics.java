/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruisetracksimulator;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author pena
 */
public class EarthGeodesics {
    double R = 6371;    
    
    public double getGreatCircleDistance(Position p0, Position p1) {
        double latStart = p0.getLatitudeInRadians();
        double lonStart = p0.getLongitudeInRadians();
        double latEnd = p1.getLatitudeInRadians();
        double lonEnd = p1.getLongitudeInRadians();
        double dLat = latEnd - latStart;
        double dLon = lonEnd - lonStart;
        double a = pow(sin(dLat / 2), 2) + cos(latStart) * cos(latEnd) * pow(sin(dLon / 2), 2);
        return R * 2 * atan2(sqrt(a), sqrt(1 - a));

    }//end getGreatCircleDistance

    public double getBearing(Position p0, Position p1) {
        double latStart = p0.getLatitudeInRadians();
        double lonStart = p0.getLongitudeInRadians();
        double latEnd = p1.getLatitudeInRadians();
        double lonEnd = p1.getLongitudeInRadians();
        double dLon = lonEnd - lonStart;
        return (180 / PI) * atan2(sin(dLon) * cos(latEnd), cos(latStart) * sin(latEnd) - sin(latStart) * cos(latEnd) * cos(dLon));
    }//end getBearing

    public Position getNextPosition(Position p, double distance, double bearing) {
        double lat = p.getLatitudeInRadians();
        double lon = p.getLongitudeInRadians();
        bearing = bearing * PI / 180;
        Position p0 = new Position();
        p0.setLatitude((180 / PI) * asin(sin(lat) * cos(distance / R) + cos(lat) * sin(distance / R) * cos(bearing)));
        p0.setLongitude((180 / PI) * (lon + atan2(sin(bearing) * sin(distance / R) * cos(lat), cos(distance / R) - sin(lat) * sin(p0.getLatitudeInRadians()))));
        return p0;

    }//end getNextPosition    
    
}
