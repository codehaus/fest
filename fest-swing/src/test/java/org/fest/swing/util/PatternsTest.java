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
 * Tests for <code>{@link Patterns}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class PatternsTest {

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPatternArrayIsNull() {
    Pattern[] patterns = null;
    Patterns.format(patterns);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfPatternInArrayIsNull() {
    Pattern[] patterns = { regex("hello"), null };
    Patterns.format(patterns);
  }

  public void shouldFormatPatternArray() {
    Pattern[] patterns = { regex("hello"), regex("world") };
    String formatted = Patterns.format(patterns);
    assertThat(formatted).isEqualTo("['hello', 'world']");
  }
}
