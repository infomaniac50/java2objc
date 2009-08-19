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
 */package com.googlecode.java2objc.objc;

import japa.parser.ast.type.Type;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.java2objc.code.ObjcType;
import com.googlecode.java2objc.javatypes.JavaClass;
import com.googlecode.java2objc.javatypes.JavaUtils;

public class ObjcTypeRepository {

  private final CompilationContext context;
  private final Map<String, ObjcType> types = new HashMap<String, ObjcType>();
  
  public ObjcTypeRepository(CompilationContext context) {
    this.context = context;
  }

  public void init() {
    getNSObject();
    getNSString();
    getNSArray();
    getNSMutableArray();
    getNSInteger();
    getNSDecimal();
    getNSBool();
    getNSId();
    getNSVoid();
    getNSEnum();
  }

  public ObjcType get(String className) {
    return types.get(className);
  }

  public void put(String name, ObjcType objcType) {
    types.put(name, objcType);
  }

  public ObjcType getTypeFor(String pkgName, Type type) {
    String name = type.toString();
    return getTypeFor(pkgName, name);
  }

  public ObjcType getTypeFor(String pkgName, String name) {
    return getTypeFor(pkgName, name, getNSObject(), true);
  }

  public ObjcType getTypeFor(String name, boolean pointerType, JavaClass javaClass) {
    return getTypeFor(name, getNSObject(), pointerType, javaClass);
  }

  private ObjcType getTypeFor(String pkgName, String className, ObjcType baseType, boolean pointerType) {
    JavaClass javaType = JavaUtils.getJavaType(pkgName, className);
    return getTypeFor(className, baseType, pointerType, javaType);
  }
  
  private ObjcType getTypeFor(String name, ObjcType baseType, boolean pointerType, JavaClass javaClass) {
    if (name.endsWith("]")) {
      return getNSArray();
    }
    ObjcType objcType = get(name);
    if (objcType == null) {
      objcType = new ObjcType(context, name, baseType, pointerType, javaClass);
      put(name, objcType);
    }
    return objcType;
  }

  public NSVoid getNSVoid() {
    NSVoid objc = (NSVoid) types.get(NSVoid.JAVA_TYPES[0]);
    if (objc == null) {
      objc = new NSVoid(context);
      registerTypes(NSVoid.JAVA_TYPES, objc);
    }
    return objc;
  }

  public NSObject getNSObject() {
    NSObject objc = (NSObject) types.get(NSObject.JAVA_TYPES[0]);
    if (objc == null) {
      objc = new NSObject(context);
      registerTypes(NSObject.JAVA_TYPES, objc);
    }
    return objc;
  }
  
  public NSArray getNSArray() {
    NSArray arr = (NSArray) types.get(NSArray.JAVA_TYPES[0]);
    if (arr == null) {
      arr = new NSArray(context);
      registerTypes(NSArray.JAVA_TYPES, arr);
    }
    return arr;
  }

  public NSMutableArray getNSMutableArray() {
    NSMutableArray arr = (NSMutableArray) types.get(NSMutableArray.JAVA_TYPES[0]);
    if (arr == null) {
      arr = new NSMutableArray(context);
      registerTypes(NSMutableArray.JAVA_TYPES, arr);
    }
    return arr;
  }

  public NSId getNSId() {
    NSId nsId = (NSId) types.get(NSId.JAVA_TYPES[0]);
    if (nsId == null) {
      nsId = new NSId(context);
      registerTypes(NSId.JAVA_TYPES, nsId);
    }
    return nsId;
  }
  
  public NSString getNSString() {
    NSString str = (NSString) types.get(NSString.JAVA_TYPES[0]);
    if (str == null) {
      str = new NSString(context);
      registerTypes(NSString.JAVA_TYPES, str);
    }
    return str;
  }
 
  public NSInteger getNSInteger() {
    NSInteger intr = (NSInteger) types.get(NSInteger.JAVA_TYPES[0]);
    if (intr == null) {
      intr = new NSInteger(context);
      registerTypes(NSInteger.JAVA_TYPES, intr);
    }
    return intr;
  }

  public NSDecimal getNSDecimal() {
    NSDecimal dec = (NSDecimal) types.get(NSDecimal.JAVA_TYPES[0]);
    if (dec == null) {
      dec = new NSDecimal(context);
      registerTypes(NSDecimal.JAVA_TYPES, dec);
    }
    return dec;
  }

  public NSBool getNSBool() {
    NSBool bool = (NSBool) types.get(NSBool.JAVA_TYPES[0]);
    if (bool == null) {
      bool = new NSBool(context);
      registerTypes(NSBool.JAVA_TYPES, bool);
    }
    return bool;
  }

  public ObjcEnumType getNSEnum() {
    ObjcEnumType objc = (ObjcEnumType) types.get(ObjcEnumType.JAVA_TYPES[0]);
    if (objc == null) {
      objc = new ObjcEnumType(context, "enum");
      registerTypes(ObjcEnumType.JAVA_TYPES, objc);
    }
    return objc;
  }

  private void registerTypes(String[] javaTypes, ObjcType type) {
    for (String javaType : javaTypes) {
      types.put(javaType, type);
    }
  }
}
