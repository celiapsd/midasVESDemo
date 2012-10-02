set(vtkParallelCore_CLASSES_LOADED 1)
set(vtkParallelCore_CLASSES "vtkCommunicator;vtkDummyCommunicator;vtkDummyController;vtkMultiProcessController;vtkProcess;vtkProcessGroup;vtkSocketCommunicator;vtkSocketController;vtkSubCommunicator;vtkSubGroup;vtkFieldDataSerializer")

foreach(class ${vtkParallelCore_CLASSES})
  set(vtkParallelCore_CLASS_${class}_EXISTS 1)
endforeach()

set(vtkParallelCore_CLASS_vtkCommunicator_ABSTRACT 1)
set(vtkParallelCore_CLASS_vtkMultiProcessController_ABSTRACT 1)
set(vtkParallelCore_CLASS_vtkProcess_ABSTRACT 1)



