package com.googlecode.java2objc.objc;

import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.type.Type;

public class ObjcField extends ObjcNode {

  private final ObjcType type;
  private final String name;
  public ObjcField(Type type, VariableDeclarator var) {
    this.type = ObjcType.getTypeFor(type);
    this.name = var.getId().getName();
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.startLine().append(type.getPointerTypeName()).append(" ");
    writer.append(name).append(";").endLine();
  }
}
