
import lejos.nxt.ColorSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wojtek
 */
public class Swiatelko implements Runnable{
    private ColorSensor laser = new ColorSensor(SensorPort.S3, ColorSensor.TYPE_COLORFULL);
    private boolean aktywny = false;
    public void przelacz(){
        aktywny = !aktywny;
        if(!aktywny)
            laser.setFloodlight(false);
    }
   public void run(){
       int odstep = 1000;
       try{
       while(true){
           if(aktywny) laser.setFloodlight(Color.RED);
           Thread.sleep(odstep);
           if(aktywny) laser.setFloodlight(Color.GREEN);
           Thread.sleep(odstep);
           if(aktywny) laser.setFloodlight(Color.BLUE);
           Thread.sleep(odstep);
       }
       }catch(Exception e){}
   }
}
