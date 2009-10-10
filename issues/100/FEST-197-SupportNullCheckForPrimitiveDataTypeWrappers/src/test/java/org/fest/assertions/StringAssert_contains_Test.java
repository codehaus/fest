/*
 * Created on Jan 10, 2007
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

import static org.fest.assertions.CommonFailures.expectErrorIfObjectIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfObjectIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link StringAssert#contains(String)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class StringAssert_contains_Test {

  @Test
  public void should_pass_if_actual_contains_given_String() {
    new StringAssert("Anakin").contains("akin");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new StringAssert(null).contains("Yoda");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new StringAssert(null).as("A Test")
                              .contains("Yoda");
      }
    });
  }

  @Test
  public void should_fail_if_actual_does_not_contain_given_String() {
    expectAssertionError("<'Luke'> should contain the String:<'Yoda'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Luke").contains("Yoda");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_contain_given_String() {
    expectAssertionError("[A Test] <'Luke'> should contain the String:<'Yoda'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Luke").as("A Test")
                                .contains("Yoda");
      }
    });
  }
}
