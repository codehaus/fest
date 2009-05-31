/*
 * Created on Jun 10, 2008
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

import javax.swing.JTable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Test case for implementations of <code>{@link JTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public abstract class JTableCellWriterTestCase {

  private Robot robot;
  private TableDialogEditDemoWindow window;
  private JTableCellWriter writer;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = createWriter();
    window = TableDialogEditDemoWindow.createNew(getClass());
    robot.showWindow(window, new Dimension(500, 100));
  }

  protected abstract JTableCellWriter createWriter();

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorIfEditorComponentCannotBeHandledWhenEnteringValue() {
    try {
      writer.enterValue(window.table, 0, 1, "hello");
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertUnableToActivateErrorMessageIsCorrect(e);
    }
  }

  public void shouldThrowErrorIfEditorComponentCannotBeHandledWhenStartingEditing() {
    try {
      writer.startCellEditing(window.table, 0, 1);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertUnableToActivateErrorMessageIsCorrect(e);
    }
  }

  private void assertUnableToActivateErrorMessageIsCorrect(ActionFailedException e) {
    assertThat(e.getMessage()).contains("Unable to find or activate editor");
  }
  
  protected final JTable table() {
    return window.table;
  }

  protected final Robot robot() {
    return robot;
  }

  protected final JTableCellWriter writer() {
    return writer;
  }

  protected final Object valueAt(int row, int column) {
    return cellValueOf(window.table, row, column);
  }
}
