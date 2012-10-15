import lejos.nxt.*;
import lejos.nxt.addon.NXTLineLeader;


public class LineLeaderExample {

	public static void wait(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
     }
	
	public static void main(String[] args) throws InterruptedException {
		NXTLineLeader liner = new NXTLineLeader(SensorPort.S3);
		liner.wakeUp();
		LCD.drawString("Light settings: ", 0, 0);
        LCD.drawString("Place robot over", 0, 1);
        LCD.drawString("white area ", 0, 2);
        LCD.drawString("Press orange btn", 0, 3);
        Button.ENTER.waitForPressAndRelease();
        LCD.drawString("**** wait ******", 0, 3);
        liner.calibrate(NXTLineLeader.LineColor.WHITE);
        wait(150);
        LCD.clear();
        LCD.drawString("Place robot over", 0, 4);
        LCD.drawString("black area ", 0, 5);
        LCD.drawString("Press orange btn", 0, 6);
        Button.ENTER.waitForPressAndRelease();
        LCD.drawString("**** wait ******", 0, 6);
        liner.calibrate(NXTLineLeader.LineColor.BLACK);
        LCD.clear();
        LCD.drawString("SetPoint=" + liner.getSetPoint(), 0, 0);
        LCD.drawString("Kp=" + liner.getKP() + " " + "KpDiv=" + liner.getKPDivisor(), 0, 1);
        LCD.drawString("Ki=" + liner.getKI() + " " + "KiDiv=" + liner.getKIDivisor(), 0, 2);
        LCD.drawString("Kd=" + liner.getKD() + " " + "KdDiv=" + liner.getKDDivisor(), 0, 3);
        LCD.drawString("Steering = ", 0, 5);
        LCD.drawString("Result   = ", 0, 6);
        LCD.drawString("Average  = ", 0, 7);
        
        Button.ENTER.waitForPressAndRelease();
        
        while (!Button.ESCAPE.isPressed()) {
           LCD.drawString("      ", 11, 5);
           LCD.drawString("      ", 11, 6);
           LCD.drawString("      ", 11, 7);
           LCD.drawInt(liner.getSteering(), 11, 5);
           LCD.drawInt(liner.getResult(), 11, 6);
           LCD.drawInt(liner.getAverage(), 11, 7);
           wait(100);
        }
	}

}
