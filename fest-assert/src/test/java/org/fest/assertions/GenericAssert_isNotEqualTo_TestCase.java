/*
 * Created on Apr 26, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.FailureMessages.unexpectedEqual;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.*;

/**
 * Base class for testing <code>{@link GenericAssert#isNotEqualTo(Object)}</code>.
 * @param <T> The type supported by the implementation of the {@code GenericAssert} to test.
 *
 * @author Ansgar Konermann
 * @author Alex Ruiz
 */
public abstract class GenericAssert_isNotEqualTo_TestCase<T> extends GenericAssert_TestCase<T> implements
    Assert_isNotEqualTo_TestCase {

  private GenericAssert<T> assertions;
  private T actual;

  @Before
  public final void setUp() {
    actual = notNullValue();
    assertions = assertionsFor(actual);
  }

  @Test
  public final void should_pass_if_actual_and_expected_are_not_equal() {
    assertions.isNotEqualTo(unequalValue());
  }

  protected abstract T unequalValue();

  @Test
  public final void should_fail_if_actual_and_expected_are_equal() {
    expectAssertionError(unexpectedEqual(actual, actual)).on(new CodeToTest() {
      public void run() {
        assertions.isNotEqualTo(actual);
      }
    });
  }

  @Test
  public final void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_equal() {
    expectAssertionError(unexpectedEqual("A Test", actual, actual)).on(new CodeToTest() {
      public void run() {
        assertions.as("A Test")
                  .isNotEqualTo(actual);
      }
    });
  }

  @Test
  public final void should_fail_with_custom_message_if_actual_and_expected_are_equal() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        assertions.overridingErrorMessage("My custom message")
                  .isNotEqualTo(actual);
      }
    });
  }

  @Test
  public final void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_and_expected_are_equal() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        assertions.as("A Test")
                  .overridingErrorMessage("My custom message")
                  .isNotEqualTo(actual);
      }
    });
  }
}
