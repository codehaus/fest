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
import static org.fest.assertions.CommonFailures.expectErrorIfListIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfListIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import java.util.List;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ListAssert#isNotEmpty()}</code>.
 *
 * @author Alex Ruiz
 */
public class ListAssert_isNotEmpty_Test implements GroupAssert_isNotEmpty_TestCase {

  @Test
  public void should_pass_if_actual_is_not_empty() {
    List<String> names = list("Frodo", "Sam");
    new ListAssert(names).isNotEmpty();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfListIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfListIsNull(new CodeToTest() {
      public void run() {
        new ListAssert(null).as("A Test").isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_empty() {
    expectAssertionError("expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(emptyList()).isNotEmpty();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_empty() {
    expectAssertionError("[A Test] expecting a non-empty list, but it was empty").on(new CodeToTest() {
      public void run() {
        new ListAssert(emptyList()).as("A Test").isNotEmpty();
      }
    });
  }
}