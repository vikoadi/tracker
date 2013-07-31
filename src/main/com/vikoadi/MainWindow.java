package com.vikoadi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.text.MaskFormatter;

import uk.me.g4dpz.satellite.AbstractSatellite;
import uk.me.g4dpz.satellite.TLE;

public class MainWindow extends JFrame implements ActionListener{
    private JFormattedTextField timeField;
    private JTable satTable;
    private EarthCanvas satCanvas;
    private PropertiesPanel propertiesPanel;
    private ArrayList<AbstractSatellite> satelites;
    private TLEDownloader tleDownloader;
    private SateliteChooser sateliteChooser;
    private GroundStationChooser gsChooser;
    public MainWindow (){
        this.setTitle("Tracker");
        this.setMinimumSize(new Dimension(500, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        JToolBar toolbar = new JToolBar("toolbar");
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        //hpanel1.setLayout(new BoxLayout(hpanel1, BoxLayout.PAGE_AXIS));
        
        /*hpanel1.add(new JLabel("time"));        
        try{
            //create formatted text field for time display and input
            MaskFormatter mask = new MaskFormatter("##/##/## ##:##:##");
            timeField = new JFormattedTextField (mask);
            timeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, timeField.getPreferredSize().height));
            hpanel1.add(timeField);
        }catch(ParseException e){
            System.err.println(e.toString());
        }*/
        JButton editSatelites = new JButton("Edit satelites");
        editSatelites.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent arg0) {
                
            }
            @Override
            public void mouseReleased(MouseEvent arg0) {
                sateliteChooser = new SateliteChooser();
                sateliteChooser.setVisible(true);
                sateliteChooser.setAlwaysOnTop(true);
            }
            @Override
            public void mouseEntered(MouseEvent arg0) {
                
            }
            @Override
            public void mouseExited(MouseEvent arg0) {
                
            }
            @Override
            public void mousePressed(MouseEvent arg0) {
            }
            
        });
        toolbar.add(editSatelites);
        JButton editGS = new JButton("Edit ground station");
        editGS.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                gsChooser = new GroundStationChooser();
                gsChooser.setAlwaysOnTop(true);
                gsChooser.setVisible(true);
            }
            
        });
        toolbar.add(editGS);
        
        JButton updateTLE = new JButton("Update TLE");
        updateTLE.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent arg0) { }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {
                Main.tleDownloader.update();
            }
            
        });
        toolbar.add(updateTLE);
        
        JButton about = new JButton("About");
        toolbar.add(about);
        
        
        satTable = new JTable();
        satTable.setModel(Main.satellites);
        satTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        satTable.setFillsViewportHeight(true);
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(satTable);
        scrollPane.setMinimumSize(new Dimension(200,400));
        
        satCanvas = new EarthCanvas();
        satCanvas.setMinimumSize(new Dimension(200,200));
        
        propertiesPanel = new PropertiesPanel();
        propertiesPanel.setMinimumSize(new Dimension(200,100));
        propertiesPanel.setSize(200, 100);
        
        JSplitPane splitv = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, satCanvas);
        splitv.setDividerLocation(200);
        
        TLEDownloader tleDownloader = new TLEDownloader();
        //tleDownloader.update();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, satCanvas);
        
        this.add(toolbar, BorderLayout.PAGE_START);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(propertiesPanel, BorderLayout.PAGE_END);
        
        satTable.addMouseListener (new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    int row = satTable.rowAtPoint(e.getPoint());
                    System.out.println(row);
                    if(row >= 0 ){
                        Predicted predicted = ((TableModel)satTable.getModel()).getPredicted(row);
                        TLE tle = Main.tleDownloader.getTLE(predicted.satName); 
                        System.out.println(tle.getCatnum());
                        Date date = predicted.satPassTime.getStartTime();
                        propertiesPanel.setTLE(tle, predicted.satPassTime);
                        satCanvas.drawSatellite(tle, predicted.satPassTime);
                    }
                }else if(e.getButton()==MouseEvent.BUTTON3){
                    Point p = e.getPoint();
                    showPopupMenu(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        this.pack();
    }
    private void showPopupMenu(MouseEvent e){
        JPopupMenu popup = new JPopupMenu();
        JMenuItem add_sat = new JMenuItem("Add Satelite");
        add_sat.addActionListener(this);
        popup.add(add_sat);
        JMenuItem rem_sat = new JMenuItem("Remove Satelite");
        rem_sat.addActionListener(this);
        popup.add(rem_sat);
        popup.show(satTable, e.getX(),e.getY());
        
    }
    public void setSatelites() {
        satTable.revalidate();
    }
    public void setTleDownloader(TLEDownloader tleDownloader) {
        this.tleDownloader = tleDownloader;
    }
    public TableModel getTableModel(){
        return Main.satellites;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        if(source.getText()=="Add Satelite"){
        }else if(source.getText()=="Remove Satelite"){
        }
    }
}
