package festiwal;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;
import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class Sterowanie 
{
    private String name = "AA";
    private int mode;
    private NXTInfo robot;
    private NXTComm nxtComm;
    private DataInputStream dis;
    private DataOutputStream dos;
    private NXTConnector connection;
    public static int BLUETOOTH_MODE = 2;
    public static int USB_MODE = 1;
    private int lewy, prawy, podnoszenie;
    private Joystick jstck;
    private double maksymalnaPredkosc;
  
    
    public void wyslijZmiany()   {
        try{
            dos.writeInt(lewy);
            dos.writeInt(prawy);
            dos.writeInt(podnoszenie);
            dos.flush();        
        } catch(Exception e){
            System.out.println("Problem z wysylaniem zmian!");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        Sterowanie sterowanie = new Sterowanie(true);
    }
    
    public Sterowanie(boolean debug) 
    {
            if(debug) mode = USB_MODE; else mode= BLUETOOTH_MODE; 
            try
            {
                connect();
                System.out.println("Polaczylem się z " + name);
                dis = connection.getDataIn();
                dos = connection.getDataOut();
                System.out.println("Otworzylem strumienie do " + name);
                System.out.println("Czekam na powitanie");
                int powitanie = dis.readInt();
                if(powitanie!=1989) throw new Exception();
                System.out.println("Powitano");
            }
            catch (Exception ne)
            {
                    System.out.println("Jakis problem z kostka");
                    ne.printStackTrace();
            }
            System.out.println("Łączenie z Joystickiem!");
            try{
                jstck = Joystick.createInstance();
                jstck.addJoystickListener(new JoystickFestiwal(this));
            } catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Jakiś problem z Joystickiem!");
            }
            System.out.println("Połączono z Joystickiem!");
    }
    

    private void connect() throws NXTCommException
    {
          nxtComm = NXTCommFactory.createNXTComm(mode);
            NXTInfo[] nxtInfo = nxtComm.search(name, mode);
            if (nxtInfo == null)  {
                    robot = null;
                    System.out.println("Mam nulla");
            }
            else  {
                    if (nxtInfo.length == 0) {
                            robot = null;
                            System.out.println("Mam pustą tablicę");
                    }
                    else {
                            robot = nxtInfo[0];
                            System.out.println("Mam robota: "+robot.name);
                    }
            } 
            connection = new NXTConnector();
            connection.setDebug(true);
            connection.connectTo(name,0);
    }


    @Override
    public void finalize(){
        try{
            dis.close();
            dos.close();
            
        } catch (IOException ex) {    }
    }

    void aktualizujMaksymalna(double r) {
        this.maksymalnaPredkosc = (int)(300.0*(1.0+r));
    }

    void aktualizujPredkosci(double prosto, double obrot) {
        double diff;
         diff = (prosto==0.0 ? this.maksymalnaPredkosc/2.0 : this.maksymalnaPredkosc/3.5);
        this.lewy = (int)(prosto*this.maksymalnaPredkosc  + diff*obrot);
        this.prawy = (int)(prosto*this.maksymalnaPredkosc - diff*obrot);
        System.out.println("L="+this.lewy+" R="+this.prawy);
        this.wyslijZmiany();
    }
    final int MARKER = -3000;
    void koniec(){
        lewy = MARKER; // markery końca
        prawy = MARKER;
        this.wyslijZmiany();
        System.out.println("Koniec");
        System.exit(0);       
        
    }

    void aktualizujPodnoszenia(double v) {
        int speed = 250;
        podnoszenie = (int)(speed*v);
    }

    void przelaczBibczenie() {
        lewy = 0; 
        prawy = MARKER;
        this.wyslijZmiany();
    }

    void przelaczSwiatelko() {
         lewy = MARKER; 
        prawy = 0;
        this.wyslijZmiany();
    }

}


