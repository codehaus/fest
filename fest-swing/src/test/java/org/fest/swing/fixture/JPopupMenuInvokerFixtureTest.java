/*
 * Created on Mar 11, 2008
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

import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Tests for <code>{@link JPopupMenuInvokerFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JPopupMenuInvokerFixtureTest {

  private Robot robot;
  private JPopupMenuInvokerFixture<JTextComponent> fixture;
  private MyWindow window;

  @BeforeClass public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    fixture = new JPopupMenuInvokerFixture<JTextComponent>(robot, window.textField) {};
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldShowJPopupMenu() {
    JPopupMenuFixture popupMenu = fixture.showPopupMenu();
    assertThat(popupMenu.target).isSameAs(window.popupMenu);
  }
  
  @Test public void shouldShowJPopupMenuAtPoint() {
    Point p = new Point(8, 6);
    JPopupMenuFixture popupMenu = fixture.showPopupMenuAt(p);
    assertThat(popupMenu.target).isSameAs(window.popupMenu);
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

    private final JTextField textField = new JTextField(20);
    private final JPopupMenu popupMenu = new JPopupMenu();
    
    private MyWindow() {
      super(JPopupMenuInvokerFixtureTest.class);
      addComponents(textField);
      popupMenu.add(new JMenuItem("Hello"));
      textField.setComponentPopupMenu(popupMenu);
    }
  }
}
