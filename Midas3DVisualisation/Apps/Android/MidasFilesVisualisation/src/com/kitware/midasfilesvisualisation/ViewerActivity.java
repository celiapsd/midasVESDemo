
/*========================================================================
VES --- VTK OpenGL ES Rendering Toolkit

    http://www.kitware.com/ves

Copyright 2011 Kitware, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
========================================================================*/
package com.kitware.midasfilesvisualisation;

/*------------------------------Libraries----------------------------------------------------------*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Build;
import android.os.Environment;
import android.os.AsyncTask;
import android.util.Log;
import android.text.InputType;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.text.method.LinkMovementMethod;
import android.net.Uri;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

/*-------------------------------------------------------------------------------------------------------------------*/
public class ViewerActivity extends Activity {

    /*------------------------------Attributes------------------------------------------------*/
  	public static final String TAG = "ViewerActivity";
  
  	protected KiwiGLSurfaceView mView;
  
  	protected ImageButton  mLoadButton;
  	protected ImageButton  mInfoButton;
  	protected ImageButton  mResetViewButton;
  
  	protected ArrayList<String> mBuiltinDatasetNames;
  
  	protected static String filePath;
  	protected int datasetToOpen = -1;
  
  	protected ProgressDialog mProgressDialog = null;
  
  	public static final int DATASETTABLE_REQUEST_CODE = 1;
  	public static boolean midasInitialized = false;


    /*------------------------------showProgressDialog------------------------------------------------*/
  	protected void showProgressDialog() {
		  Log.d(TAG, "showProgressDialog");
	    showProgressDialog("Opening data...");
	  }

    /*------------------------------showProgressDialog (message)--------------------------------------*/
  	protected void showProgressDialog(String message) {
		  Log.d(TAG, "showProgressDialog( message ="+message+" )");

	    mProgressDialog = new ProgressDialog(this);
	    mProgressDialog.setIndeterminate(true);
	    mProgressDialog.setCancelable(false);
	    mProgressDialog.setMessage(message);
	    mProgressDialog.show();
	    
	  }
  	
    /*------------------------------dismissProgressDialog-----------------------------------------------*/
  	public void dismissProgressDialog() {
		  Log.d(TAG, "dismissProgressDialog");

	    if (mProgressDialog != null) {
	      mProgressDialog.dismiss();
	    }
	  }

