package com.vikoadi;

import java.awt.GridLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.me.g4dpz.satellite.InvalidTleException;
import uk.me.g4dpz.satellite.PassPredictor;
import uk.me.g4dpz.satellite.SatNotFoundException;
import uk.me.g4dpz.satellite.SatPassTime;
import uk.me.g4dpz.satellite.SatPos;
import uk.me.g4dpz.satellite.TLE;

public class PropertiesPanel extends JPanel {
    public PropertiesPanel(){
        setLayout(new GridLayout(6,6,20,5));
    }
    public void setTLE(TLE tle){
        Date date = new Date();
        //setTLE(tle, date);
    }
    public void setTLE(TLE tle, SatPassTime satPassTime){
        removeAll();
        add(new JLabel("Epoch : "+tle.getEpoch()));
        add(new JLabel("Inclination : "+tle.getIncl()+"°"));
        add(new JLabel("RAAN : "+tle.getRaan()+"°"));
        add(new JLabel("Argument of Perigee : "+tle.getArgper()+"°"));
        add(new JLabel("Eccentricity : "+tle.getEccn()));
        add(new JLabel("Mean Motion : "+tle.getMeanmo()));
        add(new JLabel("Mean Anomaly : "+tle.getMeanan()));
        
        try {
            PassPredictor passPredictor= new PassPredictor(tle, Main.gspos);
            //System.out.println(date.toString());
            add(new JLabel("downlink freq at 437,325Mhz : "+passPredictor.getDownlinkFreq((long)437325000, satPassTime.getStartTime()).toString()+"Hz"));
            add(new JLabel("uplink freq at 437,325Mhz : "+passPredictor.getUplinkFreq((long)437325000, satPassTime.getStartTime()).toString()+"Hz"));
            List<SatPos> posList = passPredictor.getPositions(satPassTime.getStartTime(), 30, 0, 10);
            add(new JLabel("Altitude : "+ String.valueOf(posList.get(0).getAltitude())));
            add(new JLabel("Latitude : "+ String.valueOf(radToDeg(posList.get(0).getLatitude())+"°")));
            add(new JLabel("Longitude : "+ String.valueOf(radToDeg(posList.get(0).getLongitude())+"°")));
            
            add(new JLabel("Azimuth : "+ String.valueOf(radToDeg(posList.get(0).getAzimuth())+"°")));
            add(new JLabel("Elevation : "+ String.valueOf(radToDeg(posList.get(0).getElevation())+"°")));
            add(new JLabel("Max Elevation : "+String.valueOf(satPassTime.getMaxEl()+"°")));
            
            
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvalidTleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SatNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.revalidate();
    }
    private double radToDeg(double rad){
        return rad / (Math.PI * 2.0) * 360;
    }
}
