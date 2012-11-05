#!/bin/bash

install=./CMakeExternals/Install

if [ ! -d $install ]; then
  echo "Install directory not found.  This script should be run from the top level build directory that contains CMakeExternals/Install."
  exit 1
fi

make_ves_framework ()
{
  ves_device_libs=`ls $install/ves-ios-device/lib/*.a $install/libarchive-ios-device/lib/*.a $install/curl-ios-device/lib/*.a`
  ves_sim_libs=`ls $install/ves-ios-simulator/lib/*.a $install/libarchive-ios-simulator/lib/*.a $install/curl-ios-simulator/lib/*.a`
  ves_headers=`find $install/ves-ios-device/include -name \\*.h`

  kiwi_framework=$install/ves-ios-device/kiwi.framework
  mkdir -p $kiwi_framework
  rm -rf $kiwi_framework/*
  mkdir $kiwi_framework/Headers
  cp $ves_headers $kiwi_framework/Headers/

  libtool -static -o $kiwi_framework/kiwi_device $ves_device_libs
  libtool -static -o $kiwi_framework/kiwi_sim $ves_sim_libs
  lipo -create $kiwi_framework/kiwi_device $kiwi_framework/kiwi_sim -output $kiwi_framework/kiwi
  rm $kiwi_framework/kiwi_*
}


######################################################

make_vtk_framework ()
{
  vtk_device_libs=`ls $install/vtk-ios-device/lib/*.a`
  vtk_sim_libs=`ls $install/vtk-ios-simulator/lib/*.a`

  vtk_framework=$install/vtk-ios-device/vtk.framework
  mkdir -p $vtk_framework
  rm -rf $vtk_framework/*
  mkdir -p $vtk_framework/Headers
  cp -r $install/vtk-ios-device/include/vtk-6.0/* $vtk_framework/Headers/

  libtool -static -o $vtk_framework/vtk_device $vtk_device_libs
  libtool -static -o $vtk_framework/vtk_sim $vtk_sim_libs
  lipo -create $vtk_framework/vtk_device $vtk_framework/vtk_sim -output $vtk_framework/vtk
  rm $vtk_framework/vtk_*
}

make_ves_framework
make_vtk_framework