    /*------------------------------showErrorDialog-----------------------------------------------*/
  	public void showErrorDialog(String title, String message) {

		  Log.d(TAG, "showErrorDialog(title = "+title+" message ="+message+" )");
  
	    AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setIcon(R.drawable.alert_dialog_icon);
	    dialog.setTitle(title);
	    dialog.setMessage(message);
	    dialog.setButton("Ok",  new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	      return;
	      }});
	    dialog.show();
	  }


    /*------------------------------showBrainAtlasDialog-----------------------------------------------*/
  	public void showBrainAtlasDialog() {

		  Log.d(TAG, "showBrainAtlasDialog()");

	    String title = getString(R.string.brainatlas_title);
	    String message = getString(R.string.brainatlas_message);

	    final SpannableString s = new SpannableString(message);
	    Linkify.addLinks(s, Linkify.WEB_URLS);

	    AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setIcon(R.drawable.info_icon);
	    dialog.setTitle(title);
	    dialog.setMessage(s);
	    dialog.setButton("Ok",  new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	      return;
	      }});

	    dialog.show();

	    ((TextView)dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

	  }

    /*------------------------------showCanDialog----------------------------------------------------*/
  	public void showCanDialog() {

		  Log.d(TAG, "showCanDialog()");
  
	    String title = getString(R.string.can_title);
	    String message = getString(R.string.can_message);

	    AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setIcon(R.drawable.info_icon);
	    dialog.setTitle(title);
	    dialog.setMessage(message);
	    dialog.setButton("Ok",  new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	      return;
	      }});

	    dialog.show();
	  }

    /*------------------------------showHeadImageDialog----------------------------------------------------*/
  	public void showHeadImageDialog() {
		  
		  Log.d(TAG, "showHeadImageDialog()");

	    String title = getString(R.string.head_image_title);
	    String message = getString(R.string.head_image_message);

	    AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setIcon(R.drawable.info_icon);
	    dialog.setTitle(title);
	    dialog.setMessage(message);
	    dialog.setButton("Ok",  new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	      return;
	      }});

	    dialog.show();
	  }

    /*------------------------------showCannotOpenAssetDialog----------------------------------------------------*/
  	public void showCannotOpenAssetDialog() {

		  Log.d(TAG, "showCannotOpenAssetDialog()");

	    String title = getString(R.string.cannot_open_asset_title);
	    String message = getString(R.string.cannot_open_asset_message);

	    AlertDialog dialog = new AlertDialog.Builder(this).create();
	    dialog.setIcon(R.drawable.alert_dialog_icon);
	    dialog.setTitle(title);
	    dialog.setMessage(message);
	    dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",  new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	      return;
	      }});

	    dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Open in Browser",  new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	        openUrlInBrowser(getString(R.string.external_data_url));
	      }});

	    dialog.show();
	  }

    /*------------------------------openUrlInBrowser----------------------------------------------------*/
  	protected void openUrlInBrowser(String url) {
		  Log.d(TAG, "openUrlInBrowser(url = "+url+")");

	      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	      startActivity(intent);
	  }


    /*------------------------------handleUriFromIntent----------------------------------------------------*/
  	protected void handleUriFromIntent(Uri uri) {
		  Log.d(TAG, "handleUriFromIntent(uri)");

	    if (uri != null) {
                Log.d(TAG, "uri not null");
	      if (uri.getScheme().equals("file")) {
	        filePath = uri.getPath();
	      }
	    }
	  }
	  
    /*------------------------------getFilePath----------------------------------------------------*/
  	protected static String getFilePath() {
		  Log.d(TAG, "getFilePath()");

	          return filePath;
	    }
  	/*------------------------------getFilePath----------------------------------------------------*/
    protected void setFilePath(String myFilePath) {
      Log.d(TAG, "setFilePath()");

            filePath = myFilePath;
      }

	  
    /*------------------------------onNewIntent----------------------------------------------------*/
  	@Override 
	  protected void onNewIntent(Intent intent) {
		  
	    super.onNewIntent(intent);
		  Log.d(TAG, "onNewIntent(intent)");

	    handleUriFromIntent(intent.getData());
	  }



    /*------------------------------initBuiltinDatasetNames----------------------------------------*/
  	protected void initBuiltinDatasetNames() {

		  Log.d(TAG, "initBuiltinDatasetNames()");

	    if (mBuiltinDatasetNames == null) {
	        int numberOfDatasets = MidasNative.getNumberOfBuiltinDatasets();
	        mBuiltinDatasetNames = new ArrayList<String>();
	        for(int i = 0; i < numberOfDatasets; ++i) {
	          mBuiltinDatasetNames.add(MidasNative.getDatasetName(i));
	        }
	    }
	  }

	  
    /*------------------------------maybeLoadDefaultDataset----------------------------------------*/
  	void maybeLoadDefaultDataset() {
		  
		  Log.d(TAG, "maybeLoadDefaultDataset()");


	    if (getFilePath() == null) {
			  Log.d(TAG, "maybeLoadDefaultDataset()--> fileToOpen==null");

	      String storageDir = getExternalFilesDir(null).getAbsolutePath();
	      mView.postLoadDefaultDataset(this, storageDir);
	      
	    }
	    else{
	      MidasNative.clearExistingDataset();
	    }
	  }


    /*------------------------------onConfigurationChanged----------------------------------------*/
  	@Override
	  public void onConfigurationChanged(Configuration newConfig) {
		  Log.d(TAG, "onConfigurationChanged()");

	    super.onConfigurationChanged(newConfig);
	    mView.stopRendering();
	  }


    /*------------------------------onCreate-------------------------------------------------------*/
  	@Override
	  protected void onCreate(Bundle bundle) {
	    super.onCreate(bundle);
		  Log.d(TAG, "onCreate()");

		 //MidasNative.init(100, 100);
        handleUriFromIntent(getIntent().getData());
		  /*finish();*/
		  
      setFilePath(DownloadFileActivity.getOutFilename());
      //Log.d(TAG, "set file path ok ()");
      this.setContentView(R.layout.kiwivieweractivity);
      Log.d(TAG, "setcontent view ok()");
      ChooseFirstAction.activities.add(this);

	    mView = (KiwiGLSurfaceView) this.findViewById(R.id.glSurfaceView);
	    //KiwiNative.init(100,100);
	    maybeLoadDefaultDataset();
	    
	    mLoadButton = (ImageButton) this.findViewById(R.id.loadDataButton);
	    mInfoButton = (ImageButton) this.findViewById(R.id.infoButton);
	    mResetViewButton = (ImageButton) this.findViewById(R.id.resetButton);


	    mLoadButton.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	            Intent datasetTableIntent = new Intent();
	            datasetTableIntent.setClass(ViewerActivity.this, DatasetListActivity.class);
	            initBuiltinDatasetNames();
	            datasetTableIntent.putExtra("com.kitware.midasfilesvisualisation.bundle.DatasetList", mBuiltinDatasetNames);
	            startActivityForResult(datasetTableIntent, DATASETTABLE_REQUEST_CODE);
	        }
	    });

	    mInfoButton.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {

	            /*Intent infoIntent = new Intent();
	            infoIntent.setClass(ViewerActivity.this, InfoActivity.class);
	            startActivity(infoIntent);*/
	        }
	    });

	    mResetViewButton.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	            mView.resetCamera();
	        }
	    });

	  }


    /*------------------------------copyFile--------------------------------------------------------------------*/
  	private void copyFile(InputStream in, OutputStream out) throws IOException {
		  Log.d(TAG, "copyFile(in,out)");

	      byte[] buffer = new byte[1024];
	      int read;
	      while((read = in.read(buffer)) != -1) {
	        out.write(buffer, 0, read);
	      }
	  }


    /*------------------------------copyAssetFileToStorage-------------------------------------------------------*/
  	public String copyAssetFileToStorage(String filename) {

	    // todo- check storage state first, show alert dialog in case of problem
	    // Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
	    //MEDIA_MOUNTED_READ_ONLY
		  Log.d(TAG, "copyAssetFileToStorage(filename"+filename+")");

	    String storageDir = getExternalFilesDir(null).getAbsolutePath();
	    String destFilename= new String ();
	    
	    if (filename.contains("/"))
	    	destFilename = filename;
	    else
	    	destFilename = storageDir + "/" + filename;

	    File destFile = new File(destFilename);
	    if (destFile.exists()) {
	      return destFilename;
	    }


	    InputStream in = null;
	    OutputStream out = null;
	    try {
              in = getAssets().open(filename);
               //in = new FileInputStream(DownloadFileActivity.getOutFilename());
	      out = new FileOutputStream(destFilename);
	      copyFile(in, out);
	      in.close();
	      in = null;
	      out.flush();
	      out.close();
	      out = null;
	    }
	    catch(Exception e) {
	      Log.e(TAG, e.getMessage());
	    }

	    return destFilename;
	  }


    /*------------------------------CLASS BuiltinDataLoader (AsyncTask)-------------------------------------------------------*/
  	private class BuiltinDataLoader extends AsyncTask<String, Integer, String> {

	    public int mBuiltinDatasetIndex;

	    
	    /*----------Constructor---------------------------------------------------------*/
	    BuiltinDataLoader(int builtinDatasetIndex) {
	      mBuiltinDatasetIndex = builtinDatasetIndex;
	    }

	     /*----------doInBackground---------------------------------------------------------*/
	    protected String doInBackground(String... filename) {
			  Log.d(TAG+"AsyncTask=BuiltinDataLoader", "doInBackground(filename"+filename[0]+")");


	      if (filename[0].equals("textured_sphere.vtp")) {
	        copyEarthAssets();
	      }

	      return copyAssetFileToStorage(filename[0]);
	    }

      /*----------onPreExecute---------------------------------------------------------*/
	    protected void onPreExecute() {
			  Log.d(TAG+"AsyncTask=BuiltinDataLoader", "onPreExecute()");

	      showProgressDialog();
	    }

      /*----------onPostExecute---------------------------------------------------------*/
	    protected void onPostExecute(String filename) {
			  Log.d(TAG+"AsyncTask=BuiltinDataLoader", "onPostExecute()");

	      mView.loadDataset(filename, mBuiltinDatasetIndex, ViewerActivity.this);
	    }
	    
	  }


    /*------------------------------loadDataset (index)---------------------------------------------------------------------------*/
  	public void loadDataset(int builtinDatasetIndex) {
		  Log.d(TAG, "loadDataset(builtinDatasetIndex = "+builtinDatasetIndex+")");


	    String filename = MidasNative.getDatasetFilename(builtinDatasetIndex);
	    Log.d(TAG, "loadDataset(filename = "+filename+"builtinDatasetIndex = "+builtinDatasetIndex+")");

	    // don't attempt to open large asset files on android api 8
	   int sdkVersion = Build.VERSION.SDK_INT;
	    if (sdkVersion <= 8
	        && (filename.equals("visible-woman-hand.vtp")
	            || filename.equals("AppendedKneeData.vtp")
	            || filename.equals("cturtle.vtp")
	            || filename.equals("model_info.txt"))) {
	      showCannotOpenAssetDialog();
	      return;
	    }
	    
	    new BuiltinDataLoader(builtinDatasetIndex).execute(filename);
	  }


    /*------------------------------loadDataset (filename)---------------------------------------------------------------------------*/
  	public void loadDataset(String filename) {
		  Log.d(TAG, "loadDataset(filename = "+filename+")");

	    showProgressDialog();
	    mView.loadDataset(filename, ViewerActivity.this);
	  }

    /*------------------------------postLoadDataset-----------------------------------------------------------------------------------*/
  	public void postLoadDataset(String filename, boolean result, String errorTitle, String errorMessage) {
		  Log.d(TAG, "postLoadDataset(filename = "+filename+",errorTitle = "+errorTitle+",result = "+result+",errorMessage = "+errorMessage+")");

      dismissProgressDialog();
	    if (!result) {
	      showErrorDialog(errorTitle, errorMessage);

	    }
	    else {

	      if (filename.endsWith("model_info.txt")) {
	        showBrainAtlasDialog();
	      }
	      else if (filename.endsWith("can0000.vtp")) {
	        showCanDialog();
	      }
	      else if (filename.endsWith("head.vti")) {
	        showHeadImageDialog();
	      }
	    }
	  }
  	


    /*------------------------------onPause-------------------------------------------------------------------------------------------*/
  	@Override 
  	protected void onPause() {
	      super.onPause();
		  Log.d(TAG, "onPause");

	      mView.onPause();
	  }
	  
    /*------------------------------onBackPressed-----------------------------------------------------------------------------------*/
  	 /*@Override
		public void onBackPressed() {
	        Log.d(TAG, "onBackPressed Called");
	        
	        mView.onBackPressed();
	        //KiwiNative.onBackPressed();
	        //ViewerActivity.this.finish();
	        //mView.surfaceDestroyed(getHolder());
	        //KiwiGLSurfaceView.this.finish();

	        return;
		}*/
	  
    /*------------------------------onResume----------------------------------------------------------------------------------------*/
  	@Override 
  	protected void onResume() {
	      super.onResume();
		  Log.d(TAG, "onResume");

	      mView.onResume();

	      if (getFilePath()  != null) {
	        loadDataset(getFilePath() );
	        setFilePath(null);
	      }
	      else if (datasetToOpen >= 0) {
	        loadDataset(datasetToOpen);
	        datasetToOpen = -1;
	      }
	  }

	  /**
	   * ----------------------------onActivityResult-------------------------------------------------------------------------------------
	   * 
	   * Get results from the dataset dialog
	   */
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Bundle curBundle = null;
		  Log.d(TAG, "onActivityResult");

	    if (data != null) {
	      curBundle = data.getExtras();
	    }
	    if (requestCode == DATASETTABLE_REQUEST_CODE && curBundle != null
	        && curBundle.containsKey("com.kitware.midasfilesvisualisation.bundle.DatasetName")) {

	      String name = curBundle.getString("com.kitware.midasfilesvisualisation.bundle.DatasetName");
	      int offset = curBundle.getInt("com.kitware.midasfilesvisualisation.bundle.DatasetOffset");
	      datasetToOpen = offset;
	    }

	    super.onActivityResult(requestCode, resultCode, data);
	  }

	  /*---------------------------copyEarthAssets-----------------------------------------------------------------------------------*/
  	protected void copyEarthAssets() {
  		  Log.d(TAG, "copyEarthAssets");
  	  copyAssetFileToStorage("earth.jpg");
  	}
}










  
