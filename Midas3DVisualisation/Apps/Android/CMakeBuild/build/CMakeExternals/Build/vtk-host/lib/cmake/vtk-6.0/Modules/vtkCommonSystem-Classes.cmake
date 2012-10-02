set(vtkCommonSystem_CLASSES_LOADED 1)
set(vtkCommonSystem_CLASSES "vtkClientSocket;vtkDirectory;vtkServerSocket;vtkSocket;vtkSocketCollection;vtkThreadMessager;vtkTimerLog")

foreach(class ${vtkCommonSystem_CLASSES})
  set(vtkCommonSystem_CLASS_${class}_EXISTS 1)
endforeach()

set(vtkCommonSystem_CLASS_vtkSocket_ABSTRACT 1)



