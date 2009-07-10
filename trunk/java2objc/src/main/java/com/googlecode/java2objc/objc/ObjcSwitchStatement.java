package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;

import java.util.LinkedList;
import java.util.List;

public class ObjcSwitchStatement extends ObjcStatement {

  private final ObjcExpression selector;
  private final List<ObjcSwitchEntryStatement> stmts;

  public ObjcSwitchStatement(SwitchStmt stmt) {
    selector = ExpressionConverter.to(stmt.getSelector());
    stmts = new LinkedList<ObjcSwitchEntryStatement>();
    for (SwitchEntryStmt entry : stmt.getEntries()) {
      stmts.add(new ObjcSwitchEntryStatement(entry));
    }
  }  
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine();
    writer.append("switch (").append(selector).append(") {");
    writer.endLine();
    for (ObjcSwitchEntryStatement stmt : stmts) {
      writer.append(stmt);
    }
    writer.appendLine("}");    
  }
}
