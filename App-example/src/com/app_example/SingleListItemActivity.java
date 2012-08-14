package com.app_example;

//----------------------------------------libraries----------------------------------------//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//------------------------------------------------------------------------------------------//
public class SingleListItemActivity extends Activity
{
	public final static String EXTRA_MESSAGE3 = "com.app_example.MESSAGE";
	public static int id;
	public String name;
	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;
	public static List<Folder> ListChildren;	  
	public final static int CODE_RETOUR=0;
	public static String url;
	 public final static String TAG="SingleListItemActivity";
   /*Global Debug constant*/
	 public static final boolean DEBUG = true;
     
	
	//---------- ON CREATE-----------------------------------------------------------------//
    @SuppressLint("ParserError")
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
      if (SingleListItemActivity.DEBUG) 
        {
        Log.d(TAG, "OnCreate()");
        }
        super.onCreate(savedInstanceState);
        
        /*//------Parcelable---//
        Folder child;		
		Bundle b = this.getIntent().getExtras();
		child = b.getParcelable("child");
		TextView productlabel = (TextView) findViewById(R.id.product_label);
        productlabel.setText("Folder : " + "\n" + " id : " +child.getFolder_id() + "\n" + " Name : " + child.getFolder_name());
         */
        
        //----String----//
        Intent i = getIntent();
        MainActivity.activities.add(this);
        // getting attached intent data
        String product = i.getStringExtra(ListOfViewsActivity.EXTRA_MESSAGE3);
        Log.d(TAG, "get message : " + product);
        get_Parent(product);
        setTitle(name);
        
        //txtProduct.setText(product);
        url=MainActivity.UrlBeginning+"/api/json?method=midas.folder.children&id="+id;
   	 	if(MainActivity.Token!=null)
   	 	  {
   	 		url+="&token="+MainActivity.Token;
   	 	  }
   	 	Log.d(TAG, "get (url) with url="+url);
   	 	get(url);
   	 	
   	 
      	//getListChildren(url);
     	
     	setContentView(R.layout.activity_list_of_views);
     	Log.d(TAG, "setcontentview");
		// Find the ListView resource. 
     	mainListView = (ListView) findViewById( R.id.mainListView );
     	Log.d(TAG, "mainListView is enabled ?"+mainListView.isEnabled());

