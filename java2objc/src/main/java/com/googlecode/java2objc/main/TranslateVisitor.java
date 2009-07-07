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

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.SuperMemberAccessExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.ObjcMethod;
import com.googlecode.java2objc.objc.ObjcMethodInit;
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
//    ObjcStatement stmt = new ObjcIfStatement(n);
//    context.getCurrentMethod().addStatement(stmt);
    super.visit(n, context);
  }
  
  @Override
  public void visit(FieldDeclaration n, GeneratorContext context) {
    for (VariableDeclarator var : n.getVariables()) {
      ObjcField objcField = new ObjcField(n.getType(), var);
      context.getCurrentType().addField(objcField);
    }
  }
  
  @Override
  public void visit(StringLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);
//    context.getCurrentStatement().addExpression(new ObjcStringLiteralExpression(n.getValue()));
  }
//  @Override
//  public void visit(ExpressionStmt n, GeneratorContext context) {
//    Expression expression = n.getExpression();
//    ObjcStatement stmt = new ObjcStatement(n.getExpression().toString());
//    context.getCurrentMethod().addStatement(stmt);
//  }
  
  @Override
  public void visit(LineComment n, GeneratorContext context) {
    super.visit(n, context);
//    ObjcStatement stmt = new ObjcStatement("//" + n.getContent());
//    context.getCurrentMethod().addStatement(stmt);
  }

  @Override
  public void visit(ReturnStmt n, GeneratorContext context) {
//    context.getCurrentMethod().addStatement(new ObjcStatement(n.toString()));
    super.visit(n, context);
  }
  
  @Override
  public void visit(ImportDeclaration n, GeneratorContext context) {
    ObjcType type = ObjcType.getTypeFor(n.getName().getName());
    context.addImport(type);
  }

  @Override
  public void visit(MethodCallExpr n, GeneratorContext context) {
//    String targetObjectName = "self";  // TODO(inder): find actual target object name
//    ObjcMethodCallExpression expression = new ObjcMethodCallExpression(targetObjectName, n);
//    context.getCurrentStatement().addExpression(expression);
    super.visit(n, context);
  }

  @Override
  public void visit(Node n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(CompilationUnit n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(PackageDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(TypeParameter n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(BlockComment n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(EnumDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(EmptyTypeDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(EnumConstantDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(AnnotationDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(AnnotationMemberDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(VariableDeclarator n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(VariableDeclaratorId n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(Parameter n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(EmptyMemberDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(InitializerDeclaration n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(JavadocComment n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(ClassOrInterfaceType n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(PrimitiveType n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(ReferenceType n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(VoidType n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(WildcardType n, GeneratorContext context) {
    super.visit(n, context);
  }

  @Override
  public void visit(ArrayAccessExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ArrayCreationExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ArrayInitializerExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(AssignExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(BinaryExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(CastExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ClassExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ConditionalExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(EnclosedExpr n, GeneratorContext context) {
    super.visit(n, context);  
  }

  @Override
  public void visit(FieldAccessExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(InstanceOfExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(IntegerLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(LongLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(IntegerLiteralMinValueExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(LongLiteralMinValueExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(CharLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(DoubleLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(BooleanLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(NullLiteralExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(NameExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ObjectCreationExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(QualifiedNameExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(SuperMemberAccessExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ThisExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(SuperExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(UnaryExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(VariableDeclarationExpr n, GeneratorContext context) {
    super.visit(n, context);   
  }

  @Override
  public void visit(MarkerAnnotationExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(SingleMemberAnnotationExpr n, GeneratorContext context) {
    super.visit(n, context);   
  }

  @Override
  public void visit(NormalAnnotationExpr n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(MemberValuePair n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ExplicitConstructorInvocationStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(TypeDeclarationStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(AssertStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(LabeledStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(EmptyStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ExpressionStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(SwitchStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(SwitchEntryStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(BreakStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(WhileStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ContinueStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(DoStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ForeachStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ForStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(ThrowStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(SynchronizedStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(TryStmt n, GeneratorContext context) {
    super.visit(n, context);    
  }

  @Override
  public void visit(CatchClause n, GeneratorContext context) {
    super.visit(n, context);    
  }
}