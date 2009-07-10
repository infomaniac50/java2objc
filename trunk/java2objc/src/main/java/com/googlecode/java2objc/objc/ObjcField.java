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


import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.type.Type;

public class ObjcField extends ObjcNode {

  private final ObjcType type;
  private final String name;
  public ObjcField(Type type, VariableDeclarator var) {
    this.type = ObjcType.getTypeFor(type);
    this.name = var.getId().getName();
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startNewLine().append(type.getPointerTypeName()).append(" ");
    writer.append(name).append(";").endLine();
  }
}
