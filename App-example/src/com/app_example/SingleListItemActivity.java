package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

 
//------------------------------------------------------------------------------------------//
public class SingleListItemActivity extends Activity
{
	public final static String EXTRA_MESSAGE3 = "com.app_example.MESSAGE";
	public int id;
	public String name;
	
	//---------- ON CREATE-----------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //this.setContentView(R.layout.single_list_item_view);
 
        /*//------Parcelable---//
        Folder child;		
		Bundle b = this.getIntent().getExtras();
		child = b.getParcelable("child");
		TextView productlabel = (TextView) findViewById(R.id.product_label);
        productlabel.setText("Folder : " + "\n" + " id : " +child.getFolder_id() + "\n" + " Name : " + child.getFolder_name());
         */
        //----String----//
        
        
        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra(ListOfViewsActivity.EXTRA_MESSAGE3);
        get_Parent(product);
        
        //txtProduct.setText(product);
     	get("http://midas3.kitware.com/midas/api/json?method=midas..folder.children&id="+id);
     	
   }   
  	public void get_Parent(String product) {
		
		try 
		{
			JSONObject jsonObject = new JSONObject(product); 
			id=Integer.parseInt(jsonObject.getString("folder_id").toString());
			name=jsonObject.getString("name").toString();
			
		} catch (JSONException e) {
				e.printStackTrace();
		}
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
    	SingleListItemActivity parent;
		private String sUrl;
		
		//-----Constructor--------------------------------------//
    	public HttpThread(SingleListItemActivity SingleListItemActivity,String sUrl) 
    	{
			this.parent = SingleListItemActivity;
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
				//typical appli : BufferedReader buff = new BufferedReader(new FileReader("file.java"));
				//InputStreamReader(InputStream in)
				
				while ((str = in.readLine()) != null) 
				{
					buff.append(str);
				}
			} catch (Exception e) {
					Log.e("HttpRequest", e.toString());
			}
			
			// call display message activity using our response
			String response = "{\"id\":"+"\""+SingleListItemActivity.this.id+"\","+"\"name\":"+"\""+SingleListItemActivity.this.name+"\"},"+buff.toString();
			Intent intent = new Intent(parent, LoopListActivity.class);
			intent.putExtra(EXTRA_MESSAGE3, response);
		    //putExtra()==takes a string as the key and the value in the second parameter.
		    startActivity(intent);
	    }
    }
} 

  //------------------------------------------------------------------------------------------//
  //------------------------------ USEFULL ---------------------------------------------------//
  //------------------------------------------------------------------------------------------//   

