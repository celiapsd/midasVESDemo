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
CMAKE_SOURCE_DIR = /home/celia/midasVESDemo/Midas3DVisualisation

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android

# Include any dependencies generated for this target.
include src/midas/CMakeFiles/midas.dir/depend.make

# Include the progress variables for this target.
include src/midas/CMakeFiles/midas.dir/progress.make

# Include the compile flags for this target's objects.
include src/midas/CMakeFiles/midas.dir/flags.make

src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o: src/midas/CMakeFiles/midas.dir/flags.make
src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o: ../../../../../../../src/midas/vesMidasApp.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas && /home/celia/midasVESDemo/android-ndk-r6/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-g++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/midas.dir/vesMidasApp.cpp.o -c /home/celia/midasVESDemo/Midas3DVisualisation/src/midas/vesMidasApp.cpp

src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/midas.dir/vesMidasApp.cpp.i"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas && /home/celia/midasVESDemo/android-ndk-r6/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-g++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/celia/midasVESDemo/Midas3DVisualisation/src/midas/vesMidasApp.cpp > CMakeFiles/midas.dir/vesMidasApp.cpp.i

src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/midas.dir/vesMidasApp.cpp.s"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas && /home/celia/midasVESDemo/android-ndk-r6/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-g++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/celia/midasVESDemo/Midas3DVisualisation/src/midas/vesMidasApp.cpp -o CMakeFiles/midas.dir/vesMidasApp.cpp.s

src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.requires:
.PHONY : src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.requires

src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.provides: src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.requires
	$(MAKE) -f src/midas/CMakeFiles/midas.dir/build.make src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.provides.build
.PHONY : src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.provides

src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.provides.build: src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o

# Object files for target midas
midas_OBJECTS = \
"CMakeFiles/midas.dir/vesMidasApp.cpp.o"

# External object files for target midas
midas_EXTERNAL_OBJECTS =

lib/libmidas.a: src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o
lib/libmidas.a: src/midas/CMakeFiles/midas.dir/build.make
lib/libmidas.a: src/midas/CMakeFiles/midas.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking CXX static library ../../lib/libmidas.a"
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas && $(CMAKE_COMMAND) -P CMakeFiles/midas.dir/cmake_clean_target.cmake
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/midas.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
src/midas/CMakeFiles/midas.dir/build: lib/libmidas.a
.PHONY : src/midas/CMakeFiles/midas.dir/build

src/midas/CMakeFiles/midas.dir/requires: src/midas/CMakeFiles/midas.dir/vesMidasApp.cpp.o.requires
.PHONY : src/midas/CMakeFiles/midas.dir/requires

src/midas/CMakeFiles/midas.dir/clean:
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas && $(CMAKE_COMMAND) -P CMakeFiles/midas.dir/cmake_clean.cmake
.PHONY : src/midas/CMakeFiles/midas.dir/clean

src/midas/CMakeFiles/midas.dir/depend:
	cd /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/celia/midasVESDemo/Midas3DVisualisation /home/celia/midasVESDemo/Midas3DVisualisation/src/midas /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas /home/celia/midasVESDemo/Midas3DVisualisation/Apps/Android/CMakeBuild/build/CMakeExternals/Build/ves-android/src/midas/CMakeFiles/midas.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : src/midas/CMakeFiles/midas.dir/depend

