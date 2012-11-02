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

#include "vesKiwiImageWidgetRepresentation.h"

#include "vesRenderer.h"
#include "vesCamera.h"
#include "vesMapper.h"
#include "vesActor.h"
#include "vesKiwiDataConversionTools.h"
#include "vesKiwiImagePlaneDataRepresentation.h"
#include "vesKiwiPolyDataRepresentation.h"

#include <vtkNew.h>
#include <vtkImageData.h>
#include <vtkPolyData.h>
#include <vtkLookupTable.h>
#include <vtkOutlineFilter.h>
#include <vtkPointData.h>
#include <vtkExtractVOI.h>
#include <vtkContourFilter.h>
#include <vtkCellLocator.h>
#include <vtkAppendPolyData.h>
#include <vtkTimerLog.h>
#include <vtkShortArray.h>
#include <vtkUnsignedShortArray.h>
#include <vtkCharArray.h>
#include <vtkUnsignedCharArray.h>
#include <vtkFloatArray.h>
#include <vtkImagePermute.h>


#include <vtkIntArray.h>
#include <vtkUnsignedIntArray.h>
#include <vtkDoubleArray.h>
#include <vtkLongArray.h>
#include <vtkUnsignedLongArray.h>
#include <vtkIdTypeArray.h>

#include <vector>
#include <map>
#include <cassert>

//----------------------------------------------------------------------------
class vesKiwiImageWidgetRepresentation::vesInternal
{
public:

  vesInternal()
  {
    this->SelectedImageDimension = -1;
    this->ContourVis = 0;
    this->ImageScalarRange[0] = 0;
    this->ImageScalarRange[1] = 1;
    for (int i = 0; i < 3; ++i)
      this->CurrentSliceIndices[i] = 0;

    this->ContourRep = 0;
    this->OutlineRep = 0;
    this->UseContour = false;
    this->InteractionEnabled = true;
    this->WindowLevelInteractionEnabled = false;
    this->RefreshTextures = false;
  }

  ~vesInternal()
  {
    for (size_t i = 0; i < this->AllReps.size(); ++i) {
      delete this->AllReps[i];
    }
  }


  int SelectedImageDimension;
  int CurrentSliceIndices[3];
  int ContourVis;
  bool UseContour;
  bool InteractionEnabled;
  bool WindowLevelInteractionEnabled;
  bool RefreshTextures;
  double ImageScalarRange[2];

  std::vector<vesKiwiDataRepresentation*> AllReps;
  std::vector<vesKiwiImagePlaneDataRepresentation*> SliceReps;

  std::map<int, int> TargetSliceIndex;

  vesKiwiPolyDataRepresentation* ContourRep;
  vesKiwiPolyDataRepresentation* OutlineRep;

  vtkSmartPointer<vtkExtractVOI> SliceFilter;
  vtkSmartPointer<vtkCellLocator> Locator;
  vtkSmartPointer<vtkAppendPolyData> AppendFilter;
  vtkSmartPointer<vtkLookupTable> LookupTable;

  vtkSmartPointer<vtkImageData> SliceImages[3];

  std::vector<vtkSmartPointer<vtkImageData> > ZSlices;

  vtkSmartPointer<vtkImageData> StoredImage;
};

//----------------------------------------------------------------------------
vesKiwiImageWidgetRepresentation::vesKiwiImageWidgetRepresentation()
{
  this->Internal = new vesInternal();

  this->OriginalWindow           = 1.0;
  this->OriginalLevel            = 0.5;
  this->CurrentWindow            = 1.0;
  this->CurrentLevel             = 0.5;
}

