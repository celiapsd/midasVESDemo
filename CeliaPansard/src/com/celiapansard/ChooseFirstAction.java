/*----------------------comments----------------------------------------------------------//
 * Name : Celia Pansard
 * Date of last modification : 08/07/2012
 * 
 * 
 * A ajouter :
 * 		Recherche de fichiers
 * 		
 * 		 
 * 
 * Problemes :
 * 		
 * 		Ne pas devoir cliquer 2 fois sur le logo Midas
 * 		Bundle d'objets au lieu de string pour le passage entre les activiteS
 * 		
 * 
 */
package com.celiapansard;

//----------------------------------------libraries----------------------------------------//


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


//------------------------------------------------------------------------------------------//
public class ChooseFirstAction extends Activity {

	public final static String EXTRA_MESSAGE = "com.celiapansard.MESSAGE";
	public static String sEmail;
	public static String sPassword;
	public static String Apikey;
	public static String Token;
	public static String UrlBeginning=null;
	public static ArrayList<Community> comList=new ArrayList<Community>();
	
	
	/**List of all the activities*/
	public static ArrayList<Activity> activities=new ArrayList<Activity>();
	
	/**Global Debug constant*/
	public static final boolean DEBUG = true;
	
	/**logging tag	*/
	public static String TAG = "ChooseFirstAction";
	
	/**---------- Default constructor----------------------------------------------------------------*/
	public ChooseFirstAction() {
		super();
	    if (ChooseFirstAction.DEBUG) {
	    	Log.d(TAG, "ChooseFirstAction()");
	    }
	}
	
