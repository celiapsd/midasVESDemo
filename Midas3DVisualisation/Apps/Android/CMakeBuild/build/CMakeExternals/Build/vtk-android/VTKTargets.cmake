# Generated by CMake 2.8.7

IF("${CMAKE_MAJOR_VERSION}.${CMAKE_MINOR_VERSION}" LESS 2.5)
   MESSAGE(FATAL_ERROR "CMake >= 2.6.0 required")
ENDIF("${CMAKE_MAJOR_VERSION}.${CMAKE_MINOR_VERSION}" LESS 2.5)
CMAKE_POLICY(PUSH)
CMAKE_POLICY(VERSION 2.6)
#----------------------------------------------------------------
# Generated CMake target import file.
#----------------------------------------------------------------

# Commands may need to know the format version.
SET(CMAKE_IMPORT_FILE_VERSION 1)

# Create imported target vtksys
ADD_LIBRARY(vtksys STATIC IMPORTED)

# Create imported target vtkCommonCore
ADD_LIBRARY(vtkCommonCore STATIC IMPORTED)

# Create imported target vtkCommonMath
ADD_LIBRARY(vtkCommonMath STATIC IMPORTED)

# Create imported target vtkCommonMisc
ADD_LIBRARY(vtkCommonMisc STATIC IMPORTED)

# Create imported target vtkCommonSystem
ADD_LIBRARY(vtkCommonSystem STATIC IMPORTED)

# Create imported target vtkCommonTransforms
ADD_LIBRARY(vtkCommonTransforms STATIC IMPORTED)

# Create imported target vtkCommonDataModel
ADD_LIBRARY(vtkCommonDataModel STATIC IMPORTED)

# Create imported target vtkCommonComputationalGeometry
ADD_LIBRARY(vtkCommonComputationalGeometry STATIC IMPORTED)

# Create imported target vtkCommonExecutionModel
ADD_LIBRARY(vtkCommonExecutionModel STATIC IMPORTED)

# Create imported target vtkDICOMParser
ADD_LIBRARY(vtkDICOMParser STATIC IMPORTED)

# Create imported target vtkFiltersCore
ADD_LIBRARY(vtkFiltersCore STATIC IMPORTED)

# Create imported target vtkFiltersGeneral
ADD_LIBRARY(vtkFiltersGeneral STATIC IMPORTED)

# Create imported target vtkFiltersExtraction
ADD_LIBRARY(vtkFiltersExtraction STATIC IMPORTED)

# Create imported target vtkFiltersGeometry
ADD_LIBRARY(vtkFiltersGeometry STATIC IMPORTED)

# Create imported target vtkFiltersSources
ADD_LIBRARY(vtkFiltersSources STATIC IMPORTED)

# Create imported target vtkFiltersModeling
ADD_LIBRARY(vtkFiltersModeling STATIC IMPORTED)

# Create imported target vtkzlib
ADD_LIBRARY(vtkzlib STATIC IMPORTED)

# Create imported target vtkIOCore
ADD_LIBRARY(vtkIOCore STATIC IMPORTED)

# Create imported target vtkIOGeometry
ADD_LIBRARY(vtkIOGeometry STATIC IMPORTED)

# Create imported target vtkmetaio
ADD_LIBRARY(vtkmetaio STATIC IMPORTED)

# Create imported target vtkjpeg
ADD_LIBRARY(vtkjpeg STATIC IMPORTED)

# Create imported target vtkoggtheora
ADD_LIBRARY(vtkoggtheora STATIC IMPORTED)

# Create imported target vtkpng
ADD_LIBRARY(vtkpng STATIC IMPORTED)

# Create imported target vtktiff
ADD_LIBRARY(vtktiff STATIC IMPORTED)

# Create imported target vtkIOImage
ADD_LIBRARY(vtkIOImage STATIC IMPORTED)

# Create imported target vtkIOPLY
ADD_LIBRARY(vtkIOPLY STATIC IMPORTED)

# Create imported target vtkexpat
ADD_LIBRARY(vtkexpat STATIC IMPORTED)

# Create imported target vtkIOXML
ADD_LIBRARY(vtkIOXML STATIC IMPORTED)

# Create imported target vtkImagingCore
ADD_LIBRARY(vtkImagingCore STATIC IMPORTED)

# Create imported target vtkParallelCore
ADD_LIBRARY(vtkParallelCore STATIC IMPORTED)

# Create imported target vtkRenderingCore
ADD_LIBRARY(vtkRenderingCore STATIC IMPORTED)

# Create imported target vtkfreetype
ADD_LIBRARY(vtkfreetype STATIC IMPORTED)

# Create imported target vtkRenderingFreeType
ADD_LIBRARY(vtkRenderingFreeType STATIC IMPORTED)

# Import target "vtksys" for configuration "Release"
SET_PROPERTY(TARGET vtksys APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtksys PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C;CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "dl"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtksys-6.0.a"
  )

# Import target "vtkCommonCore" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtksys"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonCore-6.0.a"
  )

# Import target "vtkCommonMath" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonMath APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonMath PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonCore"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonMath-6.0.a"
  )

# Import target "vtkCommonMisc" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonMisc APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonMisc PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonMath"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonMisc-6.0.a"
  )

# Import target "vtkCommonSystem" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonSystem APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonSystem PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonCore;vtksys"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonSystem-6.0.a"
  )

# Import target "vtkCommonTransforms" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonTransforms APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonTransforms PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonMath"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonTransforms-6.0.a"
  )

# Import target "vtkCommonDataModel" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonDataModel APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonDataModel PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonMath;vtkCommonMisc;vtkCommonSystem;vtkCommonTransforms"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonDataModel-6.0.a"
  )

