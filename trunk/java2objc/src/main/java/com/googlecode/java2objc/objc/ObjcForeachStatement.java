package com.googlecode.java2objc.objc;

import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.ForeachStmt;

public class ObjcForeachStatement extends ObjcStatement {

  private final String varName;
  private final ObjcType varType;
  private final ObjcExpression iterable;
  private final ObjcStatement body;
  public ObjcForeachStatement(ForeachStmt stmt) {
    VariableDeclarationExpr variable = stmt.getVariable();
    this.varName = variable.getVars().get(0).getId().getName();
    this.varType = ObjcType.getTypeFor(variable.getType());
    this.iterable = ExpressionConverter.to(stmt.getIterable());
    this.body = StatementConverter.to(stmt.getBody());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("for(").append(varType.getName()).append(" ");
    writer.append(varName).append(" : ");
    writer.append(iterable);
    writer.append(")");
    writer.append(body);
    writer.endLine();    
  }
}
