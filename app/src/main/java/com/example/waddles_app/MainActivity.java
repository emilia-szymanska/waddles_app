package com.example.waddles_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        }
        catch (Exception ex) {
            System.out.println("Exception while hiding the action bar");
        }

        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda", "Volvo", "BMW", "Ford", "Mazda"};
        // Create a List from String Array elements
        //final List<String> fruits_list = new ArrayList<String>(Arrays.asList(cars));

        // Create an ArrayAdapter from List
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
         //       (this, android.R.layout.simple_list_item_1, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        //listView.setAdapter(arrayAdapter);

        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.liststyle, R.id.textView2, cars));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                System.out.println("Your selected fruit is : " + selectedItem);
                view.setSelected(true);
            // Anything
            }
        });
    }
}