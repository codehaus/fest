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

import static org.fest.assertions.CommonFailures.expectAssertionErrorIfCollectionIsNull;
import static org.fest.assertions.CommonFailures.expectAssertionErrorWithDescriptionIfCollectionIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import java.util.List;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CollectionAssert#hasSize(int)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssert_hasSize_Test implements Assert_hasSize_TestCase {

  @Test
  public void should_pass_if_actual_has_expected_size() {
    new CollectionAssert(list("Gandalf", "Frodo", "Sam")).hasSize(3);
  }

  @Test
  public void should_fail_if_actual_does_not_have_expected_size() {
    expectAssertionError("expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new CollectionAssert(names).hasSize(2);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_have_expected_size() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new CollectionAssert(names).as("A Test")
                                   .hasSize(2);
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).hasSize(0);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test")
                                  .hasSize(0);
      }
    });
  }
}
