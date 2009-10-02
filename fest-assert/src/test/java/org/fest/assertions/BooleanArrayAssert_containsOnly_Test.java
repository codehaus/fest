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
import static org.fest.assertions.EmptyArrays.emptyBooleanArray;
import static org.fest.assertions.ArrayFactory.booleanArray;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link BooleanArrayAssert#containsOnly(boolean...)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BooleanArrayAssert_containsOnly_Test implements GroupAssert_containsOnly_TestCase {

  @Test
  public void should_pass_if_actual_contains_only_given_values() {
    new BooleanArrayAssert(true).containsOnly(true);
  }

  @Test
  public void should_pass_if_actual_contains_only_given_values_in_different_order() {
    new BooleanArrayAssert(true, false).containsOnly(false, true);
  }

  @Test
  public void should_fail_if_actual_is_empty_and_expecting_at_least_one_element() {
    expectAssertionError("array:<[]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(emptyBooleanArray()).containsOnly(booleanArray(false));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty_and_expecting_at_least_one_element() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(emptyBooleanArray()).as("A Test")
                                                   .containsOnly(booleanArray(false));
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfArrayIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(null).containsOnly(booleanArray(false));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfArrayIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(null).as("A Test").containsOnly(booleanArray(false));
      }
    });
  }


  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of booleans should not be null").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).containsOnly(null);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of booleans should not be null").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test")
                                    .containsOnly(null);
      }
    });
  }

  @Test
  public void should_fail_if_actual_contains_unexpected_values() {
    expectAssertionError("unexpected element(s):<[false]> in array:<[true, false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true, false).containsOnly(booleanArray(true));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_contains_unexpected_values() {
    expectAssertionError("[A Test] unexpected element(s):<[false]> in array:<[true, false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true, false).as("A Test")
                                           .containsOnly(booleanArray(true));
      }
    });
  }

  @Test
  public void should_fail_if_actual_does_not_contain_all_the_expected_values() {
    expectAssertionError("array:<[true]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).containsOnly(booleanArray(true, false));
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_contain_all_the_expected_values() {
    expectAssertionError("[A Test] array:<[true]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test")
                                    .containsOnly(booleanArray(false));
      }
    });
  }
}
