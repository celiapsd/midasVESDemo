package com.kitware.midasfilesvisualisation;

/*----------------------------------------libraries----------------------------------------*/
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/*------------------------------------------------------------------------------------------*/
public class FileExplorerActivity extends ListActivity 
{
 
  /*------------------Attributes -----------------------------------------------------------*/
	 private List<String> item = null;
	 private List<String> path = null;
	 private static  String root;
	 public static TextView myPathTV=null;
	 public static TextView filenameTV=null;
	 private Button saveButton;
	 public final static String EXTRA_MESSAGE = "com.kitware.midasfilesvisualisation.MESSAGE";

	 /*-------------onCreate ------------------------------------------------------------------*/
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item_view);
        ChooseFirstAction.activities.add(this);
        
        myPathTV = (TextView)findViewById(R.id.path);
        filenameTV=(TextView)findViewById(R.id.filename);
        
        filenameTV.setText("filename : " + DownloadFileActivity.getFilename());
        setRoot(Environment.getExternalStorageDirectory().getPath());
        
        getDir(getRoot());
        saveButton = (Button) findViewById(R.id.saveDirectory);
        saveButton.setOnClickListener(new View.OnClickListener(){	
    			public void onClick(View view) 
    		    {	
    				String directory= new String(myPathTV.getText().toString());
    				int Position = directory.indexOf("/");
    		    directory = directory.substring(Position,directory.length());
    		    Intent in=new Intent(FileExplorerActivity.this,DownloadFileActivity.class);
    				
    			    //putExtra()==takes a string as the key and the value in the second parameter.
    		    	//in.putExtra("EXTRA_MESSAGE", directory);
    			   startActivity(in);	
    				//setResult(myPath);
    		    }
        });
    }
    /*----------------- Assessors-------------------------------------------------------------*/
    public static String getRoot()
      {
      return root;
      }

    public static void setRoot(String myRoot)
      {
      root = myRoot;
      }

    /*----------------- getDir -------------------------------------------------------------*/
    private void getDir(String dirPath)
    {
	     myPathTV.setText("Location: " + dirPath);
	     item = new ArrayList<String>();
	     path = new ArrayList<String>();
	     File f = new File(dirPath);
	     File[] files = f.listFiles();
	     
	     if(!dirPath.equals(getRoot()))
	     {
	      item.add(getRoot());
	      path.add(getRoot());
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
   
    /*------------------onListItemClick-------------------------------------------------------------------*/
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
			 filenameTV.setText("filename : " + file.getName());
			 DownloadFileActivity.setFilename(file.getName());
			 DownloadFileActivity.setOutFilename(file.getAbsolutePath());
			 AlertDialog.Builder alt_open = new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setTitle("[" + file.getName() + "]").setPositiveButton("open", new DialogInterface.OnClickListener(){
						
			        public void onClick(DialogInterface dialog, int which)
			          {
			        	
			          Intent i = new Intent(FileExplorerActivity.this,ViewerActivity.class);
			          startActivity(i);
			          
			          }
			      });
			AlertDialog alertDialog = alt_open.create();
					 
			alertDialog.show();
			 
			 

		 }
	 }
	
}	