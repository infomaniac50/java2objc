package com.googlecode.java2objc.objc;

import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.type.Type;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.java2objc.util.Preconditions;

public class ObjcMethodCallExpression extends ObjcExpression {

  private final String targetObjectName;
  private final String methodName;
  private final List<ObjcType> argTypes;
  private final List<ObjcExpression> args;
  private final int numParams;

  public ObjcMethodCallExpression(String targetObjectName, MethodCallExpr expr) {
    this.targetObjectName = targetObjectName;
    methodName = expr.getName(); // Find out the right method name
    argTypes = new LinkedList<ObjcType>();
    List<Type> typeArgs = expr.getTypeArgs();
    if (typeArgs != null) {
      for (Type argType : typeArgs) {
        argTypes.add(ObjcType.getTypeFor(argType));
      }
    }    
    args = new LinkedList<ObjcExpression>();
    List<Expression> actualArgs = expr.getArgs();
    if (actualArgs != null) {
      for (Expression arg : actualArgs) {
        args.add(new ObjcExpression(arg.toString()));
      }
    }
    Preconditions.assertEquals(args.size(), argTypes.size());
    numParams = args.size();
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("[").append(targetObjectName).append(" ");
    writer.append(methodName);
    if (numParams > 0) {
      // Write first argument
      writer.append(":").append(args.get(0));
    }
    // Write remaining params
    for (int i = 1; i < numParams; ++i) {
      // TODO(inder): write formal parameter names as well
      writer.append(":").append(args.get(i));
    }
    writer.append("]");
  }
}
