#----------------------------------------------------------------
# Generated CMake target import file for configuration "Release".
#----------------------------------------------------------------

# Commands may need to know the format version.
SET(CMAKE_IMPORT_FILE_VERSION 1)

# Compute the installation prefix relative to this file.
GET_FILENAME_COMPONENT(_IMPORT_PREFIX "${CMAKE_CURRENT_LIST_FILE}" PATH)
GET_FILENAME_COMPONENT(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)
GET_FILENAME_COMPONENT(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)
GET_FILENAME_COMPONENT(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)

# Import target "vtksys" for configuration "Release"
SET_PROPERTY(TARGET vtksys APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtksys PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "dl"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtksys-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtksys-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtksys )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtksys "${_IMPORT_PREFIX}/lib/libvtksys-6.0.so.1" )

# Import target "vtkCommonCore" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonCore PROPERTIES
  IMPORTED_LINK_DEPENDENT_LIBRARIES_RELEASE "vtksys"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonCore-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonCore-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonCore )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonCore "${_IMPORT_PREFIX}/lib/libvtkCommonCore-6.0.so.1" )

# Import target "vtkCommonMath" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonMath APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonMath PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonCore"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonMath-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonMath-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonMath )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonMath "${_IMPORT_PREFIX}/lib/libvtkCommonMath-6.0.so.1" )

# Import target "vtkCommonMisc" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonMisc APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonMisc PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonMath"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonMisc-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonMisc-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonMisc )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonMisc "${_IMPORT_PREFIX}/lib/libvtkCommonMisc-6.0.so.1" )

# Import target "vtkCommonSystem" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonSystem APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonSystem PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonCore;vtksys"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonSystem-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonSystem-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonSystem )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonSystem "${_IMPORT_PREFIX}/lib/libvtkCommonSystem-6.0.so.1" )

# Import target "vtkCommonTransforms" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonTransforms APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonTransforms PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonMath"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonTransforms-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonTransforms-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonTransforms )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonTransforms "${_IMPORT_PREFIX}/lib/libvtkCommonTransforms-6.0.so.1" )

# Import target "vtkCommonDataModel" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonDataModel APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonDataModel PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonMath;vtkCommonMisc;vtkCommonSystem;vtkCommonTransforms"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonDataModel-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonDataModel-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonDataModel )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonDataModel "${_IMPORT_PREFIX}/lib/libvtkCommonDataModel-6.0.so.1" )

# Import target "vtkCommonComputationalGeometry" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonComputationalGeometry APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonComputationalGeometry PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMath;vtkCommonSystem"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonComputationalGeometry-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonComputationalGeometry-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonComputationalGeometry )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonComputationalGeometry "${_IMPORT_PREFIX}/lib/libvtkCommonComputationalGeometry-6.0.so.1" )

# Import target "vtkCommonExecutionModel" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonExecutionModel APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonExecutionModel PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkCommonExecutionModel-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkCommonExecutionModel-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkCommonExecutionModel )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkCommonExecutionModel "${_IMPORT_PREFIX}/lib/libvtkCommonExecutionModel-6.0.so.1" )

# Import target "vtkDICOMParser" for configuration "Release"
SET_PROPERTY(TARGET vtkDICOMParser APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkDICOMParser PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkDICOMParser-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkDICOMParser-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkDICOMParser )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkDICOMParser "${_IMPORT_PREFIX}/lib/libvtkDICOMParser-6.0.so.1" )

# Import target "vtkFiltersCore" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonMath;vtkCommonMisc;vtkCommonSystem;vtkCommonTransforms"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkFiltersCore-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkFiltersCore-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkFiltersCore )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkFiltersCore "${_IMPORT_PREFIX}/lib/libvtkFiltersCore-6.0.so.1" )

# Import target "vtkFiltersGeneral" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersGeneral APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersGeneral PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonComputationalGeometry;vtkFiltersCore"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkFiltersGeneral-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkFiltersGeneral-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkFiltersGeneral )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkFiltersGeneral "${_IMPORT_PREFIX}/lib/libvtkFiltersGeneral-6.0.so.1" )

