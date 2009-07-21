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

  public static final ObjcType ID = getTypeFor("id", false, new JavaClass(Object.class));
  private final JavaClass javaClass;
  private final String name;
  private final boolean isInterface;
  private ObjcType baseClass;
  private final boolean pointerType;
  private final Set<ObjcType> importsInHeader;
  private final Set<ObjcType> importsInImpl;
  private final Set<ObjcMethod> methods;
  private final Set<ObjcField> fields;
  
  public ObjcType(CompilationContext context, String name, boolean isInterface, 
      Set<ObjcType> imports, JavaClass javaClass) {
    this.name = name;
    this.isInterface = isInterface;
    this.pointerType = true;
    this.javaClass = javaClass;
    importsInHeader = imports;
    importsInImpl = new HashSet<ObjcType>();
    importsInImpl.add(this);
    this.fields = new HashSet<ObjcField>();
    this.methods = new HashSet<ObjcMethod>();    
  }
  
  public void init(CompilationContext context, ObjcType baseClass, Set<ObjcMethod> methods, 
      Set<ObjcField> fields) {
    this.baseClass = baseClass;
    this.fields.addAll(fields);
    this.methods.addAll(methods);    
    methods.add(new ObjcMethodDealloc(context, this));
  }
  
  protected ObjcType(ObjcTypes type, JavaClass javaClass) {
    this(type.toString(), javaClass);
  }

  protected ObjcType(String name, JavaClass javaClass) {
    this(name, NSObject.INSTANCE, true, javaClass);
  }

  protected ObjcType(ObjcTypes type, ObjcType baseClass, boolean pointerType, JavaClass javaClass) {
    this(type.toString(), baseClass, pointerType, javaClass);
  }

  protected ObjcType(String name, ObjcType baseClass, boolean pointerType, JavaClass javaClass) {
    this.name = name;
    this.isInterface = false;
    this.baseClass = baseClass;
    this.pointerType = pointerType;
    this.javaClass = javaClass;
    importsInHeader = new HashSet<ObjcType>();
    importsInImpl = new HashSet<ObjcType>();
    importsInImpl.add(this);
    fields = new HashSet<ObjcField>();
    methods = new HashSet<ObjcMethod>();    
    methods.add(new ObjcMethodDealloc(null, this));
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
  
  public void appendImport(SourceCodeWriter writer) {
    writer.append("#import \"").append(getHeaderFileName());
    writer.append("\"").endLine();
  }

  private void appendHeaderBody(SourceCodeWriter writer) {
    for (ObjcType importedClass : importsInHeader) {
      importedClass.appendImport(writer);
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

  public static synchronized ObjcType getTypeFor(String pkgName, Type type) {
    String name = type.toString();
    return getTypeFor(pkgName, name);
  }

  public static ObjcType getTypeFor(String pkgName, String name) {
    return getTypeFor(pkgName, name, NSObject.INSTANCE, true);
  }

  public static ObjcType getTypeFor(String name, boolean pointerType, JavaClass javaClass) {
    return getTypeFor(name, NSObject.INSTANCE, pointerType, javaClass);
  }

  private static ObjcType getTypeFor(String pkgName, String className, ObjcType baseType, boolean pointerType) {
    JavaClass javaType = JavaUtils.getJavaType(pkgName, className);
    return getTypeFor(className, baseType, pointerType, javaType);
  }
  
  private static ObjcType getTypeFor(String name, ObjcType baseType, boolean pointerType, JavaClass javaClass) {
    if (name.endsWith("]")) {
      return NSArray.INSTANCE;
    }
    ObjcType objcType = types.get(name);
    if (objcType == null) {
      objcType = new ObjcType(name, baseType, pointerType, javaClass);
      types.put(name, objcType);
    }
    return objcType;
  }

  public ObjcMethod getMethodWithName(String methodName) {
    for (ObjcMethod method : methods) {
      if (method.getName().equals(methodName)) {
        return method;
      }
    }
    return null;
  }
}
