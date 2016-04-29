package me.aflak.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Omar on 14/07/2015.
 */
public class Bluetooth {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private BluetoothAdapter bluetoothAdapter;
    private ConnectThread thread;
    private boolean connected=false;

    private BluetoothCallback listener=null;

    public Bluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void enableBluetooth(){
        if(bluetoothAdapter!=null) {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
    }

    public void connectToAddress(String address) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        thread = new ConnectThread(device);
        thread.start();
    }

    public void connectToName(String name) {
        for (BluetoothDevice blueDevice : bluetoothAdapter.getBondedDevices()) {
            if (blueDevice.getName().equals(name)) {
                connectToAddress(blueDevice.getAddress());
                return;
            }
        }
    }

    public void connectToDevice(BluetoothDevice device){
        thread = new ConnectThread(device);
        thread.start();
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public boolean isConnected(){
        return connected;
    }

    public void send(String msg){
        thread.send(msg);
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private OutputStream out;

        public ConnectThread(BluetoothDevice device) {
            Bluetooth.this.device=device;
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                if(listener!=null)
                    listener.onError(e.getMessage());
            }
            Bluetooth.this.socket=mmSocket;
        }

        public void send(String msg){
            try{
                out.write(msg.getBytes());
            } catch (IOException e) {
                connected=false;
                if(listener!=null)
                    listener.onDisconnect(device, e.getMessage());
            }
        }

        public void run() {
            BufferedReader input = null;
            bluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
                connected=true;

                input = new BufferedReader(new InputStreamReader(mmSocket.getInputStream()));
                out = mmSocket.getOutputStream();

                if(listener!=null)
                    listener.onConnect(device);
            } catch (IOException e) {
                if(listener!=null)
                    listener.onConnectError(mmSocket.getRemoteDevice(), e.getMessage());

                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    if (listener != null)
                        listener.onError(closeException.getMessage());
                }
            }

            if(connected) {
                String msg;

                try {
                    assert input != null;
                    while ((msg = input.readLine()) != null) {
                        if (listener != null)
                            listener.onMessage(msg);
                    }

                    if (listener != null)
                        listener.onDisconnect(device, "null");
                } catch (IOException e) {
                    if (listener != null)
                        listener.onDisconnect(device, e.getMessage());
                }
            }
        }
    }

    public List<BluetoothDevice> getPairedDevices(){
        List<BluetoothDevice> devices = new ArrayList<>();
        for (BluetoothDevice blueDevice : bluetoothAdapter.getBondedDevices()) {
            devices.add(blueDevice);
        }
        return devices;
    }

    public BluetoothSocket getSocket(){
        return socket;
    }

    public BluetoothDevice getDevice(){
        return device;
    }

    public String getDeviceName(){
        return device.getName();
    }

    public String getDeviceAddress(){
        return device.getAddress();
    }


    public interface BluetoothCallback{
        void onConnect(BluetoothDevice device);
        void onDisconnect(BluetoothDevice device, String message);
        void onMessage(String message);
        void onError(String message);
        void onConnectError(BluetoothDevice device, String message);
    }

    public void setBluetoothCallback(BluetoothCallback listener) {
        this.listener = listener;
    }

    public void removeBluetoothCallback(){
        this.listener = null;
    }

}


