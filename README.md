# Important
I used a BufferedReader to read data from the bluetooth socket. As I'm reading with readLine(), each message you're sending to the Android must end with a \n. Otherwise it won't be received.
The class uses listeners so everything is really easy!

# How to use

In your Activity

	Bluetooth bluetooth = new Bluetooth();
	bluetooth.enableBluetooth();
	
Set listener to pair with device

	bt.setDiscoveryCallback(new Bluetooth.DiscoveryCallback() {
	
	      @Override
	      public void onFinish() {
	
	      }
	
	      @Override
	      public void onDevice(BluetoothDevice device) {
	
	      }
	
	      @Override
	      public void onPair(BluetoothDevice device) {
	
	      }
	
	      @Override
	      public void onUnpair(BluetoothDevice device) {
	
	      }
	
	      @Override
	      public void onError(String message) {
	
	      }
	});
