/*
 * Created on Mar 19, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Understands utility methods related to JUnit tests.
 *
 * @author Alex Ruiz
 */
final class Tests {

  private static final String UNKNOWN = "unknown";

  private static Method testCaseName = nameMethodIn(TestCase.class);

  static String methodNameFrom(Test test) {
    if (test == null) return UNKNOWN;
    if (isJUnit4TestCaseFacade(test)) return trimClassNameFromMethodName(test.toString());
    if (test instanceof TestCase && testCaseName != null) return invokeNameMethod(testCaseName, test);
    return invokeNameMethod(nameMethodIn(test.getClass()), test);
  }

  private static boolean isJUnit4TestCaseFacade(Test test) {
    return test.getClass().getName().equals("junit.framework.JUnit4TestCaseFacade");
  }

  // Self-describing as of JUnit 4 (#38811). But trim "(ClassName)".
  private static String trimClassNameFromMethodName(String name) {
    if (!name.endsWith(")")) return name;
    return name.substring(0, name.lastIndexOf('('));
  }

  private static Method nameMethodIn(Class<?> type) {
    return methodInType(type, "getName", "name");
  }

  private static Method methodInType(Class<?> type, String name, String alternativeName) {
    Method m = methodInType(type, name);
    if (m != null) return m;
    return methodInType(type, alternativeName);
  }

  private static Method methodInType(Class<?> type, String name) {
    try {
      return type.getMethod(name, new Class[0]);
    } catch (Exception e) {
      return null;
    }
  }

  private static String invokeNameMethod(Method m, Object target) {
    if (m == null || m.getReturnType() != String.class) return UNKNOWN;
    try {
      return (String) m.invoke(target, new Object[0]);
    } catch (Exception e) {
      return UNKNOWN;
    }
  }
}
