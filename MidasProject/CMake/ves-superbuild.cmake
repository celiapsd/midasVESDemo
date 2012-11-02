if(NOT VES_HOST_SUPERBUILD AND NOT VES_ANDROID_SUPERBUILD AND NOT VES_IOS_SUPERBUILD)
  return()
endif()

project(VES_SUPERBUILD NONE)

include(ExternalProject)


set(base "${CMAKE_BINARY_DIR}/CMakeExternals")
set_property(DIRECTORY PROPERTY EP_BASE ${base})

# set a default build type if it is undefined, then make sure it goes in the cache
if(NOT CMAKE_BUILD_TYPE)
  set(CMAKE_BUILD_TYPE Release)
endif()
set(CMAKE_BUILD_TYPE ${CMAKE_BUILD_TYPE} CACHE STRING "Build configuration type" FORCE)
set(build_type ${CMAKE_BUILD_TYPE})

set(source_prefix ${base}/Source)
set(build_prefix ${base}/Build)
set(install_prefix ${base}/Install)

set(toolchain_dir "${CMAKE_CURRENT_SOURCE_DIR}/CMake/toolchains")
set(ves_src "${CMAKE_CURRENT_SOURCE_DIR}")

find_package(PythonInterp REQUIRED)
find_package(Git REQUIRED)

set(module_defaults
  -DVTK_Group_StandAlone:BOOL=OFF
  -DVTK_Group_Rendering:BOOL=OFF
  -DModule_vtkFiltersCore:BOOL=ON
  -DModule_vtkFiltersModeling:BOOL=ON
  -DModule_vtkFiltersSources:BOOL=ON
  -DModule_vtkFiltersGeometry:BOOL=ON
  -DModule_vtkIOGeometry:BOOL=ON
  -DModule_vtkIOLegacy:BOOL=ON
  -DModule_vtkIOXML:BOOL=ON
  -DModule_vtkIOImage:BOOL=ON
  -DModule_vtkIOPLY:BOOL=ON
  -DModule_vtkIOInfovis:BOOL=ON
  -DModule_vtkImagingCore:BOOL=ON
  -DModule_vtkParallelCore:BOOL=ON
  -DModule_vtkRenderingCore:BOOL=ON
  -DModule_vtkRenderingFreeType:BOOL=ON
)

macro(force_build proj)
  ExternalProject_Add_Step(${proj} forcebuild
    COMMAND ${CMAKE_COMMAND} -E remove ${base}/Stamp/${proj}/${proj}-build
    DEPENDEES configure
    DEPENDERS build
    ALWAYS 1
  )
endmacro()

