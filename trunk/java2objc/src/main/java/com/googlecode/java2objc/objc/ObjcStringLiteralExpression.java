package com.googlecode.java2objc.objc;

public class ObjcStringLiteralExpression extends ObjcExpression {

  public ObjcStringLiteralExpression(String value) {
    super("@\"" + value + "\"");
  }
}