		//----------------------TO SEE THE string Names into a list---------------------------//
		String Names[] = new String[SingleListItemActivity.ListChildren.size()];
		for(int i1 = 0; i1<SingleListItemActivity.ListChildren.size(); i1++)  
		{  
			Names[i1] = SingleListItemActivity.ListChildren.get(i1).getFolder_name().toString();
			System.out.println(Names[i1]);
		}	
		List<String> ListNames = new ArrayList<String>();
		ListNames.addAll( Arrays.asList(Names) );
		listAdapter = new ArrayAdapter<String>(SingleListItemActivity.this,R.layout.activity_simplerow, ListNames);	
			
		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter( listAdapter);
		Log.d(TAG, "Waiting for a click");
		//------------------------------listening to single list item on click------------------------------//
		mainListView.setOnItemClickListener(new OnItemClickListener()
		{
			
			//---------------ON ITEM CLICK --------------------------------------------------//
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {	
		      if (SingleListItemActivity.DEBUG) 
  	        {
  	        Log.d(TAG, "OnItemCLick()");
  	        }
				//-----retrieve the name of the community selected and send to SingleListItemActivity
				
		      String name = ((TextView) view).getText().toString();
				
		      int fold_id = SingleListItemActivity.ListChildren.get(position).getFolder_id();

		      Folder child = new Folder();
		      child.set_Folder_attributes(fold_id, name);
		      Log.d(TAG, "string Child");
				  //----- test string -----//
				
				
				String childSt = new String(child.transFolderIntoJSONString());
				if(childSt.contains("download file"))
				{
				
	        Log.d(TAG, "Sent intent to DownloadFileActivity");
	        
					Intent i = new Intent(SingleListItemActivity.this,DownloadFileActivity.class);
					i.putExtra(EXTRA_MESSAGE3,childSt);
					startActivity(i);
				}
				else
				{
				  Log.d(TAG, "Sent intent to SingleListItemActivity");
					Intent in = new Intent(SingleListItemActivity.this, SingleListItemActivity.class);
					in.putExtra(EXTRA_MESSAGE3,childSt);
					startActivity(in);
				}
		   	}
		});
		
	}
    private void get(String url)
      {
      if (SingleListItemActivity.DEBUG) 
        {
        Log.d(TAG, "get(url)");
        }
      HttpThread Ht = new HttpThread(url);
      Log.d(TAG, "starting a new thread");
      Thread t= new Thread(Ht);
      t.start();
      try
        {
        t.join();
        } catch (InterruptedException e)
        {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
      
      
      }
    private class HttpThread implements Runnable 
    {
    	
    	//-----Attributes---------------------------------------//
    	
		private String sUrl;
		
		//-----Constructor--------------------------------------//
    	public HttpThread(String sUrl) 
    	{
			
			this.sUrl = sUrl;
    	}
    	
    	//----- RUN ---------------------------------------------//
	    public  void run() 
	      {    
	      if (SingleListItemActivity.DEBUG) 
          {
          Log.d(TAG, "run thread()");
          }
  			String str;
  			StringBuffer buff = new StringBuffer();
  			try 
  			{	
  				URL url = new URL(this.sUrl);
  				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
  				//typical appli : BufferedReader buf = new BufferedReader(new FileReader("file.java"));
  			  Log.d(TAG, "Buffer readed()");
           
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
  			//try {
  				//getListChildren(response);
  			  String str_jsonChildren;
          try
            {
            str_jsonChildren = make_json_Children_tree(response,SingleListItemActivity.id);
            Log.d(TAG, "str_jsonChildren made : "+str_jsonChildren);
            SingleListItemActivity.ListChildren = get_Children_Into_List(str_jsonChildren);//get the retrieve list linked with the json string
            Log.d(TAG, "listchildren is e ?  "+SingleListItemActivity.ListChildren.isEmpty());
            
            } catch (JSONException e)
            {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
          
  				//String str_jsonCommunity=make_json_Community_tree(response);
  				/*Intent intent = new Intent(SingleListItemActivity.this, ListOfViewsActivity.class);
  				intent.putExtra(EXTRA_MESSAGE3, str_jsonCommunity);
  			    //putExtra()==takes a string as the key and the value in the second parameter.
  			    startActivityForResult(intent, CODE_RETOUR);*/		
  						
  			/*} catch (JSONException e) {
  				
  				e.printStackTrace();
  			}*/
			
	    }
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
  
                   
         
    //---------- GET PARENT-----------------------------------------------------------------//
  	public void get_Parent(String product) 
  	  {
  	  if (SingleListItemActivity.DEBUG) 
        {
        Log.d(TAG, "get_Parent()");
        }
  		try 
  		{
  			JSONObject jsonObject = new JSONObject(product); 
  			id = jsonObject.getInt("folder_id");
  			name = jsonObject.getString("name").toString();
  			
  		} catch (JSONException e) {
  				e.printStackTrace();
  		}
	}
  	
  	//---------- GET -----------------------------------------------------------------//
  	public void getListChildren(String sUrl) 
  	{
  		/*HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(sUrl);
        
        String result = null;
                    // Execute HTTP Post Request
        HttpResponse response;*/
  	  if (SingleListItemActivity.DEBUG) 
  	    {
  	    Log.d(TAG, "getListChildren()");
  	    }
        try {
			/*response = httpclient.execute(httppost);
			result = EntityUtils.toString(response.getEntity());*/

            //HttpEntity entity = response.getEntity();
            //is = entity.getContent();
            //System.out.println(result);
        	
        	String str_jsonChildren;
        	str_jsonChildren = make_json_Children_tree(sUrl,SingleListItemActivity.id);
        	SingleListItemActivity.ListChildren = get_Children_Into_List(str_jsonChildren);//get the retrieve list linked with the json string
				
				
		/*} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
			
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
    
	 //---------- MAKE JSON CHILDREN TREE-----------------------------------------------------------------//	
		String make_json_Children_tree(String message, int parent_id)throws JSONException
 		{
 		  if (SingleListItemActivity.DEBUG) 
 		    {
 		    Log.d(TAG, "make_json_Children_tree()");
 		    }	  
 			JSONObject jsonObject = new JSONObject(message); 
 			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
 			
 			String Parent_id = String.valueOf(parent_id);		
 			String jsonChildren = "{\"parent_Id\":"+"\""+Parent_id+"\",\"children\":[";
 			
 			if(jsonObject2.getJSONArray("folders").length() != 0)
 			{
 				//jsonChildren+="\"children\":[";
 			
        Log.d(TAG, "There are folders");
        
	  			JSONArray Array1 = jsonObject2.optJSONArray("folders");
	  			
	  			
	  			for(int i=0; i<Array1.length(); i++)  
	  			{  
	  				String Name = Array1.getJSONObject(i).getString("name").toString();
	  				String Id = Array1.getJSONObject(i).getString("folder_id").toString();		
	  				jsonChildren += "{\"id\":"+"\""+Id+"\","+"\"name\":"+"\""+Name+"\"}";
	  				if(i<(Array1.length()-1))
	  					jsonChildren+=",";
	  			}
 			}
 			
 			jsonChildren += "],\"items\":[";
 			
 			if(jsonObject2.getJSONArray("items").length() != 0)
 			  {
 			  Log.d(TAG, "There are items");
 				JSONArray Array1 = jsonObject2.optJSONArray("items");
 				
 				for(int i=0; i<Array1.length(); i++)  
	  			{  
	  				String Name = Array1.getJSONObject(i).getString("name").toString();
	  				String Id = Array1.getJSONObject(i).getString("item_id").toString();		
	  				jsonChildren += "{\"item_id\":"+"\""+Id+"\","+"\"item_name\":"+"\""+Name+"\"}";
	  				if(i<(Array1.length()-1))
	  					jsonChildren += ",";
	  			}
 			}
 			jsonChildren += "]";
 			if(jsonObject2.getJSONArray("items").length() == 0 && jsonObject2.getJSONArray("folders").length() == 0){
 				jsonChildren += "\"children\":\"null\"";
 			}
 			jsonChildren += "}";
 			Log.d(TAG, "jsonChildren = " + jsonChildren);
 			
 			return jsonChildren;
 			
 		}
		//---------- GET CHILDREN INTO LIST-----------------------------------------------------------------//
		public List<Folder> get_Children_Into_List(String jsonString) 
		{
		  if (SingleListItemActivity.DEBUG) 
		    {
		    Log.d(TAG, "get_Children_Into_List()");
		    }   
			List<Folder> childrenList = new ArrayList<Folder> ();
			
			//--retrieve children--//
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString); 
				//List<Folder> childrenList=new ArrayList<Folder> ();
				
				if(jsonObject.has("children"))
	  			{
	  			
	          Log.d(TAG, "hasChildren");
	            
					JSONArray Array1 = jsonObject.optJSONArray("children");
					
					for(int i=0; i<Array1.length(); i++)  
			      {  
						int Id = Array1.getJSONObject(i).getInt("id");
			      String Name = Array1.getJSONObject(i).getString("name");
						Folder folder = new Folder();
						folder.set_Folder_attributes(Id,Name);
						childrenList.add(folder);
			      }
				}
			}catch(JSONException e) {
				e.printStackTrace();
				return childrenList;
			}
			
			//--retrieve items--//
			try 
			{	
				JSONObject jsonObject = new JSONObject(jsonString); 
				if(!jsonObject.isNull("items"))
	  			{
	  			Log.d(TAG, "has items");
					JSONArray Array1 = jsonObject.optJSONArray("items");
					
					for(int i=0; i<Array1.length(); i++)  
			        {  
						int Item_id = Array1.getJSONObject(i).getInt("item_id");
			            String Item_Name = Array1.getJSONObject(i).getString("item_name").toString()+"\n"+"				download file";			            
						Folder folder = new Folder();
						folder.set_Folder_attributes(Item_id,Item_Name);
						childrenList.add(folder);
			        }
	  			}
				return childrenList;
				
			}catch(JSONException e) {
				e.printStackTrace();
				return childrenList;
		    }
			
		}
			
} 

  //------------------------------------------------------------------------------------------//
  //------------------------------ USEFULL ---------------------------------------------------//
  //------------------------------------------------------------------------------------------//   

/*//---------- GET PARENT -----------------------------------------------------------------//
		public void get_Parent(String jsonParent,int Id, String name) {
			try 
			{
				
				JSONObject jsonObject = new JSONObject(jsonParent);
				Id = jsonObject.getInt("id");
				name = jsonObject.getString("name");
		    	int PositionName = jsonParent.indexOf(",\"name\":\"");
		    	jsonParent = jsonParent.substring(PositionName+12+name.length(),jsonParent.length());//remove first chars
				
			} catch (JSONException e) {
					e.printStackTrace();
			}
			
		}
*/