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
	public  MidasResource [] ListChildren;
	public  MidasResource [] Communities;
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
		  Communities[i] = new MidasResource(comm.getId(), comm.getName(), comm.getType(), comm.getSize());
		  Log.d(TAG, "name "+Communities[i].getName()+" Id "+Communities[i].getId()+" type "+Communities[i].getType());
		  i++;
		  }

		ChooseFirstActivity.activities.add(this);

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
				setTitle("Communities");
				//ChooseFirstActivity.setCurrentName(name);
				
				ListChildren = MidasToolsNative.findCommunityChildren(name);
				
				/*MidasResource[] resources = new MidasResource[ListChildren.length];*/
		    for(int i=0;i<ListChildren.length;i++)
		      {
		      //resources[i] = new MidasResource(i, ListChildren[i].toString(), MidasResource.FOLDER);
		      Log.d(TAG, ListChildren[i].getName());
		      }
		    
		    Intent intent = new Intent(ListOfViewsActivity.this,SingleListItemActivity.class);
		    intent.putExtra("BundleResourceFolder", ListChildren);
		    intent.putExtra("Name", name);
		    Log.d(TAG, "intent sent to SingleListItemActivity");
		    startActivity(intent);
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
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	        ListChildren = Communities; 
//	        super.onKeyDown(keyCode, event);
//	        return true;
//	    }
//
//	    return super.onKeyDown(keyCode, event);
//	}

}
