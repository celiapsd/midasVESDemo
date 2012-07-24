package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.io.BufferedInputStream;
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
			
			getFile(new URL (url),filename);
			//downloadFile(url,filename);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getFile(URL u,String filename) throws IOException{
		
		//String FileName = filename;
		//Open your local db as the input stream
		URLConnection uc = u.openConnection();
		//String FileType = uc.getContentType();
		
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("Fichier non valide.");
		}
		InputStream myInput = uc.getInputStream();
		// Path to the just created empty db
		System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
		String outFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + filename;
		//Open the empty db as the output stream
		File out = new File(outFileName);
		OutputStream myOutput = null;
		//try{
			myOutput = new FileOutputStream(out);
		//}catch(Exception e){
			//e.printStackTrace();
		//}
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		try{
			while ((length = myInput.read(buffer))>0){
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
	
	public static void downloadFile(String url, String filename)throws IOException 
	{
	 
		final URLConnection conn = new URL(url).openConnection();
		

		
	    
		conn.connect();
	 
		final InputStream input = new BufferedInputStream(conn.getInputStream());
		final OutputStream output = new FileOutputStream(filename);
	 
		final byte data[] = new byte[1024];
	 
		int count;
		while ((count = input.read(data)) != -1)
			output.write(data, 0, count);
	 
		output.flush();
		output.close();
		input.close();
	 
	}

		
		
	
	}
        
		
		
//------------------------------------------------------------------------------------------//
//------------------------------ USEFULL ---------------------------------------------------//
//------------------------------------------------------------------------------------------//		
		

	    

	