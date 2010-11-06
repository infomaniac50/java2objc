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
package com.googlecode.java2objc.converters;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.googlecode.java2objc.code.ObjcType;
import com.googlecode.java2objc.main.Config;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcTypeRepository;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Converts a Java compilation unit into its equivalent Objective C classes
 * 
 * @author Inderjeet Singh
 */
public final class CompilationUnitConverter {

  private final CompilationUnit cu;
  private final Config config;
  private final File file;
  private final Properties mappings;

  public CompilationUnitConverter(Config config, CompilationUnit cu, File file,
      Properties mappings) {
    this.cu = cu;
    this.config = config;
    this.file = file;
    this.mappings = mappings;
  }

  public void generateSourceCode() throws IOException {
    CompilationContext context = new CompilationContext();
    ObjcTypeRepository repo = new ObjcTypeRepository(context);
    context.initRepo(repo);
    addExternalMappings(context);

    PackageDeclaration pkg = cu.getPakage();
    String pkgName = pkg != null ? pkg.getName().toString() : null;
    List<ObjcType> imports = toObjcImports(pkgName, repo, cu.getImports());
    context.init(imports);

    List<ObjcType> objcTypes = new LinkedList<ObjcType>();
    if (cu.getTypes() != null) {
      for (TypeDeclaration type : cu.getTypes()) {
        if (type instanceof ClassOrInterfaceDeclaration || type instanceof EnumDeclaration) {
          ObjcType objcType = context.getTypeConverter().to(type, pkgName);
          if (objcType != null) {
            objcTypes.add(objcType);
          }
        }
      }
    }

    writeSourceCodeForTypes(objcTypes);
  }

  private void addExternalMappings(CompilationContext context) {
    if (mappings != null) {
      ObjcTypeRepository repo = context.getTypeRepo();
      for (Object key : mappings.keySet()) {
        String fullName = key.toString();
        String className = getClassName(fullName);
        String pkgName = getPkgName(fullName);
        String[] value = mappings.get(key).toString().trim().split("[\\s,]+");
        String kind = "class";
        if (value.length > 1) {
          kind = value[1];
        }
        if ("class".equalsIgnoreCase(kind)) {
          ObjcType type = repo.getOrCreate(null, value[0], new ObjcType(context, value[0], false, true));
          repo.put(pkgName, className, type);
        } else if ("struct".equalsIgnoreCase(kind)) {
          ObjcType type = repo.getOrCreate(null, value[0], new ObjcType(context, value[0], false, false));
          repo.put(pkgName, className, type);
        } else if ("protocol".equalsIgnoreCase(kind)) {
          ObjcType type = repo.getOrCreate(null, value[0], new ObjcType(context, value[0], true, true));
          repo.put(pkgName, className, type);
        } else if ("function".equalsIgnoreCase(kind)) {
          String methodName = className;
          String functionName = getClassName(value[0]);
          className = getClassName(pkgName);
          pkgName = getPkgName(pkgName);
          ObjcType type = repo.getOrCreate(pkgName, className);
          type.addMethodMapping(methodName, functionName);
        }
      }
    }
  }

  private List<ObjcType> toObjcImports(String thisPkgName, ObjcTypeRepository repo,
      List<ImportDeclaration> importedTypes) {
    List<ObjcType> imports = new LinkedList<ObjcType>();
    if (importedTypes != null) {
      for (ImportDeclaration importedType : importedTypes) {
        String fullyQualifiedClassName = importedType.getName().toString();
        String pkgName = getPkgName(fullyQualifiedClassName);
        String className = getClassName(fullyQualifiedClassName);
        if (importedType.isAsterisk()) {
          pkgName = fullyQualifiedClassName;
          List<String> classNames = getClassNames(thisPkgName, pkgName);
          for (String name : classNames) {
            imports.add(repo.getOrCreate(pkgName, name));
          }
        } else if (importedType.isStatic()) {
          className = getClassName(pkgName);
          pkgName = getPkgName(pkgName);
          imports.add(repo.getOrCreate(pkgName, className));
        } else {
          imports.add(repo.getOrCreate(pkgName, className));
        }
      }
    }
    return imports;
  }

  private String getPkgName(String fullyQualifiedClassName) {
    int index = fullyQualifiedClassName.lastIndexOf('.');
    return index == -1 ? null : fullyQualifiedClassName.substring(0, index);
  }

  private String getClassName(String fullyQualifiedClassName) {
    int index = fullyQualifiedClassName.lastIndexOf('.');
    return index == -1 ? fullyQualifiedClassName : fullyQualifiedClassName.substring(index + 1);
  }

  private List<String> getClassNames(String thisPkgName, String importPkgName) {
    LinkedList<String> classNames = new LinkedList<String>();
    // try to find matching source code
    StringBuilder path = new StringBuilder("../");
    if (thisPkgName != null) {
      int index = thisPkgName.indexOf('.');
      while (index != -1) {
        path.append("../");
        index = thisPkgName.indexOf('.', index + 1);
      }
    }
    path.append(importPkgName.replace('.', '/'));
    File pkgFile = new File(file.getParentFile(), path.toString());
    if (pkgFile.exists() && pkgFile.isDirectory()) {
      String[] files = pkgFile.list();
      for (String file : files) {
        if (file.endsWith(".java")) {
          classNames.add(file.substring(0, file.length() - 5));
        }
      }
    }
    return classNames;
  }

  private void writeSourceCodeForTypes(List<ObjcType> types)
      throws IOException {
    SourceCodeWriter headerWriter = null;
    SourceCodeWriter implWriter = null;
    try {
      config.getOutputDir().mkdirs();
      File headerFile = new File(config.getOutputDir(), types.get(0).getHeaderFileName());
      headerWriter =
          new SourceCodeWriter(new PrintWriter(new FileOutputStream(headerFile)), true,
              config.getIndent());
      types.get(0).appendHeaderImports(headerWriter);
      File implFile = null;

      for (ObjcType currentType : types) {
        headerWriter.append(currentType);

        if (currentType.hasImpl()) {
          if (implFile == null) {
            implFile = new File(config.getOutputDir(), types.get(0).getImplFileName());
            implWriter =
                new SourceCodeWriter(new PrintWriter(new FileOutputStream(implFile)), false,
                    config.getIndent());
            types.get(0).appendBodyImports(implWriter);
          }
          implWriter.append(currentType);
        }
      }

      System.out.printf("Generated %s\n", headerFile.getAbsolutePath());
      if (implFile != null) {
        System.out.printf("Generated %s\n", implFile.getAbsolutePath());
      }
    } finally {
      if (headerWriter != null) headerWriter.close();
      if (implWriter != null) implWriter.close();
    }
  }
}
