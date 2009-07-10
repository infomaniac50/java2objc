package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;

import java.util.LinkedList;
import java.util.List;

public class ObjcSwitchEntryStatement extends ObjcStatement {
  private final ObjcExpression label;
  private final List<ObjcStatement> stmts;
  private boolean isDefault;
  public ObjcSwitchEntryStatement(SwitchEntryStmt stmt) {
    isDefault = stmt.getLabel() == null;
    this.label = isDefault ? null : ExpressionConverter.to(stmt.getLabel());
    this.stmts = new LinkedList<ObjcStatement>();
    for (Statement caseStmt : stmt.getStmts()) {
      this.stmts.add(StatementConverter.to(caseStmt));
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
