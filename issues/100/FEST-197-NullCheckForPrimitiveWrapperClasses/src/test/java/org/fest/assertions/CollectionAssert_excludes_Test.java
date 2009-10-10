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

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CollectionAssert#excludes(Object...)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssert_excludes_Test implements GroupAssert_excludes_TestCase {

  @Test
  public void should_pass_if_actual_excludes_given_value() {
    new CollectionAssert(list("Luke", "Leia")).excludes("Anakin");
  }

  @Test
  public void should_pass_if_actual_excludes_given_values() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).excludes("Han", "Yoda");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).excludes("Luke");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test")
                                  .excludes("Luke");
      }
    });
  }

  @Test
  public void should_throw_error_if_expected_is_null() {
    expectNullPointerException("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(emptyList()).excludes(objects);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectNullPointerException("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(emptyList()).as("A Test")
                                         .excludes(objects);
      }
    });
  }

  @Test
  public void should_fail_if_actual_contains_given_values() {
    expectAssertionError("collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Leia")).excludes("Luke");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_contains_given_values() {
    expectAssertionError("[A Test] collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(
      new CodeToTest() {
        public void run() {
          new CollectionAssert(list("Luke", "Leia")).as("A Test")
                                                    .excludes("Luke");
        }
      });
  }
}
