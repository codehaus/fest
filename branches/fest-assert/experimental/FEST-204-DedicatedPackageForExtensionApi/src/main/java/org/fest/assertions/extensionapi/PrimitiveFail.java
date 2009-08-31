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
package org.fest.assertions.extensionapi;

import static org.fest.assertions.extensionapi.FailConditional.*;

/**
 * Understands failure methods for primitive types.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author David DIDIER
 */
public final class PrimitiveFail {

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, char actual, char value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, byte actual, byte value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, short actual, short value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, int actual, int value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, long actual, long value) {
    if (actual >= value) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, float actual, float value) {
    if (Float.compare(actual, value) >= 0) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than the value value.
   */
  public static void failIfNotLessThan(String message, double actual, double value) {
    if (Double.compare(actual, value) >= 0) fail(errorMessageIfNotLessThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, char actual, char value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, byte actual, byte value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, short actual, short value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, int actual, int value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, long actual, long value) {
    if (actual <= value) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, float actual, float value) {
    if (Float.compare(actual, value) <= 0) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than the value value.
   */
  public static void failIfNotGreaterThan(String message, double actual, double value) {
    if (Double.compare(actual, value) <= 0) fail(errorMessageIfNotGreaterThan(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, char actual, char value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, byte actual, byte value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, short actual, short value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, int actual, int value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, long actual, long value) {
    if (actual > value) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, float actual, float value) {
    if (Float.compare(actual, value) > 0) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not less than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not less than or equal to the value value.
   */
  public static void failIfNotLessThanOrEqualTo(String message, double actual, double value) {
    if (Double.compare(actual, value) > 0) fail(errorMessageIfNotLessThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, char actual, char value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, byte actual, byte value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, short actual, short value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, int actual, int value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, long actual, long value) {
    if (actual < value) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, float actual, float value) {
    if (Float.compare(actual, value) < 0) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  /**
   * Fails if the first value is not greater than or equal to the value value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the actual value.
   * @param value the value checked against <code>actual</code>.
   * @throws AssertionError if the first value is not greater than or equal to the value value.
   */
  public static void failIfNotGreaterThanOrEqualTo(String message, double actual, double value) {
    if (Double.compare(actual, value) < 0) fail(errorMessageIfNotGreaterThanOrEqualTo(message, actual, value));
  }

  protected PrimitiveFail() {}
}
