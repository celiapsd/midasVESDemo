# Install script for directory: /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction

# Set the install prefix
IF(NOT DEFINED CMAKE_INSTALL_PREFIX)
  SET(CMAKE_INSTALL_PREFIX "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/vtk-host")
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

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib/cmake/vtk-6.0/Modules" TYPE FILE FILES "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Filters/Extraction/CMakeFiles/vtkFiltersExtraction.cmake")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "RuntimeLibraries")
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkFiltersExtraction-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkFiltersExtraction-6.0.so"
      )
    IF(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      FILE(RPATH_CHECK
           FILE "${file}"
           RPATH "")
    ENDIF()
  ENDFOREACH()
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE SHARED_LIBRARY FILES
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkFiltersExtraction-6.0.so.1"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkFiltersExtraction-6.0.so"
    )
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkFiltersExtraction-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkFiltersExtraction-6.0.so"
      )
    IF(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      FILE(RPATH_REMOVE
           FILE "${file}")
      IF(CMAKE_INSTALL_DO_STRIP)
        EXECUTE_PROCESS(COMMAND "/usr/bin/strip" "${file}")
      ENDIF(CMAKE_INSTALL_DO_STRIP)
    ENDIF()
  ENDFOREACH()
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "RuntimeLibraries")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0" TYPE FILE FILES
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkConvertSelection.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractArraysOverTime.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractBlock.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractCells.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractDataOverTime.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractDataSets.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractEdges.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractGeometry.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractGrid.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractLevel.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractPolyDataGeometry.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractRectilinearGrid.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedBlock.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedFrustum.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedIds.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedLocations.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedPolyDataIds.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedRows.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectedThresholds.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelectionBase.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractSelection.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractTemporalFieldData.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractTensorComponents.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractUnstructuredGrid.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkExtractVectorComponents.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkProbeSelectedLocations.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkHierarchicalDataExtractDataSets.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Extraction/vtkHierarchicalDataExtractLevel.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Filters/Extraction/vtkFiltersExtractionModule.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

