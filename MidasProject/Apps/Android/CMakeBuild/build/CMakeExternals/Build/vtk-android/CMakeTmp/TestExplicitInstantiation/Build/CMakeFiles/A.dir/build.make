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
CMAKE_SOURCE_DIR = /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/Build

# Include any dependencies generated for this target.
include CMakeFiles/A.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/A.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/A.dir/flags.make

CMakeFiles/A.dir/A.cxx.o: CMakeFiles/A.dir/flags.make
CMakeFiles/A.dir/A.cxx.o: ../A.cxx
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/Build/CMakeFiles $(CMAKE_PROGRESS_1)
	@echo "Building CXX object CMakeFiles/A.dir/A.cxx.o"
	/home/celia/midasVESDemo/android-ndk-r6/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-g++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/A.dir/A.cxx.o -c /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/A.cxx

CMakeFiles/A.dir/A.cxx.i: cmake_force
	@echo "Preprocessing CXX source to CMakeFiles/A.dir/A.cxx.i"
	/home/celia/midasVESDemo/android-ndk-r6/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-g++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/A.cxx > CMakeFiles/A.dir/A.cxx.i

CMakeFiles/A.dir/A.cxx.s: cmake_force
	@echo "Compiling CXX source to assembly CMakeFiles/A.dir/A.cxx.s"
	/home/celia/midasVESDemo/android-ndk-r6/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-g++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/A.cxx -o CMakeFiles/A.dir/A.cxx.s

CMakeFiles/A.dir/A.cxx.o.requires:
.PHONY : CMakeFiles/A.dir/A.cxx.o.requires

CMakeFiles/A.dir/A.cxx.o.provides: CMakeFiles/A.dir/A.cxx.o.requires
	$(MAKE) -f CMakeFiles/A.dir/build.make CMakeFiles/A.dir/A.cxx.o.provides.build
.PHONY : CMakeFiles/A.dir/A.cxx.o.provides

CMakeFiles/A.dir/A.cxx.o.provides.build: CMakeFiles/A.dir/A.cxx.o

# Object files for target A
A_OBJECTS = \
"CMakeFiles/A.dir/A.cxx.o"

# External object files for target A
A_EXTERNAL_OBJECTS =

libA.a: CMakeFiles/A.dir/A.cxx.o
libA.a: CMakeFiles/A.dir/build.make
libA.a: CMakeFiles/A.dir/link.txt
	@echo "Linking CXX static library libA.a"
	$(CMAKE_COMMAND) -P CMakeFiles/A.dir/cmake_clean_target.cmake
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/A.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/A.dir/build: libA.a
.PHONY : CMakeFiles/A.dir/build

CMakeFiles/A.dir/requires: CMakeFiles/A.dir/A.cxx.o.requires
.PHONY : CMakeFiles/A.dir/requires

CMakeFiles/A.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/A.dir/cmake_clean.cmake
.PHONY : CMakeFiles/A.dir/clean

CMakeFiles/A.dir/depend:
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/Build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/Build /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/Build /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-android/CMakeTmp/TestExplicitInstantiation/Build/CMakeFiles/A.dir/DependInfo.cmake
.PHONY : CMakeFiles/A.dir/depend

