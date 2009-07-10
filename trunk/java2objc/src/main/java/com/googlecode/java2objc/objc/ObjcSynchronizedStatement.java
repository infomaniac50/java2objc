package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.SynchronizedStmt;

public class ObjcSynchronizedStatement extends ObjcStatement {

  private ObjcExpression expr;
  private ObjcStatementBlock stmt;
  
  public ObjcSynchronizedStatement(SynchronizedStmt stmt) {
    this.expr = ExpressionConverter.to(stmt.getExpr());
    this.stmt = new ObjcStatementBlock(stmt.getBlock());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().appendToDo("handle synchronized " + expr);
    writer.startNewLine().append(stmt);
  }
}
