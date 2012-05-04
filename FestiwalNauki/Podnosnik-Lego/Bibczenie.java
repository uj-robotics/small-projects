
import lejos.nxt.ColorSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wojtek
 */
public class Bibczenie implements Runnable{
    private UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S4);
    private boolean aktywny = false;
    public void przelacz(){
        aktywny = !aktywny;
    }
   public void run(){
       int odstep = 1000, odleglosc;
       try{
       while(true){
           odleglosc = 255;
           if(aktywny) {
               odleglosc = sonar.getDistance();
               Sound.playTone(900-odleglosc*3, odstep);
           }
           Thread.sleep(odstep);
       }
       }catch(Exception e){}
   }
}
