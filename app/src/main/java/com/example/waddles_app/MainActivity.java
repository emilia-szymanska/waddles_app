package com.example.waddles_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button refresh, connect, next;
    private BluetoothConnection bt;
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

        refresh.setEnabled(true);
        connect.setEnabled(false);
        next.setEnabled(false);

        List<String> devices = bt.findBT();
        System.out.println("=====================");
        System.out.println(devices);
        System.out.println("---------------------");
        // Create a List from String Array elements
        //final List<String> fruits_list = new ArrayList<String>(Arrays.asList(cars));

        // Create an ArrayAdapter from List
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
         //       (this, android.R.layout.simple_list_item_1, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        //listView.setAdapter(arrayAdapter);

        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.liststyle, R.id.textView2, devices));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                selectedDevice = (String) parent.getItemAtPosition(position);
                view.setSelected(true);
                connect.setEnabled(true);
                // Anything
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect.setEnabled(false);
                next.setEnabled(false);
                List<String> devices = bt.findBT();
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.liststyle, R.id.textView2, devices));
                //TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                //String uuid = tManager.getDeviceId();
                //System.out.println(uuid);
            }
        });

        connect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println(selectedDevice);
                Thread tmp = new Thread()
                {
                    public void run()
                    {
                        bt.connect(selectedDevice);
                    }
                };
                tmp.start();



            }
        });
    }
}