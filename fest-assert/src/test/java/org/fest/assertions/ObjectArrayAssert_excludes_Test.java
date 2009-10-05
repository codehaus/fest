/*
 * Created on Mar 1, 2007
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

import static org.fest.assertions.CommonFailures.*;
import static org.fest.assertions.EmptyArrays.emptyObjectArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ObjectArrayAssert#excludes(Object...)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssert_excludes_Test implements GroupAssert_excludes_TestCase {

  @Test
  public void should_pass_if_actual_excludes_given_value() {
    new ObjectArrayAssert(6, 8).excludes(10);
  }

  @Test
  public void should_pass_if_actual_excludes_given_values() {
    new ObjectArrayAssert(6, 8).excludes(10, 2);
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        Object[] actual = null;
        new ObjectArrayAssert(actual).excludes(6, 8);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        Object[] actual = null;
        new ObjectArrayAssert(actual).as("A Test")
                                   .excludes(6, 8);
      }
    });
  }

  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] expected = null;
        new ObjectArrayAssert(emptyObjectArray()).excludes(expected);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] expected = null;
        new ObjectArrayAssert(emptyObjectArray()).as("A Test")
                                                 .excludes(expected);
      }
    });
  }

  @Test
  public void should_fail_if_actual_contains_given_values() {
    expectAssertionError("array:<[6, 8]> does not exclude element(s):<[6]>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(6, 8).excludes(6);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_contains_given_values() {
    expectAssertionError("[A Test] array:<[6, 8]> does not exclude element(s):<[6]>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(6, 8).as("A Test")
                                   .excludes(6);
      }
    });
  }
}
