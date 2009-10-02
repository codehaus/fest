/*
 * Created on Mar 29, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.assertions;

import static java.util.Collections.emptyList;
import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ListAssert#startsWith(Object...)}</code>.
 *
 * @author Alex Ruiz
 */
public class ListAssert_startsWith_Test {

  @Test
  public void should_pass_if_actual_starts_with_sequence() {
    new ListAssert(list("Anakin", "Leia", "Han")).startsWith("Anakin", "Leia");
  }

  @Test
  public void should_pass_if_actual_and_expected_are_equal() {
    new ListAssert(list("Anakin", "Leia", "Han")).startsWith("Anakin", "Leia", "Han");
  }

  @Test
  public void should_pass_if_both_actual_and_sequence_are_empty() {
    Object[] sequence = new Object[0];
    new ListAssert(list()).startsWith(sequence);
  }

  @Test
  public void should_fail_if_actual_is_smaller_than_sequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not start with the sequence:<['Anakin', 'Leia', 'Han']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).startsWith("Anakin", "Leia", "Han");
        }
      });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_smaller_than_sequence() {
    expectAssertionError("[A Test] list:<['Anakin', 'Leia']> does not start with the sequence:<['Anakin', 'Leia', 'Han']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).as("A Test")
                                                .startsWith("Anakin", "Leia", "Han");
        }
      });
  }

  @Test
  public void should_fail_if_actual_is_not_empty_and_sequence_is_empty() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not start with the sequence:<[]>").on(
      new CodeToTest() {
        public void run() {
          Object[] sequence = new Object[0];
          new ListAssert(list("Anakin", "Leia")).startsWith(sequence);
        }
      });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_empty_and_sequence_is_empty() {
    expectAssertionError("[A Test] list:<['Anakin', 'Leia']> does not start with the sequence:<[]>").on(
      new CodeToTest() {
        public void run() {
          Object[] sequence = new Object[0];
          new ListAssert(list("Anakin", "Leia")).as("A Test")
                                                .startsWith(sequence);
        }
      });
  }

  @Test
  public void should_fail_if_actual_does_not_start_with_sequence() {
    expectAssertionError("list:<['Anakin', 'Leia']> does not start with the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).startsWith("Leia", "Anakin");
        }
      });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_start_with_sequence() {
    expectAssertionError("[A Test] list:<['Anakin', 'Leia']> does not start with the sequence:<['Leia', 'Anakin']>").on(
      new CodeToTest() {
        public void run() {
          new ListAssert(list("Anakin", "Leia")).as("A Test")
                                                .startsWith("Leia", "Anakin");
        }
      });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfListIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).startsWith("Gandalf", "Frodo", "Sam");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfListIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test")
                            .startsWith("Gandalf", "Frodo", "Sam");
      }
    });
  }

  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(emptyList()).startsWith(objects);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new ListAssert(emptyList()).as("A Test")
                                   .startsWith(objects);
      }
    });
  }
}
