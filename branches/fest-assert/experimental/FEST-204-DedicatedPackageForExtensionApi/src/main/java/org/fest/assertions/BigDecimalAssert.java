/*
 * Created on Dec 27, 2006
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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.extensionapi.FailConditional.*;

import java.math.BigDecimal;

import org.fest.assertions.extensionapi.*;

import static java.math.BigDecimal.ZERO;

/**
 * Understands assertion methods for <code>{@link BigDecimal}</code>. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(BigDecimal)}</code>.
 *
 * @author David DIDIER
 * @author Ted M. Young
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BigDecimalAssert extends GenericAssert<BigDecimal> {

  protected BigDecimalAssert(BigDecimal actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(&quot;Result&quot;).isNotNull();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BigDecimalAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(&quot;Result&quot;).isNotNull();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BigDecimalAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(new BasicDescription(&quot;Result&quot;)).isNotNull();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BigDecimalAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(new BasicDescription(&quot;Result&quot;)).isNotNull();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BigDecimalAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public BigDecimalAssert satisfies(Condition<BigDecimal> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual value does satisfies the given condition.
   */
  public BigDecimalAssert doesNotSatisfy(Condition<BigDecimal> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not positive.
   */
  public BigDecimalAssert isPositive() {
    return isGreaterThan(ZERO);
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not negative.
   */
  public BigDecimalAssert isNegative() {
    return isLessThan(ZERO);
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is equal to zero, regardless of precision.
   * Essentially, this is the same as
   * <code>{@link #isEqualByComparingTo(BigDecimal) isEqualByComparingTo}</code>(<code>{@link BigDecimal#ZERO BigDecimal.ZERO}</code>).
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not equal to zero.
   */
  public BigDecimalAssert isZero() {
    return isEqualByComparingTo(ZERO);
  }


  public BigDecimalAssert isNotNull() {
    assertNotNull();
    return this;
  }
  
  public void isNull() {
    assertNull();
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is the same as the given one.
   * @param expected the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not the same as the given one.
   */
  public BigDecimalAssert isSameAs(BigDecimal expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is not the same as the given one.
   * @param other the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is the same as the given one.
   */
  public BigDecimalAssert isNotSameAs(BigDecimal other) {
    assertNotSameAs(other);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is equal to the given one. Unlike
   * <code>{@link #isEqualByComparingTo(BigDecimal)}</code>, this method considers two
   * <code>{@link BigDecimal}</code> objects equal only if they are equal in value and scale (thus 2.0 is not equal to
   * 2.00 when compared by this method).
   * @param expected the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not equal to the given one.
   * @see BigDecimal#equals(Object)
   * @see #isEqualByComparingTo(BigDecimal)
   */
  public BigDecimalAssert isEqualTo(BigDecimal expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is not equal to the given one.
   * @param other the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is equal to the given one.
   */
  public BigDecimalAssert isNotEqualTo(BigDecimal other) {
    assertNotEqualTo(other);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is equal to the given one. Two
   * <code>{@link BigDecimal}</code> objects that are equal in value but have a different scale (like 2.0 and 2.00)
   * are considered equal by this method.
   * @param expected the given <code>BigDecimal</code> to compare the actual <code>BigDecimal</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> is not equal to the given one.
   * @see BigDecimal#compareTo(BigDecimal)
   */
  public BigDecimalAssert isEqualByComparingTo(BigDecimal expected) {
    isNotNull();
    failIfNull(description(), expected);
    if (actual.compareTo(expected) != 0) fail(errorMessageIfNotEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> is <b>not</b> equal to the given one. Two
   * <code>{@link BigDecimal}</code> objects that are equal in value but have a different scale (like 2.0 and 2.00)
   * are considered equal by this method.
   * @param expected the given <code>BigDecimal</code> to use to compare to the actual <code>BigDecimal</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> is equal to the given one.
   * @see BigDecimal#compareTo(BigDecimal)
   */
  public BigDecimalAssert isNotEqualByComparingTo(BigDecimal expected) {
    isNotNull();
    failIfNull(description(), expected);
    if (actual.compareTo(expected) == 0) fail(errorMessageIfEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> value is less than the given one.
   * @param value the value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> value is not less than the given one.
   */
  public BigDecimalAssert isLessThan(BigDecimal value) {
    isNotNull();
    if (actual.compareTo(value) >= 0) fail(errorMessageIfNotLessThan(actual, value));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> value is greater than the given one.
   * @param value the value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> value is not greater than the given one.
   */
  public BigDecimalAssert isGreaterThan(BigDecimal value) {
    isNotNull();
    if (actual.compareTo(value) <= 0) fail(errorMessageIfNotGreaterThan(actual, value));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> value is less than or equal to the given one.
   * @param value the value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> value is not less than or equal to the given one.
   */
  public BigDecimalAssert isLessThanOrEqualTo(BigDecimal value) {
    isNotNull();
    if (actual.compareTo(value) > 0) fail(errorMessageIfNotLessThanOrEqualTo(actual, value));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link BigDecimal}</code> value is greater than or equal to the given one.
   * @param value the value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>BigDecimal</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>BigDecimal</code> value is not greater than or equal to the given one.
   */
  public BigDecimalAssert isGreaterThanOrEqualTo(BigDecimal value) {
    isNotNull();
    if (actual.compareTo(value) < 0) fail(errorMessageIfNotGreaterThanOrEqualTo(actual, value));
    return this;
  }
}
