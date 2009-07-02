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

import java.util.Map;

public enum ObjcTypes {
  NSSTRING(NSString.INSTANCE, "String"), 
  NSARRAY(NSArray.INSTANCE, "Array"), 
  NSMUTABLE_ARRAY(NSMutableArray.INSTANCE, "Collection", "List", "ArrayList"), 
  NSINTEGER(NSInteger.INSTANCE, "int", "Integer", "short", "Short", "long", "Long", "byte", "Byte"), 
  NSDECIMAL(NSDecimal.INSTANCE, "float", "Float", "double", "Double", "BigDecimal"), 
  NSOBJECT(NSObject.INSTANCE, "Object"), 
  BOOL(Bool.INSTANCE, "boolean", "Boolean"),
  VOID(Void.INSTANCE, "void");
  
  private final ObjcType typeHandler;
  private final String[] javaTypesHandled;

  private ObjcTypes(ObjcType typeHandler, String... javaTypesHandled) {
    this.typeHandler = typeHandler;
    this.javaTypesHandled = javaTypesHandled;
  }
  
  public void registerTypes(Map<String, ObjcType> types) {
    for (String javaType : javaTypesHandled) {
      types.put(javaType, typeHandler);
    }
  }
}
