declare className="$1";
if [ -z $className ]
then
    echo "Class name missing"
    exit;
fi

rm bin/*
echo "art_java_javac $className.java"
art_java_javac src/$className.java
echo "art_java_java $className"
art_java_java connect.parser.$className
