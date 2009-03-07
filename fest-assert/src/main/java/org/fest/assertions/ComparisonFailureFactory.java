/*
 * Created on Oct 10, 2007
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

/**
 * Understands creation of JUnit's <code>ComparisonFailure</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ComparisonFailureFactory {

  private static ConstructorInvoker constructorInvoker = new ConstructorInvoker();

  static void constructorInvoker(ConstructorInvoker newConstructorInvoker) {
    constructorInvoker = newConstructorInvoker;
  }

  /**
   * Creates a new instance of JUnit's <code>ComparisonFailure</code> only if JUnit 4+ is in the classpath and both the
   * expected and actual values are <code>String</code>s.
   * @param message the identifying message or <code>null</code>.
   * @param expected the expected value.
   * @param actual the actual value.
   * @return the created <code>ComparisonFailure</code>, or <code>null</code> if JUnit 4+ is not in the classpath or if
   * either the expected or actual values is not a <code>String</code>.
   */
  public static AssertionError comparisonFailure(String message, Object expected, Object actual) {
    if (isNotString(expected) || isNotString(actual)) return null;
    try {
      return newComparisonFailure(clean(message), (String)expected, (String)actual);
    } catch (Exception e) {
      return null;
    }
  }

  private static String clean(String message) {
    return message == null ? "" : message;
  }

  private static boolean isNotString(Object o) { return !(o instanceof String); }

  private static AssertionError newComparisonFailure(String message, String expected, String actual) throws Exception {
    final String className = "org.junit.ComparisonFailure";
    Class<?>[] parameterTypes = new Class<?>[] { String.class, String.class, String.class };
    Object[] parameterValues = new Object[] { message, expected, actual };
    Object o = constructorInvoker.newInstance(className, parameterTypes, parameterValues);
    if (o instanceof AssertionError) return (AssertionError)o;
    return null;
  }

  private ComparisonFailureFactory() {}
}
