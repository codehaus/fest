/*
 * Created on Jan 25, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ErrorMessages.*;
import static org.fest.assertions.Fail.fail;

/**
 * Understands failure methods for primitive types.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author David DIDIER
 */
public final class PrimitiveFail {

  /**
   * Fails if the given <code>char</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>char</code>s are equal.
   */
  static void failIfEqual(Description description, char actual, char other) {
    if (actual == other) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>short</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>short</code>s are equal.
   */
  static void failIfEqual(Description description, short actual, short other) {
    if (actual == other) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>int</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>int</code>s are equal.
   */
  static void failIfEqual(Description description, int actual, int other) {
    if (actual == other) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>long</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>long</code>s are equal.
   */
  static void failIfEqual(Description description, long actual, long other) {
    if (actual == other) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>float</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>float</code>s are equal.
   */
  static void failIfEqual(Description description, float actual, float other) {
    if (Float.compare(actual, other) == 0) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>double</code>s are equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>double</code>s are equal.
   */
  static void failIfEqual(Description description, double actual, double other) {
    if (Double.compare(actual, other) == 0) fail(messageForEqual(description, actual, other));
  }

  /**
   * Fails if the given <code>char</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>char</code>s are not equal.
   */
  static void failIfNotEqual(Description description, char actual, char expected) {
    if (actual != expected) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the given <code>short</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>short</code>s are not equal.
   */
  static void failIfNotEqual(Description description, short actual, short expected) {
    if (actual != expected) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the given <code>int</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>int</code>s are not equal.
   */
  static void failIfNotEqual(Description description, int actual, int expected) {
    if (actual != expected) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the given <code>long</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>long</code>s are not equal.
   */
  static void failIfNotEqual(Description description, long actual, long expected) {
    if (actual != expected) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the given <code>float</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>float</code>s are not equal.
   */
  static void failIfNotEqual(Description description, float actual, float expected) {
    if (Float.compare(actual, expected) != 0) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the given <code>double</code>s are not equal.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>double</code>s are not equal.
   */
  static void failIfNotEqual(Description description, double actual, double expected) {
    if (Double.compare(actual, expected) != 0) fail(messageForNotEqual(description, actual, expected));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(Description description, char actual, char other) {
    if (actual >= other) fail(messageForNotLessThan(description, actual, other));
  }


  /**
   * Fails if the first value is not less than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(Description description, short actual, short other) {
    if (actual >= other) fail(messageForNotLessThan(description, actual, other));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(Description description, int actual, int other) {
    if (actual >= other) fail(messageForNotLessThan(description, actual, other));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(Description description, long actual, long other) {
    if (actual >= other) fail(messageForNotLessThan(description, actual, other));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(Description description, float actual, float other) {
    if (Float.compare(actual, other) >= 0) fail(messageForNotLessThan(description, actual, other));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(Description description, double actual, double other) {
    if (Double.compare(actual, other) >= 0) fail(messageForNotLessThan(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(Description description, char actual, char other) {
    if (actual <= other) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(Description description, short actual, short other) {
    if (actual <= other) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(Description description, int actual, int other) {
    if (actual <= other) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(Description description, long actual, long other) {
    if (actual <= other) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(Description description, float actual, float other) {
    if (Float.compare(actual, other) <= 0) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(Description description, double actual, double other) {
    if (Double.compare(actual, other) <= 0) fail(messageForNotGreaterThan(description, actual, other));
  }

  /**
   * Fails if the first value is not less than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the second value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, char actual, char other) {
    if (actual > other) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not less than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the second value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, short actual, short other) {
    if (actual > other) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not less than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the second value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, int actual, int other) {
    if (actual > other) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not less than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the second value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, long actual, long other) {
    if (actual > other) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not less than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the second value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, float actual, float other) {
    if (Float.compare(actual, other) > 0) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not less than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the second value.
   */
  static void failIfNotLessThanOrEqualTo(Description description, double actual, double other) {
    if (Double.compare(actual, other) > 0) fail(messageForNotLessThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the second value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, char actual, char other) {
    if (actual < other) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }


  /**
   * Fails if the first value is not greater than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the second value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, short actual, short other) {
    if (actual < other) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the second value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, int actual, int other) {
    if (actual < other) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the second value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, long actual, long other) {
    if (actual < other) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the second value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, float actual, float other) {
    if (Float.compare(actual, other) < 0) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }

  /**
   * Fails if the first value is not greater than or equal to the second value.
   * @param description the description of the actual value. It can be <code>null</code>.
   * @param actual the actual value.
   * @param other the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the second value.
   */
  static void failIfNotGreaterThanOrEqualTo(Description description, double actual, double other) {
    if (Double.compare(actual, other) < 0) fail(messageForNotGreaterThanOrEqualTo(description, actual, other));
  }

  private PrimitiveFail() {}
}
