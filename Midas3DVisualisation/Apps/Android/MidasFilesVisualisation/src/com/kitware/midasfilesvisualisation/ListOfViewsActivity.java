package com.kitware.midasfilesvisualisation;

/*----------------------------------------libraries----------------------------------------*/
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

/*------------------------------------------------------------------------------------------*/
public class ListOfViewsActivity extends Activity {

	/*----------Attributes-----------------------------------------------------------------*/
	public final static String EXTRA_MESSAGE3 = "com.kitware.midasfilesvisualisation.MESSAGE";
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	public List<Community> ListCommunity;
	// public static Folder child;
	public final static String TAG = "ListOfViewsActivity";
	/** Global Debug constant */
	public static final boolean DEBUG = true;

	/* ---------- ON CREATE-----------------------------------------------------------------*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "OnCreate()");
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_views);
		// Intent in=getIntent();
		ChooseFirstAction.activities.add(this);

		boolean isLaunched = ChooseFirstAction.testLauching("DownloadFile");
		boolean isLaunched2 = ChooseFirstAction.testLauching("FileExplorer");
		if(isLaunched||isLaunched2)
		  {
		  DownloadFileActivity.setFilename(null);
      DownloadFileActivity.setOutFilename(null);
		  }
		
		ListCommunity = ChooseFirstAction.comList;
		mainListView = (ListView) findViewById(R.id.mainListView);

		/*TO SEE THE string Names into a list*/
		String Names[] = new String[ListCommunity.size()];
		for (int i = 0; i < ListCommunity.size(); i++) {
			Names[i] = ListCommunity.get(i).getName().toString();
		}
		List<String> ListNames = new ArrayList<String>();
		ListNames.addAll(Arrays.asList(Names));
		listAdapter = new ArrayAdapter<String>(this,R.layout.activity_simplerow, ListNames);

		/*Set the ArrayAdapter as the ListView's adapter.*/
		mainListView.setAdapter(listAdapter);

		Log.d(TAG, "waiting for a click");

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				if (ListOfViewsActivity.DEBUG) {
					Log.d(TAG, "onItemCLick()");
				}
				
				/*retrieve the name of the community selected and send to SingleListItemActivity*/
				String name = ((TextView) view).getText().toString();
				setTitle(name);
				List<Community> ListCommunity = ListOfViewsActivity.this.ListCommunity;
				int fold_id = ListCommunity.get(position).getId_Folder();

				Folder child = new Folder();
				child.set_Folder_attributes(fold_id, name);

				// -----test Parcel----//
				/*
				 * Intent i = new Intent(ListOfViewsActivity.this,
				 * SingleListItemActivity.class); Bundle b= new Bundle();
				 * b.putParcelable("child", child);
				 * //i.getExtras().putParcelable("child", child);
				 * //i.putExtra("child", child);// sending data to new activity
				 * i.putExtras(b); startActivity(i);
				 */

				// ----- test string -----//

				Intent in = new Intent(ListOfViewsActivity.this,SingleListItemActivity.class);
				// System.out.println("This shouldnt be empty: "+child.transFolderIntoJSONString());
				String childSt = new String(child.transFolderIntoJSONString());
				in.putExtra(EXTRA_MESSAGE3, childSt);
				startActivity(in);
			}
		});
	}

	/*---------- FUNCTIONS FOR LIFECYCLEACTIVITY------------------------------------------*/
	protected void onDestroy() {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "onDestroy()");
		}
		super.onDestroy();
	}

	protected void onPause() {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "onPause()");
		}
		super.onPause();
	}

	protected void onResume() {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "onPause()");
		}
		super.onResume();
	}

	protected void onStart() {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "onStart()");
		}
		super.onStart();
	}

	protected void onStop() {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "onStop()");
		}
		super.onStop();
	}

	/**
	 * ---------- GET COMMUNITY INTO LIST--------------------------------------------------------
	 */
	public List<Community> get_Community_Into_List(String jsonString) {
		if (ListOfViewsActivity.DEBUG) {
			Log.d(TAG, "get_Community_Into_List()");
		}
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray Array1 = jsonObject.optJSONArray("community");
			List<Community> communityList = new ArrayList<Community>();

			for (int i = 0; i < Array1.length(); i++) {
				int Id = Array1.getJSONObject(i).getInt("id");
				String Name = Array1.getJSONObject(i).getString("name")
						.toString();
				int FolderId = Array1.getJSONObject(i).getInt("folder_id");
				Community folder = new Community();
				folder.set_community_attributes(Id, Name, FolderId);
				communityList.add(folder);

			}

			return communityList;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
