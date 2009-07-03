/*
 * Created on May 1, 2009
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
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.util.Objects.HASH_CODE_PRIME;
import static org.fest.util.Objects.hashCodeFor;

import javax.swing.JLabel;

import org.fest.util.Objects;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link NamedComponentMatcherTemplate}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class NamedComponentMatcherTemplateTest {

  private Matcher matcher;

  @BeforeMethod public void setUp() {
    matcher = new Matcher(JLabel.class);
  }

  public void shouldSetNameToAnyWhenNameNotSpecified() {
    assertThat(matcher.name).isSameAs(NamedComponentMatcherTemplate.ANY);
  }

  public void shouldNotQuoteAny() {
    assertThat(matcher.quoted(NamedComponentMatcherTemplate.ANY)).isSameAs(NamedComponentMatcherTemplate.ANY);
  }

  public void shouldQuotePatternAsStringIfItIsPattern() {
    assertThat(matcher.quoted(regex("hello"))).isEqualTo("'hello'");
  }

  public void shouldQuoteIfNotAny() {
    assertThat(matcher.quoted("hello")).isEqualTo("'hello'");
  }

  public void shouldNotQuoteNameIfNameIsAny() {
    assertThat(matcher.quotedName()).isSameAs(NamedComponentMatcherTemplate.ANY);
  }

  public void shouldQuoteNameIfNotAny() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.quotedName()).isEqualTo("'hello'");
  }

  public void shouldImplementToStringInAny() {
    assertThat(NamedComponentMatcherTemplate.ANY.toString()).isEqualTo("<Any>");
  }

  public void shouldAlwaysMatchIfExpectedValueIsAny() {
    matcher = new Matcher(JLabel.class, NamedComponentMatcherTemplate.ANY);
    assertThat(matcher.isNameMatching("hello")).isTrue();
  }

  public void shouldMatchNameIfGivenValueIsEqualToNameInMatcher() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.isNameMatching("hello")).isTrue();
  }

  public void shouldMatchValuesUsingEqualityIfExpectedAndActualValuesAreRegularObjects() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.arePropertyValuesMatching(new Dog("Bob"), new Dog("Bob"))).isTrue();
  }

  private static class Dog {
    private final String name;

    Dog(String name) { this.name = name; }

    @Override public int hashCode() {
      final int prime = HASH_CODE_PRIME;
      int result = 1;
      result = prime * result + hashCodeFor(name);
      return result;
    }

    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Dog other = (Dog) obj;
      return Objects.areEqual(name, other.name);
    }
  }

  private static class Matcher extends NamedComponentMatcherTemplate<JLabel> {
    protected Matcher(Class<JLabel> supportedType) {
      super(supportedType);
    }

    public Matcher(Class<JLabel> supportedType, Object name) {
      super(supportedType, name);
    }

    protected boolean isMatching(JLabel component) {
      return false;
    }
  }
}
