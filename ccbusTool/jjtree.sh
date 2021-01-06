#!/bin/sh
declare classPath="$1"
declare package_suffix="$2"
if [ -z $classPath ]
then
    echo "Class path is missing";
    exit;
fi

if [ -z $package_suffix ]
then
    echo "Suffix file path missing";
    exit;
fi

declare baseName=`basename $classPath`;                                       
declare className=`echo ${baseName%%.*}`

echo "rm out/src/*";
rm out/src/*

echo "java -cp bin/lib/javacc.jar jjtree -OUTPUT_DIRECTORY=out/src $1"
java -cp bin/lib/javacc.jar jjtree -OUTPUT_DIRECTORY=src/main/java/ccbus/tool/parser/$package_suffix $1

./javacc.sh src/main/java/ccbus/tool/parser/$package_suffix/$className.jj $package_suffix
