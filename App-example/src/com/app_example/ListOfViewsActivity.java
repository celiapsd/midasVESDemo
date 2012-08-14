package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

//------------------------------------------------------------------------------------------//
public class ListOfViewsActivity extends Activity 
{
	
	//---------- Attributes-----------------------------------------------------------------//
	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;
	public  List<Community> ListCommunity;	  
	public final static String EXTRA_MESSAGE3 = "com.app_example.MESSAGE";
	public final static int CODE_RETOUR=0;
	public final static String TAG="ListOfViewsActivity";
  /*Global Debug constant*/
	public static final boolean DEBUG = true;
	
	//---------- ON CREATE-----------------------------------------------------------------//
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
    if (ListOfViewsActivity.DEBUG) 
      {
      Log.d(TAG, "OnCreate()");
      }

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_views);
		MainActivity.activities.add(this);
		
		Intent intent = getIntent();// Get the message from the intent
		String str_jsonCommunity = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);//get the retrieve data contained within it
		 Log.d(TAG, "getintent");
		ListCommunity = get_Community_Into_List(str_jsonCommunity);//get the retrieve list linked with the json string
		 Log.d(TAG, "LIstcommunity is  null ? "+ListCommunity.isEmpty());
		// Find the ListView resource. 
		mainListView = (ListView) findViewById( R.id.mainListView );
		Log.d(TAG, "mainListView is  enabled ? "+mainListView.isEnabled());   
		
		//----------------------TO SEE THE string Names into a list---------------------------//
		String Names[] = new String[ListCommunity.size()];
		for(int i=0; i<ListCommunity.size(); i++)  
		{  
			Names[i] = ListCommunity.get(i).getName().toString();
		}	
		List<String> ListNames = new ArrayList<String>();
		ListNames.addAll( Arrays.asList(Names) );
		listAdapter = new ArrayAdapter<String>(this, R.layout.activity_simplerow, ListNames);	
			
		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter( listAdapter ); 
		Log.d(TAG, "mainListView is  enabled ? "+mainListView.isEnabled());       	
		Log.d(TAG, "waiting for a click");
		//------------------------------listening to single list item on click------------------------------//
		mainListView.setOnItemClickListener(new OnItemClickListener()
		{

			//---------------ON ITEM CLICK --------------------------------------------------//
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {	
		      if (ListOfViewsActivity.DEBUG) 
		        {
		        Log.d(TAG, "onItemCLick()");
		        }
				//-----retrieve the name of the community selected and send to SingleListItemActivity
				
				String name = ((TextView) view).getText().toString();
				setTitle(name);
				List<Community> ListCommunity = ListOfViewsActivity.this.ListCommunity;
				int fold_id = ListCommunity.get(position).getId_Folder();

				Folder child = new Folder();
				child.set_Folder_attributes(fold_id, name);
				
				
				//-----test Parcel----//		
				/*Intent i = new Intent(ListOfViewsActivity.this, SingleListItemActivity.class);
				Bundle b= new Bundle();		
				b.putParcelable("child", child);
				//i.getExtras().putParcelable("child", child);
				//i.putExtra("child", child);// sending data to new activity
				i.putExtras(b);
				startActivity(i);*/
				
				//----- test string -----//
				
				Intent in = new Intent(ListOfViewsActivity.this, SingleListItemActivity.class);
	        Log.d(TAG, "sending intent to SingleListItemActivity");

				//System.out.println("This shouldnt be empty: "+child.transFolderIntoJSONString());
				String childSt = new String(child.transFolderIntoJSONString());
				in.putExtra(EXTRA_MESSAGE3,childSt);
				startActivity(in);
		   	}
		});		
	}
	
	//---------- FUNCTIONS FOR LIFECYCLE ACTIVITY------------------------------------------//
    protected void onDestroy() 
      {
      if (ListOfViewsActivity.DEBUG) 
        {
        Log.d(TAG, "onDestroy()");
        }
 
    	super.onDestroy(); 
        
    
      }
      protected void onPause() {
        super.onPause(); }
      protected void onResume() {
        super.onResume(); }
      protected void onStart() {
        super.onStart(); }
      protected void onStop() {
        super.onStop(); } 
      
	//---------- GET COMMUNITY INTO LIST-----------------------------------------------------------------//
	public List<Community> get_Community_Into_List(String jsonString)
	  {
    if (ListOfViewsActivity.DEBUG) 
      {
      Log.d(TAG, "get_Community_Into_List()");
      }

		try 
		{
			JSONObject jsonObject = new JSONObject(jsonString); 
			JSONArray Array1 = jsonObject.optJSONArray("community");
			List<Community> communityList = new ArrayList<Community> ();
			
			for(int i=0; i<Array1.length(); i++)  
	        {  
				int Id = Array1.getJSONObject(i).getInt("id");
	            String Name = Array1.getJSONObject(i).getString("name").toString();
	            int FolderId = Array1.getJSONObject(i).getInt("folder_id");
				Community folder = new Community();
				folder.set_community_attributes(Id,Name,FolderId);
				communityList.add(folder);
				
	        }
			
			return communityList;
			
		}catch(JSONException e) {
			e.printStackTrace();
	    }
		return null;
	}
	
}

//------------------------------------------------------------------------------------------//
//------------------------------ USEFULL ---------------------------------------------------//
//------------------------------------------------------------------------------------------//
/*
	
	//---------- retrieve_ArraylistCommunity-----------------------------------------------//
	ArrayList<Community> retrieve_ArraylistCommunity(Map <Integer,Community> CommunityMap)
	{
		ArrayList<Community> ListCommunity = new ArrayList<Community>();
		Community A=new Community ();
		for (HashMap.Entry<Integer, Community> entry : CommunityMap.entrySet())
		{
		  	 A=entry.getValue();
			 ListCommunity.add(A);
		}
		return ListCommunity;	
	}
	
	
	

try 
		{
			JSONObject jsonObject1 = new JSONObject(str_jsonCommunity);
				
			JSONArray Array1=jsonObject1.optJSONArray("community");
			String Names[] = new String[Array1.length()];		
for(int i=0; i<Array1.length(); i++)  
{  
	Names[i]=Array1.getJSONObject(i).getString("name").toString();
}
this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_list_of_views, R.id.mainListView, Names));
} catch (JSONException e) 
{
	//e.printStackTrace();
}



in onItemClick 

Launching new Activity on selecting single List Item
Intent i = new Intent(getApplicationContext(), SingleListItemActivity.class);
System.out.println(com.getClass());
*/