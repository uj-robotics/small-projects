import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTLineLeader;
import lejos.util.Delay;


public class LineFollower {

	public static int TARGET_SPEED = 650;
	
	
	public static NXTRegulatedMotor leftMotor = Motor.A;
	public static NXTRegulatedMotor rightMotor = Motor.C;
	private static NXTLineLeader leader;
	
	
	private static void calibrate() {
		leader = new NXTLineLeader(SensorPort.S3);
		leader.wakeUp();
		LCD.drawString("Light settings: ", 0, 0);
        LCD.drawString("Place robot over", 0, 1);
        LCD.drawString("white area ", 0, 2);
        LCD.drawString("Press orange btn", 0, 3);
        Button.ENTER.waitForPressAndRelease();
        LCD.drawString("**** wait ******", 0, 3);
        leader.calibrate(NXTLineLeader.LineColor.WHITE);
        Delay.msDelay(150);
        LCD.clear();
        LCD.drawString("Place robot over", 0, 4);
        LCD.drawString("black area ", 0, 5);
        LCD.drawString("Press orange btn", 0, 6);
        Button.ENTER.waitForPressAndRelease();
        LCD.drawString("**** wait ******", 0, 6);
        leader.calibrate(NXTLineLeader.LineColor.BLACK);
        Delay.msDelay(150);
        LCD.clear();
        LCD.drawString("SetPoint=" + leader.getSetPoint(), 0, 0);
        LCD.drawString("Kp=" + leader.getKP() + " " + "KpDiv=" + leader.getKPDivisor(), 0, 1);
        LCD.drawString("Ki=" + leader.getKI() + " " + "KiDiv=" + leader.getKIDivisor(), 0, 2);
        LCD.drawString("Kd=" + leader.getKD() + " " + "KdDiv=" + leader.getKDDivisor(), 0, 3);
        
        Button.ENTER.waitForPressAndRelease();
	}
	
	private static void initMotors() {
		leftMotor.setSpeed(TARGET_SPEED); leftMotor.forward();
		rightMotor.setSpeed(TARGET_SPEED); rightMotor.forward();
	}
	
	public static void P() {
		int turn;
		while (true) {
			turn = leader.getKP() * leader.getSteering();
			leftMotor.setSpeed(TARGET_SPEED+turn);
			rightMotor.setSpeed(TARGET_SPEED-turn);
		}
	}
	
	public static void PI() {
		int turn;
		int integral = 0;
		int Kp = leader.getKP() * leader.getKPDivisor();
		int Ki = leader.getKI() * leader.getKIDivisor();
		while (true) {
			integral += leader.getSteering();
			turn = Kp * leader.getSteering() + Ki * integral;
			turn /= leader.getKPDivisor();
			leftMotor.setSpeed(TARGET_SPEED+turn);
			rightMotor.setSpeed(TARGET_SPEED-turn);
		}
	}
	
	public static void PID() {
		int turn;
		int error = 0;
		int integral = 0;
		int lastError = 0;
		int Kp = leader.getKP() * leader.getKPDivisor();
		int Ki = leader.getKI() * leader.getKIDivisor();
		int Kd = leader.getKD() * leader.getKDDivisor();
		while (true) {
			error = leader.getSteering();
			integral += error;
			int derivative = error - lastError;
			turn = Kp * leader.getSteering() + Ki * integral + Kd * derivative;
			turn /= leader.getKPDivisor();
			leftMotor.setSpeed(TARGET_SPEED+turn);
			rightMotor.setSpeed(TARGET_SPEED-turn);
			lastError = error;
		}
	}
	
	public static void main(String[] args) {
		calibrate();
		initMotors();		
		//P();
		//PI();
		PID();
		
	}

}
