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


#include <CProgressDialog.h>



#define  LOG_TAG    "CProgressDialog"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//----------------------------------------------------------------------------

CProgressDialog::CProgressDialog()
{
    LOGI("constructor CProgressDialog");
    init(0, 0, 0);

}
CProgressDialog::~CProgressDialog()
{
    LOGI("destructor CProgressDialog");
}
//----------------------------------------------------------------------------
void CProgressDialog::init(const int progress,const int totalToDownload,const int itemBytes)
{
     LOGI("init");
     setProgress(progress);
     setTotalToDownload(totalToDownload);
     setItemBytes(itemBytes);
}
//----------------------------------------------------------------------------
void CProgressDialog::setProgress(const int& mprogress)
{
     //LOGI("setID");
     this->progress = mprogress;
}
//----------------------------------------------------------------------------
void CProgressDialog::setTotalToDownload(const int& mtotalToDownload)
{
    this->totalToDownload = mtotalToDownload;
}
//----------------------------------------------------------------------------
void CProgressDialog::setItemBytes(const int& mItemBytes)
{
    this->itemBytes = mItemBytes;
}
//----------------------------------------------------------------------------
int CProgressDialog::getProgress() const
{
     //LOGI("setID");
     return this->progress;
}
//----------------------------------------------------------------------------
int CProgressDialog::getTotalToDownload() const
{
    return this->totalToDownload;
}
//----------------------------------------------------------------------------
int CProgressDialog::getItemBytes() const
{
    return this->itemBytes;
}
