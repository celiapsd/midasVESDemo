
#include <jni.h>
#include <sys/types.h>
#include <android/log.h>
#include <cassert>
#include <fstream>
#include <midasFilesTools.h>
#include <string.h>
#include <vector>
#include <iterator>


/*#include <vesKiwiViewerApp.h>
#include <vesKiwiBaselineImageTester.h>
#include <vesBuiltinShaders.h>
#include <vesKiwiTestHelper.h>*/

#include <vesMidasClient.h>
#include <vesKiwiCurlDownloader.h>
#include <midasResource.h>



#define  LOG_TAG    "MidasToolsNative"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN, LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,__VA_ARGS__)


typedef std::vector<std::string> vectorOfStrings;

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
    jobject myObject;
    jsize i;

    /** find the class midasResource **/
    jclass classe = env->FindClass( "midasResource");

    /** create un empty object linked with the midasResource class**/
    jobject jObjFirst = env->NewObject(classe, (*env).GetMethodID(classe, "<init>", "()V"));

    /** get the array of communities **/
    std::vector<midasResource> communitiesResource = appTools->findCommunities();

    /** create a new object array with a first element jObjFirst empty**/
    objNames= (jobjectArray)env->NewObjectArray(communitiesResource.size(),classe,jObjFirst);

     /** get the length of the object Array **/
     jsize length = env->GetArrayLength(objNames);


    for (i = 0; i<length; i++)
      {
        /** obtain the current object from the object array **/
        myObject = (*env).GetObjectArrayElement(objNames, i);

        /** set the current object "myobject" into the object array "objNames" at the index i**/
        env->SetObjectArrayElement(objNames, i, myObject );
      }

     /*for(std::vector<midasResource>::const_iterator iter = communitiesResource.begin(); iter != communitiesResource.end(); ++iter)
     {
        jobject myObject = env->GetObjectArrayElement(env, objNames, *iter);
        env->SetObjectArrayElement(objNames,i,myObject );
      }*/

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
        vectorOfStrings nameChildren = appTools->findCommunityChildren(nameStr);
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
        vectorOfStrings namesChildren = appTools->findFolderChildren(nameStr);
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
