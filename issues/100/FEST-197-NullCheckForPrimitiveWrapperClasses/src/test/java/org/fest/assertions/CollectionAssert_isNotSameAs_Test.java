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
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import java.util.Collection;
import java.util.List;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link CollectionAssert#isNotSameAs(Collection)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssert_isNotSameAs_Test implements GenericAssert_isNotSameAs_TestCase {

  @Test
  public void should_pass_if_actual_and_expected_are_not_same() {
    new CollectionAssert(list("Leia")).isNotSameAs(emptyList());
  }

  @Test
  public void should_fail_if_actual_and_expected_are_same() {
    expectAssertionError("given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        List<String> list = list("Leia");
        new CollectionAssert(list).isNotSameAs(list);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_and_expected_are_same() {
    expectAssertionError("[A Test] given objects are same:<['Leia']>").on(new CodeToTest() {
      public void run() {
        List<String> list = list("Leia");
        new CollectionAssert(list).as("A Test")
                                  .isNotSameAs(list);
      }
    });
  }
}
