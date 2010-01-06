/*
 * Created on Jun 21, 2009
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
 * Copyright @2009-2010 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.junit.Test;

/**
 * Tests for <code>{@link TextAssert#isEqualOrMatches(String)}</code>.
 *
 * @author Alex Ruiz
 */
public class TextAssert_isEqualOrMatches_Test {

  @Test
  public void should_fail_if_actual_is_not_equal_to_expected() {
    expectAssertionError("actual value:<'hello'> is not equal to or does not match pattern:<'bye'>")
      .on(new CodeToTest() {
        public void run() throws Throwable {
          new TextAssert("hello").isEqualOrMatches("bye");
        }
      });
  }

  @Test
  public void should_fail_showing_description_if_actual_is_not_equal_to_expected() {
    expectAssertionError("[A Test] actual value:<'hello'> is not equal to or does not match pattern:<'bye'>")
      .on(new CodeToTest() {
        public void run() throws Throwable {
          new TextAssert("hello").as("A Test").isEqualOrMatches("bye");
        }
      });
  }

  @Test
  public void should_pass_if_actual_is_equal_to_expected() {
    new TextAssert("Hello").isEqualOrMatches("Hello");
  }

  @Test
  public void should_pass_if_actual_matches_regex_pattern() {
    new TextAssert("Hello").isEqualOrMatches("Hell.");
  }
}
