FILE(REMOVE_RECURSE
  "CMakeFiles/eigen"
  "CMakeFiles/eigen-complete"
  "CMakeExternals/Stamp/eigen/eigen-install"
  "CMakeExternals/Stamp/eigen/eigen-mkdir"
  "CMakeExternals/Stamp/eigen/eigen-download"
  "CMakeExternals/Stamp/eigen/eigen-update"
  "CMakeExternals/Stamp/eigen/eigen-patch"
  "CMakeExternals/Stamp/eigen/eigen-configure"
  "CMakeExternals/Stamp/eigen/eigen-build"
)

# Per-language clean rules from dependency scanning.
FOREACH(lang)
  INCLUDE(CMakeFiles/eigen.dir/cmake_clean_${lang}.cmake OPTIONAL)
ENDFOREACH(lang)
