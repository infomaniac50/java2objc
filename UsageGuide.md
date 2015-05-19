# Introduction #

At the moment, there is no standalone java2objc tool that you can run to convert Java code to Objective C. This is because this project is still in progress and is meant for those willing to work with java2objc source-code.

# Details #

1. Creating a java2objc Eclipse project:
Note: java2objc uses maven2.
  * Checkout out all java2objc code ` svn checkout http://java2objc.googlecode.com/svn/trunk/ java2objc-read-only `
  * cd ` java2objc-read-only/java2objc `
  * run ` mvn eclipse:configure-workspace eclipse:eclipse `
  * Start Eclipse and select workspace ` java2objc-read-only/eclipse-ws `

2. Steps to produce Objective C code using java2objc: In the instructions below, I will show how to generate Objective C code for the example Java class ` examples/examples/AssortedExamples.java `
  * First create an eclipse project as describe above in 1
  * Select Run | Run Configuration
  * Choose Main class ` com.googlecode.java2objc.main.Main `
  * Under Arguments, Program Arguments add ` --outputdir=target examples/examples/AssortedExamples.java `
  * Run. This will create ` target/AssortedExamples.h ` and ` target/AssortedExamples.m ` files that contain the equivalent Objective C for the Java file ` AssortedExamples.java `