package com.kitware.KiwiViewer;

/*----------------------------------------libraries----------------------------------------*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/*------------------------------------------------------------------------------------------*/
public class ListOfViewsActivity extends Activity 
{

/*----------Attributes-----------------------------------------------------------------*/

/*ListView to display */
private ListView mainListView;

/* Array of MidasResources, can contain Folders, Items*/
public  MidasResource [] ListChildren;

/* Array of Communities */
public  MidasResource [] Communities;

/** logging tag */
public final static String TAG = "ListOfViewsActivity";
/** Global Debug constant */
public static final boolean DEBUG = true;

/* ---------- ON CREATE-----------------------------------------------------------------*/
@Override
public void onCreate(Bundle savedInstanceState) 
  {
  if (ListOfViewsActivity.DEBUG) 
    {
    Log.d(TAG, "OnCreate()");
    }
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_list_of_views);

  /*Get the communities in the Bundle and put it into the array of communities*/
  Bundle BundleResourceCommunity = this.getIntent().getExtras();
  Parcelable[] communitiesParcelable = BundleResourceCommunity.getParcelableArray("BundleResourceCommunity");

  String[] ListNames = new String[communitiesParcelable.length];
  Communities = new MidasResource [communitiesParcelable.length];

  int i = 0;
  for( Parcelable parcel : communitiesParcelable) 
    {
    MidasResource comm = (MidasResource) parcel;
    ListNames[i] = comm.getName();
    Communities[i] = new MidasResource(comm.getId(), comm.getName(), comm.getType(), comm.getSize());
    i++;
    }

  ResourceAdapter adapter = new ResourceAdapter(this,R.layout.custom_row_item_view, ListNames, Communities);
  mainListView = (ListView) findViewById(R.id.mainListView);
  /*Set the ArrayAdapter as the ListView's adapter.*/
  mainListView.setAdapter(adapter);

  Log.d(TAG, "waiting for a click");

  mainListView.setOnItemClickListener(new OnItemClickListener() 
    {
    public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
      {

      if (ListOfViewsActivity.DEBUG) 
        {
        Log.d(TAG, "onItemCLick()");
        }

      /*retrieve the name of the community selected */
      String name = (String) Communities[position].getName();
      setTitle("Communities");

      /*Get the array of MidasResources, children of the community selected*/
      ListChildren = MidasToolsNative.findCommunityChildren(name);

      /*Display the list in the logcat*/
      for(int i=0;i<ListChildren.length;i++)
        {
        Log.d(TAG, ListChildren[i].getName());
        }

      /*Send a bundle to SingleLsitItemActivity with the list of Children and the name of the community*/
      Intent intent = new Intent(ListOfViewsActivity.this,SingleListItemActivity.class);
      intent.putExtra("BundleResourceFolder", ListChildren);
      intent.putExtra("Name", name);
      Log.d(TAG, "intent sent to SingleListItemActivity");
      startActivity(intent);
      }
    });
  }

/*---------- FUNCTIONS FOR LIFECYCLEACTIVITY------------------------------------------*/
protected void onDestroy() 
  {
  if (ListOfViewsActivity.DEBUG) 
    {
    Log.d(TAG, "onDestroy()");
    }
  super.onDestroy();
  }

protected void onPause() 
  {
  if (ListOfViewsActivity.DEBUG) 
    {
    Log.d(TAG, "onPause()");
    }
  super.onPause();
  }

protected void onResume() 
  {
  if (ListOfViewsActivity.DEBUG) 
    {
    Log.d(TAG, "onPause()");
    }
  super.onResume();
  }

protected void onStart() 
  {
  if (ListOfViewsActivity.DEBUG) 
    {
    Log.d(TAG, "onStart()");
    }
  super.onStart();
  }

protected void onStop() 
  {
  if (ListOfViewsActivity.DEBUG) 
    {
    Log.d(TAG, "onStop()");
    }
  super.onStop();
  }
}
