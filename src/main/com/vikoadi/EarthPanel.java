package com.vikoadi;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class EarthPanel extends JPanel {
    private BufferedImage earthImage;
    public EarthPanel(){
        super();
        
    }
    public void paintComponent(Graphics g){
        //g.drawImage(earthImage, 0, 0, null);
        repaint();
    }
}
