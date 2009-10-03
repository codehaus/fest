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

import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.assertions.PrimitiveFail.*;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>char</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(char)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class CharAssert extends PrimitiveAssert {

  private final char actual;

  /**
   * Creates a new </code>{@link CharAssert}</code>.
   * @param actual the target to verify.
   */
  protected CharAssert(char actual) {
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
  public CharAssert as(String description) {
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
  public CharAssert describedAs(String description) {
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
  public CharAssert as(Description description) {
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
  public CharAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>char</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not equal to the given one.
   */
  public CharAssert isEqualTo(char expected) {
    failIfNotEqual(rawDescription(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is not equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is equal to the given one.
   */
  public CharAssert isNotEqualTo(char other) {
    failIfEqual(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is greater than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not greater than the given one.
   */
  public CharAssert isGreaterThan(char other) {
    failIfNotGreaterThan(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is less than the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not less than the given one.
   */
  public CharAssert isLessThan(char other) {
    failIfNotLessThan(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is greater or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not greater than or equal to the given one.
   */
  public CharAssert isGreaterThanOrEqualTo(char other) {
    failIfNotGreaterThanOrEqualTo(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is less or equal to the given one.
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not less than or equal to the given one.
   */
  public CharAssert isLessThanOrEqualTo(char other) {
    failIfNotLessThanOrEqualTo(rawDescription(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is an uppercase value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not an uppercase value.
   */
  public CharAssert isUpperCase() {
    if (!Character.isUpperCase(actual)) fail(concat(inBrackets(actual), " should be an uppercase character"));
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is an lowercase value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not an lowercase value.
   */
  public CharAssert isLowerCase() {
    if (!Character.isLowerCase(actual)) fail(concat(inBrackets(actual), " should be a lowercase character"));
    return this;
  }
}
