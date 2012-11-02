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
#include <sstream>

#include <vesMidasClient.h>
#include <vesKiwiCurlDownloader.h>

#include <midasFilesTools.h>



#define  LOG_TAG    "midasFilesTools"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

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
int midasFilesTools::init(std::string url,std::string email,std::string password)
{
    LOGI("init");

    this->setHost(url);
    int result = this->login(email,password);
    LOGI("login %d",result);
    return result;
    /*return 0;*/
}

//----------------------------------------------------------------------------
std::vector<std::string> midasFilesTools::findCommunities()
{
    LOGI("findCommunities");
    this->midas->listCommunities();
    folderNames = this->midas->folderNames();
    folderIds = this->midas->folderIds();
    return folderNames;

}
//----------------------------------------------------------------------------
void midasFilesTools::setHost(std::string url)
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
int midasFilesTools::login (std::string email,std::string password)
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
std::vector<std::string> midasFilesTools::findCommunityChildren(std::string communityName)
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
std::vector<std::string> midasFilesTools::findFolderChildren(std::string myName)
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
        finalList.push_back(ToString(mySize));
        finalList.push_back(myId);
        if(finalList.empty())
        {
            LOGI("finalList empty");

        }
        return finalList;
    }


}
//----------------------------------------------------------------------------
 std::string midasFilesTools::ToString(size_t sz)
{

 std::stringstream ss;

  ss << sz;

  return ss.str();
}
//----------------------------------------------------------------------------
std::string midasFilesTools::downloadItem(std::string itemName,std::string itemPath)
{
    std::string itemId;
    for (size_t i = 0; i < itemNames.size(); ++i)
    {
      if (itemNames[i] == itemName) {
        itemId = itemIds[i];
      }
    }
    LOGI("itemId = %s",itemId.c_str());
    std::string downloadUrl = this->midas->itemDownloadUrl(itemId);

    vesKiwiCurlDownloader downloader;
    if(!itemPath.size())
    {
        itemPath = "/tmp";
    }
    std::string downloadedFile = downloader.downloadUrlToDirectory(downloadUrl, itemPath);
    if (!downloadedFile.size()) {
        std::string downloadedError = downloader.errorTitle() + downloader.errorMessage();
      return downloadedError;
    }
    return downloadedFile;

}