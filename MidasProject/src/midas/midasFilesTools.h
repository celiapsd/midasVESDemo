/// \class midasFilesTools
/// \ingroup
#ifndef __midasFilesTools_h
#define __midasFilesTools_h


// VES includes
#include <vesSharedPtr.h>
#include <vesMidasClient.h>
#include <MidasResource.h>

#include <curl/curl.h>

// C++ includes
#include <string>

//-------------------------------------------------
class midasFilesTools
{

public :

   midasFilesTools();
   virtual ~midasFilesTools();

   int init(const std::string& url,const std::string& email,const std::string& password);
   std::vector<MidasResource> findCommunities();
   void setHost(const std::string& url);
   int login(const std::string& email,const std::string& password);
   std::vector<std::string> findCommunityChildren(const std::string& communityName);
   std::vector<std::string> findFolderChildren(const std::string& myName);
   static std::string ToString(const size_t& sz);
   std::string downloadItem(const std::string& nameItem,const std::string& pathItem);
   int getProgressDownload();


private :

   vesMidasClient* midas;

   std::vector<std::string> folderNames;
   std::vector<std::string> folderIds;
   std::vector<std::string> itemNames;
   std::vector<std::string> itemIds;

   std::vector<MidasResource> resources;

   std::string myItemName;
   std::string myItemId;
   size_t myItemSize;

   vesKiwiCurlDownloader downloader;

   vesSharedPtr<MyProgressDelegate> mProgressDelegate;

};


#endif
