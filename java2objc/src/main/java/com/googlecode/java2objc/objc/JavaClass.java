package com.googlecode.java2objc.objc;

public final class JavaClass {
  private final Class<?> clazz;

  private JavaClass(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  public Class<?> getJavaClass() {
    return clazz;
  }
  
  public static JavaClass getJavaClassFor(Class<?> clazz) {
    // TODO: maintain a cache to ensure object for the same class remain the same
    return new JavaClass(clazz);
  }
}
