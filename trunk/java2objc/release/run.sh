#! /bin/bash

#setup classpath
CP="./java2objc.jar"
for i in `ls ./lib/*.jar`
do
  CP=${CP}:${i}
done

java -Xmx1g -cp "${CP}" com.googlecode.java2objc.main.Main --outputdir=output $*

exit 0