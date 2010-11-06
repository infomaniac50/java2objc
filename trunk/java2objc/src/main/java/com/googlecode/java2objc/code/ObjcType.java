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

import japa.parser.ast.body.ModifierSet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.ObjcNode;
import com.googlecode.java2objc.objc.ObjcVariableDeclarator;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Base class for all Objective C types
 * 
 * @author Inderjeet Singh
 */
public class ObjcType extends ObjcNode {

  protected ObjcType containingClass;
  protected final String name;
  protected final boolean isProtocol;
  protected ObjcType baseClass;
  protected final List<ObjcType> protocols;
  protected final boolean pointerType;
  protected final List<ObjcType> importsInHeader;
  protected final List<ObjcType> importsInImpl;
  protected final List<ObjcMethod> methods;
  protected final List<ObjcField> fields;
  protected final List<ObjcStatementBlock> initializers;
  protected final List<ObjcType> subTypes;
  private Map<String, String> methodMappings;
  protected String comments;
  protected int anonCounter;

  public ObjcType(CompilationContext context, String name, boolean isProtocol,
      List<ObjcType> imports) {
    this(context, name, isProtocol, true);
    importsInHeader.addAll(imports);
  }

  protected ObjcType(CompilationContext context, String name) {
    this(context, name, context.getTypeRepo().getNSObject(), null, true);
  }

  public ObjcType(CompilationContext context, String name, ObjcType baseClass, List<ObjcType> protocols, boolean pointerType) {
    this(context, name, false, pointerType);
    this.baseClass = baseClass;
    if (protocols != null) {
      this.protocols.addAll(protocols);
    }
  }

  public ObjcType(CompilationContext context, String name, boolean isProtocol, boolean pointerType) {
    this.name = name;
    this.isProtocol = isProtocol;
    this.pointerType = pointerType;
    this.protocols = new LinkedList<ObjcType>();
    if (isProtocol) {
      this.protocols.add(context.getTypeRepo().getNSObject());
    }
    importsInHeader = new LinkedList<ObjcType>();
    importsInImpl = new LinkedList<ObjcType>();
    importsInImpl.add(this);
    methods = new LinkedList<ObjcMethod>();
    fields = new LinkedList<ObjcField>();
    initializers = new LinkedList<ObjcStatementBlock>();
    subTypes = new LinkedList<ObjcType>();
    methodMappings = new HashMap<String, String>();
  }

  public void init(CompilationContext context, ObjcType baseClass, List<ObjcType> protocols,
      ObjcType containingClass, List<ObjcMethod> methods, List<ObjcField> fields,
      List<ObjcStatementBlock> initializers, List<ObjcType> subTypes, String comments) {
    this.baseClass = baseClass;
    if (protocols != null) {
      this.protocols.addAll(protocols);
    }
    this.containingClass = containingClass;
    if (containingClass != null) {
      this.importsInImpl.remove(this);
    }
    this.methods.addAll(methods);
    this.fields.addAll(fields);

    boolean cleanup = false;
    for (ObjcField field : this.fields) {
      if (!ModifierSet.isStatic(field.getModifiers()) && field.getType().isPointerType()) {
        cleanup = true;
        break;
      }
    }
    if (cleanup) {
      this.methods.add(new ObjcMethodDealloc(context, this));
    }

    this.initializers.addAll(initializers);
    ObjcStatementBlock fieldInitializer = getFieldInitializer(context, this.fields);
    if (fieldInitializer != null) {
      this.initializers.add(fieldInitializer);
    }
    addInitializersToConstructors(context, this.initializers, this.methods);
    this.subTypes.addAll(subTypes);
    this.comments = comments;
  }

  private static ObjcStatementBlock getFieldInitializer(CompilationContext context, List<ObjcField> fields) {
    List<ObjcStatement> initializer = new LinkedList<ObjcStatement>();
    for (ObjcField field : fields) {
      if (!ModifierSet.isStatic(field.getModifiers())) {
        for (ObjcVariableDeclarator var : field.getVars()) {
          if (var.getInit() != null) {
            initializer.add(new ObjcStatementExpression(new ObjcExpressionAssign(
                new ObjcExpressionSimple(context, var.getName()), var.getInit())));
          }
        }
      }
    }
    return initializer.size() > 0 ? new ObjcStatementBlock(initializer) : null;
  }

  private static void addInitializersToConstructors(CompilationContext context,
      List<ObjcStatementBlock> initializers, List<ObjcMethod> methods) {
    if (initializers.size() > 0) {
      List<ObjcStatement> initializer = new LinkedList<ObjcStatement>();
      for (ObjcStatementBlock block : initializers) {
        initializer.addAll(block.getStatements());
      }

      boolean added = false;
      for (ObjcMethod method : methods) {
        if (method instanceof ObjcMethodInit) {
          ObjcMethodInit init = (ObjcMethodInit)method;
          if (init.isBaseInit()) {
            ObjcStatementBlock body =
                (ObjcStatementBlock)((ObjcStatementIf)init.getMethodBody().getStatements().get(0))
                    .getThenStmt();
            body.getStatements().addAll(0, initializer);
            added = true;
          }
        }
      }

      if (!added) {
        ObjcStatement thenStmt = new ObjcStatementBlock(initializer);
        ObjcStatementIf ifStmt =
            new ObjcStatementIf(new ObjcExpressionSimple(context, "self = [super init]"), thenStmt, null);
        ObjcStatementBlock body = new ObjcStatementBlock.Builder()
          .addStatement(ifStmt)
          .addStatement(new ObjcStatementSimple("return self;"))
          .build();
        ObjcMethodInit init =
            new ObjcMethodInit(context, "init", context.getTypeRepo().getVoid(), null,
                ModifierSet.PUBLIC, true, body, null);
        methods.add(0, init);
      }
    }
  }

