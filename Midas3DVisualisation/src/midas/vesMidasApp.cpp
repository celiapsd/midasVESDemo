#include <jni.h>
#include <sys/types.h>
#include <android/log.h>

#include <vesKiwiViewerApp.h>
#include <vesMidasApp.h>
#include <vesKiwiCameraSpinner.h>

#include <vtksys/SystemTools.hxx>
#include <vtkTimerLog.h>

#include <cassert>
#include <fstream>


#define  LOG_TAG    "vesMidasApp"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)


class vesMidasApp::midasInternal
{
public:

    midasInternal()
    {
        this->builtinDatasetIndex = -1;//different
        this->currentDataset = "";
        this->storageDir = "";
    }
    //----------------------------------------------------------------------------
    /*~midasInternal()
    {

    }*/
    //----------------------------------------------------------------------------
    int builtinDatasetIndex;
    std::string currentDataset;
    std::string storageDir;

    vesVector3f cameraPosition;
    vesVector3f cameraFocalPoint;
    vesVector3f cameraViewUp;

    int lastFps;
    int fpsFrames;
    double fpsT0;
  };

vesMidasApp::vesMidasApp()
{

    this->Internal = new midasInternal();
    LOGI("vesMidasApp ");
    if (this->Internal)
        LOGI("midas internal initialized");

    assert (this->builtinDatasetName(0));
    LOGI("dataset name not null");

    this->Internal->fpsFrames = 0;
    this->Internal->fpsT0 = vtkTimerLog::GetUniversalTime();
    LOGI("fpsT0 not  null %d",this->Internal->fpsT0);


    storeCameraState();
    LOGI("storeCameraState()ok");
}
vesMidasApp::~vesMidasApp()
{
    this->removeAllDataRepresentations();
    delete this->Internal;
    LOGI("destructor ok");
}
//----------------------------------------------------------------------------
void vesMidasApp::storeCameraState()
{
    LOGI("storeCameraState()");

  this->Internal->cameraPosition = this->cameraPosition();
  this->Internal->cameraFocalPoint = this->cameraFocalPoint();
    this->Internal->cameraViewUp = this->cameraViewUp();
}
void vesMidasApp::addBuiltinDataset(std::string filename, std::string path){
        this->addBuiltinDataset(filename,path);
}

