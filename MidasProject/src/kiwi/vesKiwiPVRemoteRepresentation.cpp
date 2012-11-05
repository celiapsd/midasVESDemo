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

#include "vesKiwiPVRemoteRepresentation.h"

#include "vesRenderer.h"
#include "vesCamera.h"
#include "vesMapper.h"
#include "vesGeometryData.h"
#include "vesActor.h"
#include "vesShaderProgram.h"
#include "vesKiwiDataConversionTools.h"
#include "vesKiwiPolyDataRepresentation.h"

#include <vtkPolyData.h>
#include <vtkTimerLog.h>
#include <vtkNew.h>
#include <vtkDoubleArray.h>
#include <vtkUnsignedCharArray.h>
#include <vtkSphereSource.h>
#include <vtkClientSocket.h>
#include <vtkCharArray.h>
#include <vtkFloatArray.h>
#include <vtkUnsignedCharArray.h>
#include <vtkCharArray.h>
#include <vtkPoints.h>
#include <vtkPointData.h>
#include <vtkShortArray.h>
#include <vtkMultiThreader.h>
#include <vtkMutexLock.h>

#include <vtksys/SystemTools.hxx>

#include <vector>
#include <cassert>
#include <sstream>

#include "vesPVWebClient.h"
#include "vesPVWebDataSet.h"

//----------------------------------------------------------------------------
class vesKiwiPVRemoteRepresentation::vesInternal
{
public:

  vesInternal()
  {
    this->ShouldQuit = false;
    this->HaveNew = false;
    this->ClientThreadId = -1;

    this->NewCameraState = false;
  }

  ~vesInternal()
  {
  }

  int ClientThreadId;
  bool HaveNew;
  bool ShouldQuit;

  vtkNew<vtkClientSocket> Comm;
  vtkNew<vtkMultiThreader> MultiThreader;
  vtkNew<vtkMutexLock> Lock;

  std::vector<vesKiwiPolyDataRepresentation::Ptr> Reps;
  vesShaderProgram::Ptr GeometryShader;


  struct CameraStateStruct {

    vesVector3f Position;
    vesVector3f FocalPoint;
    vesVector3f ViewUp;

  };

  CameraStateStruct CameraState;
  bool NewCameraState;

};

//----------------------------------------------------------------------------
vesKiwiPVRemoteRepresentation::vesKiwiPVRemoteRepresentation()
{
  this->Internal = new vesInternal();
}

//----------------------------------------------------------------------------
vesKiwiPVRemoteRepresentation::~vesKiwiPVRemoteRepresentation()
{
  this->Internal->Lock->Lock();
  this->Internal->ShouldQuit = true;
  this->Internal->Lock->Unlock();
  delete this->Internal;
}

//----------------------------------------------------------------------------
bool vesKiwiPVRemoteRepresentation::connectToServer(const std::string& host, int port)
{
  return (this->Internal->Comm->ConnectToServer(host.c_str(), port) == 0);
}

namespace {



//----------------------------------------------------------------------------
VTK_THREAD_RETURN_TYPE ClientLoop(void* arg)
{
  vtkMultiThreader::ThreadInfo* threadInfo = static_cast<vtkMultiThreader::ThreadInfo*>(arg);

  vesKiwiPVRemoteRepresentation::vesInternal* selfInternal =
    static_cast<vesKiwiPVRemoteRepresentation::vesInternal*>(threadInfo->UserData);



  vesKiwiPVRemoteRepresentation::vesInternal::CameraStateStruct cameraState;

  bool shouldQuit = false;
  while (!shouldQuit) {

    //usleep(300);


    int command;
    selfInternal->Comm->Receive(&command, sizeof(command));

    if (command != 3) {
      printf("command mismatch\n");
      break;
    }

    selfInternal->Lock->Lock();
    shouldQuit = selfInternal->ShouldQuit;
    cameraState = selfInternal->CameraState;
    selfInternal->Lock->Unlock();

    if (selfInternal->Comm->Send(&selfInternal->CameraState, sizeof(selfInternal->CameraState)) == 0) {
      printf("error sending camera state\n");
      break;
    }
  }

  printf("returning from thread loop\n");

  return VTK_THREAD_RETURN_VALUE;
}

}


