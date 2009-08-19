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

import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C switch statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcSwitchStatement extends ObjcStatement {

  private final ObjcExpression selector;
  private final List<ObjcSwitchEntryStatement> stmts;

  public ObjcSwitchStatement(CompilationContext context, SwitchStmt stmt) {
    selector = context.getExpressionConverter().to(stmt.getSelector());
    stmts = new LinkedList<ObjcSwitchEntryStatement>();
    for (SwitchEntryStmt entry : stmt.getEntries()) {
      stmts.add(new ObjcSwitchEntryStatement(context, entry));
    }
  }  
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("switch (").append(selector).append(") {");
    writer.endLine();
    for (ObjcSwitchEntryStatement stmt : stmts) {
      writer.append(stmt);
    }
    writer.appendLine("}");    
  }
}
