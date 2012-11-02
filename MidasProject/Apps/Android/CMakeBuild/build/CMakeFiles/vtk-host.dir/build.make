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
CMAKE_SOURCE_DIR = /home/celia/midasVESDemo/MidasProject

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build

# Utility rule file for vtk-host.

# Include the progress variables for this target.
include CMakeFiles/vtk-host.dir/progress.make

CMakeFiles/vtk-host: CMakeFiles/vtk-host-complete

CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-install
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-mkdir
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-download
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-update
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-patch
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-configure
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-build
CMakeFiles/vtk-host-complete: CMakeExternals/Stamp/vtk-host/vtk-host-install
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Completed 'vtk-host'"
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles/vtk-host-complete
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-done

CMakeExternals/Stamp/vtk-host/vtk-host-install: CMakeExternals/Stamp/vtk-host/vtk-host-build
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_2)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "No install step for 'vtk-host'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-install

CMakeExternals/Stamp/vtk-host/vtk-host-mkdir:
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_3)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Creating directories for 'vtk-host'"
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/vtk-host
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/tmp/vtk-host
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Download/vtk-host
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-mkdir

CMakeExternals/Stamp/vtk-host/vtk-host-download: CMakeExternals/Stamp/vtk-host/vtk-host-gitinfo.txt
CMakeExternals/Stamp/vtk-host/vtk-host-download: CMakeExternals/Stamp/vtk-host/vtk-host-mkdir
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_4)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing download step (git clone) for 'vtk-host'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source && /usr/bin/cmake -P /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/tmp/vtk-host/vtk-host-gitclone.cmake
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-download

CMakeExternals/Stamp/vtk-host/vtk-host-update: CMakeExternals/Stamp/vtk-host/vtk-host-download
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_5)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing update step (git fetch) for 'vtk-host'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk && /usr/bin/git fetch
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk && /usr/bin/git checkout 96af1b5
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk && /usr/bin/git submodule update --recursive

CMakeExternals/Stamp/vtk-host/vtk-host-patch: CMakeExternals/Stamp/vtk-host/vtk-host-download
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_6)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "No patch step for 'vtk-host'"
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-patch

CMakeExternals/Stamp/vtk-host/vtk-host-configure: CMakeExternals/tmp/vtk-host/vtk-host-cfgcmd.txt
CMakeExternals/Stamp/vtk-host/vtk-host-configure: CMakeExternals/Stamp/vtk-host/vtk-host-update
CMakeExternals/Stamp/vtk-host/vtk-host-configure: CMakeExternals/Stamp/vtk-host/vtk-host-patch
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_7)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing configure step for 'vtk-host'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host && /usr/bin/cmake -DCMAKE_INSTALL_PREFIX:PATH=/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/vtk-host -DCMAKE_BUILD_TYPE:STRING=Release -DBUILD_SHARED_LIBS:BOOL=ON -DBUILD_TESTING:BOOL=OFF -DVTK_Group_StandAlone:BOOL=OFF -DVTK_Group_Rendering:BOOL=OFF -DModule_vtkFiltersCore:BOOL=ON -DModule_vtkFiltersModeling:BOOL=ON -DModule_vtkFiltersSources:BOOL=ON -DModule_vtkFiltersGeometry:BOOL=ON -DModule_vtkIOGeometry:BOOL=ON -DModule_vtkIOLegacy:BOOL=ON -DModule_vtkIOXML:BOOL=ON -DModule_vtkIOImage:BOOL=ON -DModule_vtkIOPLY:BOOL=ON -DModule_vtkIOInfovis:BOOL=ON -DModule_vtkImagingCore:BOOL=ON -DModule_vtkParallelCore:BOOL=ON -DModule_vtkRenderingCore:BOOL=ON -DModule_vtkRenderingFreeType:BOOL=ON "-GUnix Makefiles" /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/vtk
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-configure

CMakeExternals/Stamp/vtk-host/vtk-host-build: CMakeExternals/Stamp/vtk-host/vtk-host-configure
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_8)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing build step for 'vtk-host'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host && make vtkCompileTools
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/vtk-host && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/vtk-host/vtk-host-build

vtk-host: CMakeFiles/vtk-host
vtk-host: CMakeFiles/vtk-host-complete
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-install
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-mkdir
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-download
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-update
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-patch
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-configure
vtk-host: CMakeExternals/Stamp/vtk-host/vtk-host-build
vtk-host: CMakeFiles/vtk-host.dir/build.make
.PHONY : vtk-host

# Rule to build all files generated by this target.
CMakeFiles/vtk-host.dir/build: vtk-host
.PHONY : CMakeFiles/vtk-host.dir/build

CMakeFiles/vtk-host.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/vtk-host.dir/cmake_clean.cmake
.PHONY : CMakeFiles/vtk-host.dir/clean

CMakeFiles/vtk-host.dir/depend:
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/celia/midasVESDemo/MidasProject /home/celia/midasVESDemo/MidasProject /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles/vtk-host.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/vtk-host.dir/depend

