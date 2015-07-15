# Bluetooth-Android
Bluetooth is a Android class that allows you to communicate simply with a bluetooth connection.

First of all you need to add the required permissions: 

    <uses-permission android:name="ANDROID.PERMISSION.BLUETOOTH"/>
    <uses-permission android:name="ANDROID.PERMISSION.BLUETOOTH_ADMIN"/>

Then it is very simple ! First initialise the object.

    Bluetooth bt = new Bluetooth();
    
You can also add listeners but don't forget to implements them.

    public class MainActivity extends Activity implements Bluetooth.OnConnectedListener, Bluetooth.OnConnectionClosedListener, Bluetooth.OnReceivedMessageListener
    /*
        ...
     */
    bt.setOnConnectedListener(this);
    bt.setOnConnectionClosedListener(this);
    bt.setOnReceivedMessageListener(this);
    
Connecting to the device named "HC-06" /!\ you must be paired with it first, via the settings app /!\ 
This function is asynchronous !

    bt.connectByName("HC-06");
    
Finally you have all the function implemented.

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
