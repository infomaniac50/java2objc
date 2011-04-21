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
 * Example of how java2objc converts Java if-then-else statements into their Objective C equivalent
 * 
 * @author Inderjeet Singh
 */
public class IfExamples {

  public int ifWithBlock() {
    int value = 3;
    if (value > 3) { 
      return 2;
    }
    if (value > 2) { 
      return 2;
    } else if (value > 1) {
      return 1;
    } else { 
      return 0;
    }
  }
  
  public int ifWithoutBlock() {
    int value = 3;
    if (value > 3) 
      return 2;
    if (value > 2) 
      return 2;
    else if (value > 1)
      return 1;
    else 
      return 0;    
  }
  
  public int ifWithMixedBlocks() {
    int value = 3;
    if (value > 2) 
      return 2;
    else if (value > 1) {
      return 1;
    } else 
      return 0;        
  }
}
