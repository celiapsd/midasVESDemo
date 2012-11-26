package com.kitware.KiwiViewer;

/*----------------------------------------libraries---------------------------------------*/

import java.util.ArrayList;
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
public class SingleListItemActivity extends Activity 
{

/*------------------Attributes ----------------------------------------------------------*/

/**ListView to display */
private ListView mainListView;

/** Array Adapter contains the list of data (array of community Name) */
private ArrayAdapter<String> listAdapter;

/** Array of MidasResources, can contain Folders, Items*/
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

  /*Get the folders/items in the Bundle*/
  Bundle BundleResource = this.getIntent().getExtras();

  /*Set the name of the folder in the title*/
  String name = BundleResource.getString("Name");
  setTitle(name);

  /*Get the folders/items from the Bundle and put it into the array ListChildren*/
  Parcelable[] communitiesParcelable = BundleResource.getParcelableArray("BundleResourceFolder");

  List<String> ListNames = new ArrayList<String>();
  ListChildren = new MidasResource [communitiesParcelable.length];

  int i = 0;
  for( Parcelable parcel : communitiesParcelable) 
    {
    MidasResource comm = (MidasResource) parcel;
    ListNames.add(comm.getName());
    ListChildren[i] = new MidasResource(comm.getId(), comm.getName(), comm.getType(), comm.getSize());
    i++;      
    }


  mainListView = (ListView) findViewById(R.id.mainListView);
  listAdapter = new ArrayAdapter<String>(SingleListItemActivity.this,android.R.layout.simple_dropdown_item_1line, ListNames);

  /*Set the ArrayAdapter as the ListView's adapter.*/
  mainListView.setAdapter(listAdapter);

  mainListView.setOnItemClickListener(new OnItemClickListener()
    {
  public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
    {
  if (SingleListItemActivity.DEBUG) 
    {
  Log.d(TAG, "OnItemCLick()");
  }

  /* retrieve the name of the folder/item selected */ 
  String name = ((TextView) view).getText().toString();

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
/*--------------------------------------------------------------------------*/

/*static class ViewHolder {
	  TextView text;
	  ImageView icon;
	}

	public class OneResource {
  	public int icon;
  	public String NameResource;
  	public OneResource(){
  	  super();
  	}
  	public OneResource(int icon, String NameResource) {
      super();
      this.icon = icon;
      this.NameResource = NameResource;
    }


	}
	public class ResourceAdapter extends ArrayAdapter<OneResource>{


  	Context context;
  	int layoutResourceId;
  	OneResource data[] = null;


  	public ResourceAdapter(Context context, int layoutResourceId, OneResource[] data) {
    	super(context, layoutResourceId, data);
    	this.layoutResourceId = layoutResourceId;
    	this.context = context;
    	this.data = data;
  	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	  ViewHolder holder = null;	

	  if (convertView == null) 
	    {	
	    LayoutInflater mInflater = ((Activity)context).getLayoutInflater();
      convertView = mInflater.inflate(layoutResourceId/*R.layout.custom_row_item_view,parent, false);
	    holder = new ViewHolder();
	    holder.text = (TextView) convertView.findViewById(R.id.tv_name_resource);
	    holder.icon = (ImageView) convertView.findViewById(R.id.iv_logo_resource);
	    convertView.setTag(holder);
	    } else {
	    holder = (ViewHolder) convertView.getTag();
	    }
	  holder.text.setText(ListChildren[position].getName());

	  if (ListChildren[position].getType() == MidasResource.FOLDER)
	    {
	    holder.icon.setImageResource(R.drawable.img_folder);
	    }
	  else if (ListChildren[position].getType() == MidasResource.ITEM)
	    {
      holder.icon.setImageResource(R.drawable.img_file);
      }

	  return convertView;
 }
	}*/}

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

/*private class ItemsAdapter extends ArrayAdapter<MidasResource> {

  private MidasResource[] items;

  public ItemsAdapter(Context context, int textViewResourceId, MidasResource[] items) {
          super(context, textViewResourceId, items);
          this.items = items;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
          View v = convertView;
          if (v == null) {
                  LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          v = vi.inflate(R.layout.custom_row_item_view, null);
          }

          MidasResource it = items[position];
          if (it.getType() == MidasResource.FOLDER) {
                  ImageView iv = (ImageView) v.findViewById(R.id.item_img);
                  ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.logo1));

                  if (iv != null) {
                          iv.setImageDrawable(it.getImage());
                  }
          }

          return v;
  }
}

@Override
protected void onListItemClick(ListView l, View v, int position, long id) {
  this.adapter.getItem(position).click(this.getApplicationContext());
}*/

