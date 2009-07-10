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

import java.io.PrintWriter;
import java.util.List;

/**
 * A specialized class to write Objective C source-code. It provides convenient methods to indent
 * statements, terminate lines, or write comments.
 * 
 * @author Inderjeet Singh
 */
public final class SourceCodeWriter {
  
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
    indentLine();
    writer.append(line);
    if (!line.endsWith("\n")) {
      writer.append("\n");
    }
    return this;
  }
  
  public <T extends ObjcNode> SourceCodeWriter append(T node) {
    node.append(this);
    return this;
  }
  
  public SourceCodeWriter startNewLine() {
    return endLine().indentLine();
  }

  private SourceCodeWriter indentLine() {
    for (int i = 0; i < indentLevel; ++i) {
      writer.append(INDENT);
    }
    return this;
  }
    
  public SourceCodeWriter endStatement() {
    return append(";").endLine();
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

  public SourceCodeWriter appendToDo(String comment) {
    return appendComment("TODO: " + comment);
  }

  public SourceCodeWriter appendComment(String comment) {
    String[] lines = comment.split("\n");
    for (String line : lines) {
      writer.append("\\\\ ");
      writer.append(line);
      writer.append("\n");
    }
    return this;
  }

  public void close() {
    writer.close();
  }

  /**
   * Appends a list of nodes each separated by the specified separator.
   * 
   * @param <T>
   * @param nodes
   * @param separator
   */
  public <T extends ObjcNode> SourceCodeWriter append(List<T> nodes, String separator) {
    boolean first = true;
    for (T node : nodes) {
      if (first) {
        first = false;
      } else {
        writer.append(separator);
      }
      append(node);
    }
    return this;
  }
}
