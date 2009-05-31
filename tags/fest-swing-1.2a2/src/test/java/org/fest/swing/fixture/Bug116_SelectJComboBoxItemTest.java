/*
 * Created on Mar 16, 2008
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=116">Bug 116</a>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, BUG })
public class Bug116_SelectJComboBoxItemTest {

  private Robot robot;
  private JComboBox target;
  private JComboBoxFixture fixture;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    target = window.comboBox;
    fixture = new JComboBoxFixture(robot, target);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldScrollToSelectLastItem() {
    int toSelect = 99;
    fixture.selectItem(toSelect);
    assertThat(selectedIndexOf(target)).isEqualTo(toSelect);
  }

  @RunsInEDT
  private static int selectedIndexOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return comboBox.getSelectedIndex();
      }
    });
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

    final JComboBox comboBox = new JComboBox();

    private MyWindow() {
      super(Bug116_SelectJComboBoxItemTest.class);
      add(comboBox);
      int itemCount = 100;
      Object[] content = new Object[itemCount];
      for (int i = 0; i < itemCount; i++) content[i] = String.valueOf(i + 1);
      comboBox.setModel(new DefaultComboBoxModel(content));
    }
  }

}
