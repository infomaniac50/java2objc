package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.LabeledStmt;

public class ObjcLabeledStmt extends ObjcStatement {

  private final String label;
  private final ObjcStatement stmt;

  public ObjcLabeledStmt(LabeledStmt stmt) {
    this.label = stmt.getLabel();
    this.stmt = StatementConverter.to(stmt.getStmt());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().append(label).append(": ").append(stmt);
  }
}
