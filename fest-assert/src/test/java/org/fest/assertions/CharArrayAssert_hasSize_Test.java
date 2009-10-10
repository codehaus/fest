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
import static org.fest.assertions.ArrayFactory.charArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CharArrayAssert#hasSize(int)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CharArrayAssert_hasSize_Test implements Assert_hasSize_TestCase {

  @Test
  public void should_pass_if_actual_has_expected_size() {
    new CharArrayAssert(charArray('a', 'b', 'c')).hasSize(3);
  }


  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(null).hasSize(1);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(null).as("A Test")
                                 .hasSize(1);
      }
    });
  }

  @Test
  public void should_fail_if_actual_does_not_have_expected_size() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[a]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(charArray('a')).hasSize(2);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_have_expected_size() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[a]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(charArray('a')).as("A Test")
                                           .hasSize(2);
      }
    });
  }
}