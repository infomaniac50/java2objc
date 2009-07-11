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

import japa.parser.ast.stmt.ForStmt;

import java.util.List;

/**
 * Objective C For statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcForStatement extends ObjcStatement {
  
  private final List<ObjcExpression> init;
  private final ObjcExpression compare;
  private final List<ObjcExpression> update;
  private final ObjcStatement body;

  public ObjcForStatement(CompilationContext context, ForStmt stmt) {
    this.init = context.getExpressionConverter().to(stmt.getInit());
    this.compare = context.getExpressionConverter().to(stmt.getCompare());
    this.update = context.getExpressionConverter().to(stmt.getUpdate());
    this.body = context.getStatementConverter().to(stmt.getBody());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("for(").append(init, ", ").append("; ");
    writer.append(compare).append("; ");
    writer.append(update, ", ").append(") ");
    writer.append(body);
    writer.endLine();
    
  }
}
