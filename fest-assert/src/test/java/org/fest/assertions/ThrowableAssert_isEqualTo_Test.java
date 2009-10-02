/*
 * Created on Jan 10, 2007
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
 * Tests for <code>{@link ThrowableAssert#isEqualTo(Throwable)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ThrowableAssert_isEqualTo_Test implements GenericAssert_isEqualTo_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_equal() {
    Exception actual = new Exception();
    new ThrowableAssert(actual).isEqualTo(actual);
  }

  @Test
  public void should_pass_if_both_actual_and_expected_are_null() {
    new ThrowableAssert(null).isEqualTo(null);
  }

  @Test
  public void should_fail_if_actual_and_expected_are_not_equal() {
    String message = "expected:<java.lang.IllegalArgumentException> but was:<java.lang.Exception>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).isEqualTo(new IllegalArgumentException());
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_not_equal() {
    String message = "[A Test] expected:<java.lang.IllegalArgumentException> but was:<java.lang.Exception>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).as("A Test")
                                            .isEqualTo(new IllegalArgumentException());
      }
    });
  }
}
