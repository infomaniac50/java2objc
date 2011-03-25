/*
 * Copyright (C) 2011 Inderjeet Singh
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
package com.google.code.java2objc.utils;

import java.util.List;

import junit.framework.Assert;

import com.googlecode.java2objc.code.ObjcStatement;
import com.googlecode.java2objc.code.ObjcType;

public final class ObjcTypeUtils {

  public static ObjcType findClassName(String name, ObjcType... types) {
    for (ObjcType type : types) {
      if (type.getName().equals(name)) {
        return type;
      }
    }
    Assert.fail();
    return null;
  }

  public static <T extends ObjcStatement> T findStatementOfType(
      List<ObjcStatement> stmts, Class<T> type) {
    return findStatementOfType(stmts, type, 0);
  }

  @SuppressWarnings("unchecked")
  public static <T extends ObjcStatement> T findStatementOfType(
      List<ObjcStatement> stmts, Class<T> type, int indexOfStatement) {
    for (ObjcStatement stmt : stmts) {
      if (type.isInstance(stmt)) {
        if (indexOfStatement == 0) {
          return (T) stmt;
        }
        --indexOfStatement;
      }
    }
    return null;
  }

  public static void print(ObjcType objcType) {
    System.err.print(objcType.toString());
  }

  private ObjcTypeUtils() {}
}
