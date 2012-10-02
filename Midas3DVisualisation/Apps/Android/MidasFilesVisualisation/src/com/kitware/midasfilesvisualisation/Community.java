package com.kitware.midasfilesvisualisation;

//----------------------------------------libraries----------------------------------------//
import android.os.Parcel;
import android.os.Parcelable;

//----------COMMUNITY CLASS ---------------------------------------------------------//
public class Community {
	// -----Attributes--------------------------------------------------------//
	private int id;
	private String name;
	private int id_folder;

	/** -----Constructor----------------------------------------------------- */
	public Community() {
		this.setId(0);
		this.setName(null);
		this.setId_folder(0);
	}

	/*
	 * public Community(Parcel in) { this.id = in.readInt(); this.name =
	 * in.readString(); this.id_folder = in.readInt(); }
	 */

	// -----GET AND SET ATTRIBUTES------------------------------------------//
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId_Folder() {
		return id_folder;
	}

	public void setId_folder(int id) {
		this.id_folder = id;
	}

	// -----GET AND SET ATTRIBUTES TOGETHER --------------------------------//
	public void set_community_attributes(int id, String name, int id_folder) {
		this.setId(id);
		this.setName(name);
		this.setId_folder(id_folder);
	}

	public void set_community_attributes(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
}
// ----- to use a parcel -----------------------------------------------//

/*
 * public int describeContents() { return 0; }
 * 
 * public void writeToParcel(Parcel dest, int flags) { dest.writeLong(id);
 * dest.writeString(name); dest.writeLong(id_folder); }
 * 
 * public static final Parcelable.Creator<Community> CREATOR = new
 * Parcelable.Creator<Community>() { public Community createFromParcel(Parcel
 * source) { return new Community(source); }
 * 
 * public Community[] newArray(int size) { return new Community[size]; } };
 */

