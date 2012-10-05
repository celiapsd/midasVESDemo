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

  void storeCameraState();
  void addBuiltinDataset(std::string filename, std::string path);


private :

  class midasInternal;
  midasInternal* Internal;

};


#endif
