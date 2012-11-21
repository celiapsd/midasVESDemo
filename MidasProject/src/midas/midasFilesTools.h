/// \class midasFilesTools
/// \ingroup
#ifndef __midasFilesTools_h
#define __midasFilesTools_h


// VES includes
#include <vesSharedPtr.h>
#include <vesMidasClient.h>
#include <MidasResource.h>
#include <vesKiwiCurlDownloader.h>
#include <vtkMutexLock.h>

#include <jni.h>


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
   std::string downloadItem(const std::string& nameItem,const std::string& pathItem, JNIEnv* env, jobject loader);

   //static std::string ToString(const size_t& sz);
   JNIEnv* getJNIEnv();
   void setJNIEnv (JNIEnv* env);
   void setProgressDownload(double progress);


private :

   vesMidasClient* midas;
   JNIEnv* mEnv;


   vtkSimpleMutexLock* mutex;

   std::vector<MidasResource> database;

   std::string myName;
   int myId;
   int myType;
   int mySize;

};


#endif
