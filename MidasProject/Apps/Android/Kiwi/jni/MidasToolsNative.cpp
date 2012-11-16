
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
#include <MidasResource.h>



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

JNIEXPORT jint JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_getProgressDownload
(JNIEnv * env, jobject obj);

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
    jsize i;

    /** find the class MidasResource **/
    jclass classe = env->FindClass( "com/kitware/KiwiViewer/MidasResource");
    if (classe == NULL) {
            if (env->ExceptionOccurred()) {
                env->ExceptionDescribe();
            }
            LOGI("Failed to get class\n");
      }

    /** create un empty object linked with the MidasResource class**/
    jobject jObjFirst = env->NewObject(classe, (*env).GetMethodID(classe, "<init>", "()V"));
    jmethodID constructor = env->GetMethodID(classe, "<init>", "(ILjava/lang/String;II)V");
    if (!jObjFirst)
      LOGI("JNICALL jObjFirst nok");


    /** get the array of communities **/
    std::vector<MidasResource> communitiesResource = appTools->findCommunities();
    /*std::vector<std::string> names(communitiesResource.size());

    for (size_t i=0; i<communitiesResource.size(); ++i)
    {
        names[i] = communitiesResource[i].getName().c_str();
    }
    LOGI("JNICALL names");

    LOGI("communitiesResource [1]= %s",communitiesResource[1].getName().c_str());*/

    if (!communitiesResource.size())
      LOGI("JNICALL communitiesResource nok");

    /** create a new object array with a first element jObjFirst empty**/
    objNames= (jobjectArray)env->NewObjectArray(communitiesResource.size(),classe,jObjFirst);
    //objNames= (jobjectArray)env->NewObjectArray(names.size(),env->FindClass("java/lang/String"),env->NewStringUTF(""));
    if (!objNames)
      LOGI("JNICALL objNames nok");

     /** get the length of the object Array **/
     jsize length = env->GetArrayLength(objNames);
     if (!length)
       LOGI("JNICALL length nok");

     env->DeleteLocalRef(jObjFirst);

    for (i = 0; i<length; ++i)
     {
        /** obtain the current object from the object array **/
        //jobject  myObject = (*env).GetObjectArrayElement(communitiesResource, i);
        jobject myObject = env->NewObject(classe, constructor,
                                         (jint)communitiesResource[i].getId(),
                                          env->NewStringUTF(communitiesResource[i].getName().c_str()),
                                          (jint)communitiesResource[i].getType(),
                                          (jint)communitiesResource[i].getSize());
        if (!myObject)
          LOGI("JNICALL myObject nok");



        /** set the current object "myobject" into the object array "objNames" at the index i**/
        env->SetObjectArrayElement(objNames, i, myObject);
        env->DeleteLocalRef(myObject);
        //env->SetObjectArrayElement(objNames,i,env->NewStringUTF(names[i].c_str()));

      }

     /*for(std::vector<MidasResource>::const_iterator iter = communitiesResource.begin(); iter != communitiesResource.end(); ++iter)
     {
        jobject myObject = env->GetObjectArrayElement(env, objNames, *iter);
        env->SetObjectArrayElement(objNames,i,myObject );
      }*/

     env->DeleteLocalRef((jobject)classe);

    if (objNames)
        LOGI("JNICALL objNames ok");

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


        /*vectorOfStrings nameChildren = appTools->findCommunityChildren(nameStr);
        objNames= (jobjectArray)env->NewObjectArray(nameChildren.size(),env->FindClass("java/lang/String"),env->NewStringUTF(""));
        for(i=0;i<nameChildren.size();i++)
        {
           env->SetObjectArrayElement(objNames,i,env->NewStringUTF(nameChildren[i].c_str()));
        }
        return(objNames);*/


        /** find the class MidasResource **/
        jclass classe = env->FindClass( "com/kitware/KiwiViewer/MidasResource");
        if (classe == NULL) {
                if (env->ExceptionOccurred()) {
                    env->ExceptionDescribe();
                }
                LOGI("Failed to get class\n");
          }

        /** create un empty object linked with the MidasResource class**/
        jobject jObjFirst = env->NewObject(classe, (*env).GetMethodID(classe, "<init>", "()V"));
        jmethodID constructor = env->GetMethodID(classe, "<init>", "(ILjava/lang/String;II)V");
        if (!jObjFirst)
          LOGI("JNICALL jObjFirst nok");


        /** get the array of communities **/
        std::vector<MidasResource> childrenResource = appTools->findCommunityChildren(nameStr);

        if (!childrenResource.size())
          LOGI("JNICALL childrenResource nok");

        /** create a new object array with a first element jObjFirst empty**/
        objNames= (jobjectArray)env->NewObjectArray(childrenResource.size(),classe,jObjFirst);

        if (!objNames)
          LOGI("JNICALL objNames nok");

         /** get the length of the object Array **/
         jsize length = env->GetArrayLength(objNames);
         if (!length)
           LOGI("JNICALL length nok");

         env->DeleteLocalRef(jObjFirst);

        for (i = 0; i<length; ++i)
         {
            /** obtain the current object from the object array **/
            jobject myObject = env->NewObject(classe, constructor,
                                             (jint)childrenResource[i].getId(),
                                              env->NewStringUTF(childrenResource[i].getName().c_str()),
                                              (jint)childrenResource[i].getType(),
                                              (jint)childrenResource[i].getSize());
            if (!myObject)
              LOGI("JNICALL myObject nok");



            /** set the current object "myobject" into the object array "objNames" at the index i**/
            env->SetObjectArrayElement(objNames, i, myObject);
            env->DeleteLocalRef(myObject);

          }

         env->DeleteLocalRef((jobject)classe);

        if (objNames)
            LOGI("JNICALL objNames ok");

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

        /** find the class MidasResource **/
        jclass classe = env->FindClass( "com/kitware/KiwiViewer/MidasResource");
        if (classe == NULL) {
                if (env->ExceptionOccurred()) {
                    env->ExceptionDescribe();
                }
                LOGI("Failed to get class\n");
          }

        /** create un empty object linked with the MidasResource class**/
        jobject jObjFirst = env->NewObject(classe, (*env).GetMethodID(classe, "<init>", "()V"));
        jmethodID constructor = env->GetMethodID(classe, "<init>", "(ILjava/lang/String;II)V");
        if (!jObjFirst)
          LOGI("JNICALL jObjFirst nok");


        /** get the array of communities **/
        std::vector<MidasResource> childrenResource = appTools->findFolderChildren(nameStr);

        if (!childrenResource.size())
          LOGI("JNICALL childrenResource nok");

        /** create a new object array with a first element jObjFirst empty**/
        objNames= (jobjectArray)env->NewObjectArray(childrenResource.size(),classe,jObjFirst);

        if (!objNames)
          LOGI("JNICALL objNames nok");

         /** get the length of the object Array **/
         jsize length = env->GetArrayLength(objNames);
         if (!length)
           LOGI("JNICALL length nok");

         env->DeleteLocalRef(jObjFirst);

        for (int i = 0; i<length; ++i)
         {
            /** obtain the current object from the object array **/
            jobject myObject = env->NewObject(classe, constructor,
                                             (jint)childrenResource[i].getId(),
                                              env->NewStringUTF(childrenResource[i].getName().c_str()),
                                              (jint)childrenResource[i].getType(),
                                              (jint)childrenResource[i].getSize());
            if (!myObject)
              LOGI("JNICALL myObject nok");



            /** set the current object "myobject" into the object array "objNames" at the index i**/
            env->SetObjectArrayElement(objNames, i, myObject);
            env->DeleteLocalRef(myObject);

          }

         env->DeleteLocalRef((jobject)classe);

        if (objNames)
            LOGI("JNICALL objNames ok");

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
//-------------------------------------------------------------------------------------------
JNIEXPORT jint JNICALL Java_com_kitware_KiwiViewer_MidasToolsNative_getProgressDownload
(JNIEnv * env, jobject obj)
{
    LOGI("getProgressDownload");
    return appTools->getProgressDownload();
}
