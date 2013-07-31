package com.vikoadi;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import uk.me.g4dpz.satellite.GroundStationPosition;

public class GroundStationChooser extends JFrame {
    private JTextField name;
    private JTextField latitude;
    private JTextField longitude;
    private JTextField height;
    public GroundStationChooser(){
        this.setMinimumSize(new Dimension(300,100));
        setLayout(new GridLayout(5,2));
        
        add(new JLabel("Name"));
        name = new JTextField();
        name.setText(Main.gspos.getName());
        add(name);
        
        NumberFormat doubleMask = NumberFormat.getNumberInstance();
        doubleMask.setGroupingUsed(true);
        doubleMask.setMaximumIntegerDigits(3);
        doubleMask.setMinimumFractionDigits(2);
        
        add(new JLabel("Latitude"));
        latitude = new JTextField();
        latitude.setText(String.valueOf(Main.gspos.getLatitude()));
        add(latitude);
        
        add(new JLabel("Longitude"));
        longitude = new JTextField();
        longitude.setText(String.valueOf(Main.gspos.getLongitude()));
        add(longitude);
        
        add(new JLabel("Height"));
        height = new JTextField();
        height.setText(String.valueOf(Main.gspos.getHeightAMSL()));
        add(height);
        
        add(new JLabel(""));
        JButton okButton = new JButton("OK");
        MouseListener buttonListener = new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                //double latitudeDouble = (double)latitude.getText();
                //Main.gspos = new GroundStationPosition((double)latitude.getText(), (double)longitude.getText(),0);
                System.out.println(latitude.getText());
            }
        };
        okButton.addMouseListener(buttonListener);
        add(okButton);
    }
}
