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

import javax.swing.JLabel;

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
