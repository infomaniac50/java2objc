package com.googlecode.java2objc.objc;

import japa.parser.ast.expr.BinaryExpr.Operator;

public class ObjcOperator extends ObjcNode {

  private final String operator;
  
  public ObjcOperator(Operator operator) {
    String op;
    switch (operator) {
      case and:
        op = "&&";
        break;
      case binAnd:
        op = "&";
        break;
      case binOr:
        op = "|";
        break;
      case divide:
        op = "/";
        break;
      case equals:
        op = "==";
        break;
      case greater:
        op = ">";
        break;
      case greaterEquals:
        op = ">=";
        break;
      case less:
        op = "<";
        break;
      case lessEquals:
        op = "<=";
        break;
      case lShift:
        op = "<<";
        break;
      case minus:
        op = "-";
        break;
      case notEquals:
        op = "!=";
        break;
      case or:
        op = "||";
        break;
      case plus:
        op = "+";
        break;
      case remainder:
        op = "%";
        break;
      case rSignedShift:
        op = ">>";
        break;
      case rUnsignedShift:
        op = ">>>";
        break;
      case times:
        op = "*";
        break;
      case xor:
        op = "^";
        break;
      default:  
        throw new UnsupportedOperationException();        
    }
    this.operator = op;
  }
  
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.append(operator);
  }
}
