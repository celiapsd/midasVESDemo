//
//  kiwiCloudApp.h
//  CloudAppTab
//
//  Created by Pat Marion on 9/29/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#ifndef __CloudAppTab__kiwiCloudApp__
#define __CloudAppTab__kiwiCloudApp__

#include <iostream>
#include <stdio.h>

#include <vesKiwiViewerApp.h>
#include <vesRenderer.h>
#include <vesBackground.h>
#include <vesCamera.h>
#include <vesKiwiImageWidgetRepresentation.h>

#include <vtkImageData.h>
#include <vtkNew.h>
#include <vtkSphereSource.h>
#include <vtkPolyData.h>

#include <iostream>
#include <iomanip>


class kiwiApp : public vesKiwiViewerApp
{
public:

  vesTypeMacro(kiwiApp);

  virtual std::string leftText()
  {
    return std::string();
  }

  virtual std::string rightText()
  {
    return std::string();
  }

  virtual std::vector<std::string> actions()
  {
    return std::vector<std::string>();
  }

  virtual bool onAction(const std::string& action)
  {
    vesNotUsed(action);
    return true;
  }


};


class kiwiCloudApp : public kiwiApp
{
public:

  vesTypeMacro(kiwiCloudApp);

  kiwiCloudApp()
  {
    this->PlaneIndex = 0;
    this->ScrollSliceMode = false;
    this->MaxParallelScale = 0.0;
  }

  void printError()
  {
      printf("Error: %s\n", this->loadDatasetErrorTitle().c_str());
      printf("Message: %s\n", this->loadDatasetErrorMessage().c_str());
  }

  bool is2DImageMode() {
    return (this->imageRep() && this->camera()->parallelProjection());
  }

  bool is2DImageZoomedOut()
  {
    return (this->is2DImageMode() && (this->camera()->parallelScale() >= this->MaxParallelScale));
  }

  void start2DImageView()
  {
    if (this->imageRep()) {
      this->haltCameraRotationInertia();
      this->camera()->setParallelProjection(true);
      this->setCameraRotationInertiaIsEnabled(false);
      this->imageRep()->setInteractionIsEnabled(false);
      this->imageRep()->setOutlineVisible(false);
      this->setBackgroundColor(0,0,0);
    }
  }

  void onCamera3D()
  {
    this->camera()->setParallelProjection(false);
    this->setCameraRotationInertiaIsEnabled(true);
    this->setDefaultBackgroundColor();

    vesKiwiImageWidgetRepresentation* imageRep = this->imageRep();
    if (imageRep) {
      imageRep->setInteractionIsEnabled(true);
      imageRep->setPlaneVisibility(0, true);
      imageRep->setPlaneVisibility(1, true);
      imageRep->setPlaneVisibility(2, true);
      imageRep->setOutlineVisible(true);
      imageRep->refreshTextures();
      //vesVector3f viewDirection(-0.65814518, -0.65814518,  0.36563621);
      //vesVector3f viewUp(-0.25127214, -0.25127214, -0.93473238);
      //this->resetView(viewDirection, viewUp);
      this->resetView();
    }
  }

  void setViewDirectionAndParallelScale(vesVector3f viewDirection, vesVector3f viewUp, double width, double height)
  {
    this->start2DImageView();
    this->resetView(viewDirection, viewUp);
    double aspectRatio = static_cast<double>(this->viewWidth())/this->viewHeight();
    double parallelScale = std::max(width/aspectRatio, height) / 2.0;
    this->camera()->setParallelScale(parallelScale);
    this->MaxParallelScale = parallelScale;
  }
  
  void onCameraX()
  {
    vesKiwiImageWidgetRepresentation* imageRep = this->imageRep();
    imageRep->setPlaneVisibility(0, true);
    imageRep->setPlaneVisibility(1, false);
    imageRep->setPlaneVisibility(2, false);
    imageRep->refreshTextures();
    this->PlaneIndex = 0;
    vesVector3f viewDirection(1,0,0);
    vesVector3f viewUp(0,0,1);
    double* bounds = imageRep->imageData()->GetBounds();
    double width = bounds[3] - bounds[2];
    double height = bounds[5] - bounds[4];
    this->setViewDirectionAndParallelScale(viewDirection, viewUp, width, height);
  }

  void onCameraY()
  {
    vesKiwiImageWidgetRepresentation* imageRep = this->imageRep();
    imageRep->setPlaneVisibility(0, false);
    imageRep->setPlaneVisibility(1, true);
    imageRep->setPlaneVisibility(2, false);
    imageRep->refreshTextures();
    this->PlaneIndex = 1;
    vesVector3f viewDirection(0,1,0);
    vesVector3f viewUp(0,0,1);
    double* bounds = imageRep->imageData()->GetBounds();
    double width = bounds[1] - bounds[0];
    double height = bounds[5] - bounds[4];
    this->setViewDirectionAndParallelScale(viewDirection, viewUp, width, height);
  }

