package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.ExpressionStmt;

public class ObjcExpressionStatement extends ObjcStatement {

  private final ObjcExpression expr;

  public ObjcExpressionStatement(ExpressionStmt stmt) {
    this.expr = ExpressionConverter.to(stmt.getExpression());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().append(expr).append(";").endLine();
  }
}
