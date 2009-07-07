package com.googlecode.java2objc.objc;

public class ObjcEnumType extends ObjcType {

  public static final ObjcEnumType INSTANCE = new ObjcEnumType("enum");

  protected ObjcEnumType(String name) {
    // TODO(inder): write canonical Objective C Enum and use that as the base type
    super("name", NSObject.INSTANCE, true);
  }
}
