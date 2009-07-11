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

import java.util.List;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;

/**
 * Converts Java methods to their equivalent Objective C methods
 * 
 * @author Inderjeet Singh
 */
public class MethodConverter {
  private final CompilationContext context;

  public MethodConverter(CompilationContext context) {
    this.context = context;
  }

  public ObjcMethod to(MethodDeclaration method) {
    if (isToStringMethod(method)) {
      return new ObjcMethodToString(context, method);
    }
    return new ObjcMethod(context, method);
  }
  
  private boolean isToStringMethod(MethodDeclaration method) {
    List<Parameter> params = method.getParameters();
    return method.getName().equals("toString") && (params == null || params.size() == 0); 
  }

  public ObjcMethodInit to(ConstructorDeclaration constructor) {
    return new ObjcMethodInit(context, constructor);
  }
}
