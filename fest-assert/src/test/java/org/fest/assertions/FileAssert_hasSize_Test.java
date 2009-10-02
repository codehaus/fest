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

import static org.fest.assertions.CommonFailures.expectAssertionErrorIfObjectIsNull;
import static org.fest.assertions.CommonFailures.expectAssertionErrorWithDescriptionIfObjectIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link FileAssert}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FileAssert_hasSize_Test extends FileAssert_TestCase implements Assert_hasSize_TestCase {

  @Test
  public void should_pass_if_actual_has_expected_size() {
    file.length(8);
    new FileAssert(file).hasSize(8);
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).hasSize(8);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new FileAssert(null).as("A Test")
                            .hasSize(8);
      }
    });
  }

  @Test
  public void should_fail_if_actual_does_not_have_expected_size() {
    file.length(8);
    expectAssertionError("size of file:<c:\\f.txt> expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).hasSize(6);
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_does_not_have_expected_size() {
    file.length(8);
    expectAssertionError("[A Test] size of file:<c:\\f.txt> expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        new FileAssert(file).as("A Test")
                            .hasSize(6);
      }
    });
  }
}
