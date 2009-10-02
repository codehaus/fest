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

import static org.fest.assertions.Collections.found;
import static org.fest.assertions.Collections.notFound;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Collections.duplicatesFrom;
import static org.fest.util.Strings.concat;

import java.util.*;

import org.fest.util.Collections;

/**
 * Understands assertions for collections. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Collection)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssert extends GroupAssert<Collection<?>> {

  /**
   * Creates a new </code>{@link CollectionAssert}</code>.
   * @param actual the target to verify.
   */
  protected CollectionAssert(Collection<?> actual) {
    super(actual);
  }

  /**
   * Verifies that the actual collection contains the given objects.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the actual collection does not contain the given objects.
   */
  public CollectionAssert contains(Object...objects) {
    isNotNull();
    validateIsNotNull(objects);
    Collection<Object> notFound = notFound(actual, objects);
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    return this;
  }

  /**
   * Verifies that the actual collection contains the given objects <strong>only</strong>.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the actual collection does not contain the given objects, or if the actual collection
   * contains elements other than the ones specified.
   */
  public CollectionAssert containsOnly(Object...objects) {
    isNotNull();
    validateIsNotNull(objects);
    List<Object> notFound = new ArrayList<Object>();
    List<Object> copy = new ArrayList<Object>(actual);
    for (Object o : objects) {
      if (!copy.contains(o)) {
        notFound.add(o);
        continue;
      }
      copy.remove(o);
    }
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    if (!copy.isEmpty())
      fail(concat("unexpected element(s):", format(copy), " in collection:", format(actual)));
    return this;
  }

  private void failIfElementsNotFound(Collection<Object> notFound) {
    fail(concat("collection:", format(actual), " does not contain element(s):", format(notFound)));
  }

  /**
   * Verifies that the actual collection does not contain the given objects.
   * @param objects the objects that the collection should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the actual collection contains any of the given objects.
   */
  public CollectionAssert excludes(Object...objects) {
    isNotNull();
    validateIsNotNull(objects);
    Collection<Object> found = found(actual, objects);
    if (!found.isEmpty())
      fail(concat("collection:", format(actual), " does not exclude element(s):", format(found)));
    return this;
  }

  private void validateIsNotNull(Object[] objects) {
    if (objects == null)
      throw new NullPointerException(formattedErrorMessage("the given array of objects should not be null"));
  }

  /**
   * Verifies that the actual collection does not have duplicates.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws AssertionError if the actual collection has duplicates.
   */
  public CollectionAssert doesNotHaveDuplicates() {
    isNotNull();
    Collection<?> duplicates = duplicatesFrom(actual);
    if (!duplicates.isEmpty())
      fail(concat("collection:", format(actual), " contains duplicate(s):", format(duplicates)));
    return this;
  }

  private String format(Collection<?> c) {
    return inBrackets(c);
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
  public CollectionAssert as(String description) {
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
  public CollectionAssert describedAs(String description) {
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
  public CollectionAssert as(Description description) {
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
  public CollectionAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual collection satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual collection does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public CollectionAssert satisfies(Condition<Collection<?>> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual collection does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual collection satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public CollectionAssert doesNotSatisfy(Condition<Collection<?>> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the actual collection is <code>null</code> or empty.
   * @throws AssertionError if the actual collection is not <code>null</code> or not empty.
   */
  public void isNullOrEmpty() {
    if (Collections.isEmpty(actual)) return;
    fail(concat("expecting a null or empty collection, but was:", format(actual)));
  }

  /**
   * Verifies that the actual collection is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   */
  public CollectionAssert isNotNull() {
    if (actual == null) fail("expecting a non-null collection, but it was null");
    return this;
  }

  /**
   * Verifies that the actual collection is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws AssertionError if the actual collection is not empty.
   */
  public void isEmpty() {
    isNotNull();
    if (!Collections.isEmpty(actual)) fail(concat("expecting empty collection, but was:", format(actual)));
  }

  /**
   * Verifies that the actual collection contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws AssertionError if the actual collection is empty.
   */
  public CollectionAssert isNotEmpty() {
    isNotNull();
    if (actual.isEmpty()) fail("expecting a non-empty collection, but it was empty");
    return this;
  }

  /**
   * Verifies that the number of elements in the actual collection is equal to the given one.
   * @param expected the expected number of elements in the actual collection.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   * @throws AssertionError if the number of elements of the actual collection is not equal to the given one.
   */
  public CollectionAssert hasSize(int expected) {
    int actualSize = actualGroupSize();
    if (actualSize != expected)
      fail(concat(
          "expected size:", inBrackets(expected)," but was:", inBrackets(actualSize), " for collection:", format(actual)));
    return this;
  }

  /**
   * Returns the number of elements in the actual collection.
   * @return the number of elements in the actual collection.
   */
  protected int actualGroupSize() {
    isNotNull();
    return actual.size();
  }

  /**
   * Verifies that the actual collection is equal to the given one.
   * @param expected the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is not equal to the given one.
   */
  public CollectionAssert isEqualTo(Collection<?> expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual collection is not equal to the given one.
   * @param other the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is equal to the given one.
   */
  public CollectionAssert isNotEqualTo(Collection<?> other) {
    assertNotEqualTo(other);
    return this;
  }

  /**
   * Verifies that the actual collection is the same as the given one.
   * @param expected the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is not the same as the given one.
   */
  public CollectionAssert isSameAs(Collection<?> expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual collection is not the same as the given one.
   * @param other the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is the same as the given one.
   */
  public CollectionAssert isNotSameAs(Collection<?> other) {
    assertNotSameAs(other);
    return this;
  }
}
