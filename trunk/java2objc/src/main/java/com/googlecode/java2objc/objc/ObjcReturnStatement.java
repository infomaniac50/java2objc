package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.ReturnStmt;

public class ObjcReturnStatement extends ObjcStatement {

  private final ObjcExpression expr;

  public ObjcReturnStatement(ReturnStmt stmt) {
    this.expr = ExpressionConverter.to(stmt.getExpr());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().append("return ").append(expr).endStatement();
  }
}
