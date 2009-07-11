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

import java.util.HashSet;
import java.util.Set;

/**
 * A Builder to build a user-defined Objective C type 
 * 
 * @author Inderjeet Singh
 */
public final class UserDefinedObjcTypeBuilder {
  private final CompilationContext context;
  private final ObjcType type;
  private final Set<ObjcType> baseClasses;
  private final Set<ObjcMethod> methods;
  private final Set<ObjcField> fields;
  
  public UserDefinedObjcTypeBuilder(CompilationContext context, String name, boolean isInterface,
      Set<ObjcType> imports) {
    this.context = context;
    this.baseClasses = new HashSet<ObjcType>();
    this.methods = new HashSet<ObjcMethod>();
    this.fields = new HashSet<ObjcField>();
    this.type = new ObjcType(context, name, isInterface, imports);
    context.setCurrentType(type);
  }

  public ObjcType build() {
    ObjcType baseClass = baseClasses.isEmpty() ? NSObject.INSTANCE : baseClasses.iterator().next();
    type.init(context, baseClass, methods, fields);
    context.setCurrentType(null);
    return type;
  }
  
  public UserDefinedObjcTypeBuilder addBaseClass(ObjcType baseClass) {
    baseClasses.add(baseClass);
    return this;
  }

  public UserDefinedObjcTypeBuilder addField(ObjcField objcField) {
    fields.add(objcField);
    return this;
  }

  public UserDefinedObjcTypeBuilder addMethod(ObjcMethod objcMethod) {
    methods.add(objcMethod);
    return this;
  }
}
