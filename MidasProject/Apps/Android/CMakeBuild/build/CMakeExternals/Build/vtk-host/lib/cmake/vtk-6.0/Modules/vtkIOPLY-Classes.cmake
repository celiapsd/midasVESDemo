set(vtkIOPLY_CLASSES_LOADED 1)
set(vtkIOPLY_CLASSES "vtkPLYReader;vtkPLYWriter")

foreach(class ${vtkIOPLY_CLASSES})
  set(vtkIOPLY_CLASS_${class}_EXISTS 1)
endforeach()




