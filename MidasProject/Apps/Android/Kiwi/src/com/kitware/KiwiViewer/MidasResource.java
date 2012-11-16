package com.kitware.KiwiViewer;

import android.os.Parcel;
import android.os.Parcelable;

public class MidasResource implements Parcelable
  {
  
    /*
     * The type of the resource
     */
    public static final int NOTSET = 0;
    public static final int COMMUNITY = 1;
    public static final int FOLDER = 2;
    public static final int ITEM = 3;
    public static final int BITSTREAM = 4;

    
    
    /*
     * The id of the resource. This is unique within the given type
     */
    protected int id;
    
    /*
     * The name of the resource 
     */
    protected String name;
 
    /*
     * The type of the resource 
     */   
    protected int type;
   
    /*
     * The size of the resource 
     */
    protected int size;

    public int getType()
      {
      return type;
      }

    public void setType(int type)
      {
      this.type = type;
      }

    public int getId()
      {
      return id;
      }

    public void setId(int id)
      {
      this.id = id;
      }

    public String getName()
      {
      return name;
      }

    public void setName(String name)
      {
      this.name = name;
      }
    public int getSize()
      {
      return size;
      }

    public void setSize(int size)
      {
      this.size = size;
      }

    public MidasResource()
      {
      this.init(-1, name, NOTSET, 0);
      }

    public MidasResource(int id, String name, int type, int size)
      {
      this.init(id, name, type, size);
      }
    
    protected void init( int id, String name, int type, int size)
      {
      this.setId(id);
      this.setName(name);
      this.setType(type);
      this.setSize(size);
      }
    
    public int describeContents()
      {
      return 0;
      }

    public void writeToParcel(Parcel dest, int flags)
      {
      dest.writeInt(id);
      dest.writeString(name);
      dest.writeInt(type);
      dest.writeInt(size);
      }
    
    public static final Parcelable.Creator<MidasResource> CREATOR
    = new Parcelable.Creator<MidasResource>() {
    public MidasResource createFromParcel(Parcel in) {
    return new MidasResource(in);
    }

    public MidasResource[] newArray(int size) {
    return new MidasResource[size];
    }
    };

    private MidasResource(Parcel in) {
      this.init(in.readInt(), in.readString(), in.readInt(), in.readInt());
    }

  }
