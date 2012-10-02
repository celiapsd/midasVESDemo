/*========================================================================
  VES --- VTK OpenGL ES Rendering Toolkit

      http://www.kitware.com/ves

  Copyright 2011 Kitware, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 ========================================================================*/
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kitware.midasfilesvisualisation;

/*--------------------Libraries----------------------------------------------------------------------*/
import org.metalev.multitouch.controller.MultiTouchController;
import org.metalev.multitouch.controller.MultiTouchController.MultiTouchObjectCanvas;
import org.metalev.multitouch.controller.MultiTouchController.PointInfo;
import org.metalev.multitouch.controller.MultiTouchController.PositionAndScale;

import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import android.app.ProgressDialog;

import java.util.ArrayList;

import java.lang.Thread;
import java.lang.InterruptedException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

/*------------------CLASS KiwiGLSurfaceView----------------------------------------------------------------------------------------------*/
public class KiwiGLSurfaceView extends GLSurfaceView implements MultiTouchObjectCanvas<KiwiGLSurfaceView> {

    /*----------------- Attributes ----------------------------------------------------*/
    private static String TAG = "KiwiGLSurfaceView";
    private static final boolean DEBUG = false;

    private MyRenderer mRenderer;

    private GestureDetector mGestureDetector;

    private MultiTouchController<KiwiGLSurfaceView> multiTouchController = new MultiTouchController<KiwiGLSurfaceView>(this);

    private PointInfo mLastTouchInfo = null;
    private PointInfo mCurrentTouchInfo = new PointInfo();

    /*-----------------  CLASS MyRunnable --------------------------------------------*/
    public class MyRunnable implements Runnable {

      public float dx, dy, x0, y0, x1, y1, scale, angle;
      public boolean isMulti;

      public void run() {
  		  Log.d(TAG +"MyRunnable", "run()");


        if (isMulti) {
          KiwiNative.handleTwoTouchPanGesture(x0, y0, x1, y1);
        }
        else {
          KiwiNative.handleSingleTouchPanGesture(dx, dy);
        }

        if (isMulti && scale != 1.0f) {
          KiwiNative.handleTwoTouchPinchGesture(scale);
        }

        if (isMulti && angle != 0.0f) {
          KiwiNative.handleTwoTouchRotationGesture(angle);
        }

        requestRender();
      }
    }
 	 /*public void onBackPressed() {
 	     
		 Log.d(TAG, "onBackPressed Called");
		 stopRendering();
		 
		 /*Intent setIntent = new Intent(Intent.ACTION_MAIN);
         setIntent.addCategory(Intent.CATEGORY_HOME);
         setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(setIntent); 

     return;
	}*/

   

    
    /*-----------------  CLASS MyGestureDetector -----------------------------------------------*/
    public class MyGestureDetector extends SimpleOnGestureListener {
  		

        /*--------- onDoubleTap-------------------------------*/
        @Override
        public boolean onDoubleTap(MotionEvent e) {
        	Log.d(TAG +"MyGestureDetector", "onDoubleTap()");

          final float displayX = e.getX();
          final float displayY = e.getY();

          queueEvent(new Runnable() {
                   public void run() {
                   	Log.d(TAG +"MyGestureDetector-->onDoubleTap()","queueEvent -->run");

                      KiwiNative.handleDoubleTap(displayX, displayY);
                      requestRender();
                   }});

          return true;
        }

        /*--------- onLongPress-------------------------------*/
        public void onLongPress(MotionEvent e) {

          final float displayX = e.getX();
          final float displayY = e.getY();

          queueEvent(new Runnable() {
                   public void run() {
                      	Log.d(TAG +"MyGestureDetector-->onLongPress()","queueEvent -->run");

                      KiwiNative.handleLongPress(displayX, displayY);
                      requestRender();
                   }});

        }
       
