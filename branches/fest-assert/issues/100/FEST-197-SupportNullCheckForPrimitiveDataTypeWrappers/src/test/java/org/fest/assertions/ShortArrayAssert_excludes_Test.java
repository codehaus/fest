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
import static org.fest.assertions.EmptyArrays.emptyShortArray;
import static org.fest.assertions.Primitives.asShort;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ShortArrayAssert#excludes(short...)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ShortArrayAssert_excludes_Test implements GroupAssert_excludes_TestCase {

  @Test
  public void should_pass_if_actual_excludes_given_value() {
    new ShortArrayAssert(asShort(8)).excludes(asShort(7));
  }

  @Test
  public void should_pass_if_actual_excludes_given_values() {
    new ShortArrayAssert(asShort(8), asShort(6)).excludes(asShort(7), asShort(10));
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(null).excludes(asShort(8), asShort(6));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(null).as("A Test")
                                  .excludes(asShort(8), asShort(6));
      }
    });
  }

  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of shorts should not be null").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(emptyShortArray()).excludes(null);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of shorts should not be null").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(emptyShortArray()).as("A Test")
                                               .excludes(null);
      }
    });
  }

  @Test
  public void should_fail_if_actual_contains_given_values() {
    expectAssertionError("array:<[8, 6]> does not exclude element(s):<[8]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(8), asShort(6)).excludes(asShort(8));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_contains_given_values() {
    expectAssertionError("[A Test] array:<[8, 6]> does not exclude element(s):<[8]>").on(new CodeToTest() {
      public void run() {
        new ShortArrayAssert(asShort(8), asShort(6)).as("A Test")
                                                    .excludes(asShort(8));
      }
    });
  }
}