  void onCameraZ()
  {
    vesKiwiImageWidgetRepresentation* imageRep = this->imageRep();
    imageRep->setPlaneVisibility(0, false);
    imageRep->setPlaneVisibility(1, false);
    imageRep->setPlaneVisibility(2, true);
    imageRep->refreshTextures();
    this->PlaneIndex = 2;
    vesVector3f viewDirection(0,0,1);
    vesVector3f viewUp(0,-1,0);
    double* bounds = imageRep->imageData()->GetBounds();
    double width = bounds[1] - bounds[0];
    double height = bounds[3] - bounds[2];
    this->setViewDirectionAndParallelScale(viewDirection, viewUp, width, height);
  }

  std::string leftText()
  {
    if (!this->is2DImageMode()) {
      return std::string();
    }
    
    vesKiwiImageWidgetRepresentation* imageRep = this->imageRep();
    vtkImageData* imageData = imageRep->imageData();

    int planeIndex = this->PlaneIndex;
    int sliceIndex = imageRep->sliceIndex(planeIndex);
    int numberOfSlices = imageRep->numberOfSlices(planeIndex);

    int imageDims[2];
    int dimensions[3];
    imageData->GetDimensions(dimensions);

    if (planeIndex == 0) {
      imageDims[0] = dimensions[1];
      imageDims[1] = dimensions[2];
    }
    else if (planeIndex == 1) {
      imageDims[0] = dimensions[0];
      imageDims[1] = dimensions[2];
    }
    else if (planeIndex == 2) {
      imageDims[0] = dimensions[0];
      imageDims[1] = dimensions[1];
    }

    std::stringstream str;

    str << std::fixed << setprecision(2)
        << "Slice: " << sliceIndex+1 << "/" << numberOfSlices << " (" << imageDims[0] << "x" << imageDims[1] << ")\n"
        << "Window: " << imageRep->window() << "\n"
        << "Level: " << imageRep->level() << "\n";
    return str.str();
  }

  std::string rightText()
  {
    if (!this->is2DImageMode()) {
      return std::string();
    }

    vesKiwiImageWidgetRepresentation* imageRep = this->imageRep();
    vtkImageData* imageData = imageRep->imageData();

    int dimensions[3];
    double scalarRange[2];
    imageData->GetDimensions(dimensions);
    imageData->GetScalarRange(scalarRange);

    std::stringstream str;

    str << std::fixed << setprecision(2)
        << "Scalar Type: " << imageData->GetScalarTypeAsString() << "\n"
        << "Scalar Range: [" << scalarRange[0] << ", " << scalarRange[1] << "]\n"
        << "Dimensions: [" << dimensions[0] << ", " << dimensions[1] << ", " << dimensions[2] << "]\n";
    return str.str();
  }



  virtual std::vector<std::string> actions()
  {
    std::vector<std::string> actionList;

    if (this->imageRep()) {
      actionList.push_back("X");
      actionList.push_back("Y");
      actionList.push_back("Z");
      actionList.push_back("3D");
    }
    else {
      actionList.push_back("Reset Camera");
    }

    return actionList;
  }

  virtual bool onAction(const std::string& action)
  {
    if (action == "X")
      this->onCameraX();
    else if (action == "Y")
      this->onCameraY();
    else if (action == "Z")
      this->onCameraZ();
    else if (action == "3D")
      this->onCamera3D();
    else if (action == "Reset Camera")
      this->resetView();
    return true;
  }


  virtual void handleTwoTouchRotationGesture(double rotation)
  {
    if (this->is2DImageMode()) {
      return;
    }
    
    this->vesKiwiViewerApp::handleTwoTouchRotationGesture(rotation);
  }
  
  virtual void handleSingleTouchDown(int x, int y)
  {
    if (this->is2DImageMode()) {
      if (x > this->viewWidth() - 50) {
        this->ScrollSliceMode = true;
        this->CurrentSliceIndex = this->imageRep()->sliceIndex(this->PlaneIndex);
      }
    }
    
    this->vesKiwiViewerApp::handleSingleTouchDown(x, y);
  }

  virtual void handleSingleTouchUp()
  {
    this->ScrollSliceMode = false;
    this->vesKiwiViewerApp::handleSingleTouchUp();
  }

  virtual void handleTwoTouchPanGesture(double x0, double y0, double x1, double y1)
  {
    if (this->is2DImageMode()) {
      return;
    }
    this->vesKiwiViewerApp::handleTwoTouchPanGesture(x0, y0, x1, y1);
  }

