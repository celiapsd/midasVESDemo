
/**----------------------Comments----------------------------------------------------------//
 * Name : Celia Pansard
 * Date of last modification : 11/06/2012
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

 */
package com.kitware.KiwiViewer;

/*----------------------------------------libraries----------------------------------------*/

import java.util.ArrayList;
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
public class ChooseFirstActivity extends Activity 
{

  /**  To know if the user login correctly*/
	private int result;
	
	/**List of the communities in the database*/
	public static String [] communityList;
	
	/**List of the communities in the database*/
  //public static MidasResource [] communityList;
	
	/**Name of the current folder or item*/
  public static String currentName;
  
  /**Path of the current folder or item*/
  public static String currentPath;

	/** List of all the activities */
	public static ArrayList<Activity> activities = new ArrayList<Activity>();

	/** Global Debug constant */
	public static final boolean DEBUG = true;

	/** Global login constant */
  private static final int NOLOGIN_ENTERED = 0;
  /** Global login constant */
  private static final int LOGIN_INCORRECT = 1;
  /** Global login constant */
  private static final int LOGIN_SUCCESS = 2;

	/** logging tag */
	public static String TAG = "ChooseFirstActivity";
	
	
	/**
	 * ---------- Default constructor-----------------------------------------------------------------<br/>
	 * 
	 * @param none
	 */
	public ChooseFirstActivity() 
	  {
		super();
		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "ChooseFirstActivity()");
			}
	  }

	
	/**
	 * ------------OnCreate ---------------------------------------------------------------------------<br/>
	 * 
	 * Called on creation of homepage
	 * 
	 * @param savedInstanceState : Bundle of saved state used for re-creation
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	  {
		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "onCreate()");
		  }
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		new AutoCompleteTextViewListener(this);
		
	  }
	
	/**
  * ---------- PRIVATE CLASS AutoCompleteTextViewListener--------------------------------------------<br/>
  *
  * @Listener on autoCompleteTextView to manage the Url editText
  * (implements Text Watcher)
  * 
  * @param loader Activity ChooseFirstActivity
  **/
	private class AutoCompleteTextViewListener implements TextWatcher
	{
	
	  /** AutoCompleteTextView for URL */
    public AutoCompleteTextView myAutoComplete;
  
    /**---------Constructor of AutoCompleteTextViewListener---------------*/
  	 AutoCompleteTextViewListener(ChooseFirstActivity loader)
    	 {
    	 
    	 /* Array of Urls defined statically in string.xml*/
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
  	 
  	 /*
  	  * Methods to managed the AutoCompleteTextView (no need to be implemented)
  	  */
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
	
	
	/* ---------- onCreateOptionsMenu--------------------------------------------------------*/
	public boolean onCreateOptionsMenu(Menu menu) 
	  {
		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "onCreateOptionsMenu()");
		  }
		getMenuInflater().inflate(R.menu.homepage, menu);
		return true;
	  }

	/* ---------- FUNCTIONS FOR LIFECYCLE ACTIVITY-----------------------------------------------*/
	protected void onDestroy() 
	  {
		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "onDestroy()");
		  }
		super.onDestroy();
	  }

	protected void onPause() 
	  {
	  if (ChooseFirstActivity.DEBUG) 
      {
      Log.d(TAG, "onPause()");
      }
		super.onPause();
	  }

	protected void onResume() 
	  {
	  if (ChooseFirstActivity.DEBUG) 
      {
      Log.d(TAG, "onPause()");
      }
		super.onResume();
	  }

	protected void onStart() 
	  {
	  if (ChooseFirstActivity.DEBUG) 
      {
      Log.d(TAG, "onStart()");
      }
		super.onStart();
	  }

	protected void onStop() 
	  {
	  if (ChooseFirstActivity.DEBUG) 
      {
      Log.d(TAG, "onStop()");
      }
		super.onStop();
	  }

	/**
	 * ---------- ButtonOnClickGoMidas --------------------------------------------------------------<br/>
	 * 
	 * function called when the GO button is pushed
	 *
	 * @param v view
	 * */
	public void ButtonOnClickGoMidas(View v) 
	  {
		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "ButtonOnClickGoMidas()");
		  }
		communityList = null;
		urlSearch(v);
	  }
	/**
   * ---------- ButtonOnClickSearchFile ------------------------------------------------------------<br/>
   * 
   * function called when the Folder button is pushed
   *
   * @param v view
   * */
	public void ButtonOnClickSearchFile(View v) 
    {
    if (ChooseFirstActivity.DEBUG) 
      {
      Log.d(TAG, "ButtonOnClickSearchFile()");
      }
      Intent i = new Intent(this, FileExplorerActivity.class);
      startActivity(i);
    }
	/**
   * ---------- ButtonOnClickSearchViewer -----------------------------------------------------------<br/>
   * 
   * function called when the Viewer button is pushed
   *
   * @param v view
   * */
	  public void ButtonOnClickViewer(View v) 
    {
    if (ChooseFirstActivity.DEBUG) 
      {
      Log.d(TAG, "ButtonOnClickViewer()");
      }
    Intent intent = new Intent(this, ViewerActivity.class);
    startActivity(intent);
    }
  	

	
	/**
	 * ---------- urlSearch  ------------------------------------------------------------------------<br/>
	 * 
	 *   - get all the information entered by the user (email, password, url)<br/>
	 *   - fill the list of communities<br/>
	 *
	 *@param v view
	 * */
	public void urlSearch(View v) 
	  {

		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "urlSearch()");
		  }
		
		EditText email = (EditText) this.findViewById(R.id.Email);
		EditText password = (EditText) this.findViewById(R.id.Password);
		AutoCompleteTextView URL = (AutoCompleteTextView) this.findViewById(R.id.autocomplete_URL);
		String mEmail = email.getText().toString();
		String mPassword = password.getText().toString();
		String url = (String) URL.getText().toString();
       
		result = MidasToolsNative.init(url, mEmail, mPassword);
		checkLogin(result);
		Log.d(TAG, "checkLogin ok ");  
		communityList = MidasToolsNative.findCommunities();
		MidasResource[] resources = new MidasResource[communityList.length];
		for(int i=0;i<communityList.length;i++)
		  {
		  resources[i] = new MidasResource(i, communityList[i].toString(), MidasResource.Type.COMMUNITY);
		  //Log.d(TAG, communityList[i].toString());
		  }
		
		Intent intent = new Intent(ChooseFirstActivity.this,ListOfViewsActivity.class);
		intent.putExtra("BundleResourceCommunity", communityList);
		Log.d(TAG, "intent sent to List of Views Activity");
    startActivity(intent);
      
	  }
	/**
	 * ---------- CheckLogin----------------------------------------------------------------------------<br/>
	 * 
	 * With the email and the password entered in a textview
	 * 
	 * @param View
	 * 
	 * */

	public void checkLogin(int result) 
	  {

		if (ChooseFirstActivity.DEBUG) 
		  {
			Log.d(TAG, "CheckLogin()");
		  }
		
		switch (result)
		  {
		  case NOLOGIN_ENTERED:
		    Log.d(TAG, "no email or password()");
        Toast.makeText(getApplicationContext(), "No Login or password ", Toast.LENGTH_SHORT).show();
        break;
        
		  case LOGIN_INCORRECT:
		    Log.d(TAG, "Login or password incorrect");
		    Toast.makeText(getApplicationContext(), "Login or password incorrect", Toast.LENGTH_SHORT).show();
		    break;
		    
		  case LOGIN_SUCCESS:
  		  Log.d(TAG, "Login successfully");
        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
        break;
        
		  default:
		    Log.d(TAG, "return value of Login not expected");
		    break;
		  }	
	  }
	
//Maybe functions to delete 	
	
 /*----------------------getters and setters----------------------------------------------------- */
	  public static void setCurrentName(String myName)
      {
      currentName = myName;
      }
    public static String getCurrentName()
      {
      return currentName;
      }

	
  
  /**
   * ----------finishAll-----------------------------------------------<br/>
   * 
   * Finish all the activities listed
   * 
   *  @param none
   * 
   * */
   public static void finishAll() 
    {
    if (ChooseFirstActivity.DEBUG) 
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
     * ----------finishAllExceptParam ---------------------<br/>
     * 
     * Finish all the activities except the one put in parameter
     * 
     *  @param String ActName
     * 
     * */
    public static void finishAllExceptParam(String ActName) 
      {
      if (ChooseFirstActivity.DEBUG) 
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
     * ---------- testLauching---------------------<br/>
     * 
     * test if the activity in parameter has been launched 
     * 
     *  @param myActivityName string : name of the activity
     * 
     * */
    public static boolean testLauching(String myActivityName)
    {
    for (Activity activity : ChooseFirstActivity.activities) 
      {
      if (activity.getTitle().toString().equals(myActivityName))
        {
          return true;
        }
      }
    return false;
    }
}





