set CLASSPATH=java2objc.jar

setLocal EnableDelayedExpansion

for /R ./lib %%a in (*.jar) do (
   echo "setting classpath %%a"
   set CLASSPATH=!CLASSPATH!;%%a
 )


java com.googlecode.java2objc.main.Main --outputdir=output %*