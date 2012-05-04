import java.io.DataInputStream;
import java.io.DataOutputStream;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;


public class ZgloszeniaBluetooth implements Runnable
{
    private DataOutputStream dos;
    private DataInputStream dis;
    private PodnLego sterowanie;

    public ZgloszeniaBluetooth(boolean debug, PodnLego sterowanie) throws Exception 
    {
            this.sterowanie = sterowanie;
            String connected = "Polaczano";
            String waiting = "Czekanie...";
            LCD.drawString(waiting,0,0);
            NXTConnection connection =null; 
            if(debug)
                connection = USB.waitForConnection(); 
            else
                connection = Bluetooth.waitForConnection();
            LCD.drawString(connected,0,0);
            dos = connection.openDataOutputStream();
            dos.writeInt(1989);// powitanie 
            dos.flush();
            dis = connection.openDataInputStream();
    }
    public void run() {
        int lewy, prawy, podnoszenie;
        int MARKER = -3000;
        try{
            while(true){
                lewy = dis.readInt();
                prawy = dis.readInt(); 
                podnoszenie = dis.readInt();
                if(lewy==prawy && lewy == MARKER)         System.exit(0);                    
                if(lewy==MARKER && prawy==0){
                    sterowanie.przelaczSwiatelko();
                    continue;
                }
                if(lewy==0 && prawy==MARKER){
                    sterowanie.przelaczBibczenie();
                    continue;
                }
                sterowanie.aktualizuj(lewy, prawy, podnoszenie);
            }
        } catch(Exception e) { 
            System.out.println(e.getMessage()); 
        }
        
    }
    

    

    @Override
    public void finalize()
    {
        try{  dos.close(); dis.close();
        }
        catch (Exception e) {     }
    }

    

    

    
}
