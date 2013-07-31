package com.vikoadi;

import uk.me.g4dpz.satellite.GroundStationPosition;

public class Main {

    /**
     * @param args
     */
    //public static ArrayList<AbstractSatellite> satelites;
    public static TableModel satellites;
    public static TLEDownloader tleDownloader;
    public static MainWindow mainWindow;
    public static GroundStationPosition gspos;
    public static void main(String[] args) {
        gspos = new GroundStationPosition(-6.5, 106.75, 0, "Rancabungur");
        tleDownloader = new TLEDownloader();
        satellites = new TableModel();
        if (mainWindow==null){
            mainWindow = new MainWindow ();
            //mainWindow.setSatelites(satelites);
            mainWindow.setTleDownloader(tleDownloader);
            mainWindow.setVisible(true);
        }
        /*SateliteChooser satChooser = new SateliteChooser();
        satChooser.setVisible(true);
        GroundStationChooser gschooser= new GroundStationChooser();
        gschooser.setVisible(true);*/
    }

}
