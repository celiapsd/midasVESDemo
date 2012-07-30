package com.app_example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LoopListActivity extends Activity{


	//---------- Attributes-----------------------------------------------------------------//
	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;
	public  List<Folder> ListChildren;	  
	public final static String EXTRA_MESSAGE3 = "com.app_example.MESSAGE";
	
	//---------- ON CREATE-----------------------------------------------------------------//
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_views);
		
		try {
			Intent intent = getIntent();// Get the message from the intent
			String jsonParent = intent.getStringExtra(SingleListItemActivity.EXTRA_MESSAGE3);//get the retrieve data contained within it
			//get_Parent(jsonParent, Id,name);
			
			JSONObject jsonObject = new JSONObject(jsonParent);
			int Id = jsonObject.getInt("id");
			String name = jsonObject.getString("name");
	    	int PositionName = jsonParent.indexOf(",\"name\":\"");
	    	jsonParent = jsonParent.substring(PositionName+12+name.length(),jsonParent.length());
		
			String str_jsonChildren = make_json_Children_tree(jsonParent,Id);
		
			ListChildren = get_Children_Into_List(str_jsonChildren);//get the retrieve list linked with the json string
	
			// Find the ListView resource. 
			mainListView = (ListView) findViewById( R.id.mainListView );
		    
		
			//----------------------TO SEE THE string Names into a list---------------------------//
			String Names[] = new String[ListChildren.size()];
			for(int i = 0; i<ListChildren.size(); i++)  
			{  
				Names[i] = ListChildren.get(i).getFolder_name().toString();
				//System.out.println(Names[i]);
			}	
			List<String> ListNames = new ArrayList<String>();
			ListNames.addAll( Arrays.asList(Names) );
			listAdapter = new ArrayAdapter<String>(this, R.layout.activity_simplerow, ListNames);	
				
			// Set the ArrayAdapter as the ListView's adapter.
			mainListView.setAdapter( listAdapter ); 
           	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//------------------------------listening to single list item on click------------------------------//
		mainListView.setOnItemClickListener(new OnItemClickListener()
		{
			
			//---------------ON ITEM CLICK --------------------------------------------------//
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {	
				
				//-----retrieve the name of the community selected and send to SingleListItemActivity
				
				String name = ((TextView) view).getText().toString();
				List<Folder> ListChildren = LoopListActivity.this.ListChildren;
				int fold_id = ListChildren.get(position).getFolder_id();

				Folder child = new Folder();
				child.set_Folder_attributes(fold_id, name);
				
				//----- test string -----//
				
				
				//System.out.println("This shouldnt be empty: "+child.transFolderIntoJSONString());
				String childSt = new String(child.transFolderIntoJSONString());
				if(childSt.contains("download file"))
				{
					Intent i = new Intent(LoopListActivity.this,DownloadFileActivity.class);
					i.putExtra(EXTRA_MESSAGE3,childSt);
					startActivity(i);
				}
				else
				{
					Intent in = new Intent(LoopListActivity.this, SingleListItemActivity.class);
					in.putExtra(EXTRA_MESSAGE3,childSt);
					startActivity(in);
				}
		   	}
		});	
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
  				  				
  				//jsonChildren+="\"items\":[";
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
  			if(jsonObject2.getJSONArray("items").length() == 0&&jsonObject2.getJSONArray("folders").length() == 0){
  				jsonChildren += "\"children\":\"null\"";
  			}
  			jsonChildren += "}";
  			
  			return jsonChildren;
  			
  		}
		//---------- GET CHILDREN INTO LIST-----------------------------------------------------------------//
		public List<Folder> get_Children_Into_List(String jsonString) 
		{
			List<Folder> childrenList = new ArrayList<Folder> ();
			
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
				//System.out.println(jsonObject.isNull("items"));
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
		//---------- GET PARENT -----------------------------------------------------------------//
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
	
}