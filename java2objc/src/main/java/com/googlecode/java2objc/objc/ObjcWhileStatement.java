package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.WhileStmt;

public class ObjcWhileStatement extends ObjcStatement {

  private final ObjcExpression condition;
  private final ObjcStatement body;

  public ObjcWhileStatement(WhileStmt stmt) {
    this.condition = ExpressionConverter.to(stmt.getCondition());
    this.body = StatementConverter.to(stmt.getBody());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("while (").append(condition).append(") ");
    boolean isBlock = body instanceof ObjcStatementBlock;
    if (!isBlock) {
      writer.indent();
    }
    writer.append(body);
    if (!isBlock) {
      writer.unIndent();
    }
  }
}