        /*--------- onSingleTapConfirmed-------------------------------*/
        public boolean onSingleTapConfirmed(MotionEvent e) {


          final float displayX = e.getX();
          final float displayY = e.getY();

          queueEvent(new Runnable() {
                   public void run() {
                     	Log.d(TAG +"MyGestureDetector-->onSingleTapConfirmed()","queueEvent -->run");
 
                      KiwiNative.handleSingleTouchTap(displayX, displayY);
                      requestRender();
                   }});

          return true;
        } 
    }
   	/* public void onBackPressed() {
   	     
		 Log.d(TAG, "onBackPressed Called");
		 stopRendering();
		 
		 /*Intent setIntent = new Intent(Intent.ACTION_MAIN);
         setIntent.addCategory(Intent.CATEGORY_HOME);
         setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(setIntent); 

      


     return;
	}*/


    /*---------------Constructors--------------------------------------------------------------------*/
    public KiwiGLSurfaceView(Context context) {
     	

      super(context);
      Log.d(TAG ,"KiwiGLSurfaceView(Context context)");
      init();
    }


    public KiwiGLSurfaceView(Context context, AttributeSet attrs) {
      super(context, attrs);
      Log.d(TAG ,"KiwiGLSurfaceView(Context context,  AttributeSet attrs)");
      init();
    }

    /*----------------------init ---------------------------------------------------------------------*/
    private void init() {
        Log.d(TAG ,"init()");

      mGestureDetector = new GestureDetector(new MyGestureDetector());
      tryPreserveEGLContext();
      initEGL(true, 8, 0);
    }


    /*----------------------tryPreserveEGLContext -----------------------------------------------------*/
    private void tryPreserveEGLContext() {

      Log.d(TAG ,"tryPreserveEGLContext()");

      try {
        Method preserveEGLContextMethod =
          KiwiGLSurfaceView.class.getMethod("setPreserveEGLContextOnPause",
                                            new Class[] {boolean.class});
          try {
            preserveEGLContextMethod.invoke(KiwiGLSurfaceView.this, true);
           }
          catch (InvocationTargetException ite) {
            Log.i(TAG, String.format("exception invoking setPreserveEGLContextOnPause()"));
          }
          catch (IllegalAccessException ie) {
            Log.i(TAG, String.format("exception accessing setPreserveEGLContextOnPause()"));
          }
      }
      catch (NoSuchMethodException nsme) {
        Log.i(TAG, String.format("api does not have setPreserveEGLContextOnPause()"));
      }
    }

    /*----------------------onPause--------------------------------------------------------------------*/
    @Override
    public void onPause() {
        Log.d(TAG ,"onPause()");

      stopRendering();
      super.onPause();
    }
    
    /*----------------------onBackPressed--------------------------------------------------------------------*/
    public void onBackPressed() {
	     
		 Log.d(TAG, "onBackPressed Called");
		 super.surfaceDestroyed(getHolder());
		 KiwiNative.onBackPressed();

		 
		 /*Intent setIntent = new Intent(Intent.ACTION_MAIN);
         setIntent.addCategory(Intent.CATEGORY_HOME);
         setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(setIntent);
      return;
      
		    */
    }
     
    /*----------------------onStop--------------------------------------------------------------------*/
    /*public void onStop() {
        Log.d(TAG ,"onStop()");

      stopRendering();
      KiwiGLSurfaceView.;
    }*/

    /*----------------------onTouchEvent-------------------------------------------------------------------------------*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG ,"onTouchEvent()");

      mGestureDetector.onTouchEvent(event);
      return multiTouchController.onTouchEvent(event);
    }


    /*----------------------queueEvent-------------------------------------------------------------------------------*/
    @Override
    public void queueEvent(Runnable r) {

        Log.d(TAG ,"queueEvent()");

      if (mRenderer.isInitialized) {
    	  Log.d(TAG ,"mRenderer.isInitialized == true");
        super.queueEvent(r);
      }
      else {
    	  Log.d(TAG ,"mRenderer.isInitialized == false");
        mRenderer.queuePostInitEvent(r);
      }
    }

