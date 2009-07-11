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

import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;

import java.util.LinkedList;
import java.util.List;

/**
 * A case or default statement with-in a Objective C switch block.
 * 
 * @author Inderjeet Singh
 */
public final class ObjcSwitchEntryStatement extends ObjcStatement {
  private final ObjcExpression label;
  private final List<ObjcStatement> stmts;
  private boolean isDefault;

  public ObjcSwitchEntryStatement(CompilationContext context, SwitchEntryStmt stmt) {
    isDefault = stmt.getLabel() == null;
    this.label = isDefault ? null : context.getExpressionConverter().to(stmt.getLabel());
    this.stmts = new LinkedList<ObjcStatement>();
    for (Statement caseStmt : stmt.getStmts()) {
      this.stmts.add(context.getStatementConverter().to(caseStmt));
    }
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    if (isDefault) {
      writer.append("default");
    } else {
      writer.append("case ").append(label);
    }
    writer.append(":");
    writer.endLine();
    writer.indent();
    for (ObjcStatement stmt : stmts) {
      writer.append(stmt);
    }
    writer.unIndent();
  }
}
