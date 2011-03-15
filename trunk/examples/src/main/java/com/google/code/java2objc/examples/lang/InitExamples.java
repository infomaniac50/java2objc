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
package com.google.code.java2objc.examples.lang;

/**
 * Examples of how java2objc converts Java constructors into Objective C init methods
 * 
 * @author Inderjeet Singh
 */
@SuppressWarnings("unused")
public class InitExamples {
  private final int a;
  private final String b;
  private final String c;
  private final double d;
  
  public InitExamples(int a, String b) {
    this(a, b, "c", 2.0);
  }

  public InitExamples(int a, String b, String c, double d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
}
