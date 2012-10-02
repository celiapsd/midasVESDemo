set(VES_INCLUDE_BASE_DIR "/home/celia/midasVESDemo/Midas3DVisualisation/src")

set(VES_RELATIVE_BUILD_INCLUDE_DIRS ves/.;shaders/.)
foreach(dir ${VES_RELATIVE_BUILD_INCLUDE_DIRS})
  list(APPEND VES_INCLUDE_DIRS "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/${dir}")
endforeach()

# add the eigen include directory that is used by ves
list(APPEND VES_INCLUDE_DIRS "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Install/eigen")
