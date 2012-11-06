package com.kitware.KiwiViewer;

/*----------------------------------------libraries---------------------------------------*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	//public final static String EXTRA_MESSAGE3 = "com.kitware.KiwiViewer.MESSAGE";
	//public int id;
	//public String name;
	// public Folder Child;
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	//public List<Folder> ListChildren;
	public static String [] ListChildren;
	
	public final static String TAG = "SingleListItemActivity";
	/* Global Debug constant */
	public static final boolean DEBUG = true;

	/*---------- ON CREATE---------------------------------------------------------------------*/
	public void onCreate(Bundle savedInstanceState) {
	
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnCreate()");
		}
		super.onCreate(savedInstanceState);
		ChooseFirstActivity.activities.add(this);
		setTitle(ChooseFirstActivity.getCurrentName());
		setContentView(R.layout.activity_list_of_views);

		mainListView = (ListView) findViewById(R.id.mainListView);

	  ListChildren=ListOfViewsActivity.ListChildren;

		List<String> ListNames = new ArrayList<String>();
		ListNames.addAll(Arrays.asList(ListChildren));
		listAdapter = new ArrayAdapter<String>(SingleListItemActivity.this,android.R.layout.simple_dropdown_item_1line, ListNames);

		/*Set the ArrayAdapter as the ListView's adapter.*/
		mainListView.setAdapter(listAdapter);

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (SingleListItemActivity.DEBUG) {
					Log.d(TAG, "OnItemCLick()");
				}
				
				/* retrieve the name of the community selected and send toSingleListItemActivity*/ 
				String name = ((TextView) view).getText().toString();
				//setTitle(name);
				ChooseFirstActivity.setCurrentName(name);
				ListChildren = MidasToolsNative.findFolderChildren(name);
				ListOfViewsActivity.ListChildren=ListChildren;
				if (ListChildren.length == 0 )
				  {
	        Log.d(TAG, "LIstChildren empty");

				  }
				else if(ListChildren[0].contentEquals("item selected"))
				  {
				  Log.d(TAG, "to DOwnload file");
				  Intent i = new Intent(SingleListItemActivity.this, DownloadFileActivity.class);
          startActivity(i);
          }
				else if(ListChildren[0].contains("Folders") || ListChildren[0].contains("Items"))
				  {
				  Log.d(TAG, "to singleItem activity");
				  Intent in = new Intent(SingleListItemActivity.this,SingleListItemActivity.class);
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
}
