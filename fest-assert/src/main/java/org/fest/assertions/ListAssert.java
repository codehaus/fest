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

import static org.fest.assertions.Collections.found;
import static org.fest.assertions.Collections.notFound;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Collections.duplicatesFrom;
import static org.fest.util.Collections.list;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

import java.util.*;

import org.fest.util.Collections;

/**
 * Understands assertions for <code>{@link List}</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(List)}</code>.
 * @since 1.1
 *
 * @author Alex Ruiz
 */
public class ListAssert extends GroupAssert<List<?>> {

  /**
   * Creates a new </code>{@link ListAssert}</code>.
   * @param actual the target to verify.
   */
  protected ListAssert(List<?> actual) {
    super(actual);
  }

  /**
   * Verifies that the actual <code>{@link List}</code> contains the given object at the given index.
   * @param o the object to look for.
   * @param index the index where the object should be stored in the actual <code>List</code>.
   * @return this assertion object.
   * @throws NullPointerException if the given <code>Index</code> is <code>null</code>.
   * @throws IndexOutOfBoundsException if the value of the given <code>Index</code> is negative, or equal to or greater
   * than the size of the actual <code>List</code>.
   * @throws AssertionError if the given <code>List</code> does not contain the given object at the given index.
   */
  public ListAssert contains(Object o, Index index) {
    if (index == null) throw new NullPointerException(formattedErrorMessage("The given index should not be null"));
    isNotNull().isNotEmpty();
    int indexValue = index.value();
    int listSize = actualGroupSize();
    if (indexValue < 0 || indexValue >= listSize) failIndexOutOfBounds(indexValue);
    Object actualElement = actual.get(indexValue);
    if (!areEqual(actualElement, o)) failElementNotFound(o, actualElement, indexValue);
    return this;
  }

  private void failElementNotFound(Object e, Object a, int index) {
    fail(concat("expecting ", inBrackets(e), " at index ", inBrackets(index), " but found ", inBrackets(a)));
  }

