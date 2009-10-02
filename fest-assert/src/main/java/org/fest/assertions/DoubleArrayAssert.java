/*
 * Created on Feb 14, 2008
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

import static java.lang.Math.abs;
import static org.fest.assertions.ErrorMessages.messageForEqual;
import static org.fest.assertions.ErrorMessages.messageForNotEqual;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Strings.concat;

import java.util.*;

/**
 * Understands assertion methods for <code>double</code> arrays. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(double[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class DoubleArrayAssert extends ArrayAssert<double[]> {

  DoubleArrayAssert(double... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   *
   * <pre>
   * assertThat(values).&lt;strong&gt;as&lt;/strong&gt;(&quot;Some values&quot;).isNotEmpty();
   * </pre>
   *
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleArrayAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a href="http://groovy.codehaus.org/"
   * target="_blank">Groovy</a>. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   *
   * <pre>
   * assertThat(values).&lt;strong&gt;describedAs&lt;/strong&gt;(&quot;Some values&quot;).isNotEmpty();
   * </pre>
   *
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleArrayAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   *
   * <pre>
   * assertThat(values).&lt;strong&gt;as&lt;/strong&gt;(new BasicDescription(&quot;Some values&quot;)).isNotEmpty();
   * </pre>
   *
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleArrayAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   *
   * <pre>
   * assertThat(values).&lt;strong&gt;describedAs&lt;/strong&gt;(new BasicDescription(&quot;Some values&quot;)).isNotEmpty();
   * </pre>
   *
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleArrayAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>double</code> array contains the given values.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   * @throws NullPointerException if the given <code>double</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>double</code> array does not contain the given values.
   */
  public DoubleArrayAssert contains(double... values) {
    isNotNull();
    validateIsNotNull(values);
    assertContains(list(values));
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array contains the given values <strong>only</strong>.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   * @throws NullPointerException if the given <code>double</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>double</code> array does not contain the given objects, or if the actual
   * <code>double</code> array contains elements other than the ones specified.
   */
  public DoubleArrayAssert containsOnly(double... values) {
    isNotNull();
    validateIsNotNull(values);
    assertContainsOnly(list(values));
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array does not contain the given values.
   * @param values the values the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   * @throws NullPointerException if the given <code>double</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>double</code> array contains any of the given values.
   */
  public DoubleArrayAssert excludes(double... values) {
    isNotNull();
    validateIsNotNull(values);
    assertExcludes(list(values));
    return this;
  }

  private void validateIsNotNull(double[] values) {
    if (values == null)
      throw new NullPointerException(formattedErrorMessage("the given array of doubles should not be null"));
  }

  List<Object> copyActual() {
    return list(actual);
  }

  private List<Object> list(double[] values) {
    List<Object> list = new ArrayList<Object>();
    for (double value : values)
      list.add(value);
    return list;
  }

  /**
   * Verifies that the actual <code>double</code> array satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public DoubleArrayAssert satisfies(Condition<double[]> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public DoubleArrayAssert doesNotSatisfy(Condition<double[]> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   */
  public DoubleArrayAssert isNotNull() {
    assertArrayNotNull();
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>double</code> array is empty.
   */
  public DoubleArrayAssert isNotEmpty() {
    assertNotEmpty();
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array is equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(double[], double[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is not equal to the given one.
   */
  public DoubleArrayAssert isEqualTo(double[] expected) {
    if (!Arrays.equals(actual, expected)) fail(messageForNotEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array is equal to the given array, within a positive delta.
   * @param expected the given array to compare the actual array to.
   * @param delta the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is not equal to the given one.
   * @since 1.1
   */
  public DoubleArrayAssert isEqualTo(double[] expected, Delta delta) {
    if (actual == expected) return this;
    if (actual == null || expected == null) failNotEquals(expected, delta);
    int length = expected.length;
    if (actual.length != length) failNotEquals(expected, delta);
    for (int i = 0; i < length; i++)
      if (!equals(expected[i], actual[i], delta)) failNotEquals(expected, delta);
    return this;
  }

  private void failNotEquals(double[] expected, Delta delta) {
    fail(concat(messageForNotEqual(actual, expected), " using delta:", inBrackets(delta.value())));
  }

  private boolean equals(double e, double a, Delta delta) {
    if (Double.compare(e, a) == 0) return true;
    return abs(e - a) <= delta.value();
  }

  /**
   * Verifies that the actual <code>double</code> array is not equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(double[], double[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is equal to the given one.
   */
  public DoubleArrayAssert isNotEqualTo(double[] array) {
    if (Arrays.equals(actual, array)) fail(messageForEqual(actual, array));
    return this;
  }

  /**
   * Verifies that the number of elements in the actual <code>double</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>double</code> array.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   * @throws AssertionError if the number of elements in the actual <code>double</code> array is not equal to the given
   * one.
   */
  public DoubleArrayAssert hasSize(int expected) {
    assertHasSize(expected);
    return this;
  }

  /**
   * Returns the number of elements in the actual <code>double</code> array.
   * @return the number of elements in the actual <code>double</code> array.
   */
  protected int actualGroupSize() {
    isNotNull();
    return actual.length;
  }

  /**
   * Verifies that the actual <code>double</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is not the same as the given one.
   */
  public DoubleArrayAssert isSameAs(double[] expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is the same as the given one.
   */
  public DoubleArrayAssert isNotSameAs(double[] expected) {
    assertNotSameAs(expected);
    return this;
  }
}
