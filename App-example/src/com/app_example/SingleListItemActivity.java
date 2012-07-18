package com.app_example;

//----------------------------------------libraries----------------------------------------//
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
//------------------------------------------------------------------------------------------//
public class SingleListItemActivity extends Activity
{
	//public final static String EXTRA_MESSAGE3 = "com.example.myapp.MESSAGE3";
	//---------- ON CREATE-----------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item_view);
 
        //TextView txtProduct = (TextView) findViewById(R.id.product_label);
        //Bundle b=this.getParcelable("child");
        //Parcel in = null;
		
        Folder child;		
		Bundle b = this.getIntent().getExtras();
		
		child = b.getParcelable("child");
		System.out.println(child);
		
		System.out.println(b);
		System.out.println(b.getParcelable("child"));
		
		
        
        //System.out.println(child.getFolder_id());
        TextView productlabel = (TextView) findViewById(R.id.product_label);
        //Intent i = getIntent();
        // getting attached intent data
        //String product = i.getStringExtra("product");
        // displaying selected product name
        
        //txtProduct.setText(product);
        productlabel.setText("Folder : " + "\n" + " id : " +child.getFolder_id() + "\n" + " Name : " + child.getFolder_name());
       
    }
} 

  //------------------------------------------------------------------------------------------//
  //------------------------------ USEFULL ---------------------------------------------------//
  //------------------------------------------------------------------------------------------//   
/*
    public void accessWeb(View view) {
      	
  		// Do something in response to button
  				
  		    	this.get("http://midas3.kitware.com/midas/api/json?method=midas.folder.children&id=",id);
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
  				    	
  					String str;
  					StringBuffer buff = new StringBuffer();
  					try {
  						URL url = new URL(this.sUrl);
  				 
  						BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
  						//typical appli : BufferedReader buf = new BufferedReader(new FileReader("file.java"));
  						//InputStreamReader(InputStream in)
  						
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
  					intent.putExtra(EXTRA_MESSAGE2, response);
  				    //putExtra()==takes a string as the key and the value in the second parameter.
  				    startActivity(intent);
  			    }
  		    }*/
