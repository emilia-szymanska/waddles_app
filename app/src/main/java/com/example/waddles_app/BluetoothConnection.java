package com.example.waddles_app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Handler;


public class BluetoothConnection {

    private Context context;
    private BluetoothAdapter btAdapter;
    private BluetoothSocket btSocket;
    private BluetoothDevice btDevice;
    private OutputStream btOutStream;
    private InputStream btIStream;
    private String label;
    private Map<String, BluetoothDevice> deviceMap = new HashMap<String, BluetoothDevice>(); ;
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    public BluetoothConnection(Context context) {
        this.context = context;
    }

    public BluetoothConnection(Context context, String device) {
        this.context = context;
        findBT();

    }


    public List<String> findBT() {
        List<String> devicesList = new ArrayList<String>();
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if(btAdapter == null) {
            label = "No BT Adapter available";
            return devicesList;
        }

        if(!btAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBluetooth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(enableBluetooth);
        }

        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices) {
                devicesList.add(device.getName());
                deviceMap.put(device.getName(), device);
            }
        }
        else{
            devicesList.add("No paired devices");
        }
        return devicesList;
    }

    public synchronized void connect(String deviceName) {
        BluetoothDevice device = deviceMap.get(deviceName);
        btSocket = null;
        // Get a BluetoothSocket for a connection with the
        // given BluetoothDevice
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            System.out.println("Failed to create RfcommSocket");
        }
        try{
            btSocket.connect();
        } catch (Exception e){
            System.out.println(e);
            System.out.println("Failed to connect the socket");
        }

    }
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