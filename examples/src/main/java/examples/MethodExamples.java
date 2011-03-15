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
package examples;

/**
 * Examples of how java2objc converts Java static and object method invocations into their 
 * Objective C equivalents
 * 
 * @author Inderjeet Singh
 */
public class MethodExamples {

  public static int staticMethod(int a, int b) {
    return a+b;
  }

  public static int callAStaticMethod(int a, int b) {
    return staticMethod(a, b);
  }

  public String method1(String value) {
    return "Value is: " + value;
  }
  
  public String callAMethodWithThis() {
    return this.method1("foo");
  }
  
  public String callAMethod() {
    return method1("foo");
  }
  
  @Override
  public String toString() {
    return "I am of type MethodExamples";
  }
}
