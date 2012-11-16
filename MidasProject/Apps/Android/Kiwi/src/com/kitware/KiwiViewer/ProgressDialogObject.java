package com.kitware.KiwiViewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

public class ProgressDialogObject extends ProgressDialog
  {

    ProgressDialog mProgressDialog;
    int mMaximum;
    int mProgress;
    
    public ProgressDialogObject(Context Context)
      {
      super(Context);
      this.mProgressDialog = new ProgressDialog(Context);
      this.mMaximum = 100;
      this.mProgress = 0;
      }
    public ProgressDialogObject(Context Context, int max, int progress)
      {
      super(Context);
      this.mProgressDialog = new ProgressDialog(Context);
      this.mMaximum = max;
      this.mProgress = progress;
      }
    //public native void setProgressDialog(int progress);
    
    public void setProgress(int progress)
      {
      this.mProgress = progress;
      this.mProgressDialog.setProgress(progress);
      }
    public void setMaximum(int max)
      {
      this.mMaximum = max;
      this.mProgressDialog.setMax(max);
      }
    public int getProgress() 
      {
      return this.mProgress;
      }

  }
