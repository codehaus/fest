/*
 * Created on Sep 5, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.AbstractButtonTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JPopupMenuDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JPopupMenuDriverTest {

  private Robot robot;
  private JPopupMenuDriver driver;
  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithCurrentAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window);
    driver = new JPopupMenuDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnsPopupLabels() {
    String[] labels = driver.menuLabelsOf(popupMenu());
    assertThat(labels).isEqualTo(array("First", "Second"));
  }

  public void shouldFindMenuItemByName() {
    JMenuItem found = driver.menuItem(popupMenu(), "first");
    assertThat(found).isSameAs(window.menuItem1);
  }

  public void shouldFindMenuItemWithGivenMatcher() {
    JMenuItem found = driver.menuItem(popupMenu(), new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
      protected boolean isMatching(JMenuItem menuItem) {
        return "Second".equals(textOf(menuItem));
      }
    });
    assertThat(found).isSameAs(window.menuItem2);
  }

  private JPopupMenu popupMenu() {
    return window.popupMenu;
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField withPopup = new JTextField("With Pop-up Menu");
    final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");

    final JMenuItem menuItem1 = new JMenuItem("First");
    final JMenuItem menuItem2 = new JMenuItem("Second");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JPopupMenuDriverTest.class);
      add(withPopup);
      withPopup.setComponentPopupMenu(popupMenu);
      popupMenu.add(menuItem1);
      menuItem1.setName("first");
      popupMenu.add(menuItem2);
    }
  }
}
