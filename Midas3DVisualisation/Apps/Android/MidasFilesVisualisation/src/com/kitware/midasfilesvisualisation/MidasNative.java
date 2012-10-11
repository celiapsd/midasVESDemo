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
    public static native synchronized void clearExistingDataset();
    public static native synchronized void reshape(int width, int height);
    public static native synchronized boolean render();
    public static native synchronized boolean getDatasetIsLoaded();
    
    public static native synchronized void handleSingleTouchPanGesture(float dx, float dy);
    public static native synchronized void handleTwoTouchPanGesture(float x0, float y0, float x1, float y1);
    public static native synchronized void handleTwoTouchPinchGesture(float scale);
    public static native synchronized void handleTwoTouchRotationGesture(float rotation);
    public static native synchronized void handleSingleTouchUp();
    public static native synchronized void handleSingleTouchDown(float x, float y);
    public static native synchronized void handleSingleTouchTap(float x, float y);
    public static native synchronized void handleDoubleTap(float x, float y);
    public static native synchronized void handleLongPress(float x, float y);
    public static native synchronized void resetCamera();
    public static native synchronized void stopInertialMotion();
    
    public static native synchronized String getDatasetFilename(int offset);
    public static native synchronized String getDatasetName(int offset);
    public static native synchronized int getNumberOfBuiltinDatasets();
    public static native synchronized int getNextBuiltinDatasetIndex();
      
          
}
