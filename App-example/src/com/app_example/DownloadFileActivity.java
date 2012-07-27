package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;



//------------------------------------------------------------------------------------------//
public class DownloadFileActivity extends Activity 
{	  
	
	//---------- Attributes-----------------------------------------------------------------//
	public final static String EXTRA_MESSAGE2 = "com.app_example.MESSAGE";
	
	//---------- ON CREATE-----------------------------------------------------------------//
	public void onCreate(Bundle savedInstanceState)
	{    	
		super.onCreate(savedInstanceState);
	        
		// Get the message from the intent
		Intent intent = getIntent();
		//get the retrieve data contained within it
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		//transform in string
		try {
			JSONObject jsonObject;
		
			jsonObject = new JSONObject(message);
		
			int Id=jsonObject.getInt("folder_id");
			String filename=jsonObject.getString("name");
			int PositionName=filename.indexOf("\n");
	    	filename= filename.substring(0,PositionName);
			String url= "http://midas3.kitware.com/midas/download?items="+Id;
			
			downloadFile(new URL (url),filename);
		
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("file downloaded successfully in the sd card !");	
		setContentView(R.layout.activity_open_file);
		TextView textView2 = new TextView(this);
		textView2.setTextSize(30);
	 	textView2.setText("file downloaded successfully in the sd card !");
	  	setContentView(textView2);
	}
	
	//---------- DOWNLOAD FILE -----------------------------------------------------------------//
	public void downloadFile(URL u,String filename) throws IOException
	{
		
		//Open your local db as the input stream
		URLConnection uc = u.openConnection();
				
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("FILE NOT VALID.");
		}
		InputStream myInput = uc.getInputStream();
		// Path to the just created empty db
		System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
		String outFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+filename;
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
}
        
		
		
//------------------------------------------------------------------------------------------//
//------------------------------ USEFULL ---------------------------------------------------//
//------------------------------------------------------------------------------------------//		
		

	    

	