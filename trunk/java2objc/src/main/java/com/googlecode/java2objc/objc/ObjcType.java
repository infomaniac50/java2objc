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

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjcType {

  private static Map<String, ObjcType> types = new HashMap<String, ObjcType>();
  static {
    ObjcType nsIntegerType = new ObjcType("NSInteger", null, false);
    types.put("int", nsIntegerType);
    types.put("Integer", nsIntegerType);
    types.put("byte", nsIntegerType);
    types.put("Byte", nsIntegerType);
    types.put("short", nsIntegerType);
    types.put("Short", nsIntegerType);
    types.put("long", nsIntegerType);
    types.put("Long", nsIntegerType);
    ObjcType nsDecimalType = new ObjcType("NSDecimal", null, false);
    types.put("float", nsDecimalType);
    types.put("Float", nsDecimalType);
    types.put("double", nsDecimalType);
    types.put("Double", nsDecimalType);
    types.put("BigDecimal", nsDecimalType);
    ObjcType nsBooleanType = new ObjcType("BOOL", null, false);
    types.put("boolean", nsBooleanType);
    types.put("Boolean", nsBooleanType);
    ObjcType nsVoidType = new ObjcType("void", null, false);
    types.put("void", nsVoidType);
  }

  public static final ObjcType NSOBJECT = getTypeFor("NSObject", null, true);
  public static final ObjcType ID = getTypeFor("id");
  private final String name;
  private final ObjcType baseClass;
  private final boolean pointerType;
  private final Set<ObjcType> importsInHeader;
  private final Set<ObjcType> importsInImpl;
  private final Set<ObjcMethod> methods;
  private ObjcMethod currentMethod;
  
  private ObjcType(String name) {
    this(name, NSOBJECT, true);
  }

  private ObjcType(String name, ObjcType baseClass, boolean pointerType) {
    this.name = name;
    this.baseClass = baseClass;
    this.pointerType = pointerType;
    importsInHeader = new HashSet<ObjcType>();
    importsInImpl = new HashSet<ObjcType>();
    importsInImpl.add(this);
    methods = new HashSet<ObjcMethod>();
    currentMethod = null;
  }
  
  public void addMethod(ObjcMethod method) {
    methods.add(method);
    currentMethod = method;
  }

  public ObjcMethod getCurrentMethod() {
    return currentMethod;
  }
  public String getName() {
    return name;
  }

  public String getHeaderFileName() {
    return name + ".h";
  }
  
  public void appendHeaderBody(Appendable writer) throws IOException {
    for (ObjcType importedClass : importsInHeader) {
      writer.append("#import \"").append(importedClass.getHeaderFileName()).append("\"\n");
    }
    writer.append("\n");
    writer.append("@interface ").append(name).append(" : ").append(baseClass.getName()).append(" {\n");
    writer.append("}\n");
    for (ObjcMethod m : methods) {
      m.appendDeclaration(writer);
    }
    writer.append("@end");
  }
  
  public void appendImplBody(Appendable writer) throws IOException {
    for (ObjcType importedClass : importsInImpl) {
      writer.append("#import \"").append(importedClass.getHeaderFileName()).append("\"\n");
    }
    writer.append("\n");
    writer.append("@implementation ").append(name).append("\n\n");
    for (ObjcMethod m : methods) {
      m.appendDefinition(writer);
    }
    writer.append("@end\n");
  }

  public String getPointerTypeName() {
    return pointerType ? name + " *" : name;
  }

  public static synchronized ObjcType getTypeFor(Type type) {
    String name = type.toString();
    return getTypeFor(name);
  }

  public static ObjcType getTypeFor(String name) {
    return getTypeFor(name, NSOBJECT, true);
  }

  public static ObjcType getTypeFor(String name, boolean pointerType) {
    return getTypeFor(name, NSOBJECT, pointerType);
  }

  private static ObjcType getTypeFor(String name, ObjcType baseType, boolean pointerType) {
    ObjcType objcType = types.get(name);
    if (objcType == null) {
      objcType = new ObjcType(name, baseType, pointerType);
      types.put(name, objcType);
    }
    return objcType;
  }
}
