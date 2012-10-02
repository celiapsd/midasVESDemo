# Install script for directory: /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling

# Set the install prefix
IF(NOT DEFINED CMAKE_INSTALL_PREFIX)
  SET(CMAKE_INSTALL_PREFIX "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Install/vtk-android")
ENDIF(NOT DEFINED CMAKE_INSTALL_PREFIX)
STRING(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
IF(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  IF(BUILD_TYPE)
    STRING(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  ELSE(BUILD_TYPE)
    SET(CMAKE_INSTALL_CONFIG_NAME "Release")
  ENDIF(BUILD_TYPE)
  MESSAGE(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
ENDIF(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)

# Set the component getting installed.
IF(NOT CMAKE_INSTALL_COMPONENT)
  IF(COMPONENT)
    MESSAGE(STATUS "Install component: \"${COMPONENT}\"")
    SET(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  ELSE(COMPONENT)
    SET(CMAKE_INSTALL_COMPONENT)
  ENDIF(COMPONENT)
ENDIF(NOT CMAKE_INSTALL_COMPONENT)

# Install shared libraries without execute permission?
IF(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  SET(CMAKE_INSTALL_SO_NO_EXE "1")
ENDIF(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib/cmake/vtk-6.0/Modules" TYPE FILE FILES "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Filters/Modeling/CMakeFiles/vtkFiltersModeling.cmake")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE STATIC_LIBRARY FILES "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersModeling-6.0.a")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0" TYPE FILE FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkBandedPolyDataContourFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkButterflySubdivisionFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkDijkstraGraphGeodesicPath.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkDijkstraImageGeodesicPath.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkFillHolesFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkGeodesicPath.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkGraphGeodesicPath.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkLinearExtrusionFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkLinearSubdivisionFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkLoopSubdivisionFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkPolyDataPointSampler.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkProjectedTexture.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkQuadRotationalExtrusionFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkRibbonFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkRotationalExtrusionFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkRuledSurfaceFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkSelectEnclosedPoints.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkSelectPolyData.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkSpherePuzzleArrows.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkSpherePuzzle.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkSubdivideTetra.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkOutlineFilter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Modeling/vtkSectorSource.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Filters/Modeling/vtkFiltersModelingModule.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

