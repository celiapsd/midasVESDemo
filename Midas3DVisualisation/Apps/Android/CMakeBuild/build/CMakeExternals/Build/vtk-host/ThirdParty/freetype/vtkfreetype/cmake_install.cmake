# Install script for directory: /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype

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

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "RuntimeLibraries")
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkfreetype-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkfreetype-6.0.so"
      )
    IF(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      FILE(RPATH_CHECK
           FILE "${file}"
           RPATH "")
    ENDIF()
  ENDFOREACH()
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE SHARED_LIBRARY FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkfreetype-6.0.so.1"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/lib/libvtkfreetype-6.0.so"
    )
  FOREACH(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkfreetype-6.0.so.1"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libvtkfreetype-6.0.so"
      )
    IF(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      IF(CMAKE_INSTALL_DO_STRIP)
        EXECUTE_PROCESS(COMMAND "/usr/bin/strip" "${file}")
      ENDIF(CMAKE_INSTALL_DO_STRIP)
    ENDIF()
  ENDFOREACH()
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "RuntimeLibraries")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0/vtkfreetype/include" TYPE FILE FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/ft2build.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/vtk_freetype_mangle.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/vtk_ftmodule.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/ThirdParty/freetype/vtkfreetype/include/vtkFreeTypeConfig.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0/vtkfreetype/include/freetype" TYPE FILE FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftstroke.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftlcdfil.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/tttables.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftgzip.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftglyph.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftincrem.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftxf86.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ttnameid.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftpfr.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftsynth.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftoutln.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/tttags.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftgxval.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftlzw.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftsystem.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/freetype.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftcache.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftcid.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/t1tables.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftbbox.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftrender.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftchapters.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/fttrigon.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftbdf.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/fterrdef.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftwinfnt.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftimage.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftmodapi.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftmm.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/fttypes.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftadvanc.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/fterrors.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftmac.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftlist.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftbitmap.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftsizes.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftsnames.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftmoderr.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftotval.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ttunpat.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftbzip2.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/ftgasp.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0/vtkfreetype/include/freetype/config" TYPE FILE FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/config/ftstdlib.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/config/ftheader.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/config/ftconfig.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/config/ftoption.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/config/ftmodule.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/vtk-6.0/vtkfreetype/include/freetype/internal" TYPE FILE FILES
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/sfnt.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftrfork.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftmemory.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/fttrace.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/pshints.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftvalid.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/internal.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/psaux.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftdebug.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftgloadr.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/tttypes.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftserv.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftobjs.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftstream.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftpic.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/autohint.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftdriver.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/ftcalc.h"
    "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/ThirdParty/freetype/vtkfreetype/include/freetype/internal/t1types.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Development")

