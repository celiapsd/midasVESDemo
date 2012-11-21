#include <sys/types.h>
#include <android/log.h>

#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <vector>
#include <iostream>
#include <sstream>
#include <fstream>
#include <cassert>


#include <MyProgressDelegate.h>
#include <midasFilesTools.h>
#include <jni.h>



#define  LOG_TAG    "MyProgressDelegate"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//----------------------------------------------------------------------------


MyProgressDelegate::MyProgressDelegate()
{
    mFilesTool = 0;
    totalBytes = 0;
    shouldAbort = false;
    mProgressDialog = NULL;
    LOGI("MyProgressDelegate");

}
/*void MyProgressDelegate::initProgressDialog(JNIEnv *env)
{
   // this->mFilesTool->getJNIEnv();
    if (!env)
    {
        LOGI("Failed to get env");
    }

    jclass cls = env->FindClass("Landroid/app/ProgressDialog");
    if (cls == NULL) {
            if (env->ExceptionOccurred()) {
                env->ExceptionDescribe();
            }
            LOGI("Failed to get class");
      }
    else
    {
        LOGI("class ok");

    }
    jmethodID constructor = env->GetMethodID(cls, "<init>", "(Landroid/app/Activity)V");
    if (!constructor)
    {
        LOGI("Failed to get constructor");
    }
    mProgressDialog =  env->NewObject(cls, constructor);
    if (!mProgressDialog)
      LOGI(" mProgressDialog nok");
}*/

void MyProgressDelegate::setFilesTool(midasFilesTools * filesTool)
{
  this->mFilesTool = filesTool;
}
void MyProgressDelegate::setTotalBytes(int totalBytes)
{
  this->totalBytes = totalBytes;
}
 void MyProgressDelegate::setProgressDialog(jobject* progressDialog)
 {
     this->mProgressDialog = progressDialog;
 }

int MyProgressDelegate::downloadProgress(double totalToDownload, double nowDownloaded)
{
  LOGI("downloadProgress");

  LOGI("   nowDownloaded = %d",nowDownloaded);
  //LOGI("   totalToDownload = %d",totalToDownload);
  //LOGI("   totalbytes = %d", this->totalBytes);


    int progress = (nowDownloaded /  this->totalBytes)*100;
    LOGI("downloadProgress progress = %d", progress);

    jclass cls = this->mFilesTool->getJNIEnv()->FindClass("android/app/ProgressDialog");
    if(cls)
        LOGI("class cls ok");
    else
        LOGI("class cls nok");

    jmethodID method = this->mFilesTool->getJNIEnv()->GetMethodID(cls, "setProgress", "(I)V");
    if(method)
        LOGI("method  ok");
    else
        LOGI("method  nok");

    if (progress < 0)
    {
        LOGI(" progress = 0;");
        progress = 0;
    }
    else if(progress > 100)
    {
        LOGI(" progress =100;");
        progress = 100;
    }

    if (mProgressDialog)
    {
        LOGI(" mProgressDialog not null");
      this->mFilesTool->getJNIEnv()->CallVoidMethod(*mProgressDialog, method, progress);
      LOGI("CallVoidMethod  ok");
    }
    else
       LOGI("CallVoidMethod  nok");

    /*fid = this->mFilesTool->env->GetFieldID(env,cls,"mProgressDialog","Lcom/kitware/KiwiViewer/DownloadFileActivity;");
    jobject jProgressDialog = this->mFilesTool->env->GetObjectField(env, obj, fid);*/

    //(*env)->SetObjectField(env,info,fid,name);


    if (shouldAbort) {
      return 1;
    }
    else {
      return 0;
    }
}

