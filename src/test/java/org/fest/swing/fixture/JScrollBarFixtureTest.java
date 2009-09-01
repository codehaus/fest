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
import static org.fest.swing.test.builder.JScrollBars.scrollBar;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JScrollBarDriver;
import org.junit.Test;

/**
 * Tests for <code>{@link JScrollBarFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JScrollBarFixtureTest extends CommonComponentFixture_TestCase<JScrollBar> {

  private JScrollBarDriver driver;
  private JScrollBar target;
  private JScrollBarFixture fixture;

  void onSetUp() {
    driver = createMock(JScrollBarDriver.class);
    target = scrollBar().createNew();
    fixture = new JScrollBarFixture(robot, target);
    fixture.driver(driver);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  @Test
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "scrollBar";
    expectLookupByName(name, JScrollBar.class);
    verifyLookup(new JScrollBarFixture(robot, name));
  }

  @Test
  public void shouldRequireValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireValue(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireValue(8));
      }
    }.run();
  }

  @Test
  public void shouldScrollBlockDown() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockDown(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockDown());
      }
    }.run();
  }

  @Test
  public void shouldScrollBlockDownTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockDown(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockDown(6));
      }
    }.run();
  }

  @Test
  public void shouldScrollBlockUp() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockUp(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockUp());
      }
    }.run();
  }

  @Test
  public void shouldScrollBlockUpTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockUp(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockUp(6));
      }
    }.run();
  }

  @Test
  public void shouldScrollToMaximum() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollToMaximum(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollToMaximum());
      }
    }.run();
  }

  @Test
  public void shouldScrollToMinimum() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollToMinimum(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollToMinimum());
      }
    }.run();
  }

  @Test
  public void shouldScrollToPosition() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollTo(target, 8);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollTo(8));
      }
    }.run();
  }

  @Test
  public void shouldScrollUnitDown() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitDown(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitDown());
      }
    }.run();
  }

  @Test
  public void shouldScrollUnitDownTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitDown(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitDown(6));
      }
    }.run();
  }

  @Test
  public void shouldScrollUnitUp() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitUp(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitUp());
      }
    }.run();
  }

  @Test
  public void shouldScrollUnitUpTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitUp(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitUp(6));
      }
    }.run();
  }

  @Test
  public void shouldShowPopupMenu() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target())).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
        assertThat(popupMenuFixture.robot).isSameAs(robot);
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  @Test
  public void shouldShowPopupMenuAtPoint() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final Point p = new Point();
    new EasyMockTemplate(driver()) {
      protected void expectations() {
        expect(driver.invokePopupMenu(target(), p)).andReturn(popupMenu);
      }

      protected void codeToTest() {
        JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
        assertThat(popupMenuFixture.robot).isSameAs(robot);
        assertThat(popupMenuFixture.component()).isSameAs(popupMenu);
      }
    }.run();
  }

  @Test
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

  @Test
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

  @Test
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
  
  JComponentDriver driver() { return driver; }
  JScrollBar target() { return target; }
  JScrollBarFixture fixture() { return fixture; }
}
