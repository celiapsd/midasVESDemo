package com.celiapansard;

//----------------------------------------libraries----------------------------------------//

import java.io.IOException;
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//------------------------------------------------------------------------------------------//
public class SingleListItemActivity extends Activity
{
	public final static String EXTRA_MESSAGE3 = "com.celiapansard.MESSAGE";
	public int id;
	public String name;
	//public Folder Child;
	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;
	public  List<Folder> ListChildren;	  
	public final static int CODE_RETOUR=0;
	 public final static String TAG="SingleListItemActivity";
	    /*Global Debug constant*/
		public static final boolean DEBUG = true;
	    	
	
	//---------- ON CREATE-----------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
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
        ChooseFirstAction.activities.add(this);
        // getting attached intent data
        String product = i.getStringExtra(ListOfViewsActivity.EXTRA_MESSAGE3);
        get_Parent(product);
        //Folder child=ListOfViewsActivity.child;
        //name=child.getFolder_name();
        //id=child.getFolder_id();
        
        setTitle(name);
        
        //txtProduct.setText(product);
        String url=ChooseFirstAction.UrlBeginning+"/api/json?method=midas.folder.children&id="+id;
   	 	if(ChooseFirstAction.Token!=null)
   	 		url+="&token="+ChooseFirstAction.Token;
      	getListChildren(url);
     	
     	setContentView(R.layout.activity_list_of_views);
		
		// Find the ListView resource. 
     	mainListView = (ListView) findViewById( R.id.mainListView );
		

		//----------------------TO SEE THE string Names into a list---------------------------//
		String Names[] = new String[this.ListChildren.size()];
		for(int i1 = 0; i1<this.ListChildren.size(); i1++)  
		{  
			Names[i1] = this.ListChildren.get(i1).getFolder_name().toString();
			System.out.println(Names[i1]);
		}	
		List<String> ListNames = new ArrayList<String>();
		ListNames.addAll( Arrays.asList(Names) );
		listAdapter = new ArrayAdapter<String>(SingleListItemActivity.this,R.layout.activity_simplerow, ListNames);	
			
		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter( listAdapter);
		
		//------------------------------listening to single list item on click------------------------------//
		mainListView.setOnItemClickListener(new OnItemClickListener()
		{
			
			//---------------ON ITEM CLICK --------------------------------------------------//
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {	
				
				//-----retrieve the name of the community selected and send to SingleListItemActivity
				
				String name = ((TextView) view).getText().toString();
				
				int fold_id = SingleListItemActivity.this.ListChildren.get(position).getFolder_id();

				Folder child = new Folder();
				child.set_Folder_attributes(fold_id, name);
				
				//----- test string -----//
				
				
				String childSt = new String(child.transFolderIntoJSONString());
				if(childSt.contains("download file"))
				{
					Intent i = new Intent(SingleListItemActivity.this,DownloadFileActivity.class);
					i.putExtra(EXTRA_MESSAGE3,childSt);
					startActivity(i);
				}
				else
				{
					Intent in = new Intent(SingleListItemActivity.this, SingleListItemActivity.class);
					in.putExtra(EXTRA_MESSAGE3,childSt);
					startActivity(in);
				}
		   	}
		});
		
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
  	public void get_Parent(String product) {
		
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

        	HttpThread HT = new HttpThread (sUrl);
        	Thread th = new Thread(HT);
        	th.start();
        	try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	String result = HT.getResponse();
        	String str_jsonChildren;
				try {
					str_jsonChildren = make_json_Children_tree(result,this.id);
					ListChildren = get_Children_Into_List(str_jsonChildren);//get the retrieve list linked with the json string
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
  	}
    
	 //---------- MAKE JSON CHILDREN TREE-----------------------------------------------------------------//	
		String make_json_Children_tree(String message, int parent_id)throws JSONException
 		{
 				  
 			JSONObject jsonObject = new JSONObject(message); 
 			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
 			
 			String Parent_id = String.valueOf(parent_id);		
 			String jsonChildren = "{\"parent_Id\":"+"\""+Parent_id+"\",\"children\":[";
 			
 			if(jsonObject2.getJSONArray("folders").length() != 0)
 			{
 				//jsonChildren+="\"children\":[";
 				
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
 			
 			return jsonChildren;
 			
 		}
		//---------- GET CHILDREN INTO LIST-----------------------------------------------------------------//
		public List<Folder> get_Children_Into_List(String jsonString) 
		{
			List<Folder> childrenList = new ArrayList<Folder> ();
			
			//--retrieve children--//
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString); 
				//List<Folder> childrenList=new ArrayList<Folder> ();
				
				if(jsonObject.has("children"))
	  			{
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
	
	/*@Override
	public void onBackPressed()
	{
		
	}*/
		
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