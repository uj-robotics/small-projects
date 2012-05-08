import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class WehicleMain {
	public static int SPEED = 800;
	public static String name = "Rabus";
	
	static NXTRegulatedMotor left = Motor.B;
	static NXTRegulatedMotor right = Motor.C;
	static NXTRegulatedMotor wheel = Motor.A;
	static IReceiver receiver;
	
	public static int angle;
	
	private static void goForward()
	{
		LCD.drawString("FORWARD           ", 0, 2);
		left.setSpeed(SPEED);
		right.setSpeed(SPEED);
		left.forward();
		right.forward();
	}
	
	private static void goBackward()
	{
		LCD.drawString("BACKWARD           ", 0, 2);
		left.setSpeed(SPEED);
		right.setSpeed(SPEED);
		left.backward();
		right.backward();
	}
	
	private static void stop()
	{
		LCD.drawString("STOP           ", 0, 2);
		left.setSpeed(0);
		right.setSpeed(0);
		left.stop();
		right.stop();
	}
	
	private static void rotateAxis() throws IOException
	{
		angle = receiver.receiveInt() - 100;
		LCD.drawString("ROTATE "+angle+"          ", 0, 4);
		wheel.setSpeed(500);
		wheel.rotateTo(WehicleMain.angle, true);
	}
	
	
	public static void main(String [] args)
	{
		receiver = new BluetoothReceiver(name);
		while (true)
		{
			try {
				int R = receiver.receiveInt();
				switch (R)
				{
				case CommunicationCons.FORWARD 		: goForward(); break;
				case CommunicationCons.BACKWARD 	: goBackward(); break;
				case CommunicationCons.ROTATE 		: rotateAxis(); break;
				case CommunicationCons.STOP 		: stop(); break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
