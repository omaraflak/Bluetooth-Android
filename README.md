# Bluetooth-Android
Bluetooth is a Android class that allows you to communicate simply with a bluetooth connection.

First of all you need to add the required permissions: 

    <uses-permission android:name="ANDROID.PERMISSION.BLUETOOTH"/>
    <uses-permission android:name="ANDROID.PERMISSION.BLUETOOTH_ADMIN"/>

Then it is very simple ! Here is a simple example.

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
