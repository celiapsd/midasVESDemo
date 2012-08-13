package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


//------------------------------------------------------------------------------------------//
public class FileExplorerActivity extends ListActivity 
{
 
	 private List<String> item = null;
	 private List<String> path = null;
	 private String root;
	 public static TextView myPath=null;
	 private Button saveButton;
	 public final static String EXTRA_MESSAGE = "com.app_example.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item_view);
        MainActivity.activities.add(this);
        myPath = (TextView)findViewById(R.id.path);
        
        root = Environment.getExternalStorageDirectory().getPath();
        
        getDir(root);
        saveButton = (Button) findViewById(R.id.saveDirectory);
        saveButton.setOnClickListener(new View.OnClickListener(){	
			//---------------ON ITEM CLICK --------------------------------------------------//
			public void onClick(View view) 
		    {	
				String directory= new String(myPath.getText().toString());
				int Position = directory.indexOf("/");
		    	directory = directory.substring(Position,directory.length());
		    	Intent in=new Intent(FileExplorerActivity.this,DownloadFileActivity.class);
				
			    //putExtra()==takes a string as the key and the value in the second parameter.
			    startActivity(in);	
				//setResult(myPath);
		    }
		});
    }
    
    private void getDir(String dirPath)
    {
	     myPath.setText("Location: " + dirPath);
	     item = new ArrayList<String>();
	     path = new ArrayList<String>();
	     File f = new File(dirPath);
	     File[] files = f.listFiles();
	     
	     if(!dirPath.equals(root))
	     {
	      item.add(root);
	      path.add(root);
	      item.add("../");
	      path.add(f.getParent()); 
	     }
     
	     for(int i=0; i < files.length; i++)
	     {
	    	 File file = files[i];
	      
	    	 if(!file.isHidden() && file.canRead())
	    	 {
	    		 path.add(file.getPath());
	    		 if(file.isDirectory())
	    		 {
	    			 item.add(file.getName() + "/");
	    		 }else{
	    			 item.add(file.getName());
	    		 }
	    	 } 
	     }
	     ArrayAdapter<String> fileList =new ArrayAdapter<String>(this, R.layout.activity_simplerow, item);
	     setListAdapter(fileList); 
    }

	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) 
	 {
	  
		 File file = new File(path.get(position));
	  
		 if (file.isDirectory())
		 {
			 if(file.canRead()){
				 getDir(path.get(position));
			 }else{
				 new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setTitle("[" + file.getName() + "] folder can't be read!").setPositiveButton("OK", null).show(); 
			 } 
		 }else {
			 new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setTitle("[" + file.getName() + "]").setPositiveButton("open", null).show();
	
		 }
	 }
	
}	