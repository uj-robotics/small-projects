import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.util.Delay;


public class BluetoothSender implements ISender {

	NXTConnection connection;
	DataOutputStream dos;
	DataInputStream dis;
	public BluetoothSender()
	{
		LCD.drawString("Waiting for connection", 0, 2);
		connection = Bluetooth.waitForConnection();
		LCD.drawString("Connected             " , 0, 2);
		dos = connection.openDataOutputStream();
		dis = connection.openDataInputStream();
	}
	
	@Override
	public void sendInt(int a) throws IOException {
		dos.write(a);
		dos.flush();
	}

}
