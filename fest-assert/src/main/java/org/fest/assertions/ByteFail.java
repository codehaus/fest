/*
 * Created on Oct 2, 2009
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

import static org.fest.assertions.ErrorMessages.*;
import static org.fest.assertions.Fail.fail;

/**
 * Understands failure methods for <code>byte</code>s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author David DIDIER
 */
final class ByteFail {

  private static final byte ZERO = (byte)0;

  /**
   * Fails if the given <code>byte</code> is not positive.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @throws AssertionError if the given <code>byte</code>s is zero or negative.
   */
  static void failIfNotPositive(Description description, byte actual) {
    failIfNotGreaterThan(description, actual, ZERO);
  }

  /**
   * Fails if the given <code>byte</code> is not negative.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @throws AssertionError if the given <code>byte</code>s is zero or positive.
   */
  static void failIfNotNegative(Description description, byte actual) {
    failIfNotLessThan(description, actual, ZERO);
  }

  /**
   * Fails if the given <code>byte</code> is not equal to zero.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @throws AssertionError if the given <code>byte</code>s is not equal to zero.
   */
  static void failIfNotZero(Description description, byte actual) {
    failIfNotEqual(description, actual, ZERO);
  }

  /**
   * Fails if the given <code>byte</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>byte</code>s are equal.
   */
  static void failIfEqual(Description description, byte actual, byte other) {
    if (actual == other) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>byte</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>byte</code>s are not equal.
   */
  static void failIfNotEqual(Description description, byte actual, byte expected) {
    if (actual != expected) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the actual value is not greater than the other value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the actual value is not greater than the other value.
   */
  static void failIfNotGreaterThan(Description description, byte actual, byte other) {
    if (actual <= other) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the actual value is not greater than or equal to the other value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the actual value is not greater than or equal to the other value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, byte actual, byte other) {
    if (actual < other) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the actual value is not less than the other value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the actual value is not less than the other value.
   */
  static void failIfNotLessThan(Description description, byte actual, byte other) {
    if (actual >= other) fail(messageForNotLessThan(description, actual, other));
  }

  /**
   * Fails if the actual value is not less than or equal to the other value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the actual value is not less than or equal to the other value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, byte actual, byte other) {
    if (actual > other) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  private ByteFail() {}
}
