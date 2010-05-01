/*
 * Created on Jun 18, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ErrorMessages.*;

/**
 * Understands assertion methods for <code>short</code>s. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(short)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ShortAssert extends PrimitiveAssert<Short> implements NumberAssert {

  private static final short ZERO = (short) 0;

  /**
   * Creates a new <code>{@link ShortAssert}</code>.
   *
   * @param actual the actual value to verify.
   */
  protected ShortAssert(short actual) {
    super(actual);
  }

  /**
   * Creates a new <code>{@link ShortAssert}</code>.
   *
   * @param actual the actual value to verify.
   */
  protected ShortAssert(Short actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description. <p> For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * </p>
   *
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ShortAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alias for <code>{@link #as(String)}</code>, since "as" is a keyword in <a href="http://groovy.codehaus.org/"
   * target="_blank">Groovy</a>. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description. <p> For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * </p>
   *
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ShortAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description. <p> For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * </p>
   *
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ShortAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alias for <code>{@link #as(Description)}</code>, since "as" is a keyword in <a href="http://groovy.codehaus.org/"
   * target="_blank">Groovy</a>. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description. <p> For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(new BasicDescription(&quot;Some value&quot;))
   *   .isEqualTo(otherValue);
   * </pre>
   * </p>
   *
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ShortAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>short</code> value is equal to the given one.
   *
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not equal to the given one.
   */
  public ShortAssert isEqualTo(short expected) {
    if (actual == expected) return this;
    failIfCustomMessageIsSet();
    throw failure(unexpectedNotEqual(actual, expected));
  }

  /**
   * Verifies that the actual <code>short</code> value is not equal to the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is equal to the given one.
   */
  public ShortAssert isNotEqualTo(short other) {
    if (actual != other) return this;
    failIfCustomMessageIsSet();
    throw failure(unexpectedEqual(actual, other));
  }

  /**
   * Verifies that the actual <code>short</code> value is greater than the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not greater than the given one.
   */
  public ShortAssert isGreaterThan(short other) {
    if (actual > other) return this;
    failIfCustomMessageIsSet();
    throw failure(unexpectedLessThanOrEqualTo(actual, other));
  }

  /**
   * Verifies that the actual <code>short</code> value is less than the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not less than the given one.
   */
  public ShortAssert isLessThan(short other) {
    if (actual < other) return this;
    failIfCustomMessageIsSet();
    throw failure(unexpectedGreaterThanOrEqualTo(actual, other));
  }

  /**
   * Verifies that the actual <code>short</code> value is greater or equal to the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not greater than or equal to the given one.
   */
  public ShortAssert isGreaterThanOrEqualTo(short other) {
    if (actual >= other) return this;
    failIfCustomMessageIsSet();
    throw failure(unexpectedLessThan(actual, other));
  }

  /**
   * Verifies that the actual <code>short</code> value is less or equal to the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not less than or equal to the given one.
   */
  public ShortAssert isLessThanOrEqualTo(short other) {
    if (actual <= other) return this;
    failIfCustomMessageIsSet();
    throw failure(unexpectedGreaterThan(actual, other));
  }

  /**
   * Verifies that the actual <code>short</code> value is equal to zero.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not equal to zero.
   */
  public ShortAssert isZero() {
    return isEqualTo(ZERO);
  }

  /**
   * Verifies that the actual <code>short</code> value is positive.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not positive.
   */
  public ShortAssert isPositive() {
    return isGreaterThan(ZERO);
  }

  /**
   * Verifies that the actual <code>short</code> value is negative.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not negative.
   */
  public ShortAssert isNegative() {
    return isLessThan(ZERO);
  }

  /** {@inheritDoc} */
  public ShortAssert overridingErrorMessage(String message) {
    replaceDefaultErrorMessagesWith(message);
    return this;
  }

  /**
   * TODO konermann: Remove this comment after FEST-133 is resolved:
   * ===== Newly implemented methods for FEST-133 / from GenericAssert start here:
   */
  /**
   * Verifies that the actual <code>{@link Short}</code> satisfies the given condition.
   *
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError       if the actual <code>Short</code> does not satisfy the given condition.
   * @see #is(Condition)
   * @since 1.3
   */
  @Override
  public ShortAssert satisfies(Condition<Short> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link Short}</code> does not satisfy the given condition.
   *
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError       if the actual value does satisfies the given condition.
   * @see #isNot(Condition)
   * @since 1.3
   */
  @Override
  public ShortAssert doesNotSatisfy(Condition<Short> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Alias for <code>{@link #satisfies(Condition)}</code>.
   *
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError       if the actual <code>Short</code> does not satisfy the given condition.
   * @since 1.3
   */
  @Override
  public ShortAssert is(Condition<Short> condition) {
    assertIs(condition);
    return this;
  }

  /**
   * Alias for <code>{@link #doesNotSatisfy(Condition)}</code>.
   *
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError       if the actual <code>Short</code> does not satisfy the given condition.
   * @since 1.3
   */
  @Override
  public ShortAssert isNot(Condition<Short> condition) {
    assertIsNot(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link Short}</code> value is equal to the given one.
   *
   * @param expected the given <code>Short</code> value to compare the actual <code>Short</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Short</code> value is not equal to the given one.
   * @since 1.3
   */
  @Override
  public ShortAssert isEqualTo(Short expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link Short}</code> is not equal to the given one.
   *
   * @param other the given <code>Short</code> to compare the actual <code>Short</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Short</code> value is equal to the given one.
   * @since 1.3
   */
  @Override
  public ShortAssert isNotEqualTo(Short other) {
    assertNotEqualTo(other);
    return this;
  }

  @Override
  public ShortAssert isNotNull() {
    assertNotNull();
    return this;
  }

  @Override
  public ShortAssert isSameAs(Short expected) {
    assertSameAs(expected);
    return this;
  }

  @Override
  public ShortAssert isNotSameAs(Short other) {
    assertNotSameAs(other);
    return this;
  }
}
