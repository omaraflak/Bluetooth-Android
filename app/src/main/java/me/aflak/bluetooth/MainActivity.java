package me.aflak.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import java.io.IOException;


public class MainActivity extends Activity implements Bluetooth.OnConnectedListener, Bluetooth.OnConnectionClosedListener, Bluetooth.OnReceivedMessageListener{
    private Bluetooth bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            This code is for connecting to a bluetooth device called "HC-06"
         */
        bt = new Bluetooth();
        bt.enableBluetooth();

        /*
            Listeners
         */
        bt.setOnConnectedListener(this);
        bt.setOnConnectionClosedListener(this);
        bt.setOnReceivedMessageListener(this);

        /*
            Connecting to the device /!\ you must be paired with it first, via the settings app /!\
            This function is asynchronous !
         */
        bt.connectByName("HC-06");
    }

    @Override
    public void OnConnected(BluetoothDevice device) {
        /*
            Device is connected
            Send a message to the device
         */
        bt.sendMessage("Hello World!");
    }

    @Override
    public void ErrorConnecting(IOException e) {
        // An error occurred during the connection
    }

    @Override
    public void OnConnectionClosed(BluetoothDevice device, String message) {
        // The connection with the device has been closed
    }

    @Override
    public void OnReceivedMessage(String message) {
        // The device sent you a message
    }
}
