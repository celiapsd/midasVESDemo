set(vtkCommonMisc_CLASSES_LOADED 1)
set(vtkCommonMisc_CLASSES "vtkContourValues;vtkFunctionParser;vtkHeap")

foreach(class ${vtkCommonMisc_CLASSES})
  set(vtkCommonMisc_CLASS_${class}_EXISTS 1)
endforeach()




