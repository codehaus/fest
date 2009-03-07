/*
 * Created on Nov 22, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.runner;

import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Strings.concat;

import java.lang.reflect.Method;

/**
 * Understands utilities methods that display objects as readable <code>String</code>s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class Formatter {

  static String format(Class<?> type, Method method) {
    return concat(type.getName(), ".", method.getName(), format(method.getParameterTypes()));
    
  }
  
  private static String format(Class<?>[] parameterTypes) {
    if (isEmpty(parameterTypes)) return "";
    StringBuilder b = new StringBuilder("(");
    for (int i = 0; i < parameterTypes.length; i++) {
      b.append(parameterTypes[i].getName());
      if (i < parameterTypes.length - 1) b.append(", ");
    }
    b.append(")");
    return b.toString();
  }
  
  private Formatter() {}
}
