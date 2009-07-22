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

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.googlecode.java2objc.main.Config;

/**
 * Converts a Java compilation unit into its equivalent Objective C classes
 * 
 * @author Inderjeet Singh
 */
public final class CompilationUnitConverter {

  private final CompilationUnit cu;
  private final Config config;

  public CompilationUnitConverter(Config config, CompilationUnit cu) {
    this.cu = cu;
    this.config = config;
  }
  
  public void generateSourceCode() throws IOException {
    Collection<ObjcType> objcTypes = new LinkedList<ObjcType>();
    PackageDeclaration pkg = cu.getPakage();
    String pkgName = pkg.getName().getName();
    CompilationContext context = new CompilationContext(pkgName);
    ObjcTypeRepository repo = new ObjcTypeRepository(context);
    context.initRepo(repo);
    Set<ObjcType> imports = toObjcImports(repo, cu.getImports());
    context.init(imports);
    repo.init();
    if (cu.getTypes() != null) {
      for (TypeDeclaration type : cu.getTypes()) {
        if (type instanceof ClassOrInterfaceDeclaration) {
          ObjcType objcType = context.getTypeConverter().to((ClassOrInterfaceDeclaration)type);
          if (objcType != null) {
            objcTypes.add(objcType);
          }
        }
      }
    }
    for (ObjcType currentType : objcTypes) {
      writeSourceCodeForType(currentType);
    }
  }

  private static Set<ObjcType> toObjcImports(ObjcTypeRepository repo, List<ImportDeclaration> importedTypes) {
    Set<ObjcType> imports = new HashSet<ObjcType>();
    if (importedTypes != null) {
      for (ImportDeclaration importedType : importedTypes) {
        String fullyQualifiedClassName = importedType.getName().getName();
        String pkgName = JavaUtils.getPkgName(fullyQualifiedClassName);
        String className = JavaUtils.getClassName(fullyQualifiedClassName);
        imports.add(repo.getTypeFor(pkgName, className));
      }
    }
    return imports;
  }

  private void writeSourceCodeForType(ObjcType currentType)
      throws IOException {
    SourceCodeWriter headerWriter = null;
    SourceCodeWriter implWriter = null;
    try {
      File headerFile = new File(config.getOutputDir(), currentType.getHeaderFileName());
      headerWriter = new SourceCodeWriter(new PrintWriter(new FileOutputStream(headerFile)), true);
      headerWriter.append(currentType);
      System.out.printf("Generated %s\n", headerFile.getAbsolutePath());
      if (!currentType.isInterface()) {
        File implFile = new File(config.getOutputDir(), currentType.getImplFileName());
        implWriter = new SourceCodeWriter(new PrintWriter(new FileOutputStream(implFile)), false);
        implWriter.append(currentType);
        System.out.printf("Generated %s\n", implFile.getAbsolutePath());
      }
    } finally {
      if (headerWriter != null) headerWriter.close();
      if (implWriter != null) implWriter.close();
    }
  }  
}
