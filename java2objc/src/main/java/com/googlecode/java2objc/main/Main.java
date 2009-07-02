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

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import com.googlecode.java2objc.objc.ObjcType;
import com.googlecode.java2objc.util.Preconditions;


public class Main {

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      printUsageAndExit();
    }
    for (String javaFileName : args) { 
      Preconditions.assertTrue(javaFileName.endsWith(".java"));
      FileInputStream in = new FileInputStream(javaFileName);
      String className = javaFileName.substring(0, javaFileName.indexOf('.'));
      parseJavaFileAndWriteObjcType(in, className);
    }
  }

  private static void printUsageAndExit() {
    System.err.println("Usage: java -jar java2objc.jar MyClass1.java MyClass2.java");
    System.exit(-1);
  }

  private static void parseJavaFileAndWriteObjcType(InputStream in, String className)
      throws FileNotFoundException, ParseException, IOException {
    PrintStream psHeader = new PrintStream(new FileOutputStream(className + ".h"));
    PrintStream psImpl = new PrintStream(new FileOutputStream(className + ".m"));
    try {
      CompilationUnit cu = JavaParser.parse(in);
      TranslateVisitor translateVisitor = new TranslateVisitor();
      GeneratorContext context = new GeneratorContext();
      translateVisitor.visit(cu, context);
      ObjcType currentType = context.getCurrentType();
      currentType.appendHeaderBody(psHeader);
      currentType.appendImplBody(psImpl);
    } finally {
      in.close();
      psHeader.close();
      psImpl.close();
    }
  }
}
