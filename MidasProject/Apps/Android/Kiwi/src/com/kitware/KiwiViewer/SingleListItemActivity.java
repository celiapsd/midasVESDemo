package com.kitware.KiwiViewer;

/*----------------------------------------libraries---------------------------------------*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	
	public  MidasResource [] ListChildren;

	public final static String TAG = "SingleListItemActivity";
	/** Global Debug constant **/
	public static final boolean DEBUG = true;

	/*---------- ON CREATE---------------------------------------------------------------------*/
	public void onCreate(Bundle savedInstanceState) {
	
		if (SingleListItemActivity.DEBUG) {
			Log.d(TAG, "OnCreate()");
		}
		super.onCreate(savedInstanceState);
		ChooseFirstActivity.activities.add(this);
		setContentView(R.layout.activity_list_of_views);

		
		
    Bundle BundleResourceCommunity = this.getIntent().getExtras();
    Parcelable[] communitiesParcelable = BundleResourceCommunity.getParcelableArray("BundleResourceFolder");
    
    List<String> ListNames = new ArrayList<String>();
    ListChildren = new MidasResource [communitiesParcelable.length];
    
    int i = 0;
    for( Parcelable parcel : communitiesParcelable) 
      {
      MidasResource comm = (MidasResource) parcel;
      ListNames.add(comm.getName());
      ListChildren[i] = new MidasResource(comm.getId(), comm.getName(), comm.getType(), comm.getSize());
      i++;      
      //Log.d(TAG,comm.getName());
      /*Log.d(TAG,Integer.toString(comm.getId()));
      Log.d(TAG,Integer.toString(comm.getType().ordinal()));*/
      }
		mainListView = (ListView) findViewById(R.id.mainListView);
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
				ListChildren = MidasToolsNative.findFolderChildren(name);

				if (ListChildren.length == 0 )
				  {
	        Log.d(TAG, "LIstChildren empty");

				  }
				else if(ListChildren.length == 1 && ListChildren[0].getType() == MidasResource.ITEM && ListChildren[0].getName().equals(name))
				  {      
				  MidasResource item = new MidasResource(ListChildren[0].getId(), ListChildren[0].getName(), MidasResource.ITEM,ListChildren[0].getSize() );
				  Log.d(TAG, "to DOwnload file");
				  Intent i = new Intent(SingleListItemActivity.this, DownloadFileActivity.class);
				  i.putExtra("file", item);
	        Log.d(TAG, "intent sent to DownloadFileActivity");
	        startActivity(i);

          }
				else 
				  Log.d(TAG, "to singleItem activity");
				  Intent in = new Intent(SingleListItemActivity.this,SingleListItemActivity.class);
         
          
          in.putExtra("BundleResourceFolder", ListChildrenStr);
          Log.d(TAG, "intent sent to SingleListItemActivity");
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
//	@Override
//  public boolean onKeyDown(int keyCode, KeyEvent event)  {
//      if (keyCode == KeyEvent.KEYCODE_BACK ) {
//          ListChildrenStr = ListChildrenMidas; 
//          super.onKeyDown(keyCode, event);
//          return true;
//      }
//
//      return super.onKeyDown(keyCode, event);
//  }
}
