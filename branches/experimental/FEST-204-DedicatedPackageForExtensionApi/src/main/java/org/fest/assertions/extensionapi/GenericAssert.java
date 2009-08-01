/*
 * Created on May 21, 2007
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
package org.fest.assertions.extensionapi;

import static org.fest.assertions.extensionapi.FailConditional.*;
import static org.fest.assertions.extensionapi.Formatting.*;
import static org.fest.util.Strings.concat;

import org.fest.assertions.*;

/**
 * Understands a template for assertion methods.
 * @param <T> the type of object implementations of this template can verify.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class GenericAssert<T> extends Assert implements NullableAssert<GenericAssert<T>> {

  protected final T actual;

  /**
   * Creates a new <code>{@link GenericAssert}</code>.
   * @param actual the actual target to verify.
   */
  protected GenericAssert(T actual) {
    this.actual = actual;
  }

  public T getActualValue() {
    return actual;
  }

  public abstract void isNull();

  public abstract GenericAssert<T> isNotNull();

  /**
   * Verifies that the actual value satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual value does not satisfy the given condition.
   */
  protected abstract GenericAssert<T> satisfies(Condition<T> condition);

  /**
   * Verifies that the actual value does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual value does satisfies the given condition.
   */
  protected abstract GenericAssert<T> doesNotSatisfy(Condition<T> condition);

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(val).<strong>as</strong>(&quot;name&quot;).isEqualTo(&quot;Frodo&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract GenericAssert<T> as(String description);

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a href="http://groovy.codehaus.org/"
   * target="_blank">Groovy</a>. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(val).<strong>describedAs</strong>(&quot;name&quot;).isEqualTo(&quot;Frodo&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract GenericAssert<T> describedAs(String description);

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(val).<strong>as</strong>(new BasicDescription(&quot;name&quot;)).isEqualTo(&quot;Frodo&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract GenericAssert<T> as(Description description);

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * 
   * <pre>
   * assertThat(val).<strong>describedAs</strong>(new BasicDescription(&quot;name&quot;)).isEqualTo(&quot;Frodo&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract GenericAssert<T> describedAs(Description description);

  /**
   * Verifies that the actual value is equal to the given one.
   * @param expected the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is not equal to the given one.
   */
  protected abstract GenericAssert<T> isEqualTo(T expected);

  /**
   * Verifies that the actual value is not equal to the given one.
   * @param other the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is equal to the given one.
   */
  protected abstract GenericAssert<T> isNotEqualTo(T other);

  /**
   * Verifies that the actual value object instance is the same object instance as the given one.
   * @param expected the given object instance to compare the actual value instance to.
   * @return this assertion object.
   * @throws AssertionError if the actual value instance is not the same as the given one.
   */
  protected abstract GenericAssert<T> isSameAs(T expected);

  /**
   * Verifies that the actual value object instance is not the same object instance as the given one.
   * @param other the given object instance to compare the actual value instance to.
   * @return this assertion object.
   * @throws AssertionError if the actual value instance is the same as the given one.
   */
  protected abstract GenericAssert<T> isNotSameAs(T other);

  /**
   * Verifies that the actual value satisfies the given condition.
   * @param condition the condition to check.
   * @throws AssertionError if the actual value does not satisfy the given condition.
   */
  protected final void assertSatisfies(Condition<T> condition) {
    validate(condition);
    if (condition.matches(actual)) return;
    fail(errorMessageIfConditionNotSatisfied(condition));
  }

  private String errorMessageIfConditionNotSatisfied(Condition<T> condition) {
    String message = concat("actual value:", inBrackets(actual), " should satisfy condition");
    return condition.addDescriptionTo(message);
  }

  /**
   * Verifies that the actual value does not satisfy the given condition.
   * @param condition the condition to check.
   * @throws AssertionError if the actual value satisfies the given condition.
   */
  protected final void assertDoesNotSatisfy(Condition<T> condition) {
    validate(condition);
    if (!condition.matches(actual)) return;
    fail(errorMessageIfConditionSatisfied(condition));
  }

  private String errorMessageIfConditionSatisfied(Condition<T> condition) {
    String message = concat("actual value:", inBrackets(actual), " should not satisfy condition");
    return condition.addDescriptionTo(message);
  }

  private void validate(Condition<T> condition) {
    if (condition == null) throw new IllegalArgumentException("Condition to check should be null");
  }

  /**
   * Verifies that the actual value is equal to the given one.
   * @param expected the value to compare the actual value to.
   * @throws AssertionError if the actual value is not equal to the given one.
   */
  protected final void assertEqualTo(T expected) {
    failIfNotEqual(description(), actual, expected);
  }

  /**
   * Verifies that the actual value is not equal to the given one.
   * @param obj the value to compare the actual value to.
   * @throws AssertionError if the actual value is equal to the given one.
   */
  protected final void assertNotEqualTo(T obj) {
    failIfEqual(description(), actual, obj);
  }

  /**
   * Verifies that the actual value is not <code>null</code>.
   * @throws AssertionError if the actual value is <code>null</code>.
   */
  protected final void assertNotNull() {
    failIfNull(description(), actual);
  }

  /**
   * Verifies that the actual value is <code>null</code>.
   * @throws AssertionError if the actual value is not <code>null</code>.
   */
  protected final void assertNull() {
    failIfNotNull(description(), actual);
  }

  /**
   * Verifies that the actual value object is the same object instance as the given one.
   * @param expected the value object to compare the actual value object to.
   * @throws AssertionError if the actual value object is not the same instance as the given one.
   */
  protected final void assertSameAs(T expected) {
    failIfNotSame(description(), actual, expected);
  }

  /**
   * Verifies that the actual value object is not the same object instance as the given one.
   * @param expected the value object to compare the actual value object to.
   * @throws AssertionError if the actual value object is the same instance as the given one.
   */
  protected final void assertNotSameAs(T expected) {
    failIfSame(description(), actual, expected);
  }

  /**
   * Fails by throwing an <code>{@link AssertionError}</code>.
   * @param reason the reason for the failure, used as the message for the thrown exception.
   * @return the thrown <code>AssertionError</code>.
   * @throws AssertionError using the given reason as the message.
   */
  protected final AssertionError fail(String reason) {
    throw Fail.fail(formatted(reason));
  }

  /**
   * Fails by throwing an <code>{@link AssertionError}</code>.
   * @param reason the reason for the failure, used as the message for the thrown exception.
   * @param cause the root cause of the failure, included in the thrown exception.
   */
  protected final void fail(String reason, Throwable cause) {
    Fail.fail(formatted(reason), cause);
  }

  private String formatted(String reason) {
    return concat(format(description()), reason);
  }
}
