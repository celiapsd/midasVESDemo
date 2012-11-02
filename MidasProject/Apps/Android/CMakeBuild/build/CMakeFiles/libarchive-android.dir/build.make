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

# Utility rule file for libarchive-android.

# Include the progress variables for this target.
include CMakeFiles/libarchive-android.dir/progress.make

CMakeFiles/libarchive-android: CMakeFiles/libarchive-android-complete

CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-install
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-mkdir
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-download
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-update
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-patch
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-configure
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-build
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-install
CMakeFiles/libarchive-android-complete: CMakeExternals/Stamp/libarchive-android/libarchive-android-forcebuild
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Completed 'libarchive-android'"
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles/libarchive-android-complete
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-done

CMakeExternals/Stamp/libarchive-android/libarchive-android-install: CMakeExternals/Stamp/libarchive-android/libarchive-android-build
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_2)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing install step for 'libarchive-android'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android && $(MAKE) install
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-install

CMakeExternals/Stamp/libarchive-android/libarchive-android-mkdir:
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_3)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Creating directories for 'libarchive-android'"
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/libarchive
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/libarchive-android
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/tmp/libarchive-android
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android
	/usr/bin/cmake -E make_directory /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Download/libarchive-android
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-mkdir

CMakeExternals/Stamp/libarchive-android/libarchive-android-download: CMakeExternals/Stamp/libarchive-android/libarchive-android-mkdir
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_4)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "No download step for 'libarchive-android'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Download/libarchive-android && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-download

CMakeExternals/Stamp/libarchive-android/libarchive-android-update: CMakeExternals/Stamp/libarchive-android/libarchive-android-download
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_5)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "No update step for 'libarchive-android'"
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-update

CMakeExternals/Stamp/libarchive-android/libarchive-android-patch: CMakeExternals/Stamp/libarchive-android/libarchive-android-download
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_6)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "No patch step for 'libarchive-android'"
	/usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-patch

CMakeExternals/Stamp/libarchive-android/libarchive-android-configure: CMakeExternals/Stamp/libarchive-fetch/libarchive-fetch-done
CMakeExternals/Stamp/libarchive-android/libarchive-android-configure: CMakeExternals/tmp/libarchive-android/libarchive-android-cfgcmd.txt
CMakeExternals/Stamp/libarchive-android/libarchive-android-configure: CMakeExternals/Stamp/libarchive-android/libarchive-android-update
CMakeExternals/Stamp/libarchive-android/libarchive-android-configure: CMakeExternals/Stamp/libarchive-android/libarchive-android-patch
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_7)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing configure step for 'libarchive-android'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android && /usr/bin/cmake -DCMAKE_TOOLCHAIN_FILE:FILEPATH=/home/celia/midasVESDemo/MidasProject/CMake/toolchains/android.toolchain.cmake -DCMAKE_INSTALL_PREFIX:PATH=/home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Install/libarchive-android -DCMAKE_BUILD_TYPE:STRING=Release -DBUILD_SHARED_LIBS:BOOL=OFF -DENABLE_NETTLE:BOOL=OFF -DENABLE_OPENSSL:BOOL=OFF -DENABLE_TAR:BOOL=OFF -DENABLE_TAR_SHARED:BOOL=OFF -DENABLE_CPIO:BOOL=OFF -DENABLE_CPIO_SHARED:BOOL=OFF -DENABLE_XATTR:BOOL=OFF -DENABLE_ACL:BOOL=OFF -DENABLE_ICONV:BOOL=OFF -DENABLE_TEST:BOOL=OFF "-GUnix Makefiles" /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Source/libarchive
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-configure

CMakeExternals/Stamp/libarchive-android/libarchive-android-build: CMakeExternals/Stamp/libarchive-android/libarchive-android-configure
CMakeExternals/Stamp/libarchive-android/libarchive-android-build: CMakeExternals/Stamp/libarchive-android/libarchive-android-forcebuild
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_8)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing build step for 'libarchive-android'"
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android && $(MAKE)
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Build/libarchive-android && /usr/bin/cmake -E touch /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-build

CMakeExternals/Stamp/libarchive-android/libarchive-android-forcebuild: CMakeExternals/Stamp/libarchive-android/libarchive-android-configure
	$(CMAKE_COMMAND) -E cmake_progress_report /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles $(CMAKE_PROGRESS_9)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Performing forcebuild step for 'libarchive-android'"
	/usr/bin/cmake -E remove /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeExternals/Stamp/libarchive-android/libarchive-android-build

libarchive-android: CMakeFiles/libarchive-android
libarchive-android: CMakeFiles/libarchive-android-complete
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-install
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-mkdir
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-download
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-update
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-patch
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-configure
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-build
libarchive-android: CMakeExternals/Stamp/libarchive-android/libarchive-android-forcebuild
libarchive-android: CMakeFiles/libarchive-android.dir/build.make
.PHONY : libarchive-android

# Rule to build all files generated by this target.
CMakeFiles/libarchive-android.dir/build: libarchive-android
.PHONY : CMakeFiles/libarchive-android.dir/build

CMakeFiles/libarchive-android.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/libarchive-android.dir/cmake_clean.cmake
.PHONY : CMakeFiles/libarchive-android.dir/clean

CMakeFiles/libarchive-android.dir/depend:
	cd /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/celia/midasVESDemo/MidasProject /home/celia/midasVESDemo/MidasProject /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build /home/celia/midasVESDemo/MidasProject/Apps/Android/CMakeBuild/build/CMakeFiles/libarchive-android.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/libarchive-android.dir/depend

