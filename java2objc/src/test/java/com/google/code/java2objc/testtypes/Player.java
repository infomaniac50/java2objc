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
package com.google.code.java2objc.testtypes;

public class Player {
  private final String name;
  private final int topScore;

  public Player(String name1, int topScore1) {
    this.name = name1;
    this.topScore = topScore1;
  }

  public String getName() {
    return name;
  }

  public int getTopScore() {
    return topScore;
  }
}
