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
package com.googlecode.java2objc.main;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.java2objc.objc.ObjcMethod;
import com.googlecode.java2objc.objc.ObjcType;

public class GeneratorContext {

  private final List<ObjcType> imports = new LinkedList<ObjcType>();
  private ObjcType objcType;
  
  public void startClass(ObjcType objcClass) {
    this.objcType = objcClass;
    this.objcType.addImports(imports);
    imports.clear();
  }
  
  public ObjcType getCurrentType() {
    return objcType;
  }
  
  public void addImport(ObjcType type) {
    imports.add(type);
  }
  
  public ObjcMethod getCurrentMethod() {
    return objcType.getCurrentMethod();
  }
}
