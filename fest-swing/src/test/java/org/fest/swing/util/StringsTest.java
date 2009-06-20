/*
 * Created on Jan 14, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Strings}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class StringsTest {

  public void shouldReturnNotDefaultStringIfStringIsNull() {
    assertThat(Strings.isDefaultToString(null)).isFalse();
  }

  public void shouldReturnNotDefaultStringIfStringIsEmpty() {
    assertThat(Strings.isDefaultToString("")).isFalse();
  }

  public void shouldReturnNotDefaultStringIfAtIsNotFollowedByHash() {
    assertThat(Strings.isDefaultToString("abc@xyz"));
  }

  public void shouldReturnNotDefaultStringIfThereIsNotAt() {
    assertThat(Strings.isDefaultToString("abc"));
  }

  public void shouldReturnTrueIfDefaultToString() {
    class Person {}
    assertThat(Strings.isDefaultToString(new Person().toString())).isTrue();
  }

  public void shouldReturnMatchingIfStringAndPatternAreEqual() {
    assertThat(Strings.areEqualOrMatch("hello", "hello")).isTrue();
  }

  public void shouldReturnMatchingIfStringMatchesPattern() {
    assertThat(Strings.areEqualOrMatch("hell.", "hello")).isTrue();
  }

  public void shouldReturnNotMatchingIfStringDoesNotMatchPattern() {
    assertThat(Strings.areEqualOrMatch("hi", "hello")).isFalse();
  }

  public void shouldReturnNotMatchingIfStringIsNull() {
    assertThat(Strings.areEqualOrMatch("hell.", null)).isFalse();
  }

  public void shouldReturnNotMatchingIfPatternIsNull() {
    assertThat(Strings.areEqualOrMatch(null, "Hello")).isFalse();
  }

  public void shouldReturnMatchingIfStringAndPatternAreNull() {
    assertThat(Strings.areEqualOrMatch(null, null)).isTrue();
  }
}
