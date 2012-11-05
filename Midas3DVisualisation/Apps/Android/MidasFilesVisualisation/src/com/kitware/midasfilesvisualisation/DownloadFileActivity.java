package com.kitware.midasfilesvisualisation;

/*----------------------------------------libraries----------------------------------------*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*------------------------------------------------------------------------------------------*/
public class DownloadFileActivity extends Activity {

	/*----------Attributes-----------------------------------------------------------------*/
	public static String EXTRA_MESSAGE = "com.app_example.MESSAGE";
	private static String filename;
	private static String outFilename;
	public static TextView location;
	private static String url;
	public final static int CODE_RETOUR = 0;
	protected ProgressDialog mProgressDialog;
	private static final boolean DEBUG = true;
	public String TAG = "DownloadFileActivity";

	/*------------------showProgressDialog--------------------------------------------------*/
	protected void showProgressDialog(String message) {
		Log.d(TAG, "showProgressDialog("+message+")");
	      mProgressDialog = new ProgressDialog(this);
	      mProgressDialog.setIndeterminate(true);
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
		ChooseFirstAction.activities.add(this);
		setContentView(R.layout.activity_open_file);

		/*
		 * String Path; if (FileExplorerActivity.root==null) { Log.d(TAG,
		 * Environment.getExternalStorageDirectory().getAbsolutePath());
		 * Path=Environment.getExternalStorageDirectory().getAbsolutePath(); }
		 * else { Path=FileExplorerActivity.root; }
		 * 
		 * location.setText("Location: " + Path);
		 */

		setUrl(ChooseFirstAction.getUrlBeginning() + "/download?items=");

		if (getFilename() == null) {

			Intent intent = getIntent();
			// get the retrieve data contained within it
			EXTRA_MESSAGE = intent.getStringExtra(SingleListItemActivity.EXTRA_MESSAGE3);

			try {
				JSONObject jsonObject = new JSONObject(EXTRA_MESSAGE);
				int Id = jsonObject.getInt("folder_id");
				setUrl(getUrl()+ Integer.toString(Id));
				setFilename(jsonObject.getString("name"));
				int PositionName = getFilename().indexOf("\n");
				setFilename(getFilename().substring(0, PositionName));

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		setTitle(getFilename());

		if (ChooseFirstAction.getToken() != null) {
			setUrl(getUrl() + "&token=" + ChooseFirstAction.getToken());
		}
	}

	/*-----------------accessors---------------------------------------------*/
  public static String getUrl()
    {
    return url;
    }
  public static void setUrl(String myUrl)
    {
    url=myUrl;
    }
  public static String getFilename()
    {
    return filename;
    }
  public static void setFilename(String myFilename)
    {
    filename=myFilename;
    }
  public static String getOutFilename()
    {
    return outFilename;
    }
  public static void setOutFilename(String myOutFilename)
    {
    outFilename=myOutFilename;
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
	@Override
	public void onBackPressed() {
    if (DownloadFileActivity.DEBUG) {
      Log.d(TAG, "onBackPressed()");
    }
    setOutFilename(null);
    setFilename(null);
    super.onBackPressed();
    
	}

	/*----------- CHOOSE SAVE----------------------------------------------------------*/
	public void ChooseSave(View v) {

		
		Log.d(TAG, "ChooseSave(view)");
		
		switch (v.getId()) {
		case (R.id.Folder):
			Log.d(TAG, "FOLDER CHOOSE");

			choosefolder(v);
			break;

		case (R.id.Save):
			try {
				Log.d(TAG, "SAVE CHOOSE");
				SaveFile(new URL(url));

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case (R.id.OpenCurrentFile):
			Log.d(TAG, "OPEN CURRENT CHOOSE");
			openFile(v);
			break;

		case (R.id.SearchFile):
			Log.d(TAG, "SEARCH FILE CHOOSE");
			choosefolder(v);
			break;

		case (R.id.ReturnHomepage):
			Log.d(TAG, "RETURNHOMEPAGE CHOOSE");
			returnHomepage(v);
			break;
		}
	}

	/*-------------RETURN HOMEPAGE--------------------------------------------------------------*/
	public void returnHomepage(View v) {
		Log.d(TAG, "returnHomepage()");
		setFilename(null);
		setUrl(null);
		ChooseFirstAction.finishAll();
	}

	/* -------------CHOOSE FOLDER---------------------------------------------------------------*/
	public void choosefolder(View v) {
		Log.d(TAG, "choosefolder()");
		Intent i = new Intent(DownloadFileActivity.this,
				FileExplorerActivity.class);
		startActivity(i);
	}

	 /* ---------- SAVE FILE-----------------------------------------------------------------*/
	public void SaveFile(URL url) throws IOException {
		Log.d(TAG, "SaveFile("+url+")");

		new WaitWhileSave(url).execute(filename);
    
	}
	
	 /**
	  * ---------- CLASS WaitWhileSave (AsyncTask)-------------------------------------------------------------
   *
   * @Param String, Integer, Integer =  one string parameter, publishing the progress, returning a value
   *  
   **/
	private class WaitWhileSave extends AsyncTask<String, Integer, Integer > {  
		
		private URL mUrl;
		
		/*------------------------------------------------------------*/
		WaitWhileSave(URL url) {
			mUrl=url;
		}
		
		/*------------------------------------------------------------*/
		@Override
	    protected Integer doInBackground(String... filename) {
	        try {
	    		Log.d(TAG + "AsyncTask :WaitWhileSave ", "doInBackground("+filename[0]+")");
	    		
	        	URLConnection uc = mUrl.openConnection();

	    		int FileLenght = uc.getContentLength();
	    		if (FileLenght == -1) {
	    			throw new IOException("FILE NOT VALID.");
	    		}
	    		InputStream myInput = uc.getInputStream();
	    		
    		
	    		//DownloadFileActivity.outFileName = new String();
	    		if (FileExplorerActivity.getRoot() == null) {
	    			DownloadFileActivity.setOutFilename(getExternalFilesDir(null).getAbsolutePath()+ "/" +  filename[0]);
	    			//DownloadFileActivity.setoutFileName("/mnt/sdcard/Android/data/com.midasfilesvisualisation/files"+ "/" +  filename[0]);
	    		} else {
	    		  DownloadFileActivity.setOutFilename(FileExplorerActivity.getRoot() + "/" + filename[0]);
	    		}

	    		System.out.println(DownloadFileActivity.getOutFilename());
	    		/*Open the empty db as the output stream*/
	    		File out = new File(DownloadFileActivity.getOutFilename());
	    		OutputStream myOutput = null;
	    		myOutput = new FileOutputStream(out);
	    		
	    		/*transfer bytes from the inputfile to the outputfile*/
	    		byte[] buffer = new byte[1024];
	    		int length;
	    		try {
	    			while ((length = myInput.read(buffer)) > 0) {
	    				myOutput.write(buffer, 0, length);
	    			}

	    			/*Close the streams*/
	    			myOutput.flush();
	    			myOutput.close();
	    			myInput.close();
	    			return 1;
	    			
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	} catch (Exception e) {
	        }
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
	    	OpenAlertDialog();
	    	
	    }
	    
	}

	
	/*------------OpenAlertDialog---------------------------------------------*/
	private void OpenAlertDialog() {
		Log.d(TAG , "OpenAlertDialog()");

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("[" + getFilename() + "]");
		adb.setMessage("Saved successfully !");
		
		
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
		alt_open.setTitle("[" + getFilename() + "]");
		alt_open.setPositiveButton("open", new DialogInterface.OnClickListener(){
		
		
        public void onClick(DialogInterface dialog, int which)
          {
        	
        	
        	Intent i = new Intent(DownloadFileActivity.this,ViewerActivity.class);
        	startActivity(i);
      		//ChooseFirstAction.finishAllExceptParam("ViewerActivity");
       		//ChooseFirstAction.finishAll();
          
          }
      });
		AlertDialog alertDialog = alt_open.create();
		 
    alertDialog.show();
		
	}
}

