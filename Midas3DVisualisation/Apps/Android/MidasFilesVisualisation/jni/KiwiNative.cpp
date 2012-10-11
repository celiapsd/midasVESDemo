///*========================================================================
//  VES --- VTK OpenGL ES Rendering Toolkit

//      http://www.kitware.com/ves

//  Copyright 2011 Kitware, Inc.

//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at

//      http://www.apache.org/licenses/LICENSE-2.0

//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
// ========================================================================*/
///*
//  Comments Celia  :

//I added all the LOGI to see what happened at the execution,
//added the function onBackPressed
//modify checkForAdditionalDatasets, loadDataset

//*/
//#include <jni.h>
//#include <sys/types.h>
//#include <android/log.h>

//#include <vesKiwiViewerApp.h>
//#include <vesKiwiCameraSpinner.h>

//#include <vtksys/SystemTools.hxx>
//#include <vtkTimerLog.h>

//#include <cassert>
//#include <fstream>


//#define  LOG_TAG    "KiwiNative"
//#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
//#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
//#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//// This include uses the LOGI, LOGW, LOGE macro definitions
//#include "vtkAndroidOutputWindow.h"

//namespace {

//class AndroidAppState {
//public:

//  AndroidAppState() : builtinDatasetIndex(-1) { }

//  int builtinDatasetIndex;
//  std::string currentDataset;
//  vesVector3f cameraPosition;
//  vesVector3f cameraFocalPoint;
//  vesVector3f cameraViewUp;
//};

////----------------------------------------------------------------------------
//vesKiwiViewerApp* app;
//AndroidAppState appState;

//int lastFps;
//int fpsFrames;
//double fpsT0;

////----------------------------------------------------------------------------
//void resetView()
//{
//  if(appState.builtinDatasetIndex!=0) {
//    LOGI("RESETVIEW buildinDatasetIndex = %d",appState.builtinDatasetIndex);
//  }
//  else{
//      LOGI("RESETVIEW buildinDatasetIndex = null");
//  }

//  if (appState.builtinDatasetIndex >= 0) {
//      LOGI("applyBuiltinDatasetCameraParameters");
//    app->applyBuiltinDatasetCameraParameters(appState.builtinDatasetIndex);
//  }
//  else {
//        LOGI("resetView");
//    app->resetView();
//  }
//}

////----------------------------------------------------------------------------
//bool loadDataset(const std::string& filename, int builtinDatasetIndex)
//{
//    if( filename.c_str()) {
//     LOGI("loadDataset(%s),builtinDatasetIndex = %d", filename.c_str(),builtinDatasetIndex);
//    }

//    else{
//        LOGI("loadDataset(filename == null)" );
//    }


//  appState.currentDataset = filename;
//  appState.builtinDatasetIndex = builtinDatasetIndex;

//  bool result = app->loadDataset(filename);
//  if (result) {
//    resetView();
//  }
//  return result;
//}

////----------------------------------------------------------------------------
//void clearExistingDataset()
//{
//    LOGI("clearExistingDataset()");

//  appState.currentDataset = std::string();
//  appState.builtinDatasetIndex = -1;
//}

////----------------------------------------------------------------------------
//void storeCameraState()
//{
//    LOGI("storeCameraState()");

//  appState.cameraPosition = app->cameraPosition();
//  appState.cameraFocalPoint = app->cameraFocalPoint();
//  appState.cameraViewUp = app->cameraViewUp();
//}

////----------------------------------------------------------------------------
//void restoreCameraState()
//{
//    LOGI("restoreCameraState()");

//  app->setCameraPosition(appState.cameraPosition);
//  app->setCameraFocalPoint(appState.cameraFocalPoint);
//  app->setCameraViewUp(appState.cameraViewUp);
//}

////----------------------------------------------------------------------------
//bool setupGraphics(int w, int h)
//{
//    LOGI("setupGraphics(w=%d,h=%d)",w,h);

