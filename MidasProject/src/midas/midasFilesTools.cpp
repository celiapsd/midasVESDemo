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
    this->ProgressDownload = -1;
    this->mutex = new vtkSimpleMutexLock() ;

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
    this->midas->listCommunities();

    vectorOfStrings folderNames = this->midas->folderNames();
    vectorOfStrings folderIds = this->midas->folderIds();

    LOGI("folderNames FOldersId");

    for (size_t i = 0; i<folderNames.size(); ++i)
    {
        MidasResource res(atoi(folderIds[i].c_str()), folderNames[i], MidasResource::COMMUNITY, 0);
        resources.push_back(res);
    }
    LOGI("ressource ok");

    return resources;
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
    std::vector<MidasResource> fail;

    int communityId;

    for (size_t i = 0; i < resources.size(); ++i)
    {
        if (resources[i].getName() == communityName) {
          communityId = resources[i].getId();
          LOGI("communityId(resources) %d",communityId);
      }
    }

    if (!communityId) {
        LOGI("failed to find community: %s", communityName.c_str());
        return fail;
    }
    std::stringstream strCommunityId;
    strCommunityId<<communityId;

    bool result=this->midas->listCommunityChildren(strCommunityId.str());

    if (!result)
    {
        LOGI("failed to find children");
        return fail;
    }

    vectorOfStrings folderNames = midas->folderNames();
    vectorOfStrings folderIds = this->midas->folderIds();

    resources.clear();

    for (size_t i = 0; i<folderNames.size(); ++i)
    {
        MidasResource res(atoi(folderIds[i].c_str()), folderNames[i], MidasResource::FOLDER, 0);
        resources.push_back(res);
    }
    LOGI("ressource ok");

    return resources;
}
//----------------------------------------------------------------------------
std::vector<MidasResource> midasFilesTools::findFolderChildren(const std::string& mName)
{
    LOGI("findFolderChildren");

    int mId;
    int mType = 0;
    int mSize;

    for (size_t i = 0; i < resources.size(); ++i)
    {
        if (resources[i].getName() == mName) {
          mId = resources[i].getId();
          LOGI("myId(resources) %d",mId);
          mType = resources[i].getType();
          mSize = resources[i].getSize();
      }
    }
    resources.clear();

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
                resources.push_back(res);
            }
            for (size_t i = 0; i<itemNames.size(); ++i)
            {
                MidasResource res(atoi(itemIds[i].c_str()), itemNames[i], MidasResource::ITEM,(int)itemBytes[i] );
                resources.push_back(res);
            }
            if (folderNames.empty() && itemNames.empty())
            {
                MidasResource res(-1, "Folder empty", MidasResource::NOTSET, (int)0 );
                resources.push_back(res);
            }
            LOGI("ressource ok");
            break;
            }
        case MidasResource::ITEM:
            {
            MidasResource res(mId, mName, MidasResource::ITEM, mSize);
            resources.push_back(res);

            LOGI("ressource ok");
            break;
            }
        default :
            LOGI("failed to find folder: %s", mName.c_str());
            LOGI("type = 0 --> finalList null");
            break;
    }
    return resources;
}
//----------------------------------------------------------------------------
/*std::string midasFilesTools::ToString(const size_t& sz)
{

 std::stringstream ss;

  ss << sz;

  return ss.str();
}*/
//----------------------------------------------------------------------------
std::string midasFilesTools::downloadItem(const std::string& itemName,const std::string& itemPath)
{
    std::string mItemPath = itemPath;

    myName = itemName;

    for (size_t i = 0; i < resources.size(); ++i)
    {
        if (resources[i].getName() == itemName && resources[i].getType() == MidasResource::ITEM) {
          myId = resources[i].getId();
          myType = resources[i].getType();
          mySize = resources[i].getSize();
      }
    }
    LOGI("itemId = %d",myId);

    std::stringstream strMyId;
    strMyId<<myId;
    std::string downloadUrl = this->midas->itemDownloadUrl(strMyId.str());
    LOGI("downloadUrl = %s",downloadUrl);

    vesSharedPtr<MyProgressDelegate> PtprogressDelegate = vesSharedPtr<MyProgressDelegate>(new MyProgressDelegate);
    LOGI("MyProgressDelegate ");
    PtprogressDelegate->setFilesTool(this);
    PtprogressDelegate->setTotalBytes(mySize);
    LOGI("setFilesTool ");

    vesKiwiCurlDownloader downloader;
    LOGI("downloader");
    downloader.setProgressDelegate(PtprogressDelegate);
    LOGI("setProgressDelegate");

    if(!mItemPath.size())
    {
        mItemPath = "/tmp";
    }
    std::string downloadedFile = downloader.downloadUrlToDirectory(downloadUrl, mItemPath);
    if (!downloadedFile.size()) {
        std::string downloadedError = downloader.errorTitle() + downloader.errorMessage();
           LOGI("downloadedError = %s",downloadedError);
      return downloadedError;
    }
    LOGI("downloadedFile = ok");

    return downloadedFile;

}
//----------------------------------------------------------------------------
double midasFilesTools::getProgressDownload ()
{
    double value = 0;
    this->mutex->Lock();
    value = this->ProgressDownload;
    LOGI("getProgressDownload = %d", value);
    this->mutex->Unlock();
    return value;
}

//----------------------------------------------------------------------------
void midasFilesTools::setProgressDownload(double progress)
{
    this->mutex->Lock();
    LOGI("setProgressDownload = %d",progress);
    this->ProgressDownload = progress;
    this->mutex->Unlock();
}
