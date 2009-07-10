/*
 * Copyright (C) 2009 Inderjeet Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.java2objc.main;

import com.googlecode.java2objc.util.Preconditions;

/**
 * Class that holds configuration of the command-line input parameters to the program
 * 
 * @author Inderjeet Singh
 */
public final class Config {
  private String outputDir = null;

  public String getOutputDir() {
    return outputDir;
  }

  void update(String arg) {
    Preconditions.assertTrue(arg.startsWith("--"));
    String[] parts = arg.split("=");
    String name = parts[0];
    String value = parts[1];
    if (name.equals("--outputdir")) {
      outputDir = value;
    }
  }

  static String availableOptions() {
    return "--outputdir=/tmp";
  }    
}