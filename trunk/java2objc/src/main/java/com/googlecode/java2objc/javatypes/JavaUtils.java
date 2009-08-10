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
package com.googlecode.java2objc.javatypes;

import com.googlecode.java2objc.util.Preconditions;

/**
 * Common utility methods related to Java
 *
 * @author Inderjeet Singh
 */
public final class JavaUtils {

  public static JavaClass getJavaType(String pkgName, String className) {
    Preconditions.assertNotNull(className);
    String fullyQualifiedClassName = pkgName == null ? className : String.format("%s.%s", pkgName, className);
    return getJavaType(fullyQualifiedClassName);
  }

  public static JavaClass getJavaType(String fullyQualifiedClassName) {
    Class<?> clazz = null;
    try {
      clazz = Class.forName(fullyQualifiedClassName);
    } catch (ClassNotFoundException e) {
      System.err.printf("Couldn't find class for %s\n", fullyQualifiedClassName);
    }
    if (clazz == null) {
      clazz = Object.class;
    }
    return JavaClass.getJavaClassFor(clazz);
  }

  public static String getPkgName(String fullyQualifiedClassName) {
    int index = fullyQualifiedClassName.lastIndexOf('.');
    return index == -1 ? null : fullyQualifiedClassName.substring(0, index);
  }

  public static String getClassName(String fullyQualifiedClassName) {
    int index = fullyQualifiedClassName.lastIndexOf('.');
    return index == -1 ? fullyQualifiedClassName : fullyQualifiedClassName.substring(index+1);
  }  
}
