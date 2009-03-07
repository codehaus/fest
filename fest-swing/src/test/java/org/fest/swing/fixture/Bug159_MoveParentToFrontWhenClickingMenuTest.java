/*
 * Created on Jul 8, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=159" target="_blank">Bug 159</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug159_MoveParentToFrontWhenClickingMenuTest {

  private static final int DELAY_BEFORE_SHOWING_MENU = 2000;

  private static Logger logger = Logger.getAnonymousLogger();
  
  private Robot robot;
  private JFrame frameToFocus;
  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    window.display();
    frameToFocus = frame().withTitle("To Focus").createNew();
    robot.showWindow(frameToFocus, new Dimension(300, 200));
    robot.focus(frameToFocus);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSelectMenuFromMenuBar() {
    JMenuItem menuItem = window.menuItemFromMenuBar;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pauseBeforeShowingMenu();
    ClickRecorder clickRecorder = attachTo(menuItem);
    fixture.click();
    assertThat(clickRecorder).wasClicked();
  }

  public void shouldSelectMenuPopupMenu() {
    JMenuItem menuItem = window.menuItemFromPopupMenu;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pauseBeforeShowingMenu();
    robot.showPopupMenu(window.textField);
    ClickRecorder clickRecorder = attachTo(menuItem);
    fixture.click();
    assertThat(clickRecorder).wasClicked();
  }
  
  private void pauseBeforeShowingMenu() {
    int delay = DELAY_BEFORE_SHOWING_MENU;
    logger.info(concat("Pausing for ", delay, " ms"));
    pause(delay);
  }

  private JMenuItemFixture fixtureFor(JMenuItem menuItem) {
    return new JMenuItemFixture(robot, menuItem);
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
 
    final JMenuItem menuItemFromMenuBar = new JMenuItem("New");
    final JMenuItem menuItemFromPopupMenu = new JMenuItem("Cut");
    final JTextField textField;

    private MyWindow() {
      super(Bug159_MoveParentToFrontWhenClickingMenuTest.class);
      setJMenuBar(new JMenuBar());
      JMenu menuFile = new JMenu("File");
      menuFile.add(menuItemFromMenuBar);
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(200, 100));
      textField = new JTextField(20);
      textField.setComponentPopupMenu(popupMenu());
      add(textField);
    }

    private JPopupMenu popupMenu() {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenu menuEdit = new JMenu("Edit");
      menuEdit.add(menuItemFromPopupMenu);
      popupMenu.add(menuEdit);
      popupMenu.setName("popupMenu");
      return popupMenu;
    }
  }
}
