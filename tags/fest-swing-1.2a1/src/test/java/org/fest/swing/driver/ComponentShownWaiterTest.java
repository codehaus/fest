/*
 * Created on Nov 21, 2008
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
import org.fest.swing.core.BasicRobot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.util.StopWatch;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;
import static org.fest.swing.timing.Pause.pause;

/**
 * Tests for <code>{@link ComponentShownWaiter}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ComponentShownWaiterTest {

  private Robot robot;
  private TestWindow window;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    window = TestWindow.createNewWindow(getClass());
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldTimeoutIfComponentNeverShown() {
    StopWatch stopWatch = startNewStopWatch();
    int timeout = 500;
    try {
      ComponentShownWaiter.waitTillShown(window, timeout);
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      stopWatch.stop();
    }
    assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(timeout);
  }
  
  public void shouldWaitTillComponentIsShown() {
    StopWatch stopWatch = startNewStopWatch();
    int timeout = 10000;
    new Thread() {
      @Override public void run() {
        pause(1000);
        robot.showWindow(window);
      }
    }.start();
    ComponentShownWaiter.waitTillShown(window, timeout);
    stopWatch.stop();
    assertThat(stopWatch.ellapsedTime()).isLessThan(timeout);
  }
}
