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
 */
package com.googlecode.java2objc.objc;

/**
 * Objective C void type
 * 
 * @author Inderjeet Singh
 */
public final class Void extends ObjcTypeStandard {

  public static final Void INSTANCE = new Void();

  private Void() {
    // TODO (inder): may be use a JavaClass for a special Void class instead of Object.class
    super("void", null, false, new JavaClass(Object.class));
  }
}
