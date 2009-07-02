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

import java.util.ArrayList;
import java.util.List;

public class ObjcMethodBody extends ObjcNode {

  private final List<ObjcStatement> statements;
  
  public ObjcMethodBody() {
    this.statements = new ArrayList<ObjcStatement>();
  }
  
  public void addStatement(ObjcStatement stmt) {
    statements.add(stmt);
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append(" {").endLine();
    writer.indent();
    for (ObjcStatement stmt : statements) {
      writer.append(stmt);
    }
    writer.unIndent();
    writer.appendLine("}").appendBlankLine();
  }
}