	//---------- ON CREATE-----------------------------------------------------------------//
	/**
	   * Called on creation of homepage
	   * 
	   * @param savedInstanceState
	   *          Bundle of saved state used for re-creation
	   */
	public void onCreate(Bundle savedInstanceState) { 
		if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "onCreate()");
		}
		super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    //---------- ON CREATE OPTIONS MENU----------------------------------------------------//
    public boolean onCreateOptionsMenu(Menu menu) {
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "onCreateOptionsMenu()");
		}
    	getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }
  //---------- FUNCTIONS FOR LIFECYCLE ACTIVITY------------------------------------------//
    protected void onDestroy() {
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "onDestroy()");
		}
        super.onDestroy();
        activities.remove(this);}
    protected void onPause() {
        super.onPause(); }
      protected void onResume() {
        super.onResume(); }
      protected void onStart() {
        super.onStart(); }
      protected void onStop() {
        super.onStop(); } 
    
  /**---------- FINISH ALL THE ACTIVITIES LISTED------------------------------------------
   * 
   * * @param 
   * 
   * */
    public static void finishAll(){
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "finishAll()");
		}
        for(Activity activity:activities)
           activity.finish();
    }
    
  /**---------- CALL A FONCTION CORRESPONDING TO THE BUTTON CLICKED-------------------------
   * 
   * * @param View 
   * 
   * */
    public void ButtonOnClick(View v) 
 	{
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "ButtonOnClick()");
		}
 		  switch (v.getId())
	   	  {		    	                   
	    		case(R.id.ButtonGo):
	    			Log.d(TAG, "Go Button pushed");
	    			urlSearch(v);
	    			CheckLogin(v);
	    			accessUrl(v);
	    			break;
	    				
	    		case(R.id.buttonSearch):
	    			Log.d(TAG, "SEARCHING A FILE");
	    			Intent i=new Intent(ChooseFirstAction.this,FileExplorerActivity.class);
	    			startActivity (i);
	    			break;
	   	  }
 	}

	/**---------- ACCESS URL----------------------------------------------------------------
	 * 
	 * * @param View 
	 * 
	 * */
    public void accessUrl(View view) 
   {	
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "accesUrl()");
		}
    	String url = new String();
    	
    	if (UrlBeginning.contentEquals("http://midas3.kitware.com/midas")||UrlBeginning.contentEquals("http://slicer.kitware.com/midas3"))
    	{
   	 		url = UrlBeginning + "/api/json?method=midas.community.list";
    	}else{
    		url = UrlBeginning;
    	}
   	 	if(Token!=null)
   	 		url += "&token=" + Token;
   	 	Log.d(TAG, "URL : "+url);
   	 	   	 	
   	 	HttpThread Httpth = new HttpThread(url);
   	 	Thread th=new Thread (Httpth);
		th.start();
		Log.d(TAG, "Http Thread Started");
		try {
			th.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String Result=Httpth.getResponse();
		Log.d(TAG, "response ok");
	 	try {
			fillCommunityList(Result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	 	
	 	Intent intent=new Intent(ChooseFirstAction.this,ListOfViewsActivity.class); 
	 	Log.d(TAG, "intent sent to List of Views Activity");
	 	startActivity(intent);
   }	 	
	 	
    
  /**---------- FILL COMMUNITY LIST-----------------------------------------------------------------
   * 
   * @param  message 
   * 			String which contains the jSon string with communities
   * 			
   * */
    public void fillCommunityList(String message) throws JSONException
    {
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "fill_community_list()");
		} 
    	
    	JSONObject jsonObject;
    	jsonObject = new JSONObject(message);
    	JSONArray Array1=jsonObject.optJSONArray("data");
	    	
	    for(int i=0; i<Array1.length(); i++)  
	    {  
	    	String Name=Array1.getJSONObject(i).getString("name").toString();
	    	int Id=Array1.getJSONObject(i).getInt("community_id");
	    	int folder_id=Array1.getJSONObject(i).getInt("folder_id");
	    	Community Com= new Community ();
	    	Com.set_community_attributes(Id, Name,folder_id);
	    	comList.add(i, Com);
	     }
    }
    /**---------- URL SEARCH-----------------------------------------------------------------------
     * 
     * @param View
     * 
     * */
    public void urlSearch(View v) {
    	
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "urlSearch()");
		}
    	String url = (String)((EditText)this.findViewById(R.id.URL)).getText().toString();
    	
    	if (url.length()==0)
    	{
    		UrlBeginning="http://midas3.kitware.com/midas";
    		Log.d(TAG, "midas beginning by default");
    	}
    	else if (url.contentEquals("http://midas3.kitware.com"))
		{
			UrlBeginning="http://midas3.kitware.com/midas";
			Log.d(TAG, "midas beginning by user");
				
		}
		else if(url.equals("http://slicer.kitware.com"))
		{
			UrlBeginning="http://slicer.kitware.com/midas3";
			Log.d(TAG, "slicer beginning");
		}
		else if(url!=null)
		{
			UrlBeginning=url;
			Log.d(TAG, "precise URL");
    	}
		
		Toast.makeText(getApplicationContext(), url,Toast.LENGTH_SHORT).show();
		//accessUrl(v);
		
	}
    /**---------- CheckLogin -----------------------------------------------------------------------
     * 
     * @param View
     * */
    @SuppressLint("ParserError")
	public void CheckLogin(View v) 
    {
		
    	if (ChooseFirstAction.DEBUG) {
		      Log.d(TAG, "CheckLogin()");
		}
    	
	    EditText email = (EditText)this.findViewById(R.id.Email);
	    EditText password = (EditText)this.findViewById(R.id.Password);
	    sEmail = email.getText().toString();
	    sPassword = password.getText().toString();
	    
	    if(sEmail.length()==0||sPassword.length()==0)
	    {
	    	Log.d(TAG, "no email or password()");
	    	return;
	    }
	    
	    String url=UrlBeginning+"/api/json?method=midas.user.apikey.default";
	    postMethod pMApiKey=new postMethod(url);
	    Thread ThApiKey=new Thread(pMApiKey);
	    ThApiKey.start();
	    try {
	    	ThApiKey.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String response=pMApiKey.getResponse();
		   
	    if (retrieve_apikey_token(response,"apikey")=="Login fail")
        {
        	Toast.makeText(getApplicationContext(), "Login or password incorrect please try again",Toast.LENGTH_SHORT).show(); 
        	Token=null;
        	return;
        }
        // Login with the api key retrieved
        Apikey = retrieve_apikey_token(response,"apikey");
        
        
	    url= UrlBeginning+"/api/json?method=midas.login";	
	    postMethod pMLogin=new postMethod(url);
	    Thread ThLogin=new Thread(pMLogin);
	    ThLogin.start();
	    try {
			ThLogin.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	    response=pMLogin.getResponse();    
	    Token = retrieve_apikey_token(response,"token");
	    
	  //appear a message as a toast
        try
        {
        	if(Token!=null)
        	{
        		if(UrlBeginning.contains("slicer"))
              		Toast.makeText(getApplicationContext(), "login successfully on Slicer",Toast.LENGTH_SHORT).show(); 
        		else
        			Toast.makeText(getApplicationContext(), "login successfully on Midas",Toast.LENGTH_SHORT).show(); 
        	}
        	else
        	{
        		Toast.makeText(getApplicationContext(), "Login or password incorrect please try again",Toast.LENGTH_SHORT).show(); 
        	}
	         
        }
        catch(Exception e)
        {
        	Log.i("tagconvertstr",""+e.toString());
        }
	    
	        
	}
    
    /**----------------------- RETRIEVE APIKEY OR TOKEN ------------------------------------
     * 
     * @param message
     * 			
     * @param type
     * 			apikey or token
     * @return String
     * 			data corresponding to the information requested
     */
    public String retrieve_apikey_token(String message,String type) 
	{
		String data=new String();
		try 
		{
			message = message.substring(message.indexOf("{"),message.length());
			JSONObject jsonObject = new JSONObject(message); 
			JSONObject jsonObject2  = jsonObject.getJSONObject("data");
			data = jsonObject2.getString(type);
	        return data;   	
	         
		}catch(JSONException e) {
			e.printStackTrace();
			return "Login fail";
	    }
	}
    
    /**---------------------- CLASS POST METHOD-----------------------------------------
     * 
     * @author celia
     * 		use the httppost to get apikey and token to login
     *
     */
    class postMethod implements Runnable
	{
		//-----Attributes---------------------------------------//
		private String sUrl;
		private String response;
		
		//-----Constructor--------------------------------------//
    	public postMethod(String sUrl) 
      	{
  			this.sUrl = sUrl;
      	}
    	
    	//----- RUN ---------------------------------------------//
	    public  void run() 
	      {  
	    	if (ChooseFirstAction.DEBUG) 
	        	{
	    		Log.d(TAG, "runThread post method()");
	        	}
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppost = new HttpPost(sUrl);
          	try {	    	
            // Add your data
          		if (sUrl.contains("apikey.default"))
          		{
          			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
          			nameValuePairs.add(new BasicNameValuePair("email", ChooseFirstAction.sEmail));
          			nameValuePairs.add(new BasicNameValuePair("password",ChooseFirstAction.sPassword));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          		}
          		else if (sUrl.contains("login"))
          		{
          			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("appname","Default"));
                    nameValuePairs.add(new BasicNameValuePair("email", ChooseFirstAction.sEmail));
                    nameValuePairs.add(new BasicNameValuePair("apikey",ChooseFirstAction.Apikey));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          		}
				
            // Execute HTTP Post Request
            HttpResponse result = httpclient.execute(httppost);
            response=EntityUtils.toString(result.getEntity());
            
          	} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            }

		public String getResponse() {
			return response;
		}

			    
	 }

    
    
    
    
    
    
}
/*
//----------------------------------------------------------------------------------------------//
//-------------------------- USEFULL -----------------------------------------------------------//
//----------------------------------------------------------------------------------------------//

/*String str_jsonCommunity;
		try {
			str_jsonCommunity = fillCommunityList(Result);
		
		Intent intent = new Intent(ChooseFirstAction.this, ListOfViewsActivity.class);
		intent.putExtra(EXTRA_MESSAGE, str_jsonCommunity);
	    //putExtra()==takes a string as the key and the value in the second parameter.
	    
	    Log.d(TAG, "intent sent");
   	 	startActivity(intent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
//}
  
  //---------- CLASS HTTPTHREAD---------------------------------------------------------//
    /*private class HttpThread implements Runnable 
    {
    	
    	//-----Attributes---------------------------------------//

		private String sUrl;
		private String response;
		
		//-----Constructor--------------------------------------//
    	public HttpThread(ChooseFirstAction parent,String sUrl) 
    	{

			this.sUrl = sUrl;
			this.setResponse(null);

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
			setResponse(buff.toString());
			/*String str_jsonCommunity;
			try {
				str_jsonCommunity = fillCommunityList(response1);
			
			Intent intent = new Intent(parent, ListOfViewsActivity.class);
			intent.putExtra(EXTRA_MESSAGE, str_jsonCommunity);
			startActivity(intent);
			
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MAIN_THREAD=true;
			/*try {
				String str_jsonCommunity=make_json_Community_tree(response);
				Intent intent = new Intent(parent, ListOfViewsActivity.class);
				intent.putExtra(EXTRA_MESSAGE, str_jsonCommunity);
			    //putExtra()==takes a string as the key and the value in the second parameter.
			    startActivityForResult(intent, RETURN_CODE);		
						
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}
   	 	
		
		
	}*/
    /*public void get(String url) {
		// Create a new HttpClient and Post Header
   	 	HttpClient httpclient = new DefaultHttpClient();          	
   	 	HttpPost httppost = new HttpPost(url);  
     
   	 	// Execute HTTP Post Request
   	 	HttpResponse response;
   	 	String Result;
		try {
			Log.d(TAG, "ok");
			response1 = httpclient.execute(httppost);
			Log.d(TAG, "response "+response1);
   	 		Result= EntityUtils.toString(response1.getEntity());
   	 		Log.d(TAG, "response : "+Result);
   	 		fillCommunityList(Result);
   	 		
   	 		Intent intent=new Intent(ChooseFirstAction.this,ListOfViewsActivity.class);  	 		
   	 		intent.putExtra(getString(R.string.CommunityBundleKey), comList);
   	 		startActivity(intent);
   	 		
		} catch (ClientProtocolException e) {
			Log.d(TAG, "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG, "IOException");
			e.printStackTrace();
		}
		
		class HTTPThread implements Runnable{
   	 		public synchronized void run() {
       	  
   	 			getListChildren(url);
       	  
         }   
   	 	
   }
   
   
    Create a new HttpClient and Post Header
           HttpClient httpclient = new DefaultHttpClient();
                        	
            HttpPost httppost = new HttpPost(UrlBeginning+"/api/json?method=midas.user.apikey.default");
            
            String result = null;
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", sEmail));
                nameValuePairs.add(new BasicNameValuePair("password",sPassword));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                result = EntityUtils.toString(response.getEntity());

                //HttpEntity entity = response.getEntity();
                //is = entity.getContent();
                //System.out.println(result);

            } catch (ClientProtocolException e) {
            } catch (IOException e) {               
            }
            if (retrieve_apikey_token(result,"apikey")=="Login fail")
            {
            	Toast.makeText(getApplicationContext(), "Login or password incorrect please try again",Toast.LENGTH_SHORT).show(); 
            	Token=null;
            	return;
            }
            
            
            
            
	    
            // Login with the api key retrieved
            Apikey = retrieve_apikey_token(result,"apikey");
            HttpClient httpclient2 = new DefaultHttpClient();
            HttpPost httppost2 = new HttpPost(UrlBeginning+"/api/json?method=midas.login");
            String result2 = null;
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("appname","Default"));
                nameValuePairs.add(new BasicNameValuePair("email", sEmail));
                nameValuePairs.add(new BasicNameValuePair("apikey",Apikey));
                httppost2.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient2.execute(httppost2);
                result2 = EntityUtils.toString(response.getEntity());
                Token = retrieve_apikey_token(result2,"token");
                
            } catch (ClientProtocolException e) {
            } catch (IOException e) {               
            }
            
	        //appear a message as a toast
            try
            {
            	if(Token!=null)
            	{
            		if(UrlBeginning.contains("slicer"))
                  		Toast.makeText(getApplicationContext(), "login successfully on Slicer",Toast.LENGTH_SHORT).show(); 
            		else
            			Toast.makeText(getApplicationContext(), "login successfully on Midas",Toast.LENGTH_SHORT).show(); 
            	}
            	else
            	{
            		Toast.makeText(getApplicationContext(), "Login or password incorrect please try again",Toast.LENGTH_SHORT).show(); 
            	}
    	         
            }
            catch(Exception e)
            {
            	Log.i("tagconvertstr",""+e.toString());
            }

*/
