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

import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.Statement;

/**
 * Objective C if-then-else statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcIfStatement extends ObjcStatement {
  private final ObjcExpression condition;
  private final ObjcStatement thenStmt;
  private final ObjcStatement elseStmt;
  
  public ObjcIfStatement(IfStmt n) {
    super(n);
    this.condition = new ObjcExpression(n.getCondition().toString());
    this.thenStmt = new ObjcStatement(n.getThenStmt());
    Statement elseStmtNode = n.getElseStmt();
    this.elseStmt = elseStmtNode == null ? null : new ObjcStatement(elseStmtNode);
  }

  public ObjcIfStatement(ObjcExpression condition, ObjcStatement thenStmt, ObjcStatement elseStmt) {
    super("");
    this.condition = condition;
    this.thenStmt = thenStmt;
    this.elseStmt = elseStmt;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("if (").append(condition).append(") ");
    boolean isIfStmtABlock = thenStmt instanceof ObjcStatementBlock;
    if (!isIfStmtABlock) {      
      writer.indent();
    }
    writer.append(thenStmt);
    if (!isIfStmtABlock) {
      writer.unIndent();
    }
    if (elseStmt != null) {
      if (isIfStmtABlock) {
        writer.append(" ");
      }
      writer.append("else");      
      boolean isElseABlock = elseStmt instanceof ObjcStatementBlock;
      boolean isElseAnIfStmt = elseStmt instanceof ObjcIfStatement;
      if (isElseABlock || isElseAnIfStmt) {
        writer.append(" ");
      } else {
        writer.indent();
      }
      writer.append(elseStmt);      
      if (isElseABlock || isElseAnIfStmt) {
        writer.unIndent();
      }
    }
    writer.endLine();
  }
}
