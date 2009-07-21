/*
 * Created on Feb 10, 2007
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
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.FrameShowTask.packAndShow;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.JPopupMenu;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.FrameDriver;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FrameFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class FrameFixtureTest extends CommonComponentFixtureTestCase<Frame> {

  private FrameDriver driver;
  private Frame target;
  private FrameFixture fixture;

  void onSetUp() {
    driver = createMock(FrameDriver.class);
    target = frame().withName("frame")
                    .withTitle("A Frame")
                    .createNew();
    fixture = new FrameFixture(robot(), target);
    fixture.driver(driver);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "frame";
    expectLookupByName(name, Frame.class);
    verifyLookup(new FrameFixture(robot(), name));
  }

  @Test(groups = GUI)
  public void shouldCreateFixtureWithNewRobotAndGivenTarget() {
    fixture = new FrameFixture(target);
    try {
      assertThat(fixture.robot).isInstanceOf(BasicRobot.class);
    } finally {
      fixture.cleanUp();
    }
  }

  @Test(groups = GUI)
  public void shouldCreateFixtureWithNewRobotAndGivenTargetName() {
    packAndShow(target);
    fixture = new FrameFixture("frame");
    try {
      assertThat(fixture.robot).isInstanceOf(BasicRobot.class);
      assertThat(fixture.component()).isSameAs(target);
    } finally {
      fixture.cleanUp();
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    fixture.driver(null);
  }

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

  public void shouldShow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.show(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.show());
      }
    }.run();
  }

  public void shouldShowWithGivenSize() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.show(target, size);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.show(size));
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

  ComponentDriver driver() { return driver; }
  Frame target() { return target; }
  FrameFixture fixture() { return fixture; }
}
