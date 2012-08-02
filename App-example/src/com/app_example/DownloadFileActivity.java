package com.app_example;

//----------------------------------------libraries----------------------------------------//
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

import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;




//------------------------------------------------------------------------------------------//
public class DownloadFileActivity extends Activity 
{	  
	
	//---------- Attributes-----------------------------------------------------------------//
	public static String EXTRA_MESSAGE2 = "com.app_example.MESSAGE";
	public String filename;
	public String url;
	public final static int CODE_RETOUR=0;
	
	//---------- ON CREATE-----------------------------------------------------------------//
	public void onCreate(Bundle savedInstanceState)
	{    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_file);   
		// Get the message from the intent
		Intent intent = getIntent();
		//get the retrieve data contained within it
		EXTRA_MESSAGE2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		//transform in string
		
		url= "http://midas3.kitware.com/midas/download?items=";
		try 
		{
			JSONObject jsonObject = new JSONObject(EXTRA_MESSAGE2);
		
			int Id=jsonObject.getInt("folder_id");
			url+=Id;
			
			filename=jsonObject.getString("name");
			
			int PositionName=filename.indexOf("\n");
	    	filename= filename.substring(0,PositionName);
	    	
	    	setTitle(filename);
	    	
		} catch (JSONException e) {
			e.printStackTrace();
			}
		
		//--
		if(MainActivity.Token!=null)
		{
	   	 	url+="&token="+MainActivity.Token;
		}
	}
	//---------- FUNCTIONS FOR LIFECYCLE ACTIVITY------------------------------------------//
    protected void onDestroy() {
        super.onDestroy(); }
      protected void onPause() {
        super.onPause(); }
      protected void onResume() {
        super.onResume(); }
      protected void onStart() {
        super.onStart(); }
      protected void onStop() {
        super.onStop(); } 
      
      
	//----------- CHOOSE SAVE --------------------------------------------------------------//
	public void ChooseSave(View v) 
    {
	

  	  	switch (v.getId())
  	  	{
  	  		case(R.id.Folder):
  	  			choosefolder(v);
  	  			break;
  	                   
  			case(R.id.Save):
				try 
  				{
					SaveFile(new URL (url),filename);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
  				break;
  			
  			case(R.id.ReturnHomepage):
  				returnHomepage(v);
  				break;
    	  }
  	}
	//-------------RETURN HOMEPAGE ----------------------------------------------------------------//
	public void returnHomepage(View v) 
	{
		//System.out.println(DownloadFileActivity.this.getParent());
		//Intent i = new Intent();finish();
		//setResult(CODE_RETOUR);
		MainActivity.finishAll();
		//System.exit(0);
	}

	//-------------CHOOSE FOLDER ----------------------------------------------------------------//
	public void choosefolder(View v) 
	{
		Intent i=new Intent(DownloadFileActivity.this, FileExplorerActivity.class);
		startActivityForResult(i, CODE_RETOUR);
		
	}
	//---------- SAVE FILE -----------------------------------------------------------------//
	public void SaveFile(URL u,String filename) throws IOException
	{
			
		//Open your local db as the input stream
		URLConnection uc = u.openConnection();
					
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("FILE NOT VALID.");
		}
		InputStream myInput = uc.getInputStream();
		// Path to the just created empty db
		String outFileName=new String();
		if(FileExplorerActivity.myPath==null)
		{
			outFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+filename;
			
		}else{
			outFileName = FileExplorerActivity.myPath+ "/"+filename;
			
		}
		System.out.println(outFileName);
		//Open the empty db as the output stream
		File out = new File(outFileName);
		OutputStream myOutput = null;
		myOutput = new FileOutputStream(out);
			
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		try
		{
			while ((length = myInput.read(buffer))>0)
			{
				myOutput.write(buffer, 0, length);
			}
			//Close the streams 
			myOutput.flush();
			myOutput.close();
			myInput.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//System.out.println("file downloaded successfully in the sd card !");	
			
			/*TextView textView2 = new TextView(this);
			textView2.setTextSize(30);
		 	textView2.setText("file downloaded successfully !");
		  	setContentView(textView2);*/
}
        
		
		
//------------------------------------------------------------------------------------------//
//------------------------------ USEFULL ---------------------------------------------------//
//------------------------------------------------------------------------------------------//		
		

	    

	