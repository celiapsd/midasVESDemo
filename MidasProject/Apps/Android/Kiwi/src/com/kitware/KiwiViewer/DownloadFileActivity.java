package com.kitware.KiwiViewer;

/*----------------------------------------libraries----------------------------------------*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*------------------------------------------------------------------------------------------*/
public class DownloadFileActivity extends Activity {

	/*----------Attributes-----------------------------------------------------------------*/
	private static String filename;
	private static String path;
	
	//public static Item myItem;
	public static MidasResource myItem;
	public static TextView location;
	//private static String url;
	protected ProgressDialog mProgressDialog;
	private static final boolean DEBUG = true;
	public String TAG = "DownloadFileActivity";

	/*------------------showProgressDialog--------------------------------------------------*/
  protected void showProgressDialog(String message) {
    Log.d(TAG, "showProgressDialog("+message+")");
    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    mProgressDialog.setIndeterminate(true);
    mProgressDialog.setMax(100);
    mProgressDialog.setCancelable(false);
    mProgressDialog.setMessage(message);
    mProgressDialog.show();
	  }
	/*------------------dismissProgressDialog--------------------------------------------------*/
	public void dismissProgressDialog() {
		Log.d(TAG, "dismissProgressDialog()");
	      if (mProgressDialog != null) {
	        mProgressDialog.dismiss();
	      }
	    }
	
	 
	/*---------- ON CREATE-----------------------------------------------------------------*/
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Oncreate()");

		super.onCreate(savedInstanceState);
		ChooseFirstActivity.activities.add(this);
		setContentView(R.layout.activity_open_file);
		
		/*myItem = retrieveItemAtributes(SingleListItemActivity.ListChildren);
		setTitle(" Item " + myItem.getItem_name());
		setFilename(myItem.getItem_name());*/
		
