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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for <code>{@link JTableHeaderDriver}</code>.
 *
 * @author Yvonne Wang
 */
public abstract class JTableHeaderDriver_TestCase extends RobotBasedTestCase {

  MyWindow window;
  JTableHeader tableHeader;
  JTableHeaderDriver driver;

  @Override protected final void onSetUp() {
    driver = new JTableHeaderDriver(robot);
    window = MyWindow.createNew(getClass());
    tableHeader = window.table.getTableHeader();
    extraSetUp();
  }

  void extraSetUp() {}

  static Object[][] columnIndices() {
    return new Object[][] { { 0 }, { 1 } };
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  final String columnNameFromIndex(int index) {
    return String.valueOf(index);
  }

  @RunsInEDT
  final void disableTableHeader() {
    disable(tableHeader);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void assertThatColumnWasClicked(ClickRecorder recorder, int columnIndex) {
    int columnAtPoint = columnAtPoint(tableHeader, recorder.pointClicked());
    assertThat(columnAtPoint).isEqualTo(columnIndex);
  }

  @RunsInEDT
  private static int columnAtPoint(final JTableHeader header, final Point point) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return header.getTable().columnAtPoint(point);
      }
    });
  }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 200);

    final TestTable table;

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      table = new TestTable(6, 2);
      add(decorate(table));
      setPreferredSize(new Dimension(600, 400));
    }

    private static Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }
}
