package com.vikoadi;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Date;
import java.util.List;

import uk.me.g4dpz.satellite.InvalidTleException;
import uk.me.g4dpz.satellite.PassPredictor;
import uk.me.g4dpz.satellite.SatNotFoundException;
import uk.me.g4dpz.satellite.SatPassTime;
import uk.me.g4dpz.satellite.SatPos;
import uk.me.g4dpz.satellite.TLE;

public class EarthCanvas extends Canvas {
    private final double aspectRatio = 2.0d;
    private final double PHI = 3.14;
    private TLE satTle;
    private SatPassTime satPassTime;
    
    @Override
    public void paint(Graphics graphics){
        URL path =  getClass().getClassLoader().getResource("earth-map-med.jpg");
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        int height = getHeight();
        int width = getWidth();
        if (width/height < aspectRatio){
            height = (int)width/2; 
        }else if(width/height > aspectRatio){
            width = height*2;
        }
        //draw map 
        graphics.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        //draw Ground Station
        drawObject(graphics, Main.gspos.getName(), toX(Main.gspos.getLongitude()), toY(Main.gspos.getLatitude()), Color.YELLOW);
        //draw selected satelite
        PassPredictor passPredictor;
        try {
            if (satTle!=null){
                passPredictor = new PassPredictor(satTle, Main.gspos);
                List<SatPos> satPos = passPredictor.getPositions(satPassTime.getStartTime(), 60, 0, 3*60);
                drawObject(graphics, satTle.getName(),toX(radToDeg(satPos.get(0).getLongitude())), toY(radToDeg(satPos.get(0).getLatitude())),Color.GREEN);
                drawRange(graphics, satPos.get(0));
                //drawPath(graphics,satPos);
            }
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
    }
    public void drawSatellite(TLE tle, SatPassTime satPassTime){
        this.satTle = tle;
        this.satPassTime = satPassTime;
        this.repaint();
    }
    private int toX(double longitude){
        return (int)(((longitude+180)%360/360*getWidth()));
    }
    private int toY(double latitude){
        int zeroLat = getHeight() / 2;
        return getHeight()-(int)(((latitude)%180 / 180 *getHeight())+zeroLat);
    }
    private void drawObject(Graphics g, String label, int x, int y, Color color){
        g.setColor(color);
        g.fillRect(x-1, y-1, 2, 2);
        g.drawString(label, x+5, y+5);
    }
    private void drawRange(Graphics g, SatPos satPos){
        Polygon p = new Polygon();
        double[][] range = satPos.getRangeCircle();
        for (int i = 0; i<360 ;i++){
            p.addPoint(toX(range[i][1]), toY(range[i][0]));
            /*if(toX(range[i][1])<5||toX(range[i][1])>getWidth()-5){
                g.drawPolygon(p);
                p.reset();
            }*/
        }
        g.drawPolygon(p);
    }
    private void drawPath(Graphics g, List<SatPos> satPos) {
        int xPoints[]= new int[satPos.size()];
        int yPoints[]= new int[satPos.size()];
        for(int i=0;i<satPos.size();i++) {
            xPoints[i]=toX(radToDeg(satPos.get(i).getLongitude()));
            yPoints[i]=toY(radToDeg(satPos.get(i).getLatitude()));
        }
        g.drawPolyline(xPoints, yPoints, satPos.size());
    }
    private double radToDeg(double rad){
        return rad / (Math.PI * 2.0) * 360;
    }
}
