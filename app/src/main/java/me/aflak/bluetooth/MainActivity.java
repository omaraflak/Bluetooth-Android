package me.aflak.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements Bluetooth.CommunicationCallback, Bluetooth.DiscoveryCallback{
    private Bluetooth bt;
    public final static String TAG = "Bluetooth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt = new Bluetooth(this);
        bt.enableBluetooth();

        // TextView txt = (TextView)findViewById(R.id.text);
        // for (BluetoothDevice device : bt.getPairedDevices())
        //         txt.append(device.getAddress()+" : "+device.getName()+"\n");

        /*
            Listeners
         */
        bt.setCommunicationCallback(this);
        bt.setDiscoveryCallback(this);
        bt.scanDevices();
    }

    /* **************************** */
    /* COMMUNICATION CALLBACK BEGIN */
    /* **************************** */

    @Override
    public void onConnect(BluetoothDevice device) {
         /*
            Device is connected
            Send a message to the device
         */
        Log.e(TAG, "Connected!");
        Log.e(TAG, "Device = " + device.getName() + "   Address = " + device.getAddress());
        bt.send("hello");
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        // The connection with the device has been closed
        Log.e(TAG, "Disconnected!");
    }

    @Override
    public void onMessage(String message) {
        // The device sent you a message
        Log.e(TAG, message);
    }

    @Override
    public void onConnectError(BluetoothDevice device, String message) {
        // An error occurred during connection
        // try to connect again
        Log.e(TAG, message);
        bt.connectToDevice(device);
    }

    /* ************************** */
    /* COMMUNICATION CALLBACK END */
    /* ************************** */


    /* ************************ */
    /* DISCOVERY CALLBACK BEGIN */
    /* ************************ */

    @Override
    public void onFinish() {
        // The scan finished
    }

    @Override
    public void onDevice(BluetoothDevice device) {
        // Found a device
        Log.d(TAG, "Found: "+device.getAddress()+" - "+device.getName());

        // pair to it
        bt.pair(device);    // bt.unpair(device) exist also
    }

    @Override
    public void onPair(BluetoothDevice device) {
        // device is paired
        // lets connect to it :

        /* three methods are available for connecting */
        bt.connectToName("name");
        bt.connectToAddress("address");
        bt.connectToDevice(device);
    }

    @Override
    public void onUnpair(BluetoothDevice device) {
        // device is unpaired
    }

    /* ********************** */
    /* DISCOVERY CALLBACK END */
    /* ********************** */


    // COMMON CALLBACK

    @Override
    public void onError(String message) {
        // An error occurred
        Log.e(TAG, message);
    }
}