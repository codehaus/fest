/*
 * Created on Dec 25, 2007
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
import static org.fest.swing.test.builder.JLists.list;
import static org.fest.swing.test.builder.JScrollBars.scrollBar;
import static org.fest.swing.test.builder.JScrollPanes.scrollPane;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JScrollPaneDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JScrollPaneFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test
public class JScrollPaneFixtureTest extends CommonComponentFixtureTestCase<JScrollPane> {

  private JScrollPaneDriver driver;
  private JScrollPane target;
  private JScrollPaneFixture fixture;

  void onSetUp() {
    driver = createMock(JScrollPaneDriver.class);
    target = scrollPane().withView(list().createNew())
                         .createNew();
    fixture = new JScrollPaneFixture(robot(), target);
    fixture.driver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "scrollPane";
    expectLookupByName(name, JScrollPane.class);
    verifyLookup(new JScrollPaneFixture(robot(), name));
  }

  public void shouldReturnHorizontalScrollBar() {
    final JScrollBar scrollBar = scrollBar().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.horizontalScrollBarIn(target)).andReturn(scrollBar);
      }

      protected void codeToTest() {
        JScrollBarFixture horizontalScrollBar = fixture.horizontalScrollBar();
        assertThat(horizontalScrollBar.target).isSameAs(scrollBar);
      }
    }.run();
  }

  public void shouldReturnVerticalScrollBar() {
    final JScrollBar scrollBar = scrollBar().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.verticalScrollBarIn(target)).andReturn(scrollBar);
      }

      protected void codeToTest() {
        JScrollBarFixture verticalScrollBar = fixture.verticalScrollBar();
        assertThat(verticalScrollBar.target).isSameAs(scrollBar);
      }
    }.run();
  }

  public void shouldRequireToolTip() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.requireToolTip(target(), "A ToolTip");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireToolTip("A ToolTip"));
      }
    }.run();
  }

  public void shouldRequireToolTipToMatchPattern() {
    final Pattern pattern = regex(".");
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        driver.requireToolTip(target(), pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireToolTip(pattern));
      }
    }.run();
  }

  public void shouldShowPopupMenu() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target())).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
        assertThat(popupMenuFixture.robot).isSameAs(robot());
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  public void shouldShowPopupMenuAtPoint() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final Point p = new Point();
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target(), p)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
        assertThat(popupMenuFixture.robot).isSameAs(robot());
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  public void shouldReturnClientProperty() {
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.clientProperty(target, "name")).andReturn("Luke");
      }

      protected void codeToTest() {
        assertThat(fixture.clientProperty("name")).isEqualTo("Luke");
      }
    }.run();
  }
  
  ComponentDriver driver() { return driver; }
  JScrollPane target() { return target; }
  JScrollPaneFixture fixture() { return fixture; }
}
