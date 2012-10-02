# Install script for directory: /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core

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
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib/cmake/vtk-6.0/Modules" TYPE FILE FILES "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Parallel/Core/CMakeFiles/vtkParallelCore.cmake")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "RuntimeLibraries")
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkParallelCore-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkParallelCore-6.0.so"
      )
    IF(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      FILE(RPATH_CHECK
           FILE "${file}"
           RPATH "")
    ENDIF()
  ENDFOREACH()
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE SHARED_LIBRARY FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkParallelCore-6.0.so.1"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkParallelCore-6.0.so"
    )
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkParallelCore-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkParallelCore-6.0.so"
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
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkCommunicator.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkDummyCommunicator.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkDummyController.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkMultiProcessController.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkMultiProcessStream.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkProcess.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkProcessGroup.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkSocketCommunicator.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkSocketController.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkSubCommunicator.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkSubGroup.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Parallel/Core/vtkFieldDataSerializer.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Parallel/Core/vtkSocketCommunicatorHash.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Parallel/Core/vtkParallelCoreModule.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

