package com.app_example;

//----------------------------------------libraries----------------------------------------//
import java.util.ArrayList;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;


//---------- CLASS FOLDER---------------------------------------------------------//
public class Folder  implements Parcelable 
{
	
	//-----Attributes------------------------------------------------------//
	private int folder_id;
	private String folder_name ;
	private ArrayList<Folder> children; //list of all the folders you can access
	
	//-----Constructors-----------------------------------------------------//
	public Folder () 
	{
		this.setFolder_id(0);
        this.setFolder_name(null);
        this.setChildren(null);
	}
	
	public Folder(Parcel in) 
	{
		readFromParcel(in);
    }

	//-----GET AND SET ATTRIBUTES-----------------------------------------------------//
	public int getFolder_id() {
		return folder_id;
	}
	public void setFolder_id(int folder_id) {
		this.folder_id = folder_id;
	}
	public String getFolder_name() {
		return folder_name;
	}
	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}
	public ArrayList<Folder> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Folder> children) {
		this.children = children;
	}
	
	//-----GET AND SET ATTRIBUTES TOGETHER -----------------------------------------------------//	
	public void set_Folder_attributes(int folder_id,String Folder_name,ArrayList<Folder> children)
	{
		
        this.setFolder_id(folder_id);
        this.setFolder_name(Folder_name);
        this.setChildren(children);
        
	}
	public void set_Folder_attributes(int folder_id,String Folder_name)
	{
        this.setFolder_id(folder_id);
        this.setFolder_name(Folder_name);
	}
	
	//----- to use a parcel -----------------------------------------------------//	
	

	public int describeContents() {
		return 0;
	}


	public void writeToParcel(Parcel dest, int flags) {
	   	dest.writeLong(folder_id);
	    dest.writeString(folder_name);
  	    //dest.writeSerializable(children);
  	    dest.writeList(children);
     }

	private void readFromParcel(Parcel in) 
	{
		folder_id = in.readInt();
 		folder_name = in.readString();
 		//in.readMap(children, loader)
 		//in.readMap(this.children,Folder.class.getClassLoader());
 		//children=(Map<Integer, String>)in.readSerializable();
 		children=in.readArrayList(ClassLoader.getSystemClassLoader());
	}
	public final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() 
	{	
		public Folder createFromParcel(Parcel in) 
		{
			return new Folder(in);
	    }
		
	    public Folder[] newArray(int size) 
	    {
	    	return new Folder[size];
		}
	};
}
