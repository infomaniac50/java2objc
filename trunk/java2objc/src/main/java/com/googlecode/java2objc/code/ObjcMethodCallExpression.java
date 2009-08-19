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
package com.googlecode.java2objc.code;

import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.type.Type;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcTypeRepository;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C expression involving a method call
 * 
 * @author Inderjeet Singh
 */
public class ObjcMethodCallExpression extends ObjcExpression {

  protected final String target;
  protected final String methodName;
  protected final List<ObjcType> argTypes;
  protected final List<ObjcExpression> args;
  protected final int numParams;
  
  public ObjcMethodCallExpression(CompilationContext context, MethodCallExpr expr) {
    this(context, expr, getScope(context, expr), expr.getName());
  }

  protected ObjcMethodCallExpression(CompilationContext context, MethodCallExpr expr, 
      String scope, String methodName) {
    this.methodName = methodName;
    this.target = scope;
    argTypes = new LinkedList<ObjcType>();
    List<Type> typeArgs = expr.getTypeArgs();
    ObjcTypeRepository typeRepo = context.getTypeRepo();
    if (typeArgs != null) {
      for (Type argType : typeArgs) {
        String argPkgName = null; // TODO(inder): figure out the correct package name for the arg type
        argTypes.add(typeRepo.getTypeFor(argPkgName, argType));
      }
    }    
    args = new LinkedList<ObjcExpression>();
    List<Expression> actualArgs = expr.getArgs();
    if (actualArgs != null) {
      for (Expression arg : actualArgs) {
        args.add(new ObjcExpression(arg.toString()));
      }
    }
    //  TODO:    Preconditions.assertEquals(args.size(), argTypes.size());
    numParams = args.size();
  }

  private static String getScope(CompilationContext context, MethodCallExpr expr) {
    Expression scope = expr.getScope();
    String methodName = expr.getName();
    if (scope == null) {
      ObjcType enclosingType = context.getCurrentType();
      ObjcMethod invokedMethod = enclosingType.getMethodWithName(methodName);
      if (invokedMethod == null || invokedMethod.isStatic()) {
        return enclosingType.getName();
      } else {
        return "self";
      }
    } else if ("this".equals(scope.toString())) {
      return "self";
    } else {
      return scope.toString();
    }
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("[").append(target).append(" ");
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