  private void failIndexOutOfBounds(int index) {
    throw new IndexOutOfBoundsException(
        formattedErrorMessage(concat(
        "The index ", inBrackets(index), " should be greater than or equal to zero and less than ", actualGroupSize())));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> contains the given sequence of objects, without any other
   * objects between them.
   * @param sequence the sequence of objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> does not contain the given sequence of objects.
   */
  public ListAssert containsSequence(Object...sequence) {
    isNotNull();
    validateIsNotNull(sequence);
    int sequenceSize = sequence.length;
    if (sequenceSize == 0) return this;
    int indexOfFirst = actual.indexOf(sequence[0]);
    if (indexOfFirst == -1) failIfSequenceNotFound(sequence);
    int listSize = actualGroupSize();
    for (int i = 0; i < sequenceSize; i++) {
      int actualIndex = indexOfFirst + i;
      if (actualIndex > listSize - 1) failIfSequenceNotFound(sequence);
      if (!areEqual(sequence[i], actual.get(actualIndex))) failIfSequenceNotFound(sequence);
    }
    return this;
  }

  private void failIfSequenceNotFound(Object[] notFound) {
    fail(concat("list:", inBrackets(actual), " does not contain the sequence:", inBrackets(notFound)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> starts with the given sequence of objects, without any other
   * objects between them. Same as <code>{@link #containsSequence}</code>, but verifies also that first given object is
   * also first element of <code>List</code>.
   * @param sequence the sequence of objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> is not empty and with the given sequence of objects is
   * empty.
   * @throws AssertionError if the actual <code>List</code> does not start with the given sequence of objects.
   */
  public ListAssert startsWith(Object...sequence) {
    isNotNull();
    validateIsNotNull(sequence);
    int sequenceSize = sequence.length;
    int listSize = actualGroupSize();
    if (sequenceSize == 0 && listSize == 0) return this;
    if (sequenceSize == 0 && listSize != 0) failIfNotStartingWithSequence(sequence);
    if (listSize < sequenceSize) failIfNotStartingWithSequence(sequence);
    for (int i = 0; i < sequenceSize; i++)
      if (!areEqual(sequence[i], actual.get(i))) failIfNotStartingWithSequence(sequence);
    return this;
  }

  private void failIfNotStartingWithSequence(Object[] notFound) {
    fail(concat("list:", inBrackets(actual), " does not start with the sequence:", inBrackets(notFound)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> ends with the given sequence of objects, without any other
   * objects between them. Same as <code>{@link #containsSequence}</code>, but verifies also that last given object is
   * also last element of <code>List</code>.
   * @param sequence the sequence of objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> is not empty and with the given sequence of objects is
   * empty.
   * @throws AssertionError if the actual <code>List</code> does not end with the given sequence of objects.
   */
  public ListAssert endsWith(Object...sequence) {
    isNotNull();
    validateIsNotNull(sequence);
    int sequenceSize = sequence.length;
    int listSize = actualGroupSize();
    if (sequenceSize == 0 && listSize == 0) return this;
    if (sequenceSize == 0 && listSize != 0) failIfNotEndingWithSequence(sequence);
    if (listSize < sequenceSize) failIfNotEndingWithSequence(sequence);
    for (int i = 0; i < sequenceSize; i++) {
      int sequenceIndex = sequenceSize - 1 - i;
      int listIndex = listSize - 1 - i;
      if (!areEqual(sequence[sequenceIndex], actual.get(listIndex))) failIfNotEndingWithSequence(sequence);
    }
    return this;
  }

  private void failIfNotEndingWithSequence(Object[] notFound) {
    fail(concat("list:", inBrackets(actual), " does not end with the sequence:", inBrackets(notFound)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> contains the given objects, in any order.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> does not contain the given objects.
   */
  public ListAssert contains(Object...objects) {
    isNotNull();
    validateIsNotNull(objects);
    Collection<Object> notFound = notFoundInActual(objects);
    if (notFound.isEmpty()) return this;
    throw failureIfExpectedElementsNotFound(notFound);
  }

  private Collection<Object> notFoundInActual(Object... objects) {
    return notFound(actual, objects);
  }

  /**
   * Verifies that the actual <code>{@link List}</code> contains the given objects <strong>only</strong>, in any order.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> does not contain the given objects, or if the actual
   * <code>List</code> contains elements other than the ones specified.
   */
  public ListAssert containsOnly(Object...objects) {
    isNotNull();
    validateIsNotNull(objects);
    List<Object> copy = new ArrayList<Object>(actual);
    List<Object> notFound = notFoundInCopy(copy, objects);
    if (!notFound.isEmpty()) throw failureIfExpectedElementsNotFound(notFound);
    if (copy.isEmpty()) return this;
    throw failureIfUnexpectedElementsFound(copy);
  }

  private List<Object> notFoundInCopy(List<Object> copy, Object... objects) {
    List<Object> notFound = new ArrayList<Object>();
    for (Object o : objects) {
      if (!copy.contains(o)) {
        notFound.add(o);
        continue;
      }
      copy.remove(o);
    }
    return notFound;
  }

  private AssertionError failureIfExpectedElementsNotFound(Collection<Object> notFound) {
    failIfCustomMessageIsSet();
    return failure(concat("list:", inBrackets(actual), " does not contain element(s):", inBrackets(notFound)));
  }

  private AssertionError failureIfUnexpectedElementsFound(List<Object> unexpected) {
    failIfCustomMessageIsSet();
    return failure(concat("unexpected element(s):", inBrackets(unexpected), " in list:", inBrackets(actual)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> does not contain the given objects.
   * @param objects the objects that the <code>List</code> should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> contains any of the given objects.
   */
  public ListAssert excludes(Object...objects) {
    isNotNull();
    validateIsNotNull(objects);
    Collection<Object> found = found(actual, objects);
    if (!found.isEmpty())
      fail(concat("list:", inBrackets(actual), " does not exclude element(s):", inBrackets(found)));
    return this;
  }

  private void validateIsNotNull(Object[] objects) {
    if (objects == null)
      throw new NullPointerException(formattedErrorMessage("the given array of objects should not be null"));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> does not have duplicates.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> has duplicates.
   */
  public ListAssert doesNotHaveDuplicates() {
    isNotNull();
    Collection<?> duplicates = duplicatesFrom(actual);
    if (!duplicates.isEmpty())
      fail(concat("list:", inBrackets(actual), " contains duplicate(s):", inBrackets(duplicates)));
    return this;
  }

  /** {@inheritDoc} */
  public ListAssert as(String description) {
    description(description);
    return this;
  }

  /** {@inheritDoc} */
  public ListAssert describedAs(String description) {
    return as(description);
  }

  /** {@inheritDoc} */
  public ListAssert as(Description description) {
    description(description);
    return this;
  }

  /** {@inheritDoc} */
  public ListAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>{@link List}</code> satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> does not satisfy the given condition.
   * @see #is(Condition)
   */
  public ListAssert satisfies(Condition<List<?>> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>{@link List}</code> does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> satisfies the given condition.
   * @see #isNot(Condition)
   */
  public ListAssert doesNotSatisfy(Condition<List<?>> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Alias for <code>{@link #satisfies(Condition)}</code>.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> does not satisfy the given condition.
   * @since 1.2
   */
  public ListAssert is(Condition<List<?>> condition) {
    assertIs(condition);
    return this;
  }

  /**
   * Alias for <code>{@link #doesNotSatisfy(Condition)}</code>.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws NullPointerException if the given condition is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> satisfies the given condition.
   * @since 1.2
   */
  public ListAssert isNot(Condition<List<?>> condition) {
    assertIsNot(condition);
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
    if (actualSize == expected) return this;
    failIfCustomMessageIsSet();
    throw failure(concat(
          "expected size:", inBrackets(expected)," but was:", inBrackets(actualSize), " for list:", inBrackets(actual)));
  }

  /**
   * Returns the number of elements in the actual <code>{@link List}</code>.
   * @return the number of elements in the actual <code>List</code>.
   */
  protected int actualGroupSize() {
    isNotNull();
    return actual.size();
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> is not empty.
   */
  public void isEmpty() {
    isNotNull();
    if (!Collections.isEmpty(actual)) fail(concat("expecting empty list, but was:", inBrackets(actual)));
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
    fail(concat("expecting a null or empty list, but was:", inBrackets(actual)));
  }

  /**
   * Verifies that the actual <code>{@link List}</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   */
  public ListAssert isNotNull() {
    if (actual != null) return this;
    failIfCustomMessageIsSet();
    throw failure("expecting a non-null list, but it was null");
  }

  /**
   * Verifies that the actual <code>{@link List}</code> contains the given objects, in the same order. This method works
   * just like <code>{@link #isEqualTo(List)}</code>, with the difference that internally the given array is
   * converted to a <code>List</code>.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>List</code> is <code>null</code>.
   * @throws AssertionError if the given array is <code>null</code>.
   * @throws AssertionError if the actual <code>List</code> does not contain the given objects.
   */
  public ListAssert containsExactly(Object... objects) {
    validateIsNotNull(objects);
    return isNotNull().isEqualTo(list(objects));
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

  /** {@inheritDoc} */
  public ListAssert overridingErrorMessage(String message) {
    replaceDefaultErrorMessagesWith(message);
    return this;
  }
}
