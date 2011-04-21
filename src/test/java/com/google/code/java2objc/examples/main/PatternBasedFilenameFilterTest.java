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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PatternBasedFilenameFilterTest {
  private FilenameFilter filter;
  private File baseDir;

  @Before
  public void setUp() {
    this.filter = PatternBasedFilenameFilter.getFilterForJavaFiles();
    this.baseDir = new File(".");
  }

  @Test
  public void testJavaFile() {
    Assert.assertTrue(filter.accept(baseDir, "Base.java"));
    Assert.assertTrue(filter.accept(baseDir, "$$Base$$Sub.java"));
    Assert.assertTrue(filter.accept(baseDir, "_Base.java"));
    Assert.assertTrue(filter.accept(baseDir, "Base0_.java"));
  }

  @Test
  public void testNonJavaFile() {
    Assert.assertFalse(filter.accept(baseDir, "Base.xml"));
  }

  @Test
  public void testFileNameEndsInJava() {
    Assert.assertFalse(filter.accept(baseDir, "Basejava"));
  }

  @Test
  public void testFileNameStartsWithDigit() {
    Assert.assertFalse(filter.accept(baseDir, "0Base.java"));
  }

  @Test
  public void testFileNameHasOperators() {
    Assert.assertFalse(filter.accept(baseDir, "Base-Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "-BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base+Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "+BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base/Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "/BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base\\Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "\\BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base*Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "*BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base&Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "&BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base^Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "^BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base%Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "%BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base@Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "@BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base!Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "!BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base~Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "~BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base`Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "`BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base'Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "'BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base\"Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "\"BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base(Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "(BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base)Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, ")BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base[Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "[BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base]Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "]BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base{Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "{BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base}Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "}BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base|Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "|BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base:Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, ":BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base;Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, ";BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base>Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, ">BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base<Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "<BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base,Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, ",BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base.Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, ".BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base?Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "?BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base=Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "=BaseSub.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base#Sub.java"));
    Assert.assertFalse(filter.accept(baseDir, "#BaseSub.java"));
  }

  @Test
  public void testFileNameHasWhiteSpace() {
    Assert.assertFalse(filter.accept(baseDir, " Base.java"));
    Assert.assertFalse(filter.accept(baseDir, "\tBase.java"));
    Assert.assertFalse(filter.accept(baseDir, "\rBase.java"));
    Assert.assertFalse(filter.accept(baseDir, "\nBase.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base class.java"));
    Assert.assertFalse(filter.accept(baseDir, "Base.java\t"));
  }
}
