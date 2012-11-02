package com.kitware.KiwiViewer;

/*----------------------------------------libraries----------------------------------------*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	public static String [] ListChildren;
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

		ChooseFirstAction.activities.add(this);

		boolean isLaunched = ChooseFirstAction.testLauching("DownloadFile");
		boolean isLaunched2 = ChooseFirstAction.testLauching("FileExplorer");
		if(isLaunched||isLaunched2)
		  {
		  DownloadFileActivity.setFilename(null);
      DownloadFileActivity.setPath(null);
		  }
		
		mainListView = (ListView) findViewById(R.id.mainListView);
		
		List<String> ListNames = new ArrayList<String>();
    ListNames.addAll(Arrays.asList(ChooseFirstAction.communityList));
    listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, ListNames);

		Log.d(TAG, "waiting for a click");

		/*Set the ArrayAdapter as the ListView's adapter.*/
    mainListView.setAdapter(listAdapter); 
    
		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				if (ListOfViewsActivity.DEBUG) {
					Log.d(TAG, "onItemCLick()");
				}
				
				/*retrieve the name of the community selected and send to SingleListItemActivity*/
				String name = ((TextView) view).getText().toString();
				setTitle(name);
				ChooseFirstAction.setCurrentName(name);
				ListChildren = MidasToolsNative.findCommunityChildren(name);
				
				Intent in = new Intent(ListOfViewsActivity.this,SingleListItemActivity.class);
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

}