  virtual void handleSingleTouchPanGesture(double deltaX, double deltaY)
  {
    if (this->is2DImageMode()) {

      if (this->ScrollSliceMode) {


        int numberOfSlices = this->imageRep()->numberOfSlices(this->PlaneIndex);
        
        deltaY = -deltaY;
        deltaY = deltaY/this->viewHeight();

        double scrollDistanceInSlices = numberOfSlices*deltaY;

        this->CurrentSliceIndex += scrollDistanceInSlices;
        if (this->CurrentSliceIndex < 0) {
          this->CurrentSliceIndex = 0;
        }
        else if (this->CurrentSliceIndex > numberOfSlices-1) {
          this->CurrentSliceIndex = numberOfSlices-1;
        }

        this->imageRep()->setScrollSlice(this->PlaneIndex);
        this->imageRep()->scheduleSetSliceIndex(this->PlaneIndex, static_cast<int>(this->CurrentSliceIndex));
        return;
      }
      else if (this->is2DImageZoomedOut()) {

        double scalarRange[2];
        this->imageRep()->imageData()->GetScalarRange(scalarRange);

        double range = scalarRange[1] - scalarRange[0];
        deltaY = -deltaY;
        deltaX = deltaX/this->viewWidth();
        deltaY = deltaY/this->viewHeight();

        deltaX = deltaX*(range/1);
        deltaY = deltaY*(range/1);

        this->imageRep()->setWindowLevel(this->imageRep()->window()+deltaX, this->imageRep()->level()+deltaY);
        return;
      }
      else {
        this->vesKiwiViewerApp::handleTwoTouchPanGesture(0,0,deltaX,-deltaY);
        return;
      }
    }
    
    this->vesKiwiViewerApp::handleSingleTouchPanGesture(deltaX, deltaY);
  }

  virtual void handleTwoTouchPinchGesture(double scale)
  {
    this->vesKiwiViewerApp::handleTwoTouchPinchGesture(scale);

    // check if we're in 2d image view mode
    if (this->is2DImageMode()) {

      if (this->is2DImageZoomedOut()) {
        this->camera()->setParallelScale(this->MaxParallelScale);
        if (this->PlaneIndex == 0) {
          this->onCameraX();
        }
        else if (this->PlaneIndex == 1) {
          this->onCameraY();
        }
        else if (this->PlaneIndex == 2) {
          this->onCameraZ();
        }
      }
    }
  
  }

  vesKiwiImageWidgetRepresentation* imageRep() const
  {
    if (!this->dataRepresentations().size()) {
      return 0;
    }
    return dynamic_cast<vesKiwiImageWidgetRepresentation*>(this->dataRepresentations()[0]);
  }

  virtual void setDefaultBackgroundColor()
  {
    vesVector4f topColor(0.2, 0.2, 0.2, 1.0);
    vesVector4f bottomColor(0, 0, 0, 1);
    this->renderer()->background()->setGradientColor(bottomColor, topColor);
  }

protected:



  bool ScrollSliceMode;
  int PlaneIndex;
  float MaxParallelScale;
  double CurrentSliceIndex;

};





#include <vesKiwiStreamingDataRepresentation.h>
#include <vesKiwiText2DRepresentation.h>


class vesKiwiPointCloudApp : public kiwiApp {
public:

  vesTypeMacro(vesKiwiPointCloudApp);
  typedef vesKiwiViewerApp Superclass;

  vesKiwiPointCloudApp()
  {
    mIsConnected = false;
    mText = 0;
    mHost = "";
    mPort = 0;
  }

  ~vesKiwiPointCloudApp()
  {
    this->unloadData();
  }

  void unloadData()
  {
    if (mDataRep) {
      mDataRep->removeSelfFromRenderer(this->renderer());
      mDataRep.reset();
    }
  }

  void willRender()
  {
    this->Superclass::willRender();

    if (mIsConnected) {
      this->mDataRep->willRender(this->renderer());
    }
    else {
      this->connect(mHost, mPort);
    }
  }

  void showText(const std::string& textStr)
  {
    if (!mText) {
      mText = this->addTextRepresentation(textStr);
      mText->setDisplayPosition(vesVector2f(10, 10));
    }
    else {
      mText->setText(textStr);
    }
  }

  void setHost(const std::string& host)
  {
    mHost = host;
  }

  void setPort(int port)
  {
    mPort = port;
  }

  bool connect(const std::string& host, int port)
  {
    //this->unloadData();
    mIsConnected = false;

    std::stringstream hostPort;
    hostPort << host << ":" << port;
    this->showText("Connecting to " + hostPort.str());

    if (!mDataRep) {
      mDataRep = vesKiwiStreamingDataRepresentation::Ptr(new vesKiwiStreamingDataRepresentation);
    }

    if (!mDataRep->connectToServer(host, port)) {
      this->showText("Connection failed to " + hostPort.str());
      return false;
    }

    this->showText("Connected to " + hostPort.str());
    mIsConnected = true;
    mDataRep->initializeWithShader(this->shaderProgram());
    mDataRep->addSelfToRenderer(this->renderer());
    this->resetView();
    return true;
  }

  bool mIsConnected;
  int mPort;
  std::string mHost;

  //vesShaderProgram::Ptr mShader;
  vesKiwiStreamingDataRepresentation::Ptr mDataRep;
  vesKiwiText2DRepresentation* mText;
};


#endif /* defined(__CloudAppTab__kiwiCloudApp__) */
