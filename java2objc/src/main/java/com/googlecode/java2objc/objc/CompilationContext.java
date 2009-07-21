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

import java.util.Set;

/**
 * class to keep track of current method and type being navigated in the source-code
 * 
 * @author Inderjeet Singh
 */
public class CompilationContext {

  private final TypeConverter typeConverter;
  private final MethodConverter methodConverter;
  private final StatementConverter statementConverter;
  private final ExpressionConverter expressionConverter;
  private ObjcMethod currentMethod;
  private ObjcType currentType;

  public CompilationContext(String pkgName, Set<ObjcType> imports) {
    this.typeConverter = new TypeConverter(this, pkgName, imports);
    this.methodConverter = new MethodConverter(this);
    this.statementConverter = new StatementConverter(this);
    this.expressionConverter = new ExpressionConverter(this);
  }

  /**
   * @return the typeConverter
   */
  public TypeConverter getTypeConverter() {
    return typeConverter;
  }

  public MethodConverter getMethodConverter() {
    return methodConverter;
  }

  /**
   * @return the statementConverter
   */
  public StatementConverter getStatementConverter() {
    return statementConverter;
  }
  /**
   * @return the expressionConverter
   */
  public ExpressionConverter getExpressionConverter() {
    return expressionConverter;
  }

  /**
   * @param method
   */
  public void setCurentMethod(ObjcMethod method) {
    this.currentMethod = method;
  }
  
  public ObjcMethod getCurrentMethod() {
    return currentMethod;
  }

  public void setCurrentType(ObjcType type) {
    this.currentType = type;
  }

  public ObjcType getCurrentType() {
    return currentType;
  }
}
