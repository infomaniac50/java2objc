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

import com.google.code.java2objc.testtypes.ClassWithStaticFinal;
import com.google.code.java2objc.testtypes.Player;
import com.google.code.java2objc.utils.ObjcTypeUtils;
import com.googlecode.java2objc.code.ObjcExpressionObjectCreation;
import com.googlecode.java2objc.code.ObjcType;
import com.googlecode.java2objc.main.Config;
import com.googlecode.java2objc.main.Main;
import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.ObjcVariableDeclarator;

/**
 * Functional test to verify the behavior for invocation of static final constants that
 * invoke a method call.
 *
 * @author Inderjeet Singh
 */
public class MethodCallInStaticFinalDeclarationTest {
  private static final String BASE_PATH = "src/test/java/" + "com/google/code/java2objc/";
  private static final String[] filePaths = {
      BASE_PATH + "testtypes/Player.java",
      BASE_PATH + "testtypes/ClassWithStaticFinal.java"
  };
  private ObjcType player;
  private ObjcType classWithStaticFinal;

  @Before
  public void setUp() throws Exception {
    Config config = new Config();
    String outputdir = System.getProperty("java.io.tmpdir");
    config.update("--outputdir=" + outputdir);
    Collection<String> javaFiles = Arrays.asList(filePaths);
    Main main = new Main(config, javaFiles);

    List<List<ObjcType>> objcTypes = main.convertFilesToObjcTypes();
    ObjcType type1 = objcTypes.get(0).get(0);
    ObjcType type2 = objcTypes.get(1).get(0);
    player = ObjcTypeUtils.findClassName(Player.class.getSimpleName(), type1, type2);
    classWithStaticFinal = ObjcTypeUtils.findClassName(ClassWithStaticFinal.class.getSimpleName(), type1, type2);
  }

  @Test
  public void testFieldsCorrectlyDefined() throws Exception {
    ObjcField nameField = player.getFieldWithName("name");
    Assert.assertEquals("NSString", nameField.getType().getName());
  }

  @Test
  public void testMethodCallInStaticFinalDeclaration() throws Exception {
    ObjcField player1Field = classWithStaticFinal.getFieldWithName("PLAYER1");
    Assert.assertEquals("Player", player1Field.getType().getName());
    ObjcVariableDeclarator declarator = player1Field.getVars().get(0);
    Assert.assertEquals("PLAYER1", declarator.getName());
    ObjcExpressionObjectCreation init = (ObjcExpressionObjectCreation) declarator.getInit();
    Assert.assertEquals("init", init.getMethodName());
    Assert.assertEquals(2, init.getNumParams());
  }
}