# Import target "vtkCommonComputationalGeometry" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonComputationalGeometry APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonComputationalGeometry PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMath;vtkCommonSystem"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonComputationalGeometry-6.0.a"
  )

# Import target "vtkCommonExecutionModel" for configuration "Release"
SET_PROPERTY(TARGET vtkCommonExecutionModel APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkCommonExecutionModel PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkCommonExecutionModel-6.0.a"
  )

# Import target "vtkDICOMParser" for configuration "Release"
SET_PROPERTY(TARGET vtkDICOMParser APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkDICOMParser PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkDICOMParser-6.0.a"
  )

# Import target "vtkFiltersCore" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonMath;vtkCommonMisc;vtkCommonSystem;vtkCommonTransforms"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersCore-6.0.a"
  )

# Import target "vtkFiltersGeneral" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersGeneral APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersGeneral PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonComputationalGeometry;vtkFiltersCore"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersGeneral-6.0.a"
  )

# Import target "vtkFiltersExtraction" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersExtraction APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersExtraction PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkFiltersCore;vtkFiltersGeneral"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersExtraction-6.0.a"
  )

# Import target "vtkFiltersGeometry" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersGeometry APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersGeometry PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkFiltersCore"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersGeometry-6.0.a"
  )

# Import target "vtkFiltersSources" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersSources APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersSources PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonComputationalGeometry;vtkFiltersGeneral"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersSources-6.0.a"
  )

# Import target "vtkFiltersModeling" for configuration "Release"
SET_PROPERTY(TARGET vtkFiltersModeling APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkFiltersModeling PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkFiltersGeneral;vtkFiltersSources"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersModeling-6.0.a"
  )

# Import target "vtkzlib" for configuration "Release"
SET_PROPERTY(TARGET vtkzlib APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkzlib PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkzlib-6.0.a"
  )

# Import target "vtkIOCore" for configuration "Release"
SET_PROPERTY(TARGET vtkIOCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonExecutionModel;vtkCommonMisc;vtksys;vtkzlib"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkIOCore-6.0.a"
  )

# Import target "vtkIOGeometry" for configuration "Release"
SET_PROPERTY(TARGET vtkIOGeometry APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOGeometry PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMisc;vtkCommonSystem;vtkIOCore;vtkzlib"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkIOGeometry-6.0.a"
  )

# Import target "vtkmetaio" for configuration "Release"
SET_PROPERTY(TARGET vtkmetaio APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkmetaio PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkzlib"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkmetaio-6.0.a"
  )

# Import target "vtkjpeg" for configuration "Release"
SET_PROPERTY(TARGET vtkjpeg APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkjpeg PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkjpeg-6.0.a"
  )

# Import target "vtkoggtheora" for configuration "Release"
SET_PROPERTY(TARGET vtkoggtheora APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkoggtheora PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkoggtheora-6.0.a"
  )

# Import target "vtkpng" for configuration "Release"
SET_PROPERTY(TARGET vtkpng APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkpng PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkzlib;-lm"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkpng-6.0.a"
  )

# Import target "vtktiff" for configuration "Release"
SET_PROPERTY(TARGET vtktiff APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtktiff PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkzlib;vtkjpeg;-lm"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtktiff-6.0.a"
  )

# Import target "vtkIOImage" for configuration "Release"
SET_PROPERTY(TARGET vtkIOImage APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOImage PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMath;vtkCommonMisc;vtkCommonSystem;vtkCommonTransforms;vtkDICOMParser;vtkIOGeometry;vtkmetaio;vtkjpeg;vtkoggtheora;vtkpng;vtktiff"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkIOImage-6.0.a"
  )

# Import target "vtkIOPLY" for configuration "Release"
SET_PROPERTY(TARGET vtkIOPLY APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOPLY PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonMisc;vtkIOGeometry"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkIOPLY-6.0.a"
  )

# Import target "vtkexpat" for configuration "Release"
SET_PROPERTY(TARGET vtkexpat APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkexpat PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkexpat-6.0.a"
  )

# Import target "vtkIOXML" for configuration "Release"
SET_PROPERTY(TARGET vtkIOXML APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkIOXML PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonDataModel;vtkCommonMisc;vtkCommonSystem;vtkIOCore;vtkIOGeometry;vtkexpat"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkIOXML-6.0.a"
  )

# Import target "vtkImagingCore" for configuration "Release"
SET_PROPERTY(TARGET vtkImagingCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkImagingCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonMath;vtkCommonSystem;vtkCommonTransforms"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkImagingCore-6.0.a"
  )

# Import target "vtkParallelCore" for configuration "Release"
SET_PROPERTY(TARGET vtkParallelCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkParallelCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkIOGeometry"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkParallelCore-6.0.a"
  )

# Import target "vtkRenderingCore" for configuration "Release"
SET_PROPERTY(TARGET vtkRenderingCore APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkRenderingCore PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkCommonExecutionModel;vtkCommonTransforms;vtkFiltersExtraction;vtkFiltersGeneral;vtkFiltersGeometry;vtkFiltersSources;vtkIOImage;vtkIOXML"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkRenderingCore-6.0.a"
  )

# Import target "vtkfreetype" for configuration "Release"
SET_PROPERTY(TARGET vtkfreetype APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkfreetype PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkfreetype-6.0.a"
  )

# Import target "vtkRenderingFreeType" for configuration "Release"
SET_PROPERTY(TARGET vtkRenderingFreeType APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
SET_TARGET_PROPERTIES(vtkRenderingFreeType PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "vtkRenderingCore;vtkfreetype"
  IMPORTED_LOCATION_RELEASE "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkRenderingFreeType-6.0.a"
  )

# Commands beyond this point should not need to know the version.
SET(CMAKE_IMPORT_FILE_VERSION)
CMAKE_POLICY(POP)