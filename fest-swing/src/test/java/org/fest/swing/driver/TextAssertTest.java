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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.test.ExpectedFailure.expectAssertionError;

import java.util.regex.Pattern;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TextAssert}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TextAssertTest {

  public void shouldFailIfActualValueIsNotEqualToExpected() {
    expectAssertionError("actual value:<'hello'> is not equal to or does not match pattern:<'bye'>")
      .on(new CodeToTest() {
        public void run() throws Throwable {
          new TextAssert("hello").isEqualOrMatches("bye");
        }
      });
  }

  public void shouldFailShowingDescriptionIfActualValueIsNotEqualToExpected() {
    expectAssertionError("[A Test] actual value:<'hello'> is not equal to or does not match pattern:<'bye'>")
      .on(new CodeToTest() {
        public void run() throws Throwable {
          new TextAssert("hello").as("A Test").isEqualOrMatches("bye");
        }
      });
  }

  public void shouldPassIfActualValueIsEqualToExpected() {
    new TextAssert("Hello").isEqualOrMatches("Hello");
  }

  public void shouldPassIfActualValueMatchesGivenRegexPattern() {
    new TextAssert("Hello").isEqualOrMatches("Hell.");
  }

  public void shouldFailIfActualValueDoesNotMatchPattern() {
    expectAssertionError("actual value:<'hello'> does not match pattern:<'bye'>")
      .on(new CodeToTest() {
        public void run() throws Throwable {
          new TextAssert("hello").matches(Pattern.compile("bye"));
        }
      });
  }

  public void shouldFailShowingDescriptionIfActualValueDoesNotMatchPattern() {
    expectAssertionError("[A Test] actual value:<'hello'> does not match pattern:<'bye'>")
      .on(new CodeToTest() {
        public void run() throws Throwable {
          new TextAssert("hello").as("A Test").matches(Pattern.compile("bye"));
        }
      });
  }

  public void shouldPassIfActualValueMatchesPattern() {
    new TextAssert("Hello").matches(Pattern.compile("Hel.*"));
  }
}
