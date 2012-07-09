package com.app_example;

//import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



	public class DisplayMessageActivity extends Activity {
	    
	    public void onCreate(Bundle savedInstanceState) {
	    	
	        super.onCreate(savedInstanceState);
	        
	        // Get the message from the intent
	        Intent intent = getIntent();
	        //get the retrieve data contained within it
	        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	        //transform in string
	        
	        
	     // Get a string resource from your app's Resources
	        //String hello = getResources().getString(R.string.edit_message);
	        //or
	        
	     // Or supply a string resource to a method that requires a string
	        TextView textView = new TextView(this);
	        textView.setText("hello world");
	        
	        
	        // Create the text view
	        TextView textView2 = new TextView(this);
	        textView2.setTextSize(40);
	        textView2.setText(message);

	        setContentView(textView2);
	    }
	    
	    /*public static HttpData get(String sUrl) {
			HttpData ret = new HttpData();
			String str;
			StringBuffer buff = new StringBuffer();
			try {
				URL url = new URL(sUrl);
	 
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((str = in.readLine()) != null) {
					buff.append(str);
				}
				ret.content = buff.toString();
			} catch (Exception e) {
				Log.e("HttpRequest", e.toString());
			}
			return ret;
		}*/
	}

	