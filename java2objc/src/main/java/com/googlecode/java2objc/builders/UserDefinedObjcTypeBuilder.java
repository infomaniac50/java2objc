package com.googlecode.java2objc.builders;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.java2objc.objc.NSObject;
import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.ObjcMethod;
import com.googlecode.java2objc.objc.ObjcType;

public class UserDefinedObjcTypeBuilder {
  private final String name;
  private final Set<ObjcType> baseClasses;
  private final Set<ObjcType> imports;
  private final Set<ObjcMethod> methods;
  private final Set<ObjcField> fields;
  
  public UserDefinedObjcTypeBuilder(String name, Set<ObjcType> imports) {
    this.name = name;
    this.imports = imports;
    this.baseClasses = new HashSet<ObjcType>();
    this.methods = new HashSet<ObjcMethod>();
    this.fields = new HashSet<ObjcField>();
  }

  public ObjcType build() {
    ObjcType baseClass = baseClasses.isEmpty() ? NSObject.INSTANCE : baseClasses.iterator().next();
    return new ObjcType(name, baseClass, imports, methods, fields);
  }
  
  public void addBaseClass(ObjcType baseClass) {
    baseClasses.add(baseClass);
  }

  public void addField(ObjcField objcField) {
    fields.add(objcField);
  }

  public void addMethod(ObjcMethod objcMethod) {
    methods.add(objcMethod);
  }
}
