
find_package(VTK REQUIRED)
include(${VTK_USE_FILE})

set(sources
  vesMidasApp.cpp
  )

set(deps
  kiwi
  ves
  )

# Configure version file
configure_file(${CMAKE_CURRENT_SOURCE_DIR}/vesMidasVersion.h.in
  ${CMAKE_CURRENT_BINARY_DIR}/vesMidasVersion.h @ONLY)

ves_add_library(midas "${sources}" "${deps}")

# Add version info to the target. Currently using a single global version string.
set_target_properties(midas PROPERTIES SOVERSION ${VES_VERSION_STR}
                      VERSION ${VES_VERSION_STR})

if(BUILD_TESTING)
  add_subdirectory(Testing)
endif()
