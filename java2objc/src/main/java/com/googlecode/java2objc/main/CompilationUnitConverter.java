package com.googlecode.java2objc.main;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
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

import com.googlecode.java2objc.builders.TypeDeclarationToObjcTypeConverter;
import com.googlecode.java2objc.objc.ObjcType;
import com.googlecode.java2objc.objc.SourceCodeWriter;

public class CompilationUnitConverter {

  private final CompilationUnit cu;
  private final Config config;

  public CompilationUnitConverter(Config config, CompilationUnit cu) {
    this.cu = cu;
    this.config = config;
  }
  
  public void generateSourceCode() throws IOException {
    Collection<ObjcType> objcTypes = new LinkedList<ObjcType>();
    Set<ObjcType> imports = toObjcImports(cu.getImports());
    TypeDeclarationToObjcTypeConverter objcTypeConverter = new TypeDeclarationToObjcTypeConverter(imports);    
    if (cu.getTypes() != null) {
      for (TypeDeclaration type : cu.getTypes()) {
        if (type instanceof ClassOrInterfaceDeclaration) {
          ObjcType objcType = objcTypeConverter.createObjcType((ClassOrInterfaceDeclaration)type);
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

  private static Set<ObjcType> toObjcImports(List<ImportDeclaration> importedTypes) {
    Set<ObjcType> imports = new HashSet<ObjcType>();
    if (importedTypes != null) {
      for (ImportDeclaration importedType : importedTypes) {      
        imports.add(ObjcType.getTypeFor(importedType.getName().getName()));
      }
    }
    return imports;
  }

  private void writeSourceCodeForType(ObjcType currentType)
      throws IOException {
    SourceCodeWriter headerWriter = null;
    SourceCodeWriter implWriter = null;
    try {
      File headerFile = new File(config.outputDir, currentType.getHeaderFileName());
      headerWriter = new SourceCodeWriter(new PrintWriter(new FileOutputStream(headerFile)), true);
      headerWriter.append(currentType);
      File implFile = new File(config.outputDir, currentType.getImplFileName());
      implWriter = new SourceCodeWriter(new PrintWriter(new FileOutputStream(implFile)), false);
      implWriter.append(currentType);
    } finally {
      if (headerWriter != null) headerWriter.close();
      if (implWriter != null) implWriter.close();
    }
  }  
}
