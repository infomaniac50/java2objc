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

import japa.parser.ast.expr.MethodCallExpr;

/**
 * Class to provide equivalent handling of Java String methods with NSString equivalents.
 * 
 * @author Inderjeet Singh
 */
public class ObjcMethodCallExpressionString extends ObjcMethodCallExpression {

  public ObjcMethodCallExpressionString(CompilationContext context, MethodCallExpr expr) {
    super(context, expr, "NSString", getMethodName(expr));
  }

  private static String getMethodName(MethodCallExpr expr) {
    String name = expr.getName();
    if (name.equals("valueOf")) {
      // TODO: Need to add the format string with parameter values 
      return "stringWithFormat";
    } else {
      return name;
    }
  }
}
