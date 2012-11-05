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

#include <vesActor.h>
#include <vesBuiltinShaders.h>
#include <vesKiwiBaseApp.h>
#include <vesKiwiDataLoader.h>
#include <vesKiwiBaselineImageTester.h>
#include <vesKiwiPolyDataRepresentation.h>
#include <vesShaderProgram.h>
#include <vesUniform.h>

#include <vtkPolyData.h>
#include <vtkSmartPointer.h>

#include <vesKiwiTestHelper.h>


class vesCapApp : public vesKiwiBaseApp {
public:

  vesTypeMacro(vesCapApp);

  vesCapApp() : vesKiwiBaseApp()
  {
    this->setBackgroundColor(63/255.0, 96/255.0, 144/255.0);
    this->DataRep = 0;
  }

  ~vesCapApp()
  {
    this->unloadData();
  }

  void initClipShader(const std::string& vertexSource, const std::string fragmentSource)
  {
    vesSharedPtr<vesShaderProgram> shaderProgram
      = this->addShaderProgram(vertexSource, fragmentSource);
    this->addModelViewMatrixUniform(shaderProgram);
    this->addProjectionMatrixUniform(shaderProgram);
    this->addNormalMatrixUniform(shaderProgram);
    this->addVertexPositionAttribute(shaderProgram);
    this->addVertexNormalAttribute(shaderProgram);
    this->addVertexColorAttribute(shaderProgram);
    this->addVertexTextureCoordinateAttribute(shaderProgram);
    this->ClipShader = shaderProgram;

    this->ClipUniform = vesSharedPtr<vesUniform>(
      new vesUniform("clipPlaneEquation", vesVector4f(-1.0f, 0.0f, 0.0f, 0.0f)));
    this->ClipShader->addUniform(this->ClipUniform);
  }

  void unloadData()
  {
    if (this->DataRep) {
      this->DataRep->removeSelfFromRenderer(this->renderer());
      delete this->DataRep;
      this->DataRep = 0;
    }
  }

  void loadData(const std::string& filename)
  {
    this->unloadData();

    vesKiwiDataLoader loader;
    vtkSmartPointer<vtkPolyData> polyData
      = vtkPolyData::SafeDownCast(loader.loadDataset(filename));
    assert(polyData.GetPointer());

    vesKiwiPolyDataRepresentation* rep = new vesKiwiPolyDataRepresentation();
    rep->initializeWithShader(this->ClipShader);
    rep->setPolyData(polyData);
    rep->actor()->setRotation(vesVector4f(0.0f, 1.0f, 0.0f, (-155.0f * M_PI/180.0f)));
    rep->addSelfToRenderer(this->renderer());
    this->DataRep = rep;
  }


  vesSharedPtr<vesUniform> ClipUniform;
  vesSharedPtr<vesShaderProgram> ClipShader;
  vesKiwiPolyDataRepresentation* DataRep;
};



class MyTestHelper : public vesKiwiTestHelper {
public:

  MyTestHelper()
  {
    mCapApp = vesCapApp::Ptr(new vesCapApp);
    this->setApp(mCapApp);
  }

  void loadData()
  {
    std::string filename = this->sourceDirectory() +
      std::string("/Apps/iOS/Kiwi/Kiwi/Data/bunny.vtp");

    mCapApp->loadData(filename);
    mCapApp->resetView();
  }

  void initApp()
  {
    mCapApp->initClipShader(vesBuiltinShaders::vesCap_vert(), vesBuiltinShaders::vesCap_frag());
  }

  bool doTesting()
  {
    const double threshold = 10.0;
    const std::string testName = "Capped Standford Bunny";

    vesKiwiBaselineImageTester baselineTester;
    baselineTester.setApp(&*this->app());
    baselineTester.setBaselineImageDirectory(this->dataDirectory());
    return baselineTester.performTest(testName, threshold);
  }


  vesCapApp::Ptr mCapApp;

};



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
  helper.init(&argc, argv, windowWidth, windowHeight, "TestCap");
  helper.initApp();
  helper.loadData();

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
    testPassed = helper.doTesting();
  }

  return testPassed ? 0 : 1;
}
