package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.WhileStmt;

public final class StatementConverter {

  public static ObjcStatement to(Statement stmt) {
    if (stmt instanceof IfStmt) {
      return new ObjcIfStatement((IfStmt)stmt);
    } else if (stmt instanceof BlockStmt) {
      return new ObjcStatementBlock((BlockStmt)stmt);
    } else if (stmt instanceof ForStmt) {
      return new ObjcForStatement((ForStmt)stmt);
    } else if (stmt instanceof ForeachStmt) {
      return new ObjcForeachStatement((ForeachStmt)stmt);
    } else if (stmt instanceof SwitchStmt) {
      return new ObjcSwitchStatement((SwitchStmt)stmt);
    } else if (stmt instanceof DoStmt) {
      return new ObjcDoWhileStatement((DoStmt)stmt);
    } else if (stmt instanceof WhileStmt) {
      return new ObjcWhileStatement((WhileStmt)stmt);
    } else if (stmt instanceof EmptyStmt) {
      return new ObjcEmptyStatement((EmptyStmt)stmt);
    } else if (stmt instanceof ExpressionStmt) {
      return new ObjcExpressionStatement((ExpressionStmt)stmt);      
    } else if (stmt instanceof SynchronizedStmt) {
      return new ObjcSynchronizedStatement((SynchronizedStmt)stmt);
    }
    return new ObjcStatement(stmt);
  }
}
