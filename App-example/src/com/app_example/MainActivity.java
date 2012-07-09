package com.app_example;

//import android.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
//import android.view.MenuItem;
//import android.support.v4.app.NavUtils;
//import android.net.*;


public class MainActivity extends Activity {
	
	
	public final static String EXTRA_MESSAGE = "com.example.myapp.MESSAGE";//?????
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    //second activity
    /** Called when the user selects the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    	
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	//An Intent==object that provides runtime binding between separate components
    	//used to start another activity called DisplayMessageActvity
    	//the constructor-->two parameters:A Context(this) and The Class of the app component

    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	//findViewById()==carry a bundle of data to the activity 
    	
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	//putExtra()==takes a string as the key and the value in the second parameter.
    	startActivity(intent);
    }
    public void accessWeb(View view) {
    	
// Do something in response to button
    	//Intent intent = new Intent(this, HttpRequest.class);
    	
    	//An Intent==object that provides runtime binding between separate components
    	//used to start another activity called DisplayMessageActvity
    	//the constructor-->two parameters:A Context(this) and The Class of the app component
    	this.get("http://midas3.kitware.com/midas/api/json?method=midas.community.list");
    	
    	
    	
    }
    public void get(String sUrl) {
		HttpThread thread = new HttpThread(this, sUrl);
		Thread t = new Thread(thread);
		t.start();
	}
    
    private class HttpThread implements Runnable {
    	MainActivity parent;
		private String sUrl;
    	public HttpThread(MainActivity parent,String sUrl) {
			this.parent = parent;
			this.sUrl=sUrl;
		}
    	
	    public void run() {
	    	//HttpData ret = new HttpData();
			String str;
			StringBuffer buff = new StringBuffer();
			try {
				URL url = new URL(this.sUrl);
	 
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((str = in.readLine()) != null) {
					buff.append(str);
				}
				//ret.content = buff.toString();
			} catch (Exception e) {
				Log.e("HttpRequest", e.toString());
			}
			// call display message activity
			// using our response
			String response = buff.toString();
			Intent intent = new Intent(parent, DisplayMessageActivity.class);
			intent.putExtra(EXTRA_MESSAGE, response);
	    	//putExtra()==takes a string as the key and the value in the second parameter.
	    	startActivity(intent);
	    }
    }
}
