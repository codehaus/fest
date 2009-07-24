package org.fest.assertions;

import static java.lang.Math.abs;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.assertions.PrimitiveFail.*;
import static org.fest.util.Strings.concat;

/**
 * Understands Assertion methods for <code>Double</code>. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(Double)}</code>.
 * 
 * @author Yvonne Wang
 * @author David DIDIER
 */
public final class DoubleAssert extends GenericAssert<Double> {

  private static final Double ZERO = Double.valueOf(0.0d);

  DoubleAssert(Double actual) {
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
   * assertThat(value).&lt;strong&gt;as&lt;/strong&gt;(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleAssert as(String description) {
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
   * assertThat(value).&lt;strong&gt;describedAs&lt;/strong&gt;(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleAssert describedAs(String description) {
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
   * assertThat(value).&lt;strong&gt;as&lt;/strong&gt;(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleAssert as(Description description) {
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
   * assertThat(value).&lt;strong&gt;describedAs&lt;/strong&gt;(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * 
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>Double</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not equal to the given one.
   */
  public DoubleAssert isEqualTo(Double expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is not equal to the given one.
   * @param value the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is equal to the given one.
   */
  public DoubleAssert isNotEqualTo(Double value) {
    assertNotEqualTo(value);
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is greater than the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not greater than the given one.
   */
  public DoubleAssert isGreaterThan(Double value) {
    failIfNotGreaterThan(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is less than the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not less than the given one.
   */
  public DoubleAssert isLessThan(Double value) {
    failIfNotLessThan(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is greater or equal to the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not greater than or equal to the given one.
   */
  public DoubleAssert isGreaterThanOrEqualTo(Double value) {
    failIfNotGreaterThanOrEqualTo(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is less or equal to the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not less than or equal to the given one.
   */
  public DoubleAssert isLessThanOrEqualTo(Double value) {
    failIfNotLessThanOrEqualTo(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not positive.
   */
  public DoubleAssert isPositive() {
    return isGreaterThan(ZERO);
  }

  /**
   * Verifies that the actual <code>Double</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not negative.
   */
  public DoubleAssert isNegative() {
    return isLessThan(ZERO);
  }

  /**
   * Verifies that the actual <code>Double</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not equal to zero.
   */
  public DoubleAssert isZero() {
    return isEqualTo(ZERO);
  }

  /**
   * Verifies that the actual <code>Double</code> value is equal to <code>{@link Double#NaN}</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not equal to <code>NAN</code>.
   */
  public DoubleAssert isNaN() {
    return isEqualTo(Double.NaN);
  }

  /**
   * Verifies that the actual <code>Double</code> value is equal to the given one, within a positive delta.
   * @param expected the value to compare the actual one to.
   * @param delta the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not equal to the given one.
   * @deprecated use method <code>{@link #isEqualTo(Double, org.fest.assertions.Delta)}</code> instead. This method will
   *             be removed in version 2.0.
   */
  public DoubleAssert isEqualTo(Double expected, Delta delta) {
    if (Double.compare(expected, actual) == 0) return this;
    if (!(abs(expected - actual) <= delta.value))
      fail(concat(errorMessageIfNotEqual(actual, expected), " using delta:", inBrackets(delta.value)));
    return this;
  }

  /**
   * Verifies that the actual <code>Double</code> value is equal to the given one, within a positive delta.
   * @param expected the value to compare the actual one to.
   * @param delta the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Double</code> value is not equal to the given one.
   * @since 1.1
   */
  public DoubleAssert isEqualTo(Double expected, org.fest.assertions.Delta delta) {
    if (Double.compare(expected, actual) == 0) return this;
    if (!(abs(expected - actual) <= delta.value()))
      fail(concat(errorMessageIfNotEqual(actual, expected), " using delta:", inBrackets(delta.value())));
    return this;
  }

  /**
   * Creates a new holder for a delta value to be used in
   * <code>{@link DoubleAssert#isEqualTo(Double, org.fest.assertions.DoubleAssert.Delta)}</code>.
   * @param d the delta value.
   * @return a new delta value holder.
   * @deprecated use method <code>{@link org.fest.assertions.Delta#delta(double)}</code> instead. This method will be
   *             removed in version 2.0.
   */
  public static Delta delta(Double d) {
    return new Delta(d);
  }

  /**
   * Holds a delta value to be used in
   * <code>{@link DoubleAssert#isEqualTo(Double, org.fest.assertions.DoubleAssert.Delta)}</code>.
   * @deprecated use top-level class <code>{@link org.fest.assertions.Delta}</code> instead. This class will be removed
   *             in version 2.0.
   */
  public static class Delta {
    final double value;

    private Delta(double value) {
      this.value = value;
    }
  }

  /** {@inheritDoc} */
  @Override
  public DoubleAssert doesNotSatisfy(Condition<Double> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DoubleAssert isNotNull() {
    assertNotNull();
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DoubleAssert isNotSameAs(Double other) {
    assertNotSameAs(other);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void isNull() {
    assertNull();
  }

  /** {@inheritDoc} */
  @Override
  public DoubleAssert isSameAs(Double expected) {
    assertSameAs(expected);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DoubleAssert satisfies(Condition<Double> condition) {
    assertSatisfies(condition);
    return this;
  }
}
