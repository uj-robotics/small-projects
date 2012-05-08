import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;


public class BluetoothReceiver implements IReceiver {

	NXTConnection connection;
	DataOutputStream dos;
	DataInputStream dis;
		
	public BluetoothReceiver(String name)
	{
		LCD.drawString("Connecting", 0, 2);
		RemoteDevice btrd = Bluetooth.getKnownDevice(name);
		connection = Bluetooth.connect(btrd);
		LCD.drawString("Connected", 0, 3);
		dos = connection.openDataOutputStream();
		dis = connection.openDataInputStream();
	}
	
	@Override
	public int receiveInt() throws IOException {
		return dis.read();
	}
}
