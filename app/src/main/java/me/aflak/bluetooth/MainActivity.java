package me.aflak.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends Activity implements Bluetooth.CommunicationCallback, Bluetooth.DiscoveryCallback{
    private Bluetooth bt;
    public final static String TAG = "Bluetooth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = new Bluetooth(this);
        bt.enableBluetooth();

        /*  GET PAIRED DEVICES  */
        List<BluetoothDevice> devices = bt.getPairedDevices();

        /*  LISTENERS  */
        bt.setDiscoveryCallback(this);
        bt.setCommunicationCallback(this);

        /*  SCAN  */
        bt.scanDevices();
    }


    /* ************************ */
    /* DISCOVERY CALLBACK BEGIN */
    /* ************************ */

    @Override
    public void onFinish() {
        /*  The scan finished  */
        Log.d(TAG, "Scan finished");
    }

    @Override
    public void onDevice(BluetoothDevice device) {
        /*  DEVICE FOUND  */
        Log.d(TAG, "Found: "+device.getAddress()+" - "+device.getName());

        /*  PAIRING  */
        bt.pair(device);    // bt.unpair(device) exist also
    }

    @Override
    public void onPair(BluetoothDevice device) {
        /*  DEVICE PAIRED  */

        /*  three methods are available for connecting  */
        bt.connectToName("name");
        bt.connectToAddress("address");
        bt.connectToDevice(device);
    }

    @Override
    public void onUnpair(BluetoothDevice device) {
        /*  DEVICE UNPAIRED  */
    }

    /* ********************** */
    /* DISCOVERY CALLBACK END */
    /* ********************** */



    /* **************************** */
    /* COMMUNICATION CALLBACK BEGIN */
    /* **************************** */

    @Override
    public void onConnect(BluetoothDevice device) {
        /*  DEVICE CONNECTED  */
        Log.d(TAG, "Connected!");
        Log.d(TAG, "Device = " + device.getName() + "   Address = " + device.getAddress());

        /*  SEND MESSAGE  */
        bt.send("hello");
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        /*  The connection with the device has been closed  */
        Log.d(TAG, "Disconnected!");
    }

    @Override
    public void onMessage(String message) {
        /*  The device sent you a message  */
        Log.d(TAG, message);
    }

    @Override
    public void onConnectError(BluetoothDevice device, String message) {
        /*  ERROR DURING CONNECTION  */
        Log.d(TAG, message);

        /*  CONNECT AGAIN (You should wait like 5 sec)  */
        bt.connectToDevice(device);
    }

    /* ************************** */
    /* COMMUNICATION CALLBACK END */
    /* ************************** */


    // COMMON CALLBACK

    @Override
    public void onError(String message) {
        /*  ERROR OCCURRED  */
        Log.e(TAG, message);
    }
}