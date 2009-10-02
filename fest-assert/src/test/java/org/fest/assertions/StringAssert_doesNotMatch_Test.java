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

import static org.fest.assertions.CommonFailures.expectAssertionErrorIfObjectIsNull;
import static org.fest.assertions.CommonFailures.expectAssertionErrorWithDescriptionIfObjectIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link StringAssert#doesNotMatch(String)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class StringAssert_doesNotMatch_Test {

  @Test
  public void should_pass_if_actual_does_not_match_given_pattern() {
    new StringAssert("Luke 001").doesNotMatch("^\\d+.*$");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectAssertionErrorIfObjectIsNull(new CodeToTest() {
      public void run() {
        new StringAssert(null).doesNotMatch("^\\d+.*$");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_is_null() {
    expectAssertionErrorWithDescriptionIfObjectIsNull(new CodeToTest() {
      public void run() {
        new StringAssert(null).as("A Test")
                              .doesNotMatch("^\\d+.*$");
      }
    });
  }

  @Test
  public void should_fail_if_actual_matches_given_pattern() {
    expectAssertionError("<'Luke 001'> should not match the regular expression:<'^.*\\d+$'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Luke 001").doesNotMatch("^.*\\d+$");
      }
    });
  }

  @Test
  public void should_fail_and_display_description_of_assertion_if_actual_matches_given_pattern() {
    expectAssertionError("[A Test] <'Luke 001'> should not match the regular expression:<'^.*\\d+$'>").on(
      new CodeToTest() {
        public void run() {
          new StringAssert("Luke 001").as("A Test")
                                      .doesNotMatch("^.*\\d+$");
        }
      });
  }
}
