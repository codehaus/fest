/*
 * Created on Jul 17, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Base class for test cases for <code>{@link JComponentDriver}</code>.
 *
 * @author Alex Ruiz
 */
public class JComponentDriverTestCase {

  private Robot robot;
  private JComponentDriver driver;
  private MyWindow window;

  @BeforeClass
  public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod
  public final void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    driver = new JComponentDriver(robot);
    window = MyWindow.createNew(getClass());
  }

  @AfterMethod
  public final void tearDown() {
    robot.cleanUp();
  }

  final Robot robot() { return robot; }
  final JComponentDriver driver() { return driver; }
  final JButton button() { return window.button; }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JButton button = new JButton("Click Me");

    private MyWindow(Class<?> testClass) {
      super(testClass);
      button.setToolTipText("A ToolTip");
      addComponents(button);
    }
  }

}