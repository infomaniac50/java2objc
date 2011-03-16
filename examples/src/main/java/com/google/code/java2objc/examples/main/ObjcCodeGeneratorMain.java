package com.google.code.java2objc.examples.main;

import java.util.Arrays;
import java.util.Collection;

import com.googlecode.java2objc.main.Config;
import com.googlecode.java2objc.main.Main;

public class ObjcCodeGeneratorMain {

  public static void main(String[] args) throws Exception {
    Config config = new Config();
    config.update("--outputdir=target/generated");
    String path = "src/main/java/" + "com/google/code/java2objc/examples/";
    Collection<String> javaFiles = Arrays.asList(path + "lang/*.java", path + "inheritance/*.java");
    Main main = new Main(config, javaFiles);
    main.execute();
  }
}
