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
import static org.fest.assertions.CommonFailures.expectErrorIfCollectionIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfCollectionIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CollectionAssert#doesNotHaveDuplicates()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssert_doesNotHaveDuplicates_Test implements GroupAssert_doesNotHaveDuplicates_TestCase {

  @Test
  public void should_pass_if_actual_does_not_contain_duplicates() {
    new CollectionAssert(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  @Test
  public void should_pass_if_actual_is_empty() {
    new CollectionAssert(emptyList()).doesNotHaveDuplicates();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).doesNotHaveDuplicates();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfCollectionIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test")
                                  .doesNotHaveDuplicates();
      }
    });
  }

  @Test
  public void should_fail_if_actual_has_duplicates() {
    expectAssertionError("collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Yoda", "Luke")).doesNotHaveDuplicates();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_has_duplicates() {
    expectAssertionError("[A Test] collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(
      new CodeToTest() {
        public void run() {
          new CollectionAssert(list("Luke", "Yoda", "Luke")).as("A Test")
                                                            .doesNotHaveDuplicates();
        }
      });
  }
}
