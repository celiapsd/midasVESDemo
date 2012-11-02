set(vtkIOXMLParser_CLASSES_LOADED 1)
set(vtkIOXMLParser_CLASSES "vtkXMLDataParser;vtkXMLParser;vtkXMLUtilities")

foreach(class ${vtkIOXMLParser_CLASSES})
  set(vtkIOXMLParser_CLASS_${class}_EXISTS 1)
endforeach()




