package com.vikoadi;

import uk.me.g4dpz.satellite.SatPassTime;

public class Predicted {
    public String satName;
    public SatPassTime satPassTime;
    public Predicted(String satName, SatPassTime satPassTime){
        this.satName = satName;
        this.satPassTime = satPassTime;
    }
}
