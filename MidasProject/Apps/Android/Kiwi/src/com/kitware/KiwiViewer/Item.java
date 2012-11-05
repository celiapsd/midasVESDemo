package com.kitware.KiwiViewer;

import android.os.Parcel;
import android.os.Parcelable;

/*----------------------------------------libraries----------------------------------------*/

/*---------- CLASS Item---------------------------------------------------------*/
public class Item{

	/*-----Attributes------------------------------------------------------*/
	private int item_id;
	private String item_name;
	private String item_size;

	/*-----Constructors-----------------------------------------------------*/
	public Item() {
		this.setItem_id(0);
		this.setItem_name(null);
		this.setItem_size(null);
	}
	
	/*-----Assessors-----------------------------------------------------*/
	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}
	

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
  	public void setItem_size(String item_size) {
    this.item_size = item_size;
  }

  public String getItem_size() {
    return item_size;
  }
  
	public void set_item_attributes(int Item_id, String Item_name, String Item_size) {
		this.setItem_id(Item_id);
		this.setItem_name(Item_name);
		this.setItem_size(Item_size);
	}

	
}

