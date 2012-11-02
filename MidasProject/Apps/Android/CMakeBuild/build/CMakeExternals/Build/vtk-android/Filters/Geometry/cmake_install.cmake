# Install script for directory: /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry

# Set the install prefix
IF(NOT DEFINED CMAKE_INSTALL_PREFIX)
  SET(CMAKE_INSTALL_PREFIX "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/vtk-android")
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
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib/cmake/vtk-6.0/Modules" TYPE FILE FILES "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Filters/Geometry/CMakeFiles/vtkFiltersGeometry.cmake")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE STATIC_LIBRARY FILES "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/lib/libvtkFiltersGeometry-6.0.a")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0" TYPE FILE FILES
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkCompositeDataGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkHierarchicalDataSetGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkImageDataGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkProjectSphereFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkRectilinearGridGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredGridGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredPointsGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkUnstructuredGridGeometryFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkDataSetSurfaceFilter.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkAbstractGridConnectivity.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkDataSetGhostGenerator.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkRectilinearGridPartitioner.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredAMRNeighbor.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredAMRGridConnectivity.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredGridConnectivity.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredGridGhostDataGenerator.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredGridPartitioner.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkStructuredNeighbor.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Filters/Geometry/vtkUniformGridGhostDataGenerator.h"
    "/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Filters/Geometry/vtkFiltersGeometryModule.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

