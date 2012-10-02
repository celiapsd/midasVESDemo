package com.kitware.midasfilesvisualisation;

/*----------------------------------------libraries---------------------------------------*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/*----------------------------------------------------------------------------------------------------------*/
public class SingleListItemActivity extends Activity {

	/*------------------Attributes ----------------------------------------------------------*/
	public final static String EXTRA_MESSAGE3 = "com.kitware.midasfilesvisualisation.MESSAGE";
	public int id;
	public String name;
	// public Folder Child;
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	public List<Folder> ListChildren;
	public final static int CODE_RETOUR = 0;
	public final static String TAG = "SingleListItemActivity";
	/* Global Debug constant */
	public static final boolean DEBUG = true;

	/*---------- ON CREATE---------------------------------------------------------------------*/
	public void onCreate(Bundle savedInstanceState) {
	
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnCreate()");
		}
		super.onCreate(savedInstanceState);

		/*
		 * //------Parcelable---//
		 * 
		 * Folder child; Bundle b = this.getIntent().getExtras(); child =
		 * b.getParcelable("child"); TextView productlabel = (TextView)
		 * findViewById(R.id.product_label); productlabel.setText("Folder : " +
		 * "\n" + " id : " +child.getFolder_id() + "\n" + " Name : " +
		 * child.getFolder_name());
		 */

		// ----String----//

		Intent i = getIntent();
		ChooseFirstAction.activities.add(this);
		String product = i.getStringExtra(ListOfViewsActivity.EXTRA_MESSAGE3);
		get_Parent(product);
		// Folder child=ListOfViewsActivity.child;
		// name=child.getFolder_name();
		// id=child.getFolder_id();
		setTitle(name);
		String url;

		// txtProduct.setText(product);
		// if
		// (!ChooseFirstAction.UrlBeginning.contains("midas.folder.children&id="))
		// {
		
		url = ChooseFirstAction.getUrlBeginning()+ "/api/json?method=midas.folder.children&id=" + id;
		// }
		/*
		 * else { url=ChooseFirstAction.UrlBeginning; }
		 */

		if (ChooseFirstAction.getToken() != null) 
		  {
			url += "&token=" + ChooseFirstAction.getToken();
		  }
		getListChildren(url);
		
		
		
		setContentView(R.layout.activity_list_of_views);

		mainListView = (ListView) findViewById(R.id.mainListView);

		/*TO SEE string Names into a list---------------------------*/
		String Names[] = new String[this.ListChildren.size()];
		for (int i1 = 0; i1 < this.ListChildren.size(); i1++) 
		  {
			Names[i1] = this.ListChildren.get(i1).getFolder_name().toString();
			System.out.println(Names[i1]);
		  }
		
		List<String> ListNames = new ArrayList<String>();
		ListNames.addAll(Arrays.asList(Names));
		listAdapter = new ArrayAdapter<String>(SingleListItemActivity.this,
				R.layout.activity_simplerow, ListNames);

		/*Set the ArrayAdapter as the ListView's adapter.*/
		mainListView.setAdapter(listAdapter);

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (SingleListItemActivity.DEBUG) {
					Log.d(TAG, "OnItemCLick()");
				}
				
				/* retrieve the name of the community selected and send toSingleListItemActivity*/ 
				String name = ((TextView) view).getText().toString();

				int fold_id = SingleListItemActivity.this.ListChildren.get( position).getFolder_id();

				Folder child = new Folder();
				child.set_Folder_attributes(fold_id, name);

				// ----- test string -----//

