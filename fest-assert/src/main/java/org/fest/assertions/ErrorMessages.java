/*
 * Created on Sep 27, 2009
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

import static org.fest.assertions.Formatting.*;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Understands common error messages.
 *
 * @author Alex Ruiz
 */
final class ErrorMessages {

  static String messageForNotEqual(String description, Object actual, Object expected) {
    return format(description, messageForNotEqual(actual, expected));
  }

  static String messageForNotEqual(Object actual, Object expected) {
    return concat("expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }

  static String messageForEqual(String description, Object actual, Object o) {
    return format(description, messageForEqual(actual, o));
  }

  static String messageForEqual(Object actual, Object o) {
    return comparisonFailed(actual, " should not be equal to:", o);
  }

  static String messageForNotGreaterThan(String description, Object actual, Object value) {
    return format(description, messageForNotGreaterThan(actual, value));
  }

  static String messageForNotGreaterThan(Object actual, Object value) {
    return comparisonFailed(actual, " should be greater than:", value);
  }

  static String messageForNotGreaterThanOrEqualTo(String description, Object actual, Object value) {
    return format(description, messageForNotGreaterThanOrEqualTo(actual, value));
  }

  static String messageForNotGreaterThanOrEqualTo(Object actual, Object value) {
    return comparisonFailed(actual, " should be greater than or equal to:", value);
  }

  static String messageForNotLessThan(String description, Object actual, Object value) {
    return format(description, messageForNotLessThan(actual, value));
  }

  static String messageForNotLessThan(Object actual, Object value) {
    return comparisonFailed(actual, " should be less than:", value);
  }

  static String messageForNotLessThanOrEqualTo(String description, Object actual, Object value) {
    return format(description, messageForNotLessThanOrEqualTo(actual, value));
  }

  static String messageForNotLessThanOrEqualTo(Object actual, Object value) {
    return comparisonFailed(actual, " should be less than or equal to:", value);
  }

  private static String comparisonFailed(Object actual, String reason, Object expected) {
    return comparisonFailed(null, actual, reason, expected);
  }

  private static String comparisonFailed(String description, Object actual, String reason, Object expected) {
    return messageFrom(description, array("actual value:", inBrackets(actual), reason, inBrackets(expected)));
  }

  private ErrorMessages() {}
}
