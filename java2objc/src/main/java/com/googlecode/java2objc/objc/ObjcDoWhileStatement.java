package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.DoStmt;

public class ObjcDoWhileStatement extends ObjcStatement {

  private final ObjcStatement body;
  private final ObjcExpression condition;

  public ObjcDoWhileStatement(DoStmt stmt) {
    body = StatementConverter.to(stmt.getBody());
    condition = ExpressionConverter.to(stmt.getCondition());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().append("do ");
    writer.append(body);
    writer.append(" while (").append(condition).append(");").endLine();
  }
}
