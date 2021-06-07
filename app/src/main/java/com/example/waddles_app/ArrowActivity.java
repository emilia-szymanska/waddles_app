package com.example.waddles_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ArrowActivity extends AppCompatActivity
{

    private ImageButton up, down, left, right, center, previous, upleftarrow, uprightarrow, downleftarrow, downrightarrow;
    BluetoothConnection bt;
    String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        }
        catch (Exception ex) {
            System.out.println("Exception while hiding the action bar");
        }

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        setContentView(R.layout.arrow_activity);
        bt = new BluetoothConnection(getApplicationContext());

        up              = findViewById(R.id.uparrow);
        down            = findViewById(R.id.downarrow);
        left            = findViewById(R.id.leftarrow);
        right           = findViewById(R.id.rightarrow);
        center          = findViewById(R.id.center);
        previous        = findViewById(R.id.previous);
        upleftarrow     = findViewById(R.id.upleftarrow);
        uprightarrow    = findViewById(R.id.uprightarrow);
        downleftarrow   = findViewById(R.id.downleftarrow);
        downrightarrow  = findViewById(R.id.downrightarrow);

        up.setImageResource(R.drawable.image_btn_src);



        Thread btThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println(message);
                        bt.sendCommand(message);
                        Thread.sleep(50);
                    }
                    catch(Exception e) {
                        System.out.println("Caught an exception while sleeping");
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        });



        up.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "ff";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        down.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "bb";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        left.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "ll";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        right.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "rr";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        upleftarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "fl";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        uprightarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "fr";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        downleftarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "bl";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }
        });


        downrightarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "br";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }

        });


        center.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "cc";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "nn";
                        return true;
                }
                return false;
            }

        });

        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bt.disconnect();
                closeThread(btThread);

                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });


        btThread.start();
    }

    public static void closeThread(Thread chosenThread)
    {
        if (chosenThread.isAlive())
        {
            chosenThread.interrupt();
            try
            {
                chosenThread.join();
            }
            catch (Exception ex)
            {
                System.out.println("Caught an exception while killing a thread");
            }
        }
    }
}