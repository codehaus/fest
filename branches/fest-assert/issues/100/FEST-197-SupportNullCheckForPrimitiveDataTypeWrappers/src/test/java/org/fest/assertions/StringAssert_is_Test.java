/*
 * Created on Oct 7, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.CommonFailures.expectErrorIfConditionIsNull;
import static org.fest.assertions.UpperCase.upperCase;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link StringAssert#is(Condition)}</code>.
 *
 * @author Alex Ruiz
 */
public class StringAssert_is_Test implements GenericAssert_satisfies_TestCase {

  @Test
  public void should_pass_if_condition_is_satisfied() {
    new StringAssert("HELLO").is(upperCase());
  }

  @Test
  public void should_throw_error_if_condition_is_null() {
    expectErrorIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new StringAssert("").is(null);
      }
    });
  }

  @Test
  public void should_fail_if_condition_is_not_satisfied() {
    expectAssertionError("actual value:<'hello'> should be:<UpperCase>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").is(upperCase());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_condition_is_not_satisfied() {
    expectAssertionError("[Test] actual value:<'hello'> should be:<UpperCase>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").as("Test")
                                 .is(upperCase());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_condition_if_condition_is_not_satisfied() {
    expectAssertionError("actual value:<'hello'> should be:<uppercase>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").is(upperCase().as("uppercase"));
      }
    });
  }

  @Test
  public void should_fail_and_display_descriptions_of_assertion_and_condition_if_condition_is_not_satisfied() {
    expectAssertionError("[Test] actual value:<'hello'> should be:<uppercase>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").as("Test")
                                 .is(upperCase().as("uppercase"));
      }
    });
  }
}