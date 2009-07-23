package com.googlecode.java2objc.objc;

import java.util.HashMap;
import java.util.Map;

public final class JavaClass {
  private static final Map<Class<?>, JavaClass> cache = new HashMap<Class<?>, JavaClass>();
  
  private final Class<?> clazz;

  private JavaClass(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  public Class<?> getJavaClass() {
    return clazz;
  }
  
  public static JavaClass getJavaClassFor(Class<?> clazz) {
    JavaClass javaClass = cache.get(clazz);
    if (javaClass == null) {
      javaClass = new JavaClass(clazz);
      cache.put(clazz, javaClass);
    }
    return javaClass;
  }
}
