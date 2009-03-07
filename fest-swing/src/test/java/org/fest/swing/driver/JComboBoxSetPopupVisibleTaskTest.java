/*
 * Created on Aug 8, 2008
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

import javax.swing.JComboBox;
import javax.swing.JList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.query.ComponentShowingQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxSetPopupVisibleTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JComboBoxSetPopupVisibleTaskTest {

  private Robot robot;
  private MyWindow window;
  private JComboBox comboBox;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetPopupVisibleAndInvisible() {
    setPopupVisible(true);
    assertThat(popupIsShowing()).isTrue();
    setPopupVisible(false);
    try {
      comboBoxPopup();
      fail("Expecting JList not found!");
    } catch (ComponentLookupException expected) {}
  }

  private void setPopupVisible(final boolean visible) {
    JComboBoxSetPopupVisibleTask.setPopupVisible(comboBox, visible);
    robot.waitForIdle();
  }

  private boolean popupIsShowing() {
    return ComponentShowingQuery.isShowing(comboBoxPopup());
  }

  private JList comboBoxPopup() {
    return robot.finder().findByType(window, JList.class);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
 
    final JComboBox comboBox = new JComboBox(array("One", "Two"));

    private MyWindow() {
      super(JComboBoxSetPopupVisibleTaskTest.class);
      addComponents(comboBox);
    }
  }
}
