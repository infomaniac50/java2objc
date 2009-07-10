/*
 * Copyright (C) 2009 Inderjeet Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.WhileStmt;

/**
 * A utility class to convert Java statements into their equivalent Objective C statements
 * 
 * @author Inderjeet Singh
 */
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
    } else if (stmt instanceof ReturnStmt) {
      return new ObjcReturnStatement((ReturnStmt)stmt);
    } else if (stmt instanceof LabeledStmt) {
      return new ObjcLabeledStmt((LabeledStmt)stmt);
    }
    return new ObjcStatement(stmt);
  }
}
