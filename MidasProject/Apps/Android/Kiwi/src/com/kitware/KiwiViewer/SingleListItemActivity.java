package com.kitware.KiwiViewer;

/*----------------------------------------libraries---------------------------------------*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/*----------------------------------------------------------------------------------------------------------*/
public class SingleListItemActivity extends Activity 
{

/*------------------Attributes ----------------------------------------------------------*/

/*ListView to display */
private ListView mainListView;

/*Text View displayed on the list*/
public TextView textViewName;

/* Array of MidasResources, can contain Folders, Items*/
public  MidasResource [] ListChildren;

/** logging tag */
public final static String TAG = "SingleListItemActivity";
/** Global Debug constant **/
public static final boolean DEBUG = true;

/*---------- ON CREATE---------------------------------------------------------------------*/
public void onCreate(Bundle savedInstanceState) 
  {

  if (SingleListItemActivity.DEBUG) 
    {
    Log.d(TAG, "OnCreate()");
    }

  super.onCreate(savedInstanceState);
  //ChooseFirstActivity.activities.add(this);
  setContentView(R.layout.activity_list_of_views);

  textViewName = (TextView)findViewById(R.id.tv_name_resource);

  /*Get the folders/items in the Bundle*/
  Bundle BundleResource = this.getIntent().getExtras();

  /*Set the name of the folder in the title*/
  String name = BundleResource.getString("Name");
  setTitle(name);

  /*Get the folders/items from the Bundle and put it into the array ListChildren*/
  Parcelable[] communitiesParcelable = BundleResource.getParcelableArray("BundleResourceFolder");

  String[] ListNames = new String[communitiesParcelable.length];
  ListChildren = new MidasResource [communitiesParcelable.length];

  int i = 0;
  for( Parcelable parcel : communitiesParcelable) 
    {
    MidasResource comm = (MidasResource) parcel;
    ListNames[i] = comm.getName();
    ListChildren[i] = new MidasResource(comm.getId(), comm.getName(), comm.getType(), comm.getSize());
    i++;      
    }

  ResourceAdapter adapter = new ResourceAdapter(this,R.layout.custom_row_item_view, ListNames, ListChildren);
  mainListView = (ListView) findViewById(R.id.mainListView);
  /*Set the ArrayAdapter as the ListView's adapter.*/
  mainListView.setAdapter(adapter);

  mainListView.setOnItemClickListener(new OnItemClickListener()
    {
    public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
      {
      if (SingleListItemActivity.DEBUG) 
        {
        Log.d(TAG, "OnItemCLick()");
        }

      String name = (String) ListChildren[position].getName();

      /*Get the array of MidasResources, children of the folder selected*/
      ListChildren = MidasToolsNative.findFolderChildren(name);

      /*No data in ListChildren*/
      if (ListChildren.length == 0 )
        {
        Log.d(TAG, "LIstChildren empty");

        }
      /*The item selected*/
      else if(ListChildren.length == 1 && ListChildren[0].getType() == MidasResource.ITEM && ListChildren[0].getName().equals(name))
        {      
        MidasResource item = new MidasResource(ListChildren[0].getId(), ListChildren[0].getName(), MidasResource.ITEM,ListChildren[0].getSize() );
        Log.d(TAG, "to DOwnload file");
        Intent i = new Intent(SingleListItemActivity.this, DownloadFileActivity.class);
        i.putExtra("file", item);
        Log.d(TAG, "intent sent to DownloadFileActivity");
        startActivity(i);

        }
      /*Folder selected --> list of children contains folder/items*/
      else 
        {       
        Log.d(TAG, "to singleItem activity");
        Intent in = new Intent(SingleListItemActivity.this,SingleListItemActivity.class);        
        in.putExtra("BundleResourceFolder", ListChildren);
        in.putExtra("Name", name);
        Log.d(TAG, "intent sent to SingleListItemActivity");
        startActivity(in);

        }
      }
    });

  }

/*---------- FUNCTIONS FOR LIFECYCLE ACTIVITY------------------------------------------*/
protected void onDestroy()
  {
  if (SingleListItemActivity.DEBUG)
    {
    Log.d(TAG, "OnDestroy()");
    }
  super.onDestroy();
  }

protected void onPause() 
  {
  if (SingleListItemActivity.DEBUG)
    {
    Log.d(TAG, "OnPause()");
    }
  super.onPause();
  }

protected void onResume()
  {
  if (SingleListItemActivity.DEBUG)
    {
    Log.d(TAG, "OnResume()");
    }
  super.onResume();
  }

protected void onStart()
  {
  if (SingleListItemActivity.DEBUG) 
    {
    Log.d(TAG, "OnStart()");
    }
  super.onStart();
  }

protected void onStop()
  {
  if (SingleListItemActivity.DEBUG) 
    {
    Log.d(TAG, "OnStop()");
    }
  super.onStop();
  }

}
