/*
 * Created on Jun 6, 2009
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
package org.fest.swing.core;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.Component;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.data.BooleanProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link AbstractComponentMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class AbstractComponentMatcherTest {

  private ConcreteComponentMatcher matcher;

  public void shouldNotRequireShowingByDefault() {
    matcher = new ConcreteComponentMatcher();
    assertThat(matcher.requireShowing()).isFalse();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldRequireShowingAsSpecifiedInConstructor(boolean requireShowing) {
    matcher = new ConcreteComponentMatcher(requireShowing);
    assertThat(matcher.requireShowing()).isEqualTo(requireShowing);
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldRequireShowingAsSpecifiedInSetter(boolean requireShowing) {
    matcher = new ConcreteComponentMatcher();
    matcher.requireShowing(requireShowing);
    assertThat(matcher.requireShowing()).isEqualTo(requireShowing);
  }

  public void shouldAlwaysMatchIfRequireShowingIsFalse() {
    matcher = new ConcreteComponentMatcher(false);
    Component c = mockComponent();
    assertThat(matcher.requireShowingMatches(c)).isTrue();
  }

  public void shouldMatchIfRequireShowingIsTrueAndComponentIsShowing() {
    matcher = new ConcreteComponentMatcher(true);
    final Component c = mockComponent();
    new EasyMockTemplate(c) {
      protected void expectations() {
        expect(c.isShowing()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(matcher.requireShowingMatches(c)).isTrue();
      }
    }.run();
  }

  public void shouldNotMatchIfRequireShowingIsTrueAndComponentIsNotShowing() {
    matcher = new ConcreteComponentMatcher(true);
    final Component c = mockComponent();
    new EasyMockTemplate(c) {
      protected void expectations() {
        expect(c.isShowing()).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(matcher.requireShowingMatches(c)).isFalse();
      }
    }.run();
  }

  private Component mockComponent() {
    return createMock(Component.class);
  }

  private static class ConcreteComponentMatcher extends AbstractComponentMatcher {
    public ConcreteComponentMatcher() {
      super();
    }

    public ConcreteComponentMatcher(boolean requireShowing) {
      super(requireShowing);
    }

    public boolean matches(Component c) {
      return false;
    }
  }
}
