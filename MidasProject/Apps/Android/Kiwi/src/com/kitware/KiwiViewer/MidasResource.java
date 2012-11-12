package com.kitware.KiwiViewer;

import android.os.Parcel;
import android.os.Parcelable;

public class MidasResource implements Parcelable
  {
  
    /*
     * The type of the resource
     */
    public static final int COMMUNITY = 0;
    public static final int FOLDER = 1;
    public static final int ITEM = 2;
    public static final int BITSTREAM = 3;
    public static final int NOTSET = -1;
    
    
    /*
     * The id of the resource. This is unique within the given type
     */
    protected int id;
    
    /*
     * The name of the resource 
     */
    protected String name;
    
    protected int type;

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

    public MidasResource()
      {
      this.init(-1, name, NOTSET);
      }

    public MidasResource(int id, String name, int type)
      {
      this.init(id, name, type);
      }
    
    protected void init( int id, String name, int type)
      {
      this.setId(id);
      this.setName(name);
      this.setType(type);
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
      this.init(in.readInt(), in.readString(), in.readInt());
    }

  }
