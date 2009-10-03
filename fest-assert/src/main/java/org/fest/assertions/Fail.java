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
import static org.fest.assertions.ErrorMessages.*;
import static org.fest.assertions.Formatting.*;
import static org.fest.util.Arrays.array;
import static org.fest.util.Objects.areEqual;

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
   * @param description the description of the failed assertion. It can be <code>null</code>.
   * @param realCause cause of the error.
   */
  public static void fail(String description, Throwable realCause) {
    AssertionError error = new AssertionError(description);
    error.initCause(realCause);
    throw error;
  }

  static void failIfEqual(Description description, Object first, Object second) {
    if (areEqual(first, second)) fail(messageForEqual(description, first, second));
  }

  static void failIfNotEqual(Description description, Object actual, Object expected) {
    if (areEqual(actual, expected)) return;
    AssertionError comparisonFailure = comparisonFailure(valueOf(description), expected, actual);
    if (comparisonFailure != null) throw comparisonFailure;
    fail(messageForNotEqual(description, actual, expected));
  }

  static void failIfNull(Description description, Object o) {
    if (o != null) return;
    fail(description, array("expecting a non-null object, but it was null"));
  }

  static void failIfNotNull(Description description, Object o) {
    if (o == null) return;
    fail(description, array(inBrackets(o), " should be null"));
  }

  static void failIfSame(Description description, Object first, Object second) {
    if (first != second) return;
    fail(description, array("given objects are same:", inBrackets(first)));
  }

  static void failIfNotSame(Description description, Object first, Object second) {
    if (first == second) return;
    fail(description, array("expected same instance but found:", inBrackets(first), " and:", inBrackets(second)));
  }

  private static AssertionError fail(Description description, Object[] message) {
    return fail(messageFrom(description, message));
  }

  /**
   * Fails with the given message.
   * @param message error message.
   * @return the thrown <code>AssertionError</code>.
   * @throws AssertionError with the given message.
   */
  public static AssertionError fail(String message) {
    throw new AssertionError(message);
  }

  private Fail() {}
}