    /*----------------------postLoadDefaultDataset--------------------------------------------------------------------*/
    public void postLoadDefaultDataset(final ViewerActivity loader, final String storageDir) {
    	
       
        this.queueEvent(new Runnable() {
        	
           public void run() {
               Log.d(TAG+" postLoadDefaultDataset()" ,"queue event -->run()-->storagedir = "+storageDir);


              KiwiNative.checkForAdditionalDatasets(storageDir/*,storageDir*/);
              //Log.d(TAG+"postLoadDefaultDataset()" ,"queue event -->run()-->KiwiNative.getDatasetIsLoaded() = "+KiwiNative.getDatasetIsLoaded());
              if (!KiwiNative.getDatasetIsLoaded()) {
                //final int defaultDatasetIndex = KiwiNative.getDefaultBuiltinDatasetIndex();
                final int defaultDatasetIndex = MidasNative.giveBuiltinDatasetIndex();
                Log.d(TAG+"postLoadDefaultDataset()" ,"queue event -->run()-->defaultDatasetIndex = "+defaultDatasetIndex);

                KiwiGLSurfaceView.this.post(new Runnable() {
                    public void run() {
                        Log.d(TAG+"postLoadDefaultDataset(),queue event -->run()" ,"KiwiGLSurfaceView.this.post-->run()");

                      loader.loadDataset(defaultDatasetIndex);
                    }
                });
                Log.d(TAG+"postLoadDefaultDataset(),queue event -->run()" ,"KiwiGLSurfaceView.this.post-->run() finished"); 
              }

           }});
        Log.d(TAG+" postLoadDefaultDataset()" ,"queue event -->run()-->finished");

    }

    /*----------------------selectObject-----------------------------------------------------------------------------*/
    public void selectObject(KiwiGLSurfaceView obj, PointInfo touchPoint) {

        Log.d(TAG,"selectObject");

      if (obj == null) {
        this.queueEvent(new Runnable() {
                 public void run() {
                    KiwiNative.handleSingleTouchUp();
                    requestRender();
                 }});
      }
    }


    /*----------------------getDraggableObjectAtPoint--------------------------------------------------------------------*/
    public KiwiGLSurfaceView getDraggableObjectAtPoint(PointInfo touchPoint) {

      final float x = touchPoint.getX();
      final float y = touchPoint.getY();
      Log.d(TAG,"getDraggableObjectAtPoint");

      this.queueEvent(new Runnable() {
                 public void run() {
                    KiwiNative.handleSingleTouchDown(x, y);
                    requestRender();
                 }});

      return this;
    }


    /*----------------------getPositionAndScale--------------------------------------------------------------------*/
    public void getPositionAndScale(KiwiGLSurfaceView obj, PositionAndScale pos) {

      float xOff = 0.0f;
      float yOff = 0.0f;
      float scale = 1.0f;
      float scaleX = 1.0f;
      float scaleY = 1.0f;
      float angle = 0.0f;
      boolean updateScale = true;
      boolean updateAngle = true;
      boolean updateScaleXY = false;

      Log.d(TAG,"getPositionAndScale");

      pos.set(xOff, yOff, updateScale, scale, updateScaleXY, scaleX, scaleY, updateAngle, angle);

      this.mLastTouchInfo = null;
    }


    /*----------------------setPositionAndScale--------------------------------------------------------------------*/
    public boolean setPositionAndScale(KiwiGLSurfaceView obj, PositionAndScale pos, PointInfo info) {

      Log.d(TAG,"setPositionAndScale");
	
      if (this.mLastTouchInfo == null) {
        this.mLastTouchInfo = new PointInfo();
        this.mLastTouchInfo.set(info);
        return true;
      }

      this.mCurrentTouchInfo.set(info);

      boolean isMulti = mLastTouchInfo.isMultiTouch();

      float dx = mCurrentTouchInfo.getX() - mLastTouchInfo.getX();
      float dy = mCurrentTouchInfo.getY() - mLastTouchInfo.getY();
      float x1 = mCurrentTouchInfo.getX();
      float y1 = mCurrentTouchInfo.getY();
      float x0 = x1 - dx;
      float y0 = y1 + dy;
      float scale = 1.0f;
      float angle = 0.0f;

      if (isMulti && mLastTouchInfo.getMultiTouchDiameter() != 0.0f) {
        scale = mCurrentTouchInfo.getMultiTouchDiameter() / mLastTouchInfo.getMultiTouchDiameter();
      }

      if (isMulti) {
        angle = mCurrentTouchInfo.getMultiTouchAngle() - mLastTouchInfo.getMultiTouchAngle();
      }

      MyRunnable myrun = new MyRunnable();
      myrun.dx = dx;
      myrun.dy = dy;
      myrun.x0 = x0;
      myrun.y0 = y0;
      myrun.x1 = x1;
      myrun.y1 = y1;
      myrun.scale = scale;
      myrun.angle = angle;
      myrun.isMulti = isMulti;
      this.queueEvent(myrun);

      mLastTouchInfo.set(mCurrentTouchInfo);
      return true;
    }
    

