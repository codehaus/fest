/*
 * Created on Jun 6, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * GUI tests for <code>{@link JComboBoxDropDownListFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JComboBoxDropDownListFinderGuiTest {

  private MyWindow window;
  private Robot robot;
  private JComboBoxDropDownListFinder finder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    finder = new JComboBoxDropDownListFinder(robot);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindDropDownListInJComboBox() {
    showJComboBoxDropDownList();
    JList list = finder.findDropDownList();
    assertThat(list).isNotNull();
    assertThatListContains(list, "first", "second", "third");
  }

  public void shouldTryToFindDropDownListInJComboBox() {
    showJComboBoxDropDownListWithDelay();
    JList list = finder.findDropDownList();
    assertThat(list).isNotNull();
    assertThatListContains(list, "first", "second", "third");
  }

  private void showJComboBoxDropDownListWithDelay() {
    java.util.Timer timer = new Timer("showJComboBoxDropDownList", false);
    timer.schedule(new TimerTask() {
      public void run() {
        showJComboBoxDropDownList();
      }
    }, 18000);
  }

  private void showJComboBoxDropDownList() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.comboBox.showPopup();
      }
    });
    robot.waitForIdle();
  }

  private void assertThatListContains(final JList list, final String...expectedElements) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        ListModel model = list.getModel();
        int elementCount = model.getSize();
        assertThat(elementCount).isEqualTo(expectedElements.length);
        for (int i = 0; i < elementCount; i++)
          assertThat(model.getElementAt(i)).isEqualTo(expectedElements[i]);
      }
    });
  }

  public void shouldReturnNullIfJComboBoxDropDownNotFound() {
    JList list = finder.findDropDownList();
    assertThat(list).isNull();
  }

  public void shouldReturnNullIfActiveDropDownIsNotFromJComboBox() {
    robot.rightClick(window.textField);
    JList list = finder.findDropDownList();
    assertThat(list).isNull();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    final JTextField textField = new JTextField(20);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxDropDownListFinderGuiTest.class);
      JPopupMenu popupMenu = new JPopupMenu();
      popupMenu.add(new JMenuItem("One"));
      popupMenu.add(new JMenuItem("Two"));
      textField.setComponentPopupMenu(popupMenu);
      addComponents(comboBox, textField);
    }
  }
}
