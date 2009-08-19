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
package com.googlecode.java2objc.converters;

import com.googlecode.java2objc.code.ObjcStatementDoWhile;
import com.googlecode.java2objc.code.ObjcEmptyStatement;
import com.googlecode.java2objc.code.ObjcStatementExpression;
import com.googlecode.java2objc.code.ObjcStatementFor;
import com.googlecode.java2objc.code.ObjcStatementForeach;
import com.googlecode.java2objc.code.ObjcStatementIf;
import com.googlecode.java2objc.code.ObjcStatementLabeled;
import com.googlecode.java2objc.code.ObjcStatementReturn;
import com.googlecode.java2objc.code.ObjcStatement;
import com.googlecode.java2objc.code.ObjcStatementBlock;
import com.googlecode.java2objc.code.ObjcStatementSwitch;
import com.googlecode.java2objc.code.ObjcStatementSynchronized;
import com.googlecode.java2objc.code.ObjcStatementWhile;
import com.googlecode.java2objc.objc.CompilationContext;

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

  private final CompilationContext context;

  public StatementConverter(CompilationContext context) {
    this.context = context;
  }

  public ObjcStatement to(Statement stmt) {
    if (stmt instanceof IfStmt) {
      return new ObjcStatementIf((IfStmt)stmt);
    } else if (stmt instanceof BlockStmt) {
      return new ObjcStatementBlock(context, (BlockStmt)stmt);
    } else if (stmt instanceof ForStmt) {
      return new ObjcStatementFor(context, (ForStmt)stmt);
    } else if (stmt instanceof ForeachStmt) {
      return new ObjcStatementForeach(context, (ForeachStmt)stmt);
    } else if (stmt instanceof SwitchStmt) {
      return new ObjcStatementSwitch(context, (SwitchStmt)stmt);
    } else if (stmt instanceof DoStmt) {
      return new ObjcStatementDoWhile(context, (DoStmt)stmt);
    } else if (stmt instanceof WhileStmt) {
      return new ObjcStatementWhile(context, (WhileStmt)stmt);
    } else if (stmt instanceof EmptyStmt) {
      return new ObjcEmptyStatement((EmptyStmt)stmt);
    } else if (stmt instanceof ExpressionStmt) {
      return new ObjcStatementExpression(context, (ExpressionStmt)stmt);      
    } else if (stmt instanceof SynchronizedStmt) {
      return new ObjcStatementSynchronized(context, (SynchronizedStmt)stmt);
    } else if (stmt instanceof ReturnStmt) {
      return new ObjcStatementReturn(context, (ReturnStmt)stmt);
    } else if (stmt instanceof LabeledStmt) {
      return new ObjcStatementLabeled(context, (LabeledStmt)stmt);
    }
    return new ObjcStatement(stmt);
  }
}
