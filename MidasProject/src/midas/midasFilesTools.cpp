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


#include <vesMidasClient.h>
#include <vesKiwiCurlDownloader.h>

#include <midasFilesTools.h>
#include <MidasResource.h>
#include <MyProgressDelegate.h>
#include <vesSharedPtr.h>
#include <vtkMutexLock.h>
#include <jni.h>



#define  LOG_TAG    "midasFilesTools"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

typedef std::vector<std::string> vectorOfStrings;

//----------------------------------------------------------------------------

midasFilesTools::midasFilesTools()
{
    LOGI("constructor MidasFilesTools");
    this->midas = new vesMidasClient();
   // this->ProgressDownload = -1;
    this->mutex = new vtkSimpleMutexLock() ;
    this->mEnv = new JNIEnv();

}
midasFilesTools::~midasFilesTools()
{
    LOGI("destructor MidasFilesTools");
    delete this->midas;
}
//----------------------------------------------------------------------------
int midasFilesTools::init(const std::string& url,const std::string& email,const std::string& password)
{
    LOGI("init");

    this->setHost(url);
    int result = this->login(email,password);
    LOGI("login %d",result);
    return result;
    /*return 0;*/
}

//----------------------------------------------------------------------------
std::vector<MidasResource> midasFilesTools::findCommunities()
{
    LOGI("findCommunities");
    std::vector<MidasResource> currentResources;
    this->midas->listCommunities();

    vectorOfStrings folderNames = this->midas->folderNames();
    vectorOfStrings folderIds = this->midas->folderIds();

    LOGI("folderNames FOldersId");

    for (size_t i = 0; i<folderNames.size(); ++i)
    {
        MidasResource res(atoi(folderIds[i].c_str()), folderNames[i], MidasResource::COMMUNITY, 0);
        currentResources.push_back(res);
        database.push_back(res);
    }
    LOGI("ressource ok");

    return currentResources;
}
//----------------------------------------------------------------------------
void midasFilesTools::setHost(const std::string& url)
{
    if (!url.size())
    {
        LOGI("init");
        this->midas->setHost("http://midas3.kitware.com/midas");
    }
    else
    {
        LOGI("init");
        this->midas->setHost(url);
    }

}
//----------------------------------------------------------------------------
int midasFilesTools::login (const std::string& email,const std::string& password)
{
    if (!email.size() && !password.size())
    {
        LOGI("No Login");
        return 0;
    }
    else
    {
        bool result = this->midas->login(email, password);
        if (!result)
        {
          LOGI("Login error");
          return 1;
        }
        LOGI("Login successfully");
        return 2;
    }
}
//----------------------------------------------------------------------------
std::vector<MidasResource> midasFilesTools::findCommunityChildren(const std::string& communityName)
{
    LOGI("findCommunityChildren");
    std::vector<MidasResource> currentResources;

    int communityId;

    for (size_t i = 0; i < database.size(); ++i)
    {
        if (database[i].getName() == communityName) {
          communityId = database[i].getId();
          LOGI("communityId(resources) %d",communityId);
      }
    }

    if (!communityId) {
        LOGI("failed to find community: %s", communityName.c_str());
        return currentResources;
    }
    std::stringstream strCommunityId;
    strCommunityId<<communityId;

    bool result=this->midas->listCommunityChildren(strCommunityId.str());

    if (!result)
    {
        LOGI("failed to find children");
        return currentResources;
    }

    vectorOfStrings folderNames = midas->folderNames();
    vectorOfStrings folderIds = this->midas->folderIds();

    for (size_t i = 0; i<folderNames.size(); ++i)
    {
        MidasResource res(atoi(folderIds[i].c_str()), folderNames[i], MidasResource::FOLDER, 0);
        currentResources.push_back(res);
        database.push_back(res);
    }
    LOGI("ressource ok");

    return currentResources;
}
//----------------------------------------------------------------------------
std::vector<MidasResource> midasFilesTools::findFolderChildren(const std::string& mName)
{
    LOGI("findFolderChildren");

    int mId;
    int mType = 0;
    int mSize;
    std::vector<MidasResource> currentResources;


    for (size_t i = 0; i < database.size(); ++i)
    {
        if (database[i].getName() == mName) {
          mId = database[i].getId();
          LOGI("myId(resources) %d",mId);
          mType = database[i].getType();
          mSize = database[i].getSize();
      }
    }

    switch(mType)
    {
        case MidasResource::NOTSET:
            LOGI("failed to find folder: %s", mName.c_str());
            LOGI("type = 0 --> finalList null");
            break;

        case MidasResource::FOLDER:
            {
            std::stringstream strMyId;
            strMyId<<mId;

            LOGI("type folder");
            this->midas->listFolderChildren(strMyId.str());
            LOGI("type = folder --> listFolderChildren");

            vectorOfStrings folderNames = this->midas->folderNames();
            vectorOfStrings folderIds = this->midas->folderIds();
            vectorOfStrings itemNames = this->midas->itemNames();
            vectorOfStrings itemIds = this->midas->itemIds();
            std::vector<size_t> itemBytes = this->midas->itemBytes();

            for (size_t i = 0; i<folderNames.size(); ++i)
            {
                MidasResource res(atoi(folderIds[i].c_str()), folderNames[i], MidasResource::FOLDER, 0);
                currentResources.push_back(res);
                database.push_back(res);
            }
            for (size_t i = 0; i<itemNames.size(); ++i)
            {
                MidasResource res(atoi(itemIds[i].c_str()), itemNames[i], MidasResource::ITEM,(int)itemBytes[i] );
                currentResources.push_back(res);
                database.push_back(res);
            }
            if (folderNames.empty() && itemNames.empty())
            {
                MidasResource res(-1, "Folder empty", MidasResource::NOTSET, (int)0 );
                currentResources.push_back(res);
            }
            LOGI("ressource ok");
            break;
            }
        case MidasResource::ITEM:
            {
            MidasResource res(mId, mName, MidasResource::ITEM, mSize);
            currentResources.push_back(res);
            LOGI("ressource ok");
            break;
            }
        default :
            LOGI("failed to find folder: %s", mName.c_str());
            LOGI("type = 0 --> finalList null");
            break;
    }
    return currentResources;
}
//----------------------------------------------------------------------------
/*std::string midasFilesTools::ToString(const size_t& sz)
{

 std::stringstream ss;

  ss << sz;

  return ss.str();
}*/
//----------------------------------------------------------------------------
std::string midasFilesTools::downloadItem(const std::string& itemName,const std::string& itemPath, JNIEnv* env, jobject loader)
{
    std::string mItemPath = itemPath;

    myName = itemName;

    for (size_t i = 0; i < database.size(); ++i)
    {
        if (database[i].getName() == itemName && database[i].getType() == MidasResource::ITEM) {
          myId = database[i].getId();
          myType = database[i].getType();
          mySize = database[i].getSize();
      }
    }
    LOGI("itemId = %d",myId);

    std::stringstream strMyId;
    strMyId<<myId;
    std::string downloadUrl = this->midas->itemDownloadUrl(strMyId.str());
    LOGI("downloadUrl = %s",downloadUrl.c_str());



    vesSharedPtr<MyProgressDelegate> PtprogressDelegate = vesSharedPtr<MyProgressDelegate>(new MyProgressDelegate);
    LOGI("MyProgressDelegate ");
    PtprogressDelegate->setFilesTool(this);
    LOGI("MyProgressDelegate setFilesTool ok");

    jclass cls = env->GetObjectClass( loader);
    if(cls)
      LOGI("JNICALL cls ok");
    jfieldID fid = env->GetFieldID(cls, "mProgressDialog", "Landroid/app/ProgressDialog;");
    if(fid)
     LOGI("JNICALL fid ok");
    jobject jProgressDialog = env->GetObjectField(loader, fid);
   if(jProgressDialog)
   {
     LOGI("JNICALL jProgressDialog ok");
     PtprogressDelegate->setProgressDialog(&jProgressDialog);
     LOGI("MyProgressDelegate setProgressDialog ok");
   }
    else
    {
        LOGI("MyProgressDelegate setProgressDialog nok");
        return NULL;
    }

    setJNIEnv(env);
     LOGI("MyProgressDelegate setJNIEnv ok");
    PtprogressDelegate->setTotalBytes(mySize);

    LOGI("setFilesTool ");

    //vesKiwiCurlDownloader downloader;
    vesSharedPtr<vesKiwiCurlDownloader> downloader = vesSharedPtr<vesKiwiCurlDownloader>(new vesKiwiCurlDownloader);

    LOGI("downloader");
    downloader->setProgressDelegate(PtprogressDelegate);
    LOGI("setProgressDelegate");

    if(!mItemPath.size())
    {
        mItemPath = "/tmp";
    }
    std::string downloadedFile = downloader->downloadUrlToDirectory(downloadUrl, mItemPath);
    if (!downloadedFile.size()) {
      std::string downloadedError = downloader->errorTitle() + downloader->errorMessage();
      LOGI("downloadedError = %s",downloadedError.c_str());

      return downloadedError;
    }
    LOGI("downloadedFile = ok");

    return downloadedFile;

}
//----------------------------------------------------------------------------
JNIEnv* midasFilesTools::getJNIEnv ()
{
    /*double value = 0;
    //this->mutex->Lock();
    value = this->ProgressDownload;
    //LOGI("getProgressDownload = %d", value);
    //this->mutex->Unlock();*/
    return mEnv;
}
//----------------------------------------------------------------------------
void midasFilesTools::setJNIEnv (JNIEnv* env)
{
    /*double value = 0;
    //this->mutex->Lock();
    value = this->ProgressDownload;
    //LOGI("getProgressDownload = %d", value);
    //this->mutex->Unlock();*/
    this->mEnv = env;
}

//----------------------------------------------------------------------------
/*void midasFilesTools::setProgressDownload(double progress)
{
   // this->mutex->Lock();
    //LOGI("setProgressDownload = %d",progress);
    this->ProgressDownload = progress;
    //this->mutex->Unlock();
}*/
