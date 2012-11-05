
#include <jni.h>
#include <sys/types.h>
#include <android/log.h>
#include <cassert>
#include <fstream>
#include <midasFilesTools.h>
#include <string.h>
//#include <vector.h>
//#include <iterator.h>


/*#include <vesKiwiViewerApp.h>
#include <vesKiwiBaselineImageTester.h>
#include <vesBuiltinShaders.h>
#include <vesKiwiTestHelper.h>*/

#include <vesMidasClient.h>
#include <vesKiwiCurlDownloader.h>



#define  LOG_TAG    "MidasToolsNative"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN, LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,__VA_ARGS__)


namespace {

midasFilesTools* appTools;

int initAppTools(std::string url,std::string email,std::string password)
{
    LOGI("initAppTools");
    appTools = new midasFilesTools();
    return appTools->init(url,email,password);
}


};// end namespace

//-------------------------------------------------------------------------------------------

extern "C" {
JNIEXPORT jint JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_init
(JNIEnv * env, jobject obj, jstring url,jstring email,jstring password);

JNIEXPORT jobjectArray JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_findCommunities
(JNIEnv * env, jobject obj);

JNIEXPORT jobjectArray JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_findCommunityChildren
(JNIEnv * env, jobject obj, jstring nameCommunity);

JNIEXPORT jobjectArray JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_findFolderChildren
(JNIEnv * env, jobject obj, jstring nameChildren);

JNIEXPORT jstring JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_downloadItem
(JNIEnv * env, jobject obj, jstring nameItem, jstring pathItem);

};
//-------------------------------------------------------------------------------------------
JNIEXPORT jint JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_init
(JNIEnv * env, jobject obj, jstring url, jstring email, jstring password)
{

    const char *javaStrUrl = env->GetStringUTFChars(url, NULL);
    const char *javaStrEmail = env->GetStringUTFChars(email, NULL);
    const char *javaStrPassword = env->GetStringUTFChars(password, NULL);

    if (javaStrUrl && javaStrEmail && javaStrPassword) {

      std::string urlStr = javaStrUrl;
      std::string emailStr = javaStrEmail;
      std::string passwordStr = javaStrPassword;

      env->ReleaseStringUTFChars(url, javaStrUrl);
      env->ReleaseStringUTFChars(email, javaStrEmail);
      env->ReleaseStringUTFChars(password, javaStrPassword);

      int result = initAppTools(urlStr, emailStr, passwordStr);
      return result;
    }
}

//-------------------------------------------------------------------------------------------
JNIEXPORT jobjectArray JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_findCommunities
(JNIEnv * env, jobject obj)
{
    LOGI("JNICALL findCommunities");
    jobjectArray objNames;

    int i;

    std::vector<std::string> names = appTools->findCommunities();
    objNames= (jobjectArray)env->NewObjectArray(names.size(),env->FindClass("java/lang/String"),env->NewStringUTF(""));

    for(i=0;i<names.size();i++)
      {
        env->SetObjectArrayElement(objNames,i,env->NewStringUTF(names[i].c_str()));
      }
    /*for(std::vector<std::string>::const_iterator iter = names.begin(); iter != names.end(); ++iter)
          {
            env->SetObjectArrayElement(objNames,i,env->NewStringUTF(*iter.c_str()));
          }*/

    /*
  for (RequestArgs::const_iterator iter = args.begin(); iter != args.end(); ++iter) {
    argString << "&" << iter->first << "=" << iter->second;
  }
      */
    return(objNames);
}
//-------------------------------------------------------------------------------------------
JNIEXPORT jobjectArray JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_findCommunityChildren
(JNIEnv * env, jobject obj, jstring nameCommunity)
{
    LOGI("JNICALL findCommunityChildren");
    const char *javaStrName = env->GetStringUTFChars(nameCommunity, NULL);
    if (javaStrName)
    {
        std::string nameStr = javaStrName;
        env->ReleaseStringUTFChars(nameCommunity, javaStrName);


        jobjectArray objNames;
        int i;
        std::vector<std::string> nameChildren = appTools->findCommunityChildren(nameStr);
        objNames= (jobjectArray)env->NewObjectArray(nameChildren.size(),env->FindClass("java/lang/String"),env->NewStringUTF(""));
        for(i=0;i<nameChildren.size();i++)
        {
           env->SetObjectArrayElement(objNames,i,env->NewStringUTF(nameChildren[i].c_str()));
        }
        return(objNames);
    }
}
//-------------------------------------------------------------------------------------------
JNIEXPORT jobjectArray JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_findFolderChildren
(JNIEnv * env, jobject obj, jstring nameChildren)
{
    LOGI("JNICALL findCommunityChildren");
    const char *javaStrName = env->GetStringUTFChars(nameChildren, NULL);
    if (javaStrName)
    {
        std::string nameStr = javaStrName;
        env->ReleaseStringUTFChars(nameChildren, javaStrName);


        jobjectArray objNames;
        int i;
        std::vector<std::string> namesChildren = appTools->findFolderChildren(nameStr);
        LOGI("find folder children finished");
        if (namesChildren.empty())
        {
            LOGI("namesChildren empty");
        }

        objNames= (jobjectArray)env->NewObjectArray(namesChildren.size(),env->FindClass("java/lang/String"),env->NewStringUTF(""));
        for(i=0;i<namesChildren.size();i++)
        {
           env->SetObjectArrayElement(objNames,i,env->NewStringUTF(namesChildren[i].c_str()));
        }
        if (objNames == NULL)
        {
            LOGI("objNames empty");
        }
        LOGI("objNames not empty");
        return(objNames);
    }
}
//-------------------------------------------------------------------------------------------
JNIEXPORT jstring JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_downloadItem
(JNIEnv * env, jobject obj, jstring nameItem, jstring pathItem)
{
    const char *javaStrName = env->GetStringUTFChars(nameItem, NULL);
    const char *javaStrPath = env->GetStringUTFChars(pathItem, NULL);

    if (javaStrName && javaStrPath) {

      std::string nameStr = javaStrName;
      std::string pathStr = javaStrPath;


      env->ReleaseStringUTFChars(nameItem, javaStrName);
      env->ReleaseStringUTFChars(pathItem, javaStrPath);

      std::string result = appTools->downloadItem(nameStr,pathStr);
      return(env->NewStringUTF(result.c_str()));
    }
}
