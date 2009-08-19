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

import com.googlecode.java2objc.code.ObjcType;
import com.googlecode.java2objc.javatypes.JavaClass;

/**
 * Representation of an Objective C Enum. Objective C doesn't have rich object based Enums like
 * Java does, so this type generates an equivalent pattern for enums.
 * 
 * @author Inderjeet Singh
 */
public final class ObjcEnumType extends ObjcType {

  public static final String[] JAVA_TYPES = {"enum", "Enum", "java.lang.Enum"};

  protected ObjcEnumType(CompilationContext context, String name) {
    // TODO(inder): write canonical Objective C Enum and use that as the base type
    super(context, name, context.getTypeRepo().getNSObject(), true, JavaClass.getJavaClassFor(Enum.class));
  }
}
