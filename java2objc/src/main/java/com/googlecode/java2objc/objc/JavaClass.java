package com.googlecode.java2objc.objc;

public final class JavaClass {
  private final Class<?> clazz;

  public JavaClass(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  public Class<?> getJavaClass() {
    return clazz;
  }
}
