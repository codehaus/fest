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

import static org.fest.assertions.CommonFailures.expectErrorIfObjectIsNull;
import static org.fest.assertions.CommonFailures.expectErrorWithDescriptionIfObjectIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link FileAssert#isAbsolute()}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FileAssert_isAbsolute_Test extends FileAssert_TestCase {

  @Test
  public void should_pass_if_actual_is_absolute() {
    file.ensureIsAbsolute();
    new FileAssert(file).isAbsolute();
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).isAbsolute();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test")
                            .isAbsolute();
      }
    });
  }

  @Test
  public void should_fail_if_actual_is_not_absolute() {
    expectAssertionError("file:<c:\\f.txt> should be an absolute path").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).isAbsolute();
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_not_absolute() {
    expectAssertionError("[A Test] file:<c:\\f.txt> should be an absolute path").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test")
                            .isAbsolute();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_if_actual_is_not_absolute() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).overridingErrorMessage("My custom message")
                            .isAbsolute();
      }
    });
  }

  @Test
  public void should_fail_with_custom_message_ignoring_description_of_assertion_if_actual_is_not_absolute() {
    expectAssertionError("My custom message").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test")
                            .overridingErrorMessage("My custom message")
                            .isAbsolute();
      }
    });
  }
}
