package com.example.waddles_app;

public class UpdateTextRunnable implements Runnable
{
    private String mainState;
    private String mainInfo;

    public UpdateTextRunnable(String state, String info)
    {
        this.mainState = state;
        this.mainInfo = info;
    }

    public void run()
    {
        MainActivity.updateTexts(mainState, mainInfo);
    }
}