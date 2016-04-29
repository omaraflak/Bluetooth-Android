# Bluetooth-Android
Bluetooth is a Android class that allows you to communicate simply with a bluetooth connection.


        /*
            This code is for connecting to a bluetooth device called "HC-06"
         */


        Bluetooth bt = new Bluetooth();
        bt.enableBluetooth();

        /*
            Callbacks
         */     
        bt.setBluetoothCallback(new Bluetooth.BluetoothCallback() {
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
            public void onError(String message) {
                // An error occurred
                Log.e(TAG, message);
            }

            @Override
            public void onConnectError(BluetoothDevice device, String message) {
                // An error occurred during connection
                // try to connect again
                Log.e(TAG, message);
                bt.connectToDevice(device);
            }
        });

        /*
            Connecting to the device /!\ you must be paired with it first, via the settings app /!\
            This function is asynchronous !
         */
        bt.connectToName("HC-06");      //also available:        bt.connectToAddress(address) and bt.connectToDevice(device)

# Sample

https://github.com/omaflak/Bluetooth-Android/blob/master/app/src/main/java/me/aflak/bluetooth/MainActivity.java
