/*
 * Created on Apr 3, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.Index.atIndex;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JTabbedPanes.tabbedPane;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.util.Arrays.array;

import java.awt.Component;
import java.util.regex.Pattern;

import javax.swing.JTabbedPane;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTabbedPaneDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTabbedPaneFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JTabbedPaneFixtureTest extends CommonComponentFixtureTestCase<JTabbedPane> {

  private JTabbedPaneDriver driver;
  private JTabbedPane target;
  private JTabbedPaneFixture fixture;

  void onSetUp() {
    driver = createMock(JTabbedPaneDriver.class);
    target = tabbedPane().createNew();
    fixture = new JTabbedPaneFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "tabbedPane";
    expectLookupByName(name, JTabbedPane.class);
    verifyLookup(new JTabbedPaneFixture(robot(), name));
  }

  public void shouldSelectTabWithIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectTab(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectTab(8));
      }
    }.run();
  }

  public void shouldReturnSelectedComponent() {
    final Component selected = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.selectedComponentOf(target)).andReturn(selected);
      }

      protected void codeToTest() {
        assertThat(fixture.selectedComponent()).isSameAs(selected);
      }
    }.run();
  }

  public void shouldRequireTitleAtTabIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireTabTitle(target, "Hello", atIndex(1));
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireTitle("Hello", atIndex(1)));
      }
    }.run();
  }

  public void shouldRequireTitleMatchingPatternAtTabIndex() {
    final Pattern pattern = regex("hello");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireTabTitle(target, pattern, atIndex(1));
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireTitle(pattern, atIndex(1)));
      }
    }.run();
  }

  public void shouldRequireTabTitles() {
    final String[] titles = array("One", "Two");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireTabTitles(target, titles);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireTabTitles(titles));
      }
    }.run();

  }

  public void shouldSelectTabWithText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectTab(target, "A Tab");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectTab("A Tab"));
      }
    }.run();
  }
  
  public void shouldSelectTabMatchingPattern() {
    final Pattern pattern = regex("hello");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectTab(target, pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectTab(pattern));
      }
    }.run();
  }

  public void shouldReturnTabTitles() {
    final String[] titles = array("One", "Two");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.tabTitles(target)).andReturn(titles);
      }

      protected void codeToTest() {
        String[] result = fixture.tabTitles();
        assertThat(result).isSameAs(titles);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JTabbedPane target() { return target; }
  JTabbedPaneFixture fixture() { return fixture; }
}
