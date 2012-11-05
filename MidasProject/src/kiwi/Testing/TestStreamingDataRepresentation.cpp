/*========================================================================
  VES --- VTK OpenGL ES Rendering Toolkit

      http://www.kitware.com/ves

  Copyright 2011 Kitware, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 ========================================================================*/

#include <iostream>
#include <sstream>
#include <fstream>
#include <cassert>
#include <cstdlib>
#include <cstdio>
#include <cstring>

#include <vesKiwiViewerApp.h>
#include <vesKiwiBaselineImageTester.h>
#include <vesBuiltinShaders.h>
#include <vesKiwiTestHelper.h>


#include <vesKiwiStreamingDataRepresentation.h>
#include <vesKiwiText2DRepresentation.h>
#include <vesCamera.h>


class vesKiwiPointCloudApp : public vesKiwiViewerApp {
public:

  vesTypeMacro(vesKiwiPointCloudApp);
  typedef vesKiwiViewerApp Superclass;

  vesKiwiPointCloudApp() : vesKiwiViewerApp()
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



class MyTestHelper : public vesKiwiTestHelper {
public:

  MyTestHelper()
  {
    mKiwiApp = vesKiwiPointCloudApp::Ptr(new vesKiwiPointCloudApp);
    this->setApp(mKiwiApp);
  }


  void handleKeyboard(unsigned char key, int x, int y)
  {
    vesKiwiTestHelper::handleKeyboard(key, x, y);
  }


  vesKiwiPointCloudApp::Ptr mKiwiApp;
};


void testStreaming(vesKiwiPointCloudApp::Ptr app)
{
  app->setHost("localhost");
  app->setPort(11111);
}



int main(int argc, char *argv[])
{
  if (argc < 2) {
    printf("Usage: %s <path to VES source directory> [path to testing data directory]\n", argv[0]);
    return 1;
  }

  MyTestHelper helper;
  helper.setSourceDirectory(argv[1]);
  if (argc == 3) {
    helper.setDataDirectory(argv[2]);
    helper.setTesting(true);
  }

  const int windowWidth = 800;
  const int windowHeight = 600;
  helper.init(&argc, argv, windowWidth, windowHeight, "TestStreamingDataRepresentation");

  testStreaming(helper.mKiwiApp);

  // render once
  helper.app()->resizeView(windowWidth, windowHeight);
  helper.app()->resetView();
  helper.app()->render();

  // begin the event loop if not in testing mode
  bool testPassed = true;
  if (!helper.isTesting()) {
    helper.startMainLoop();
  }
  else {
    //testPassed = helper.doTesting();
  }

  return testPassed ? 0 : 1;
}
