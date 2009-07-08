/*
 * Created on Jul 7, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentLookupScope.DEFAULT;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.BUG;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Component;

import javax.swing.JLabel;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-139" target="_blank">FEST_139</a>.
 *
 * @author Woody Folsom
 * @author Alex Ruiz
 */
@Test(groups = { BUG, GUI })
public class FEST139_FinderNotUsingRobotSettings {

  private Robot robot;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithCurrentAwtHierarchy();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void finderShouldUseSettingsFromRobot() {
    MyWindow.createNew();
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    assertThat(robot.settings().componentLookupScope()).isEqualTo(DEFAULT);
    Component found = robot.finder().findByName("testLabel");
    assertThat(found).isSameAs(window.label);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JLabel label = new JLabel("Test Label");

    private MyWindow() {
      super(FEST139_FinderNotUsingRobotSettings.class);
      label.setName("testLabel");
      addComponents(label);
    }
  }
}
