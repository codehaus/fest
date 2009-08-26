/*
 * Created on Dec 23, 2007
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

import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>{@link Throwable}</code>. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Throwable)}</code>.
 *
 * @author David DIDIER
 * @author Alex Ruiz
 */
public final class ThrowableAssert extends GenericAssert<Throwable> {

  private final ObjectAssert objectAssert;

  /**
   * Creates a new <code>ThrowableAssert</code>.
   * @param actual the actual <code>Throwable</code> to test.
   */
  ThrowableAssert(Throwable actual) {
    super(actual);
    objectAssert = new ObjectAssert(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>.
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(error).<strong>as</strong>(&quot;Number Formatting&quot;).isInstanceOf(NumberFormatException.class);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert as(String description) {
    objectAssert.as(description);
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
   * assertThat(error).<strong>describedAs</strong>(&quot;Number Formatting&quot;).isInstanceOf(NumberFormatException.class);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>.
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(error).<strong>as</strong>(new BasicDescription(&quot;Number Formatting&quot;)).isInstanceOf(NumberFormatException.class);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert as(Description description) {
    objectAssert.as(description);
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(error).<strong>describedAs</strong>(new BasicDescription(&quot;Number Formatting&quot;)).isInstanceOf(NumberFormatException.class);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is an instance of the given type.
   * @param type the type to check the actual <code>Throwable</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is not an instance of the given type.
   * @throws IllegalArgumentException if the given type is <code>null</code>.
   */
  public ThrowableAssert isInstanceOf(Class<? extends Throwable> type) {
    objectAssert.isInstanceOf(type);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is an instance of the given type. In order for the assertion to
   * pass, the type of the actual <code>Throwable</code> has to be exactly the same as the given type.
   * @param type the type to check the actual <code>Throwable</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Throwable</code> is not an instance of the given type.
   * @throws IllegalArgumentException if the given type is <code>null</code>.
   */
  public ThrowableAssert isExactlyInstanceOf(Class<?> type) {
    isNotNull();
    objectAssert.validateNotNull(type);
    Class<?> current = actual.getClass();
    if (!type.equals(current))
      fail(concat("expected exactly the same type:", inBrackets(type), " but was:", inBrackets(current)));
    return this;
  }

  /**
   * Verifies that the message of the actual <code>Throwable</code> is equal to the given one.
   * @param message the expected message.
   * @return this assertion error.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the message of the actual <code>Throwable</code> is not equal to the given one.
   */
  public ThrowableAssert hasMessage(String message) {
    isNotNull();
    failIfNotEqual(description(), actual.getMessage(), message);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> does not have a cause.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>Throwable</code> has a cause.
   */
  public ThrowableAssert hasNoCause() {
    isNotNull();
    Throwable actualCause = actual.getCause();
    if (actualCause != null)
      fail(concat("expected exception without cause, but cause was:", inBrackets(actualCause.getClass())));
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is equal to the given one.
   * @param expected the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is not equal to the given one.
   */
  public ThrowableAssert isEqualTo(Throwable expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not equal to the given one.
   * @param other the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is equal to the given one.
   */
  public ThrowableAssert isNotEqualTo(Throwable other) {
    assertNotEqualTo(other);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not <code>null</code>.
   *
   * @return this assertion object.
   *
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   */
  public ThrowableAssert isNotNull() {
    assertNotNull();
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not the same as the given one.
   * @param other the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is the same as the given one.
   */
  public ThrowableAssert isNotSameAs(Throwable other) {
    assertNotSameAs(other);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is the same as the given one.
   * @param expected the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> is not the same as the given one.
   */
  public ThrowableAssert isSameAs(Throwable expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public ThrowableAssert satisfies(Condition<Throwable> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is <code>null</code>.
   */
  public ThrowableAssert doesNotSatisfy(Condition<Throwable> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }
}
