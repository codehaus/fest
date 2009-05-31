/*
 * Created on Dec 29, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Point;
import java.awt.Robot;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link RobotEventGenerator}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class RobotEventGeneratorTest {

  private Robot robot;
  private RobotEventGenerator eventGenerator;
  
  @BeforeMethod public void setUp() throws Exception {
    Class<Robot> targetType = Robot.class;
    Method mouseMoveMethod  = targetType.getMethod("mouseMove", int.class, int.class);
    Method mousePressMethod = targetType.getMethod("mousePress", int.class);
    Method mouseWheelMethod = targetType.getMethod("mouseWheel", int.class);
    robot = createMock(targetType, array(mouseMoveMethod, mousePressMethod, mouseWheelMethod));
    eventGenerator = new RobotEventGenerator(robot, new Settings());
  }
  
  public void shouldMoveMouseToGivenCoordinates() {
    final int x = 6;
    final int y = 8;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.mouseMove(x, y);
      }

      protected void codeToTest() {
        eventGenerator.moveMouse(x, y);
      }
    }.run();
  }
  
  public void shouldPressMouseAtGivenCoordinates() {
    final Point where = new Point(6, 8);
    final int mouseButtons = BUTTON1_MASK;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.mouseMove(where.x, where.y);
        robot.mousePress(mouseButtons);
      }

      protected void codeToTest() {
        eventGenerator.pressMouse(where, mouseButtons);
      }
    }.run();
  }

  public void shouldPressMouseAtGivenAbsoluteCoordinatesIfComponentIsNull() {
    final Point where = new Point(6, 8);
    final int mouseButtons = BUTTON1_MASK;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.mouseMove(where.x, where.y);
        robot.mousePress(mouseButtons);
      }

      protected void codeToTest() {
        eventGenerator.pressMouse(null, where, mouseButtons);
      }
    }.run();
  }
  
  public void shouldRotateMouseWheel() {
    final int amount = 8;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.mouseWheel(amount);
      }

      protected void codeToTest() {
        eventGenerator.rotateMouseWheel(amount);
      }
    }.run();
  }
}
