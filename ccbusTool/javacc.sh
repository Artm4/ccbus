#!/bin/sh
declare input_grammer="$1"
declare package_suffix="$2"

if [ -z $package_suffix ]
then
    echo "Suffix file path missing";
    exit;
fi


java -cp bin/lib/javacc.jar javacc -OUTPUT_DIRECTORY=src/main/java/ccbus/tool/parser/$package_suffix $input_grammer
