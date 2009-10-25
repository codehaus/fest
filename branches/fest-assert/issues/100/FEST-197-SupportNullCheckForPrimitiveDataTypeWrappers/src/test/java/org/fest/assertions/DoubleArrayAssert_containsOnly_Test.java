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
import static org.fest.assertions.EmptyArrays.emptyDoubleArray;
import static org.fest.assertions.ArrayFactory.doubleArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link DoubleArrayAssert#containsOnly(double...)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class DoubleArrayAssert_containsOnly_Test implements GroupAssert_containsOnly_TestCase {

  @Test
  public void should_pass_if_actual_contains_only_given_values() {
    new DoubleArrayAssert(98.6, 8.66).containsOnly(98.6, 8.66);
  }

  @Test
  public void should_pass_if_actual_contains_only_given_values_in_different_order() {
    new DoubleArrayAssert(98.6, 8.66).containsOnly(8.66, 98.6);
  }

  @Test
  public void should_fail_if_actual_is_empty_and_expecting_at_least_one_element() {
    expectAssertionError("array:<[]> does not contain element(s):<[5323.2]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(emptyDoubleArray()).containsOnly(doubleArray(5323.2));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty_and_expecting_at_least_one_element() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[5323.2]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(emptyDoubleArray()).as("A Test")
                                                 .containsOnly(doubleArray(5323.2));
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(null).containsOnly(doubleArray(5323.2));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(null).as("A Test")
                                   .containsOnly(doubleArray(5323.2));
      }
    });
  }

  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of doubles should not be null").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(emptyDoubleArray()).containsOnly(null);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of doubles should not be null").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(emptyDoubleArray()).as("A Test")
                                                 .containsOnly(null);
      }
    });
  }

  @Test
  public void should_fail_if_actual_contains_unexpected_values() {
    expectAssertionError("unexpected element(s):<[4345.91]> in array:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).containsOnly(doubleArray(55.03));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_contains_unexpected_values() {
    expectAssertionError("[A Test] unexpected element(s):<[4345.91]> in array:<[55.03, 4345.91]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).as("A Test")
                                             .containsOnly(doubleArray(55.03));
      }
    });
  }

  @Test
  public void should_fail_if_actual_does_not_contain_all_the_expected_values() {
    expectAssertionError("array:<[55.03, 4345.91]> does not contain element(s):<[5323.2]>").on(new CodeToTest() {
      public void run() {
        new DoubleArrayAssert(55.03, 4345.91).containsOnly(doubleArray(5323.2));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_contain_all_the_expected_values() {
    expectAssertionError("[A Test] array:<[55.03, 4345.91]> does not contain element(s):<[5323.2]>").on(
      new CodeToTest() {
        public void run() {
          new DoubleArrayAssert(55.03, 4345.91).as("A Test")
                                               .containsOnly(doubleArray(5323.2));
        }
      });
  }

}