/*
 * Created on Dec 17, 2007
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
import static org.fest.swing.test.builder.JInternalFrames.internalFrame;
import static org.fest.swing.test.core.Regex.regex;

import java.awt.Dimension;
import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JInternalFrameDriver;
import org.junit.Test;

/**
 * Tests for <code>{@link JInternalFrameFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixtureTest extends CommonComponentFixture_TestCase<JInternalFrame> {

  private JInternalFrameDriver driver;
  private JInternalFrame target;
  private JInternalFrameFixture fixture;

  void onSetUp() {
    driver = createMock(JInternalFrameDriver.class);
    target = internalFrame().createNew();
    fixture = new JInternalFrameFixture(robot, target);
    fixture.driver(driver);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

  @Test
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "internalFrame";
    expectLookupByName(name, JInternalFrame.class);
    verifyLookup(new JInternalFrameFixture(robot, name));
  }

  @Test
  public void shouldMoveToFront() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveToFront(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveToFront());
      }
    }.run();
  }

  @Test
  public void shouldMoveToBack() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveToBack(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveToBack());
      }
    }.run();
  }

  @Test
  public void shouldDeiconify() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.deiconify(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.deiconify());
      }
    }.run();
  }

  @Test
  public void shouldIconify() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.iconify(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.iconify());
      }
    }.run();
  }

  @Test
  public void shouldMaximize() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.maximize(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.maximize());
      }
    }.run();
  }

  @Test
  public void shouldNormalize() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.normalize(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.normalize());
      }
    }.run();
  }

  @Test
  public void shouldClose() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.close(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        fixture.close();
      }
    }.run();
  }

  @Test
  public void shouldRequireSize() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSize(target, size);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSize(size));
      }
    }.run();
  }

  @Test
  public void shouldMoveToPoint() {
    final Point p = new Point(6, 8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveTo(target, p);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveTo(p));
      }
    }.run();
  }

  @Test
  public void shouldResizeHeight() {
    final int height = 68;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeHeightTo(target, height);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeHeightTo(height));
      }
    }.run();
  }

  @Test
  public void shouldResizeWidth() {
    final int width = 68;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeWidthTo(target, width);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeWidthTo(width));
      }
    }.run();
  }

  @Test
  public void shouldResizeWidthAndHeight() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeTo(target, size);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeTo(size));
      }
    }.run();
  }

  @Test
  public void shouldBeContainerFixture() {
    assertThat(fixture).isInstanceOf(ContainerFixture.class);
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
  JInternalFrame target() { return target; }
  JInternalFrameFixture fixture() { return fixture; }
}
