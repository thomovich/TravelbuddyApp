package com.example.travelbuddy.Models;

public class GlobalVariable {
    private static GlobalVariable mInstance= null;

    public boolean isscan = false;
    public int qrcode =2;

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
