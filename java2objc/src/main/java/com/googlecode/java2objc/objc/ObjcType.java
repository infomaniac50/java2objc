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

import japa.parser.ast.type.Type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Base class for all Objective C types
 * 
 * @author Inderjeet Singh
 */
public class ObjcType extends ObjcNode {

  private static Map<String, ObjcType> types = new HashMap<String, ObjcType>();
  static {
    for (ObjcTypes objcType : ObjcTypes.values()) {
      objcType.registerTypes(types);
    }
  }

  public static final ObjcType ID = getTypeFor("id", false);
  private final String name;
  private final boolean isInterface;
  private final ObjcType baseClass;
  private final boolean pointerType;
  private final Set<ObjcType> importsInHeader;
  private final Set<ObjcType> importsInImpl;
  private final Set<ObjcMethod> methods;
  private final Set<ObjcField> fields;
  
  public ObjcType(String name, boolean isInterface, ObjcType baseClass, Set<ObjcType> imports, 
      Set<ObjcMethod> methods, Set<ObjcField> fields) {
    this.name = name;
    this.isInterface = isInterface;
    this.baseClass = baseClass;
    this.pointerType = true;
    importsInHeader = imports;
    importsInImpl = new HashSet<ObjcType>();
    importsInImpl.add(this);
    this.fields = fields;
    this.methods = methods;    
    methods.add(new ObjcMethodDealloc(this));
  }
  
  protected ObjcType(ObjcTypes type) {
    this(type.toString());
  }

  protected ObjcType(String name) {
    this(name, NSObject.INSTANCE, true);
  }

  protected ObjcType(ObjcTypes type, ObjcType baseClass, boolean pointerType) {
    this(type.toString(), baseClass, pointerType);
  }

  protected ObjcType(String name, ObjcType baseClass, boolean pointerType) {
    this.name = name;
    this.isInterface = false;
    this.baseClass = baseClass;
    this.pointerType = pointerType;
    importsInHeader = new HashSet<ObjcType>();
    importsInImpl = new HashSet<ObjcType>();
    importsInImpl.add(this);
    fields = new HashSet<ObjcField>();
    methods = new HashSet<ObjcMethod>();    
    methods.add(new ObjcMethodDealloc(this));
  }
  
  public String getName() {
    return name;
  }

  public boolean isInterface() {
    return isInterface;
  }

  public String getHeaderFileName() {
    return name + ".h";
  }
  
  public String getImplFileName() {
    return name + ".m";
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    if (writer.isWritingHeaderFile()) {
      appendHeaderBody(writer);
    } else {
      if (!isInterface) {
        appendImplBody(writer);
      }
    }
  }

  private void appendHeaderBody(SourceCodeWriter writer) {
    for (ObjcType importedClass : importsInHeader) {
      writer.startNewLine();
      writer.append("#import \"").append(importedClass.getHeaderFileName());
      writer.append("\"").endLine();
    }
    writer.startNewLine().append("@interface ").append(name).append(" : ");
    writer.append(baseClass.getName()).append(" {").endLine();
    writer.indent();
    for (ObjcField field : fields) {
      writer.append(field);
    }
    writer.unIndent();
    writer.appendLine("}");
    for (ObjcMethod m : methods) {
      writer.append(m);
    }
    writer.appendLine("@end");
  }
  
  private void appendImplBody(SourceCodeWriter writer) {
    for (ObjcType importedClass : importsInImpl) {
      writer.startNewLine().append("#import \"").append(importedClass.getHeaderFileName());
      writer.append("\"").endLine();
    }
    writer.startNewLine().append("@implementation ").append(name).endLine().appendBlankLine();
    for (ObjcMethod m : methods) {
      writer.append(m);
    }
    writer.appendLine("@end");
  }

  public String getPointerTypeName() {
    return pointerType ? name + " *" : name;
  }

  public static synchronized ObjcType getTypeFor(Type type) {
    String name = type.toString();
    return getTypeFor(name);
  }

  public static ObjcType getTypeFor(String name) {
    return getTypeFor(name, NSObject.INSTANCE, true);
  }

  public static ObjcType getTypeFor(String name, boolean pointerType) {
    return getTypeFor(name, NSObject.INSTANCE, pointerType);
  }

  private static ObjcType getTypeFor(String name, ObjcType baseType, boolean pointerType) {
    if (name.endsWith("]")) {
      return NSArray.INSTANCE;
    }
    ObjcType objcType = types.get(name);
    if (objcType == null) {
      objcType = new ObjcType(name, baseType, pointerType);
      types.put(name, objcType);
    }
    return objcType;
  }
}
