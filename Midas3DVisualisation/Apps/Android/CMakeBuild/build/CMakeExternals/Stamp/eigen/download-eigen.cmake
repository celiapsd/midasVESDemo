message(STATUS "downloading...
     src='http://www.vtk.org/files/support/eigen-3.1.0-alpha1.tar.gz'
     dst='/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Download/eigen/eigen-3.1.0-alpha1.tar.gz'
     timeout='none'")

file(DOWNLOAD
  "http://www.vtk.org/files/support/eigen-3.1.0-alpha1.tar.gz"
  "/home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Download/eigen/eigen-3.1.0-alpha1.tar.gz"
  SHOW_PROGRESS
  EXPECTED_MD5;c04dedf4ae97b055b6dd2aaa01daf5e9
  # no TIMEOUT
  STATUS status
  LOG log)

list(GET status 0 status_code)
list(GET status 1 status_string)

if(NOT status_code EQUAL 0)
  message(FATAL_ERROR "error: downloading 'http://www.vtk.org/files/support/eigen-3.1.0-alpha1.tar.gz' failed
  status_code: ${status_code}
  status_string: ${status_string}
  log: ${log}
")
endif()

message(STATUS "downloading... done")
