#!/bin/bash

source tools.sh

$ADB logcat ChooseFirstActivity:D KiwiViewer:D *:s