//  // The app may lose its GL context and so it
//  // calls setupGraphics when the app resumes.  We don't have an
//  // easy way to re-initialize just the GL resources, so the strategy
//  // for now is to delete the whole app instance and then restore
//  // the current dataset and camera state.
//  bool isResume = app != NULL;

//  if( isResume ) {
//      LOGI("isResume = not null");
//  }
//  else{
//      LOGI("isResume = null");
//  }
//  if (isResume) {
//    storeCameraState();
//    delete app; app = 0;

//    if( isResume) {
//        LOGI("isResume = %s",isResume);
//    }
//    else{
//        LOGI("isResume = null");
//    }
//    isResume = true;
//  }

//  // Pipe VTK messages into the android log
//  vtkAndroidOutputWindow::Install();

//  app = new vesKiwiViewerApp();
//  LOGI("app initialized");
//  app->initGL();

//  app->resizeView(w, h);

//  if( isResume && appState.currentDataset.empty()) {
//      LOGI("isResume == %s && appState.currentDataset.empty() ==%s",isResume,appState.currentDataset.empty());
//  }
//  else{

//      if(isResume)
//        LOGI("isResume == %s,appState.currentDataset.empty() ==false",isResume);
//      if(appState.currentDataset.empty())
//        LOGI("isResume == false , appState.currentDataset.empty() ==true");
//  }



//  if (isResume && !appState.currentDataset.empty()) {
//    app->loadDataset(appState.currentDataset);
//    restoreCameraState();
//  }

//  fpsFrames = 0;
//  fpsT0 = vtkTimerLog::GetUniversalTime();

//  return true;
//}
//}; // end namespace
////----------------------------------------------------------------------------


////----------------------------------------------------------------------------
//extern "C" {


//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_init(JNIEnv * env, jobject obj,  jint width, jint height);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_reshape(JNIEnv * env, jobject obj,  jint width, jint height);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchPanGesture(JNIEnv * env, jobject obj,  jfloat dx, jfloat dy);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleTwoTouchPanGesture(JNIEnv * env, jobject obj,  jfloat x0, jfloat y0, jfloat x1, jfloat y1);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleTwoTouchPinchGesture(JNIEnv * env, jobject obj,  jfloat scale);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleTwoTouchRotationGesture(JNIEnv * env, jobject obj,  jfloat rotation);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchDown(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchUp(JNIEnv * env, jobject obj);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchTap(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleDoubleTap(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleLongPress(JNIEnv * env, jobject obj,  jfloat x, jfloat y);
//  JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_render(JNIEnv * env, jobject obj);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_resetCamera(JNIEnv * env, jobject obj);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_stopInertialMotion(JNIEnv * env, jobject obj);
//  JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDatasetName(JNIEnv* env, jobject obj, jint offset);
//  JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDatasetFilename(JNIEnv* env, jobject obj, jint offset);
//  JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfBuiltinDatasets(JNIEnv* env, jobject obj);
//  JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDefaultBuiltinDatasetIndex(JNIEnv* env, jobject obj);
//  JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDatasetIsLoaded(JNIEnv* env, jobject obj);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_clearExistingDataset(JNIEnv * env, jobject obj);
//  JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_loadDataset(JNIEnv* env, jobject obj, jstring filename, int builtinDatasetIndex);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_checkForAdditionalDatasets(JNIEnv* env, jobject obj,/*jstring name,*/ jstring storageDir);
//  JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getLoadDatasetErrorTitle(JNIEnv* env, jobject obj);
//  JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getLoadDatasetErrorMessage(JNIEnv* env, jobject obj);

//  JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfTriangles(JNIEnv* env, jobject obj);
//  JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfLines(JNIEnv* env, jobject obj);
//  JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfVertices(JNIEnv* env, jobject obj);
//  JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getFramesPerSecond(JNIEnv* env, jobject obj);
//  JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_onBackPressed(JNIEnv * env, jobject obj);
//};

