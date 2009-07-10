package com.googlecode.java2objc.objc;

import japa.parser.ast.expr.Expression;

import java.util.LinkedList;
import java.util.List;

public class ExpressionConverter {

  public static List<ObjcExpression> to(List<Expression> expressions) {
    List<ObjcExpression> objcExpressions = new LinkedList<ObjcExpression>();
    if (expressions != null) {
      for (Expression expr : expressions) {
        objcExpressions.add(to(expr));
      }
    }
    return objcExpressions;
  }

  public static ObjcExpression to(Expression expression) {
    // TODO (inder): bring in real expression conversion
    return new ObjcExpression(expression.toString());
  }
}
