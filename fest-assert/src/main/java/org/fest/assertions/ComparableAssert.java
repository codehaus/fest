/*
 * Created on Oct 4, 2009
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
package org.fest.assertions;

import static org.fest.assertions.ErrorMessages.*;

/**
 * Understands a template for assertion methods, applicable to <code>{@link Comparable}</code>s.
 * @param <T> the type of <code>Comparable</code> this template can verify.
 *
 * @author Alex Ruiz
 */
public abstract class ComparableAssert<T extends Comparable<T>> extends GenericAssert<T> {

  /**
   * Creates a new </code>{@link ComparableAssert}</code>.
   * @param actual the target to verify.
   */
  public ComparableAssert(T actual) {
    super(actual);
  }

  /**
   * Verifies that the actual <code>{@link Comparable}</code> is equal to the given one.
   * @param expected the given <code>Comparable</code> to compare the actual <code>Comparable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Comparable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Comparable</code> is not equal to the given one.
   */
  protected abstract ComparableAssert<T> isEqualByComparingTo(T expected);

  /**
   * Verifies that the actual <code>{@link Comparable}</code> is <b>not</b> equal to the given one.
   * @param expected the given <code>Comparable</code> to use to compare to the actual <code>Comparable</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Comparable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Comparable</code> is equal to the given one.
   */
  protected abstract ComparableAssert<T> isNotEqualByComparingTo(T expected);

  /**
   * Verifies that the actual <code>{@link Comparable}</code> is less than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Comparable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Comparable</code> is not less than the given one.
   */
  protected abstract ComparableAssert<T> isLessThan(T other);

  /**
   * Verifies that the actual <code>{@link Comparable}</code> is greater than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Comparable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Comparable</code> is not greater than the given one.
   */
  protected abstract ComparableAssert<T> isGreaterThan(T other);

  /**
   * Verifies that the actual <code>{@link Comparable}</code> is less than or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Comparable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Comparable</code> is not less than or equal to the given one.
   */
  protected abstract ComparableAssert<T> isLessThanOrEqualTo(T other);

  /**
   * Verifies that the actual <code>{@link Comparable}</code> is greater than or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Comparable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Comparable</code> is not greater than or equal to the given one.
   */
  protected abstract ComparableAssert<T> isGreaterThanOrEqualTo(T other);


  protected final void assertEqualByComparingTo(T expected) {
    isNotNull();
    if (actual.compareTo(expected) != 0) fail(unexpectedNotEqual(actual, expected));
  }

  protected final void assertNotEqualByComparingTo(T expected) {
    isNotNull();
    if (actual.compareTo(expected) == 0) fail(unexpectedEqual(actual, expected));
  }

  protected final void assertIsLessThan(T other) {
    isNotNull();
    if (actual.compareTo(other) >= 0) fail(unexpectedGreaterThanOrEqualTo(actual, other));
  }

  protected final void assertIsGreaterThan(T other) {
    isNotNull();
    if (actual.compareTo(other) <= 0) fail(unexpectedLessThanOrEqualTo(actual, other));
  }

  protected final void assertIsLessThanOrEqualTo(T other) {
    isNotNull();
    if (actual.compareTo(other) > 0) fail(unexpectedGreaterThan(actual, other));
  }

  protected final void assertIsGreaterThanOrEqualTo(T other) {
    isNotNull();
    if (actual.compareTo(other) < 0) fail(unexpectedLessThan(actual, other));
  }
}