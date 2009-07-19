/*
 * Created on Jul 18, 2009
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
package org.fest.swing.fixture;

import static org.easymock.EasyMock.expectLastCall;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.Regex.regex;

import java.util.regex.Pattern;

import javax.swing.JComponent;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;

/**
 * Understands a test case for implementations of <code>{@link JComponentFixture}</code> and
 * <code>{@link CommonComponentFixture}</code>.
 * @param <T> the type of component supported by the fixture to test. 
 *
 * @author Alex Ruiz
 */
public abstract class JComponentFixtureTestCase<T extends JComponent> extends CommonComponentFixtureTestCase<T> {

  abstract JComponentDriver driver();

  public void shouldRequireToolTip() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireToolTip(target(), "A ToolTip");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixtureToTest().requireToolTip("A ToolTip"));
      }
    }.run();
  }

  public void shouldRequireToolTipToMatchPattern() {
    final Pattern pattern = regex(".");
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver().requireToolTip(target(), pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixtureToTest().requireToolTip(pattern));
      }
    }.run();
  }

  private JComponentFixture fixtureToTest() {
    CommonComponentFixture fixture = fixture();
    assertThat(fixture).isInstanceOf(JComponentFixture.class);
    return (JComponentFixture) fixture;
  }

}