  public String getName() {
    return name;
  }

  public boolean isProtocol() {
    return isProtocol;
  }

  public boolean hasImpl() {
    return !isProtocol;
  }

  public boolean isPointerType() {
    return pointerType;
  }

  public String getHeaderFileName() {
    return name + ".h";
  }

  public String getImplFileName() {
    return name + ".m";
  }

  public String getPointerTypeName() {
    if (isProtocol)
      return "id<" + name + ">";
    return pointerType && !"id".equals(name) ? name + " *" : name;
  }

  public ObjcMethod getMethodWithName(String methodName) {
    return getMethodWithName(methodName, null);
  }

  public ObjcMethod getMethodWithName(String methodName, Integer paramCount) {
    for (ObjcMethod method : methods) {
      if (method.getName().equals(methodName)
          && (paramCount == null || method.getParams().size() == paramCount)) {
        return method;
      }
    }
    return null;
  }

  public boolean hasPropertyGetter(String propertyName) {
    for (ObjcMethod method : methods) {
      if (method.isGetter() && method.getPropertyName().equals(propertyName)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasPropertySetter(String propertyName) {
    for (ObjcMethod method : methods) {
      if (method.isSetter() && method.getPropertyName().equals(propertyName)) {
        return true;
      }
    }
    return false;
  }

  public ObjcField getFieldWithName(String fieldName) {
    for (ObjcField field : fields) {
      if (field.hasName(fieldName)) {
        return field;
      }
    }
    return null;
  }

  public String getNextAnonymousName() {
    anonCounter++;
    return String.format("%s_Anon%d", name, anonCounter);
  }

  public void addSubType(ObjcType subType) {
    subTypes.add(subType);
  }

  public void addMethodMapping(String methodName, String functionName) {
    methodMappings.put(methodName, functionName);
  }

  public String mapMethodName(String name) {
    String mappedName = methodMappings.get(name);
    if (mappedName == null && baseClass != null) {
      mappedName = baseClass.mapMethodName(name);
    }
    return mappedName != null ? mappedName : name;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if (writer.isWritingHeaderFile()) {
      appendHeaderBody(writer);
    } else if (!isProtocol) {
      appendImplBody(writer);
    }
  }

  public void appendImport(SourceCodeWriter writer) {
    writer.append("#import \"").append(getHeaderFileName());
    writer.append("\"").newLine();
  }

  public void appendHeaderImports(SourceCodeWriter writer) {
    for (ObjcType importedClass : importsInHeader) {
      importedClass.appendImport(writer);
    }
  }

  public void appendBodyImports(SourceCodeWriter writer) {
    for (ObjcType importedClass : importsInImpl) {
      writer.append("#import \"").append(importedClass.getHeaderFileName());
      writer.append("\"").newLine();
    }
  }

  private void appendHeaderBody(SourceCodeWriter writer) {
    for (ObjcType subType : subTypes) {
      subType.append(writer);
    }

    if (comments != null) {
      writer.appendDocComment(comments);
    }

    if (!isProtocol) {
      boolean statics = false;
      for (ObjcField field : fields) {
        if (ModifierSet.isStatic(field.getModifiers()) && ModifierSet.isPublic(field.getModifiers())) {
          if (!statics) {
            statics = true;
            writer.newLine();
          }
          writer.append(field);
        }
      }
    }

    if (isProtocol) {
      writer.newLine().append("@protocol ").append(name);
    } else {
      writer.newLine().append("@interface ").append(name).append(" : ").append(baseClass.getName());
    }

    if (protocols.size() > 0) {
      writer.append(" <");
      boolean first = true;
      for (ObjcType protocol : protocols) {
        if (first) {
          first = false;
        } else {
          writer.append(", ");
        }
        writer.append(protocol.getName());
      }
      writer.append('>');
    }

    if (!isProtocol) {
      writer.append(" {").indent().newLine();
      for (ObjcField field : fields) {
        if (!ModifierSet.isStatic(field.getModifiers())) {
          writer.append(field);
        }
      }
      writer.unIndent();
      writer.append("}").newLine();
    }

    writer.newLine();

    for (ObjcMethod m : methods) {
      if (m.isGetter()) {
        writer.append("@property(nonatomic");
        if (m.getReturnType().isPointerType()) {
          writer.append(", retain");
        }
        if (!hasPropertySetter(m.getPropertyName())) {
          writer.append(", readonly");
        }
        writer.append(") ").append(m.getReturnType().getPointerTypeName()).append(' ')
            .append(m.getPropertyName()).endStatement();
      }
    }

    for (ObjcMethod m : methods) {
      if (!m.isGetter() && !ModifierSet.isPrivate(m.getModifiers())) {
        writer.append(m);
      }
    }
    writer.append("@end").newLine();
  }

  private void appendImplBody(SourceCodeWriter writer) {
    for (ObjcType subType : subTypes) {
      subType.append(writer);
    }

    boolean statics = false;
    for (ObjcField field : fields) {
      if (ModifierSet.isStatic(field.getModifiers())) {
        if (!statics) {
          statics = true;
          writer.newLine();
        }
        writer.append(field);
      }
    }

    writer.newLine().append("@implementation ").append(name).newLine().appendBlankLine();
    boolean properties = false;
    for (ObjcMethod m : methods) {
      if (m.isGetter()) {
        properties = true;
        writer.append("@synthesize ").append(m.getPropertyName()).endStatement();
      }
    }
    if (properties) {
      writer.newLine();
    }
    for (ObjcMethod m : methods) {
      if (!m.isSimple() || (!m.isGetter() && !hasPropertyGetter(m.getPropertyName()))) {
        writer.append(m);
      }
    }
    writer.append("@end").newLine();
  }
}
