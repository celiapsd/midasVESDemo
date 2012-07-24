package com.app_example;

//----------------------------------------libraries----------------------------------------//
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

//------------------------------------------------------------------------------------------//
public class DisplayMessageActivity extends Activity 
{	  
	
	//---------- Attributes-----------------------------------------------------------------//
	public final static String EXTRA_MESSAGE2 = "com.app_example.MESSAGE";
	
	//---------- ON CREATE-----------------------------------------------------------------//
	public void onCreate(Bundle savedInstanceState)
	{    	
		super.onCreate(savedInstanceState);
	        
		// Get the message from the intent
		Intent intent = getIntent();
		//get the retrieve data contained within it
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		//transform in string
		
		try 
		{		
			//get_info_map(message,CommunityMap);
			String str_jsonCommunity=new String();
			str_jsonCommunity=make_json_Community_tree(message);
	        
	  		/*
			//----------------------TO SEE THE string Names in str_jsonCommunity-----------------------//
			JSONObject jsonObject1 = new JSONObject(str_jsonCommunity);
			JSONArray Array1=jsonObject1.optJSONArray("community");
			String Name=new String();
			for(int i=0; i<Array1.length(); i++)  
		    {  
		     	Name+="\n"+Array1.getJSONObject(i).getString("name").toString();
		 	}
	 		TextView textView2 = new TextView(this);
			textView2.setTextSize(10);
		 	textView2.setText(Name);
		  	setContentView(textView2);
			//------------------------------------------------------------------------------------------//
			*/
			
			Intent intent2 = new Intent(this, ListOfViewsActivity.class);
			intent2.putExtra(EXTRA_MESSAGE2, str_jsonCommunity);
			//putExtra()==takes a string as the key and the value in the second parameter.
			startActivity(intent2);
			    
			    
			  //On créé un objet Bundle, c'est ce qui va nous permetre d'envoyer des données à l'autre Activity
				//Bundle objetbunble = new Bundle();
	 
				//Cela fonctionne plus ou moins comme une HashMap, on entre une clef et sa valeur en face
				//objetbunble.putString("titre", map.get("titre"));
				//objetbunble.putString("description", map.get("description"));
				
	        	
		    } catch (JSONException e) {
				e.printStackTrace();
		    }
	        	        
	        //--------------------------------------TO SEE THE MAP VALUES-----------------------------------//
	        //TextView textView2 = new TextView(this);
	        //StringBuffer contenu = new StringBuffer();
	        /*for (Community value : Community.values()) {
	        	contenu.append(value.getId()+"\n");
	        }*/
	        
	        /*textView2.setTextSize(10);
	        textView2.setText(Name);
	        setContentView(textView2);*/
		    //---------------------------------------------------------------------------------------------//
	    }
		/*//---------- GET INFO COMMUNITY -----------------------------------------------------------------//    
	    void get_info_community(String message, Map<Integer, Community> communityMap) throws JSONException
	    {
	    	    	
			JSONObject jsonObject = new JSONObject(message); 
			JSONArray Array1=jsonObject.optJSONArray("data");
			
			for(int i=0; i<Array1.length(); i++)  
	        {  
	            String Name=Array1.getJSONObject(i).getString("name").toString();
				String Id=Array1.getJSONObject(i).getString("folder_id").toString();
				int id=Integer.parseInt(Id);
				Community folder=new Community();
				folder.set_community_attributes(id,Name);
				communityMap.put(id, folder);
	        }  
	    }*/
	
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
		
	  /*//---------- MAKE JSON CHILDREN TREE-----------------------------------------------------------------//
	  		String make_json_Children_tree(String message, int parent_id)throws JSONException
	  		{
	  				  
	  			JSONObject jsonObject = new JSONObject(message); 
	  			JSONObject jsonObject2=jsonObject.getJSONObject("folders");
	  			JSONArray Array1=jsonObject2.optJSONArray("data");
	  			String Parent_id=String.valueOf(parent_id);
	  					
	  			String jsonChildren="{\"parent_Id\":"+"\""+Parent_id+"\",\"children\":[";
	  			
	  			for(int i=0; i<Array1.length(); i++)  
	  			{  
	  				String Name=Array1.getJSONObject(i).getString("name").toString();
	  				String Id=Array1.getJSONObject(i).getString("folder_id").toString();
	  				//int id=Integer.parseInt(Id);
	  				//String Parent_id=Array1.getJSONObject(i).getString("parent_id").toString();
	  				//int parent_id=Integer.parseInt(Parent_id);
	  						
	  				jsonChildren+="{\"id\":"+"\""+Id+"\","+"\"name\":"+"\""+Name+"\"}";//,"+"\"parent_id\":"+"\""+parent_id+"\"}";
	  				if(i<(Array1.length()-1))
	  					jsonChildren+=",";
	  			}
	  			jsonChildren+="]}";
	  			return jsonChildren;
	  		}*/
		
		
	
	}
        
		
		
//------------------------------------------------------------------------------------------//
//------------------------------ USEFULL ---------------------------------------------------//
//------------------------------------------------------------------------------------------//		
		
/*
		 JSONObject object = (JSONObject) new JSONTokener(json).nextValue();
		 String query = object.getString("query");
		 JSONArray locations = object.getJSONArray("locations");*/
		
	    
	    	
	//}

        
	     // Get a string resource from your app's Resources
	        //String hello = getResources().getString(R.string.edit_message);
	        //or
	        
	     // Or supply a string resource to a method that requires a string
	        //TextView textView = new TextView(this);
	        //textView.setText("hello world");
	        
	        
	        // Create the text view
	        /*TextView textView2 = new TextView(this);
	        textView2.setTextSize(10);
	        textView2.setText(s2);

	        setContentView(textView2);*/ 
	   /// }
	
	
	

	//String Modif=new String(message);
	//int positionId=0;
	//int positionName=0;	
	
	/*while(positionName!=-1&&positionId!=-1)
	{
    	String str_name=",\"name\":\"";
    	String str_id=",\"folder_id\":\"";
    	
        positionName=search_position_name(Modif);
        
        
        if(positionName==-1||positionId==-1)
        	break;
        
        //retrieve the folder name in s1 and returns the string truncated
        String Result_name= Modif.substring(positionName+str_name.length(),Modif.length());//remove first chars
        StringTokenizer st1=new StringTokenizer(Result_name,"\"");//recovered the string containing the name
        String s1=st1.nextToken();
        Modif=new String(Result_name);
        
        positionId=search_position_id(Modif);
        //retrieve the folder id in s2 and returns the string truncated
        String Result_id= Modif.substring(positionId+str_id.length(),Modif.length());//remove first chars
        StringTokenizer st2=new StringTokenizer(Result_id,"\"");//recovered the string containing the id
        String s2=st2.nextToken();
        int folder_id = Integer.parseInt(s2);//change into int
        
        //enter values into map
        Identificators folder = new Identificators();
        folder.set_Identificators_attributes(folder_id,s1);
        community.put(folder.getId(), folder);
               
        Modif=new String(Result_id);
   	}	*/
	
	/*int search_position_name(String chaine)
    {
    	String str_name=",\"name\":\"";
    	int pos=chaine.indexOf(str_name);
		return pos;
    }
    int search_position_id(String chaine)
    {
    	String str_id=",\"folder_id\":\"";
    	int pos=chaine.indexOf(str_id);
		return pos;
    }
    int search_position(String chaine, String strToFind)
    {
    	int pos=chaine.indexOf(strToFind);
		return pos;
    }*/
	    

	