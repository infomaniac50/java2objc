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
package com.googlecode.java2objc.objc;

import com.googlecode.java2objc.javatypes.JavaClass;

/**
 * Objective C BOOL type
 * 
 * @author Inderjeet Singh
 */
public final class NSBool extends ObjcTypeStandard {

  public static final String[] JAVA_TYPES = {"boolean", "Boolean", "java.lang.Boolean"};

  NSBool(CompilationContext context) {
    super(context, "BOOL", null, false, JavaClass.getJavaClassFor(Boolean.class));
  }
}
