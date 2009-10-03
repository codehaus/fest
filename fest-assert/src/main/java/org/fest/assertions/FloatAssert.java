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

import static java.lang.Math.abs;
import static org.fest.assertions.ErrorMessages.messageForNotEqual;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.assertions.PrimitiveFail.*;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>float</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(float)}</code>.
 *
 * @author Yvonne Wang
 */
public class FloatAssert extends PrimitiveAssert {

  private static final float ZERO = 0f;

  private final float actual;

  /**
   * Creates a new </code>{@link FloatAssert}</code>.
   * @param actual the target to verify.
   */
  protected FloatAssert(float actual) {
    this.actual = actual;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatAssert as(String description) {
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
   * assertThat(value).<strong>describedAs</strong>(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatAssert as(Description description) {
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
   * assertThat(value).<strong>describedAs</strong>(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>float</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to the given one.
   */
  public FloatAssert isEqualTo(float expected) {
    failIfNotEqual(rawDescription(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is equal to the given one, within a positive delta.
   * @param expected the value to compare the actual one to.
   * @param delta the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to the given one.
   */
  public FloatAssert isEqualTo(float expected, Delta delta) {
    if (Float.compare(expected, actual) == 0) return this;
    if (!(abs(expected - actual) <= delta.value))
      fail(concat(messageForNotEqual(actual, expected), " using delta:", inBrackets(delta.value)));
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is not equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is equal to the given one.
   */
  public FloatAssert isNotEqualTo(float other) {
    failIfEqual(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is greater than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not greater than the given one.
   */
  public FloatAssert isGreaterThan(float other) {
    failIfNotGreaterThan(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is less than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not less than the given one.
   */
  public FloatAssert isLessThan(float other) {
    failIfNotLessThan(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is greater or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not greater than or equal to the given one.
   */
  public FloatAssert isGreaterThanOrEqualTo(float other) {
    failIfNotGreaterThanOrEqualTo(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is less or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not less than or equal to the given one.
   */
  public FloatAssert isLessThanOrEqualTo(float other) {
    failIfNotLessThanOrEqualTo(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is equal to <code>{@link Float#NaN}</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to <code>NaN</code>.
   */
  public FloatAssert isNaN() { return isEqualTo(Float.NaN); }

  /**
   * Verifies that the actual <code>float</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not positive.
   */
  public FloatAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>float</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not negative.
   */
  public FloatAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>float</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to zero.
   */
  public FloatAssert isZero() { return isEqualTo(ZERO); }

  /**
   * Creates a new holder for a delta value to be used in
   * <code>{@link FloatAssert#isEqualTo(float, org.fest.assertions.FloatAssert.Delta)}</code>.
   * @param d the delta value.
   * @return a new delta value holder.
   */
  public static Delta delta(float d) {
    return new Delta(d);
  }

  /**
   * Holds a delta value to be used in
   * <code>{@link FloatAssert#isEqualTo(float, org.fest.assertions.FloatAssert.Delta)}</code>.
   */
  public static class Delta {
    final float value;

    private Delta(float value) {
      this.value = value;
    }
  }

}


