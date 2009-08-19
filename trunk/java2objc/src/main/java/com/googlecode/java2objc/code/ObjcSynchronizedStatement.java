package com.googlecode.java2objc.code;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

import japa.parser.ast.stmt.SynchronizedStmt;

/**
 * Objective C equivalent of a Java synchronized block
 * 
 * @author Inderjeet Singh
 */
public final class ObjcSynchronizedStatement extends ObjcStatement {

  private ObjcExpression expr;
  private ObjcStatementBlock stmt;
  
  public ObjcSynchronizedStatement(CompilationContext context, SynchronizedStmt stmt) {
    this.expr = context.getExpressionConverter().to(stmt.getExpr());
    this.stmt = new ObjcStatementBlock(context, stmt.getBlock());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().appendToDo("handle synchronized " + expr);
    writer.startNewLine().append(stmt);
  }
}
