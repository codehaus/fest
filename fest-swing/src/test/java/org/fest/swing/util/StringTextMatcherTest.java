/*
 * Created on Jun 26, 2009
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
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link StringTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class StringTextMatcherTest {

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfValueArrayIsNull() {
    String[] values = null;
    new StringTextMatcher(values);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfValueArrayIsEmpty() {
    new StringTextMatcher(new String[0]);
  }

  public void shouldReturnTrueIfTextIsEqualToAnyValue() {
    StringTextMatcher matcher = new StringTextMatcher("hello", "world");
    assertThat(matcher.isMatching("world")).isTrue();
  }

  public void shouldReturnTrueIfTextMatchesAnyValueAsPattern() {
    StringTextMatcher matcher = new StringTextMatcher("hell.*", "world");
    assertThat(matcher.isMatching("hello")).isTrue();
  }

  public void shouldReturnFalseIfTextDoesNotMatchAnyValue() {
    StringTextMatcher matcher = new StringTextMatcher("hell.*", "world");
    assertThat(matcher.isMatching("bye")).isFalse();
  }

  public void shouldReturnValueWordAsDescriptionIfMatcherHasOnlyOneValue() {
    StringTextMatcher matcher = new StringTextMatcher("one");
    assertThat(matcher.description()).isEqualTo("value");
  }

  public void shouldReturnValuesWordAsDescriptionIfMatcherHasMoreThanOneValue() {
    StringTextMatcher matcher = new StringTextMatcher("one", "two");
    assertThat(matcher.description()).isEqualTo("values");
  }

  public void shouldReturnSingleValueAsFormattedValueIfMatcherHasOnlyOneValue() {
    StringTextMatcher matcher = new StringTextMatcher("one");
    assertThat(matcher.formattedValues()).isEqualTo("'one'");
  }

  public void shouldReturnArrayOfValuesAsFormattedValueIfMatcherHasMoreThanOneValue() {
    StringTextMatcher matcher = new StringTextMatcher("one", "two");
    assertThat(matcher.formattedValues()).isEqualTo("['one', 'two']");
  }
}
