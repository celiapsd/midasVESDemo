/*----------------------comments----------------------------------------------------------//
 * Name : Celia Pansard
 * Date of last modification : 07/27/2012
 * 
 * 
 * A ajouter :
 * 		Recherche de fichiers
 * 		Lien pour ouvrir le fichier directement de l'application
 * 		Le nom du fichier en hqut de l'activite
 * 		Login/Mp
 * 		Demander si l'utilisateur a une carte sd --> sinon chercher le dossier approprie
 * 		bouton return accessible de toutes les pages pour revoir les communities
 * 		apres les chargement du premier fichier demander si il faut en telecharger un autre ou ouvrir celui ci
 * 
 * 
 * Problemes :
 * 		Ne pas afficher single litsActivity
 * 		Ne pas devoir cliauer 2 fois sur le logo Midas
 * 		Bundle d'objets au lieu de string pour le passage entre les activiteS
 * 		sTOCKAGE SIL N'Y A PAS DE CARTE SD PLANTE LE PROGRAMME
 * 
 */

package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

//------------------------------------------------------------------------------------------//
public class MainActivity extends Activity 
{	
	
	//---------- Attributes-----------------------------------------------------------------//
	public final static String EXTRA_MESSAGE = "com.app_example.MESSAGE";
	//ImageButton ImageButton;
	public static String sEmail;
	public static String Apikey;
	public static String Token;
	public static final int CODE_RETOUR = 0;
	private static final String TAG=MainActivity.class.getName();
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

	
	//---------- ON CREATE-----------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activities.add(this);

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
    
      public static void finishAll()
      {
          for(Activity activity:activities)
             activity.finish();
      }

       	  public void ButtonOnClick(View v) 
	      {
	    	  
	    	  switch (v.getId())
	    	  {
	    	  		case(R.id.midasButton):
	    	  			accessMidas(v);
	    	  			break;
	    	                   
	    			case(R.id.LoginButton):
	    				postData (v);
	    				break;
	    			
	    			case(R.id.OkButton):
	    				urlSearch(v);
	    				break;
	    				
	    			case(R.id.buttonSearch):
	    				Intent i=new Intent(MainActivity.this,FileExplorerActivity.class);
	    				startActivityForResult(i, CODE_RETOUR);
	    				break;
	      	  }
	    	} 
      
      
      
    //---------------------------------------------------------------------------------------//
    //------------------------------Midas image Button---------------------------------------//
    //---------------------------------------------------------------------------------------//
      
  		
	//---------- ACCESS WEB-----------------------------------------------------------------//
     public void accessMidas(View view)
    {	
    	// Do something in response to button
    	 String url="http://midas3.kitware.com/midas/api/json?method=midas.community.list";
    	 if(Token!=null)
    		 url+="&token="+Token;
    	this.get(url);
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
			this.sUrl = sUrl;
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
			try {
				String str_jsonCommunity=make_json_Community_tree(response);
				Intent intent = new Intent(parent, ListOfViewsActivity.class);
				intent.putExtra(EXTRA_MESSAGE, str_jsonCommunity);
			    //putExtra()==takes a string as the key and the value in the second parameter.
			    startActivityForResult(intent, CODE_RETOUR);		
						
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }
	    
    }
  //---------- MAKE JSON COMMUNITY TREE-----------------------------------------------------------------//
	    String make_json_Community_tree(String message)throws JSONException
	    {
			  
	    	JSONObject jsonObject = new JSONObject(message); 
	    	JSONArray Array1=jsonObject.optJSONArray("data");
	    	String jsonCommunity="{\"community\":[";
				
	    	for(int i=0; i<Array1.length(); i++)  
	    	{  
	    		String Name=Array1.getJSONObject(i).getString("name").toString();
	    		String Id=Array1.getJSONObject(i).getString("community_id").toString();
	    		String folder_id=Array1.getJSONObject(i).getString("folder_id").toString();
	    		jsonCommunity+="{\"id\":"+"\""+Id+"\","+"\"name\":"+"\""+Name+"\","+"\"folder_id\":"+"\""+folder_id+"\"}";
	    		if(i<(Array1.length()-1))
	    			jsonCommunity+=",";
	    	}
	    	jsonCommunity+="]}";
	    	return jsonCommunity;
	    }
    //------------------------------------------------------------------------------------------//
    //---------------------------LOGIN----------------------------------------------------------//
    //------------------------------------------------------------------------------------------//
    
	    @SuppressLint("ParserError")
		public void postData (View view)
	    {
	    	EditText email = (EditText)this.findViewById(R.id.Email);
	    	EditText password = (EditText)this.findViewById(R.id.Password);
	    	sEmail = email.getText().toString();
	        String sPassword = password.getText().toString();
	     // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://midas3.kitware.com/midas/api/json?method=midas.user.apikey.default");
            
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
            HttpPost httppost2 = new HttpPost("http://midas3.kitware.com/midas/api/json?method=midas.login");
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
            		Toast.makeText(getApplicationContext(), "login successfully",Toast.LENGTH_SHORT).show(); 
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

		public String retrieve_apikey_token(String result,String type) 
		{
			String data=new String();
			try 
			{
				result = result.substring(result.indexOf("{"),result.length());
				JSONObject jsonObject = new JSONObject(result); 
				JSONObject jsonObject2  = jsonObject.getJSONObject("data");
				data = jsonObject2.getString(type);
		        return data;   	
		         
			}catch(JSONException e) {
				e.printStackTrace();
				return "Login fail";
		    }
		}
		
		
		//------------------------------------------------------------------------------------------//
	    //---------------------------URL------------------------------------------------------------//
	    //------------------------------------------------------------------------------------------//
	    
		public void urlSearch(View v)
		{
			try {
				EditText url = (EditText)this.findViewById(R.id.URL);
				HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(url.getText().toString());
		        HttpResponse response = httpclient.execute(httppost);
				String result = EntityUtils.toString(response.getEntity());
				Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show(); 
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            catch(Exception e)
            {
            	Log.i("tagconvertstr",""+e.toString());
            }
		}
}
    

