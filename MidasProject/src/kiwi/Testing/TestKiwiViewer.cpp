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

class MyTestHelper : public vesKiwiTestHelper {
public:

  MyTestHelper()
  {
    mCurrentDataset = 0;
    mKiwiApp = vesKiwiViewerApp::Ptr(new vesKiwiViewerApp);
    this->setApp(mKiwiApp);
  }

  void resetView()
  {
    mKiwiApp->applyBuiltinDatasetCameraParameters(mCurrentDataset);
  }

  void handleKeyboard(unsigned char key, int x, int y)
  {
    if (key == 'n') {
      mCurrentDataset = (mCurrentDataset + 1) % mKiwiApp->numberOfBuiltinDatasets();
      this->loadData(mCurrentDataset);
    }
    else {
      vesKiwiTestHelper::handleKeyboard(key, x, y);
    }
  }

  void loadData(int index)
  {
    std::string dataRoot = this->sourceDirectory() + "/Apps/iOS/Kiwi/Kiwi/Data/";
    std::string filename = dataRoot + mKiwiApp->builtinDatasetFilename(index);
    mKiwiApp->loadDataset(filename);
    mCurrentDataset = index;
    this->resetView();
  }

  bool doTesting()
  {
    const double threshold = 10.0;
    bool allTestsPassed = true;

    vesKiwiBaselineImageTester baselineTester;
    baselineTester.setApp(&*this->app());
    baselineTester.setBaselineImageDirectory(this->dataDirectory());

    // This loads each builtin dataset, renders it, and saves a screenshot

    // Note, this loop renders but does not bother to swap buffers
    for (int i = 0; i < mKiwiApp->numberOfBuiltinDatasets(); ++i) {
      this->loadData(i);

      // call the info methods, this helps coverage, though we're not testing the return values
      mKiwiApp->numberOfModelFacets();
      mKiwiApp->numberOfModelVertices();
      mKiwiApp->numberOfModelLines();

      mKiwiApp->render();
      std::string testName = mKiwiApp->builtinDatasetName(i);

      if (!baselineTester.performTest(testName, threshold)) {
        allTestsPassed = false;
      }
    }

    return allTestsPassed;
  }

  int mCurrentDataset;

  vesKiwiViewerApp::Ptr mKiwiApp;
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
  helper.init(&argc, argv, windowWidth, windowHeight, "TestKiwiViewer");
  helper.loadData(helper.mKiwiApp->defaultBuiltinDatasetIndex());

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
