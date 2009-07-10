package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.EmptyStmt;

public class ObjcEmptyStatement extends ObjcStatement {

  public ObjcEmptyStatement(EmptyStmt stmt) {
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    // do nothing
  }
}