////-------------------------------------------------------------------------------------------
//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_onBackPressed
//  (JNIEnv *env, jobject obj)
//{
//    LOGI("onBackPressed()");
//    //app->removeAllDataRepresentations();

//    app->haltCameraRotationInertia();
//    clearExistingDataset();
//    app->~vesKiwiViewerApp();
//}
////-----------------------------------------------------------------------------
//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_init(JNIEnv * env, jobject obj,  jint width, jint height)
//{
//  LOGI("setupGraphics(%d, %d)", width, height);
//  setupGraphics(width, height);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_reshape(JNIEnv * env, jobject obj,  jint width, jint height)
//{
//  LOGI("reshape(%d, %d)", width, height);
//  app->resizeView(width, height);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchPanGesture(JNIEnv * env, jobject obj,  jfloat dx, jfloat dy)
//{

//  app->handleSingleTouchPanGesture(dx, dy);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleTwoTouchPanGesture(JNIEnv * env, jobject obj,  jfloat x0, jfloat y0, jfloat x1, jfloat y1)
//{
//  app->handleTwoTouchPanGesture(x0, y0, x1, y1);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleTwoTouchPinchGesture(JNIEnv * env, jobject obj,  jfloat scale)
//{
//  app->handleTwoTouchPinchGesture(scale);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleTwoTouchRotationGesture(JNIEnv * env, jobject obj,  jfloat rotation)
//{
//  app->handleTwoTouchRotationGesture(rotation);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchDown(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
//{
//  app->handleSingleTouchDown(x, y);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchUp(JNIEnv * env, jobject obj)
//{
//  app->handleSingleTouchUp();
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleSingleTouchTap(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
//{
//  app->handleSingleTouchTap(x, y);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleDoubleTap(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
//{
//  app->handleDoubleTap(x, y);
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_handleLongPress(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
//{
//  app->handleLongPress(x, y);
//}

//JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_render(JNIEnv * env, jobject obj)
//{
//    LOGI("render()");

//  double currentTime = vtkTimerLog::GetUniversalTime();
//  double dt = currentTime - fpsT0;
//  if (dt > 1.0) {
//    lastFps = static_cast<int>(fpsFrames/dt);
//    fpsFrames = 0;
//    fpsT0 = currentTime;
//    //LOGI("fps: %d", lastFps);
//  }

//  //LOGI("render");
//  app->render();

//  fpsFrames++;

//  return app->cameraSpinner()->isEnabled() || app->isAnimating();
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_resetCamera(JNIEnv * env, jobject obj)
//{
//    LOGI("resetCamera");

//  resetView();
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_stopInertialMotion(JNIEnv * env, jobject obj)
//{
//  app->haltCameraRotationInertia();
//}

//JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDatasetName(JNIEnv* env, jobject obj, jint offset)
//{
//  std::string name = app->builtinDatasetName(offset);
//  if( offset && name.c_str()) {
//      LOGI("getDatasetName offset = %d,name = %s",offset,name.c_str());
//  }
//  else{
//      LOGI("getDatasetName offset == null  or name ==null");
//  }
//  const char* nameForOutput = name.c_str();
//  return(env->NewStringUTF(name.c_str()));
//}

//JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDatasetFilename(JNIEnv* env, jobject obj, jint offset)
//{

//  std::string name = app->builtinDatasetFilename(offset);
//  if( offset && name.c_str()) {
//      LOGI("getDatasetFilename offset = %d,name = %s",offset,name.c_str());
//  }
//  else{
//      LOGI("getDatasetFilename offset == null  or name ==null");
//  }
//  //LOGI("getDatasetFilename offset = %d,name = %s",offset,name.c_str());

//  const char* nameForOutput = name.c_str();
//  return(env->NewStringUTF(name.c_str()));
//}

//JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfBuiltinDatasets(JNIEnv* env, jobject obj)
//{
//    LOGI("getNumberOfBuiltinDatasets()");

//  return app->numberOfBuiltinDatasets();
//}

//JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDefaultBuiltinDatasetIndex(JNIEnv* env, jobject obj)
//{
//    LOGI("getDefaultBuiltinDatasetIndex()");

//  return app->defaultBuiltinDatasetIndex();
//}

//JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getDatasetIsLoaded(JNIEnv* env, jobject obj)
//{
//   LOGI("getDatasetIsLoaded()");
//   if( appState.currentDataset.empty()) {
//         LOGI("getDatasetIsLoaded(), appState.currentDataset.empty-->empty ");
//    }
//    else{
//         LOGI("getDatasetIsLoaded(), appState.currentDataset.empty-->not empty ");
//    }
//  return !appState.currentDataset.empty();
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_clearExistingDataset(JNIEnv * env, jobject obj)
//{
//    LOGI("clearExistingDataset()");
//  clearExistingDataset();
//}

//JNIEXPORT jboolean JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_loadDataset(JNIEnv* env, jobject obj, jstring filename, jint builtinDatasetIndex)
//{
//    LOGI("loadDataset()");


//  const char *javaStr = env->GetStringUTFChars(filename, NULL);
//  if (javaStr) {
//    std::string filenameStr = javaStr;
//    env->ReleaseStringUTFChars(filename, javaStr);
//    if( javaStr ) {
//        LOGI("loadDataset(), filename = not null,buildindatatsetindex = %d",builtinDatasetIndex);
//}
//    else{
//        LOGI("loadDataset(), filename = null ");
//        }
//    return loadDataset(filenameStr, builtinDatasetIndex);
//  }
//  return false;
//}

//JNIEXPORT void JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_checkForAdditionalDatasets(JNIEnv* env, jobject obj/*,jstring name*/, jstring storageDir)
//{

//    LOGI("checkForAdditionalDatasets ");

//  const char *javaStr = env->GetStringUTFChars(storageDir, NULL);
//  //const char *javaStr1 = env->GetStringUTFChars(name, NULL);
//  if (javaStr /*&& javaStr1*/) {
//    std::string storageDirStr = javaStr;
//    //std::string nameStr = javaStr1;
//    if( javaStr) {
//        LOGI("checkForAdditionalDatasets storageDir =  not null");
//    }
//    else{
//            LOGI("checkForAdditionalDatasets storageDir = null)");
//        }
//    /*if( javaStr1) {
//        LOGI("checkForAdditionalDatasets name =  not null");
//    }
//    else{
//            LOGI("checkForAdditionalDatasets name = null)");
//        }*/

//    env->ReleaseStringUTFChars(storageDir, javaStr);

//    //env->ReleaseStringUTFChars(name, javaStr1);
//    app->checkForAdditionalData(/*nameStr,*/storageDirStr);

//     }
//}

//JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getLoadDatasetErrorTitle(JNIEnv* env, jobject obj)
//{
//  std::string str = app->loadDatasetErrorTitle();
//  return env->NewStringUTF(str.c_str());
//}

//JNIEXPORT jstring JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getLoadDatasetErrorMessage(JNIEnv* env, jobject obj)
//{
//  std::string str = app->loadDatasetErrorMessage();
//  return env->NewStringUTF(str.c_str());
//}

//JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfTriangles(JNIEnv* env, jobject obj)
//{
//  return app->numberOfModelFacets();
//}

//JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfLines(JNIEnv* env, jobject obj)
//{
//  return app->numberOfModelLines();
//}

//JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getNumberOfVertices(JNIEnv* env, jobject obj)
//{
//  return app->numberOfModelVertices();
//}

//JNIEXPORT jint JNICALL Java_com_kitware_midasfilesvisualisation_KiwiNative_getFramesPerSecond(JNIEnv* env, jobject obj)
//{
//  return lastFps;
//}
