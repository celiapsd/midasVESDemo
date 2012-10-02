FILE(REMOVE_RECURSE
  "CMakeFiles/vtk-host"
  "CMakeFiles/vtk-host-complete"
  "CMakeExternals/Stamp/vtk-host/vtk-host-install"
  "CMakeExternals/Stamp/vtk-host/vtk-host-mkdir"
  "CMakeExternals/Stamp/vtk-host/vtk-host-download"
  "CMakeExternals/Stamp/vtk-host/vtk-host-update"
  "CMakeExternals/Stamp/vtk-host/vtk-host-patch"
  "CMakeExternals/Stamp/vtk-host/vtk-host-configure"
  "CMakeExternals/Stamp/vtk-host/vtk-host-build"
)

# Per-language clean rules from dependency scanning.
FOREACH(lang)
  INCLUDE(CMakeFiles/vtk-host.dir/cmake_clean_${lang}.cmake OPTIONAL)
ENDFOREACH(lang)
