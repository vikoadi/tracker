package com.vikoadi;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import uk.me.g4dpz.satellite.Satellite;
import uk.me.g4dpz.satellite.SatelliteFactory;
import uk.me.g4dpz.satellite.TLE;

public class SateliteChooser extends JFrame{
    private JList fileChooser;
    private JList satChooser;
    private JList sat;
    private DefaultListModel satList; 
    public SateliteChooser (){
        this.setMinimumSize(new Dimension(500,300));
        this.setLayout(new GridLayout(1,4));
        Container con = this.getContentPane();
        
        fileChooser = new JList();
        fileChooser.setListData(Main.tleDownloader.getFiles().toArray());
        MouseListener fileChooserListener = new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                String path = (String)fileChooser.getSelectedValue();
                if(path!=null){
                    List<String> sats = Main.tleDownloader.getSats(path);
                    satChooser.setListData(sats.toArray());
                }
            }
        };
        fileChooser.addMouseListener(fileChooserListener);
        con.add(new JScrollPane(fileChooser));
        
        satChooser = new JList();
        MouseListener satChooserListener = new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    TLE tle = Main.tleDownloader.getTLE((String)satChooser.getSelectedValue());
                    Satellite satelite = SatelliteFactory.createSatellite(tle);
                    satList.addElement(satelite);
                }
            }
        };
        satChooser.addMouseListener(satChooserListener);
        con.add(new JScrollPane(satChooser));
        
        ArrayList<Satellite> list = Main.mainWindow.getTableModel().getSatellites();
        satList = new DefaultListModel();
        for(Satellite satellite : list){
            satList.addElement(satellite);
        }
        sat = new JList(satList);
        MouseListener satListener = new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){   
                    satList.remove(sat.getSelectedIndex());
                }
            }
        };
        sat.addMouseListener(satListener);
        con.add(new JScrollPane(sat));
        JButton okButton = new JButton("OK");
        MouseListener buttonListener = new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                ArrayList<Satellite> satelites= new ArrayList<Satellite>();
                for(int i=0;i<satList.getSize();i++){
                    satelites.add((Satellite)satList.get(i));
                }
                System.out.println(satelites.size());
                Main.satellites.setSatellites(satelites);
                Main.mainWindow.setSatelites();
                
            }
        };
        okButton.addMouseListener(buttonListener);
        con.add(okButton);
    }
}
