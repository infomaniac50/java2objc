/**
 * 
 */
package com.googlecode.java2objc.main;

import com.googlecode.java2objc.util.Preconditions;

class Config {
  String outputDir = null;

  public void update(String arg) {
    Preconditions.assertTrue(arg.startsWith("--"));
    String[] parts = arg.split("=");
    String name = parts[0];
    String value = parts[1];
    if (name.equals("--outputdir")) {
      outputDir = value;
    }
  }

  public static String availableOptions() {
    return "--outputdir=/tmp";
  }    
}