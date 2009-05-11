/*
 * Created on Oct 24, 2008
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
package org.fest.swing.driver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;

/**
 * Tests for <code>{@link ComponentStateValidator}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ComponentStateValidatorTest {

  private Robot robot;
  private TestWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TestWindow.createNewWindow(getClass());
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldNotThrowErrorIfComponentIsEnabledAndShowing() {
    validateWindowIsEnabledAndShowing();
  }

  public void shouldThrowErrorIfComponentIsDisabled() {
    disable(window);
    robot.waitForIdle();
    try {
      validateWindowIsEnabledAndShowing();
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }
  
  public void shouldThrowExceptionInComponentIsNotShowing() {
    hide(window);
    robot.waitForIdle();
    try {
      validateWindowIsEnabledAndShowing();
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
  
  private void validateWindowIsEnabledAndShowing() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        ComponentStateValidator.validateIsEnabledAndShowing(window);
      }
    });
  }
}
