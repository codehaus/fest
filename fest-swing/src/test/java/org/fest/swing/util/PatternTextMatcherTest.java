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
import static org.fest.swing.test.core.Regex.regex;

import java.util.regex.Pattern;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link PatternTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class PatternTextMatcherTest {

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPatternArrayIsNull() {
    Pattern[] patterns = null;
    new PatternTextMatcher(patterns);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfPatternArrayIsEmpty() {
    new PatternTextMatcher(new Pattern[0]);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfAnyPatternInArrayIsNull() {
    Pattern[] patterns = { null, regex("hello"), null };
    PatternTextMatcher matcher = new PatternTextMatcher(patterns);
    matcher.isMatching("hello");
  }

  public void shouldReturnTrueIfTextMatchesAnyPattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(regex("hello"));
    assertThat(matcher.isMatching("hello")).isTrue();
  }

  public void shouldReturnFalseIfTextDoesNotMatchAnyPattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(regex("bye"), regex("hello"));
    assertThat(matcher.isMatching("world")).isFalse();
  }

  public void shouldReturnPatternWordAsDescriptionIfMatcherHasOnlyOnePattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(regex("one"));
    assertThat(matcher.description()).isEqualTo("pattern");
  }

  public void shouldReturnPatternsWordAsDescriptionIfMatcherHasMoreThanOnePattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(regex("one"), regex("two"));
    assertThat(matcher.description()).isEqualTo("patterns");
  }

  public void shouldReturnSinglePatternAsFormattedValueIfMatcherHasOnlyOnePattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(regex("one"));
    assertThat(matcher.formattedValues()).isEqualTo("'one'");
  }

  public void shouldReturnArrayOfPatternsAsFormattedValueIfMatcherHasMoreThanOnePattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(regex("one"), regex("two"));
    assertThat(matcher.formattedValues()).isEqualTo("['one', 'two']");
  }
}
