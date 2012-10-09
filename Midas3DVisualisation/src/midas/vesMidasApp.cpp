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

//----------------------------------------------------------------------------
vesMidasApp::vesMidasApp()
{

    this->Internal = new midasInternal();
    LOGI("vesMidasApp ");
    if (this->Internal)
        LOGI("midas internal initialized");

    assert (this->builtinDatasetName(0));
    LOGI("dataset name not null");

    /*storeCameraState();
    LOGI("storeCameraState()ok");*/
}
//----------------------------------------------------------------------------
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
//----------------------------------------------------------------------------
void vesMidasApp::restoreCameraState()
{
    LOGI("restoreCameraState()");

  this->setCameraPosition(this->Internal->cameraPosition);
  this->setCameraFocalPoint(this->Internal->cameraFocalPoint);
  this->setCameraViewUp(this->Internal->cameraViewUp);
}

//----------------------------------------------------------------------------
void vesMidasApp::initCamera(int w,int h)
{
    LOGI("initCamera()");
    this->initGL();
    this->resizeView(w, h);
}
//----------------------------------------------------------------------------
void vesMidasApp::addBuiltinDataset(std::string filename, std::string path)
{

    LOGI("addBuiltinDataset(filename,path)");

    this->Internal->currentDataset = filename;
    this->Internal->storageDir = path;
    this->vesKiwiViewerApp::addBuiltinDataset(filename,path);
}
//----------------------------------------------------------------------------
void vesMidasApp::setParametersDataset(std::string filename, int builtinDatasetIndex)
{

    LOGI("setParametersDataset(filename,index)");

    this->Internal->currentDataset = filename;
    this->Internal->builtinDatasetIndex = builtinDatasetIndex;
}
//----------------------------------------------------------------------------
void vesMidasApp::initTime()
{
    LOGI("initTime()");
    this->Internal->fpsFrames = 0;
    this->Internal->fpsT0 = vtkTimerLog::GetUniversalTime();
    LOGI("fpsT0 not  null = %d",this->Internal->fpsT0);

}

int vesMidasApp::getBuiltinDatasetIndex()
{
     LOGI("getBuiltinDatasetIndex");
     LOGI("bidi =%d ",this->Internal->builtinDatasetIndex);
    if(this->Internal->builtinDatasetIndex)
    {
        LOGI("getBuiltinDatasetIndex not null = %d",this->Internal->builtinDatasetIndex);
        return this->Internal->builtinDatasetIndex;
    }
    if(this->Internal->builtinDatasetIndex == -1)
    {
        LOGI("getBuiltinDatasetIndex == -1 ");
        return -1;
    }
    else
    {

        LOGI("getBuiltinDatasetIndex == 0 ");
        return 0;
    }
    //return this->Internal->builtinDatasetIndex;
}

//----------------------------------------------------------------------------
int vesMidasApp::defaultBuiltinDatasetIndex() const
{
  return this->numberOfBuiltinDatasets();
}
//----------------------------------------------------------------------------
void vesMidasApp::clearExistingDataset()
{
    LOGI("clearExistingDataset()");

    this->Internal->currentDataset = "";
    LOGI(" this->Internal->currentDataset");
  this->Internal->storageDir = "";
    LOGI(" this->Internal->storageDir");
  this->Internal->builtinDatasetIndex = -1;
    LOGI(" this->Internal->builtinDatasetIndex");
}
//----------------------------------------------------------------------------

bool vesMidasApp::render()
{
    double currentTime = vtkTimerLog::GetUniversalTime();
    double dt =  currentTime -  this->Internal->fpsT0;

    if (dt > 1.0) {
       this->Internal->lastFps = static_cast<int>( this->Internal->fpsFrames/dt);
       this->Internal->fpsFrames = 0;
       this->Internal->fpsT0 = currentTime;
      //LOGI("fps: %d", lastFps);
    }

    //LOGI("render");
    this->vesKiwiViewerApp::render();

     this->Internal->fpsFrames++;

    return this->vesKiwiViewerApp::cameraSpinner()->isEnabled() || this->vesKiwiViewerApp::isAnimating();

}
