/// \class midasFilesTools
/// \ingroup
#ifndef __midasFilesTools_h
#define __midasFilesTools_h


// VES includes
#include <vesSharedPtr.h>
#include <vesMidasClient.h>

// C++ includes
#include <string>

//-------------------------------------------------
class midasFilesTools
{

public :

   midasFilesTools();
  ~midasFilesTools();

   int init(std::string url,std::string email,std::string password);
   std::vector<std::string> findCommunities();
   void setHost(std::string url);
   int login(std::string email,std::string password);
   std::vector<std::string> findCommunityChildren(std::string communityName);
   std::vector<std::string> findFolderChildren(std::string myName);
   std::string ToString(size_t sz);
   std::string downloadItem(std::string nameItem,std::string pathItem);

private :

   vesMidasClient* midas;

   std::vector<std::string> folderNames;
   std::vector<std::string> folderIds;
   std::vector<std::string> itemNames;
   std::vector<std::string> itemIds;

};


#endif
