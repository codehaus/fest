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
 * Tests for <code>{@link ShortAssert#isZero()}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ShortAssert_isZero_Test implements Assert_isZero_TestCase {

  @Test
  public void should_pass_if_actual_is_zero() {
    new ShortAssert(asShort(0)).isZero();
  }

  @Test
  public void should_fail_if_actual_is_not_zero() {
    expectAssertionError("expected:<0> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).isZero();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_zero() {
    expectAssertionError("[A Test] expected:<0> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ShortAssert(asShort(6)).as("A Test")
                                   .isZero();
      }
    });
  }
}