//----------------------------------------------------------------------------
void vesKiwiPVRemoteRepresentation::initializeWithShader(
  vesSharedPtr<vesShaderProgram> shader)
{
  this->Internal->GeometryShader = shader;

  int command = 1;
  this->Internal->Comm->Send(&command, sizeof(int));

  unsigned long long streamLength;

  this->Internal->Comm->Receive(&streamLength, 8);

  std::cout << "stream length: " << streamLength << std::endl;

  vtkCharArray* streamData = vtkCharArray::New();
  streamData->SetNumberOfTuples(streamLength);

  this->Internal->Comm->Receive(streamData->GetPointer(0), streamLength);

  printf("metadata:\n%s\n", streamData->GetPointer(0));

  std::stringstream resp;
  resp << std::string(streamData->GetPointer(0), streamLength);

  vesPVWebClient client;

  bool success = client.parseSceneMetaData(resp);
  if (!success) printf("parse error in scene meta data\n");

  const std::vector<std::tr1::shared_ptr<vesPVWebDataSet> >& datasets = client.datasets();

  printf("got %lu dataset parts\n", datasets.size());

  command = 2;
  this->Internal->Comm->Send(&command, sizeof(int));

  for (size_t i = 0; i < datasets.size(); ++i)
    {

    this->Internal->Comm->Receive(&streamLength, 8);
    std::cout << "part stream length: " << streamLength << std::endl;

    datasets[i]->m_buffer = new char[streamLength];
    this->Internal->Comm->Receive(datasets[i]->m_buffer, streamLength);

    datasets[i]->initFromBuffer();
    }



  for (size_t i = 0; i < datasets.size(); ++i) {

    const vesPVWebDataSet::Ptr dataset = datasets[i];

    if (dataset->m_datasetType == 'P')
      continue;

    if (dataset->m_layer != 0)
      continue;

    if (dataset->m_numberOfVerts == 0)
      continue;

    vesKiwiPolyDataRepresentation::Ptr rep = vesKiwiPolyDataRepresentation::Ptr(new vesKiwiPolyDataRepresentation);
    rep->initializeWithShader(this->Internal->GeometryShader);
    rep->setPVWebData(dataset);
    this->Internal->Reps.push_back(rep);
  }



  this->Internal->ClientThreadId = this->Internal->MultiThreader->SpawnThread(ClientLoop, this->Internal);
}

//----------------------------------------------------------------------------
bool vesKiwiPVRemoteRepresentation::handleSingleTouchDown(int displayX, int displayY)
{
  vesNotUsed(displayX);
  vesNotUsed(displayY);
  return false;
}

//----------------------------------------------------------------------------
bool vesKiwiPVRemoteRepresentation::handleSingleTouchUp()
{
  return false;
}

//----------------------------------------------------------------------------
bool vesKiwiPVRemoteRepresentation::handleSingleTouchTap(int displayX, int displayY)
{
  vesNotUsed(displayX);
  vesNotUsed(displayY);

  return false;
}

//----------------------------------------------------------------------------
bool vesKiwiPVRemoteRepresentation::handleSingleTouchPanGesture(double deltaX, double deltaY)
{
  vesNotUsed(deltaX);
  vesNotUsed(deltaY);
  return false;
}

//----------------------------------------------------------------------------
void vesKiwiPVRemoteRepresentation::willRender(vesSharedPtr<vesRenderer> renderer)
{
  vesNotUsed(renderer);

  this->Internal->Lock->Lock();

  this->Internal->NewCameraState = true;

  vesCamera::Ptr camera = renderer->camera();
  this->Internal->CameraState.Position = camera->position();
  this->Internal->CameraState.FocalPoint = camera->focalPoint();
  this->Internal->CameraState.ViewUp = camera->viewUp();

  //printf("pos: %f %f %f\n", this->Internal->CameraState.Position[0],this->Internal->CameraState.Position[1],this->Internal->CameraState.Position[2]);
  //printf("view up: %f %f %f\n", this->Internal->CameraState.ViewUp[0],this->Internal->CameraState.ViewUp[1],this->Internal->CameraState.ViewUp[2]);

  this->Internal->Lock->Unlock();

}

//----------------------------------------------------------------------------
void vesKiwiPVRemoteRepresentation::addSelfToRenderer(vesSharedPtr<vesRenderer> renderer)
{
  this->Superclass::addSelfToRenderer(renderer);

  for (size_t i = 0; i < this->Internal->Reps.size(); ++i)
    this->Internal->Reps[i]->addSelfToRenderer(renderer);
}

//----------------------------------------------------------------------------
void vesKiwiPVRemoteRepresentation::removeSelfFromRenderer(vesSharedPtr<vesRenderer> renderer)
{
  this->Superclass::removeSelfFromRenderer(renderer);

  this->Internal->Lock->Lock();

  for (size_t i = 0; i < this->Internal->Reps.size(); ++i)
    this->Internal->Reps[i]->removeSelfFromRenderer(renderer);

  this->Internal->Lock->Unlock();
}

//----------------------------------------------------------------------------
int vesKiwiPVRemoteRepresentation::numberOfFacets()
{
  return 0;
}

//----------------------------------------------------------------------------
int vesKiwiPVRemoteRepresentation::numberOfVertices()
{
  return 0;
}

//----------------------------------------------------------------------------
int vesKiwiPVRemoteRepresentation::numberOfLines()
{
  return 0;
}
