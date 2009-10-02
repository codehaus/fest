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

import static org.fest.assertions.CommonFailures.*;
import static org.fest.assertions.EmptyArrays.emptyLongArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link LongArrayAssert#isEmpty()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class LongArrayAssert_isEmpty_Test implements GroupAssert_isEmpty_TestCase {

  @Test
  public void should_pass_if_actual_is_empty() {
    new LongArrayAssert(emptyLongArray()).isEmpty();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new LongArrayAssert(null).isEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new LongArrayAssert(null).as("A Test")
                                 .isEmpty();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_not_empty() {
    expectAssertionError("expecting empty array, but was:<[6, 8]>").on(new CodeToTest() {
      public void run() {
        new LongArrayAssert(6, 8).isEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_empty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[6, 8]>").on(new CodeToTest() {
      public void run() {
        new LongArrayAssert(6, 8).as("A Test")
                                 .isEmpty();
      }
    });
  }
}
