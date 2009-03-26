/*
 * Created on Mar 25, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.assertions;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static org.fest.util.Strings.concat;

/**
 * Understands a color.
 *
 * @author Alex Ruiz
 */
final class RGBColor {

  private final int red;
  private final int green;
  private final int blue;

  RGBColor(int rgb) {
    this(redIn(rgb), greenIn(rgb), blueIn(rgb));
  }

  RGBColor(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  private static int redIn(int rgb) {
    return (rgb >> 16) & 0xFF;
  }

  private static int greenIn(int rgb) {
    return (rgb >> 8) & 0xFF;
  }

  private static int blueIn(int rgb) {
    return (rgb >> 0) & 0xFF;
  }

  int red() { return red; }
  int green() { return green; }
  int blue() { return blue; }

  boolean isEqualTo(RGBColor color, int threshold) {
    if (abs(red - color.red) > threshold) return false;
    if (abs(green - color.green) > threshold) return false;
    return abs(blue - color.blue) <= threshold;
  }

  @Override public String toString() {
    return concat(
        "color[",
        "r=", valueOf(red), ",",
        "g=", valueOf(green), ",",
        "b=", valueOf(blue), "]"
        );
  }
}
