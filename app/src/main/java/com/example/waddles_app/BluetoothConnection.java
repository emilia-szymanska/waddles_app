package com.example.waddles_app;

public class BluetoothConnection {
}


/*

public void click(View view) {
        if(view == openButton) {
            try {
                myBluetooth.findBT();
                myBluetooth.openBT();
            }
            catch(IOException e){}
        }

        if(view == closeButton) {
            try {
                myBluetooth.closeBT();
            }
            catch(IOException e){}
        }
    }
public class MyBluetooth {

    private Context context;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private Thread workerThread;
    private byte[] readBuffer;
    private int readBufferPosition;
    private volatile boolean stopWorker;
    private TextView myLabel;



    public MyBluetooth(Context context, TextView myLabel) {
        this.context = context;
        this.myLabel = myLabel;
    }

    public void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }

        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBluetooth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(enableBluetooth);
        }


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("Pogodynka"))
                {
                    mmDevice = device;
                    break;
                }
            }
        }
        myLabel.setText("Device found");
    }

    public void openBT() throws IOException {
        if(mmDevice == null) {
            myLabel.setText("Nie połączono z żadnym urządzeniem");
        }
        else {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
        }
        beginListenForData();
    }

    public void beginListenForData() {

        final Handler handler = new Handler();
        final Byte delimiter = 10; // ASCII code for nextline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopWorker) {

                    try {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable >0) {
                            byte [] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0; i<bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if(b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data =  new String(encodedBytes, "UTF-8");
                                    readBufferPosition = 0;

                                    final char tab [] = new char[4];

                                    for(int j = 0; j<3; j++ ) {

                                        tab[j]=data.charAt(j);
                                    }

                                    final String dane = "Nasłonecznienie: " + tab[0] + tab[1] + "%" + "\n" + "Wilgotność: "
                                            + tab[2] + tab[3] + "%";



                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            myLabel.setText(dane);
                                        }
                                    });
                                }
                                else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        stopWorker = true;
                    }
                }
            }
        });
        workerThread.start();
    }

    public void closeBT() throws IOException
    {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }
}


 */