package festiwal;


import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

/**
 *
 * @author wojtek
 */
public class JoystickFestiwal implements JoystickListener  {
    private Sterowanie sterowanie;
    private boolean isWindows;
    public JoystickFestiwal(Sterowanie sterowanie) {
         this.sterowanie = sterowanie;
         String os = System.getProperty("os.name").toLowerCase();
         isWindows =  (os.indexOf("win") >= 0);
    }
    
    @Override
    public void joystickAxisChanged(Joystick jstck) {
        if(isWindows){
            sterowanie.aktualizujMaksymalna(jstck.getZ());
            double podnoszenie=0.0;
            if(jstck.getPOV()==0.0) podnoszenie = -1.0;
            if(jstck.getPOV()==180.0) podnoszenie = 1.0;
            sterowanie.aktualizujPodnoszenia(-podnoszenie);
            sterowanie.aktualizujPredkosci(-jstck.getY(), jstck.getR());            
        } else {
            sterowanie.aktualizujMaksymalna(jstck.getR());
            sterowanie.aktualizujPodnoszenia(-jstck.getV());
            sterowanie.aktualizujPredkosci(-jstck.getY(), jstck.getZ());
        }
        sterowanie.wyslijZmiany();
    }

    @Override
     public void joystickButtonChanged(Joystick jstck) {
        if(jstck.isButtonDown(Joystick.BUTTON4))
            sterowanie.przelaczBibczenie();
        if(jstck.isButtonDown(Joystick.BUTTON5))
            sterowanie.przelaczSwiatelko();
        if(jstck.isButtonDown(Joystick.BUTTON10))
            sterowanie.koniec();
    }

}
