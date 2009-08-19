package com.googlecode.java2objc.javatypes;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.java2objc.code.ObjcType;

public final class JavaClass {
  private static final Map<Class<?>, JavaClass> cache = new HashMap<Class<?>, JavaClass>();
  
  private final Class<?> clazz;

  private JavaClass(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  public Class<?> getJavaClass() {
    return clazz;
  }
  
  public JavaConstructor getJavaConstructor(ObjcType[] parameterTypes) {
    try {
      Constructor<?> constructor = clazz.getConstructor(convert(parameterTypes));
      return new JavaConstructor(constructor);
    } catch (SecurityException e) {
    } catch (NoSuchMethodException e) {
    }
    return JavaConstructor.UNKNOWN;
  }

  @SuppressWarnings("unchecked")
  public JavaMethod getMethod(String name, Class[] parameterTypes) {
    try {
      return new JavaMethod(clazz.getMethod(name, parameterTypes));
    } catch (SecurityException e) {
    } catch (NoSuchMethodException e) {
    }
    return JavaMethod.UNKNOWN;
  }

  @SuppressWarnings("unchecked")
  public JavaMethod getMethod(String name, ObjcType[] paramTypes) {
    try {
      Class[] paramClasses = convert(paramTypes);
      return new JavaMethod(clazz.getMethod(name, paramClasses));
    } catch (SecurityException e) {
    } catch (NoSuchMethodException e) {
    }
    return JavaMethod.UNKNOWN;
  }

  @SuppressWarnings("unchecked")
  private Class[] convert(ObjcType[] paramTypes) {
    Class[] paramClasses = new Class[paramTypes.length];
    for (int i = 0; i < paramTypes.length; ++i) {
      paramClasses[i] = paramTypes[i].getClass();
    }
    return paramClasses;
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
