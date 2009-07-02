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

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ObjcMethod {

  private final List<ObjcMethodParam> params;
  private final ObjcType returnType;
  private final String name;
  private final ObjcMethodBody methodBody;
  
  public ObjcMethod(ConstructorDeclaration n) {
    this("init", ObjcType.ID, n.getParameters());
  }

  public ObjcMethod(MethodDeclaration n) {
    this(n.getName(), ObjcType.getTypeFor(n.getType()), n.getParameters());
  }

  public void addStatement(ObjcStatement stmt) {
    methodBody.addStatement(stmt);
  }

  public ObjcMethod(String name, ObjcType returnType, List<Parameter> params) {
    this.params = new LinkedList<ObjcMethodParam>();
    this.returnType = returnType;
    this.name = name;
    this.methodBody = new ObjcMethodBody();    
  }
  
  public void appendDeclaration(Appendable writer) throws IOException {
    appendMethodSignature(writer);
    writer.append(";\n");
  }
  
  public void appendDefinition(Appendable writer) throws IOException {
    appendMethodSignature(writer);
    methodBody.append(writer);
  }
  
  private void appendMethodSignature(Appendable writer) throws IOException {
    writer.append("- (");
    writer.append(returnType.getPointerTypeName()).append(") ");
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
