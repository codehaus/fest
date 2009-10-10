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

import static org.fest.assertions.Primitives.asByte;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ByteAssert#isGreaterThanOrEqualTo(byte)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ByteAssert_isGreaterThanOrEqualTo_Test implements Assert_isGreaterThanOrEqualTo_TestCase {

  @Test
  public void should_pass_if_actual_is_greater_than_expected() {
    new ByteAssert(asByte(8)).isGreaterThanOrEqualTo(asByte(6));
  }

  @Test
  public void should_pass_if_actual_is_equal_to_expected() {
    new ByteAssert(asByte(8)).isGreaterThanOrEqualTo(asByte(8));
  }

  @Test
  public void should_fail_if_actual_is_less_than_expected() {
    expectAssertionError("actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isGreaterThanOrEqualTo(asByte(8));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_less_than_expected() {
    expectAssertionError("[A Test] actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test")
                                 .isGreaterThanOrEqualTo(asByte(8));
      }
    });
  }
}
