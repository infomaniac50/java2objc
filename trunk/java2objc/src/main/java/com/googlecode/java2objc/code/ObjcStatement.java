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
package com.googlecode.java2objc.code;

import japa.parser.ast.stmt.Statement;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.java2objc.objc.ObjcNode;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Base class for all Objective C statements
 * 
 * @author Inderjeet Singh
 */
public class ObjcStatement extends ObjcNode {

  // TODO: Get rid of the String form of statement
  private final String stmt;
  private final List<ObjcExpression> expressions = new LinkedList<ObjcExpression>();

  protected ObjcStatement() {
    this((String) null);
  }

  public ObjcStatement(Statement stmt) {
    this.stmt = stmt.toString();
  }

  public ObjcStatement(String stmt) {
    if (stmt != null) {
      if (!stmt.endsWith(";")) {
        stmt = stmt + ";";
      }
      stmt = stmt.replace("this.", "");
    }
    this.stmt = stmt;
  }

  public void addExpression(ObjcExpression expression) {
    expressions.add(expression);
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if (stmt.trim().equals("")) {
      return;
    }
    writer.startNewLine();
    writer.append(stmt);
    for (ObjcExpression expr : expressions) {
      writer.append(expr);
    }
    writer.endLine();
  }
}
