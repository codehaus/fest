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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.testng.annotations.*;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractJTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class AbstractJTableCellWriterTest {

  private Robot robot;
  private TableDialogEditDemoWindow frame;
  private AbstractJTableCellWriter writer;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = new AbstractJTableCellWriterStub(robot);
    frame = TableDialogEditDemoWindow.createNew(AbstractJTableCellWriterTest.class);
    robot.showWindow(frame, new Dimension(500, 100));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "cellEditors")
  public void shouldReturnEditorForCell(int row, int column, Class<Component> editorType) {
    Component editor = writer.editorForCell(frame.table, row, column);
    assertThat(editor).isNotNull().isInstanceOf(editorType);
  }

  @DataProvider(name = "cellEditors")
  public Object[][] cellEditors() {
    return new Object[][] {
        { 0, 2, JComboBox.class },
        { 0, 3, JTextField.class },
        { 0, 4, JCheckBox.class }
    };
  }
}
