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

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;

import java.util.LinkedList;
import java.util.List;

public class ObjcMethod extends ObjcNode {

  private final List<ObjcMethodParam> params;
  private final ObjcType returnType;
  private final String name;
  private final ObjcMethodBody methodBody;
  private final int modifiers;
  
  public ObjcMethod(MethodDeclaration n) {
    this(n.getName(), ObjcType.getTypeFor(n.getType()), n.getParameters(), n.getModifiers());
  }
  
  public void addStatement(ObjcStatement stmt) {
    methodBody.addStatement(stmt);
  }

  public ObjcMethod(String name, ObjcType returnType, List<Parameter> params, int modifiers) {
    this.params = new LinkedList<ObjcMethodParam>();
    this.returnType = returnType;
    this.name = name;
    this.methodBody = new ObjcMethodBody();
    this.modifiers = modifiers;
    if (params != null) {
      for (Parameter param : params) {
        ObjcType type = ObjcType.getTypeFor(param.getType());
        String typeName = param.getId().getName();
        this.params.add(new ObjcMethodParam(type, typeName));
      }
    }
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    if (writer.isWritingHeaderFile()) {
      appendDeclaration(writer);
    } else {
      appendDefinition(writer);
    }
  }

  private void appendDeclaration(SourceCodeWriter writer) {
    if (!ModifierSet.isPrivate(modifiers)) {
      appendMethodSignature(writer);
      writer.append(";").endLine();
    }
  }
  
  private void appendDefinition(SourceCodeWriter writer) {
    appendMethodSignature(writer);
    writer.append(methodBody);
  }
  
  private void appendMethodSignature(SourceCodeWriter writer) {    
    boolean isStatic = ModifierSet.isStatic(modifiers);
    writer.append(isStatic ? "+" : "-");
    writer.append(" (").append(returnType.getPointerTypeName()).append(") ");
    writer.append(name);
    if (params == null || params.size() == 0) {
      return;
    }
    writer.append(": ");
    boolean first = true;
    for (ObjcMethodParam param : params) {
      if (first) {
        first = false;
      } else {
        writer.append(param.getName()).append(":");
      }
      writer.append('(').append(param.getType().getPointerTypeName()).append(")");
      writer.append(param.getName()).append(" ");
    }    
  }
}
