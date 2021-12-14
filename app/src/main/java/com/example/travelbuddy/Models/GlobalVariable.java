package com.example.travelbuddy.Models;

public class GlobalVariable {
    private static GlobalVariable mInstance= null;

    public boolean isscan = true;
    public int qrcode;
    public String languagechosen = "EN";


    protected GlobalVariable(){}

    public static synchronized GlobalVariable getInstance() {
        if(null == mInstance){
            mInstance = new GlobalVariable();
        }
        return mInstance;
    }

    public int getQrcode() {
        return qrcode;
    }
}
