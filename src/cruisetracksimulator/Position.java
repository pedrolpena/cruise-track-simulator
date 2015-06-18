/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruisetracksimulator;

import static java.lang.Math.*;
import java.text.DecimalFormat;

/**
 *
 * @author pena
 */
public class Position {
        double latitude = 0.0;
        double longitude = 0.0;

        public Position(){}
        public Position(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }//end getLatitude

        public double getLongitude() {
            return longitude;
        }//end getLatitude
        
    public String getLatitudeDegrees() {
        DecimalFormat df = new DecimalFormat("00");

        return df.format((int) abs(latitude));
    }

    public String getLatitudeMinutes() {
        DecimalFormat df = new DecimalFormat("00.000");

        return df.format( abs(60*(latitude-(int)latitude)));

    }
    
    
    public String getLongitudeDegrees() {
        DecimalFormat df = new DecimalFormat("000");

        return df.format((int) abs(longitude));
    }

    public String getLongitudeMiuntes() {
        DecimalFormat df = new DecimalFormat("00.000");

        return df.format( abs(60*(longitude-(int)longitude)));
        

    }    
    
    
    
  

        
        public double getLatitudeInRadians() {
            return toRadians(latitude);
        }//end getLatitude

        public double getLongitudeInRadians() {
            return toRadians(longitude);
        }//end getLatitude        

        public void setLatitude(double l) {
            latitude = l;

        }//end getLatitude

        public void setLongitude(double l) {
            longitude = l;

        }//end getLatitude 
        public void setPosition(Position p){
            latitude=p.getLatitude();
            longitude=p.getLongitude();
        }//end setPosition
        String getLatitudeDirection()
        {
            String direction = "N";
            if(latitude < 0){
                direction = "S";
            }
            return direction;
        }
        
        String getLongitudeDirection()
        {
            String direction = "E";
            if(longitude < 0){
                direction = "W";
            }
            return direction;
        }        
        public String toString(){
            String x;
            x=getLatitudeDegrees()+" "+getLatitudeMinutes()+" "+getLatitudeDirection()+" "+
              getLongitudeDegrees()+" "+getLongitudeMiuntes()+" "+getLongitudeDirection() ;
            return x;
        }//end toString
}
