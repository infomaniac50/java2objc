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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link FilenamePattern} that matches file names based on a regular expression
 *
 * @author Inderjeet Singh
 */
public final class PatternBasedFilenameFilter implements FilenameFilter {
  private final Pattern pattern;

  public PatternBasedFilenameFilter(String fileNamePattern) {
    this.pattern = Pattern.compile(fileNamePattern);
  }

  @Override
  public boolean accept(File dir, String name) {
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }

  /**
   * Returns a {@link FilenameFilter} for matching allowed Java source file names
   *
   * This ignores the valid Unicode characters for now.
   * A Java file name can not have the following characters: ~!@%^&*()-+=[]{}\\|:;'\",.<>?/
   */
  public static FilenameFilter getFilterForJavaFiles() {
    return new PatternBasedFilenameFilter("([a-zA-Z\\$\\_]){1}[a-zA-Z0-9\\$\\_]*\\.java");
  }
}
