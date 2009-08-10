package com.googlecode.java2objc.javatypes;

import java.lang.reflect.Method;

public class JavaMethod {
  public static final JavaMethod UNKNOWN = new JavaMethod(null);

  private final Method method;

  public JavaMethod(Method method) {
    this.method = method;
  }

  public Method getMethod() {
    return method;
  }
}
