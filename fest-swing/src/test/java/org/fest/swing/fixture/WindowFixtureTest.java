/*
 * Created on Jun 11, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.*;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Timeout;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link WindowFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class WindowFixtureTest {

  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    window = MyWindow.createAndShow();
  }

  public void shouldCreateNewFixtureWithGivenWindowType() {
    ConcreteWindowFixture fixture = null;
    try {
      fixture = new ConcreteWindowFixture(MyWindow.class);
      assertThatContainsExistingHierarchy(fixture);
      assertThatManagesMyWindow(fixture);
    } finally {
      if (fixture != null) fixture.cleanUp();
    }
  }

  public void shouldCreateNewFixtureWithGivenRobotAndWindowType() {
    Robot robot = null;
    try {
      robot = BasicRobot.robotWithCurrentAwtHierarchy();
      ConcreteWindowFixture fixture = new ConcreteWindowFixture(robot, MyWindow.class);
      assertThatHasSameRobot(fixture, robot);
      assertThatManagesMyWindow(fixture);
    } finally {
      if (robot != null) robot.cleanUp();
    }
  }

  public void shouldCreateNewFixtureWithGivenWindowNameAndType() {
    ConcreteWindowFixture fixture = null;
    try {
      fixture = new ConcreteWindowFixture("MyWindow", MyWindow.class);
      assertThatContainsExistingHierarchy(fixture);
      assertThatManagesMyWindow(fixture);
    } finally {
      if (fixture != null) fixture.cleanUp();
    }
  }

  public void shouldCreateNewFixtureWithGivenRobotAndWindowNameAndType() {
    Robot robot = null;
    try {
      robot = BasicRobot.robotWithCurrentAwtHierarchy();
      ConcreteWindowFixture fixture = new ConcreteWindowFixture(robot, "MyWindow", MyWindow.class);
      assertThatHasSameRobot(fixture, robot);
      assertThatManagesMyWindow(fixture);
    } finally {
      if (robot != null) robot.cleanUp();
    }
  }

  public void shouldCreateNewFixtureWithGivenWindow() {
    ConcreteWindowFixture fixture = null;
    try {
      fixture = new ConcreteWindowFixture(window);
      assertThatContainsExistingHierarchy(fixture);
      assertThatManagesMyWindow(fixture);
    } finally {
      if (fixture != null) fixture.cleanUp();
    }
  }

  private void assertThatContainsExistingHierarchy(ConcreteWindowFixture fixture) {
    assertThat(fixture.robot.hierarchy()).isInstanceOf(ExistingHierarchy.class);
  }

  public void shouldCreateNewFixtureWithGivenRobotAndWindow() {
    Robot robot = null;
    try {
      robot = BasicRobot.robotWithCurrentAwtHierarchy();
      ConcreteWindowFixture fixture = new ConcreteWindowFixture(robot, window);
      assertThatHasSameRobot(fixture, robot);
      assertThatManagesMyWindow(fixture);
    } finally {
      if (robot != null) robot.cleanUp();
    }
  }

  private void assertThatHasSameRobot(ConcreteWindowFixture fixture, Robot robot) {
    assertThat(fixture.robot).isSameAs(robot);
  }

  private void assertThatManagesMyWindow(ConcreteWindowFixture fixture) {
    assertThat(fixture.component()).isSameAs(window);
  }

  private static class ConcreteWindowFixture extends WindowFixture<JFrame> {

    public ConcreteWindowFixture(Class<? extends JFrame> type) {
      super(type);
    }

    public ConcreteWindowFixture(Robot robot, Class<? extends JFrame> type) {
      super(robot, type);
    }

    public ConcreteWindowFixture(String name, Class<? extends JFrame> type) {
      super(name, type);
    }

    public ConcreteWindowFixture(Robot robot, String name, Class<? extends JFrame> type) {
      super(robot, name, type);
    }

    public ConcreteWindowFixture(JFrame target) {
      super(target);
    }

    public ConcreteWindowFixture(Robot robot, JFrame target) {
      super(robot, target);
    }

    public ConcreteWindowFixture show() { return this; }
    public ConcreteWindowFixture show(Dimension size) { return this; }
    public void close() {}
    public ConcreteWindowFixture focus() { return this; }
    public ConcreteWindowFixture requireFocused() { return this; }
    public ConcreteWindowFixture pressKey(int keyCode) { return this; }
    public ConcreteWindowFixture releaseKey(int keyCode) { return this; }
    public ConcreteWindowFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) { return this; }
    public ConcreteWindowFixture pressAndReleaseKeys(int... keyCodes) { return this; }
    public ConcreteWindowFixture click() { return this; }
    public ConcreteWindowFixture click(MouseButton button) { return this; }
    public ConcreteWindowFixture click(MouseClickInfo mouseClickInfo) { return this; }
    public ConcreteWindowFixture rightClick() { return this; }
    public ConcreteWindowFixture doubleClick() { return this; }
    public ConcreteWindowFixture requireEnabled() { return this; }
    public ConcreteWindowFixture requireEnabled(Timeout timeout) { return this; }
    public ConcreteWindowFixture requireDisabled() { return this; }
    public ConcreteWindowFixture requireVisible() { return this; }
    public ConcreteWindowFixture requireNotVisible() { return this; }
    public ConcreteWindowFixture moveTo(Point p) { return this; }
    public ConcreteWindowFixture moveToBack() { return this; }
    public ConcreteWindowFixture moveToFront() { return this; }
    public ConcreteWindowFixture requireSize(Dimension size) { return this; }
    public ConcreteWindowFixture resizeTo(Dimension size) { return this; }
    public ConcreteWindowFixture resizeHeightTo(int height) { return this; }
    public ConcreteWindowFixture resizeWidthTo(int width) { return this; }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return TestWindow.display(new MyWindow());
        }
      });
    }

    private MyWindow() {
      super(WindowFixtureTest.class);
      setName("MyWindow");
      setPreferredSize(new Dimension(80, 60));
    }
  }
}
