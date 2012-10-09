package com.kitware.midasfilesvisualisation;

public class MidasNative {


	static {
        System.loadLibrary("MidasNative");
    }

    public static native synchronized void init(int width, int height);
    public static native synchronized void initFile(int width, int height,String filename, String path);
    public static native synchronized void putInDatabase(String filename, String path);
    public static native synchronized boolean loadDataset(String filename, int builtinDatasetIndex);
    public static native synchronized int giveCurrentBuiltinDatasetIndex();
    public static native synchronized String getLoadDatasetErrorTitle();
    public static native synchronized String getLoadDatasetErrorMessage();
    public static native synchronized int getDefaultBuiltinDatasetIndex();
    
}
