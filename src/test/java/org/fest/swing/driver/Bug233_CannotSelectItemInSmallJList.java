/*
 * Created on Feb 8, 2009
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

import java.awt.Dimension;

import javax.swing.JList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=233" target="_blank">Bug 233</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug233_CannotSelectItemInSmallJList {

  private Robot robot;
  private JListDriver driver;
  private JList list;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    driver = new JListDriver(robot);
    MyWindow window = MyWindow.createNew();
    list = window.list;
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldSelectItemWithSuperLongText() {
    driver.selectItem(list, superLongText());
    assertThat(list.getSelectedIndex()).isEqualTo(1);
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

    final JList list = new JList(array("One", superLongText()));

    private MyWindow() {
      super(Bug233_CannotSelectItemInSmallJList.class);
      list.setPreferredSize(new Dimension(5, 100));
      addComponents(list);
    }
  }

  static String superLongText() {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      b.append("a ");
    }
    return b.toString();
  }
}
