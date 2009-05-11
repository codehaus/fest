/*
 * Created on Aug 21, 2008
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

import javax.swing.JOptionPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.Assertions;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.test.swing.JOptionPaneLauncher.launch;

/**
 * Tests for <code>{@link JOptionPaneTitleQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JOptionPaneTitleQueryTest {

  private static final String TITLE = "My Message";

  private Robot robot;
  private MyOptionPane optionPane;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    optionPane = MyOptionPane.createNew();
    launch(optionPane, TITLE);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTitleOfJOptionPane() {
    String title = JOptionPaneTitleQuery.titleOf(optionPane);
    Assertions.assertThat(title).isEqualTo(TITLE);
  }

  private static class MyOptionPane extends JOptionPane {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyOptionPane createNew() {
      return execute(new GuiQuery<MyOptionPane>() {
        protected MyOptionPane executeInEDT() {
          return new MyOptionPane();
        }
      });
    }

    private MyOptionPane() {
      super("Hello World", INFORMATION_MESSAGE);
    }
  }
}
