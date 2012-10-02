#!/bin/bash

source tools.sh

$ADB -d install -r $build_dir/bin/MidasFilesVisualisation-debug.apk
$ADB shell am start -a android.intent.action.MAIN -n com.kitware.midasfilesvisualisation/.ChooseFirstAction
