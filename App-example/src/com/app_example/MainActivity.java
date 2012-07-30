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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//------------------------------------------------------------------------------------------//
public class MainActivity extends Activity 
{	
	
	//---------- Attributes-----------------------------------------------------------------//
	public final static String EXTRA_MESSAGE = "com.app_example.MESSAGE";
	ImageButton imageButton;
	
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
    
       	  public void ButtonOnClick(View v) 
	      {
	    	  
	    	  switch (v.getId())
	    	  {
	    	  		case(R.id.midasButton):
	    	  			accessMidas(v);
	    	  			break;
	    	                   
	    			case(R.id.LoginButton):
	    				postData (v);
	    				
	    			
	    			case(R.id.OkButton):
	    				//urlSearch(v);
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
			Intent intent = new Intent(parent, DisplayMessageActivity.class);
			intent.putExtra(EXTRA_MESSAGE, response);
		    //putExtra()==takes a string as the key and the value in the second parameter.
		    startActivity(intent);
	    }
    
    }
    //------------------------------------------------------------------------------------------//
    //------------------------------------------------------------------------------------------//
    
	    @SuppressLint("ParserError")
		public void postData (View view)
	    {
	    	EditText email = (EditText)this.findViewById(R.id.Email);
	    	EditText password = (EditText)this.findViewById(R.id.Password);
	    	String sEmail = email.getText().toString();
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
                System.out.println(result);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
	       
	        //conversion de la réponse en chaine de caractère
            try
            {
    	         Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
    	         wait(200);  
            }
            catch(Exception e)
            {
            	Log.i("tagconvertstr",""+e.toString());
            }
	        
	    }
	    
}
    

