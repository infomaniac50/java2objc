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

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import com.googlecode.java2objc.objc.ObjcIfStatement;
import com.googlecode.java2objc.objc.ObjcMethod;
import com.googlecode.java2objc.objc.ObjcMethodInit;
import com.googlecode.java2objc.objc.ObjcStatement;
import com.googlecode.java2objc.objc.ObjcType;

class TranslateVisitor extends VoidVisitorAdapter<GeneratorContext> {

  @Override
  public void visit(ClassOrInterfaceDeclaration n, GeneratorContext context) {
    context.startClass(ObjcType.getTypeFor(n.getName()));
    if (n.getMembers() != null) {
      for (BodyDeclaration member : n.getMembers()) {
        member.accept(this, context);
      }
    }
  }

  @Override
  public void visit(ConstructorDeclaration n, GeneratorContext context) {
    context.getCurrentType().addMethod(new ObjcMethodInit(n));
  }

  @Override
  public void visit(BlockStmt n, GeneratorContext arg) {
    super.visit(n, arg);
  }
  
  @Override
  public void visit(MethodDeclaration n, GeneratorContext context) {
    context.getCurrentType().addMethod(new ObjcMethod(n));
    super.visit(n, context);
  }
  
  @Override
  public void visit(IfStmt n, GeneratorContext context) {
    ObjcStatement stmt = new ObjcIfStatement(n);
    context.getCurrentMethod().addStatement(stmt);
    super.visit(n, context);
  }
}