# Import target "vtkFiltersExtraction" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersExtraction APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersExtraction PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkFiltersCore;vtkFiltersGeneral"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkFiltersExtraction-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkFiltersExtraction-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkFiltersExtraction )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkFiltersExtraction "${_IMPORT_PREFIX}/lib/libvtkFiltersExtraction-6.0.so.1" )

# Import target "vtkFiltersGeometry" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersGeometry APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersGeometry PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkFiltersCore"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkFiltersGeometry-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkFiltersGeometry-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkFiltersGeometry )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkFiltersGeometry "${_IMPORT_PREFIX}/lib/libvtkFiltersGeometry-6.0.so.1" )

# Import target "vtkFiltersSources" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersSources APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersSources PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonComputationalGeometry;vtkFiltersGeneral"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkFiltersSources-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkFiltersSources-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkFiltersSources )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkFiltersSources "${_IMPORT_PREFIX}/lib/libvtkFiltersSources-6.0.so.1" )

# Import target "vtkFiltersModeling" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersModeling APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersModeling PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkFiltersGeneral;vtkFiltersSources"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkFiltersModeling-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkFiltersModeling-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkFiltersModeling )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkFiltersModeling "${_IMPORT_PREFIX}/lib/libvtkFiltersModeling-6.0.so.1" )

# Import target "vtkzlib" for configuration "Release"
SET_PROPERTY(TARGET vtkzlib APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkzlib PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkzlib-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkzlib-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkzlib )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkzlib "${_IMPORT_PREFIX}/lib/libvtkzlib-6.0.so.1" )

