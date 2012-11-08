package com.kitware.KiwiViewer;

/*----------------------------------------libraries----------------------------------------*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
	public static MidasResource [] Communities;
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
    
		Bundle BundleResourceCommunity = this.getIntent().getExtras();
		Parcelable[] communitiesParcelable = BundleResourceCommunity.getParcelableArray("BundleResourceCommunity");
		
		List<String> ListNames = new ArrayList<String>();
		Communities = new MidasResource [communitiesParcelable.length];
		
		int i = 0;
		for( Parcelable parcel : communitiesParcelable) 
		  {
		  MidasResource comm = (MidasResource) parcel;
		  ListNames.add(comm.getName());
		  Communities[i] = new MidasResource(comm.getId(), comm.getName(), comm.getType());
		  i++;
		  
		  //Log.d(TAG,comm.getName());
		  /*Log.d(TAG,Integer.toString(comm.getId()));
		  Log.d(TAG,Integer.toString(comm.getType().ordinal()));*/
		  }
		
		

		//ChooseFirstActivity.activities.add(this);

		/*boolean isLaunched = ChooseFirstActivity.testLauching("DownloadFile");
		boolean isLaunched2 = ChooseFirstActivity.testLauching("FileExplorer");
		if(isLaunched||isLaunched2)
		  {
		  DownloadFileActivity.setFilename(null);
      DownloadFileActivity.setPath(null);
		  }*/
		
		
		
		mainListView = (ListView) findViewById(R.id.mainListView);
    listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, ListNames);

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
				
				//ChooseFirstActivity.setCurrentName(name);
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
