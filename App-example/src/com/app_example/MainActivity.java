package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

//------------------------------------------------------------------------------------------//
public class MainActivity extends Activity 
{	
	
	//---------- Attributes-----------------------------------------------------------------//
	public final static String EXTRA_MESSAGE = "com.example.myapp.MESSAGE";
	
	
	//---------- ON CREATE-----------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //---------- ON CREATE OPTIONS MENU----------------------------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
    
  	//---------- ACCESS WEB-----------------------------------------------------------------//
    public void accessWeb(View view) 
    {	
    	// Do something in response to button
    	this.get("http://midas3.kitware.com/midas/api/json?method=midas.community.list");
    }
    
  	//---------- GET ----------------------------------------------------------------------//
    public void get(String sUrl) 
    {
		HttpThread thread = new HttpThread(this, sUrl);
		Thread t = new Thread(thread);
		t.start();
	}
    
  	//---------- CLASS HTTPTHREAD---------------------------------------------------------//
    private class HttpThread implements Runnable 
    {
    	
    	//-----Attributes---------------------------------------//
    	MainActivity parent;
		private String sUrl;
		
		//-----Constructor--------------------------------------//
    	public HttpThread(MainActivity parent,String sUrl) 
    	{
			this.parent = parent;
			this.sUrl=sUrl;
    	}
    	
    	//----- RUN ---------------------------------------------//
	    public void run() 
		{    	
			String str;
			StringBuffer buff = new StringBuffer();
			try 
			{	
				URL url = new URL(this.sUrl);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				//typical appli : BufferedReader buf = new BufferedReader(new FileReader("file.java"));
				//InputStreamReader(InputStream in)
				
				while ((str = in.readLine()) != null) 
				{
					buff.append(str);
				}
			} catch (Exception e) {
					Log.e("HttpRequest", e.toString());
			}
			
			// call display message activity using our response
			String response = buff.toString();
			Intent intent = new Intent(parent, DisplayMessageActivity.class);
			intent.putExtra(EXTRA_MESSAGE, response);
		    //putExtra()==takes a string as the key and the value in the second parameter.
		    startActivity(intent);
	    }
    }
}
