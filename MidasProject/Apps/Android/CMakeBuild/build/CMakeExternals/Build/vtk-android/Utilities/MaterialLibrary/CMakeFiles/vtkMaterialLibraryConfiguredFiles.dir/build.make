# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 2.8

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list

# Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# The program to use to edit the cache.
CMAKE_EDIT_COMMAND = /usr/bin/ccmake

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android

# Utility rule file for vtkMaterialLibraryConfiguredFiles.

# Include the progress variables for this target.
include Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/progress.make

Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles: Utilities/MaterialLibrary/vtkShaderCodeLibraryMacro.h
Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles: Utilities/MaterialLibrary/vtkMaterialLibraryMacro.h

vtkMaterialLibraryConfiguredFiles: Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles
vtkMaterialLibraryConfiguredFiles: Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/build.make
.PHONY : vtkMaterialLibraryConfiguredFiles

# Rule to build all files generated by this target.
Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/build: vtkMaterialLibraryConfiguredFiles
.PHONY : Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/build

Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/clean:
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Utilities/MaterialLibrary && $(CMAKE_COMMAND) -P CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/cmake_clean.cmake
.PHONY : Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/clean

Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/depend:
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Utilities/MaterialLibrary /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Utilities/MaterialLibrary /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : Utilities/MaterialLibrary/CMakeFiles/vtkMaterialLibraryConfiguredFiles.dir/depend

