# Install script for directory: /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry

# Set the install prefix
IF(NOT DEFINED CMAKE_INSTALL_PREFIX)
  SET(CMAKE_INSTALL_PREFIX "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Install/vtk-host")
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
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib/cmake/vtk-6.0/Modules" TYPE FILE FILES "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/IO/Geometry/CMakeFiles/vtkIOGeometry.cmake")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "RuntimeLibraries")
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkIOGeometry-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkIOGeometry-6.0.so"
      )
    IF(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      FILE(RPATH_CHECK
           FILE "${file}"
           RPATH "")
    ENDIF()
  ENDFOREACH()
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE SHARED_LIBRARY FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkIOGeometry-6.0.so.1"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkIOGeometry-6.0.so"
    )
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkIOGeometry-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkIOGeometry-6.0.so"
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
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkAVSucdReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkBYUReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkBYUWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkChacoReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkCompositeDataReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkCompositeDataWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkDataSetReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkDataSetWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkFacetWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkGAMBITReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkGenericDataObjectReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkGenericDataObjectWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkGenericMovieWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkIVWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkMaterialLibrary.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkMCubesReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkMCubesWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkMFIXReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkMoleculeReaderBase.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkMultiBlockPLOT3DReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkOBJReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkOpenFOAMReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkParticleReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkPDBReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkPolyDataReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkPolyDataWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkProStarReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkRectilinearGridReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkRectilinearGridWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkSESAMEReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkSimplePointsReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkSimplePointsWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkSTLReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkSTLWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkStructuredGridReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkStructuredGridWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkTecplotReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkTreeReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkTreeWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkUGFacetReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkUnstructuredGridReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkUnstructuredGridWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkStructuredPointsReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkStructuredPointsWriter.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkShaderCodeLibrary.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/IO/Geometry/vtkXYZMolReader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/IO/Geometry/vtkIOGeometryModule.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

