/*
 * Created on Nov 29, 2008
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

import java.awt.Dimension;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=225" target="_blank">Bug 225</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug225_PressF2ToStartEditingTableCellTest {

  private Robot robot;
  private JTableTextComponentEditorCellWriter writer;
  private TableDialogEditDemoWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = new JTableTextComponentEditorCellWriter(robot);
    window = TableDialogEditDemoWindow.createNew(getClass());
    robot.showWindow(window, new Dimension(500, 100));
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldEditCellWithTextFieldAsEditor() {
    int row = 4;
    int col = 3;
    writer.enterValue(window.table, row, col, "8");
    assertThat(valueAt(row, col)).isEqualTo(8);
  }

  @RunsInEDT
  private Object valueAt(int row, int column) {
    return cellValueOf(window.table, row, column);
  }
}
