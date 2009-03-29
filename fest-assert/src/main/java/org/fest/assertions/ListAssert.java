/*
 * Created on Mar 29, 2009
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

import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Strings.concat;

import java.util.List;

import org.fest.util.Collections;

/**
 * Understands assertions for <code>{@link List}</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(List)}</code>.
 *
 * @author Alex Ruiz
 */
public class ListAssert extends GroupAssert<List<?>> {

  ListAssert(List<?> actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(employees).<strong>as</strong>(&quot;New Hires&quot;).hasSize(6);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ListAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(employees).&lt;strong&gt;describedAs&lt;/strong&gt;(&quot;New Hires&quot;).hasSize(6);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ListAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(employees).<strong>as</strong>(new BasicDescription(&quot;New Hires&quot;)).hasSize(6);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ListAssert as(Description description) {
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
   * assertThat(employees).<strong>describedAs</strong>(new BasicDescription(&quot;New Hires&quot;)).hasSize(6);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ListAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>{@link List}</code> satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public ListAssert satisfies(Condition<List<?>> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public ListAssert doesNotSatisfy(Condition<List<?>> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the number of elements in the actual <code>{@link List}</code> is equal to the given one.
   * @param expected the expected number of elements in the actual <code>List</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the number of elements of the actual <code>List</code> is not equal to the given one.
   */
  public ListAssert hasSize(int expected) {
    int actualSize = actualGroupSize();
    if (actualSize != expected)
      fail(concat(
          "expected size:", inBrackets(expected)," but was:", inBrackets(actualSize), " for list:", format(actual)));
    return this;
  }

  /**
   * Returns the number of elements in the actual <code>{@link List}</code>.
   * @return the number of elements in the actual <code>List</code>.
   */
  protected int actualGroupSize() {
    isNotNull();
    return actual.size();
  }

  private String format(List<?> c) {
    return inBrackets(c);
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> is not empty.
   */
  public void isEmpty() {
    isNotNull();
    if (!Collections.isEmpty(actual)) fail(concat("expecting empty list, but was:", format(actual)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> is empty.
   */
  public ListAssert isNotEmpty() {
    isNotNull();
    if (actual.isEmpty()) fail("expecting a non-empty list, but it was empty");
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is <code>null</code> or empty.
   * @throws AssertionError if the actual <code>List</code> is not <code>null</code> or not empty.
   */
  public void isNullOrEmpty() {
    if (Collections.isEmpty(actual)) return;
    fail(concat("expecting a null or empty list, but was:", format(actual)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   */
  public ListAssert isNotNull() {
    if (actual == null) fail("expecting a non-null list, but it was null");
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is equal to the given one.
   * @param expected the given <code>List</code> to compare the actual <code>List</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is not equal to the given one.
   */
  public ListAssert isEqualTo(List<?> expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is not equal to the given one.
   * @param other the given <code>List</code> to compare the actual <code>List</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is equal to the given one.
   */
  public ListAssert isNotEqualTo(List<?> other) {
    assertNotEqualTo(other);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is the same as the given one.
   * @param expected the given <code>List</code> to compare the actual <code>List</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is not the same as the given one.
   */
  public ListAssert isSameAs(List<?> expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is not the same as the given one.
   * @param other the given <code>List</code> to compare the actual <code>List</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is the same as the given one.
   */
  public ListAssert isNotSameAs(List<?> other) {
    assertNotSameAs(other);
    return this;
  }
}
