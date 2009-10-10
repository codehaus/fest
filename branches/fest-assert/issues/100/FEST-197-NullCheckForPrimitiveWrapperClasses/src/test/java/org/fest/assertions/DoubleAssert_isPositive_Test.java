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

import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Test for <code>{@link DoubleAssert#isPositive()}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class DoubleAssert_isPositive_Test implements Assert_isPositive_TestCase {

  @Test
  public void should_pass_if_actual_is_positive() {
    new DoubleAssert(6.68).isPositive();
  }

  @Test
  public void should_fail_if_actual_is_zero() {
    expectAssertionError("actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).isPositive();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_zero() {
    expectAssertionError("[A Test] actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).as("A Test")
                             .isPositive();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_negative() {
    expectAssertionError("actual value:<-6.68> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(-6.68).isPositive();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_negative() {
    expectAssertionError("[A Test] actual value:<-6.68> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(-6.68).as("A Test")
                               .isPositive();
      }
    });
  }
}