				String childSt = new String(child.transFolderIntoJSONString());
				if (childSt.contains("download file")) {
					Intent i = new Intent(SingleListItemActivity.this,
							DownloadFileActivity.class);
					i.putExtra(EXTRA_MESSAGE3, childSt);
					startActivity(i);
					SingleListItemActivity.this.finish();
				} else {
					Intent in = new Intent(SingleListItemActivity.this,
							SingleListItemActivity.class);
					in.putExtra(EXTRA_MESSAGE3, childSt);
					startActivity(in);
				}
			}
		});

	}

	/*---------- FUNCTIONS FOR LIFECYCLE ACTIVITY------------------------------------------*/
	protected void onDestroy() {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnDestroy()");
		}
		super.onDestroy();
	}

	protected void onPause() {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnPause()");
		}
		super.onPause();
	}

	protected void onResume() {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnResume()");
		}
		super.onResume();
	}

	protected void onStart() {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnStart()");
		}
		super.onStart();
	}

	protected void onStop() {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnStop()");
		}
		super.onStop();
	}

	/**
	 * ----------GETPARENT---------------------------------------------------------------
	 */
	public void get_Parent(String folder) {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "get_Parent()");
		}

		try {
			JSONObject jsonObject = new JSONObject(folder);
			id = jsonObject.getInt("folder_id");
			name = jsonObject.getString("name").toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * ---------getListChildren----------------------------------------------------------------------
	 *
	 */
	public void getListChildren(String sUrl) {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "getListChildren()");
		}
		HttpThread HT = new HttpThread(sUrl);
		Thread th = new Thread(HT);
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		String result = HT.getResponse();
		String str_jsonChildren;
		try {
			str_jsonChildren = make_json_Children_tree(result, this.id);
			ListChildren = get_Children_Into_List(str_jsonChildren);
			// get the retrieve list linked with the json string
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ---------- MAKE JSON CHILDREN TREE-----------------------------------------------------
	 */
	String make_json_Children_tree(String message, int parent_id) throws JSONException {
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "make_json_Children_tree()");
		}
		JSONObject jsonObject = new JSONObject(message);
		JSONObject jsonObject2 = jsonObject.getJSONObject("data");

		String Parent_id = String.valueOf(parent_id);
		String jsonChildren = "{\"parent_Id\":" + "\"" + Parent_id+ "\",\"children\":[";

		if (jsonObject2.getJSONArray("folders").length() != 0) 
		  {
			// jsonChildren+="\"children\":[";
			JSONArray Array1 = jsonObject2.optJSONArray("folders");

			for (int i = 0; i < Array1.length(); i++) 
			  {
				String Name = Array1.getJSONObject(i).getString("name").toString();
				String Id = Array1.getJSONObject(i).getString("folder_id").toString();
				jsonChildren += "{\"id\":" + "\"" + Id + "\"," + "\"name\":"+ "\"" + Name + "\"}";
				if (i < (Array1.length() - 1)) 
				  {
					jsonChildren += ",";
				  }
			  }
		  }

		jsonChildren += "],\"items\":[";

		if (jsonObject2.getJSONArray("items").length() != 0) 
		  {

			JSONArray Array1 = jsonObject2.optJSONArray("items");

			for (int i = 0; i < Array1.length(); i++) 
			  {
				String Name = Array1.getJSONObject(i).getString("name").toString();
				String Id = Array1.getJSONObject(i).getString("item_id").toString();
				jsonChildren += "{\"item_id\":" + "\"" + Id + "\","+ "\"item_name\":" + "\"" + Name + "\"}";
				if (i < (Array1.length() - 1)) 
				  {
					jsonChildren += ",";
				  }
			  }
		  }
		jsonChildren += "]";
		if (jsonObject2.getJSONArray("items").length() == 0
				&& jsonObject2.getJSONArray("folders").length() == 0) 
		  {
			jsonChildren += "\"children\":\"null\"";
		  }
		jsonChildren += "}";

		return jsonChildren;

	}

	/**
	 * ---------- GET CHILDREN INTO LIST----------------------------------------------------------------
	 */
	public List<Folder> get_Children_Into_List(String jsonString) {
		List<Folder> childrenList = new ArrayList<Folder>();

		// --retrieve children--//
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// List<Folder> childrenList=new ArrayList<Folder> ();

			if (jsonObject.has("children")) {
				JSONArray Array1 = jsonObject.optJSONArray("children");

				for (int i = 0; i < Array1.length(); i++) {
					int Id = Array1.getJSONObject(i).getInt("id");
					String Name = Array1.getJSONObject(i).getString("name");
					Folder folder = new Folder();
					folder.set_Folder_attributes(Id, Name);
					childrenList.add(folder);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return childrenList;
		}

		// --retrieve items--//
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			if (!jsonObject.isNull("items")) {
				JSONArray Array1 = jsonObject.optJSONArray("items");

				for (int i = 0; i < Array1.length(); i++) {
					int Item_id = Array1.getJSONObject(i).getInt("item_id");
					String Item_Name = Array1.getJSONObject(i)
							.getString("item_name").toString()
							+ "\n" + "				download file";
					Folder folder = new Folder();
					folder.set_Folder_attributes(Item_id, Item_Name);
					childrenList.add(folder);
				}
			}
			return childrenList;

		} catch (JSONException e) {
			e.printStackTrace();
			return childrenList;
		}

	}
}
