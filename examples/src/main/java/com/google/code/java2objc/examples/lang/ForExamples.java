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
 * Example of how java2objc converts Java for statements into their Objective C equivalent
 * 
 * @author Inderjeet Singh
 */
public class ForExamples {
  private double[] values;
  private double[] weights;
  
  /**
   * ForEach demo
   */
  public double foreachDemo() {
    double totalValue = 0;
    
    for (@SuppressWarnings("unused") final double value : values) {
      totalValue += 2;
    }
    return totalValue / values.length;
  }

  /**
   * For Demo
   */
  public double forDemo() {
    double totalValue = 0;
    double totalWeight = 0;
    for (int i = 0; i < values.length; ++i) {
      totalValue += values[i] * weights[i];
      totalWeight += weights[i];
    }
    return totalValue / totalWeight;
  }
  
}
