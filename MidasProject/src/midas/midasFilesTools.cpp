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
#include <midasResource.h>



#define  LOG_TAG    "midasFilesTools"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

typedef std::vector<std::string> vectorOfStrings;



namespace{
class MyProgressDelegate : public vesKiwiCurlDownloader::ProgressDelegate
{
  public:

    MyProgressDelegate():itemKilobytes(0)
    {
    }

    virtual int downloadProgress(double totalToDownload, double nowDownloaded)
    {


      int progress = nowDownloaded/itemKilobytes;
      return progress;
    }

    int itemKilobytes;
};
}
//----------------------------------------------------------------------------

midasFilesTools::midasFilesTools()
{
    LOGI("constructor MidasFilesTools");
    this->midas = new vesMidasClient();

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
std::vector<midasResource> midasFilesTools::findCommunities()
{
    LOGI("findCommunities");
    this->midas->listCommunities();

    folderNames = this->midas->folderNames();
    folderIds = this->midas->folderIds();

    LOGI("folderNames FOldersId");
    /*for(vectorOfStrings::const_iterator iterName = folderNames.begin() && vectorOfStrings::const_iterator iterId = folderNames.begin();
        iterName != folderNames.end() && iterId != folderNames.end(); ++iterName && ++iterId)
    {*/
    for (size_t i = 0; i<folderNames.size(); ++i)
    {
        midasResource res(atoi(folderIds[i].c_str()), folderNames[i], midasResource::COMMUNITY);
        //LOGI(" res %d",res.getId());
        resources.push_back(res);
        //LOGI(" resources %d",resources[i].getId());
        //LOGI("push res into ressource");
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
std::vector<std::string> midasFilesTools::findCommunityChildren(const std::string& communityName)
{
    LOGI("findCommunityChildren");

    std::string communityId;

    for (size_t i = 0; i < folderNames.size(); ++i)
    {
      if (folderNames[i] == communityName) {
        communityId = folderIds[i];
      }
    }
    if (!communityId.size()) {
        LOGI("failed to find community: %s", communityName.c_str());
      return folderNames;
    }

    bool result=this->midas->listCommunityChildren(communityId);

    if (!result)
    {
        LOGI("failed to find children");
        return folderNames;
    }

    folderNames = midas->folderNames();
    folderIds = this->midas->folderIds();
    return folderNames;

}
//----------------------------------------------------------------------------
std::vector<std::string> midasFilesTools::findFolderChildren(const std::string& myName)
{
    LOGI("findFolderChildren");

    std::string myId;
    size_t mySize;
    std::vector<std::string> finalList;

    int type = 0;
    /*
      type = 0 --> Id not found
      type = 1 --> folder Id
      type = 2 --> item id
      */

    for (size_t i = 0; i < folderNames.size(); ++i)
    {
      if (folderNames[i] == myName) {
        myId = folderIds[i];
        type = 1;
        LOGI("type = 1");
      }
    }
    for (size_t i = 0; i < itemNames.size(); ++i)
    {
        if (itemNames[i] == myName) {
          myId = itemIds[i];
          mySize = this->midas->itemBytes()[i];
          type = 2;
          LOGI("type = 1");
        }
    }


    if (type == 0) {
      LOGI("failed to find folder: %s", myName.c_str());
      LOGI("type = 0 --> finalList null");
      return finalList;
    }

    if (type == 1)
    {
        LOGI("type folder");
        this->midas->listFolderChildren(myId);
        LOGI("type = folder --> listFolderChildren");
        folderNames = this->midas->folderNames();
        folderIds = this->midas->folderIds();
        itemNames = this->midas->itemNames();
        itemIds = this->midas->itemIds();


        finalList.reserve(folderNames.size()+itemNames.size()+2);
        LOGI("reserve memory");
        finalList.push_back("-->    Folders    <--");
        finalList.insert(finalList.end(),folderNames.begin(),folderNames.end());
        //finalList.push_back(folderNames);
        finalList.push_back("-->    Items    <--");
        finalList.insert(finalList.end(),itemNames.begin(),itemNames.end());
        //finalList.push_back(itemNames);
        if(finalList.empty())
        {
            LOGI("finalList empty");

        }
        return finalList;

    }
    if (type == 2)
    {
        finalList.reserve(4);
        finalList.push_back("item selected");
        finalList.push_back(myName);
        myItemName = myName;
        finalList.push_back(ToString(mySize));
        myItemSize = mySize;
        finalList.push_back(myId);
        myItemId = myId;

        if(finalList.empty())
        {
            LOGI("finalList empty");

        }
        return finalList;
    }


}
//----------------------------------------------------------------------------
std::string midasFilesTools::ToString(const size_t& sz)
{

 std::stringstream ss;

  ss << sz;

  return ss.str();
}
//----------------------------------------------------------------------------
std::string midasFilesTools::downloadItem(const std::string& itemName,const std::string& itemPath)
{
    std::string itemId;
    std::string mItemPath = itemPath;
    for (size_t i = 0; i < itemNames.size(); ++i)
    {
      if (itemNames[i] == itemName) {
        itemId = itemIds[i];
      }
    }
    LOGI("itemId = %s",itemId.c_str());
    std::string downloadUrl = this->midas->itemDownloadUrl(itemId);

    //vesKiwiCurlDownloader downloader;
    if(!mItemPath.size())
    {
        mItemPath = "/tmp";
    }
    std::string downloadedFile = downloader.downloadUrlToDirectory(downloadUrl, mItemPath);
    if (!downloadedFile.size()) {
        std::string downloadedError = downloader.errorTitle() + downloader.errorMessage();
      return downloadedError;
    }
    return downloadedFile;

}
//----------------------------------------------------------------------------
int midasFilesTools::getProgressDownload ()
{
    int mItemKilobytes = myItemSize/1024.0;
    this->mProgressDelegate->itemKilobytes = mItemKilobytes;
    downloader.setProgressDelegate(this->mProgressDelegate);
    return progress_function;
}