# Import target "vtkIOCore" for configuration "Release"
SET_PROPERTY(TARGET vtkIOCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonExecutionModel;vtkCommonMisc;vtksys;vtkzlib"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkIOCore-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkIOCore-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkIOCore )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkIOCore "${_IMPORT_PREFIX}/lib/libvtkIOCore-6.0.so.1" )

# Import target "vtkIOGeometry" for configuration "Release"
SET_PROPERTY(TARGET vtkIOGeometry APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOGeometry PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMisc;vtkCommonSystem;vtkIOCore;vtkzlib"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkIOGeometry-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkIOGeometry-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkIOGeometry )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkIOGeometry "${_IMPORT_PREFIX}/lib/libvtkIOGeometry-6.0.so.1" )

# Import target "vtkmetaio" for configuration "Release"
SET_PROPERTY(TARGET vtkmetaio APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkmetaio PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkzlib"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkmetaio-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkmetaio-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkmetaio )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkmetaio "${_IMPORT_PREFIX}/lib/libvtkmetaio-6.0.so.1" )

# Import target "vtkjpeg" for configuration "Release"
SET_PROPERTY(TARGET vtkjpeg APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkjpeg PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkjpeg-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkjpeg-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkjpeg )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkjpeg "${_IMPORT_PREFIX}/lib/libvtkjpeg-6.0.so.1" )

# Import target "vtkoggtheora" for configuration "Release"
SET_PROPERTY(TARGET vtkoggtheora APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkoggtheora PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkoggtheora-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkoggtheora-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkoggtheora )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkoggtheora "${_IMPORT_PREFIX}/lib/libvtkoggtheora-6.0.so.1" )

# Import target "vtkpng" for configuration "Release"
SET_PROPERTY(TARGET vtkpng APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkpng PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkzlib;-lm"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkpng-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkpng-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkpng )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkpng "${_IMPORT_PREFIX}/lib/libvtkpng-6.0.so.1" )

# Import target "vtktiff" for configuration "Release"
SET_PROPERTY(TARGET vtktiff APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtktiff PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkzlib;vtkjpeg;-lm"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtktiff-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtktiff-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtktiff )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtktiff "${_IMPORT_PREFIX}/lib/libvtktiff-6.0.so.1" )

# Import target "vtkIOImage" for configuration "Release"
SET_PROPERTY(TARGET vtkIOImage APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOImage PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMath;vtkCommonMisc;vtkCommonSystem;vtkCommonTransforms;vtkDICOMParser;vtkIOGeometry;vtkmetaio;vtkjpeg;vtkoggtheora;vtkpng;vtktiff"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkIOImage-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkIOImage-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkIOImage )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkIOImage "${_IMPORT_PREFIX}/lib/libvtkIOImage-6.0.so.1" )

# Import target "vtkIOPLY" for configuration "Release"
SET_PROPERTY(TARGET vtkIOPLY APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOPLY PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonMisc;vtkIOGeometry"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkIOPLY-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkIOPLY-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkIOPLY )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkIOPLY "${_IMPORT_PREFIX}/lib/libvtkIOPLY-6.0.so.1" )

# Import target "vtkexpat" for configuration "Release"
SET_PROPERTY(TARGET vtkexpat APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkexpat PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkexpat-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkexpat-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkexpat )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkexpat "${_IMPORT_PREFIX}/lib/libvtkexpat-6.0.so.1" )

# Import target "vtkIOXML" for configuration "Release"
SET_PROPERTY(TARGET vtkIOXML APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOXML PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMisc;vtkCommonSystem;vtkIOCore;vtkIOGeometry;vtkexpat"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkIOXML-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkIOXML-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkIOXML )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkIOXML "${_IMPORT_PREFIX}/lib/libvtkIOXML-6.0.so.1" )

# Import target "vtkImagingCore" for configuration "Release"
SET_PROPERTY(TARGET vtkImagingCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkImagingCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonMath;vtkCommonSystem;vtkCommonTransforms"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkImagingCore-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkImagingCore-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkImagingCore )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkImagingCore "${_IMPORT_PREFIX}/lib/libvtkImagingCore-6.0.so.1" )

# Import target "vtkParallelCore" for configuration "Release"
SET_PROPERTY(TARGET vtkParallelCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkParallelCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkIOGeometry"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkParallelCore-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkParallelCore-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkParallelCore )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkParallelCore "${_IMPORT_PREFIX}/lib/libvtkParallelCore-6.0.so.1" )

# Import target "vtkRenderingCore" for configuration "Release"
SET_PROPERTY(TARGET vtkRenderingCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkRenderingCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonTransforms;vtkFiltersExtraction;vtkFiltersGeneral;vtkFiltersGeometry;vtkFiltersSources;vtkIOImage;vtkIOXML"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkRenderingCore-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkRenderingCore-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkRenderingCore )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkRenderingCore "${_IMPORT_PREFIX}/lib/libvtkRenderingCore-6.0.so.1" )

# Import target "vtkfreetype" for configuration "Release"
SET_PROPERTY(TARGET vtkfreetype APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkfreetype PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkfreetype-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkfreetype-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkfreetype )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkfreetype "${_IMPORT_PREFIX}/lib/libvtkfreetype-6.0.so.1" )

# Import target "vtkRenderingFreeType" for configuration "Release"
SET_PROPERTY(TARGET vtkRenderingFreeType APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkRenderingFreeType PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkRenderingCore;vtkfreetype"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvtkRenderingFreeType-6.0.so.1"
  IMPORTED_SONAME_RELEASE "libvtkRenderingFreeType-6.0.so.1"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vtkRenderingFreeType )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vtkRenderingFreeType "${_IMPORT_PREFIX}/lib/libvtkRenderingFreeType-6.0.so.1" )

# Loop over all imported files and verify that they actually exist
FOREACH(target ${_IMPORT_CHECK_TARGETS} )
  FOREACH(file ${_IMPORT_CHECK_FILES_FOR_${target}} )
    IF(NOT EXISTS "${file}" )
      MESSAGE(FATAL_ERROR "The imported target \"${target}\" references the file
   \"${file}\"
but this file does not exist.  Possible reasons include:
* The file was deleted, renamed, or moved to another location.
* An install or uninstall procedure did not complete successfully.
* The installation package was faulty and contained
   \"${CMAKE_CURRENT_LIST_FILE}\"
but not all the files it references.
")
    ENDIF()
  ENDFOREACH()
  UNSET(_IMPORT_CHECK_FILES_FOR_${target})
ENDFOREACH()
UNSET(_IMPORT_CHECK_TARGETS)

# Cleanup temporary variables.
SET(_IMPORT_PREFIX)

# Commands beyond this point should not need to know the version.
SET(CMAKE_IMPORT_FILE_VERSION)
