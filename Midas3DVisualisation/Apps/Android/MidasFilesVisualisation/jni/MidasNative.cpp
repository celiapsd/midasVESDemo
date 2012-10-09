

#include <jni.h>
#include <sys/types.h>
#include <android/log.h>
#include <cassert>
#include <fstream>
#include <vesKiwiViewerApp.h>
#include <string.h>
#include <vesMidasApp.h>


#define  LOG_TAG1    "MidasNative"
#define  LOGI1(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG1,__VA_ARGS__)
#define  LOGW1(...)  __android_log_print(ANDROID_LOG_WARN, LOG_TAG1,__VA_ARGS__)
#define  LOGE1(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG1,__VA_ARGS__)

// This include uses the LOGI, LOGW, LOGE macro definitions
//#include "vtkAndroidOutputWindow.h"


namespace {

vesMidasApp* app;
//vesKiwiViewerApp* app;

//-------------------------------------------------------------------------------------------
void init(int width,int height)
{
   LOGI1("init(2)");
   app = new vesMidasApp();
   app->initCamera(width,height);
   LOGI1("MidasApp initialized");
}
void init(int width,int height,const std::string& filename, const std::string& path)
{
   LOGI1("init(4)");
   app = new vesMidasApp();
   LOGI1("MidasApp initialized");
   //putInDatabase(filename,path);
   app->addBuiltinDataset(filename,path);
   LOGI1("file added");
   app->initCamera(width,height);
   app->loadDataset(filename);
      LOGI1("file loaded");
   app->restoreCameraState();
   app->initTime();
}

void putInDatabase(const std::string& filename, const std::string& path)
{
    /*LOGI("putindatabase filename");
    //LOGI1("loadDataset(%s,%s)", filename.c_str(),path.c_str());
    //appDetails.currentDataset = filename;
    //int found=filename.find(".");
   // std::string name = name.substr(filename.find("."));
    LOGI("putindatabase name");
    //LOGI("putindatabase name %s",name.c_str());
    if (filename.c_str())
        {
        LOGI("name: %s", filename.c_str());
        }
    else
    {
        LOGI("no name");
    }
    if (filename.c_str())
        {
        LOGI("path: %s", path.c_str());
        }
    else
    {
            LOGI("no path");
    }
    LOGI("end putindatabase name");
    if(app)
        LOGI("app");

    app->addBuiltinDataset(filename,path);
      LOGI("putindatabase finish");*/
}

//----------------------------------------------------------------------------
void loadDataset(const std::string& filename, int builtinDatasetIndex)
{
    if( filename.c_str()) {
     LOGI1("loadDataset(%s),builtinDatasetIndex = %d", filename.c_str(),builtinDatasetIndex);
    }

    else{
        LOGI1("loadDataset(filename == null)" );
    }


    app->setParametersDataset(filename,builtinDatasetIndex);

    bool result = app->loadDataset(filename);
    if (result) {
    app->resetView();
    }

}
//----------------------------------------------------------------------------
void resetView()
{
  if(app->getBuiltinDatasetIndex()!=0) {
    LOGI1("RESETVIEW buildinDatasetIndex = %d",app->getBuiltinDatasetIndex());
  }
  else{
      LOGI1("RESETVIEW buildinDatasetIndex = null");
  }

  if (app->getBuiltinDatasetIndex() >= 0) {
      LOGI1("applyBuiltinDatasetCameraParameters");
    app->applyBuiltinDatasetCameraParameters(app->getBuiltinDatasetIndex());
  }
  else {
        LOGI1("resetView");
    app->resetView();
  }
}
};// end namespace

//-------------------------------------------------------------------------------------------

