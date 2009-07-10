package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.ForStmt;

import java.util.List;

public class ObjcForStatement extends ObjcStatement {
  
  private final List<ObjcExpression> init;
  private final ObjcExpression compare;
  private final List<ObjcExpression> update;
  private final ObjcStatement body;
  public ObjcForStatement(ForStmt stmt) {
    this.init = ExpressionConverter.to(stmt.getInit());
    this.compare = ExpressionConverter.to(stmt.getCompare());
    this.update = ExpressionConverter.to(stmt.getUpdate());
    this.body = StatementConverter.to(stmt.getBody());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("for(").append(init, ", ").append("; ");
    writer.append(compare).append("; ");
    writer.append(update, ", ").append(") ");
    writer.append(body);
    writer.endLine();
    
  }
}
