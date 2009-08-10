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

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.List;
import java.util.Set;

import com.googlecode.java2objc.javatypes.JavaClass;
import com.googlecode.java2objc.javatypes.JavaUtils;

/**
 * Provides utility methods to convert {@link TypeDeclaration} to {@link ObjcType}
 * 
 * @author Inderjeet Singh
 */
public final class TypeConverter {
  private final String pkgName;
  private final CompilationContext context;
  private final Set<ObjcType> imports;

  public TypeConverter(CompilationContext context, String pkgName, Set<ObjcType> imports) {
    this.context = context;
    this.pkgName = pkgName;
    this.imports = imports;
  }

  public ObjcType to(ClassOrInterfaceDeclaration type) {
    JavaClass javaClass = JavaUtils.getJavaType(pkgName, type.getName()); 
    UserDefinedObjcTypeBuilder typeBuilder = 
      new UserDefinedObjcTypeBuilder(context, type.getName(), type.isInterface(), imports, javaClass);
    buildType(typeBuilder, type);
    return typeBuilder.build();
  }

  private void buildType(UserDefinedObjcTypeBuilder typeBuilder, ClassOrInterfaceDeclaration type) {
    if (type.getExtends() != null) {
      for (ClassOrInterfaceType extendedClass : type.getExtends()) {
        String pkgName = this.pkgName; // TODO(inder) :try finding the real pkage for the extends
        typeBuilder.addBaseClass(context.getTypeRepo().getTypeFor(pkgName, extendedClass.getName()));
      }
    }
    if (type.getImplements() != null) {
      for (ClassOrInterfaceType implementedClass : type.getImplements()) {
        String pkgName = this.pkgName; // TODO(inder) :try finding the real pkage for the extends
        typeBuilder.addBaseClass(context.getTypeRepo().getTypeFor(pkgName, implementedClass.getName()));
      }
    }
    List<BodyDeclaration> members = type.getMembers();
    JavaClass containingClass = typeBuilder.getJavaClass();
    for (BodyDeclaration member : members) {
      if (member instanceof FieldDeclaration) {
        FieldDeclaration field = (FieldDeclaration) member;
        for (VariableDeclarator var : field.getVariables()) {
          ObjcField objcField = new ObjcField(context, field.getType(), var);
          typeBuilder.addField(objcField);
        }
      } else if (member instanceof MethodDeclaration) {
        typeBuilder.addMethod(context.getMethodConverter().to((MethodDeclaration) member, containingClass));
      } else if (member instanceof ConstructorDeclaration) {
        typeBuilder.addMethod(context.getMethodConverter().to((ConstructorDeclaration) member, containingClass));
      }
    }
  }
}
