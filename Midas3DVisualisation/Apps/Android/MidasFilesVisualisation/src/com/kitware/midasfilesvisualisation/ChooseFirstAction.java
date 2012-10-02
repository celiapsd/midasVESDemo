
/**----------------------Comments----------------------------------------------------------//
 * Name : Celia Pansard
 * Date of last modification : 08/16/2012
 * 
 * Description 
 *    This application provides access to the Midas database by default.  
 *    It also provides access to the Slicer database when given the corresponding URL.  
 *    You must log in to access private folders, 
 *    but these are not necessary to the core functionality.  
 *    Click "Go" to navigate the communities and folders.  
 *    When you wish to download a folder you may choose to save it to your SD Card 
 *    or another directory.
 * 
 * 
 * 		
 * 		
 * 
 */
package com.kitware.midasfilesvisualisation;

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
public class ChooseFirstAction extends Activity 
  {

  /**  Email of the user entered in the textView*/
	private static String sEmail;
	
	/** Password entered by the user in the textView*/
	private static String sPassword;
	
	/** Apikey corresponding to a good pair of email and password to login*/
	private static String Apikey;
	
	/** Token key retrieve when you have your apikey*/ 
	private static String Token;
	
	/** the url beginning to access*/
	private static String UrlBeginning = null;
	
	/**List of the communities in the database*/
	public static ArrayList<Community> comList = new ArrayList<Community>();

	/** List of all the activities */
	public static ArrayList<Activity> activities = new ArrayList<Activity>();

	/** Global Debug constant */
	public static final boolean DEBUG = true;

	/** logging tag */
	public static String TAG = "ChooseFirstAction";

	/**
	 * ---------- Default constructor----------------
	 */
	public ChooseFirstAction() 
	  {
		super();
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "ChooseFirstAction()");
			comList.clear();
		  }
	  }

	
	/**
	 * ON CREATE : Called on creation of homepage
	 * 
	 * @param savedInstanceState
	 *            Bundle of saved state used for re-creation
	 */
	public void onCreate(Bundle savedInstanceState) 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "onCreate()");
		  }
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
	  }

	/* ---------- ON CREATE OPTIONS MENU----------------------------------------*/
	public boolean onCreateOptionsMenu(Menu menu) 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "onCreateOptionsMenu()");
		  }
		getMenuInflater().inflate(R.menu.homepage, menu);
		return true;
	  }

	/* ---------- FUNCTIONS FOR LIFECYCLE ACTIVITY--------------------------------*/
	protected void onDestroy() 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "onDestroy()");
		  }
		super.onDestroy();
		activities.remove(this);
	  }

	protected void onPause() 
	  {
	  if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "onPause()");
      }
		super.onPause();
	  }

	protected void onResume() 
	  {
	  if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "onPause()");
      }
		super.onResume();
	  }

	protected void onStart() 
	  {
	  if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "onStart()");
      }
		super.onStart();
	  }

	protected void onStop() 
	  {
	  if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "onStop()");
      }
		super.onStop();
	  }

	/**
	 * ---------- FINISH ALL THE ACTIVITIES LISTED------------------------------------------
	 * 
	 *  @param none
	 * 
	 * */
	public static void finishAll() 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "finishAll()");
		  }
		for (Activity activity : activities) 
		  {
			activity.finish();
			}
		comList.clear();
	  }
	/**
	 * ---------- FINISH ALL THE ACTIVITIES LISTED EXCEPT THE ONE PUT IN PARAM------------------------------------------
	 * 
	 *  @param String ActName
	 * 
	 * */
	public static void finishAllExceptParam(String ActName) 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "finishAllExceptParam()");
		  }
		for (Activity activity : activities) 
		  {
			if (activity.getLocalClassName() != ActName)
			{
				activity.finish();
			}
		  }
		comList.clear();
	  }

	/**
	 * ---------- CALL A FONCTION CORRESPONDING TO THE BUTTON CLICKED-------------------------
	 * 
	 * * @param View
	 * 
	 * */
	public void ButtonOnClick(View v) 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "ButtonOnClick()");
		  }
		switch (v.getId()) 
		  {
  		case (R.id.ButtonGo):
  			Log.d(TAG, "Go Button pushed");
  			comList.clear();
  			urlSearch(v);
  			CheckLogin(v);
  			accessUrl(v);
  			break;

  		case (R.id.buttonSearch):
  			Log.d(TAG, "SEARCHING A FILE");
  			Intent i = new Intent(ChooseFirstAction.this,FileExplorerActivity.class);
  			startActivity(i);
  			break;
		  }
	  }

	/**
	 * ---------- ACCESS URL----------------------------------------------------------------
	 * 
	 * * @param View
	 *       
	 * */
	public void accessUrl(View view) 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "accesUrl()");
		  }
		
		String url = new String();

		if (getUrlBeginning().contentEquals("http://midas3.kitware.com/midas")
				|| getUrlBeginning().contentEquals("http://slicer.kitware.com/midas3")) 
		  {
			url = getUrlBeginning() + "/api/json?method=midas.community.list";
		  } else 
		  {
			url = getUrlBeginning();
		  }
		
		if (getToken() != null)
		  {
			url += "&token=" + getToken();
		  }
		Log.d(TAG, "URL : " + url);

		HttpThread Httpth = new HttpThread(url);
		Thread th = new Thread(Httpth);
		th.start();
		Log.d(TAG, "Http Thread Started");
		
		try 
		  {
			th.join();	/*block the current thread until th had finished*/
		 } catch (InterruptedException e1) {
			e1.printStackTrace();
		  }
		
		String Result = Httpth.getResponse();
		Log.d(TAG, "response ok");
		
		try 
		  {
			fillCommunityList(Result);
		 } catch (JSONException e) {
			e.printStackTrace();
		  }

		Intent intent = new Intent(ChooseFirstAction.this,ListOfViewsActivity.class);
		Log.d(TAG, "intent sent to List of Views Activity");
		startActivity(intent);
	}

	/**
	 * ---------- FILL COMMUNITY LIST-----------------------------------------------------------------
	 * 
	 * @param message
	 *            String which contains the jSon string with communities
	 * 
	 * */
	public void fillCommunityList(String message) throws JSONException 
	{
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "fill_community_list()");
		  }

		JSONObject jsonObject;
		jsonObject = new JSONObject(message);
		JSONArray Array1 = jsonObject.optJSONArray("data");

		for (int i = 0; i < Array1.length(); i++) 
		  {
			String Name = Array1.getJSONObject(i).getString("name").toString();
			int Id = Array1.getJSONObject(i).getInt("community_id");
			int folder_id = Array1.getJSONObject(i).getInt("folder_id");
			Community Com = new Community();
			Com.set_community_attributes(Id, Name, folder_id);
			comList.add(i, Com);	
		  }
		
		if(comList == null){
			Log.e(TAG, "Community List is not filled correctly--> empty");
		}
	 }

	/**
	 * ---------- URL SEARCH------------------------------------------------------
	 * 
	 * 
	 * @param View
	 * 
	 * */
	public void urlSearch(View v) 
	  {

		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "urlSearch()");
		  }
		String url = (String) ((EditText) this.findViewById(R.id.URL)).getText().toString();

		if (url.length() == 0) 
		  {
			setUrlBeginning("http://midas3.kitware.com/midas");
			Log.d(TAG, "midas beginning by default");
			
		} else if (url.contentEquals("http://midas3.kitware.com")) 
		  {
			setUrlBeginning("http://midas3.kitware.com/midas");
			Log.d(TAG, "midas beginning by user");

		} else if (url.equals("http://slicer.kitware.com")) 
		  {
			setUrlBeginning("http://slicer.kitware.com/midas3");
			Log.d(TAG, "slicer beginning");
			
		} else if (url != null) 
		  {
			setUrlBeginning(url);
			Log.d(TAG, "precise URL");
		}

		if (getUrlBeginning() == null)
		{
			Log.e(TAG, "error searching url, UrlBeginning--> empty");
		}
	}

	/**
	 * ---------- CheckLogin----------------------------------------------------------------------------
	 * 
	 * With the email and the password entered in a textview
	 * 
	 * @param View
	 * 
	 * */
	@SuppressLint("ParserError")
	public void CheckLogin(View v) 
	  {

		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "CheckLogin()");
		  }
		
		/*get the email and the password entered*/
		EditText email = (EditText) this.findViewById(R.id.Email);
		EditText password = (EditText) this.findViewById(R.id.Password);
		setEmail(email.getText().toString());
		setPassword(password.getText().toString());

		if (getEmail().length() == 0 || getPassword().length() == 0) 
		  {
			Log.d(TAG, "no email or password()");
			return;
		  }

		/*retrieve apikey*/
		String url = getUrlBeginning() + "/api/json?method=midas.user.apikey.default";
		postMethod pMApiKey = new postMethod(url);
		Thread ThApiKey = new Thread(pMApiKey);
		ThApiKey.start();
		try 
		  {
			ThApiKey.join();
		} catch (InterruptedException e1) 
		  {
			e1.printStackTrace();
		}
		String response = pMApiKey.getResponse();

		if (retrieve_apikey_token(response, "apikey") == "Login fail") 
		  {
			Toast.makeText(getApplicationContext(),"Login or password incorrect please try again",Toast.LENGTH_SHORT).show();
			setToken(null);
			return;
		}
		
		/* Login with the api key retrieved*/
		setApikey(retrieve_apikey_token(response, "apikey"));

		url = getUrlBeginning() + "/api/json?method=midas.login";
		postMethod pMLogin = new postMethod(url);
		Thread ThLogin = new Thread(pMLogin);
		ThLogin.start();
		try 
		  {
			ThLogin.join();
		} catch (InterruptedException e1) 
		  {
			e1.printStackTrace();
		}
		response = pMLogin.getResponse();
		setToken(retrieve_apikey_token(response, "token"));

		// appear a message as a toast
		try 
		  {
			if (getToken() != null) 
			  {
				if (getUrlBeginning().contains("slicer"))
				  {
					Toast.makeText(getApplicationContext(), "login successfully on Slicer", Toast.LENGTH_SHORT).show();
				}else
				  {
					Toast.makeText(getApplicationContext(),"login successfully on Midas", Toast.LENGTH_SHORT).show();
				  }
			} else {
				Toast.makeText(getApplicationContext(),"Login or password incorrect please try again",Toast.LENGTH_SHORT).show();
			  }

		} catch (Exception e) 
		  {
			Log.i("tagconvertstr", "" + e.toString());
		}

	}

	/**
	 * ----------------------- RETRIEVE APIKEY OR TOKEN------------------------------------
	 * 
	 * 
	 * @param message
	 * 
	 * @param type
	 *            apikey or token
	 * @return String data corresponding to the information requested
	 */
	public String retrieve_apikey_token(String message, String type) 
	  {

    if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "retrieve_apikey_token()");
      }
		String data = new String();
		try 
		  {
			message = message.substring(message.indexOf("{"), message.length());
			JSONObject jsonObject = new JSONObject(message);
			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
			data = jsonObject2.getString(type);
			return data;

		} catch (JSONException e) 
		  {
			return "Login fail";		
		  }
	  }

	/**
	 * ---------------------- CLASS POST METHOD-----------------------------------------
	 * 
	 *   use the httppost to get apikey and token to login
	 * 
	 */
	class postMethod implements Runnable 
	{
		/* -----Attributes---------------------------------------*/
		private String sUrl;
		private String response;

		/** 
		 * -----Constructor--------------------------------------
		 * */
		public postMethod(String sUrl) {
			this.sUrl = sUrl;
			this.response=null;
		}

		/** ----- RUN ---------------------------------------------*/
		public void run() 
		  {
			if (ChooseFirstAction.DEBUG) 
			  {
				Log.d(TAG, "runThread post method()");
			  }
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(sUrl);
			try 
			  {
	
			  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		     /*Add your data*/
				if (sUrl.contains("apikey.default")) 
				  {
					nameValuePairs.add(new BasicNameValuePair("email",getEmail()));
					nameValuePairs.add(new BasicNameValuePair("password",getPassword()));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					nameValuePairs.clear();
					
				} else if (sUrl.contains("login")) 
				  {
					nameValuePairs.add(new BasicNameValuePair("appname","Default"));
					nameValuePairs.add(new BasicNameValuePair("email",getEmail()));
					nameValuePairs.add(new BasicNameValuePair("apikey",getApikey()));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					nameValuePairs.clear();
				}

				/*Execute HTTP Post Request*/
				HttpResponse result = httpclient.execute(httppost);
				setResponse(EntityUtils.toString(result.getEntity()));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		public String getResponse() 
		  {
			return response;
		  }
		public void setResponse(String myResponse)
		  {
		  response = myResponse;
		  }
	}
	
	public static String getEmail()
	  {
      return sEmail;
	  }
	public static void setEmail(String myEmail)
    {
    sEmail = myEmail;
    }
	public static String getApikey()
    {
      return Apikey;
    }
	public static void setApikey(String myApikey)
    {
    Apikey = myApikey;
    }
	public static String getPassword()
    {
      return sPassword;
    }
	public static void setPassword(String myPassword)
    {
    sPassword = myPassword;
    }
	public static String getToken()
    {
      return Token;
    }
  public static void setToken(String myToken)
    {
    Token = myToken;
    }
  public static String getUrlBeginning()
    {
      return UrlBeginning;
    }
  public static void setUrlBeginning(String myUrlBeginning)
    {
    UrlBeginning = myUrlBeginning;
    }

}

