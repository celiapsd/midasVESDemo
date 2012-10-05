

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

//-------------------------------------------------------------------------------------------
void init()
{
   LOGI1("init()");
   app = new vesMidasApp();
   LOGI1("MidasApp initialized");
}

/*void putInDatabase(const std::string& filename, const std::string& path)
{
    LOGI("putindatabase filename");
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

    app->checkForAdditionalData(filename,path);
      LOGI("putindatabase finish");
}*/

};// end namespace

//-------------------------------------------------------------------------------------------

extern "C" {
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_init(JNIEnv * env, jobject obj,  jint width, jint height);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_putInDatabase(JNIEnv * env, jobject obj, jstring filename, jstring path);
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_loadDataset(JNIEnv * env, jobject obj, jstring filename, jint builtinDatasetIndex);
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_giveBuiltinDatasetIndex(JNIEnv* env, jobject obj);

};
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_init(JNIEnv * env, jobject obj,  jint width, jint height)
{
  LOGI1("JNICALL init(%d, %d)", width, height);

  init();//width, height);
}
//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_putInDatabase
  (JNIEnv *env, jobject obj, jstring filename,jstring path)
{
   /* LOGI1("JNICALL1 putInDataBase()");
       const char *javaStr1 = env->GetStringUTFChars(filename, NULL);
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
*/
}
//-------------------------------------------------------------------------------------------
JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_giveBuiltinDatasetIndex(JNIEnv* env, jobject obj)
{
    LOGI1("giveBuiltinDatasetIndex()");

/*  int size= app->numberOfBuiltinDatasets();
  LOGI1("size = %d",app->numberOfBuiltinDatasets());
  appState.builtinDatasetIndex=size;

   LOGI1("index %d",appState.builtinDatasetIndex);
  return appState.builtinDatasetIndex;*/
}

//-------------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_MidasNative_loadDataset
  (JNIEnv *env, jobject obj, jstring filename, jint builtinDatasetIndex)
{
    LOGI1("JNICALL loadDataset(%d, %d)", filename, builtinDatasetIndex);


}