    /*----------------------loadDataset (2 param)--------------------------------------------------------------------*/
    public void loadDataset(final String filename, final ViewerActivity loader) {
        Log.d(TAG,"loadDataset(filename="+filename+"ViewerActivityloader)");

      int builtinDatasetIndex = -1;
     //int builtinDatasetIndex = MidasNative.giveBuiltinDatasetIndex();
      loadDataset(filename, builtinDatasetIndex, loader);
    }

    /*----------------------loadDataset (3 param)--------------------------------------------------------------------*/
    public void loadDataset(final String filename, final int builtinDatasetIndex, final ViewerActivity loader) {
        Log.d(TAG,"loadDataset(filename="+filename+"builtinDatasetIndex="+builtinDatasetIndex+"ViewerActivityloader)");


      queueEvent(new Runnable() {
        public void run() {
        	Log.d(TAG,"loadDataset+queueevent-->run builtinDatasetIndex = "+ builtinDatasetIndex);
          final boolean result = KiwiNative.loadDataset(filename, builtinDatasetIndex);
          final String errorTitle = KiwiNative.getLoadDatasetErrorTitle();
          final String errorMessage = KiwiNative.getLoadDatasetErrorMessage();

          requestRender();

          KiwiGLSurfaceView.this.post(new Runnable() {
            public void run() {
                Log.d(TAG+"loadDataset(),queue event -->run()" ,"KiwiGLSurfaceView.this.post-->run()");

              loader.postLoadDataset(filename, result, errorTitle, errorMessage);
            }});
        }});
    }


    /*----------------------resetCamera -----------------------------------------------------------------------------*/
    public void resetCamera() {
    	Log.d(TAG,"resetCamera");
      queueEvent(new Runnable() {
                   public void run() {
                      KiwiNative.resetCamera();
                      requestRender();
                   }});
    }

    /*----------------------stopRendering ---------------------------------------------------------------------------*/
    public void stopRendering() {
    	Log.d(TAG,"stopRendering");

      queueEvent(new Runnable() {
                   public void run() {
                      KiwiNative.stopInertialMotion();
                      setRenderMode(RENDERMODE_WHEN_DIRTY);
                   }});
    }


    /*----------------------initEGL ----------------------------------------------------------------------------------*/
    private void initEGL(boolean translucent, int depth, int stencil) {

    	Log.d(TAG,"initEGL");

        /* By default, GLSurfaceView() creates a RGB_565 opaque surface.
         * If we want a translucent one, we should change the surface's
         * format here, using PixelFormat.TRANSLUCENT for GL Surfaces
         * is interpreted as any 32-bit surface with alpha by SurfaceFlinger.
         */
    	
        if (translucent) {
            this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }

        /* Setup the context factory for 2.0 rendering.
         * See ContextFactory class definition below
         */
        setEGLContextFactory(new ContextFactory());

        /* We need to choose an EGLConfig that matches the format of
         * our surface exactly. This is going to be done in our
         * custom config chooser. See ConfigChooser class definition
         * below.
         */
        setEGLConfigChooser( translucent ?
                             new ConfigChooser(8, 8, 8, 8, depth, stencil) :
                             new ConfigChooser(5, 6, 5, 0, depth, stencil) );

        /* Set the renderer responsible for frame rendering */
        mRenderer = new MyRenderer();
        mRenderer.parentView = this;
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        requestRender();
    }

