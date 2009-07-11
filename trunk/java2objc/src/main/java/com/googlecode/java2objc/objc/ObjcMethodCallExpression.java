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

import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.type.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * An Objective C expression involving a method call
 * 
 * @author Inderjeet Singh
 */
public final class ObjcMethodCallExpression extends ObjcExpression {

  private final String targetObjectName;
  private final String methodName;
  private final List<ObjcType> argTypes;
  private final List<ObjcExpression> args;
  private final int numParams;
  
  public ObjcMethodCallExpression(CompilationContext context, MethodCallExpr expr) {
    Expression scope = expr.getScope();
    if (scope == null || "this".equals(scope.toString())) {
      this.targetObjectName = "self";
    } else {
      this.targetObjectName = scope.toString();
    }
    methodName = expr.getName();
    argTypes = new LinkedList<ObjcType>();
    List<Type> typeArgs = expr.getTypeArgs();
    if (typeArgs != null) {
      for (Type argType : typeArgs) {
        argTypes.add(ObjcType.getTypeFor(argType));
      }
    }    
    args = new LinkedList<ObjcExpression>();
    List<Expression> actualArgs = expr.getArgs();
    if (actualArgs != null) {
      for (Expression arg : actualArgs) {
        args.add(new ObjcExpression(arg.toString()));
      }
    }
// TODO:    Preconditions.assertEquals(args.size(), argTypes.size());
    numParams = args.size();
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("[").append(targetObjectName).append(" ");
    writer.append(methodName);
    if (numParams > 0) {
      // Write first argument
      writer.append(" :").append(args.get(0));
    }
    // Write remaining params
    for (int i = 1; i < numParams; ++i) {
      // TODO(inder): write formal parameter names as well
      writer.append(" :").append(args.get(i));
    }
    writer.append("]");
  }
}
