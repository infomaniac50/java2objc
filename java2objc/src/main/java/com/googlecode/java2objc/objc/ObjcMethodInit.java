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

import japa.parser.ast.body.ConstructorDeclaration;

/**
 * Method body for creating an init method
 * 
 * @author Inderjeet Singh
 */
public final class ObjcMethodInit extends ObjcMethod {

  public ObjcMethodInit(CompilationContext context, ConstructorDeclaration n) {
    super("init", ObjcType.ID, n.getParameters(), n.getModifiers(), getConstructorBody(context, n));
  }
  
  // TODO(inder): constructor body should treat this() as a call to another init method
  
  private static ObjcStatementBlock getConstructorBody(CompilationContext context, 
      ConstructorDeclaration n) {
    ObjcExpression condition = new ObjcExpression("self=[super init]");
    ObjcStatement thenStmt = new ObjcStatementBlock(context, n.getBlock());
    ObjcIfStatement ifStmt = new ObjcIfStatement(condition, thenStmt, null);     
    return new ObjcStatementBlock.Builder()
      .addStatement(ifStmt)
      .addStatement(new ObjcStatement("return self;"))
      .build();    
  }
}
