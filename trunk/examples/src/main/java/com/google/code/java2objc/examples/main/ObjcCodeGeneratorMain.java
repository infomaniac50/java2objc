/*
 * Copyright (C) 2011 Inderjeet Singh
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
package com.google.code.java2objc.examples.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.googlecode.java2objc.main.Config;
import com.googlecode.java2objc.main.Main;

public class ObjcCodeGeneratorMain {

  public static void main(String[] args) throws Exception {
    Config config = new Config();
    config.update("--outputdir=target/generated");
    String path = "src/main/java/" + "com/google/code/java2objc/examples/";
    Collection<String> javaFiles = Lists.newArrayList();
    FilenameFilter filterForJavaFiles = PatternBasedFilenameFilter.getFilterForJavaFiles();
    addFilesInDirectory(javaFiles, path + "lang/", filterForJavaFiles);
    addFilesInDirectory(javaFiles, path + "inheritance/", filterForJavaFiles);
    Main main = new Main(config, javaFiles);
    main.execute();
  }

  public static void addFilesInDirectory(Collection<String> files,
      String pathToDir, FilenameFilter filter) throws IOException {
    File dir = new File(pathToDir);
    Preconditions.checkArgument(dir.isDirectory());
    for (String file : dir.list(filter)) {
      files.add(pathToDir + file);
    }
  }
}