extern "C" {
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_init(JNIEnv * env, jobject obj,  jint width, jint height);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_initFile(JNIEnv * env, jobject obj,  jint width, jint height, jstring filename, jstring path);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_putInDatabase(JNIEnv * env, jobject obj, jstring filename, jstring path);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_loadDataset(JNIEnv * env, jobject obj, jstring filename, jint builtinDatasetIndex);
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_giveCurrentBuiltinDatasetIndex(JNIEnv* env, jobject obj);
JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getLoadDatasetErrorTitle(JNIEnv* env, jobject obj);
JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getLoadDatasetErrorMessage(JNIEnv* env, jobject obj);
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDefaultBuiltinDatasetIndex(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_clearExistingDataset(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_reshape(JNIEnv * env, jobject obj,  jint width, jint height);
JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_render(JNIEnv * env, jobject obj);


JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchPanGesture(JNIEnv * env, jobject obj,  jfloat dx, jfloat dy);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleTwoTouchPanGesture(JNIEnv * env, jobject obj,  jfloat x0, jfloat y0, jfloat x1, jfloat y1);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleTwoTouchPinchGesture(JNIEnv * env, jobject obj,  jfloat scale);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleTwoTouchRotationGesture(JNIEnv * env, jobject obj,  jfloat rotation);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchDown(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchUp(JNIEnv * env, jobject obj);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchTap(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleDoubleTap(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleLongPress(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_resetCamera(JNIEnv * env, jobject obj);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_stopInertialMotion(JNIEnv * env, jobject obj);
JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDatasetIsLoaded(JNIEnv* env, jobject obj);

JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDatasetFilename(JNIEnv* env, jobject obj, jint offset);
JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDatasetName(JNIEnv* env, jobject obj, jint offset);
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getNumberOfBuiltinDatasets(JNIEnv* env, jobject obj);


};
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_init(JNIEnv * env, jobject obj,  jint width, jint height)
{
  LOGI1("JNICALL init(%d, %d)", width, height);

  init(width, height);
}
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_initFile(JNIEnv * env, jobject obj,  jint width, jint height, jstring filename, jstring path)
{
  LOGI1("JNICALL init(%d, %d)", width, height);



  const char *javaStr1 = env->GetStringUTFChars(filename, NULL);
  const char *javaStr2 = env->GetStringUTFChars(path, NULL);
  if (javaStr1 && javaStr2)
    {
    std::string filenameStr = javaStr1;
    std::string pathStr = javaStr2;
    env->ReleaseStringUTFChars(filename, javaStr1);
    env->ReleaseStringUTFChars(path, javaStr2);
    LOGI1("before putInDataBase()");

    init(width, height,filenameStr,pathStr);
  }
    /*if(app)
      {
          LOGI1("app not null");
          putInDatabase(filenameStr,pathStr);
          //putInDatabase(filenameStr, pathStr);
          LOGI1("after putInDataBase()");}*/
   else
     LOGI1("objects not created");


}
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_putInDatabase
  (JNIEnv *env, jobject obj, jstring filename,jstring path)
{
   LOGI1("JNICALL1 putInDataBase()");
      /* const char *javaStr1 = env->GetStringUTFChars(filename, NULL);
     LOGI1("JNICALL1 javaStr1()");
    const char *javaStr2 = env->GetStringUTFChars(path, NULL);
     LOGI1("JNICALL1 javaStr2()");
    if (javaStr1 && javaStr2)
    {
      std::string filenameStr = javaStr1;
      std::string pathStr = javaStr2;
      env->ReleaseStringUTFChars(filename, javaStr1);
      env->ReleaseStringUTFChars(path, javaStr2);
       LOGI1("before putInDataBase()");


       if(app)
       {
           LOGI1("app not null");
           app->checkForAdditionalData( pathStr);
           //putInDatabase(filenameStr, pathStr);
           LOGI1("after putInDataBase()");}
       else
         LOGI1("app null");
    }
    app->addBuiltinDataset(filenameStr,pathStr);
    }*/

}
JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getLoadDatasetErrorTitle(JNIEnv* env, jobject obj)
{
  std::string str = app->loadDatasetErrorTitle();
  return env->NewStringUTF(str.c_str());
}

JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getLoadDatasetErrorMessage(JNIEnv* env, jobject obj)
{
  std::string str = app->loadDatasetErrorMessage();
  return env->NewStringUTF(str.c_str());
}
//-------------------------------------------------------------------------------------------
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_giveCurrentBuiltinDatasetIndex(JNIEnv* env, jobject obj)
{
    LOGI1("getBuiltinDatasetIndex()");

  return app->getBuiltinDatasetIndex();
}

//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_loadDataset
  (JNIEnv *env, jobject obj, jstring filename, jint builtinDatasetIndex)
{
    LOGI1("JNICALL loadDataset()");

    const char *javaStr = env->GetStringUTFChars(filename, NULL);
    if (javaStr) {
      std::string filenameStr = javaStr;
      env->ReleaseStringUTFChars(filename, javaStr);
      if( javaStr ) {
          LOGI1("loadDataset(), filename = not null,buildindatatsetindex = %d",builtinDatasetIndex);
        }
      else{
          LOGI1("loadDataset(), filename = null ");
          }
      loadDataset(filenameStr, builtinDatasetIndex);
    }

}
//-------------------------------------------------------------------------------------------
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDefaultBuiltinDatasetIndex(JNIEnv* env, jobject obj)
{
    LOGI1("getDefaultBuiltinDatasetIndex()");

  return app->defaultBuiltinDatasetIndex();
}
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_clearExistingDataset(JNIEnv * env, jobject obj)
{
  LOGI1("clearExistingDataset()");
  if(app)
  {
    app->clearExistingDataset();
  }
  else
  {
      LOGI1("Dataset empty");
  }
}
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_reshape(JNIEnv * env, jobject obj,  jint width, jint height)
{
  LOGI1("reshape(%d, %d)", width, height);
  app->resizeView(width, height);
}
//-------------------------------------------------------------------------------------------
JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_render(JNIEnv * env, jobject obj)
{
    LOGI1("render()");
    return app->render();
}
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchPanGesture
(JNIEnv * env, jobject obj,  jfloat dx, jfloat dy)
{
     app->handleSingleTouchPanGesture(dx, dy);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleTwoTouchPanGesture
(JNIEnv * env, jobject obj,  jfloat x0, jfloat y0, jfloat x1, jfloat y1)
{
    app->handleTwoTouchPanGesture(x0, y0, x1, y1);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleTwoTouchPinchGesture
(JNIEnv * env, jobject obj,  jfloat scale)
{
     app->handleTwoTouchPinchGesture(scale);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleTwoTouchRotationGesture
(JNIEnv * env, jobject obj,  jfloat rotation)
{
      app->handleTwoTouchRotationGesture(rotation);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchDown
(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
{
     app->handleSingleTouchDown(x, y);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchUp
(JNIEnv * env, jobject obj)
{
    app->handleSingleTouchUp();
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleSingleTouchTap
(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
{
      app->handleSingleTouchTap(x, y);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleDoubleTap
(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
{
    app->handleDoubleTap(x, y);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_handleLongPress
(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
{
     app->handleLongPress(x, y);
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_resetCamera
(JNIEnv * env, jobject obj)
{
    LOGI1("resetCamera");

  resetView();
}

JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_stopInertialMotion
(JNIEnv * env, jobject obj)
{
  app->haltCameraRotationInertia();
}
JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDatasetIsLoaded
(JNIEnv* env, jobject obj)
{

   LOGI1("getDatasetIsLoaded()");
   return app->getDatasetIsLoaded();


}
JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDatasetFilename(JNIEnv* env, jobject obj, jint offset)
{

  std::string name = app->builtinDatasetFilename(offset);
  return(env->NewStringUTF(name.c_str()));
}
JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getDatasetName(JNIEnv* env, jobject obj, jint offset)
{
  std::string name = app->builtinDatasetName(offset);
  return(env->NewStringUTF(name.c_str()));
}
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_getNumberOfBuiltinDatasets(JNIEnv* env, jobject obj)
{
    LOGI1("getNumberOfBuiltinDatasets()");

  return app->numberOfBuiltinDatasets();
}
