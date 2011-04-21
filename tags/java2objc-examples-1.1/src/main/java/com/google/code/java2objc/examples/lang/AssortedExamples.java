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
 * Demo of assorted language constructs
 * 
 * @author Inderjeet Singh
 */
public class AssortedExamples {

  public int labeledStatement() {
    int value = 4;

    outer:
    for (int i = 0; i < value; ++i) {
      middle:
      for (int j = 0; j < value; ++j) {
        for (int k = 0; k < value; ++k) {
          if (i*j*k == 27) {
            break outer;
          } if (i*j*k == 33) {
            continue middle;
          }
        }
      }
    }
    return value;
  }  
}
