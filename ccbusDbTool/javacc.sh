#!/bin/sh
declare doNotClean="$2"
if [ -z $doNotClean ]
then
    #rm out/src/*
    #echo "rm out/src/*";
    echo "";
fi
java -cp bin/lib/javacc.jar javacc -OUTPUT_DIRECTORY=src/main/java/ccbus/dbtool/parser $1
