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
 */package com.googlecode.java2objc.objc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.googlecode.java2objc.javatypes.JavaClass;
import com.googlecode.java2objc.javatypes.JavaUtils;

/**
 * Unit test for {@link JavaUtils}
 * 
 * @author Inderjeet Singh
 */
public class JavaUtilsTest {

  @Test
  public void testGetJavaType() {
    String className = JavaUtils.class.getName();
    JavaClass javaType = JavaUtils.getJavaType(className);
    assertEquals(JavaUtils.class, javaType.getJavaClass());
  }

  @Test
  public void testGetJavaTypeForPkgAndClassName() {
    String pkgName = JavaUtils.class.getPackage().getName();
    String className = JavaUtils.class.getSimpleName();
    JavaClass javaType = JavaUtils.getJavaType(pkgName, className);
    assertEquals(JavaUtils.class, javaType.getJavaClass());
  }

  @Test
  public void testGetPkgName() {
    String expectedPkgName = "com.googlecode.java2objc";
    String name = expectedPkgName + ".Main";
    String pkgName = JavaUtils.getPkgName(name);
    assertEquals(expectedPkgName, pkgName);
  }

  @Test
  public void testGetPkgNameInDefaultPackage() {
    String name = "Main";
    String pkgName = JavaUtils.getPkgName(name);
    assertNull(pkgName);
  }

  @Test
  public void testGetClassName() {
    String expectedClassName = "Main";
    String name = "com.googlecode.java2objc." + expectedClassName;
    String className = JavaUtils.getClassName(name);
    assertEquals(expectedClassName, className);
  }

  @Test
  public void testGetClassNameInDefaultPackage() {
    String expectedClassName = "Main";
    String className = JavaUtils.getClassName(expectedClassName);
    assertEquals(expectedClassName, className);
  }
}
