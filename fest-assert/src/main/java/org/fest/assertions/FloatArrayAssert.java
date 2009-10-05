/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ArrayInspection.copy;
import static org.fest.assertions.ErrorMessages.unexpectedEqual;
import static org.fest.assertions.ErrorMessages.unexpectedNotEqual;

import java.util.*;

/**
 * Understands assertion methods for <code>float</code> arrays. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(float[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssert extends ArrayAssert<float[]> {

  /**
   * Creates a new </code>{@link FloatArrayAssert}</code>.
   * @param actual the target to verify.
   */
  protected FloatArrayAssert(float... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(values).<strong>as</strong>(&quot;Some values&quot;).isNotEmpty();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatArrayAssert as(String description) {
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
   * assertThat(values).<strong>describedAs</strong>(&quot;Some values&quot;).isNotEmpty();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatArrayAssert describedAs(String description) {
    return as(description);
  }


  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(values).<strong>as</strong>(new BasicDescription(&quot;Some values&quot;)).isNotEmpty();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatArrayAssert as(Description description) {
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
   * assertThat(values).<strong>describedAs</strong>(new BasicDescription(&quot;Some values&quot;)).isNotEmpty();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public FloatArrayAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>float</code> array contains the given values.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   * @throws NullPointerException if the given <code>float</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>float</code> array does not contain the given values.
   */
  public FloatArrayAssert contains(float...values) {
    isNotNull();
    validateIsNotNull(values);
    assertContains(copy(values));
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array contains the given values <strong>only</strong>.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   * @throws NullPointerException if the given <code>float</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>float</code> array does not contain the given objects, or if the actual
   * <code>float</code> array contains elements other than the ones specified.
   */
  public FloatArrayAssert containsOnly(float...values) {
    isNotNull();
    validateIsNotNull(values);
    assertContainsOnly(copy(values));
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array does not contain the given values.
   * @param values the values the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   * @throws NullPointerException if the given <code>float</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given values.
   */
  public FloatArrayAssert excludes(float...values) {
    isNotNull();
    validateIsNotNull(values);
    assertExcludes(copy(values));
    return this;
  }

  private void validateIsNotNull(float[] values) {
    if (values == null)
      throw new NullPointerException(formattedErrorMessage("the given array of floats should not be null"));
  }

  /**
   * Verifies that the actual <code>float</code> array satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public FloatArrayAssert satisfies(Condition<float[]> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public FloatArrayAssert doesNotSatisfy(Condition<float[]> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   */
  public FloatArrayAssert isNotNull() {
    assertThatActualIsNotNull();
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>float</code> array is empty.
   */
  public FloatArrayAssert isNotEmpty() {
    assertThatActualIsNotEmpty();
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(float[], float[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is not equal to the given one.
   */
  public FloatArrayAssert isEqualTo(float[] expected) {
    if (!Arrays.equals(actual, expected)) fail(unexpectedNotEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is not equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(float[], float[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is equal to the given one.
   */
  public FloatArrayAssert isNotEqualTo(float[] array) {
    if (Arrays.equals(actual, array)) fail(unexpectedEqual(actual, array));
    return this;
  }

  /**
   * Verifies that the number of elements in the actual <code>float</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>float</code> array.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   * @throws AssertionError if the number of elements in the actual <code>float</code> array is not equal to the given
   * one.
   */
  public FloatArrayAssert hasSize(int expected) {
    assertThatActualHasSize(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is not the same as the given one.
   */
  public FloatArrayAssert isSameAs(float[] expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is the same as the given one.
   */
  public FloatArrayAssert isNotSameAs(float[] expected) {
    assertNotSameAs(expected);
    return this;
  }
}
