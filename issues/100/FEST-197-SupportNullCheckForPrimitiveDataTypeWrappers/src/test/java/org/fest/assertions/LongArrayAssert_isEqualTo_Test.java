/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ArrayFactory.longArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link LongArrayAssert#isEqualTo(long[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class LongArrayAssert_isEqualTo_Test implements GenericAssert_isEqualTo_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_equal() {
    new LongArrayAssert(6, 8).isEqualTo(longArray(6, 8));
  }

  @Test
  public void should_pass_if_both_actual_and_expected_are_null() {
    new LongArrayAssert(null).isEqualTo(null);
  }

  @Test
  public void should_fail_if_actual_and_expected_are_not_equal() {
    expectAssertionError("expected:<[10, 2]> but was:<[6, 8]>").on(new CodeToTest() {
      public void run() {
        new LongArrayAssert(6, 8).isEqualTo(longArray(10, 2));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_not_equal() {
    expectAssertionError("[A Test] expected:<[10, 2]> but was:<[6, 8]>").on(new CodeToTest() {
      public void run() {
        new LongArrayAssert(6, 8).as("A Test")
                                 .isEqualTo(longArray(10, 2));
      }
    });
  }
}
