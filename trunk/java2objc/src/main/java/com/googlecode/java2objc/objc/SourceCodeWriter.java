package com.googlecode.java2objc.objc;

import java.io.PrintWriter;


public class SourceCodeWriter {
  
  private static final String INDENT = "  ";
  
  private final PrintWriter writer;
  private boolean writingHeaderFile;  
  private int indentLevel;
  
  public SourceCodeWriter(PrintWriter writer, boolean writingHeaderFile) {
    this.writer = writer;
    this.writingHeaderFile = writingHeaderFile;
    this.indentLevel = 0;
  }

  public boolean isWritingHeaderFile() {
    return writingHeaderFile;
  }

  public void setWritingHeaderFile(boolean writingHeaderFile) {
    this.writingHeaderFile = writingHeaderFile;
  }

  public SourceCodeWriter indent() {
    ++indentLevel;
    return this;
  }
  
  public SourceCodeWriter unIndent() {
    --indentLevel;
    return this;
  }

  public SourceCodeWriter append(String str) {
    writer.append(str);
    return this;
  }

  public SourceCodeWriter append(char ch) {
    writer.append(ch);
    return this;
  }

  public SourceCodeWriter appendLine(String line) {
    for (int i = 0; i < indentLevel; ++i) {
      writer.append(INDENT);
    }
    writer.append(line);
    writer.append("\n");
    return this;
  }
  
  public <T extends ObjcNode> SourceCodeWriter append(T node) {
    node.append(this);
    return this;
  }
  
  public SourceCodeWriter startLine() {
    for (int i = 0; i < indentLevel; ++i) {
      writer.append(INDENT);
    }
    return this;
  }
    
  public SourceCodeWriter endLine() {
    return append('\n');
  }

  public <T extends ObjcNode> SourceCodeWriter appendLine(T node) {
    for (int i = 0; i < indentLevel; ++i) {
      writer.append(INDENT);
    }
    node.append(this);
    writer.append("\n");
    return this;
  }

  public SourceCodeWriter appendBlankLine() {
    return endLine();
  }

  public void close() {
    writer.close();
  }
}
