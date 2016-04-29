# Bluetooth-Android
Bluetooth is a Android class that allows you to communicate simply with a bluetooth connection.

# Sample

https://github.com/omaflak/Bluetooth-Android/blob/master/app/src/main/java/me/aflak/bluetooth/MainActivity.java

    /*
        This code is for connecting to a bluetooth device called "HC-06"
    */

    Bluetooth bt = new Bluetooth();
    bt.enableBluetooth();

    bt.setBluetoothCallback(new Bluetooth.BluetoothCallback() {
        @Override
        public void onConnect(BluetoothDevice device) {
            Log.e(TAG, "Connected!");
            Log.e(TAG, "Device = " + device.getName() + "   Address = " + device.getAddress());
            bt.send("hello");
        }

        @Override
        public void onDisconnect(BluetoothDevice device, String message) {  // The connection with the device has been closed
            Log.e(TAG, "Disconnected!");
        }

        @Override
        public void onMessage(String message) {  // The device sent you a message
            Log.e(TAG, message);
        }

        @Override
        public void onError(String message) {
            // An error occurred
            Log.e(TAG, message);
        }

        @Override
        public void onConnectError(BluetoothDevice device, String message) {  // An error occurred during connection
            Log.e(TAG, message);
            bt.connectToDevice(device);        // try to connect again
        }
    });

    /*
        Connection...
        /!\ you must be paired with the device via the settings app /!\
    */
    bt.connectToName("HC-06");   //also available:   bt.connectToAddress(address) and bt.connectToDevice(device)
