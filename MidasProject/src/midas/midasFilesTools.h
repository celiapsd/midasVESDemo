/// \class midasFilesTools
/// \ingroup
#ifndef __midasFilesTools_h
#define __midasFilesTools_h


// VES includes
#include <vesSharedPtr.h>
#include <vesMidasClient.h>
#include <MidasResource.h>
#include <vesKiwiCurlDownloader.h>


// C++ includes
#include <string>

//-------------------------------------------------
class midasFilesTools
{

public :

   typedef midasFilesTools Self;
   midasFilesTools();
   virtual ~midasFilesTools();

   int init(const std::string& url,const std::string& email,const std::string& password);
   void setHost(const std::string& url);
   int login(const std::string& email,const std::string& password);

   std::vector<MidasResource> findCommunities();
   std::vector<MidasResource> findCommunityChildren(const std::string& communityName);
   std::vector<MidasResource> findFolderChildren(const std::string& mName);
   std::string downloadItem(const std::string& nameItem,const std::string& pathItem);

   //static std::string ToString(const size_t& sz);
   double getProgressDownload();
   void setProgressDownload(double progress);


private :

   vesMidasClient* midas;

   double ProgressDownload;

   /*std::vector<std::string> folderNames;
   std::vector<std::string> folderIds;
   std::vector<std::string> itemNames;
   std::vector<std::string> itemIds;
*/
   std::vector<MidasResource> resources;

   std::string myName;
   int myId;
   int myType;

};


#endif
