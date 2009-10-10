/*
 * Created on Jan 10, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.assertions;

import static java.util.Collections.emptyList;
import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import java.util.List;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CollectionAssert#containsOnly(Object...)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssert_containsOnly_Test implements GroupAssert_containsOnly_TestCase {

  @Test
  public void should_pass_if_actual_contains_only_given_values() {
    new CollectionAssert(list("Gandalf", "Frodo", "Sam")).containsOnly("Gandalf", "Frodo", "Sam");
  }

  @Test
  public void should_pass_if_actual_contains_only_given_values_in_different_order() {
    new CollectionAssert(list("Gandalf", "Frodo", "Sam")).containsOnly("Sam", "Frodo", "Gandalf");
  }

  @Test
  public void should_fail_if_actual_is_empty_and_expecting_at_least_one_element() {
    expectAssertionError("collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(emptyList()).containsOnly("Sam");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty_and_expecting_at_least_one_element() {
    expectAssertionError("[A Test] collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(emptyList()).as("A Test")
                                         .containsOnly("Sam");
      }
    });
  }

  @Test
  public void should_fail_if_actual_contains_unexpected_values() {
    expectAssertionError("unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(
      new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo", "Sam");
          new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
        }
      });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_contains_unexpected_values() {
    expectAssertionError("[A Test] unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(
      new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo", "Sam");
          new CollectionAssert(names).as("A Test")
                                     .containsOnly("Gandalf", "Frodo");
        }
      });
  }

  @Test
  public void should_fail_if_actual_does_not_contain_all_the_expected_values() {
    expectAssertionError("collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
      new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo");
          new CollectionAssert(names).containsOnly("Gandalf", "Frodo", "Sam");
        }
      });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_contain_all_the_expected_values() {
    expectAssertionError("[A Test] collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
      new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo");
          new CollectionAssert(names).as("A Test")
                                     .containsOnly("Gandalf", "Frodo", "Sam");
        }
      });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test")
                                  .containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }


  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(emptyList()).containsOnly(objects);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(emptyList()).as("A Test")
                                         .containsOnly(objects);
      }
    });
  }
}
