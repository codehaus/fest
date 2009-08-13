/*
 * Created on Jun 10, 2007
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
import static org.fest.swing.test.builder.JToggleButtons.toggleButton;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.JComponentDriver;
import org.junit.Test;

/**
 * Tests for <code>{@link JToggleButtonFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JToggleButtonFixtureTest extends CommonComponentFixtureTestCase<JToggleButton> {

  private AbstractButtonDriver driver;
  private JToggleButton target;
  private JToggleButtonFixture fixture;

  void onSetUp() {
    driver = createMock(AbstractButtonDriver.class);
    target = toggleButton().createNew();
    fixture = new JToggleButtonFixture(robot, target);
    fixture.driver(driver);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  @Test
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "toggleButton";
    expectLookupByName(name, JToggleButton.class);
    verifyLookup(new JToggleButtonFixture(robot, name));
  }

  @Test
  public void shouldSelectCheckBox() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.select(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.check());
      }
    }.run();
  }

  @Test
  public void shoulUnselectCheckBox() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unselect(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.uncheck());
      }
    }.run();
  }

  @Test
  public void shouldReturnText() {
    final String text = "A Toggle Button";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.textOf(target)).andReturn(text);
      }

      protected void codeToTest() {
        assertThat(fixture.text()).isEqualTo(text);
      }
    }.run();
  }

  @Test
  public void shouldRequireText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, "A ToggleButton");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A ToggleButton"));
      }
    }.run();
  }

  @Test
  public void shouldRequireTextToMatchPattern() {
    final Pattern pattern = regex(".");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, pattern);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText(pattern));
      }
    }.run();
  }

  @Test
  public void shouldRequireNotSelected() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotSelected(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotSelected());
      }
    }.run();
  }

  @Test
  public void shouldRequireSelected() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelected(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelected());
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
  JToggleButton target() { return target; }
  JToggleButtonFixture fixture() { return fixture; }
}