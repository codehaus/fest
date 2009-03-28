/*
 * Created on Mar 3, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Collections.duplicatesFrom;
import static org.fest.util.Collections.list;
import static org.fest.util.Strings.concat;

import java.util.*;

/**
 * Understands assertions for <code>Object</code> arrays.  To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Object[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ObjectArrayAssert extends ArrayAssert<Object[]> {

  ObjectArrayAssert(Object... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(names).<strong>as</strong>(&quot;Jedi Knights&quot;).contains(&quot;Yoda&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert as(String description) {
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
   * assertThat(names).<strong>describedAs</strong>(&quot;Jedi Knights&quot;).contains(&quot;Yoda&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(names).<strong>as</strong>(new BasicDescription(&quot;Jedi Knights&quot;)).contains(&quot;Yoda&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert as(Description description) {
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
   * assertThat(names).<strong>describedAs</strong>(new BasicDescription(&quot;Jedi Knights&quot;)).contains(&quot;Yoda&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that all the elements in the actual <code>Object</code> array belong to the specified type. Matching
   * includes subclasses of the given type.
   * <p>
   * For example, consider the following code listing:
   * <pre>
   * Number[] numbers = { 2, 6 ,8 };
   * assertThat(numbers).hasComponentType(Integer.class);
   * </pre>
   * The assertion <code>hasAllElementsOfType</code> will be successful.
   * </p>
   * @param type the expected type.
   * @return this assertion object.
   * @throws AssertionError if the component type of the actual <code>Object</code> array is not the same as the
   *          specified one.
   */
  public ObjectArrayAssert hasAllElementsOfType(Class<?> type) {
    isNotNull();
    for (Object o : actual)
      if (!type.isInstance(o))
        fail(concat("not all elements in array:", actualInBrackets(), " belong to the type:", inBrackets(type)));
    return this;
  }

  /**
   * Verifies that at least one element in the actual <code>Object</code> array belong to the specified type. Matching
   * includes subclasses of the given type.
   * @param type the expected type.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> does not have any elements of the given type.
   */
  public ObjectArrayAssert hasAtLeastOneElementOfType(Class<?> type) {
    isNotNull();
    boolean found = false;
    for (Object o : actual) {
      if (!type.isInstance(o)) continue;
      found = true;
      break;
    }
    if (!found) fail(concat("array:", actualInBrackets(), " does not have any elements of type:", inBrackets(type)));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array contains the given objects.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array does not contain the given objects.
   */
  public ObjectArrayAssert contains(Object...objects) {
    isNotNull();
    assertContains(list(objects));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array contains the given objects <strong>only</strong>.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array does not contain the given objects, or if the
   *          actual <code>Object</code> array contains elements other than the ones specified.
   */
  public ObjectArrayAssert containsOnly(Object...objects) {
    isNotNull();
    assertContainsOnly(list(objects));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array does not contain the given objects.
   * @param objects the objects the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given objects.
   */
  public ObjectArrayAssert excludes(Object...objects) {
    isNotNull();
    assertExcludes(list(objects));
    return this;
  }

  List<Object> copyActual() {
    return list(actual);
  }

  /**
   * Verifies that the actual <code>Object</code> array does not have duplicates.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array has duplicates.
   */
  public ObjectArrayAssert doesNotHaveDuplicates() {
    isNotNull();
    Collection<?> actualAsList = list(actual);
    Collection<?> duplicates = duplicatesFrom(actualAsList);
    if (!duplicates.isEmpty())
      fail(concat("array:", actualInBrackets(), " contains duplicate(s):", inBrackets(duplicates)));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public ObjectArrayAssert satisfies(Condition<Object[]> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public ObjectArrayAssert doesNotSatisfy(Condition<Object[]> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   */
  public ObjectArrayAssert isNotNull() {
    assertArrayNotNull();
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array is empty.
   */
  public ObjectArrayAssert isNotEmpty() {
    assertNotEmpty();
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is equal to the given array. Array equality is checked by
   * <code>{@link Arrays#deepEquals(Object[], Object[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is not equal to the given one.
   */
  public ObjectArrayAssert isEqualTo(Object[] expected) {
    if (!Arrays.deepEquals(actual, expected)) fail(errorMessageIfNotEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is not equal to the given array. Array equality is checked by
   * <code>{@link Arrays#deepEquals(Object[], Object[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is equal to the given one.
   */
  public ObjectArrayAssert isNotEqualTo(Object[] array) {
    if (Arrays.deepEquals(actual, array)) fail(errorMessageIfEqual(actual, array));
    return this;
  }

  /**
   * Verifies that the number of elements in the actual <code>Object</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>Object</code> array.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the number of elements in the actual <code>Object</code> array is not equal to the given
   * one.
   */
  public ObjectArrayAssert hasSize(int expected) {
    assertHasSize(expected);
    return this;
  }

  /**
   * Returns the number of elements in the actual <code>Object</code> array.
   * @return the number of elements in the actual <code>Object</code> array.
   */
  protected int actualGroupSize() {
    isNotNull();
    return actual.length;
  }

  /**
   * Verifies that the actual <code>Object</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is not the same as the given one.
   */
  public ObjectArrayAssert isSameAs(Object[] expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is the same as the given one.
   */
  public ObjectArrayAssert isNotSameAs(Object[] expected) {
    assertNotSameAs(expected);
    return this;
  }
}
