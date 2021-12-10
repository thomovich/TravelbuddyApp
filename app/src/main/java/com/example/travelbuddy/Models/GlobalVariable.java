package com.example.travelbuddy.Models;

public class GlobalVariable {
    private static GlobalVariable mInstance= null;

    public boolean isscan;

    protected GlobalVariable(){}

    public static synchronized GlobalVariable getInstance() {
        if(null == mInstance){
            mInstance = new GlobalVariable();
        }
        return mInstance;
    }
}
