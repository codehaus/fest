/*
 * Created on Mar 19, 2007
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
package org.fest.assertions;

import static org.fest.assertions.ComparisonFailureFactory.comparisonFailure;
import static org.fest.assertions.Formatting.*;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

/**
 * Understands failure methods.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Fail {

  /**
   * Fails with no message.
   * @throws AssertionError without any message.
   */
  public static void fail() {
    fail(null);
  }

  /**
   * Throws an {@link AssertionError} with the given message an with the <code>{@link Throwable}</code> that caused the
   * failure.
   * @param message error message.
   * @param realCause cause of the error.
   */
  public static void fail(String message, Throwable realCause) {
    AssertionError error = new AssertionError(message);
    error.initCause(realCause);
    throw error;
  }

  static void failIfEqual(String message, Object first, Object second) {
    if (areEqual(first, second)) fail(errorMessageIfEqual(message, first, second));
  }

  static void failIfNotEqual(String message, Object actual, Object expected) {
    if (areEqual(actual, expected)) return;
    AssertionError comparisonFailure = comparisonFailure(message, expected, actual);
    if (comparisonFailure != null) throw comparisonFailure;
    fail(errorMessageIfNotEqual(message, actual, expected));
  }

  static void failIfNull(String message, Object o) {
    if (o == null) fail(concat(format(message), "expecting a non-null object, but it was null"));
  }

  static void failIfNotNull(String message, Object o) {
    if (o != null) fail(concat(format(message), inBrackets(o), " should be null"));
  }

  static void failIfSame(String message, Object first, Object second) {
    if (first == second) fail(concat(format(message), "given objects are same:", inBrackets(first)));
  }

  static void failIfNotSame(String message, Object first, Object second) {
    if (first != second)
      fail(concat(format(message), "expected same instance but found:", inBrackets(first), " and:", inBrackets(second)));
  }

  /**
   * Fails with the given message.
   * @param message error message.
   * @throws AssertionError with the given message.
   */
  public static void fail(String message) {
    throw new AssertionError(message);
  }

  static String errorMessageIfNotEqual(String message, Object actual, Object expected) {
    return concat(format(message), errorMessageIfNotEqual(actual, expected));
  }

  static String errorMessageIfNotEqual(Object actual, Object expected) {
    return concat("expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }

  static String errorMessageIfEqual(String message, Object actual, Object o) {
    return concat(format(message), errorMessageIfEqual(actual, o));
  }

  static String errorMessageIfEqual(Object actual, Object o) {
    return comparisonFailed(actual, " should not be equal to:", o);
  }

  static String errorMessageIfNotGreaterThan(String message, Object actual, Object value) {
    return concat(format(message), errorMessageIfNotGreaterThan(actual, value));
  }

  static String errorMessageIfNotGreaterThan(Object actual, Object value) {
    return comparisonFailed(actual, " should be greater than:", value);
  }

  static String errorMessageIfNotGreaterThanOrEqualTo(String message, Object actual, Object value) {
    return concat(format(message), errorMessageIfNotGreaterThanOrEqualTo(actual, value));
  }

  static String errorMessageIfNotGreaterThanOrEqualTo(Object actual, Object value) {
    return comparisonFailed(actual, " should be greater than or equal to:", value);
  }

  static String errorMessageIfNotLessThan(String message, Object actual, Object value) {
    return concat(format(message), errorMessageIfNotLessThan(actual, value));
  }
  
  static String errorMessageIfNotLessThan(Object actual, Object value) {
    return comparisonFailed(actual, " should be less than:", value);
  }

  static String errorMessageIfNotLessThanOrEqualTo(String message, Object actual, Object value) {
    return concat(format(message), errorMessageIfNotLessThanOrEqualTo(actual, value));
  }

  static String errorMessageIfNotLessThanOrEqualTo(Object actual, Object value) {
    return comparisonFailed(actual, " should be less than or equal to:", value);
  }

  private static String comparisonFailed(Object actual, String reason, Object expected) {
    return comparisonFailed(null, actual, reason, expected);
  }

  private static String comparisonFailed(String message, Object actual, String reason, Object expected) {
    return concat(format(message), "actual value:", inBrackets(actual), reason, inBrackets(expected));
  }
  
  private Fail() {}
}