    /*-----------------  CLASS ContextFactory ------------------------------------------------------------------------*/
    private static class ContextFactory implements GLSurfaceView.EGLContextFactory {
        private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;
        
        /*--------- createContext-------------------------------------------------------------*/
        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        	Log.d(TAG+"class ContextFactory","createContext");

            Log.w(TAG, "creating OpenGL ES 2.0 context");
            checkEglError("Before eglCreateContext", egl);
            int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
            EGLContext context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
            checkEglError("After eglCreateContext", egl);
            return context;
        }

        /*--------- destroyContext-------------------------------------------------------------*/
        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
        	Log.d(TAG+"class ContextFactory","destroyContext");
            egl.eglDestroyContext(display, context);
            
        }
    }

    /*-----------------  checkEglError ---------------------------------------------------------------------*/
    private static void checkEglError(String prompt, EGL10 egl) {
    	Log.d(TAG,"checkEglError");

        int error;
        while ((error = egl.eglGetError()) != EGL10.EGL_SUCCESS) {
            Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, error));
        }
    }
    
    /*-----------------  CLASS ConfigChooser ----------------------------------------------------------------*/
    private static class ConfigChooser implements GLSurfaceView.EGLConfigChooser {

        /*--------- Constructor-------------------------------------------------------------*/
        public ConfigChooser(int r, int g, int b, int a, int depth, int stencil) {
        	Log.d(TAG+"class ConfigChooser","constructor");

            mRedSize = r;
            mGreenSize = g;
            mBlueSize = b;
            mAlphaSize = a;
            mDepthSize = depth;
            mStencilSize = stencil;
        }

        /* This EGL config specification is used to specify 2.0 rendering.
         * We use a minimum size of 4 bits for red/green/blue, but will
         * perform actual matching in chooseConfig() below.
         */
        private static int EGL_OPENGL_ES2_BIT = 4;
        private static int[] s_configAttribs2 =
        {
            EGL10.EGL_RED_SIZE, 4,
            EGL10.EGL_GREEN_SIZE, 4,
            EGL10.EGL_BLUE_SIZE, 4,
            EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
            EGL10.EGL_NONE
        };

        /*--------- chooseConfig (2 param )-----------------------------------------------------*/
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {

            /* Get the number of minimally matching EGL configurations
             */
        	Log.d(TAG+"class ConfigChooser","chooseConfig 2 param");

            int[] num_config = new int[1];
            egl.eglChooseConfig(display, s_configAttribs2, null, 0, num_config);

            int numConfigs = num_config[0];

            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }

            /* Allocate then read the array of minimally matching EGL configs
             */
            EGLConfig[] configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, s_configAttribs2, configs, numConfigs, num_config);

            if (DEBUG) {
                 printConfigs(egl, display, configs);
            }
            /* Now return the "best" one
             */
            return chooseConfig(egl, display, configs);
        }

        /*--------- chooseConfig (3 param) -----------------------------------------------------*/
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display,EGLConfig[] configs) {
        	
        	Log.d(TAG+"class ConfigChooser","chooseConfig 3 param");

            for(EGLConfig config : configs) {
                int d = findConfigAttrib(egl, display, config,
                        EGL10.EGL_DEPTH_SIZE, 0);
                int s = findConfigAttrib(egl, display, config,
                        EGL10.EGL_STENCIL_SIZE, 0);

                // We need at least mDepthSize and mStencilSize bits
                if (d < mDepthSize || s < mStencilSize)
                    continue;

                // We want an *exact* match for red/green/blue/alpha
                int r = findConfigAttrib(egl, display, config,
                        EGL10.EGL_RED_SIZE, 0);
                int g = findConfigAttrib(egl, display, config,
                            EGL10.EGL_GREEN_SIZE, 0);
                int b = findConfigAttrib(egl, display, config,
                            EGL10.EGL_BLUE_SIZE, 0);
                int a = findConfigAttrib(egl, display, config,
                        EGL10.EGL_ALPHA_SIZE, 0);

                if (r == mRedSize && g == mGreenSize && b == mBlueSize && a == mAlphaSize) {

                    printConfig(egl, display, config);
                    return config;
                }
            }
            return null;
        }
        
        /*--------- findConfigAttrib -----------------------------------------------------*/
        private int findConfigAttrib(EGL10 egl, EGLDisplay display,EGLConfig config, int attribute, int defaultValue) {

        	Log.d(TAG+"class ConfigChooser","findConfigAttrib");

            if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
                return mValue[0];
            }
            return defaultValue;
        }

        /*--------- printConfigs (2 param ) -----------------------------------------------------*/
        private void printConfigs(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
        	Log.d(TAG+"class ConfigChooser","printConfigs 3 param");

            int numConfigs = configs.length;
            Log.w(TAG, String.format("%d configurations", numConfigs));
            for (int i = 0; i < numConfigs; i++) {
                Log.w(TAG, String.format("Configuration %d:\n", i));
                printConfig(egl, display, configs[i]);
            }
        }

        /*--------- printConfigs (3 param ) -----------------------------------------------------*/
        private void printConfig(EGL10 egl, EGLDisplay display, EGLConfig config) {
            
        	Log.d(TAG+"class ConfigChooser","printConfigs 2 param");

        	int[] attributes = {
                    EGL10.EGL_BUFFER_SIZE,
                    EGL10.EGL_ALPHA_SIZE,
                    EGL10.EGL_BLUE_SIZE,
                    EGL10.EGL_GREEN_SIZE,
                    EGL10.EGL_RED_SIZE,
                    EGL10.EGL_DEPTH_SIZE,
                    EGL10.EGL_STENCIL_SIZE,
                    EGL10.EGL_CONFIG_CAVEAT,
                    EGL10.EGL_CONFIG_ID,
                    EGL10.EGL_LEVEL,
                    EGL10.EGL_MAX_PBUFFER_HEIGHT,
                    EGL10.EGL_MAX_PBUFFER_PIXELS,
                    EGL10.EGL_MAX_PBUFFER_WIDTH,
                    EGL10.EGL_NATIVE_RENDERABLE,
                    EGL10.EGL_NATIVE_VISUAL_ID,
                    EGL10.EGL_NATIVE_VISUAL_TYPE,
                    0x3030, // EGL10.EGL_PRESERVED_RESOURCES,
                    EGL10.EGL_SAMPLES,
                    EGL10.EGL_SAMPLE_BUFFERS,
                    EGL10.EGL_SURFACE_TYPE,
                    EGL10.EGL_TRANSPARENT_TYPE,
                    EGL10.EGL_TRANSPARENT_RED_VALUE,
                    EGL10.EGL_TRANSPARENT_GREEN_VALUE,
                    EGL10.EGL_TRANSPARENT_BLUE_VALUE,
                    0x3039, // EGL10.EGL_BIND_TO_TEXTURE_RGB,
                    0x303A, // EGL10.EGL_BIND_TO_TEXTURE_RGBA,
                    0x303B, // EGL10.EGL_MIN_SWAP_INTERVAL,
                    0x303C, // EGL10.EGL_MAX_SWAP_INTERVAL,
                    EGL10.EGL_LUMINANCE_SIZE,
                    EGL10.EGL_ALPHA_MASK_SIZE,
                    EGL10.EGL_COLOR_BUFFER_TYPE,
                    EGL10.EGL_RENDERABLE_TYPE,
                    0x3042 // EGL10.EGL_CONFORMANT
            };
            String[] names = {
                    "EGL_BUFFER_SIZE",
                    "EGL_ALPHA_SIZE",
                    "EGL_BLUE_SIZE",
                    "EGL_GREEN_SIZE",
                    "EGL_RED_SIZE",
                    "EGL_DEPTH_SIZE",
                    "EGL_STENCIL_SIZE",
                    "EGL_CONFIG_CAVEAT",
                    "EGL_CONFIG_ID",
                    "EGL_LEVEL",
                    "EGL_MAX_PBUFFER_HEIGHT",
                    "EGL_MAX_PBUFFER_PIXELS",
                    "EGL_MAX_PBUFFER_WIDTH",
                    "EGL_NATIVE_RENDERABLE",
                    "EGL_NATIVE_VISUAL_ID",
                    "EGL_NATIVE_VISUAL_TYPE",
                    "EGL_PRESERVED_RESOURCES",
                    "EGL_SAMPLES",
                    "EGL_SAMPLE_BUFFERS",
                    "EGL_SURFACE_TYPE",
                    "EGL_TRANSPARENT_TYPE",
                    "EGL_TRANSPARENT_RED_VALUE",
                    "EGL_TRANSPARENT_GREEN_VALUE",
                    "EGL_TRANSPARENT_BLUE_VALUE",
                    "EGL_BIND_TO_TEXTURE_RGB",
                    "EGL_BIND_TO_TEXTURE_RGBA",
                    "EGL_MIN_SWAP_INTERVAL",
                    "EGL_MAX_SWAP_INTERVAL",
                    "EGL_LUMINANCE_SIZE",
                    "EGL_ALPHA_MASK_SIZE",
                    "EGL_COLOR_BUFFER_TYPE",
                    "EGL_RENDERABLE_TYPE",
                    "EGL_CONFORMANT"
            };
            int[] value = new int[1];
            for (int i = 0; i < attributes.length; i++) {
                int attribute = attributes[i];
                String name = names[i];
                if ( egl.eglGetConfigAttrib(display, config, attribute, value)) {
                    Log.w(TAG, String.format("  %s: %d\n", name, value[0]));
                } else {
                    // Log.w(TAG, String.format("  %s: failed\n", name));
                    while (egl.eglGetError() != EGL10.EGL_SUCCESS);
                }
            }
        }

        /*-------------Subclasses can adjust these values:*/
        protected int mRedSize;
        protected int mGreenSize;
        protected int mBlueSize;
        protected int mAlphaSize;
        protected int mDepthSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];
    }

  }

  /*---------------CLASS MyRenderer----------------------------------------------------------------------------*/
  class MyRenderer implements GLSurfaceView.Renderer {
  
    private static final String TAG = "KiwiGLSurfaceView";
    public GLSurfaceView parentView;
    public boolean isInitialized = false;
    public ArrayList<Runnable> mPostInitRunnables = new ArrayList<Runnable>();
    public ArrayList<Runnable> mPreRenderRunnables = new ArrayList<Runnable>();
  
    /*--------- queuePostInitEvent -----------------------------------------------------*/
    synchronized void queuePostInitEvent(Runnable runnable) {
    	Log.d(TAG+"class MyRenderer","queuePostInitEvent");
    	
      mPostInitRunnables.add(runnable);
      Log.d(TAG,"queuePostInitEvent-->mPostInitRunnables added");
    }

    /*--------- queuePreRenderEvent -----------------------------------------------------*/
    synchronized void queuePreRenderEvent(Runnable runnable) {
  		Log.d(TAG+"class MyRenderer","queuePreRenderEvent");
      mPreRenderRunnables.add(runnable);
    }

    /*--------- onDrawFrame ---------------------------------------------------------------*/
    public void onDrawFrame(GL10 gl) {

		  Log.d(TAG+"class MyRenderer","onDrawFrame");

      boolean result = KiwiNative.render();
      if (result) {
        parentView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
      }
      else {
        parentView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
      }

      while (mPreRenderRunnables.size() > 0) {
        mPreRenderRunnables.remove(0).run();
      }
    }
    
    /*--------- onSurfaceChanged -----------------------------------------------------------*/
    public void onSurfaceChanged(GL10 gl, int width, int height) {
  		Log.d(TAG+"class MyRenderer","onSurfaceChanged");
  
        KiwiNative.reshape(width, height);
    }

   
    /*--------- onSurfaceCreated -----------------------------------------------------------*/
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
  		Log.d(TAG+"class MyRenderer","onSurfaceCreated");

      KiwiNative.init(100, 100);
      isInitialized = true;

      while (mPostInitRunnables.size() > 0) {
        mPostInitRunnables.remove(0).run();
      }

    }
}

