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

/**
 * A predefined in Objective C available through the standard libraries
 * 
 * @author Inderjeet Singh
 */
public abstract class ObjcTypeStandard extends ObjcType {
  
  /**
   * @param name name of the type
   * @param baseClass base class
   * @param pointerType whether this is a pointer type or a primitive type
   */
  protected ObjcTypeStandard(CompilationContext context, String className, ObjcType baseClass, boolean pointerType) {
    super(context, className, baseClass, null, pointerType);
  }

  /**
   * @param name name of the type
   */
  public ObjcTypeStandard(CompilationContext context, String name) {
    super(context, name);
  }

  @Override
  public void appendImport(SourceCodeWriter writer) {
    // These are predefined types, so no need to add an import statement
  }
}
