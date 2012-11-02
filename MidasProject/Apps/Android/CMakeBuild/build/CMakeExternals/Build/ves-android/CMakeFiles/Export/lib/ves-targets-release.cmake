#----------------------------------------------------------------
# Generated CMake target import file for configuration "Release".
#----------------------------------------------------------------

# Commands may need to know the format version.
SET(CMAKE_IMPORT_FILE_VERSION 1)

# Compute the installation prefix relative to this file.
GET_FILENAME_COMPONENT(_IMPORT_PREFIX "${CMAKE_CURRENT_LIST_FILE}" PATH)
GET_FILENAME_COMPONENT(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)

# Import target "ves" for configuration "Release"
SET_PROPERTY(TARGET ves APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(ves PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libves.a"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS ves )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_ves "${_IMPORT_PREFIX}/lib/libves.a" )

# Import target "vesShaders" for configuration "Release"
SET_PROPERTY(TARGET vesShaders APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vesShaders PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libvesShaders.a"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS vesShaders )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_vesShaders "${_IMPORT_PREFIX}/lib/libvesShaders.a" )

# Import target "kiwi" for configuration "Release"
SET_PROPERTY(TARGET kiwi APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(kiwi PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C;CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "ves;vesShaders;vtkIOGeometry;vtkIOXML;vtkIOImage;vtkIOInfovis;vtkIOLegacy;vtkIOPLY;vtkFiltersCore;vtkFiltersSources;vtkFiltersGeometry;vtkFiltersModeling;vtkImagingCore;vtkRenderingCore;vtkRenderingFreeType;/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/curl-android/lib/libcurl.a;/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/libarchive-android/lib/libarchive.a"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libkiwi.a"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS kiwi )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_kiwi "${_IMPORT_PREFIX}/lib/libkiwi.a" )

# Import target "midas" for configuration "Release"
SET_PROPERTY(TARGET midas APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(midas PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "kiwi;ves;/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/curl-android/lib/libcurl.a;/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/libarchive-android/lib/libarchive.a"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libmidas.a"
  )

LIST(APPEND _IMPORT_CHECK_TARGETS midas )
LIST(APPEND _IMPORT_CHECK_FILES_FOR_midas "${_IMPORT_PREFIX}/lib/libmidas.a" )

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
