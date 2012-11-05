ANT=`which ant`
ADB=/home/celia/android-sdks/platform-tools/adb
ANDROID=/home/celia/android-sdks/tools/android
CMAKE=`which cmake`


app_dir=$(cd $(dirname $0) && pwd)
source_dir=$app_dir/../../..
build_dir=$app_dir

cmakeexternals=$app_dir/../CMakeBuild/build/CMakeExternals

set -x
