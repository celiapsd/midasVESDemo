package com.kitware.KiwiViewer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ResourceAdapter extends ArrayAdapter<String>
  {
  private final Context context;
  int layoutResourceId;
  private String data[] = null;
  private MidasResource [] ListChild = null;

  public ResourceAdapter(Context context, int layoutResourceId, String[] data, MidasResource [] ListChild) 
    {
    super(context, layoutResourceId, data);
    this.layoutResourceId = layoutResourceId;
    this.context = context;
    this.setData(data);
    this.ListChild = ListChild;
    }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) 
    {

    ViewHolder holder = null; 

    if (convertView == null) 
      { 
      LayoutInflater mInflater = ((Activity)context).getLayoutInflater();
      convertView = mInflater.inflate(layoutResourceId, /*R.layout.custom_row_item_view*/parent, false);
      holder = new ViewHolder();
      holder.text = (TextView) convertView.findViewById(R.id.tv_name_resource);
      holder.icon = (ImageView) convertView.findViewById(R.id.iv_logo_resource);
      convertView.setTag(holder);

      } else 
        {
        holder = (ViewHolder) convertView.getTag();
        }
    holder.text.setText(ListChild[position].getName());

    if (ListChild[position].getType() == MidasResource.FOLDER)
      {
      holder.icon.setImageResource(R.drawable.img_folder);
      }
    else if (ListChild[position].getType() == MidasResource.ITEM)
      {
      holder.icon.setImageResource(R.drawable.img_file);
      }
    else if (ListChild[position].getType() == MidasResource.COMMUNITY)
      {
      holder.icon.setImageResource(R.drawable.img_community);
      }
    else if (ListChild[position].getType() == MidasResource.NOTSET)
      {
      holder.icon.setImageResource(R.drawable.img_unknown);
      }

    return convertView;
    }
  
  public String[] getData()
    {
      return data;
    }


  public void setData(String data[])
    {
      this.data = data;
    }

  static class ViewHolder 
  {
  TextView text;
  ImageView icon;
  }
}