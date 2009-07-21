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

import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.ForeachStmt;

/**
 * Objective C for-each statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcForeachStatement extends ObjcStatement {

  private final String varName;
  private final ObjcType varType;
  private final ObjcExpression iterable;
  private final ObjcStatement body;
  public ObjcForeachStatement(CompilationContext context, ForeachStmt stmt) {
    VariableDeclarationExpr variable = stmt.getVariable();
    this.varName = variable.getVars().get(0).getId().getName();
    String pkgName = null; // TODO(inder): Get the real package name for the type
    this.varType = ObjcType.getTypeFor(pkgName, variable.getType());
    this.iterable = context.getExpressionConverter().to(stmt.getIterable());
    this.body = context.getStatementConverter().to(stmt.getBody());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("for(").append(varType.getName()).append(" ");
    writer.append(varName).append(" in ");
    writer.append(iterable);
    writer.append(")");
    writer.append(body);
    writer.endLine();    
  }
}
