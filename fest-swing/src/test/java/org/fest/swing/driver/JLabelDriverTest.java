/*
 * Created on Apr 5, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link JLabelDriver}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JLabelDriverTest {

  private Robot robot;
  private JLabel label;
  private JLabelDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JLabelDriver(robot);
    MyWindow window = MyWindow.createNew();
    label = window.label;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldPassIfHasExpectedText() {
    driver.requireText(label, "Hi");
  }

  public void shouldPassIfTextMatchesPatternAsString() {
    driver.requireText(label, "H.*");
  }

  public void shouldFailIfDoesNotHaveExpectedText() {
    try {
      driver.requireText(label, "Bye");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'text'")
                                .contains("actual value:<'Hi'> is not equal to or does not match pattern:<'Bye'>");
    }
  }

  public void shouldFailIfTextDoesNotMatchPattern() {
    try {
      driver.requireText(label, regex("Bye"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'text'")
                                .contains("actual value:<'Hi'> does not match pattern:<'Bye'>");
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JLabel label = new JLabel("Hi");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTextComponentDriverTest.class);
      add(label);
      setPreferredSize(new Dimension(100, 50));
    }
  }

}