		Bundle BundleResourceCommunity = this.getIntent().getExtras();
		if(BundleResourceCommunity.keySet().contains("file"))
		  {
		  
		  
		  Parcelable itemParcelable = BundleResourceCommunity.getParcelable("file");  
		  myItem = new MidasResource (((MidasResource) itemParcelable).getId(), ((MidasResource) itemParcelable).getName(), ((MidasResource) itemParcelable).getSize(),((MidasResource) itemParcelable).getType());
		  setTitle(" Item " + myItem.getName());
		  setFilename(myItem.getName());
		  }
		else
		  {
		  Bundle directoryBundle = this.getIntent().getExtras();
      setPath (directoryBundle.getString("myItemPath"));
      setFilename(directoryBundle.getString("myItemName"));
		  }
    
	}

	/*private Item retrieveItemAtributes(String[] listChildren)
    {
    Item myItem = new Item();
    myItem.set_item_attributes(Integer.parseInt( listChildren[2] ), listChildren[1], listChildren[3]);
    return myItem;
    }*/
  /*-----------------accessors---------------------------------------------*/

  public static String getFilename()
    {
    return filename;
    }
  public static void setFilename(String myFilename)
    {
    filename=myFilename;
    }
  public static String getPath()
    {
    return path;
    }
  public static void setPath(String myPath)
    {
    path=myPath;
    }
  
  /*---------- FUNCTIONS FOR LIFECYCLE ACTIVITY------------------------------------------*/
	protected void onDestroy() {
		if (DownloadFileActivity.DEBUG) {
			Log.d(TAG, "onDestroy()");
		}
		super.onDestroy();
	}

	protected void onPause() {
		if (DownloadFileActivity.DEBUG) {
			Log.d(TAG, "onPause()");
		}
		super.onPause();
	}

	protected void onResume() {
		if (DownloadFileActivity.DEBUG) {
			Log.d(TAG, "onPause()");
		}
		super.onResume();
	}

	protected void onStart() {
		if (DownloadFileActivity.DEBUG) {
			Log.d(TAG, "onStart()");
		}
		super.onStart();
	}

	protected void onStop() {
		if (DownloadFileActivity.DEBUG) {
			Log.d(TAG, "onStop()");
		}
		super.onStop();
	}

	/*----------- CHOOSE SAVE----------------------------------------------------------*/
	public void ButtonOnClickSearchFolder(View v)  
	  {
			Log.d(TAG, "FOLDER CHOOSE");
			choosefolder(v);
	}	
	public void ButtonOnClickSave(View v)  
	  {
		Log.d(TAG, "SAVE CHOOSE");
		  if(path == null)
		  {
		  setPath("/mnt/sdcard/Android/data/com.kitware.KiwiViewer/files");
		  }
		try
      {
      SaveFile(getPath());
      } catch (IOException e)
      {
      e.printStackTrace();
      }
		}
	public void ButtonOnClickOpenFile(View v)  
    {
    Log.d(TAG, "OPEN CURRENT CHOOSE");
    openFile(v);
    }
	
	public void ButtonOnClickReturnHomepage(View v)  
	  {
	  Log.d(TAG, "ButtonOnClickReturnHomepage()");
	  setFilename(null);
    //ChooseFirstActivity.finishAll();
    //start ch again
    Intent i = new Intent(DownloadFileActivity.this,
        ChooseFirstActivity.class);
    startActivity(i);
		}

	/*-------------RETURN HOMEPAGE--------------------------------------------------------------*/
	/*public void returnHomepage(View v) {
		Log.d(TAG, "returnHomepage()");
		
	}*/

	/* -------------CHOOSE FOLDER---------------------------------------------------------------*/
	public void choosefolder(View v) {
		Log.d(TAG, "choosefolder()");
		Intent i = new Intent(DownloadFileActivity.this,
				FileExplorerActivity.class);
		startActivity(i);
	}

	 /* ---------- SAVE FILE-----------------------------------------------------------------*/
	public void SaveFile(String path) throws IOException {
		Log.d(TAG, "SaveFile("+filename +"in" +path+")");

		new WaitWhileSave(path).execute(filename);
    
	}
	
	 /**
	  * ---------- CLASS WaitWhileSave (AsyncTask)-------------------------------------------------------------
   *
   * @Param String, Integer, Integer =  one string parameter, publishing the progress, returning a value
   *  
   **/
	private class WaitWhileSave extends AsyncTask<String, Integer, Integer > {  
		
		private String mPath;
		private String mResult;
		
		/*------------------------------------------------------------*/
		WaitWhileSave(String path) {
		mPath=path;
		}
		
		/*------------------------------------------------------------*/
		@Override
	  protected Integer doInBackground(String... filename) {
        
	    
	    Log.d(TAG + "AsyncTask :WaitWhileSave ", "doInBackground("+filename[0]+")");
	    final String file = filename[0];
	    
	    /**thread for download*/
	  new Thread(new Runnable() {
      
        public void run() {
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "new thread download  ");
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "download getPriority ()"+ Thread.currentThread().getPriority());
            mResult = MidasToolsNative.downloadItem(file,mPath);
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "download finished");
                
        }}).run();
	    
	     /**thread for progress*/
	  new Thread(new Runnable() {
      
      public void run() {
    
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "new thread progress ");
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "progress getPriority ()"+ Thread.currentThread().getPriority());
       Integer [] progress = new Integer [2];
        progress[0] = -1;
        double myProg = progress[0];
  
        while (myProg < 100 && mResult == null) {
    
        try
          {
          Log.d(TAG , "Thread begin Sleep");
          Thread.sleep(1000);
          Log.d(TAG , "Thread finish sleep");
          } catch (InterruptedException e)
            {
            e.printStackTrace();
            }
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "new thread progress "+ myProg);
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "getPriority ()"+ Thread.currentThread().getPriority());
        myProg= MidasToolsNative.getProgressDownload();
        progress[0] = (int) myProg;
        
        publishProgress(progress);
        Log.d(TAG + "AsyncTask :WaitWhileSave ", "new thread ("+progress[0]+")");
        }
      }
    }).start();
      
      Log.d(TAG, mResult);
      return 0;

	    }
		/*------------------------onPreExecute-------------------------------------------------------*/
	    @Override
	    protected void onPreExecute() {
			Log.d(TAG + "AsyncTask :WaitWhileSave ", "onPreExecute()");

	      super.onPreExecute();
	      showProgressDialog("Saving File...");
	    }
	    /*---------------------onProgressUpdate-----------------------------------------------------*/
	    @Override
	    protected void onProgressUpdate(Integer... progress) {
			Log.d(TAG + "AsyncTask :WaitWhileSave ", "onProgressUpdate()");

	        super.onProgressUpdate(progress);
	        mProgressDialog.setProgress(progress[0]);
	    }
	     /*------------------onPostExecute----------------------------------------------------------*/
	    protected void onPostExecute(Integer result) {
			Log.d(TAG + "AsyncTask :WaitWhileSave ", "onPostExecute()");

	    	super.onPostExecute(result);
	    	mProgressDialog.dismiss();
	    	String message = new String();
	    	if (mResult.contains("Error") )
	    	  {
	        message = mResult;
	    	  }
	    	else
	    	  {
          message = "Saved successfully !";
	    	  } 	
	    	OpenAlertDialog(message);
	    	
	    }
	    
	}

	
	/*------------OpenAlertDialog---------------------------------------------*/
	private void OpenAlertDialog(String message) {
		Log.d(TAG , "OpenAlertDialog()");

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("[" + getFilename() + "]");
		adb.setMessage(message);
		
		
		adb.setPositiveButton("ok", new DialogInterface.OnClickListener(){
			
			
	        public void onClick(DialogInterface dialog, int which)
	        {
	          
	          }
	      });
			AlertDialog alertDialog = adb.create();
			 
	    alertDialog.show();
		
	}

	/*---------------Open File ---------------------------------------------- */
	public void openFile(View v) {
		AlertDialog.Builder alt_open = new AlertDialog.Builder(this);
		alt_open.setTitle("[" + filename + "]");
		alt_open.setPositiveButton("open", new DialogInterface.OnClickListener(){

        public void onClick(DialogInterface dialog, int which)
          {
            	
            	
        	Intent i = new Intent(DownloadFileActivity.this,ViewerActivity.class);
          i.putExtra("myItemName", filename);
         	i.putExtra("myItemPath", path);
         	startActivity(i);
       		//ChooseFirstActivity.finishAllExceptParam("ViewerActivity");
       		//ChooseFirstActivity.finishAll();
              
          }
      });
		AlertDialog alertDialog = alt_open.create();
		 
    alertDialog.show();
		
	}
}

