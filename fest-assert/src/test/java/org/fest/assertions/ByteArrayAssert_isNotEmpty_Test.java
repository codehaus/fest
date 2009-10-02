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

import static org.fest.assertions.CommonFailures.expectAssertionErrorIfArrayIsNull;
import static org.fest.assertions.CommonFailures.expectAssertionErrorWithDescriptionIfArrayIsNull;
import static org.fest.assertions.EmptyArrays.emptyByteArray;
import static org.fest.assertions.Primitives.asByte;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ByteArrayAssert#isNotEmpty()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ByteArrayAssert_isNotEmpty_Test implements GroupAssert_isNotEmpty_TestCase {

  @Test
  public void should_pass_if_actual_is_not_empty() {
    new ByteArrayAssert(asByte(8), asByte(6)).isNotEmpty();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(emptyByteArray()).isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(emptyByteArray()).as("A Test")
                                             .isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_empty() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(null).isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(null).as("A Test")
                                 .isNotEmpty();
      }
    });
  }
}
