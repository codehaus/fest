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

import static org.fest.assertions.CommonFailures.expectErrorIfArrayIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfArrayIsNull;
import static org.fest.assertions.EmptyArrays.emptyFloatArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link FloatArrayAssert#isEmpty()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssert_isEmpty_Test implements GroupAssert_isEmpty_TestCase {

  @Test
  public void should_pass_if_actual_is_empty() {
    new FloatArrayAssert(emptyFloatArray()).isEmpty();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(null).isEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(null).as("A Test")
                                    .isEmpty();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_not_empty() {
    expectAssertionError("expecting empty array, but was:<[6.0]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(6f).isEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_empty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[6.0]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(6f).as("A Test")
                                .isEmpty();
      }
    });
  }
}