set(vtkRenderingFreeType_CLASSES_LOADED 1)
set(vtkRenderingFreeType_CLASSES "vtkFreeTypeStringToImage;vtkVectorText")

foreach(class ${vtkRenderingFreeType_CLASSES})
  set(vtkRenderingFreeType_CLASS_${class}_EXISTS 1)
endforeach()




