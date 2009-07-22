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

/**
 * Base class for all Objective C static or class methods
 * 
 * @author Inderjeet Singh
 */
public class ObjcMethod extends ObjcNode {

  // TODO: Add special method classes for toString() and equals()
  private final List<ObjcMethodParam> params;
  private final ObjcType returnType;
  private final String name;
  private final ObjcStatementBlock methodBody;
  private final int modifiers;
  public ObjcMethod(CompilationContext context, MethodDeclaration n) {
    this(context, n, n.getName());
  }
  
  public ObjcMethod(CompilationContext context, MethodDeclaration n, String name) {
    if (context != null) {
      context.setCurentMethod(this);
    }
    String pkgName = null; // TODO(inder): get the real package name for the return type
    ObjcTypeRepository typeRepo = context.getTypeRepo();
    this.returnType = typeRepo.getTypeFor(pkgName, n.getType());
    this.name = name;
    this.methodBody = new ObjcStatementBlock(context, n.getBody());
    this.modifiers = n.getModifiers();
    this.params = new LinkedList<ObjcMethodParam>();
    if (n.getParameters() != null) {
      for (Parameter param : n.getParameters()) {
        String paramPkgName = null; // TODO(inder): figure out the package name
        ObjcType type = typeRepo.getTypeFor(paramPkgName, param.getType());
        String typeName = param.getId().getName();
        this.params.add(new ObjcMethodParam(type, typeName));
      }
    }
    if (context != null) {
      context.setCurentMethod(null);
    }
  }

  public ObjcMethod(CompilationContext context, String name, ObjcType returnType, 
      List<Parameter> params, int modifiers, ObjcStatementBlock methodBody) {
    if (context != null) {
      context.setCurentMethod(this);
    }
    this.returnType = returnType;
    this.name = name;
    this.methodBody = methodBody;
    this.modifiers = modifiers;
    this.params = new LinkedList<ObjcMethodParam>();
    ObjcTypeRepository typeRepo = context.getTypeRepo();
    if (params != null) {
      for (Parameter param : params) {
        String paramPkgName = null; // TODO(inder): figure out the correct package name fo rhte parameter
        ObjcType type = typeRepo.getTypeFor(paramPkgName, param.getType());
        String typeName = param.getId().getName();
        this.params.add(new ObjcMethodParam(type, typeName));
      }
    }
    if (context != null) {
      context.setCurentMethod(null);
    }
  }

  /**
   * Returns true if this method is a static method
   */
  public boolean isStatic() {
    return ModifierSet.isStatic(modifiers);
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
    writer.append(" ");
    writer.append(methodBody);
    writer.endLine();
  }
  
  private void appendMethodSignature(SourceCodeWriter writer) {    
    writer.append(isStatic() ? "+" : "-");
    writer.append(" (").append(returnType.getPointerTypeName()).append(") ");
    writer.append(name);
    if (params == null || params.size() == 0) {
      return;
    }
    writer.append(":");
    boolean first = true;
    for (ObjcMethodParam param : params) {
      if (first) {
        first = false;
      } else {
        writer.append(" ").append(param.getName()).append(":");
      }
      writer.append('(').append(param.getType().getPointerTypeName()).append(")");
      writer.append(param.getName());
    }    
  }

  public Object getName() {
    return name;
  }
}
