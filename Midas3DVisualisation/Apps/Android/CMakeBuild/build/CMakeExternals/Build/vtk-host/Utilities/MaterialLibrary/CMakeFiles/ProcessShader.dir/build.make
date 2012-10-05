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
CMAKE_SOURCE_DIR = /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host

# Include any dependencies generated for this target.
include Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/depend.make

# Include the progress variables for this target.
include Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/progress.make

# Include the compile flags for this target's objects.
include Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/flags.make

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/flags.make
Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o: /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Utilities/MaterialLibrary/ProcessShader.cxx
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary && /usr/bin/c++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o -c /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Utilities/MaterialLibrary/ProcessShader.cxx

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ProcessShader.dir/ProcessShader.cxx.i"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Utilities/MaterialLibrary/ProcessShader.cxx > CMakeFiles/ProcessShader.dir/ProcessShader.cxx.i

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ProcessShader.dir/ProcessShader.cxx.s"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Utilities/MaterialLibrary/ProcessShader.cxx -o CMakeFiles/ProcessShader.dir/ProcessShader.cxx.s

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.requires:
.PHONY : Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.requires

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.provides: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.requires
	$(MAKE) -f Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/build.make Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.provides.build
.PHONY : Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.provides

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.provides.build: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o

# Object files for target ProcessShader
ProcessShader_OBJECTS = \
"CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o"

# External object files for target ProcessShader
ProcessShader_EXTERNAL_OBJECTS =

bin/ProcessShader: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o
bin/ProcessShader: lib/libvtksys-6.0.so.1
bin/ProcessShader: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/build.make
bin/ProcessShader: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking CXX executable ../../bin/ProcessShader"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/ProcessShader.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/build: bin/ProcessShader
.PHONY : Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/build

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/requires: Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/ProcessShader.cxx.o.requires
.PHONY : Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/requires

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/clean:
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary && $(CMAKE_COMMAND) -P CMakeFiles/ProcessShader.dir/cmake_clean.cmake
.PHONY : Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/clean

Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/depend:
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk/Utilities/MaterialLibrary /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host/Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : Utilities/MaterialLibrary/CMakeFiles/ProcessShader.dir/depend
