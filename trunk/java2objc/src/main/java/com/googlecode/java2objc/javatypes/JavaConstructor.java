package com.googlecode.java2objc.javatypes;

import java.lang.reflect.Constructor;

@SuppressWarnings("unchecked")
public final class JavaConstructor extends JavaMethod {
  public static final JavaConstructor UNKNOWN = new JavaConstructor(null);

  private final Constructor constructor;

  public JavaConstructor(Constructor constructor) {
    super(null); // TODO: can we avoid extending from JavaMethod
    this.constructor = constructor;
  }

  public Constructor getConstructor() {
    return constructor;
  }
}
