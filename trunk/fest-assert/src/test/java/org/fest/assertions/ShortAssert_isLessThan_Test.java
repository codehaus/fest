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

import static org.fest.assertions.Primitives.asShort;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ShortAssert#isLessThan(short)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ShortAssert_isLessThan_Test implements Assert_isLessThan_TestCase {

  @Test
  public void should_pass_if_actual_is_less_than_expected() {
    new ShortAssert(asShort(2)).isLessThan(asShort(6));
  }

  @Test
  public void should_fail_if_actual_is_equal_to_expected() {
    expectAssertionError("actual value:<6> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_equal_to_expected() {
    expectAssertionError("[A Test] actual value:<6> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).as("A Test")
                                   .isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_is_equal_to_expected() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).overridingErrorMessage("My custom message")
                                   .isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_is_equal_to_expected() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).as("A Test")
                                   .overridingErrorMessage("My custom message")
                                   .isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_greater_than_expected() {
    expectAssertionError("actual value:<10> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(10)).isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_greater_than_expected() {
    expectAssertionError("[A Test] actual value:<10> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(10)).as("A Test")
                                    .isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_is_greater_than_expected() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(10)).overridingErrorMessage("My custom message")
                                    .isLessThan(asShort(6));
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_is_greater_than_expected() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(10)).as("A Test")
                                    .overridingErrorMessage("My custom message")
                                    .isLessThan(asShort(6));
      }
    });
  }


}