//----------------------------------------------------------------------------
vesKiwiImageWidgetRepresentation::~vesKiwiImageWidgetRepresentation()
{
  delete this->Internal;
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::willRender(vesSharedPtr<vesRenderer> renderer)
{
  vesNotUsed(renderer);

  if (this->Internal->TargetSliceIndex.size()) {

    std::map<int, int>::const_iterator itr;
    for (itr = this->Internal->TargetSliceIndex.begin(); itr != this->Internal->TargetSliceIndex.end(); ++itr) {
      this->setSliceIndex(itr->first, itr->second);
    }

    this->Internal->TargetSliceIndex.clear();
  }

  if (this->Internal->RefreshTextures) {
    for (int i = 0; i < 3; ++i) {
      vtkImageData* imageData = this->Internal->SliceReps[i]->imageData();
      if (imageData && this->planeVisibility(i)) {
        this->Internal->SliceReps[i]->setImageData(imageData);
      }
    }
    this->Internal->RefreshTextures = false;
  }
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::refreshTextures()
{
  this->Internal->RefreshTextures = true;
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setInteractionIsEnabled(bool enabled)
{
  this->Internal->InteractionEnabled = enabled;
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::interactionIsEnabled() const
{
  return this->Internal->InteractionEnabled;
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setWindowLevelInteractionEnabled(bool enabled)
{
  this->Internal->WindowLevelInteractionEnabled = enabled;
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::windowLevelInteractionEnabled() const
{
  return this->Internal->WindowLevelInteractionEnabled;
}

//----------------------------------------------------------------------------
vtkImageData* vesKiwiImageWidgetRepresentation::imageData() const
{
  return static_cast<vtkImageData*>(this->Internal->SliceFilter->GetInput());
}

//----------------------------------------------------------------------------
double* vesKiwiImageWidgetRepresentation::imageBounds()
{
  return this->imageData()->GetBounds();
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setScrollSlice(int planeIndex)
{
  this->Internal->SelectedImageDimension = planeIndex;
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setImageData(vtkImageData* image)
{

  /*
  vtkSmartPointer<vtkImagePermute> permute = vtkSmartPointer<vtkImagePermute>::New();
  permute->SetInputData(image);
  permute->SetFilteredAxes(0,2,1);
  //permute->SetFilteredAxes(0,1,2);
  permute->Update();
  image = permute->GetOutput();
  this->Internal->StoredImage = image;
  */

  image->GetPointData()->GetScalars()->GetRange(this->Internal->ImageScalarRange);


  //vtkSmartPointer<vtkScalarsToColors> colorMap =
  //  vesKiwiDataConversionTools::GetGrayscaleLookupTable(this->Internal->ImageScalarRange);

  this->Internal->LookupTable = vtkSmartPointer<vtkLookupTable>::New();

  this->Internal->LookupTable->SetNumberOfColors( 256);
  this->Internal->LookupTable->SetHueRange( 0, 0);
  this->Internal->LookupTable->SetSaturationRange( 0, 0);
  this->Internal->LookupTable->SetValueRange( 0 ,1);
  this->Internal->LookupTable->SetAlphaRange( 1, 1);

  double range[2];
  image->GetScalarRange(range);

  this->Internal->LookupTable->SetTableRange(range[0],range[1]);
  this->Internal->LookupTable->Build();

  this->OriginalWindow = range[1] - range[0];
  this->OriginalLevel = 0.5*(range[0] + range[1]);

  if( fabs( this->OriginalWindow ) < 0.001 )
    {
    this->OriginalWindow = 0.001 * ( this->OriginalWindow < 0.0 ? -1 : 1 );
    }
  if( fabs( this->OriginalLevel ) < 0.001 )
    {
    this->OriginalLevel = 0.001 * ( this->OriginalLevel < 0.0 ? -1 : 1 );
    }

  this->setWindowLevel(this->OriginalWindow,this->OriginalLevel);
  this->Internal->RefreshTextures = false;




  for (int i = 0; i < 3; ++i)
    this->Internal->SliceReps[i]->setColorMap(this->Internal->LookupTable);


  int dimensions[3];
  image->GetDimensions(dimensions);
  this->Internal->CurrentSliceIndices[0] = dimensions[0]/2;
  this->Internal->CurrentSliceIndices[1] = dimensions[1]/2;
  this->Internal->CurrentSliceIndices[2] = dimensions[2]/2;

  this->Internal->SliceFilter = vtkSmartPointer<vtkExtractVOI>::New();
  this->Internal->SliceFilter->SetInputData(image);




  // prepare z slices
  /*
  printf("preparing z slices...\n");

  for (size_t sliceIndex = 0; sliceIndex < dimensions[2]; ++sliceIndex) {

    printf("%d...\n", sliceIndex);
    vtkSmartPointer<vtkImageData> sliceImage = vtkSmartPointer<vtkImageData>::New();
    this->Internal->ZSlices.push_back(sliceImage);
    sliceImage->SetOrigin(this->imageData()->GetOrigin());
    sliceImage->SetSpacing(this->imageData()->GetSpacing());
    sliceImage->SetDimensions(dimensions[0], dimensions[1], 1);
    sliceImage->AllocateScalars(VTK_SHORT, 1);

    vtkShortArray* dataArray = vtkShortArray::SafeDownCast(sliceImage->GetPointData()->GetScalars());
    short* data = dataArray->GetPointer(0);
    short* pixels = vtkShortArray::SafeDownCast(this->imageData()->GetPointData()->GetScalars())->GetPointer(0);

    const size_t z = sliceIndex;
    for (size_t x = 0; x < dimensions[0]; ++x) {
      for (size_t y = 0; y < dimensions[1]; ++y) {

        data[y*dimensions[0] + x] = pixels[(z * dimensions[1] + y) * dimensions[0] + x];
        //data[y*dimensions[0] + x] = pixels[(z * (dimensions[0]*dimensions[1])) + (y*dimensions[0]) + x];
      }
    }
  }
  */




  for (int i = 0; i < 3; ++i)
    this->setSliceIndex(i, this->Internal->CurrentSliceIndices[i]);

  vtkNew<vtkOutlineFilter> outline;
  outline->SetInputData(image);
  outline->Update();
  this->Internal->OutlineRep->setPolyData(outline->GetOutput());
  this->Internal->OutlineRep->setColor(0.5, 0.5, 0.5, 0.5);

  if (false && image->GetNumberOfPoints() < 600000) {
    vtkNew<vtkContourFilter> contour;
    contour->SetInputData(image);
    // contour value hardcoded for head image dataset
    contour->SetValue(0, 1400);
    contour->ComputeScalarsOff();
    contour->ComputeNormalsOff();
    contour->Update();

    this->Internal->ContourRep->setPolyData(contour->GetOutput());
    this->Internal->ContourRep->setColor(0.8, 0.8, 0.8, 0.4);
    this->Internal->ContourVis = 1;
    this->Internal->AllReps.push_back(this->Internal->ContourRep);
    this->Internal->UseContour = true;
  }
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::invertTable()
{

  int index = this->Internal->LookupTable->GetNumberOfTableValues();
  unsigned char swap[4];
  size_t num = 4*sizeof(unsigned char);
  vtkUnsignedCharArray* table = this->Internal->LookupTable->GetTable();
  for ( int count = 0; count < --index; count++ )
    {
    unsigned char *rgba1 = table->GetPointer(4*count);
    unsigned char *rgba2 = table->GetPointer(4*index);
    memcpy( swap,  rgba1, num );
    memcpy( rgba1, rgba2, num );
    memcpy( rgba2, swap,  num );
    }

  // force the lookuptable to update its InsertTime to avoid
  // rebuilding the array
  this->Internal->LookupTable->SetTableValue(0, this->Internal->LookupTable->GetTableValue(0));

}

//----------------------------------------------------------------------------
double vesKiwiImageWidgetRepresentation::window() const
{
  return this->CurrentWindow;
}

//----------------------------------------------------------------------------
double vesKiwiImageWidgetRepresentation::level() const
{
  return this->CurrentLevel;
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::resetWindowLevel()
{
  this->setWindowLevel(this->OriginalWindow, this->OriginalLevel);
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setWindowLevel(double window, double level)
{
  if (this->CurrentWindow == window && this->CurrentLevel == level)
    {
    return;
    }

  // if the new window is negative and the old window was positive invert table
  if (   ( window < 0 && this->CurrentWindow > 0 )
      || ( window > 0 && this->CurrentWindow < 0 ))
    {
    this->invertTable();
    }

  this->CurrentWindow = window;
  this->CurrentLevel = level;


  double rmin = this->CurrentLevel - 0.5*fabs( this->CurrentWindow );
  double rmax = rmin + fabs( this->CurrentWindow );
  this->Internal->LookupTable->SetTableRange( rmin, rmax );

  this->Internal->RefreshTextures = true;
}


//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::initializeWithShader(
  vesSharedPtr<vesShaderProgram> geometryShader,
  vesSharedPtr<vesShaderProgram> textureShader)
{

  this->Internal->AppendFilter = vtkSmartPointer<vtkAppendPolyData>::New();

  this->Internal->ContourRep = new vesKiwiPolyDataRepresentation();
  this->Internal->ContourRep->initializeWithShader(geometryShader);
  this->Internal->ContourRep->setBinNumber(2);
  this->Internal->OutlineRep = new vesKiwiPolyDataRepresentation();
  this->Internal->OutlineRep->initializeWithShader(geometryShader);
  this->Internal->OutlineRep->setBinNumber(2);
  this->Internal->AllReps.push_back(this->Internal->OutlineRep);

  for (int i = 0; i < 3; ++i) {
    vesKiwiImagePlaneDataRepresentation* rep = new vesKiwiImagePlaneDataRepresentation();
    rep->initializeWithShader(textureShader);
    rep->setBinNumber(1);
    this->Internal->SliceReps.push_back(rep);
    this->Internal->AllReps.push_back(rep);
    this->Internal->AppendFilter->AddInputData(vtkSmartPointer<vtkPolyData>::New());
  }

}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setOutlineVisible(bool visible)
{
  if (this->Internal->OutlineRep) {
    this->Internal->OutlineRep->actor()->setVisible(visible);
  }
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::scrollSliceModeActive() const
{
  return (this->Internal->SelectedImageDimension >= 0);
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setPlaneVisibility(int planeIndex, bool visible)
{
  assert(planeIndex >= 0 && planeIndex <= 2);

  this->Internal->SliceReps[planeIndex]->actor()->setVisible(visible);
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::planeVisibility(int planeIndex) const
{
  assert(planeIndex >= 0 && planeIndex <= 2);
  return this->Internal->SliceReps[planeIndex]->actor()->isVisible();
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setShaderProgram(
  vesSharedPtr<vesShaderProgram> shaderProgram)
{
  if (!shaderProgram) {
    return;
  }

  // Use new shader only for polydata representations.
  this->Internal->ContourRep->setShaderProgram(shaderProgram);
  this->Internal->OutlineRep->setShaderProgram(shaderProgram);
}

//----------------------------------------------------------------------------
vesSharedPtr<vesShaderProgram> vesKiwiImageWidgetRepresentation::shaderProgram() const
{
  return this->Internal->ContourRep->shaderProgram();
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::scrollImageSlice(double deltaX, double deltaY)
{
  deltaY *= -1;

  vesSharedPtr<vesRenderer> ren = this->renderer();
  vesSharedPtr<vesCamera> camera = ren->camera();
  vesVector3f viewFocus = camera->focalPoint();
  vesVector3f viewFocusDisplay = ren->computeWorldToDisplay(viewFocus);
  float focalDepth = viewFocusDisplay[2];

  double x0 = viewFocusDisplay[0];
  double y0 = viewFocusDisplay[1];
  double x1 = x0 + deltaX;
  double y1 = y0 + deltaY;

  // map change into world coordinates
  vesVector3f point0 = ren->computeDisplayToWorld(vesVector3f(x0, y0, focalDepth));
  vesVector3f point1 = ren->computeDisplayToWorld(vesVector3f(x1, y1, focalDepth));
  vesVector3f motionVector = point1 - point0;

  int flatDimension = this->Internal->SelectedImageDimension;

  vesVector3f planeNormal(0, 0, 0);
  planeNormal[flatDimension] = 1.0;

  double vectorDot = motionVector.dot(planeNormal);
  double delta = vectorDot;
  if (fabs(delta) < 1e-6) {
    delta = deltaY;
  }

  int sliceDelta = static_cast<int>(delta);
  if (sliceDelta == 0) {
    sliceDelta = delta > 0 ? 1 : -1;
  }

  int sliceIndex = this->Internal->CurrentSliceIndices[flatDimension] + sliceDelta;
  this->scheduleSetSliceIndex(flatDimension, sliceIndex);
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::scheduleSetSliceIndex(int planeIndex, int sliceIndex)
{
  int dimensions[3];
  this->imageData()->GetDimensions(dimensions);

  if (sliceIndex < 0) {
    sliceIndex = 0;
  }
  else if (sliceIndex >= dimensions[planeIndex]) {
    sliceIndex = dimensions[planeIndex] - 1;
  }

  this->Internal->TargetSliceIndex[planeIndex] = sliceIndex;
  this->Internal->CurrentSliceIndices[planeIndex] = sliceIndex;
}


//vtkIdType GetLinearIndex(const int i, const int j, const int k, const int N1, const int N2 )
//{
//  return( (k*N2+j)*N1+i );
//}

//----------------------------------------------------------------------------
int vesKiwiImageWidgetRepresentation::numberOfSlices(int planeIndex) const
{
  assert(planeIndex >= 0 && planeIndex <= 2);
  return this->imageData()->GetDimensions()[planeIndex];
}

//----------------------------------------------------------------------------
int vesKiwiImageWidgetRepresentation::sliceIndex(int planeIndex) const
{
  assert(planeIndex >= 0 && planeIndex <= 2);
  return this->Internal->CurrentSliceIndices[planeIndex];
}


template <typename VTKARRAYTYPE, typename PRIMITIVETYPE>
void extractSliceExecute(vtkImageData *inImage, vtkImageData *sliceImage, int planeIndex, int sliceIndex)
{
  PRIMITIVETYPE* pixels = VTKARRAYTYPE::SafeDownCast(inImage->GetPointData()->GetScalars())->GetPointer(0);
  PRIMITIVETYPE* data = VTKARRAYTYPE::SafeDownCast(sliceImage->GetPointData()->GetScalars())->GetPointer(0);

  int dimensions[3];
  double origin[3];
  double spacing[3];
  inImage->GetOrigin(origin);
  inImage->GetSpacing(spacing);
  inImage->GetDimensions(dimensions);


  if (planeIndex == 0) {

    // x axis, yz plane
    const vtkIdType x = sliceIndex;
    for (vtkIdType y = 0; y < dimensions[1]; ++y) {
      for (vtkIdType z = 0; z < dimensions[2]; ++z) {
        //data[z*dimensions[1] + y] = pixels[(z * dimensions[1] + y) * dimensions[0] + x];
        data[z*dimensions[1] + y] = pixels[(z * (dimensions[0]*dimensions[1])) + (y*dimensions[0]) + x];
      }
    }

  }
  else if (planeIndex == 1) {

    // y axis, xz plane
    const size_t y = sliceIndex;
    for (size_t x= 0; x < dimensions[0]; ++x) {
      for (size_t z = 0; z < dimensions[2]; ++z) {
        //data[z*dimensions[0] + x] = pixels[(z * dimensions[1] + y) * dimensions[0] + x];
        data[z*dimensions[0] + x] = pixels[(z * (dimensions[0]*dimensions[1])) + (y*dimensions[0]) + x];
      }
    }

  }
  else {

    // z axis, xy plane
    const size_t z = sliceIndex;
    for (size_t x = 0; x < dimensions[0]; ++x) {
      for (size_t y = 0; y < dimensions[1]; ++y) {
        //data[y*dimensions[0] + x] = pixels[(z * dimensions[1] + y) * dimensions[0] + x];
        data[y*dimensions[0] + x] = pixels[(z * (dimensions[0]*dimensions[1])) + (y*dimensions[0]) + x];
      }
    }

  }

  origin[planeIndex] = origin[planeIndex] + sliceIndex*spacing[planeIndex];
  sliceImage->SetOrigin(origin);

}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::setSliceIndex(int planeIndex, int sliceIndex)
{
  int dimensions[3];
  this->imageData()->GetDimensions(dimensions);

  if (sliceIndex < 0) {
    sliceIndex = 0;
  }
  else if (sliceIndex >= dimensions[planeIndex]) {
    sliceIndex = dimensions[planeIndex] - 1;
  }

  //printf("scalars type: %s\n", this->imageData()->GetPointData()->GetScalars()->GetClassName());

  // allocate images if needed
  if (planeIndex == 0) {
    if (!this->Internal->SliceImages[0]) {
      this->Internal->SliceImages[0] = vtkSmartPointer<vtkImageData>::New();
      this->Internal->SliceImages[0]->SetOrigin(this->imageData()->GetOrigin());
      this->Internal->SliceImages[0]->SetSpacing(this->imageData()->GetSpacing());
      this->Internal->SliceImages[0]->SetDimensions(1, dimensions[1], dimensions[2]);
      this->Internal->SliceImages[0]->AllocateScalars(this->imageData()->GetScalarType(), 1);
    }
  }
  else if (planeIndex == 1) {
    if (!this->Internal->SliceImages[1]) {
      this->Internal->SliceImages[1] = vtkSmartPointer<vtkImageData>::New();
      this->Internal->SliceImages[1]->SetOrigin(this->imageData()->GetOrigin());
      this->Internal->SliceImages[1]->SetSpacing(this->imageData()->GetSpacing());
      this->Internal->SliceImages[1]->SetDimensions(dimensions[0], 1, dimensions[2]);
      this->Internal->SliceImages[1]->AllocateScalars(this->imageData()->GetScalarType(), 1);
    }
  }
  else {
    if (!this->Internal->SliceImages[2]) {
      this->Internal->SliceImages[2] = vtkSmartPointer<vtkImageData>::New();
      this->Internal->SliceImages[2]->SetOrigin(this->imageData()->GetOrigin());
      this->Internal->SliceImages[2]->SetSpacing(this->imageData()->GetSpacing());
      this->Internal->SliceImages[2]->SetDimensions(dimensions[0], dimensions[1], 1);
      this->Internal->SliceImages[2]->AllocateScalars(this->imageData()->GetScalarType(), 1);
    }
  }

  vtkImageData* sliceImage = this->Internal->SliceImages[planeIndex];

  #define mycall(vtktypename, vtkarraytype, primitivetype)   \
    case vtktypename: extractSliceExecute<vtkarraytype, primitivetype>(this->imageData(), sliceImage, planeIndex, sliceIndex); break;

  switch (this->imageData()->GetScalarType())
    {
      mycall(VTK_CHAR, vtkCharArray, char);
      mycall(VTK_UNSIGNED_CHAR, vtkUnsignedCharArray, unsigned char);
      mycall(VTK_SHORT, vtkShortArray, short);
      mycall(VTK_UNSIGNED_SHORT,vtkUnsignedShortArray, unsigned short);
      mycall(VTK_INT, vtkIntArray, int);
      mycall(VTK_UNSIGNED_INT,vtkUnsignedIntArray, unsigned int);
      mycall(VTK_LONG, vtkLongArray, long);
      mycall(VTK_UNSIGNED_LONG,vtkUnsignedLongArray, unsigned long);
      mycall(VTK_FLOAT, vtkFloatArray, float);
      mycall(VTK_DOUBLE, vtkDoubleArray, double);
      mycall(VTK_ID_TYPE, vtkIdTypeArray, vtkIdType);
    default:
      vtkGenericWarningMacro("Execute: Unknown input ScalarType");
      return;
    }



  vesKiwiImagePlaneDataRepresentation* rep = this->Internal->SliceReps[planeIndex];
  rep->setImageData(sliceImage);

  this->Internal->AppendFilter->GetInput(planeIndex)->DeepCopy(rep->imagePlanePolyData());
  this->Internal->CurrentSliceIndices[planeIndex] = sliceIndex;
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::handleSingleTouchPanGesture(double deltaX, double deltaY)
{
  if (!this->interactionIsActive()) {
    return false;
  }

  if (this->Internal->WindowLevelInteractionEnabled) {
    this->setWindowLevel(this->CurrentWindow + deltaX, this->CurrentLevel+deltaY);
  }
  else {
    this->scrollImageSlice(deltaX, deltaY);
  }
  return true;
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::handleSingleTouchDown(int displayX, int displayY)
{
  if (!this->Internal->InteractionEnabled) {
    return false;
  }

  // calculate the focal depth so we'll know how far to move
  vesSharedPtr<vesRenderer> ren = this->renderer();

  // flip Y coordinate
  displayY = ren->height() - displayY;

  std::tr1::shared_ptr<vesCamera> camera = ren->camera();
  vesVector3f cameraFocalPoint = camera->focalPoint();
  vesVector3f cameraPosition = camera->position();
  vesVector3f displayFocus = ren->computeWorldToDisplay(cameraFocalPoint);
  float focalDepth = displayFocus[2];

  vesVector3f rayPoint0 = cameraPosition;
  vesVector3f rayPoint1 = ren->computeDisplayToWorld(vesVector3f(displayX, displayY, focalDepth));

  vesVector3f rayDirection = rayPoint1 - rayPoint0;

  rayDirection.normalize();
  rayDirection *= 1000.0;
  rayPoint1 += rayDirection;


  vtkNew<vtkAppendPolyData> appendFilter;
  std::vector<int> cellIdToPlaneId;

  for (int i = 0; i < 3; ++i) {
    if (this->planeVisibility(i)) {
      appendFilter->AddInputData(this->Internal->SliceReps[i]->imagePlanePolyData());
      cellIdToPlaneId.push_back(i);
    }
  }

  appendFilter->Update();

  vtkNew<vtkCellLocator> locator;
  //this->Internal->AppendFilter->Update();
  //locator->SetDataSet(this->Internal->AppendFilter->GetOutput());
  locator->SetDataSet(appendFilter->GetOutput());
  locator->BuildLocator();

  double p0[3] = {rayPoint0[0], rayPoint0[1], rayPoint0[2]};
  double p1[3] = {rayPoint1[0], rayPoint1[1], rayPoint1[2]};

  double pickPoint[3];
  double t;
  double paramCoords[3];
  vtkIdType cellId = -1;
  int subId;

  int result = locator->IntersectWithLine(p0, p1, 0.0, t, pickPoint, paramCoords, subId, cellId);
  if (result == 1) {
    this->Internal->SelectedImageDimension = cellIdToPlaneId[cellId];
    this->interactionOn();
  }
  else {
    this->Internal->SelectedImageDimension = -1;
    this->interactionOff();
  }

  return this->interactionIsActive();
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::handleDoubleTap(int displayX, int displayY)
{
  return false;

  vesNotUsed(displayX);
  vesNotUsed(displayY);

  if (!this->Internal->UseContour) {

    //this->Internal->InteractionEnabled = !this->Internal->InteractionEnabled;
    //return true;
    return false;
  }

  this->Internal->ContourVis = (this->Internal->ContourVis + 1) % 3;
  if (this->Internal->ContourVis == 0) {
    this->Internal->ContourRep->removeSelfFromRenderer(this->renderer());
  }
  else if (this->Internal->ContourVis == 1) {
    this->Internal->ContourRep->addSelfToRenderer(this->renderer());
    this->Internal->ContourRep->mapper()->setColor(0.8, 0.8, 0.8, 0.3);
  }
  else {
    this->Internal->ContourRep->mapper()->setColor(0.8, 0.8, 0.8, 1.0);
  }

  return true;
}

//----------------------------------------------------------------------------
bool vesKiwiImageWidgetRepresentation::handleSingleTouchUp()
{
  if (!this->interactionIsActive()) {
    return false;
  }

  this->Internal->SelectedImageDimension = -1;
  this->interactionOff();
  return true;
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::addSelfToRenderer(
  vesSharedPtr<vesRenderer> renderer)
{
  this->Superclass::addSelfToRenderer(renderer);
  for (size_t i = 0; i < this->Internal->AllReps.size(); ++i) {
    this->Internal->AllReps[i]->addSelfToRenderer(renderer);
  }
}

//----------------------------------------------------------------------------
void vesKiwiImageWidgetRepresentation::removeSelfFromRenderer(
  vesSharedPtr<vesRenderer> renderer)
{
  this->Superclass::removeSelfFromRenderer(renderer);
  for (size_t i = 0; i < this->Internal->AllReps.size(); ++i) {
    this->Internal->AllReps[i]->removeSelfFromRenderer(renderer);
  }
}

//----------------------------------------------------------------------------
int vesKiwiImageWidgetRepresentation::numberOfFacets()
{
  int count = 0;
  for (size_t i = 0; i < this->Internal->AllReps.size(); ++i)
    count += this->Internal->AllReps[i]->numberOfFacets();
  return count;
}

//----------------------------------------------------------------------------
int vesKiwiImageWidgetRepresentation::numberOfVertices()
{
  int count = 0;
  for (size_t i = 0; i < this->Internal->AllReps.size(); ++i)
    count += this->Internal->AllReps[i]->numberOfVertices();
  return count;
}

//----------------------------------------------------------------------------
int vesKiwiImageWidgetRepresentation::numberOfLines()
{
  int count = 0;
  for (size_t i = 0; i < this->Internal->AllReps.size(); ++i)
    count += this->Internal->AllReps[i]->numberOfLines();
  return count;
}
