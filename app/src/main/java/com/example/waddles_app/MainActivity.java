package com.example.waddles_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button refresh, connect, next;
    static TextView state, additionalInfo;
    private BluetoothConnection bt;
    private Thread connectThread;
    private String selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        }
        catch (Exception ex) {
            System.out.println("Exception while hiding the action bar");
        }
        bt = new BluetoothConnection(getApplicationContext());

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        refresh = (Button) findViewById(R.id.refresh);
        connect = (Button) findViewById(R.id.tryConnecting);
        next = (Button) findViewById(R.id.next);
        state = (TextView) findViewById(R.id.state);
        additionalInfo = (TextView) findViewById(R.id.additional_info);

        refresh.setEnabled(true);
        connect.setEnabled(false);
        next.setEnabled(false);

        List<String> devices = bt.findBT();

        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.liststyle, R.id.textView2, devices));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                selectedDevice = (String) parent.getItemAtPosition(position);
                view.setSelected(true);
                connect.setEnabled(true);
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt.isConnected()) bt.disconnect();
                connect.setEnabled(false);
                next.setEnabled(false);
                updateTexts("waiting for connection", "");
                List<String> devices = bt.findBT();
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.liststyle, R.id.textView2, devices));
            }
        });


        connect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                connectThread = new Thread()
                {
                    public void run()
                    {
                        runOnUiThread(new UpdateTextRunnable("CONNECTING...", " "));
                        Integer result = bt.connect(selectedDevice);
                        switch(result){
                            case 0: runOnUiThread(new UpdateTextRunnable("CONNECTED", " "));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            next.setEnabled(true);
                                        }
                                    });
                                    break;
                            case 1: runOnUiThread(new UpdateTextRunnable("FAILURE", "Failed to create socket"));
                                    break;
                            case 2: runOnUiThread(new UpdateTextRunnable("FAILURE", "Failed to connect the socket"));
                                    break;
                            default: runOnUiThread(new UpdateTextRunnable("ERROR", ""));
                                     break;
                        }
                    }
                };
                connectThread.start();
            }
        });


        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (connectThread.isAlive()) {
                    connectThread.interrupt();
                    try {
                        connectThread.join();
                    }
                    catch (Exception ex) {
                        System.out.println("Caught an exception while killing a thread");
                    }
                }

                Intent changeToArrows = new Intent(MainActivity.this, ArrowActivity.class);
                startActivity(changeToArrows);
            }
        });
    }

    public static void updateTexts(String stateTxt, String additionalTxt)
    {
        additionalInfo.setText(additionalTxt);
        state.setText(stateTxt);
        state.invalidate();
        state.requestLayout();
    }
}

