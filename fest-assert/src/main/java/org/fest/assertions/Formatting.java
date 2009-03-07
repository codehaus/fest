/*
 * Created on Sep 14, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.util.Strings.*;

import java.awt.Dimension;
import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.fest.util.Arrays;
import org.fest.util.Collections;
import org.fest.util.Maps;

/**
 * Understands utility methods related to formatting.
 *
 * @author Yvonne Wang
 */
final class Formatting {

  private static final String EMPTY_MESSAGE = "";

  static String format(String message) {
    if (isEmpty(message)) return EMPTY_MESSAGE;
    return concat("[", message, "] ");
  }

  static String inBrackets(Object o) {
    if (isOneDimensionalArray(o)) return doBracketAround(Arrays.format(o));
    if (o instanceof Class<?>) return doBracketAround((Class<?>)o);
    if (o instanceof Collection<?>) return doBracketAround((Collection<?>)o);
    if (o instanceof Map<?, ?>) return doBracketAround((Map<?, ?>)o);
    if (o instanceof File) return doBracketAround((File)o);
    if (o instanceof Dimension) return doBracketAround((Dimension)o);
    return doBracketAround(quote(o));
  }

  private static boolean isOneDimensionalArray(Object o) {
    return o != null && o.getClass().isArray() && !o.getClass().getComponentType().isArray();
  }

  private static String doBracketAround(Class<?> c) {
    return doBracketAround(c.getName());
  }

  private static String doBracketAround(Collection<?> c) {
    return doBracketAround(Collections.format(c));
  }

  private static String doBracketAround(Map<?, ?> m) {
    return doBracketAround(Maps.format(m));
  }

  private static String doBracketAround(File f) {
    return doBracketAround(f.getAbsolutePath());
  }

  private static String doBracketAround(Dimension d) {
    return doBracketAround(concat("(", d.width, ", ", d.height, ")"));
  }

  private static String doBracketAround(Object o) {
    return concat("<", o, ">");
  }

  private Formatting() {}

}
