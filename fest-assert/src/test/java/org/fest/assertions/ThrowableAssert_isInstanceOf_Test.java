/*
 * Created on Dec 23, 2007
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

import static org.fest.assertions.CommonFailures.*;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ThrowableAssert#isInstanceOf(Class)}</code>.
 *
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class ThrowableAssert_isInstanceOf_Test implements Assert_isInstanceOf_TestCase {

  @Test
  public void should_pass_if_actual_is_instance_of_expected() {
    new ThrowableAssert(new Exception()).isInstanceOf(Exception.class);
  }

  @Test
  public void should_throw_error_if_expected_is_null() {
    // TODO should be NullPointerException
    expectErrorIfTypeIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).isInstanceOf(null);
      }
    });
  }

  @Test
  public void should_throw_error_and_display_description_of_assertion_if_expected_is_null() {
    expectErrorWithDescriptionIfTypeIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).as("A Test")
                                            .isInstanceOf(null);
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_not_instance_of_expected() {
    String message = "expected instance of:<java.lang.NullPointerException> but was instance of:<java.lang.Exception>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).isInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_instance_of_expected() {
    String message = "[A Test] expected instance of:<java.lang.NullPointerException> but was instance of:<java.lang.Exception>";
    expectAssertionError(message).on(new CodeToTest() {
      public void run() {
        new ThrowableAssert(new Exception()).as("A Test")
                                            .isInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).isInstanceOf(NullPointerException.class);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new ThrowableAssert(null).as("A Test")
                                 .isInstanceOf(NullPointerException.class);
      }
    });
  }
}
