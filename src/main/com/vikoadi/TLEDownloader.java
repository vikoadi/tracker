package com.vikoadi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.me.g4dpz.satellite.TLE;



public class TLEDownloader {
    private final String server = "http://www.celestrak.com/NORAD/elements/";
    private final String[] filesname = {
                "amateur.txt", "cubesat.txt","dmc.txt","education.txt","engineering.txt","galileo.txt","geo.txt",
                "geodetic.txt","globalstar.txt","glo-ops.txt","goes.txt","gorizont.txt","gps-ops.txt","intelsat.txt",
                "iridium.txt","military.txt","molniya.txt","musson.txt","nnss.txt","noaa.txt","orbcomm.txt",
                "other.txt","other-comm.txt","radar.txt","raduga.txt","resource.txt","sarsat.txt","sbas.txt",
                "science.txt","tdrss.txt","tle-new.txt","visual.txt","weather.txt","x-comm.txt"
    };
    private List<String> files;
    public TLEDownloader(){
        files = new ArrayList<String>();
        for(int i=0;i<filesname.length;i++)
            files.add(getDataDirectory()+filesname[i]);
    }
    public void update(){
        //create dir
        File dir = new File(getDataDirectory());
        if(!dir.exists()){
            try{
                dir.mkdirs();
            }catch(Exception e){
                System.err.println(e);
            }
        }
        //download  all files
        for(int i=0;i<filesname.length;i++){
            String filename = filesname[i];
            try{
                saveUrl(getDataDirectory()+filename, server+filename);
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }
    public void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
    {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try
        {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1)
            {
                fout.write(data, 0, count);
            }
        }
        finally
        {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
    }
    public Date getLastUpdate(){
        return null;
    }
    public List<String> getFiles(){
        return files;
    }
    public List<String> getSats(String path){
        ArrayList<String> sats = new ArrayList<String>();
        ArrayList<TLE> tleList;
        try {
            System.out.println(path);
            File fl = new File(path);
            FileInputStream fileIS = new FileInputStream(fl);
            tleList = (ArrayList<TLE>)TLE.importSat(fileIS);
            for(TLE tle : tleList){
                sats.add(tle.getName());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } 
        
        return sats;
    }
    public FileInputStream getInputStream(String file){
        if (files.contains(file)){
            File fl = new File(getDataDirectory()+file);
            if (fl.exists()){
                try{
                    FileInputStream inputStream = new FileInputStream(fl);
                    return inputStream;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private String getDataDirectory(){
        String OS = (System.getProperty("os.name")).toUpperCase();
        if(OS.contains("WIN")){
            return System.getenv("AppData")+"/tracker/";
        }else if (OS.contains("LIN")){
            String home = System.getProperty("user.home");
            return home+"/.local/share/tracker/";
        }else{//assume on mac
            String home = System.getProperty("user.home");
            return home+"/Library/Application Support/tracker";
        }
    }
    public TLE getTLE(String string) {
        for(String tle : files){
            try {
                File fl = new File(tle);
                FileInputStream fileIS = new FileInputStream(fl);
                List<TLE> list = TLE.importSat(fileIS);
                for(TLE tlein : list){
                    if (tlein.getName().contains(string)){
                        return tlein;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*    
        try{
            File fl = new File("/home/vikoadi/.local/share/tracker/galileo.txt");
            FileInputStream is = new FileInputStream(fl);
            List<TLE> list = TLE.importSat(is);
            return list.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }*/
        return null;
    }
}
