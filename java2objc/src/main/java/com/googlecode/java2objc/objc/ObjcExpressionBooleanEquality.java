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

/**
 * An Objective C boolean expression that is constructed by equating two expressions
 * 
 * @author Inderjeet Singh
 */
public final class ObjcExpressionBooleanEquality extends ObjcExpressionBoolean {

  private final ObjcExpression left;
  private final ObjcExpression right;

  public ObjcExpressionBooleanEquality(String expression) {
    super(expression);
    this.left = null;
    this.right = null;
  }
  
  public ObjcExpressionBooleanEquality(ObjcExpression left, ObjcExpression right) {
    this.left = left;
    this.right = right;
  }

  public ObjcExpression getLeft() {
    return left;
  }

  public ObjcExpression getRight() {
    return right;
  }
}
