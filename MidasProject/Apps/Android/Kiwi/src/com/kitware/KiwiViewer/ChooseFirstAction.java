
/**----------------------Comments----------------------------------------------------------//
 * Name : Celia Pansard
 * Date of last modification : /2012
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
package com.kitware.KiwiViewer;

//----------------------------------------libraries----------------------------------------//

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

//------------------------------------------------------------------------------------------//
public class ChooseFirstAction extends Activity 
{

  /**  To know if the user login correctly*/
	private int result;
	
	/**List of the communities in the database*/
	public static String [] communityList;
	
	/**Name of the current folder or item*/
  public static String currentName;
  
  /**Path of the current folder or item*/
  public static String currentPath;

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
		new AutoCompleteTextViewListener(this);
		
	  }
	 /**
   * ---------- CLASS AutoCompleteTextViewListener ()-------------------------------------------------------------
  *
  *   
  **/
	private class AutoCompleteTextViewListener implements TextWatcher
	{
	
	  /** AutoCompleteTextView for URL */
    public AutoCompleteTextView myAutoComplete;
  
    /*------------------------------------------------------------*/
  	 AutoCompleteTextViewListener(ChooseFirstAction loader)
  	 {
  	 String[] URLS = getResources().getStringArray(R.array.url_array);
     myAutoComplete = (AutoCompleteTextView) loader.findViewById(R.id.autocomplete_URL);
     myAutoComplete.addTextChangedListener(this);
     
     ArrayAdapter<String> adapter = new ArrayAdapter<String>(loader, android.R.layout.simple_dropdown_item_1line, URLS);
     myAutoComplete.setAdapter(adapter);
     myAutoComplete.setOnItemClickListener(new OnItemClickListener() {
       public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
         }
       });
  	 } 
  	 public void beforeTextChanged(CharSequence s, int start, int count, int after)
  	   {
  	   }
  	 public void onTextChanged(CharSequence s, int start, int before, int count)
  	   {
       }
  	 public void afterTextChanged(Editable s)
       {      
       }
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
		//activities.remove(this);
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
		  communityList = null;
		  DownloadFileActivity.setFilename(null);
		  DownloadFileActivity.setPath(null);
	  }
	/**
         * ---------- FINISH ALL THE ACTIVITIES LISTED EXCEPT THE ONE PUT IN PARAM-----------------------
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
			if (activity.getTitle().toString() != ActName)
			{
				activity.finish();
			}
		  }
		//communityList.clear();

	  }

	/**
	 * ---------- CALL A FONCTION CORRESPONDING TO THE BUTTON CLICKED-------------------------
	 * 
	 * 
	 * 
	 * */
	public void ButtonOnClickGoMidas(View v) 
	  {
		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "ButtonOnClickGoMidas()");
		  }
		communityList = null;
		urlSearch(v);
	  }
	
	public void ButtonOnClickSearchFile(View v) 
    {
    if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "ButtonOnClickSearchFile()");
      }
      Intent i = new Intent(ChooseFirstAction.this,FileExplorerActivity.class);
      startActivity(i);
    }
	  public void ButtonOnClickViewer(View v) 
    {
    if (ChooseFirstAction.DEBUG) 
      {
      Log.d(TAG, "ButtonOnClickViewer()");
      }
    Intent intent = new Intent(ChooseFirstAction.this,ViewerActivity.class);
    startActivity(intent);
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
		
		EditText email = (EditText) this.findViewById(R.id.Email);
		EditText password = (EditText) this.findViewById(R.id.Password);
		AutoCompleteTextView URL = (AutoCompleteTextView) this.findViewById(R.id.autocomplete_URL);
		String mEmail = email.getText().toString();
		String mPassword = password.getText().toString();
		String url = (String) URL.getText().toString();
       
		result = MidasToolsNative.init(url,mEmail,mPassword);
		checkLogin(result);
       
		communityList=MidasToolsNative.findCommunities();
		MidasResource[] resources = new MidasResource[communityList.length];
		for(int i=0;i<communityList.length;i++)
		  {
		  resources[i] = new MidasResource(i, communityList[i].toString(), MidasResource.Type.COMMUNITY);
		  Log.d(TAG, communityList[i].toString());
		  }
		
		Intent intent = new Intent(ChooseFirstAction.this,ListOfViewsActivity.class);
		intent.putExtra("test test test", resources);
		Log.d(TAG, "intent sent to List of Views Activity");
    startActivity(intent);
      
	  }
	/**
	 * ---------- CheckLogin----------------------------------------------------------------------------
	 * 
	 * With the email and the password entered in a textview
	 * 
	 * @param View
	 * 
	 * */

	public void checkLogin(int result) 
	  {

		if (ChooseFirstAction.DEBUG) 
		  {
			Log.d(TAG, "CheckLogin()");
		  }
		//case 
		if (result == 0) 
		  {
			Log.d(TAG, "no email or password()");
			Toast.makeText(getApplicationContext(),"No Login or password ",Toast.LENGTH_SHORT).show();
			return;
		  }
		else if (result == 1) 
      {
      Log.d(TAG, "Login or password incorrect");
      Toast.makeText(getApplicationContext(),"Login or password incorrect",Toast.LENGTH_SHORT).show();
      return;
      }
		else if (result == 2) 
      {
      Log.d(TAG, "Login successfully");
      Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();
      return;
      }

	}

	
  public static boolean testLauching(String myActivityName)
    {
    for (Activity activity : ChooseFirstAction.activities) 
      {
      if (activity.getTitle().toString().equals(myActivityName))
        {
          return true;
        }
      }
    return false;
    }
  
  public static void setCurrentName(String myName)
    {
    currentName = myName;
    }
  public static String getCurrentName()
    {
    return currentName;
    }

}



