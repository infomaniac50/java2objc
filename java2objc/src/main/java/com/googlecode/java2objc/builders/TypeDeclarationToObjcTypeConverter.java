package com.googlecode.java2objc.builders;

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.List;
import java.util.Set;

import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.ObjcMethod;
import com.googlecode.java2objc.objc.ObjcType;

/**
 * Provides utility methods to convert {@link TypeDeclaration} to {@link ObjcType}
 * 
 * @author inder
 */
public class TypeDeclarationToObjcTypeConverter {
  private final Set<ObjcType> imports;

  public TypeDeclarationToObjcTypeConverter(Set<ObjcType> imports) {
    this.imports = imports;
  }

  public ObjcType createObjcType(ClassOrInterfaceDeclaration type) {
    UserDefinedObjcTypeBuilder typeBuilder = 
      new UserDefinedObjcTypeBuilder(type.getName(), imports);
    buildType(typeBuilder, type);
    return typeBuilder.build();
  }

  private void buildType(UserDefinedObjcTypeBuilder typeBuilder, ClassOrInterfaceDeclaration type) {
    if (type.getExtends() != null) {
      for (ClassOrInterfaceType extendedClass : type.getExtends()) {
        typeBuilder.addBaseClass(ObjcType.getTypeFor(extendedClass.getName()));
      }
    }
    if (type.getImplements() != null) {
      for (ClassOrInterfaceType implementedClass : type.getImplements()) {
        typeBuilder.addBaseClass(ObjcType.getTypeFor(implementedClass.getName()));
      }
    }    
    List<BodyDeclaration> members = type.getMembers();
    for (BodyDeclaration member : members) {
      if (member instanceof FieldDeclaration) {
        FieldDeclaration field = (FieldDeclaration) member;
        for (VariableDeclarator var : field.getVariables()) {
          ObjcField objcField = new ObjcField(field.getType(), var);
          typeBuilder.addField(objcField);
        }
      } else if (member instanceof MethodDeclaration) {
        MethodDeclaration method = (MethodDeclaration) member;
        typeBuilder.addMethod(new ObjcMethod(method));
      }
    }
  }
}
