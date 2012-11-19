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



#define  LOG_TAG    "MyProgressDelegate"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//----------------------------------------------------------------------------


void MyProgressDelegate::setFilesTool(midasFilesTools * filesTool)
{
  this->mFilesTool = filesTool;
}
void MyProgressDelegate::setTotalBytes(int totalBytes)
{
  this->totalBytes = totalBytes;
}

int MyProgressDelegate::downloadProgress(double totalToDownload, double nowDownloaded)
{
  //LOGI("downloadProgress");

  //LOGI("   nowDownloaded = %d",nowDownloaded);
  //LOGI("   totalToDownload = %d",totalToDownload);
  //LOGI("   totalbytes = %d", this->totalBytes);


    double progress = (nowDownloaded /  this->totalBytes);//*100;
    //LOGI("downloadProgress progress = %d",progress);

    if (this->mFilesTool)
    {
       //LOGI("mFilesTools not null");
      this->mFilesTool->setProgressDownload(progress);
    }

    if (shouldAbort) {
      return 1;
    }
    else {
      return 0;
    }
}

