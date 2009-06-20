/*
 * Created on Jun 19, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link StringIsEqualToOrMatches}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class StringIsEqualToOrMatchesTest {

  public void shouldPassIfStringAndPatternAreEqual() {
    assertThat("hello").satisfies(StringIsEqualToOrMatches.isEqualToOrMatches("hello"));
  }

  public void shouldPassIfStringMatchesPattern() {
    assertThat("hello").satisfies(StringIsEqualToOrMatches.isEqualToOrMatches("hell."));
  }

  public void shouldFailIfStringDoesNotMatchPattern() {
    expectAssertionError("actual value:<'hello'> should satisfy condition:<equal to or match pattern:'hi'>")
      .on(new CodeToTest() {
        public void run() {
          assertThat("hello").satisfies(StringIsEqualToOrMatches.isEqualToOrMatches("hi"));
        }
      });
  }

  public void shouldFailIfStringIsNull() {
    expectAssertionError("actual value:<null> should satisfy condition:<equal to or match pattern:'hell.'>")
      .on(new CodeToTest() {
        public void run() {
          String s = null;
          assertThat(s).satisfies(StringIsEqualToOrMatches.isEqualToOrMatches("hell."));
        }
      });
  }

  public void shouldFailIfPatternIsNull() {
    expectAssertionError("actual value:<'hello'> should satisfy condition:<equal to or match pattern:null>")
      .on(new CodeToTest() {
        public void run() {
          assertThat("hello").satisfies(StringIsEqualToOrMatches.isEqualToOrMatches(null));
        }
      });
  }

  public void shouldPassIfStringAndPatternAreNull() {
    String s = null;
    assertThat(s).satisfies(StringIsEqualToOrMatches.isEqualToOrMatches(null));
  }
}
