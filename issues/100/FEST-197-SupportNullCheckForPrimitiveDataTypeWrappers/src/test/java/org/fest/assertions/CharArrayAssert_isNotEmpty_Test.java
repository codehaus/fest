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
import static org.fest.assertions.EmptyArrays.emptyCharArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CharArrayAssert#isNotEmpty()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CharArrayAssert_isNotEmpty_Test implements GroupAssert_isNotEmpty_TestCase {

  @Test
  public void should_pass_if_actual_is_not_empty() {
    new CharArrayAssert('a', 'b').isNotEmpty();
  }

  @Test
  public void should_fail_if_actual_is_empty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(emptyCharArray()).isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(emptyCharArray()).as("A Test")
                                             .isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(null).isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(null).as("A Test")
                                 .isNotEmpty();
      }
    });
  }
}