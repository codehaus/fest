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
 * Understands assertion methods for <code>long</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(long)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class LongAssert extends PrimitiveAssert implements NumberAssert {

  private static final long ZERO = 0L;

  private final long actual;

  /**
   * Creates a new </code>{@link LongAssert}</code>.
   * @param actual the target to verify.
   */
  protected LongAssert(long actual) {
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
  public LongAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alias for <code>{@link #as(String)}</code>, since "as" is a keyword in
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
  public LongAssert describedAs(String description) {
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
  public LongAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alias for <code>{@link #as(Description)}</code>, since "as" is a keyword in
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
  public LongAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>long</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not equal to the given one.
   */
  public LongAssert isEqualTo(long expected) {
    if (actual != expected) fail(unexpectedNotEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is not equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is equal to the given one.
   */
  public LongAssert isNotEqualTo(long other) {
    if (actual == other) fail(unexpectedEqual(actual, other));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is greater than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not greater than the given one.
   */
  public LongAssert isGreaterThan(long other) {
    if (actual <= other) fail(unexpectedLessThanOrEqualTo(actual, other));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is less than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not less than the given one.
   */
  public LongAssert isLessThan(long other) {
    if (actual >= other) fail(unexpectedGreaterThanOrEqualTo(actual, other));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is greater or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not greater than or equal to the given one.
   */
  public LongAssert isGreaterThanOrEqualTo(long other) {
    if (actual < other) fail(unexpectedLessThan(actual, other));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is less or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not less than or equal to the given one.
   */
  public LongAssert isLessThanOrEqualTo(long other) {
    if (actual > other) fail(unexpectedGreaterThan(actual, other));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not equal to zero.
   */
  public LongAssert isZero() {
    return isEqualTo(ZERO);
  }

  /**
   * Verifies that the actual <code>long</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not positive.
   */
  public LongAssert isPositive() {
    return isGreaterThan(ZERO);
  }

  /**
   * Verifies that the actual <code>long</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not negative.
   */
  public LongAssert isNegative() {
    return isLessThan(ZERO);
  }
}
