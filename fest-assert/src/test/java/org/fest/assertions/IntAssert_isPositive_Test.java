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
 * Copyright @2007-2010 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link IntAssert#isPositive()}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class IntAssert_isPositive_Test implements NumberAssert_isPositive_TestCase {

  @Test
  public void should_pass_if_actual_is_positive() {
    new IntAssert(6).isPositive();
  }

  @Test
  public void should_fail_if_actual_is_negative() {
    expectAssertionError("actual value:<-2> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new IntAssert(-2).isPositive();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_negative() {
    expectAssertionError("[A Test] actual value:<-2> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new IntAssert(-2).as("A Test")
                         .isPositive();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_is_negative() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new IntAssert(-2).overridingErrorMessage("My custom message")
                         .isPositive();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_is_negative() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new IntAssert(-2).as("A Test")
                         .overridingErrorMessage("My custom message")
                         .isPositive();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_zero() {
    expectAssertionError("actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new IntAssert(0).isPositive();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_zero() {
    expectAssertionError("[A Test] actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new IntAssert(0).as("A Test")
                        .isPositive();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_is_zero() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new IntAssert(0).overridingErrorMessage("My custom message")
                        .isPositive();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_is_zero() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new IntAssert(0).as("A Test")
                        .overridingErrorMessage("My custom message")
                        .isPositive();
      }
    });
  }
}
