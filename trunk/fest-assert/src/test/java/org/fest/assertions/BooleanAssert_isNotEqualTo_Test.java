/*
 * Created on Mar 19, 2007
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

import static org.fest.assertions.FailureMessages.unexpectedEqual;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link BooleanAssert#isNotEqualTo(boolean)}</code>.
 *
 * @author Alex Ruiz
 * @author David DIDIER
 */
public class BooleanAssert_isNotEqualTo_Test implements Assert_isNotEqualTo_TestCase {

  private static boolean actual;
  private static BooleanAssert assertObject;

  @BeforeClass
  public static void setUpOnce() {
    actual = false;
    assertObject = new BooleanAssert(actual);
  }

  @Test
  public void should_pass_if_actual_and_expected_are_not_equal() {
    assertObject.isNotEqualTo(true);
  }

  @Test
  public void should_fail_if_actual_and_expected_are_equal() {
    expectAssertionError(unexpectedEqual(actual, actual)).on(new CodeToTest() {
      public void run() {
        assertObject.isNotEqualTo(false);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_equal() {
    expectAssertionError(unexpectedEqual("A Test", actual, actual)).on(new CodeToTest() {
      public void run() {
        assertObject.as("A Test")
                    .isNotEqualTo(false);
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_and_expected_are_equal() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        assertObject.overridingErrorMessage("My custom message")
                    .isNotEqualTo(false);
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_and_expected_are_equal() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        assertObject.as("A Test")
                    .overridingErrorMessage("My custom message")
                    .isNotEqualTo(false);
      }
    });
  }
}
