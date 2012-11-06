package com.kitware.KiwiViewer;

import android.os.Parcel;
import android.os.Parcelable;

public class MidasResource implements Parcelable
  {
  
    /*
     * The type of the resource
     */
    public enum Type { COMMUNITY, FOLDER, ITEM, BITSTREAM, NOTSET };
    
    /*
     * The id of the resource. This is unique within the given type
     */
    protected int id;
    
    /*
     * The name of the resource 
     */
    protected String name;
    
    protected Type type;

    public Type getType()
      {
      return type;
      }

    public void setType(Type type)
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
      this.init(-1, name, Type.NOTSET);
      }

    public MidasResource(int id, String name, Type type)
      {
      this.init(id, name, type);
      }
    
    protected void init( int id, String name, Type type)
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
      dest.writeInt(type.ordinal());
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
      this.init(in.readInt(), in.readString(), Type.values()[in.readInt()]);
    }

  }
