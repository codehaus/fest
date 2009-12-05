/*
 * Created on Jul 5, 2007
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
import static org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint.*;
import static org.fest.swing.test.builder.JToolBars.toolBar;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JToolBarDriver;
import org.junit.Test;

/**
 * Tests for <code>{@link JToolBarFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarFixtureTest extends CommonComponentFixture_TestCase<JToolBar> {

  private JToolBarDriver driver;
  private JToolBar target;
  private JToolBarFixture fixture;

  void onSetUp() {
    driver = createMock(JToolBarDriver.class);
    target = toolBar().createNew();
    fixture = new JToolBarFixture(robot, target);
    fixture.driver(driver);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  @Test
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "toolBar";
    expectLookupByName(name, JToolBar.class);
    verifyLookup(new JToolBarFixture(robot, name));
  }

  @Test
  public void shouldFloatToPoint() {
    final Point p = new Point(8, 6);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.floatTo(target, p.x, p.y);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.floatTo(p));
      }
    }.run();
  }

  @Test
  public void shouldUnfloat() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unfloat(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.unfloat());
      }
    }.run();
  }

  @Test
  public void shouldBeContainerFixture() {
    assertThat(fixture).isInstanceOf(ContainerFixture.class);
  }

  @Test
  public void shouldUnfloatUsingGivingConstraint() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unfloat(target, NORTH.value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.unfloat(NORTH));
      }
    }.run();
  }

  // TODO parameterize
  public Object[][] unfloatConstraints() {
    return new Object[][] { { NORTH }, { EAST }, { SOUTH }, { WEST } };
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
  JToolBar target() { return target; }
  JToolBarFixture fixture() { return fixture; }
}