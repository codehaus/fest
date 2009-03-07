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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Fail.*;

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
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>char</code>s are equal.
   */
  static void failIfEqual(String message, char actual, char value) {
    if (actual == value) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>byte</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>byte</code>s are equal.
   */
  static void failIfEqual(String message, byte actual, byte value) {
    if (actual == value) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>short</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>short</code>s are equal.
   */
  static void failIfEqual(String message, short actual, short value) {
    if (actual == value) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>int</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>int</code>s are equal.
   */
  static void failIfEqual(String message, int actual, int value) {
    if (actual == value) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>long</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>long</code>s are equal.
   */
  static void failIfEqual(String message, long actual, long value) {
    if (actual == value) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>float</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>float</code>s are equal.
   */
  static void failIfEqual(String message, float actual, float value) {
    if (Float.compare(actual, value) == 0) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>double</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the given <code>double</code>s are equal.
   */
  static void failIfEqual(String message, double actual, double value) {
    if (Double.compare(actual, value) == 0) fail(errorMessageIfEqual(message, actual, value));
  }

  /**
   * Fails if the given <code>char</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>char</code>s are not equal.
   */
  static void failIfNotEqual(String message, char actual, char expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the given <code>byte</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>byte</code>s are not equal.
   */
  static void failIfNotEqual(String message, byte actual, byte expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the given <code>short</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>short</code>s are not equal.
   */
  static void failIfNotEqual(String message, short actual, short expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the given <code>int</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>int</code>s are not equal.
   */
  static void failIfNotEqual(String message, int actual, int expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the given <code>long</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>long</code>s are not equal.
   */
  static void failIfNotEqual(String message, long actual, long expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the given <code>float</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>float</code>s are not equal.
   */
  static void failIfNotEqual(String message, float actual, float expected) {
    if (Float.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the given <code>double</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>double</code>s are not equal.
   */
  static void failIfNotEqual(String message, double actual, double expected) {
    if (Double.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(message, actual, expected));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, char actual, char value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, byte actual, byte value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, short actual, short value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, int actual, int value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, long actual, long value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, float actual, float value) {
    if (Float.compare(actual, value) >= 0) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  static void failIfNotLessThan(String message, double actual, double value) {
    if (Double.compare(actual, value) >= 0) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, char actual, char value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, byte actual, byte value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, short actual, short value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, int actual, int value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, long actual, long value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, float actual, float value) {
    if (Float.compare(actual, value) <= 0) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  static void failIfNotGreaterThan(String message, double actual, double value) {
    if (Double.compare(actual, value) <= 0) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, char actual, char value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, byte actual, byte value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, short actual, short value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, int actual, int value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, long actual, long value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, float actual, float value) {
    if (Float.compare(actual, value) > 0) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  static void failIfNotLessThanOrEqualTo(String message, double actual, double value) {
    if (Double.compare(actual, value) > 0) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, char actual, char value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, byte actual, byte value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, short actual, short value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, int actual, int value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, long actual, long value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, float actual, float value) {
    if (Float.compare(actual, value) < 0) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  static void failIfNotGreaterThanOrEqualTo(String message, double actual, double value) {
    if (Double.compare(actual, value) < 0) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  private PrimitiveFail() {}
}