macro(install_eigen)
  set(eigen_url http://www.vtk.org/files/support/eigen-3.1.0-alpha1.tar.gz)
  set(eigen_md5 c04dedf4ae97b055b6dd2aaa01daf5e9)
  ExternalProject_Add(
    eigen
    SOURCE_DIR ${source_prefix}/eigen
    URL ${eigen_url}
    URL_MD5 ${eigen_md5}
    CONFIGURE_COMMAND ""
    BUILD_COMMAND ""
    INSTALL_COMMAND ${CMAKE_COMMAND} -E copy_directory "${source_prefix}/eigen/Eigen" "${install_prefix}/eigen/Eigen"
  )
endmacro()

macro(compile_vtk proj)
  if(NOT VES_HOST_SUPERBUILD)
    set(vtk_host_build_command BUILD_COMMAND make vtkCompileTools)
  endif()
  ExternalProject_Add(
    ${proj}
    SOURCE_DIR ${source_prefix}/vtk
    GIT_REPOSITORY git://github.com/patmarion/VTK.git
    GIT_TAG 96af1b5
    INSTALL_COMMAND ""
    ${vtk_host_build_command}
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DBUILD_SHARED_LIBS:BOOL=ON
      -DBUILD_TESTING:BOOL=OFF
      ${module_defaults}
  )
endmacro()



#
# libarchive fetch
#
macro(fetch_libarchive)
  ExternalProject_Add(
    libarchive-fetch
    SOURCE_DIR ${source_prefix}/libarchive
    GIT_REPOSITORY git://github.com/libarchive/libarchive.git
    GIT_TAG origin/release
    PATCH_COMMAND git apply ${ves_src}/CMake/libarchive_patch.diff
    CONFIGURE_COMMAND ""
    BUILD_COMMAND ""
    INSTALL_COMMAND ""
  )
endmacro()

#
# libarchive compile
#
macro(compile_libarchive tag)
  set(proj libarchive-${tag})
  ExternalProject_Add(
    ${proj}
    SOURCE_DIR ${source_prefix}/libarchive
    DOWNLOAD_COMMAND ""
    DEPENDS libarchive-fetch
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DBUILD_SHARED_LIBS:BOOL=OFF
      -DENABLE_NETTLE:BOOL=OFF
      -DENABLE_OPENSSL:BOOL=OFF
      -DENABLE_TAR:BOOL=OFF
      -DENABLE_TAR_SHARED:BOOL=OFF
      -DENABLE_CPIO:BOOL=OFF
      -DENABLE_CPIO_SHARED:BOOL=OFF
      -DENABLE_XATTR:BOOL=OFF
      -DENABLE_ACL:BOOL=OFF
      -DENABLE_ICONV:BOOL=OFF
      -DENABLE_TEST:BOOL=OFF
  )

  force_build(${proj})
endmacro()


#
# libarchive crosscompile
#
macro(crosscompile_libarchive proj toolchain_file)
  ExternalProject_Add(
    ${proj}
    SOURCE_DIR ${source_prefix}/libarchive
    DOWNLOAD_COMMAND ""
    DEPENDS libarchive-fetch
    CMAKE_ARGS
      -DCMAKE_TOOLCHAIN_FILE:FILEPATH=${toolchain_dir}/${toolchain_file}
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DBUILD_SHARED_LIBS:BOOL=OFF
      -DENABLE_NETTLE:BOOL=OFF
      -DENABLE_OPENSSL:BOOL=OFF
      -DENABLE_TAR:BOOL=OFF
      -DENABLE_TAR_SHARED:BOOL=OFF
      -DENABLE_CPIO:BOOL=OFF
      -DENABLE_CPIO_SHARED:BOOL=OFF
      -DENABLE_XATTR:BOOL=OFF
      -DENABLE_ACL:BOOL=OFF
      -DENABLE_ICONV:BOOL=OFF
      -DENABLE_TEST:BOOL=OFF
  )

  force_build(${proj})
endmacro()


macro(compile_ves proj)
  set(tag host)
  ExternalProject_Add(
    ${proj}
    SOURCE_DIR ${ves_src}
    DOWNLOAD_COMMAND ""
    DEPENDS vtk-${tag} eigen
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DBUILD_TESTING:BOOL=ON
      -DBUILD_SHARED_LIBS:BOOL=OFF
      -DVES_USE_VTK:BOOL=ON
      -DVES_USE_DESKTOP_GL:BOOL=ON
      -DVTK_DIR:PATH=${build_prefix}/vtk-${tag}
      -DEIGEN_INCLUDE_DIR:PATH=${install_prefix}/eigen


      -DCMAKE_CXX_FLAGS:STRING=${VES_CXX_FLAGS} -fPIC
  )

  force_build(${proj})
endmacro()

macro(crosscompile_vtk proj toolchain_file)
  ExternalProject_Add(
    ${proj}
    SOURCE_DIR ${base}/Source/vtk
    DOWNLOAD_COMMAND ""
    DEPENDS vtk-host
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DBUILD_SHARED_LIBS:BOOL=OFF
      -DBUILD_TESTING:BOOL=OFF
      -DCMAKE_TOOLCHAIN_FILE:FILEPATH=${toolchain_dir}/${toolchain_file}
      -DVTKCompileTools_DIR:PATH=${build_prefix}/vtk-host
      ${module_defaults}
      -C ${toolchain_dir}/TryRunResults.cmake
  )
endmacro()

macro(download_curl)
  ExternalProject_Add(
    curl-download
    GIT_REPOSITORY git://github.com/patmarion/curl.git
    GIT_TAG origin/v7.24.0-with-cmake-patches
    SOURCE_DIR ${source_prefix}/curl
    CONFIGURE_COMMAND ""
    BUILD_COMMAND ""
    INSTALL_COMMAND ""
  )
endmacro()

macro(compile_curl tag)
  set(proj curl-${tag})
  ExternalProject_Add(
    ${proj}
    DOWNLOAD_COMMAND ""
    SOURCE_DIR ${source_prefix}/curl
    DEPENDS curl-download
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DBUILD_CURL_EXE:BOOL=OFF
      -DBUILD_CURL_TESTS:BOOL=OFF
      -DCURL_STATICLIB:BOOL=ON
  )
endmacro()

macro(crosscompile_curl proj toolchain_file)
  ExternalProject_Add(
    ${proj}
    DOWNLOAD_COMMAND ""
    SOURCE_DIR ${source_prefix}/curl
    DEPENDS curl-download
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DCURL_STATICLIB:BOOL=ON
      -DBUILD_CURL_EXE:BOOL=OFF
      -DBUILD_CURL_TESTS:BOOL=OFF
      -DCMAKE_TOOLCHAIN_FILE:FILEPATH=${toolchain_dir}/${toolchain_file}
      -C ${toolchain_dir}/curl-TryRunResults.cmake
  )
endmacro()

macro(crosscompile_ves proj tag toolchain_file)
  ExternalProject_Add(
    ${proj}
    SOURCE_DIR ${ves_src}
    DOWNLOAD_COMMAND ""
    DEPENDS vtk-${tag} eigen curl-${tag} libarchive-${tag}
    CMAKE_ARGS
      -DCMAKE_INSTALL_PREFIX:PATH=${install_prefix}/${proj}
      -DCMAKE_BUILD_TYPE:STRING=${build_type}
      -DCMAKE_TOOLCHAIN_FILE:FILEPATH=${toolchain_dir}/${toolchain_file}
      -DCMAKE_CXX_FLAGS:STRING=${VES_CXX_FLAGS}
      -DBUILD_SHARED_LIBS:BOOL=OFF
      -DVES_USE_VTK:BOOL=ON
      -DVTK_DIR:PATH=${build_prefix}/vtk-${tag}
      -DCURL_INCLUDE_DIR:PATH=${install_prefix}/curl-${tag}/include
      -DCURL_LIBRARY:PATH=${install_prefix}/curl-${tag}/lib/libcurl.a
      -DLibArchive_LIBRARY:PATH=${install_prefix}/libarchive-${tag}/lib/libarchive.a
      -DLibArchive_INCLUDE_DIR:PATH=${install_prefix}/libarchive-${tag}/include
      -DEIGEN_INCLUDE_DIR:PATH=${install_prefix}/eigen
      -DPYTHON_EXECUTABLE:FILEPATH=${PYTHON_EXECUTABLE}
  )

  force_build(${proj})
endmacro()


install_eigen()
download_curl()
compile_vtk(vtk-host)

fetch_libarchive()

if(VES_IOS_SUPERBUILD)
  foreach(target ios-simulator ios-device)
    crosscompile_vtk(vtk-${target} toolchain-${target}.cmake)
    crosscompile_curl(curl-${target} toolchain-${target}.cmake)
    crosscompile_libarchive(libarchive-${target} toolchain-${target}.cmake)
    crosscompile_ves(ves-${target} ${target} toolchain-${target}.cmake)
  endforeach()
endif()

if(VES_ANDROID_SUPERBUILD)
  crosscompile_vtk(vtk-android android.toolchain.cmake)
  crosscompile_curl(curl-android android.toolchain.cmake)
  crosscompile_libarchive(libarchive-android android.toolchain.cmake)
  crosscompile_ves(ves-android android android.toolchain.cmake)
endif()

if(VES_HOST_SUPERBUILD)
  compile_libarchive(host)
  compile_curl(host)
  compile_ves(ves-host)
endif()

set(ves_superbuild_enabled ON)


# CTestCustom.cmake needs to be placed at the top level build directory
configure_file(${CMAKE_SOURCE_DIR}/CMake/CTestCustom.cmake.in
               ${CMAKE_BINARY_DIR}/CTestCustom.cmake COPYONLY)
