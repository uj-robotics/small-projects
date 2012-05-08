import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class RemoteMain {
	private static ISender sender;
	
	private static TouchSensor forward = new TouchSensor(SensorPort.S2);
	private static TouchSensor backward = new TouchSensor(SensorPort.S3);
	private static NXTRegulatedMotor wheel = Motor.B;
	
	private static int limes = 50;
	
	public static void main(String [] args)
	{
		sender = new BluetoothSender();
		boolean going = false;
		int oldTacho = 0;
		while (true)
		{
			try
			{
				if (forward.isPressed())
				{
					if (!going)
					{
						LCD.drawString("FORWARD           ", 0, 2);
						sender.sendInt(CommunicationCons.FORWARD);
						going = true;
					}
				}
				else if (backward.isPressed())
				{
					if (!going)
					{
						LCD.drawString("BACKWARD           ", 0, 2);
						sender.sendInt(CommunicationCons.BACKWARD);
						going = true;
					}
				}
				else
				{
					LCD.drawString("STOP           ", 0, 2);
					sender.sendInt(CommunicationCons.STOP);
					going = false;
				}
				int tacho = wheel.getTachoCount();
				if (Math.abs(tacho)<limes && Math.abs(oldTacho - tacho) > 1)
				{
					sender.sendInt(CommunicationCons.ROTATE);
					sender.sendInt((tacho) +100 );
					oldTacho = tacho;
				}
			}
			catch (Exception ex)
			{
				
			}
		}
	}
}
