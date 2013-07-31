package com.vikoadi;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import uk.me.g4dpz.satellite.*;

public class TableModel extends AbstractTableModel {
    private ArrayList<Satellite> list;
    private ArrayList<Predicted> predictor;
    private String[] columnNames = {
            "satelite",
            "Next pass"
            };
    public TableModel(){
        list = new ArrayList<Satellite>();
        predictor = new ArrayList<Predicted>();
        refresh();
        TLE tle = Main.tleDownloader.getTLE("LAPAN-TUBSAT");
        if(tle != null)
            addSatellite(SatelliteFactory.createSatellite(tle));
    }
    
    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        //return list.size();
        return predictor.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 0)
            return predictor.get(row).satName;
        else
            return predictor.get(row).satPassTime.getStartTime();
    }
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void addSatellite(Satellite satellite){
        list.add(satellite);
        refresh();
    }
    public void remSatellite(AbstractSatellite satelite){
        list.remove(satelite);
        refresh();
    }
    public void setSatellites(ArrayList<Satellite> satelites) {
        list = satelites;
        refresh();
    }
    public ArrayList<Satellite> getSatellites(){
        return list;
    }
    public Predicted getPredicted(int index){
        return predictor.get(index);
    }
    public void refresh(){
        predictor.clear();
        for(Satellite satelite : list){
            try {
                PassPredictor passPredictor = new PassPredictor(satelite.getTLE(), Main.gspos);
                for(SatPassTime satPassTime:passPredictor.getPasses(new Date(), 24, false)){
                    predictor.add(new Predicted(satelite.getTLE().getName(), satPassTime));
                }
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (InvalidTleException e) {
                e.printStackTrace();
            }
            catch (SatNotFoundException e) {
                e.printStackTrace();
            } 
        }
    }
}
