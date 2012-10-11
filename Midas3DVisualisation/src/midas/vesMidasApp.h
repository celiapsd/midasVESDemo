/// \class vesMidasApp
/// \ingroup
#ifndef __vesMidasApp_h
#define __vesMidasApp_h


// VES includes
#include <vesSharedPtr.h>
#include <vesKiwiViewerApp.h>
#include <vesKiwiBaseApp.h>

// C++ includes
#include <string>

//-------------------------------------------------
class vesMidasApp : public vesKiwiViewerApp
{

public :

  typedef vesKiwiViewerApp Superclass;
  vesMidasApp();
  ~vesMidasApp();

  void initBeginning(int width,int height,const std::string& filename, const std::string& path);
  void storeCameraState();
  void restoreCameraState();
  void initCamera(int w,int h);
  void addBuiltinDataset(std::string filename, std::string path);
  void initTime();
  void setParametersDataset(std::string filename, int builtinDatasetIndex);
  int getBuiltinDatasetIndex();
  int defaultBuiltinDatasetIndex() const;
  int nextBuiltinDatasetIndex() const;
  void clearExistingDataset();
  bool render();
  bool getDatasetIsLoaded();


private :

  class midasInternal;
  midasInternal* Internal;

};


#endif
