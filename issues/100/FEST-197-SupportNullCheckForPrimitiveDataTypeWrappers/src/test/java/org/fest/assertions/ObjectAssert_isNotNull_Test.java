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
 * Tests for <code>{@link ObjectAssert#isNotNull()}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectAssert_isNotNull_Test implements NullableAssert_isNotNull_TestCase {

  @Test
  public void should_pass_if_actual_is_not_null() {
    new ObjectAssert(6).isNotNull();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).isNotNull();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test")
                              .isNotNull();
      }
    });
  }
  
  @Test
  public void should_fail_with_custom_message_if_actual_is_null() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).overridingErrorMessage("My custom message")
                              .isNotNull();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_is_null() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new ObjectAssert(null).as("A Test")
                              .overridingErrorMessage("My custom message")
                              .isNotNull();
      }
    });
  }

  @Test
  public void should_have_leaf_assertion_class_as_return_type() {
    ObjectAssert initialInstance = new ObjectAssert(new Object());
    @SuppressWarnings("unused")
    ObjectAssert returnValue = initialInstance.isNotNull();
  }
}