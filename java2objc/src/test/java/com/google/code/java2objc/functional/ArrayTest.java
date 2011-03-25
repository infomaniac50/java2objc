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
package com.google.code.java2objc.functional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.code.java2objc.testtypes.ClassWithArray;
import com.google.code.java2objc.utils.ObjcTypeUtils;
import com.googlecode.java2objc.code.ObjcExpressionArrayAccess;
import com.googlecode.java2objc.code.ObjcExpressionAssign;
import com.googlecode.java2objc.code.ObjcMethod;
import com.googlecode.java2objc.code.ObjcStatement;
import com.googlecode.java2objc.code.ObjcStatementExpression;
import com.googlecode.java2objc.code.ObjcStatementFor;
import com.googlecode.java2objc.code.ObjcStatementForeach;
import com.googlecode.java2objc.code.ObjcType;
import com.googlecode.java2objc.main.Config;
import com.googlecode.java2objc.main.Main;
import com.googlecode.java2objc.objc.ObjcField;

/**
 * Functional test for Arrays
 *
 * @author Inderjeet Singh
 */
public class ArrayTest {
  private static final String BASE_PATH = "src/test/java/" + "com/google/code/java2objc/";
  private static final String[] filePaths = {
      BASE_PATH + "testtypes/ClassWithArray.java",
  };
  private ObjcType classWithArray;
  private List<ObjcStatement> statements;

  @Before
  public void setUp() throws Exception {
    Config config = new Config();
    String outputdir = System.getProperty("java.io.tmpdir");
    config.update("--outputdir=" + outputdir);
    Collection<String> javaFiles = Arrays.asList(filePaths);
    Main main = new Main(config, javaFiles);

    List<List<ObjcType>> objcTypes = main.convertFilesToObjcTypes();
    ObjcType type1 = objcTypes.get(0).get(0);
    classWithArray = ObjcTypeUtils.findClassName(ClassWithArray.class.getSimpleName(), type1);
    ObjcMethod visitArrayMethod = classWithArray.getMethodWithName("visitArray");
    this.statements = visitArrayMethod.getMethodBody().getStatements();
  }

  @Test
  public void testArrayDefinition() throws Exception {
    ObjcField nameField = classWithArray.getFieldWithName("array");
    Assert.assertEquals("NSArray", nameField.getType().getName());
  }

  @Test
  public void testForEachOnArray() throws Exception {
    ObjcStatementForeach foreach = ObjcTypeUtils.findStatementOfType(statements, ObjcStatementForeach.class);
    Assert.assertEquals("s", foreach.getVarName());
    Assert.assertEquals("NSString", foreach.getVarType().getName());
    Assert.assertEquals(1, foreach.getBodyAsBlock().getStatements().size());
  }

  @Test
  public void testForLoopOnArray() throws Exception {
    ObjcTypeUtils.print(classWithArray);
    ObjcStatementFor forloop = ObjcTypeUtils.findStatementOfType(statements, ObjcStatementFor.class);
    Assert.assertEquals("i < [array count]", forloop.getCompare().toString());
    List<ObjcStatement> forStmts = forloop.getBodyAsBlock().getStatements();
    ObjcExpressionAssign s = (ObjcExpressionAssign)((ObjcStatementExpression)forStmts.get(0)).getExpression();
    Assert.assertEquals("NSArray", s.getType().getName());
    ObjcExpressionArrayAccess arrayAccess = (ObjcExpressionArrayAccess) s.getTarget();
    Assert.assertEquals("array[i]", arrayAccess.toString());

    // TODO(inder): Ideally, we should be asserting the following for array RHS access
    // For statement 1, ObjcExpressionArrayAccess arrayAccess = (ObjcExpressionArrayAccess) s.getVar().getVars().get(0).getInit();
    // Assert.assertEquals("[array objectAtIndex: i]", arrayAccess.toString());
  }
}
